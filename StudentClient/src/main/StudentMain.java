/**
 * Lalitvchau
*/
package main;

import java.io.IOException;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Student;

public class StudentMain {
	private final static String URL = "http://localhost:8080/StudentWebServiceProject/rest/students";
	private final static ClientConfig CONFIG = new ClientConfig();
	private final static Client CLIENT = ClientBuilder.newClient(CONFIG);
	private final static WebTarget TARGET = CLIENT.target(URL);

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scIn = new Scanner(System.in);
		while (true) {
			System.out.println("Please Enter the choice");
			System.out.println("1. Insert the Student");
			System.out.println("2. Update the Student");
			System.out.println("3. Delete the Student");
			System.out.println("4. Display Student ");
			System.out.println("5. Display all Students");
			System.out.println("6. Exit");
			switch (scIn.nextByte()) {
			case 1:
				Student tempStudent = new Student();
				System.out.println("Insereting a Student");
				System.out.println("Please insert the id :");
				tempStudent.setId(scIn.nextInt());
				System.out.println("Please insert the name :");
				tempStudent.setName(scIn.next());
				System.out.println("Please insert the address :");
				tempStudent.setAddr(scIn.next());
				StudentMain.insertStudent(tempStudent);
				break;
			case 2:
				tempStudent = new Student();
				System.out.println("Updating a Student");
				System.out.println("Please insert the id :");
				int id = scIn.nextInt();
				tempStudent.setId(id);
				System.out.println("Please insert the name :");
				tempStudent.setName(scIn.next());
				System.out.println("Please insert the address :");
				tempStudent.setAddr(scIn.next());
				StudentMain.updateStudent(String.valueOf(id), tempStudent);
				break;
			case 3:
				System.out.println("deleting a Student");
				System.out.println("Please insert the id :");
				id = scIn.nextInt();
				StudentMain.deleteStudent(String.valueOf(id));
				break;
			case 4:
				System.out.println("Student Details");
				System.out.println("Please insert the id :");
				id = scIn.nextInt();
				StudentMain.displayStudent(String.valueOf(id));
				break;
			case 5:
				System.out.println("Student Details");
				StudentMain.displayAllStudent();
				break;
			case 6:
				System.out.println("System exit !");
				return;
			default:
				System.out.println("Please Select Correct choice");
			}
		}

	}

	private static void insertStudent(Student student) {
		Form mapStudent = new Form();
		mapStudent.param("id",String.valueOf(student.getId()));
		mapStudent.param("name", student.getName());
		mapStudent.param("addr", student.getAddr());
		
		Response result = TARGET.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.form(mapStudent));
		if(result.getStatus()==200) System.out.println("Student Inserted successfully  !");
		else System.out.println(result.getStatus()+" \n"+result);
	}
	

	private static void updateStudent(String id, Student student) {
		Form studentForm = new Form();
		studentForm.param("name", student.getName());
		studentForm.param("addr", student.getAddr());
		Response result = TARGET.path(id).request().accept(MediaType.APPLICATION_JSON).post(Entity.form(studentForm));
		if(result.getStatus()==200) System.out.println("Student Updated successfully  !");
		else System.out.println(result.getStatus()+" \n"+result);
	}

	private static void deleteStudent(String id) {
		Response result = TARGET.path(id).request().accept(MediaType.APPLICATION_JSON).delete();
		if(result.getStatus()==200) System.out.println("Student Deleted successfully  !");
		else System.out.println(result.getStatus()+" \n"+result);
	}

	private static void displayStudent(String id) {
		ObjectMapper objectMapper = new ObjectMapper();
		Student student;
		try {
			student = objectMapper.readValue(
					TARGET.path(id).request().accept(MediaType.APPLICATION_JSON)
					.get(String.class), Student.class);
			
			System.out.println("-------------------");
			System.out.println("Student :");
			System.out.println("Id : " + student.getId());
			System.out.println("Name : " + student.getName());
			System.out.println("Address : " + student.getAddr());
			System.out.println("-------------------");
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void displayAllStudent() {
		ObjectMapper objectMapper = new ObjectMapper();
		Student[] students;
		try {
			students = objectMapper.readValue(TARGET.request().accept(MediaType.APPLICATION_JSON).get(String.class),
					Student[].class);
			System.out.println("Students :");
			for (Student student : students) {
				System.out.println("-------------------");
				System.out.println("Id : " + student.getId());
				System.out.println("Name : " + student.getName());
				System.out.println("Address : " + student.getAddr());
				System.out.println("-------------------");
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
