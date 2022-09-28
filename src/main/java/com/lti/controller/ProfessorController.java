package com.lti.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.bean.Course;
import com.lti.bean.Student;
import com.lti.dao.ProfessorDao;
import com.lti.service.ProfessorInterfaceOperation;
@RestController
public class ProfessorController {
	
	@Autowired
	ProfessorInterfaceOperation professorInterfaceOperation;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getCoursesAssigned/{id}")
	@ResponseBody
	public List getCoursesAssigned(@PathVariable int id) throws Exception {
		List<Course> courseList=professorInterfaceOperation.viewCoursesAssigned(id);
		return courseList;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getEnrolledStudents/{professorId}/{courseId}")
	@ResponseBody
	public List getEnrolledStudents(@PathVariable int professorId,@PathVariable int courseId) throws Exception {
		List<Student> studentList=professorInterfaceOperation.viewEnrolledStudent(professorId,courseId);
		return studentList;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/addGrade/{studentId}/{courseId}/{grade}")
	@ResponseBody
	public ResponseEntity addGrade(@PathVariable int studentId,@PathVariable int courseId, @PathVariable String grade) throws Exception{
		boolean status=professorInterfaceOperation.addGrades(studentId,courseId,grade);
		if(status==false)
		{
			return new ResponseEntity("Cannot add grades for student with id:"+studentId,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity(status,HttpStatus.OK);
	}
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/logout/{id}")
//	@ResponseBody
//	public String logout(@PathVariable int id) throws Exception {
//		return "logging out";
//	}
}
