package com.dao;

import java.util.List;

import com.models.Students;

public interface StudentDBOperation {
	public List<Students> getAllStudents();
	public Students getStudent(int id);
	public boolean insertStudent(Students student);
	public boolean updateStudent(Students student);
	public boolean deleteStudent(int id);
}
