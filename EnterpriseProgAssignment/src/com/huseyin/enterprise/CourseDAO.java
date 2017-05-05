package com.huseyin.enterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.appengine.api.utils.SystemProperty;

public class CourseDAO {

  Course course = null;
  Connection conn = null;
  Statement stmt = null;
  String url = null;
  PreparedStatement ptmt = null;

  public CourseDAO() {}


  private void openConnection() {
    // loading jdbc driver for mysql
    try {
      if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
        Class.forName("com.mysql.jdbc.GoogleDriver").newInstance();
      } else {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    // connecting to database
    try {
      if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
        // DriverManager.registerDriver(new AppEngineDriver());
        url =
            "jdbc:google:mysql://enterprise-programming-1:europe-west1:ent-prog-db/courses?user=root&password=hus280294";
      } else {
        // Local MySQL instance to use during development.
        url = "jdbc:mysql://104.199.62.121:3306/courses?user=root&password=hus280294";
        // url = "jdbc:mysql://localhost:3306/courses?user=root&password=password";
      }
      // connection string for demos database, username demos, password demos
      conn = DriverManager.getConnection(url);
      stmt = conn.createStatement();
    } catch (SQLException se) {
      System.out.println(se);
    }
  }

  private void closeConnection() {
    try {
      conn.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void addCourse(Course course) {
    openConnection();
    try {
      String queryString =
          "INSERT INTO course(name, description, degreelevel, year, ucascode, length) VALUES(?,?,?,?,?,?)";
      ptmt = conn.prepareStatement(queryString);
      ptmt.setString(1, course.getName());
      ptmt.setString(2, course.getDescription());
      ptmt.setString(3, course.getDegreeLevel());
      ptmt.setString(4, course.getCourseYear());
      ptmt.setString(5, course.getUcasCode());
      ptmt.setString(6, course.getLength());
      ptmt.executeUpdate();
      System.out.println("Data Added Successfully");
      ptmt.close();
      closeConnection();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public String updateCourse(int id, String attribute, String value) {
    openConnection();
    try {
      String queryString = "UPDATE course SET " + attribute + "='" + value + "' WHERE id =" + id;
      ptmt = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
      int affectedRows = ptmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Data Updated Successfully");
      } else {
        return "No rows updated";
      }
      ptmt.close();
      closeConnection();
    } catch (SQLException e) {
      System.out.println(e);
      return e.getMessage();
    }
    return null;
  }

  public String deleteCourse(int id) {
    openConnection();
    try {
      String queryString = "DELETE FROM course WHERE id=?";
      ptmt = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
      ptmt.setInt(1, id);
      int affectedRows = ptmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Data Deleted Successfully");
      } else {
        return "No rows deleted";
      }
      ptmt.close();
      closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Course getNextCourse(ResultSet rs) {
    Course thisCourse = null;
    try {
      thisCourse = new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
          rs.getString("degreelevel"), rs.getString("year"), rs.getString("ucascode"),
          rs.getString("length"));
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return thisCourse;
  }



  public ArrayList<Course> getAllCourses() {

    ArrayList<Course> allCourses = new ArrayList<Course>();
    openConnection();

    // Create select statement and execute it
    try {
      String selectSQL = "select * from course";
      ResultSet rs1 = stmt.executeQuery(selectSQL);
      // Retrieve the results
      while (rs1.next()) {
        course = getNextCourse(rs1);
        allCourses.add(course);
      }

      stmt.close();
      closeConnection();
    } catch (SQLException se) {
      System.out.println(se);
    }

    return allCourses;
  }

  public ArrayList<Course> getAllCourseByName(String name) {

    ArrayList<Course> allCourseByName = new ArrayList<Course>();
    openConnection();

    // Create select statement and execute it
    try {
      String selectSQL = "select * from course where  UPPER(name) LIKE UPPER('%" + name + "%')";
      ResultSet rs1 = stmt.executeQuery(selectSQL);
      // Retrieve the results
      while (rs1.next()) {
        course = getNextCourse(rs1);
        allCourseByName.add(course);
      }

      stmt.close();
      closeConnection();
    } catch (SQLException se) {
      System.out.println(se);
    }

    return allCourseByName;
  }

}
