package com.lti.service;

import com.lti.bean.Student;
import com.lti.exception.DuplicateUserEntryException;

/**interface for registration operations*/
public interface RegistrationInterfaceOperation {

	/**
	 * method for registering a student
	 */
	public boolean registerStudent(Student student) throws DuplicateUserEntryException;		
}