package com.lti.service;

import java.util.List;

import com.lti.bean.Admin;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.bean.User;
import com.lti.exception.AdminNotFoundException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.exception.StudentNotFoundException;
import com.lti.exception.UserNotFoundException;

/**
 * interface for user operations
 */
public interface UserInterfaceOperation {
	
	public User userLogin(String emailId, String password, String role) throws AdminNotFoundException,StudentNotFoundException,ProfessorNotFoundException;
	
	public Student getStudentByEmail(String emailId) throws AdminNotFoundException,StudentNotFoundException,ProfessorNotFoundException;
	
	public Professor getProfessorByEmail(String emailId) throws AdminNotFoundException,StudentNotFoundException,ProfessorNotFoundException;
	
	public Admin getAdminByEmail(String emailId) throws AdminNotFoundException,StudentNotFoundException,ProfessorNotFoundException;
	/**
	 * method for user logout
	 */
	public void logout();

	/**
	 * method for user reset password
	 */
	public User resetPassword(String emailId,String oldPassword,String newPassword) throws UserNotFoundException;
		

	/**
	 * method for updating user information
	 */
	public User updateInfo(String emailId,String password, long mobileNumber) throws UserNotFoundException;
			/**
	 * method for student login
	 * @param emailId
	 * @param password
	 * @param role
	 */
//	public Student studentLogin(String email, String password, String role);
//
//	/**
//	 * method for professor login
//	 * @param emailId
//	 * @param password
//	 * @param role
//	 */
//	public Professor professorLogin(String email, String password, String role);
//
//	/**
//	 * method for admin login
//	 * @param emailId
//	 * @param password
//	 * @param role
//	 */
//	public Admin adminLogin(String email, String password, String role);
}