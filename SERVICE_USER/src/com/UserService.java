package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.User;

@Path("/User")
public class UserService {
	User UserObj = new User();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUser() {
		return UserObj.readUser();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUser(@FormParam("uName") String uName,
			
	 @FormParam("uAddress") String uAddress,
	 @FormParam("pName") String pName,
	 @FormParam("uDate") String uDate,
	 @FormParam("uEmail") String uEmail,
	 @FormParam("pNo") String pNo)
	{
	 String output = UserObj.insertUser(uName, uAddress, pName, uDate, uEmail, pNo);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(String userData)
	{
	//Convert the input string to a JSON object
	 JsonObject regObject = new JsonParser().parse(userData).getAsJsonObject();
	//Read the values from the JSON object
	 String uID = regObject.get("uID").getAsString();
	 String uName = regObject.get("uName").getAsString();
	 String uAddress = regObject.get("uAddress").getAsString();
	 String pName = regObject.get("pName").getAsString();
	 String uDate = regObject.get("uDate").getAsString();
	 String uEmail = regObject.get("uEmail").getAsString();
	 String pNo = regObject.get("pNo").getAsString();
	 String output = UserObj.updateUser(uID, uName, uAddress, pName, uDate, uEmail, pNo);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(String userData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(userData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String uID = doc.select("uID").text();
	 String output = UserObj.deleteUser(uID);
	return output;
	}
}
