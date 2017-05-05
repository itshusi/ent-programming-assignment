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

public class DeleteCourses extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteCourses() {
    super();
  }
  public void deleteProcess(HttpServletResponse response, int courseID) throws IOException{
    PrintWriter out = response.getWriter();
    if (!(courseID == 0)) {
      CourseDAO cConnect = new CourseDAO();
      String error = cConnect.deleteCourse(courseID);
      if (error != null) {
        out.print("Course with ID " + courseID + " does not exist.");
      } else {
        out.print("Course with ID " + courseID + " has been delete from the database");
      }
    } else {
      out.print("No course has been deleted from the database as one or more fields are empty");
    }
    out.close();
  }
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doDelete(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doDelete(request, response);
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    String dataType = request.getParameter("dataType");

    if ("text".equalsIgnoreCase(dataType)) {
      int courseID = Integer.parseInt(request.getParameter("id"));
      
      deleteProcess(response, courseID);
    } else if ("json".equalsIgnoreCase(dataType)) {
      // Determine the parameters passed to the servlet
      String jsonData = request.getParameter("data");
      // Convert to a JSON object to print data
      JsonParser jp = new JsonParser(); // from gson
      JsonElement root = jp.parse(jsonData); // Convert the input stream to a json element

      JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.
      int courseID = rootobj.get("id").getAsInt();

      deleteProcess(response, courseID);
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
        int courseID = Integer.parseInt(root.getElementsByTagName("id").item(0).getTextContent());

        deleteProcess(response, courseID);
      } catch (SAXException | ParserConfigurationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
