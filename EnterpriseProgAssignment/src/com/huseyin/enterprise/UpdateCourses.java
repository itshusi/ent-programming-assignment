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

public class UpdateCourses extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public UpdateCourses() {
    super();
  }
  
  public void processUpdate(HttpServletResponse response, String updateValue, String attribute, int courseID) throws IOException {
    PrintWriter out = response.getWriter();
    if (!(updateValue.isEmpty() || attribute.isEmpty())) {
      CourseDAO cConnect = new CourseDAO();
      String error = cConnect.updateCourse(courseID, attribute, updateValue);
      if (error != null) {
        out.print("Course with ID " + courseID + " does not exist.");
      } else {
        out.print("Course with ID " + courseID + " has been updated in the database");
      }
    } else {
      out.print("No course has been updated to the database as one or more fields are empty");
    }
    out.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPut(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPut(request, response);
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    String dataType = request.getParameter("dataType");

    if ("text".equalsIgnoreCase(dataType)) {
      String updateValue = request.getParameter("updateValue");
      String attribute = request.getParameter("attribute");
      int courseID = Integer.parseInt(request.getParameter("id"));

      processUpdate(response, updateValue, attribute, courseID);
    } else if ("json".equalsIgnoreCase(dataType)) {
      // Determine the parameters passed to the servlet
      String jsonData = request.getParameter("data");
      // Convert to a JSON object to print data
      JsonParser jp = new JsonParser(); // from gson
      JsonElement root = jp.parse(jsonData); // Convert the input stream to a json element

      JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.
      String updateValue = rootobj.get("updateValue").getAsString();
      String attribute = rootobj.get("attribute").getAsString();
      int courseID = rootobj.get("id").getAsInt();

      processUpdate(response, updateValue, attribute, courseID);
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
        String updateValue = root.getElementsByTagName("updateValue").item(0).getTextContent();
        String attribute = root.getElementsByTagName("attribute").item(0).getTextContent();
        int courseID = Integer.parseInt(root.getElementsByTagName("id").item(0).getTextContent());

        processUpdate(response, updateValue, attribute, courseID);
      } catch (SAXException | ParserConfigurationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
