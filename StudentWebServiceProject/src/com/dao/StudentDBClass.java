package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.models.Student;
import com.models.Students;

public class StudentDBClass implements StudentDBOperation {
	/**
	 * Database Configuration Information
	 */
	private final static String CON_STR = "jdbc:mysql://localhost:3306/demodb";
	private final static String USER = "root";
	private final static String PASSWORD = "root";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	/**
	 * These all are the SQL Queries Statements
	 */
	private final static String SQL_QRY_SELECT_ALL = "select * from student order by id";
	private final static String SQL_QRY_SELECT_BY_ID = "select * from student where id=?";
	private final static String SQL_QRY_INSERT = "INSERT INTO STUDENT(id,name,addr) values(?,?,?)";
	private static final String SQL_QRY_UPDATE = "UPDATE STUDENT SET addr = ?, name =? WHERE id=?";
	private static final String SQL_QRY_DELETE = "DELETE FROM STUDENT WHERE id =?";
	// has a relationship with java.sql class
	private Connection con = null;
	private PreparedStatement pSt = null;
	private ResultSet rs = null;
	private Statement st = null;

	/**
	 * This Method will create connection and assign to the Connection con
	 * instance
	 */
	private boolean createConnection() {
		try {
			Class.forName(StudentDBClass.DRIVER);
			con = DriverManager.getConnection(StudentDBClass.CON_STR, StudentDBClass.USER, StudentDBClass.PASSWORD);
			if (con == null)
				return false;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// closing the database connection with statement and result-set
	private void closeConnection() {
		try {
			if (con != null)
				con.close();
			if (pSt != null)
				pSt.close();
			if (st != null)
				st.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.dao.StudentDBOperation#getAllStudents() This method will collect
	 *      the all students information and assign to a List of Student Pojo
	 *      Class Instances Database : demoDb Table : Student Return The List
	 */
	@Override
	public List<Students> getAllStudents() {
		// gathering the all student from student table and return as list of
		// students
		List<Students> stuList = new ArrayList<>();
		this.createConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(StudentDBClass.SQL_QRY_SELECT_ALL);
			while (rs.next()) {
				Students stu = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("addr"));
				stuList.add(stu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return stuList;
	}

	/**
	 * @see com.dao.StudentDBOperation#getStudent(java.lang.int id) Accept
	 *      integer id as parameter that match with the Student id This method
	 *      will collect the particular given id information and assign to a
	 *      List of Student Pojo Class Instances Database : demoDb Table :
	 *      Student Return The Student Instance
	 */
	@Override
	public Students getStudent(int id) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDBClass.SQL_QRY_SELECT_BY_ID);
			pSt.setInt(1, id);
			rs = pSt.executeQuery();
			while (rs.next()) {
				return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("addr"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return null;
	}

	/**
	 * @see com.dao.StudentDBOperation#insertStudent(com.models.Students) The
	 *      Method will accept the Student information through the Student
	 *      instance Then It will store new Student Information to Student table
	 *      and return true if it inserted successfully Otherwise it will return
	 *      false
	 */
	@Override
	public boolean insertStudent(Students student) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDBClass.SQL_QRY_INSERT);
			pSt.setInt(1, student.getId());
			pSt.setString(2, student.getName());
			pSt.setString(3, student.getAddr());
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}

	/**
	 * @see com.dao.StudentDBOperation#updateStudent(com.models.Students) The
	 *      method accept a Student information This Method will update the
	 *      existing student with new student information with matching the
	 *      student id. If it try to update successfully then it will return
	 *      true It will return false in other all situations
	 */
	@Override
	public boolean updateStudent(Students student) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDBClass.SQL_QRY_UPDATE);
			pSt.setInt(3, student.getId());
			pSt.setString(2, student.getName());
			pSt.setString(1, student.getAddr());
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}

	/**
	 * @see com.dao.StudentDBOperation#deleteStudent(int) This Method will
	 *      accept a integer id information It will find the particular matching
	 *      student id If it will find then the student record will deleted and
	 *      return True All other condition it will return false
	 */
	@Override
	public boolean deleteStudent(int id) {
		this.createConnection();
		try {
			pSt = con.prepareStatement(StudentDBClass.SQL_QRY_DELETE);
			pSt.setInt(1, id);
			if (pSt.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return false;
	}
}
