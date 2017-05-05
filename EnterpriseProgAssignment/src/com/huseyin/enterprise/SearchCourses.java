package com.huseyin.enterprise;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 * Servlet implementation class RetrieveCourses
 */

public class SearchCourses extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SearchCourses() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    PrintWriter out = response.getWriter();
    String dataType = request.getParameter("dataType");
    String searchName = request.getParameter("searchName");
    List<Course> myData = new ArrayList();
    CourseDAO cConnect = new CourseDAO();
    myData = cConnect.getAllCourseByName(searchName);

    if (searchName.equals("") || searchName.equals(" ") || myData.isEmpty()) {
      out.print("No matches were found.");
      myData = new ArrayList();
    } else {
      if ("json".equalsIgnoreCase(dataType)) {
        response.setContentType("application/json");
        Courses courses = new Courses();
        JAXBContext jaxbContext;
        try {
          jaxbContext = JAXBContext.newInstance(Course.class, Courses.class);

          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
          jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
          jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
          for (Course course : myData) {
            courses.addCourse(course);
          }

          jaxbMarshaller.marshal(courses, out);
          out.close();
        } catch (JAXBException e) {
          e.printStackTrace();
        }

      } else if ("text".equalsIgnoreCase(dataType)) {
        response.setContentType("text/plain");
        String text = "";
        Iterator it = myData.iterator();
        while (it.hasNext()) {
          Course course = (Course) it.next();
          // For each Course object create <BeanLocationTrack> element
          text += "id: " + course.getId() + ", " + "name: " + course.getName() + ", "
              + "description: " + course.getDescription() + ", " + "degreeLevel: "
              + course.getDegreeLevel() + ", " + "courseYear: " + course.getCourseYear() + ", "
              + "ucasCode: " + course.getUcasCode() + ", " + "length: " + course.getLength() + " ";
          if (it.hasNext()) {
            text += "|";
            text += "\n";
          }

        }
        out = response.getWriter();
        out.print(text);
      } else if ("xml".equalsIgnoreCase(dataType)) {
        response.setContentType("text/xml");
        out = response.getWriter();
        try {
          Courses courses = new Courses();
          // code to create a xml file
          JAXBContext jaxbContext = JAXBContext.newInstance(Course.class, Courses.class);
          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
          // output pretty printed
          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

          for (Course course : myData) {
            courses.addCourse(course);
          }

          jaxbMarshaller.marshal(courses, out);

          out.close();

        } catch (JAXBException e) {
          e.printStackTrace();
        }

      }
    }

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
