/**
 * 
 */
package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.Student;
import com.models.Students;

/**
 * @author lalitk This class Implement using the SQL sub programs
 */
public class StudentDBOperationProcedure implements StudentDBOperation {
	private final static String CON_STR = "jdbc:mysql://localhost:3306/demodb";
	private final static String USER = "root";
	private final static String PASSWORD = "root";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	// has a relationship with java.sql class
	private Connection con = null;
	CallableStatement cstmt = null;
	private ResultSet rs = null;

	/**
	 * This Method will create connection and assign to the Connection con
	 * instance
	 */
	private boolean createConnection() {
		try {
			Class.forName(StudentDBOperationProcedure.DRIVER);
			con = DriverManager.getConnection(StudentDBOperationProcedure.CON_STR, StudentDBOperationProcedure.USER,
					StudentDBOperationProcedure.PASSWORD);
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
			if (cstmt != null)
				cstmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * call the count_all_student(OUT id int) procedure Calculate the total
	 * number of students
	 * 
	 * @return total number of student
	 */
	public int countStudent() {
		int count = 0;
		createConnection();

		String SQL = "{call count_all_student(?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.execute();
			count = cstmt.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return count;
	}

	/**
	 * This RestFul Web Resource will collect the All Student Information It
	 * will return the All student information as JSON. Accept the HTTP GET
	 * Request
	 * 
	 * @return List of Students
	 */
	@Override
	public List<Students> getAllStudents() {
		createConnection();
		List<Students> stuList = new ArrayList<>();
		try {
			cstmt = con.prepareCall("{call get_all_student(1)}");
			boolean result = cstmt.execute();
			if (!result) {
				stuList.add(new Student(999, "Error", "Data not found"));
			}
			rs = cstmt.getResultSet();
			while (rs.next()) {
				stuList.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("addr")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stuList;
	}

	/**
	 * Procedure : select_student_by_id Collect the Student information of given
	 * id exist
	 * 
	 * @param id
	 * @return Student matched with id
	 */
	@Override
	public Student getStudent(int id) {
		createConnection();
		Student stu = new Student();
		String SQL = "{call select_student_by_id(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, id);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			stu.setId(cstmt.getInt("var_id"));
			stu.setName(cstmt.getString("var_name"));
			stu.setAddr(cstmt.getString("var_addr"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return stu;
	}

	/**
	 * Procedure : insert_student_record inserting the student record
	 * 
	 * @param Student
	 * @return boolean true success and false failure
	 */
	@Override
	public boolean insertStudent(Students student) {
		createConnection();
		String SQL = "{call insert_student_record(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, student.getId());
			cstmt.setString(2, student.getName());
			cstmt.setString(3, student.getAddr());
			if (cstmt.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	/**
	 * Procedure : update_student_record updating existing student record
	 * 
	 * @param id
	 * @return boolean true success and false failure
	 */
	@Override
	public boolean updateStudent(Students student) {
		createConnection();
		String SQL = "{call update_student_record(?,?,?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, student.getId());
			cstmt.setString(2, student.getName());
			cstmt.setString(3, student.getAddr());
			if (cstmt.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	/**
	 * Procedure : delete_student_record deleting existing student record
	 * 
	 * @param id
	 * @return boolean true success and false failure
	 */
	@Override
	public boolean deleteStudent(int id) {
		createConnection();
		String SQL = "{call delete_student_record(?)}";
		try {
			cstmt = con.prepareCall(SQL);
			cstmt.setInt(1, id);

			if (cstmt.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

}
