package com.lti.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Payment;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.dao.AdminDao;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.CourseDao;
import com.lti.dao.CourseDaoImplementation;
import com.lti.dao.PaymentDao;
import com.lti.dao.PaymentDaoImplementation;
import com.lti.dao.ProfessorDao;
import com.lti.dao.ProfessorDaoImplementation;
import com.lti.dao.SemesterRegistrationDao;
import com.lti.dao.SemesterRegistrationDaoImplementation;
import com.lti.dao.StudentDao;
import com.lti.dao.StudentDaoImplementation;
import com.lti.dao.UserDao;
import com.lti.dao.UserDaoImplementation;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.CoursesNotApprovedException;
import com.lti.exception.DuplicateCourseEntryException;
import com.lti.exception.DuplicatePaymentEntryException;
import com.lti.exception.DuplicateProfessorEntryException;
import com.lti.exception.DuplicateUserEntryException;
import com.lti.exception.PaymentNotMadeException;
import com.lti.exception.ProfessorNotAddedException;
import com.lti.exception.ProfessorNotDeletedException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.exception.StudentNotFoundException;
import com.lti.exception.StudentNotGradedException;
import com.lti.service.AdminInterfaceOperation;
import com.lti.service.ProfessorInterfaceOperation;
import com.lti.utils.TableWithLines;

@RestController
public class AdminController {

	@Autowired
	AdminInterfaceOperation adminInterfaceOperation;

	@Autowired
	ProfessorInterfaceOperation professorInterfaceOperation;

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/addCourse")
	@ResponseBody
	public ResponseEntity addCourse(@RequestBody Course course) throws Exception {
		adminInterfaceOperation.addCourse(course);
		return new ResponseEntity(course, HttpStatus.OK);
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteCourse/{id}")
	@ResponseBody
	public ResponseEntity deleteCourse(@PathVariable int id) throws Exception {
		Course course = adminInterfaceOperation.deleteCourse(id);
		if (null == course) {
			return new ResponseEntity("No Course found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(course, HttpStatus.OK);
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/updateCourse")
	@ResponseBody
	public ResponseEntity updateCourse(@RequestBody Course course) throws Exception {
		Course updatedCourse = adminInterfaceOperation.updateCourse(course);
		if (null == updatedCourse) {
			return new ResponseEntity("No Course found for ID " + course.getId(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(updatedCourse, HttpStatus.OK);

	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getCourseById/{courseId}")
	@ResponseBody
	public ResponseEntity getCourseById(@PathVariable int courseId) throws Exception {
		Course course = adminInterfaceOperation.getCourseById(courseId);
		if (course == null) {
			return new ResponseEntity("No Course found for ID " + courseId, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(course, HttpStatus.OK);

	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/cancelCourse/{id}")
	@ResponseBody
	public ResponseEntity cancelCourse(@PathVariable int id) throws Exception {
		Course updatedCourse = adminInterfaceOperation.cancelCourse(id);
		if (null == updatedCourse) {
			return new ResponseEntity("No Course found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(updatedCourse, HttpStatus.OK);
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/addProfessor")
	@ResponseBody
	public ResponseEntity addProfessor(@RequestBody Professor professor) throws Exception {
		Professor addedProfessor = adminInterfaceOperation.addProfessor(professor);
		if (addedProfessor == null) {
			return new ResponseEntity("Cannot add the professor with id" + professor.getId(),
					HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity(addedProfessor, HttpStatus.OK);
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE, value = "/deleteProfessor/{id}")
	@ResponseBody
	public ResponseEntity deleteProfessor(@PathVariable int id) throws Exception {
		Professor deletedProfessor = adminInterfaceOperation.deleteProfessor(id);
		if (deletedProfessor == null) {
			return new ResponseEntity("Cannot delete professor with id:" + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(deletedProfessor, HttpStatus.OK);
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/assignCourseToProfessor/{professorId}/{courseId}")
	@ResponseBody
	public ResponseEntity assignCourseToProfessor(@PathVariable int professorId, @PathVariable int courseId)
			throws Exception {
		if (adminInterfaceOperation.assignCourseToProfessor(professorId, courseId) == false) {
			return new ResponseEntity(
					"Cannot assign course with id " + courseId + " to Professor with id: " + professorId,
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity("Course is assigned successfully to the professor", HttpStatus.OK);
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getProfessorList")
	@ResponseBody
	public List getProfessorList() throws Exception {
		return adminInterfaceOperation.viewProfessorList();
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getCourseList")
	@ResponseBody
	public List getCourseList() throws Exception {

		return adminInterfaceOperation.viewCourses();
	}

	// done
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getStudentList")
	@ResponseBody
	public List getStudentList() throws Exception {

		return adminInterfaceOperation.viewStudentList();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/approveStudentRegistration/{id}")
	@ResponseBody
	public ResponseEntity approveStudentRegistration(@PathVariable int id) throws Exception {
		Student student = adminInterfaceOperation.approveStudentRegistration(id);
		if (student == null) {
			return new ResponseEntity("Cannot approve student with id" + id, HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity(student, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/approveStudentCourseRegistration/{id}")
	@ResponseBody
	public ResponseEntity approveStudentCourseRegistration(@PathVariable int id) throws Exception {
		Student student = adminInterfaceOperation.approveStudentCourseRegistration(id);
		if (student == null) {
			return new ResponseEntity("Cannot approve student courses with id" + id, HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity(student, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/generateReportCard/{studentId}")
	@ResponseBody
	public ResponseEntity generateReportCard(@PathVariable int studentId) throws Exception {
		Student student = adminInterfaceOperation.generateReportCard(studentId);
		if (student == null) {
			return new ResponseEntity("Cannot generate report card for student with id" + studentId,
					HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity(student, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/generateBill/{id}")
	@ResponseBody
	public ResponseEntity generateBill(@PathVariable int id) throws Exception {
		int studentfee = adminInterfaceOperation.generateBill(id);
		if (studentfee == -1) {
			return new ResponseEntity("Cannot generate bill for student with id" + id, HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity(studentfee, HttpStatus.OK);
	}

}
