package com.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.dao.StudentDBClass;
import com.dao.StudentDBOperation;
import com.models.Student;
import com.models.Students;

@Path("/students")
public class StudentResource implements StudentService {
	/**
	 * @see com.service.StudentService#getStudents() This RestFul Web Resource
	 *      will collect the All Student Information It will return the All
	 *      student information as JSON. Accept the HTTP GET Request
	 *      URL("http://..../StudentWebServiceProject/rest/students")
	 */
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> getStudents() {
		StudentDBOperation studentDBOperation = new StudentDBClass();
		List<Students> stuList = studentDBOperation.getAllStudents();
		return stuList;
	}

	/**
	 * @see com.service.StudentService#getStudent(int) URL(
	 *      "http://..../StudentWebServiceProject/rest/students/[1.........n,studentId]")
	 *      it will collect the particular id student and return as JSON with
	 *      HTTPSTATUS 200 if the student do not exist then it will return empty
	 *      JSON with HTTPSTATUS 200 Accept the HTTP GET Request
	 */
	@Override
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudent(@PathParam("id") int id) {
		Students stu = new StudentDBClass().getStudent(id);
		return stu;
	}

	/**
	 * @see com.service.StudentService#totalCountStudent()
	 *      "http://..../StudentWebServiceProject/rest/students/size") it will
	 *      collect and count the total students and return the total number of
	 *      students as JSON with HTTPSTATUS 200 Accept the HTTP GET Request
	 */
	@Override
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_JSON)
	public String totalCountStudent() {
		int count = new StudentDBClass().getAllStudents().size();
		return "{\"TotalStudent\":\"" + count + "\"}";
	}

	/**
	 * @see com.service.StudentService#totalCountStudentXML()
	 *      "http://..../StudentWebServiceProject/rest/students/size") it will
	 *      collect and count the total students and return the total number of
	 *      students as XML with HTTPSTATUS 200 Accept the HTTP GET Request
	 */
	@Override
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_XML)
	public String totalCountStudentXML() {
		int count = new StudentDBClass().getAllStudents().size();
		return "<Student><TotalStudent>" + count + "</TotalStudent></Student>";
	}

	/**
	 * @see com.service.StudentService#insertStudents(int, java.lang.String,
	 *      java.lang.String)
	 *      "http://..../StudentWebServiceProject/rest/students") It will
	 *      collect the student information from FORM and it will insert the new
	 *      Student record. It will return a return msg with student inserted
	 *      JSON in successful In other all condition return msg with student
	 *      not inserted Accept the HTTP POST Request
	 */
	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String insertStudents(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDBClass().insertStudent(stu)) {
			return "{\"msg\":\"student inserted\"}";
		}
		return "{\"msg\":\"student not inserted\"}";
	}

	/**
	 * @see com.service.StudentService#updateStudent(int, java.lang.String,
	 *      java.lang.String)
	 *      "http://..../StudentWebServiceProject/rest/students/[1.....n,Student id]"
	 *      ) It will collect the student information from FORM It will update
	 *      the student record It will return a return msg with student updated
	 *      JSON in successful In other all condition return msg with student
	 *      not updated Accept the HTTP POST Request
	 */
	@Override
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String updateStudent(@PathParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDBClass().updateStudent(stu)) {
			return "{\"msg\":\"student updated\"}";
		}
		return "{\"msg\":\"student not updated\"}";
	}

	/**
	 * @see com.service.StudentService#deleteStudent(int)
	 *      "http://..../StudentWebServiceProject/rest/students/[1.....n,Student id]"
	 *      ) It will collect the student information from FORM and it will
	 *      delete the existing Student record with match id. It will return a
	 *      return msg with student deleted JSON in successful In other all
	 *      condition return msg with student not deleted Accept the HTTP POST
	 *      Request
	 */
	@Override
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String deleteStudent(@PathParam("id") int id) {
		if (new StudentDBClass().deleteStudent(id)) {
			return "{\"msg\":\"student deleted\"}";
		}
		return "{\"msg\":\"student not deleted\"}";
	}

}
