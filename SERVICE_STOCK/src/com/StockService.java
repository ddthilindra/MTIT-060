package com;

import model.Stock;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Stock")
public class StockService {
	Stock StockObj = new Stock();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readStock() {
		return StockObj.readStock();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearcher(@FormParam("sid") String sid, 
			@FormParam("rname") String iname,
			@FormParam("pname") String qty, 
			@FormParam("rDesp") String skeepr, 
			@FormParam("rdate") String sdate) {
		String output = StockObj.insertStock(sid, iname, qty, skeepr, sdate);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateStock(String StockData) {
		// Convert the input string to a JSON object
		JsonObject StockObject = new JsonParser().parse(StockData).getAsJsonObject();

		// Read the values from the JSON object
		String sid = StockObject.get("sid").getAsString();
		String iname = StockObject.get("iname").getAsString();
		String qty = StockObject.get("qty").getAsString();
		String skeepr = StockObject.get("skeepr").getAsString();
		String sdate = StockObject.get("sdate").getAsString();

		String output = StockObj.updateStock(sid, iname, qty, skeepr, sdate);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteStock(String StockData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(StockData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String sid = doc.select("rid").text();
		String output = StockObj.deleteStock(sid);
		return output;
	}
}
