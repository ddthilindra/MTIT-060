package com;

import model.Researcher;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Researcher")
public class ReseacherService {
	Researcher ResearcherObj = new Researcher();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readResearchers() {
		return ResearcherObj.readResearchers();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearcher(@FormParam("rid") String rid, 
			@FormParam("rname") String rname,
			@FormParam("pname") String pname, 
			@FormParam("rDesp") String rDesp, 
			@FormParam("rdate") String rdate) {
		String output = ResearcherObj.insertResearcher(rid, rname, pname, rDesp, rdate);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateResearcher(String ResearcherData) {
		// Convert the input string to a JSON object
		JsonObject ResearcherObject = new JsonParser().parse(ResearcherData).getAsJsonObject();

		// Read the values from the JSON object
		String rid = ResearcherObject.get("rid").getAsString();
		String rname = ResearcherObject.get("rname").getAsString();
		String pname = ResearcherObject.get("pname").getAsString();
		String rDesp = ResearcherObject.get("rDesp").getAsString();
		String rdate = ResearcherObject.get("rdate").getAsString();

		String output = ResearcherObj.updateResearcher(rid, rname, pname, rDesp, rdate);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteResearcher(String ResearcherData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(ResearcherData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String rid = doc.select("rid").text();
		String output = ResearcherObj.deleteResearcher(rid);
		return output;
	}
}
