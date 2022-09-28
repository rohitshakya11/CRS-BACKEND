package com.lti.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.bean.Admin;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.bean.User;
import com.lti.exception.AdminNotFoundException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.exception.StudentNotFoundException;
import com.lti.service.RegistrationInterfaceOperation;
import com.lti.service.UserInterfaceOperation;
@RestController
public class UserController{
	@Autowired
	UserInterfaceOperation userInterfaceOperation;
	
	@Autowired
	RegistrationInterfaceOperation registrationInterfaceOperation;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/login/{emailId}/{password}/{role}")
	@ResponseBody
	public ResponseEntity login(@PathVariable String emailId,@PathVariable String password,@PathVariable String role) throws Exception{
		User user=userInterfaceOperation.userLogin(emailId, password, role);
		return new ResponseEntity(user,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getStudentByEmail/{emailId}")
	@ResponseBody
	public ResponseEntity getStudentByEmail(@PathVariable String emailId) throws Exception{
		Student student=userInterfaceOperation.getStudentByEmail(emailId);
		return new ResponseEntity(student,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getProfessorByEmail/{emailId}")
	@ResponseBody
	public ResponseEntity getProfessorByEmail(@PathVariable String emailId) throws Exception{
		Professor professor=userInterfaceOperation.getProfessorByEmail(emailId);
		return new ResponseEntity(professor,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getAdminByEmail/{emailId}")
	@ResponseBody
	public ResponseEntity getAdminByEmail(@PathVariable String emailId) throws Exception{
		Admin admin=userInterfaceOperation.getAdminByEmail(emailId);
		return new ResponseEntity(admin,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/logout")
	@ResponseBody
	public ResponseEntity logout() {
		return new ResponseEntity("logging out",HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/resetPassword/{emailId}/{oldPassword}/{newPassword}")
	@ResponseBody
	public ResponseEntity updatePassword(@PathVariable String emailId,@PathVariable String oldPassword,@PathVariable String newPassword ) throws Exception{
		User user=userInterfaceOperation.resetPassword(emailId,oldPassword,newPassword);	
		return new ResponseEntity(user,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateInfo/{emailId}/{password}/{mobileNumber}")
	@ResponseBody
	public ResponseEntity updateInfo(@PathVariable String emailId, @PathVariable String password, @PathVariable long mobileNumber) throws Exception{
		User user=userInterfaceOperation.updateInfo(emailId,password,mobileNumber);
		return new ResponseEntity(user,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/registerStudent")
	@ResponseBody
	public ResponseEntity studentRegistration(@RequestBody Student student) throws Exception {
		boolean status=registrationInterfaceOperation.registerStudent(student);
		return new ResponseEntity(status,HttpStatus.OK);
	}
	
}
