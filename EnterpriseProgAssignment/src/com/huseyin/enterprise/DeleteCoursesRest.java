package com.huseyin.enterprise;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/deletecourses")
public class DeleteCoursesRest {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  @DELETE
  @Consumes("application/xml")
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/xml")
  public String deleteCourseXml(String xmlData) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringBuilder xmlStringBuilder = new StringBuilder();
      xmlStringBuilder.append(xmlData);
      ByteArrayInputStream input =
          new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
      Document doc = builder.parse(input);
      Element root = doc.getDocumentElement();
      int courseID = Integer.parseInt(root.getElementsByTagName("id").item(0).getTextContent());


      if (!(courseID == 0)) {
        CourseDAO cConnect = new CourseDAO();
        String error = cConnect.deleteCourse(courseID);
        if (error != null) {
          return "Course with ID " + courseID + " does not exist.";
        } else {
          return "Course with ID " + courseID + " has been delete from the database";
        }
      } else {
        return "No course has been deleted from the database as one or more fields are empty";
      }
    } catch (SAXException | ParserConfigurationException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "An error occured and the course has not been added to the database";
  }

  @DELETE
  @Consumes("application/json")
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/json")
  public String deleteCourseJson(String jsonData) {
    // Convert to a JSON object to print data
    JsonParser jp = new JsonParser(); // from gson
    JsonElement root = jp.parse(jsonData); // Convert the input stream to a json element

    JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.
    int courseID = rootobj.get("id").getAsInt();


    if (!(courseID == 0)) {
      CourseDAO cConnect = new CourseDAO();
      String error = cConnect.deleteCourse(courseID);
      if (error != null) {
        return "Course with ID " + courseID + " does not exist.";
      } else {
        return "Course with ID " + courseID + " has been delete from the database";
      }
    } else {
      return "No course has been deleted from the database as one or more fields are empty";
    }
  }

  @DELETE
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/text/{courseID}")
  public String deleteCourseTxt(@PathParam("courseID") int courseID) {

    if (!(courseID == 0)) {
      CourseDAO cConnect = new CourseDAO();
      String error = cConnect.deleteCourse(courseID);
      if (error != null) {
        return "Course with ID " + courseID + " does not exist.";
      } else {
        return "Course with ID " + courseID + " has been delete from the database";
      }
    } else {
      return "No course has been deleted from the database as one or more fields are empty";
    }

  }

}
