package com;

import model.Payment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payment")

public class PaymentService {
	Payment PaymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment() {
		return PaymentObj.readPayment();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("pyId") String pyId, 
			@FormParam("pName") String pName,
			@FormParam("cName") String cName,
			@FormParam("pyDate") String pyDate,
			@FormParam("amount") String amount) {
		String output = PaymentObj.insertPayment(pyId, pName, cName, pyDate, amount);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePayment(String paymentData) {
		// Convert the input string to a JSON object
		JsonObject PayObject = new JsonParser().parse(paymentData).getAsJsonObject();

		// Read the values from the JSON object
		String pyId = PayObject.get("pyId").getAsString();
		String pName = PayObject.get("pName").getAsString();
		String cName = PayObject.get("cName").getAsString();
		String pyDate = PayObject.get("pyDate").getAsString();
		String amount = PayObject.get("amount").getAsString();
		
		String output = PaymentObj.updatePayment(pyId, pName, cName, pyDate, amount);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element
		String pyId = doc.select("pyId").text();
		String output = PaymentObj.deletePayment(pyId);
		return output;
	}
}
