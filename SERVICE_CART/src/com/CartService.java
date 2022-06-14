package com;

import model.Cart;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Cart")
public class CartService {
	Cart CartObj = new Cart();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCart() {
		return CartObj.readCart();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCart(@FormParam("rid") String rid, 
			@FormParam("rname") String rname,
			@FormParam("pname") String pname, 
			@FormParam("rDesp") String rDesp, 
			@FormParam("rdate") String rdate) {
		String output = CartObj.insertCart(rid, rname, pname, rDesp, rdate);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateCart(String CartData) {
		// Convert the input string to a JSON object
		JsonObject CartObject = new JsonParser().parse(CartData).getAsJsonObject();

		// Read the values from the JSON object
		String rid = CartObject.get("rid").getAsString();
		String rname = CartObject.get("rname").getAsString();
		String pname = CartObject.get("pname").getAsString();
		String rDesp = CartObject.get("rDesp").getAsString();
		String rdate = CartObject.get("rdate").getAsString();

		String output = CartObj.updateCart(rid, rname, pname, rDesp, rdate);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCart(String CartData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(CartData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String rid = doc.select("rid").text();
		String output = CartObj.deleteCart(rid);
		return output;
	}
}
