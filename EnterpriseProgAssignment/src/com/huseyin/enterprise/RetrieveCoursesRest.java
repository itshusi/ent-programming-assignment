package com.huseyin.enterprise;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

@Path("/retrievecourses")
public class RetrieveCoursesRest {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  private List<Course> getAllCourses() {
    List<Course> myData = new ArrayList();
    CourseDAO cConnect = new CourseDAO();
    myData = cConnect.getAllCourses();
    return myData;
  }

  @GET
  @Produces(MediaType.TEXT_XML)
  @Path("/xml")
  public String getCoursesBrowserXml() {
    Courses courses = new Courses();
    JAXBContext jaxbContext;
    try {
      jaxbContext = JAXBContext.newInstance(Course.class, Courses.class);

      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

      // output pretty printed
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      for (Course course : getAllCourses()) {
        courses.addCourse(course);
      }
      StringWriter writer = new StringWriter();
      jaxbMarshaller.marshal(courses, writer);
      return writer.toString();
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  return null;
}
  

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/json")
  public String getCoursesBrowserJson() {
    Courses courses = new Courses();
    JAXBContext jaxbContext;
    try {
      jaxbContext = JAXBContext.newInstance(Course.class, Courses.class);

      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
      jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      for (Course course : getAllCourses()) {
        courses.addCourse(course);
      }
      StringWriter writer = new StringWriter();
      jaxbMarshaller.marshal(courses, writer);
      return writer.toString();
    } catch (JAXBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/text")
  public String getCoursesBrowserText() {
    String text = "";
    Iterator it = getAllCourses().iterator();
    while (it.hasNext()) {
      Course course = (Course) it.next();
      // For each Course object create <BeanLocationTrack> element
      text += "id: " + course.getId() + ", " + "name: " + course.getName() + ", " + "description: "
          + course.getDescription() + ", " + "degreeLevel: " + course.getDegreeLevel() + ", "
          + "courseYear: " + course.getCourseYear() + ", " + "ucasCode: " + course.getUcasCode()
          + ", " + "length: " + course.getLength() + " ";
      if (it.hasNext()) {
        text += "|";
        text += "\n";
      }
    }

    return text;
  }

}
