package com;

import model.Delivery;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Delivery")
public class DeliveryService {
	Delivery DeliveryObj = new Delivery();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readDelivery() {
		return DeliveryObj.readDelivery();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertDelivery(@FormParam("rid") String did, 
			@FormParam("cname") String cname,
			@FormParam("date") String date, 
			@FormParam("Dest") String Dest, 
			@FormParam("etime") String etime) {
		String output = DeliveryObj.insertDelivery(did, cname, date, Dest, etime);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateDelivery(String DeliveryData) {
		// Convert the input string to a JSON object
		JsonObject DeliveryObject = new JsonParser().parse(DeliveryData).getAsJsonObject();

		// Read the values from the JSON object
		String did = DeliveryObject.get("did").getAsString();
		String cname = DeliveryObject.get("cname").getAsString();
		String date = DeliveryObject.get("date").getAsString();
		String Dest = DeliveryObject.get("Dest").getAsString();
		String etime = DeliveryObject.get("etime").getAsString();

		String output = DeliveryObj.updateDelivery(did, cname, date, Dest, etime);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDelivery(String DeliveryData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(DeliveryData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String did = doc.select("rid").text();
		String output = DeliveryObj.deleteDelivery(did);
		return output;
	}
}
