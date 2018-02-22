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
import com.dao.StudentDBOperationProcedure;
import com.models.Student;
import com.models.Students;

@Path("/proc")
public class StudentsService {
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String countStudent() {
		StudentDBOperationProcedure st = new StudentDBOperationProcedure();
		int count = st.countStudent();
		return "{\"Total Students\" : \"" + count + "\"}";
	}

	/**
	 * @see com.service.StudentService#getStudent(int) URL(
	 *      "http://..../StudentWebServiceProject/rest/students/[1.........n,studentId]")
	 *      it will collect the particular id student and return as JSON with
	 *      HTTPSTATUS 200 if the student do not exist then it will return empty
	 *      JSON with HTTPSTATUS 200 Accept the HTTP GET Request
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("id") int id) {
		Student stu = new StudentDBOperationProcedure().getStudent(id);
		return stu;
	}

	/**
	 * collect the student information from FORM and it will insert the new
	 * Student record. It will return a return msg with student inserted JSON in
	 * successful In other all condition return msg with student not inserted
	 * Accept the HTTP POST Request
	 * 
	 * @param id
	 * @param name
	 * @param addr
	 * @return Msg as JSON
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String insertStudents(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDBOperationProcedure().insertStudent(stu)) {
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
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String updateStudent(@PathParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDBOperationProcedure().updateStudent(stu)) {
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
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String deleteStudent(@PathParam("id") int id) {
		if (new StudentDBOperationProcedure().deleteStudent(id)) {
			return "{\"msg\":\"student deleted\"}";
		}
		return "{\"msg\":\"student not deleted\"}";
	}
	/**
	 * @see com.service.StudentService#getStudents() This RestFul Web Resource
	 *      will collect the All Student Information It will return the All
	 *      student information as JSON. Accept the HTTP GET Request
	 *      URL("http://..../StudentWebServiceProject/rest/students")
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> getStudents() {	
		return new StudentDBOperationProcedure().getAllStudents();
	}
}
