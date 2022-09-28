package com.lti.service;

import java.util.List;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Professor;
import com.lti.bean.Student;
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
import com.lti.exception.UserNotDeletedException;
import com.lti.exception.UserNotFoundException;

public interface AdminInterfaceOperation {

	// Function to view all the courses present in the course catalogue
	List<Course> viewCourses();

	// Function to view all the courses present in the course catalogue for a particular semester
	// @Param semesterId
	void viewCourses(int semesterId);

	// Function to add a new course in the catalogue
	// @Param courseId, courseName, courseFee, courseDepartment, courseCredit, semesterId
	// handle DuplicateCourseEntry
	void addCourse(Course course) throws DuplicateCourseEntryException;

	// Function to remove a course from the catalogue
	// @Param courseId
	// handle CourseNotFound
	Course deleteCourse(int id) throws CourseNotFoundException;

	// Function to modify the course information/content
	// @Param courseId, courseName, courseFee, courseDepartment, courseCredit, status, professorId semesterId
	// handle CourseNotFound
	Course updateCourse(Course course) throws CourseNotFoundException;

	// Function to assign a professor to a course
	// @Param courseId, professorId
	// handle CourseNotFound, ProfessorNotFound
	boolean assignCourseToProfessor(int professorId, int courseId)
			throws CourseNotFoundException, ProfessorNotFoundException;

	// Function to view all the professors
	List<Professor> viewProfessorList();

	// Function to add a professor in the system
	// @Param professorId, professorName, professorEmail, professorPassword
	// handle DuplicateProfessorEntry
	Professor addProfessor(Professor professor)
			throws DuplicateUserEntryException, DuplicateProfessorEntryException, ProfessorNotAddedException;

	// Function to remove a professor from the system
	// @Param ProfessorId
	// handle ProfessorNotFound
	Professor deleteProfessor(int professorId) throws UserNotFoundException, UserNotDeletedException,
			ProfessorNotFoundException, ProfessorNotDeletedException;

	public List<Student> viewStudentList();

	void viewPendingCourseApprovalStudentList();

	// Function to approve a student registration in the system
	// @param studentId
	// handle StudentNotFound, DuplicateUserEntry
	Student approveStudentRegistration(int studentId) throws DuplicateUserEntryException, StudentNotFoundException;

	// Function to approve student registered courses by admin
	// @param studentId
	// handle StudentNotFound, NoCourseFoundForStudent
	Student approveStudentCourseRegistration(int studentId) throws StudentNotFoundException;

	// Function to cancel a course for the semester if student strength < 3
	// @param courseId
	// handle CourseNotFound, NoStudentFoundIntoCourse
	Course cancelCourse(int courseId) throws CourseNotFoundException;

	Student findStudent(int id, List<Student> studentList);

	// Function to show report card of student
	// @param studentId
	// handle StudentNotFound
	void showReportCard(Student st);

	// Function to generate the report card for students for the semester
	// @param studentId
	// handle StudentNotFound
	Student generateReportCard(int studentId) throws StudentNotFoundException, CoursesNotApprovedException,
			StudentNotGradedException, PaymentNotMadeException;

	// Function to generate bill of student
	// @param studentId
	// handle StudentNotFound, NoCourseFoundForStudent, DuplicatePaymentEntry
	int generateBill(int studentId)
			throws StudentNotFoundException, DuplicatePaymentEntryException, CoursesNotApprovedException;

	void printList(List<Grade> gradeList);
	
	public Course getCourseById(int courseId);

}