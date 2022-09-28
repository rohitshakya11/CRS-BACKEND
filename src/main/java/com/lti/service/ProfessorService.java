package com.lti.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.constant.SQLConstant;
import com.lti.dao.CourseDao;
import com.lti.dao.CourseDaoImplementation;
import com.lti.dao.SemesterRegistrationDao;
import com.lti.dao.SemesterRegistrationDaoImplementation;
import com.lti.dao.ProfessorDao;
import com.lti.dao.ProfessorDaoImplementation;
import com.lti.dao.StudentDao;
import com.lti.dao.StudentDaoImplementation;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.GradeAlreadyAssignedException;
import com.lti.exception.ProfessorNotFoundException;
import com.lti.exception.StudentCourseNotApprovedException;
import com.lti.exception.StudentNotFoundException;
import com.lti.utils.DBUtils;
import com.lti.utils.TableWithLines;
import com.lti.bean.Course;

@Repository
public class ProfessorService  implements ProfessorInterfaceOperation {
	
	@Autowired
	ProfessorDao professorDao;
	
	@Autowired
	SemesterRegistrationDao semesterRegistrationDao;
	
	@Autowired
	CourseDao courseDao;
	
	@Override
	public List<Course> viewCoursesAssigned(int professorId) throws ProfessorNotFoundException {
		List<Course> coursesAssigned=new ArrayList<>();
	//	try {
			ProfessorDao professorDao = new ProfessorDaoImplementation();
			coursesAssigned = professorDao.getCoursesAssigned(professorId);
			
//			String[] columns = new String[]{"COURSE ID", "COURSE NAME"};
//	        String[][] table = new String[coursesAssigned.size()+1][columns.length];
//	        table[0] = columns;
//
//	        for(int i=0; i<coursesAssigned.size(); i++){
//	            table[i+1][0] = coursesAssigned.get(i).getId()+"";
//	            table[i+1][1] = coursesAssigned.get(i).getCourseName();
//	        }
//	        TableWithLines.tableWithGivenColumnsLength(table, true, "ASSIGNED COURSES", new int[] {20,20});
		//}
//		catch (ProfessorNotFoundException pnfEx) {
//			System.out.printf("->professor not found with this id: %s<-\n", pnfEx.getProfessorId());
//        }
//		catch (Exception e) {
//			System.out.println(e.toString());
//		}
		return coursesAssigned;
	}
	
	
	@Override
	public List<Student> viewEnrolledStudent(int professorId,int courseId) throws CourseNotFoundException,ProfessorNotFoundException {
		List<Student> studentList=new ArrayList<>();
		//try {
//			viewCoursesAssigned(pf);
//			Scanner sc = new Scanner(System.in);
//			
//			System.out.println("Course id:");
//			int courseId = sc.nextInt();
//			
//			SemesterRegistrationDao semesterRegistrationDao = new SemesterRegistrationDaoImplementation();
			
			Course course=courseDao.fetchCourseById(courseId);
			if(course==null)
			{
				throw new CourseNotFoundException(courseId);
			}
			if(professorDao.getProfessorById(professorId)==null)
			{
				throw new ProfessorNotFoundException(professorId);
			}
			if(course.getProfessorId()==professorId)
			{
			studentList = semesterRegistrationDao.getAllStudentsByCourseId(courseId);
			}
			if(studentList.size()==0) {
				System.out.printf("->student list is empty in course with course id: %s<-\n", courseId);
			}
			//showEnrolledStudentsByProfessor(studentList);
	//	}
//		catch (InputMismatchException inEx) {
//			System.out.println("->input type is not acceptable<-");
//        }
//		catch (Exception e) {
//			System.out.println(e.toString());
//		}
		return studentList;
	}
	
	@Override
	public boolean addGrades(int studentId,int courseId,String grade) throws StudentNotFoundException, CourseNotFoundException, GradeAlreadyAssignedException, StudentCourseNotApprovedException {
		
			
//			this.viewCoursesAssigned(pf);
//			Scanner sc = new Scanner(System.in);
//			
//			System.out.println("Course id:");
//			int courseId = sc.nextInt();
//			
//			SemesterRegistrationDao semesterRegistrationDao = new SemesterRegistrationDaoImplementation();
//			List<Student> studentList = semesterRegistrationDao.getAllStudentsByCourseId(courseId);
//			showEnrolledStudentsByProfessor(studentList);
//			
//			System.out.println("Student id:");
//			int studentId = sc.nextInt();
//			
//			System.out.println("Student grade:");
//			String studentGrade = sc.next();
//			
//			ProfessorDao professorDao = new ProfessorDaoImplementation();
			professorDao.addGrades(studentId, courseId, grade);
			return true;
//			StudentDao stu=new StudentDaoImplementation();
//			
//			StudentService studentService=new StudentService();
//			studentService.viewGrades(stu.fetchStudentById(studentId));
		
		
	}
	
	// function to show enrolled students into a course
	private void showEnrolledStudents(Course c) {
		
////		List<Student> studentsList = c.getStudentList();
//		
//		String[] columns = new String[]{"ID", "NAME", "EMAIL", "GRADE"};
//        String[][] table = new String[studentsList.size()+1][columns.length];
//        table[0] = columns;
//
//        for(int i=0; i<studentsList.size(); i++){
//            table[i+1][0] = studentsList.get(i).getId()+"";
//            table[i+1][1] = studentsList.get(i).getName();
//            table[i+1][2] = studentsList.get(i).getEmailId();
//            table[i+1][3] = c.getStudentGrades().get(studentsList.get(i).getId())+"";
//        }
//        TableWithLines.tableWithGivenColumnsLength(table, true, "ENROLLED STUDENTS (" + c.getCourseName() + ")", new int[] {20,20,20,20});		
	}
	
	// function to show enrolled students into a course
	private void showEnrolledStudentsByProfessor(List<Student> studentList) {
		String[] columns = new String[]{"ID", "NAME", "EMAIL"};
        String[][] table = new String[studentList.size()+1][columns.length];
        table[0] = columns;

        for(int i=0; i<studentList.size(); i++){
            table[i+1][0] = studentList.get(i).getId()+"";
            table[i+1][1] = studentList.get(i).getName();
            table[i+1][2] = studentList.get(i).getEmailId();
        }
        TableWithLines.tableWithGivenColumnsLength(table, true, "ENROLLED STUDENTS", new int[] {20,20,20});		
	}

}
