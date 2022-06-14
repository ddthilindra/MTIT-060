package com;

import model.Wishlist;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Wishlist")
public class WishlistService {
	Wishlist WishlistObj = new Wishlist();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readWishlist() {
		return WishlistObj.readWishlist();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearcher(@FormParam("wid") String wid, 
			@FormParam("cname") String cname,
			@FormParam("items") String items, 
			@FormParam("des") String des, 
			@FormParam("date") String date) {
		String output = WishlistObj.insertWishlist(wid, cname, items, des, date);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateWishlist(String WishlistData) {
		// Convert the input string to a JSON object
		JsonObject WishlistObject = new JsonParser().parse(WishlistData).getAsJsonObject();

		// Read the values from the JSON object
		String wid = WishlistObject.get("wid").getAsString();
		String cname = WishlistObject.get("cname").getAsString();
		String items = WishlistObject.get("items").getAsString();
		String des = WishlistObject.get("des").getAsString();
		String date = WishlistObject.get("date").getAsString();

		String output = WishlistObj.updateWishlist(wid, cname, items, des, date);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteWishlist(String WishlistData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(WishlistData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String wid = doc.select("wid").text();
		String output = WishlistObj.deleteWishlist(wid);
		return output;
	}
}
