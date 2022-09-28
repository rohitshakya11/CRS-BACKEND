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
import com.lti.bean.Grade;
import com.lti.bean.Payment;
import com.lti.service.StudentInterfaceOperation;
@RestController
public class StudentController {
	
	@Autowired
	StudentInterfaceOperation studentInterfaceOperation;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getAllCourses/{semesterId}")
	@ResponseBody
	public List getAllCourses(@PathVariable int semesterId){
		List<Course> courseList=studentInterfaceOperation.viewCourses(semesterId);
		return courseList;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/registerCourse/{studentId}/{semesterId}/{courseId}/{isCoursePrimary}")
	@ResponseBody
	public ResponseEntity addCourse(@PathVariable int studentId,@PathVariable int semesterId,@PathVariable int courseId,@PathVariable int isCoursePrimary)throws Exception {
		boolean status=studentInterfaceOperation.addCourse(studentId,semesterId,courseId,isCoursePrimary);
		return new ResponseEntity(status,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/dropCourse/{studentId}/{courseId}")
	@ResponseBody
	public ResponseEntity dropCourse(@PathVariable int studentId,@PathVariable int courseId) throws Exception{
		boolean status=studentInterfaceOperation.dropCourse(studentId,courseId);
		return new ResponseEntity(status,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRegisteredCourses/{studentId}")
	@ResponseBody
	public List getRegisteredCourses(@PathVariable int studentId) throws Exception{
		List<Grade> gradeList=studentInterfaceOperation.viewRegisteredCourses(studentId);
		return gradeList;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getRegisteredCoursesInSemester/{studentId}/{semesterId}")
	@ResponseBody
	public List getRegisteredCoursesInSemester(@PathVariable int studentId, @PathVariable int semesterId) throws Exception{
		List<Grade> gradeList=studentInterfaceOperation.viewRegisteredCoursesInSemester(studentId, semesterId);
		return gradeList;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getGrades/{studentId}")
	@ResponseBody
	public List getGrades(@PathVariable int studentId) throws Exception{
		List<Grade> gradeList=studentInterfaceOperation.viewGrades(studentId);
		return gradeList;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getPayments/{studentId}")
	@ResponseBody
	public List getPayments(@PathVariable int studentId) throws Exception{
		List<Payment> paymentList=studentInterfaceOperation.getPayments(studentId);
		return paymentList;
	}

	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/payFee/{studentId}/{paymentMethod}")
	@ResponseBody
	public ResponseEntity payFee(@PathVariable int studentId,@PathVariable String paymentMethod) throws Exception{
		Payment payment=studentInterfaceOperation.payFee(studentId,paymentMethod);
		if(payment==null)
		{
			return new ResponseEntity("Payment failed for student with id -"+studentId,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity(payment,HttpStatus.OK);
	}
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/logout")
//	@ResponseBody
//	public String logout()throws Exception {
//		return "student logging out";
//	}
}
