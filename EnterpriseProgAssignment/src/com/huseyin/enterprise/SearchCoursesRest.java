package com.huseyin.enterprise;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

@Path("/searchcourses")
public class SearchCoursesRest {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String error = "";

  private List<Course> getCoursesByName(String name) {
    List<Course> myData = new ArrayList();
    CourseDAO cConnect = new CourseDAO();
    myData = cConnect.getAllCourseByName(name);
    return myData;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/{searchName}/xml")
  public String getCoursesBrowserXml(@PathParam("searchName") String name) {
    if (name.equals(" ") || getCoursesByName(name).isEmpty()) {
      error = "No matches were found.";
      return error;
    } else {
      Courses courses = new Courses();
      JAXBContext jaxbContext;
      try {
        jaxbContext = JAXBContext.newInstance(Course.class, Courses.class);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        for (Course course : getCoursesByName(name)) {
          courses.addCourse(course);
        }
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(courses, writer);
        return writer.toString();
      } catch (JAXBException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{searchName}/json")
  public String getCoursesBrowserJson(@PathParam("searchName") String name) {
    if (name.equals(" ") || getCoursesByName(name).isEmpty()) {
      error = "No matches were found.";
      return error;
    } else {
      Courses courses = new Courses();
      JAXBContext jaxbContext;
      try {
        jaxbContext = JAXBContext.newInstance(Course.class, Courses.class);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        for (Course course : getCoursesByName(name)) {
          courses.addCourse(course);
        }
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(courses, writer);
        return writer.toString();
      } catch (JAXBException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return null;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/{searchName}/text")
  public String getCoursesBrowserText(@PathParam("searchName") String name) {
    if (name.equals(" ") || getCoursesByName(name).isEmpty()) {
      error = "No matches were found.";
      return error;
    } else {
      String text = "";
      Iterator it = getCoursesByName(name).iterator();
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

      return text;
    }
  }

}
