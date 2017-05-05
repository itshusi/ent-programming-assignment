package com.huseyin.enterprise;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Courses")
public class Courses {
  private List<Course> courses = new ArrayList<Course>();

  @XmlElements(value = {@XmlElement(name = "Course", type = Course.class)})
  public List<Course> getCourses() {
    return courses;
  }

  public void addCourse(Course course) {
    courses.add(course);
  }
}
