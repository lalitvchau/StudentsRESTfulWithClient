package com.service;

import java.util.List;
import com.models.Students;


public interface StudentService {
	
	public List<Students> getStudents();
	public Students getStudent(int id);
	public String totalCountStudent();
	public String totalCountStudentXML();
	public String insertStudents(int id, String name, String addr);
	public String updateStudent(int id, String name, String addr);
	public String deleteStudent(int id);
}
