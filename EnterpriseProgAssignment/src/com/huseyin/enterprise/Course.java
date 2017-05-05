package com.huseyin.enterprise;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(
    propOrder = {"id", "name", "description", "degreeLevel", "courseYear", "ucasCode", "length"})
public class Course {
  private int id;
  private String name;
  private String description;
  private String degreeLevel;
  private String courseYear;
  private String ucasCode;
  private String length;

  public Course(int id) {
    this.id = id;
  }

  public Course(int id, String name, String description, String degreeLevel, String courseYear,
      String ucasCode, String length) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.degreeLevel = degreeLevel;
    this.courseYear = courseYear;
    this.ucasCode = ucasCode;
    this.length = length;
  }

  public Course(String name, String description, String degreeLevel, String courseYear,
      String ucasCode, String length) {
    this.name = name;
    this.description = description;
    this.degreeLevel = degreeLevel;
    this.courseYear = courseYear;
    this.ucasCode = ucasCode;
    this.length = length;
  }

  public Course() {}

  public int getId() {
    return id;
  }

  @XmlElement(name = "id")
  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  @XmlElement(name = "name")
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  @XmlElement(name = "description")
  public void setDescription(String description) {
    this.description = description;
  }

  public String getDegreeLevel() {
    return degreeLevel;
  }

  @XmlElement(name = "degreeLevel")
  public void setDegreeLevel(String degreeLevel) {
    this.degreeLevel = degreeLevel;
  }

  public String getCourseYear() {
    return courseYear;
  }

  @XmlElement(name = "courseYear")
  public void setCourseYear(String courseYear) {
    this.courseYear = courseYear;
  }

  public String getUcasCode() {
    return ucasCode;
  }

  @XmlElement(name = "ucasCode")
  public void setUcasCode(String ucasCode) {
    this.ucasCode = ucasCode;
  }

  public String getLength() {
    return length;
  }

  @XmlElement(name = "length")
  public void setLength(String length) {
    this.length = length;
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", name=" + name + ", description=" + description + ", degreeLevel="
        + degreeLevel + ", courseYear=" + courseYear + ", ucasCode=" + ucasCode + ", length="
        + length + "]";
  }

}
