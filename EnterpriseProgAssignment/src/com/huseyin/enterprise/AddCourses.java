package com.huseyin.enterprise;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class AddCourses
 */

public class AddCourses extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AddCourses() {
    super();
  }
  
  public void processAdd(HttpServletResponse response, String msg, String name, String description, String degreeLevel, String courseYear, String ucasCode, String length) throws IOException{
    PrintWriter out = response.getWriter();
    if (!(name.isEmpty() || description.isEmpty() || degreeLevel.isEmpty() || courseYear.isEmpty()
        || ucasCode.isEmpty() || length.isEmpty())) {
      Course course = new Course(name, description, degreeLevel, courseYear, ucasCode, length);
      CourseDAO cConnect = new CourseDAO();
      cConnect.addCourse(course);
      out.print("Course called " + name + " has been " + msg + " added to the database");
    } else {
      out.print("No course has been added to the database as one or more fields are empty");
    }
    out.close();
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    String dataType = request.getParameter("dataType");
    if ("text".equalsIgnoreCase(dataType)) {
      // Determine the parameters passed to the servlet
      String name = request.getParameter("name");
      String description = request.getParameter("description");
      String degreeLevel = request.getParameter("degreeLevel");
      String courseYear = request.getParameter("courseYear");
      String ucasCode = request.getParameter("ucasCode");
      String length = request.getParameter("length");
      processAdd(response, "", name, description, degreeLevel, courseYear, ucasCode, length);
    } else if ("json".equalsIgnoreCase(dataType)) {
      // Determine the parameters passed to the servlet
      String jsonData = request.getParameter("data");
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

      processAdd(response, "converted from JSON and", name, description, degreeLevel, courseYear, ucasCode, length);
    } else if ("xml".equalsIgnoreCase(dataType)) {
      String xmlData = request.getParameter("data");
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

        processAdd(response, "converted from XML and", name, description, degreeLevel, courseYear, ucasCode, length);
      } catch (SAXException | ParserConfigurationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}

}
