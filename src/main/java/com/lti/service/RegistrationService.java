package com.lti.service;

import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.lti.bean.Student;
import com.lti.dao.UserDao;
import com.lti.dao.UserDaoImplementation;
import com.lti.exception.DuplicateStudentEntryException;
import com.lti.exception.DuplicateUserEntryException;

@Repository
public class RegistrationService implements RegistrationInterfaceOperation {

	
	@Override
	public boolean registerStudent(Student student) throws DuplicateUserEntryException {
		
//		System.out.println("Welcome to registration");
//		
//		Student student = new Student();
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("Enter name:");
//		String name = sc.next();
//		student.setName(name);
//		
//		System.out.println("Enter email id:");
//		String emailId = sc.next();
//		student.setEmailId(emailId);
//		
//		System.out.println("Enter password:");
//		String password = sc.next();
//		student.setPassword(password);
		
	 
	    	UserDao userDao = new UserDaoImplementation();
	    
		    userDao.userRegistration(student.getName(), student.getEmailId(), student.getPassword(),student.getRole());
		    return true;
	   
	    //System.out.println("\nStudent registered successfully!!");
		//System.out.println("->Pending admin approval for login<-\n");
		
	}

}
