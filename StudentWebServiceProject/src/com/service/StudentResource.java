package com.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Students> getStudents() {
		StudentDBOperation studentDBOperation = new StudentDBClass();
		List<Students> stuList = studentDBOperation.getAllStudents();
		return stuList;
	}

	@Override
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Students getStudent(@PathParam("id") int id) {
		Students stu = new StudentDBClass().getStudent(id);
		return stu;
	}

	@Override
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_JSON)
	public String totalCountStudent() {
		int count = new StudentDBClass().getAllStudents().size();
		return "{\"TotalStudent\":\"" + count + "\"}";
	}

	@Override
	@GET
	@Path("/size")
	@Produces(MediaType.APPLICATION_XML)
	public String totalCountStudentXML() {
		int count = new StudentDBClass().getAllStudents().size();
		return "<Student><TotalStudent>" + count + "</TotalStudent></Student>";
	}

	@Override
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public String insertStudents(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDBClass().insertStudent(stu)) {
			return "{\"msg\":\"student inserted\"}";
		}
		return "{\"msg\":\"student not inserted\"}";
	}
	
	@Override
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateStudent(@PathParam("id") int id, @FormParam("name") String name,
			@FormParam("addr") String addr) {
		Students stu = new Student(id, name, addr);
		if (new StudentDBClass().updateStudent(stu)) {
			return "{\"msg\":\"student updated\"}";
		}
		return "{\"msg\":\"student not updated\"}";
	}
	@Override
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteStudent(@PathParam("id") int id) {
		if (new StudentDBClass().deleteStudent(id)) {
			return "{\"msg\":\"student deleted\"}";
		}
		return "{\"msg\":\"student not deleted\"}";
	}

}
