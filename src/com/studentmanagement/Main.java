package com.studentmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/student_management";
    static String username = "root";
    static String password = "Muni@80088##";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n=================================");
            System.out.println("     STUDENT MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Student Registration");
            System.out.println("2. Course Registration");
            System.out.println("3. Course Enrollment");
            System.out.println("4. Attendance Management");
            System.out.println("5. Marks Entry");
            System.out.println("6. View Student Result");
            System.out.println("7. View All Students");
            System.out.println("8. Update Student");
            System.out.println("9. Delete Student");
            System.out.println("10. Transaction Demo");
            System.out.println("11. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

            case 1:
                registerStudent();
                break;

            case 2:
                addCourse();
                break;

            case 3:
                enrollStudent();
                break;

            case 4:
                addAttendance();
                break;

            case 5:
                addMarks();
                break;

            case 6:
                generateResult();
                break;

            case 7:
                viewStudents();
                break;

            case 8:
                updateStudent();
                break;

            case 9:
                deleteStudent();
                break;

            case 10:
                transactionDemo();
                break;

            case 11:
                System.out.println("Thank you!");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    // 1. Student Registration - CREATE
    static void registerStudent() {

        String sql = "INSERT INTO students(student_name, email, phone) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            sc.nextLine();

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter email: ");
            String email = sc.nextLine();

            System.out.print("Enter phone: ");
            String phone = sc.nextLine();

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();

            System.out.println("Student registered successfully!");

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 2. Course Registration - CREATE
    static void addCourse() {

        String sql = "INSERT INTO courses(course_name, duration) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            sc.nextLine();

            System.out.print("Enter course name: ");
            String courseName = sc.nextLine();

            System.out.print("Enter duration: ");
            String duration = sc.nextLine();

            ps.setString(1, courseName);
            ps.setString(2, duration);

            ps.executeUpdate();

            System.out.println("Course added successfully!");

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Course Enrollment
    static void enrollStudent() {

        String sql = "INSERT INTO enrollments(student_id, course_id, enrollment_date) "
                   + "VALUES (?, ?, CURDATE())";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int studentId = sc.nextInt();

            System.out.print("Enter course ID: ");
            int courseId = sc.nextInt();

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ps.executeUpdate();

            System.out.println("Student enrolled successfully!");

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 4. Attendance Management
    static void addAttendance() {

        String sql = "INSERT INTO attendance(student_id, course_id, attendance_date, status) "
                   + "VALUES (?, ?, CURDATE(), ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int studentId = sc.nextInt();

            System.out.print("Enter course ID: ");
            int courseId = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter status (Present/Absent): ");
            String status = sc.nextLine();

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setString(3, status);

            ps.executeUpdate();

            System.out.println("Attendance added successfully!");

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 5. Marks Entry
    static void addMarks() {

        String sql = "INSERT INTO marks(student_id, course_id, marks) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int studentId = sc.nextInt();

            System.out.print("Enter course ID: ");
            int courseId = sc.nextInt();

            System.out.print("Enter marks: ");
            int marks = sc.nextInt();

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setInt(3, marks);

            ps.executeUpdate();

            System.out.println("Marks added successfully!");

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 6. Result Generation - JOIN
    static void generateResult() {

        String sql = "SELECT s.student_name, c.course_name, m.marks "
                   + "FROM students s "
                   + "JOIN marks m ON s.student_id = m.student_id "
                   + "JOIN courses c ON m.course_id = c.course_id "
                   + "WHERE s.student_id = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int studentId = sc.nextInt();

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== STUDENT RESULT ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Student Name : "
                        + rs.getString("student_name"));

                System.out.println("Course       : "
                        + rs.getString("course_name"));

                System.out.println("Marks        : "
                        + rs.getInt("marks"));

                System.out.println("------------------------------------");
            }

            if (!found) {
                System.out.println("No result found!");
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 7. View All Students - READ
    static void viewStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== STUDENT LIST ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "ID: " + rs.getInt("student_id")
                        + " | Name: " + rs.getString("student_name")
                        + " | Email: " + rs.getString("email")
                        + " | Phone: " + rs.getString("phone")
                );
            }

            if (!found) {
                System.out.println("No students found!");
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 8. Update Student - UPDATE
    static void updateStudent() {

        String sql = "UPDATE students SET student_name = ?, email = ?, phone = ? "
                   + "WHERE student_id = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter new name: ");
            String name = sc.nextLine();

            System.out.print("Enter new email: ");
            String email = sc.nextLine();

            System.out.print("Enter new phone: ");
            String phone = sc.nextLine();

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 9. Delete Student - DELETE
    static void deleteStudent() {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter student ID: ");
            int id = sc.nextInt();

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // 10. Transaction Demo
    static void transactionDemo() {

        String insertStudent =
                "INSERT INTO students(student_name, email, phone) VALUES (?, ?, ?)";

        String insertCourse =
                "INSERT INTO courses(course_name, duration) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            con.setAutoCommit(false);

            try {

                PreparedStatement studentPs =
                        con.prepareStatement(insertStudent);

                studentPs.setString(1, "Transaction Student");
                studentPs.setString(2, "transaction@gmail.com");
                studentPs.setString(3, "9999999999");

                studentPs.executeUpdate();

                PreparedStatement coursePs =
                        con.prepareStatement(insertCourse);

                coursePs.setString(1, "Transaction Java");
                coursePs.setString(2, "2 Months");

                coursePs.executeUpdate();

                con.commit();

                System.out.println("Transaction completed successfully!");

            } catch (SQLException e) {

                con.rollback();

                System.out.println("Transaction failed.");
                System.out.println("Changes rolled back.");

            } finally {

                con.setAutoCommit(true);
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}