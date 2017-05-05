package com.huseyin.enterprise;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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

@Path("/addcourses")
public class AddCoursesRest {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  
  @POST
  @Consumes("application/xml")
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/xml")
  public String addCourseXml(String xmlData) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringBuilder xmlStringBuilder = new StringBuilder();
      xmlStringBuilder.append(xmlData);
      ByteArrayInputStream input =
          new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
      Document doc = builder.parse(input);
      Element root = doc.getDocumentElement();
      String name = root.getElementsByTagName("name").item(0).getTextContent();
      String description = root.getElementsByTagName("description").item(0).getTextContent();
      String degreeLevel = root.getElementsByTagName("degreeLevel").item(0).getTextContent();
      String courseYear = root.getElementsByTagName("courseYear").item(0).getTextContent();
      String ucasCode = root.getElementsByTagName("ucasCode").item(0).getTextContent();
      String length = root.getElementsByTagName("length").item(0).getTextContent();

      if (!(name.isEmpty() || description.isEmpty() || degreeLevel.isEmpty() || courseYear.isEmpty()
          || ucasCode.isEmpty() || length.isEmpty())) {
        Course course = new Course(name, description, degreeLevel, courseYear, ucasCode, length);
        CourseDAO cConnect = new CourseDAO();
        cConnect.addCourse(course);
        return "Course called " + name + " has been converted from XML and added to the database";
      } else {
        return "No course has been added to the database as one or more fields are empty";
      }
    } catch (SAXException | ParserConfigurationException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "An error occured and the course has not been added to the database";
  }

  @POST
  @Consumes("application/json")
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/json/")
  public String addCourseJson(String jsonData) {
    // Convert to a JSON object to print data
    JsonParser jp = new JsonParser(); // from gson
    JsonElement root = jp.parse(jsonData); // Convert the input stream to a json element

    JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.
    String name = rootobj.get("name").getAsString();
    String description = rootobj.get("description").getAsString();
    String degreeLevel = rootobj.get("degreeLevel").getAsString();
    String courseYear = rootobj.get("courseYear").getAsString();
    String ucasCode = rootobj.get("ucasCode").getAsString();
    String length = rootobj.get("length").getAsString();
    if (!(name.isEmpty() || description.isEmpty() || degreeLevel.isEmpty() || courseYear.isEmpty()
        || ucasCode.isEmpty() || length.isEmpty())) {
      Course course = new Course(name, description, degreeLevel, courseYear, ucasCode, length);
      CourseDAO cConnect = new CourseDAO();
      cConnect.addCourse(course);
      return "Course called " + name + " has been converted from JSON and added to the database";
    } else {
      return "No course has been added to the database as one or more fields are empty";
    }

  }

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/text/{name}/{description}/{degreeLevel}/{courseYear}/{ucasCode}/{length}")
  public String addCourseTxt(@PathParam("name") String name,
      @PathParam("description") String description, @PathParam("degreeLevel") String degreeLevel,
      @PathParam("courseYear") String courseYear, @PathParam("ucasCode") String ucasCode,
      @PathParam("length") String length) {
    if (!(name.isEmpty() || description.isEmpty() || degreeLevel.isEmpty() || courseYear.isEmpty()
        || ucasCode.isEmpty() || length.isEmpty())) {
      Course course = new Course(name, description, degreeLevel, courseYear, ucasCode, length);
      CourseDAO cConnect = new CourseDAO();
      cConnect.addCourse(course);
      return "Course called " + name + " has been added to the database";
    } else {
      return "No course has been added to the database as one or more fields are empty";
    }

  }

}
