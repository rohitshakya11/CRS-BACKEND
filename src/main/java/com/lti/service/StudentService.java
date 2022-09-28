package com.lti.service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Payment;
import com.lti.bean.Student;
import com.lti.dao.CourseDao;
import com.lti.dao.CourseDaoImplementation;
import com.lti.dao.SemesterRegistrationDao;
import com.lti.dao.SemesterRegistrationDaoImplementation;
import com.lti.dao.PaymentDao;
import com.lti.dao.PaymentDaoImplementation;
import com.lti.dao.StudentDao;
import com.lti.dao.StudentDaoImplementation;
import com.lti.exception.CourseAlreadyRegisteredException;
import com.lti.exception.CourseNotFoundException;
import com.lti.exception.DuplicatePaymentEntryException;
import com.lti.exception.SeatNotAvailableException;
import com.lti.exception.StudentCourseNotApprovedException;
import com.lti.exception.StudentNotFoundException;
import com.lti.utils.TableWithLines;

@Repository
public class StudentService  implements StudentInterfaceOperation {

	@Autowired
	CourseDao courseDao;
	
	@Autowired
	SemesterRegistrationDao semesterRegistrationDao;
	
	@Autowired
	StudentDao studentDao;
	
	@Autowired
	PaymentDao paymentDao;
	
	@Override
	public void viewCourse() {
		CourseDao coursedao = new CourseDaoImplementation();
		List<Course> courseList = coursedao.getAllCourses();
		
		String[] columns = new String[]{"COURSE ID", "COURSE NAME", "COURSE FEE"};
        String[][] table = new String[courseList.size()+1][columns.length];
        table[0] = columns;

        for(int i=0; i<courseList.size(); i++){
            table[i+1][0] = courseList.get(i).getId()+"";
            table[i+1][1] = courseList.get(i).getCourseName();
            table[i+1][2] = courseList.get(i).getCourseFee()+"";
        }
        TableWithLines.tableWithGivenColumnsLength(table, true, "COURSES AVAILABLE", new int[] {20, 20, 20});
	}

	
	@Override
	public boolean addCourse(int studentId,int semesterId,int courseId,int isCoursePrimary) throws CourseNotFoundException, SeatNotAvailableException, CourseAlreadyRegisteredException {

			semesterRegistrationDao.registerCourse(studentId, courseId, semesterId, isCoursePrimary);
			return true;
//			List<Grade> gradeList = semesterRegistrationDao.getAllRegisteredCoursesByStudentId(st.getId());
//			this.printList1(gradeList);
	
		
	}

	// function to view all courses present in course catalouge for a particular semester
	public List<Course> viewCourses(int semesterId) {
		List<Course> courseList = new ArrayList<>();
		Scanner sc=new Scanner(System.in);
		
//		CourseDao coursedao = new CourseDaoImplementation();
		courseList = courseDao.getAllCourses(semesterId);
		
//		String[] columns = new String[]{"COURSE ID", "COURSE NAME", "COURSE FEE", "SEMESTER"};
//
//        String[][] table = new String[courseList.size()+1][columns.length];
//        table[0] = columns;
//
//        for(int i=0; i<courseList.size(); i++){
//            table[i+1][0] = courseList.get(i).getId()+"";
//            table[i+1][1] = courseList.get(i).getCourseName();
//            table[i+1][2] = courseList.get(i).getCourseFee()+"";
//            table[i+1][3] = courseList.get(i).getSemesterId()+"";
//        }
//
//        // TableWithLines.tableWithLinesAndTitle(table, true, "COURSE LIST");
//        TableWithLines.tableWithGivenColumnsLength(table, true, "COURSE LIST", new int[] {20,20,20,20});
//        
		return courseList;
	}
	
	// function to print courseList
	public String printList(List<Course> courseList) {
		String[] columns = new String[]{"COURSE ID", "COURSE NAME", "COURSE FEE"};
        String[][] table = new String[courseList.size()+1][columns.length];
        table[0] = columns;

        for(int i=0; i<courseList.size(); i++){
            table[i+1][0] = courseList.get(i).getId()+"";
            table[i+1][1] = courseList.get(i).getCourseName();
            table[i+1][2] = courseList.get(i).getCourseFee()+"";
        }
        TableWithLines.tableWithGivenColumnsLength(table, true, "ADDED COURSES", new int[] {10,20, 20});
		return "";
	}
	
	// function to print courseList
    public void printList1(List<Grade> gradeList) {
		String[] columns = new String[]{"COURSE ID", "COURSE NAME", "COURSE FEE", "PRIMARY", "APPROVED"};
        String[][] table = new String[gradeList.size()+1][columns.length];
        table[0] = columns;

        for(int i=0; i<gradeList.size(); i++){
            table[i+1][0] = gradeList.get(i).getCourse().getId()+"";
            table[i+1][1] = gradeList.get(i).getCourse().getCourseName();
            table[i+1][2] = gradeList.get(i).getCourse().getCourseFee()+"";
            table[i+1][3] = gradeList.get(i).getIsCoursePrimary()+"";
            table[i+1][4] = gradeList.get(i).getIsApprovedByAdmin()+"";
           }
        TableWithLines.tableWithGivenColumnsLength(table, true, "ADDED COURSES", new int[] {20,20,20,20,20});
	}
	
    
	@Override
	public boolean dropCourse(int studentId,int courseId) throws CourseNotFoundException, StudentNotFoundException {
		
//			SemesterRegistrationDao semesterRegistrationDao = new SemesterRegistrationDaoImplementation();
//			printList1(semesterRegistrationDao.getAllRegisteredCoursesByStudentId(st.getId()));
//			System.out.println("Enter course id which you want to drop:");
//			
//			Scanner sc=new Scanner(System.in);
//			int id=sc.nextInt();
			
			semesterRegistrationDao.deleteRegisteredCourse(studentId,courseId);
//			printList1(semesterRegistrationDao.getAllRegisteredCoursesByStudentId(st.getId()));
		return true;
	}
	
	
	@Override
	public List<Grade> viewRegisteredCourses(int studentId) throws StudentNotFoundException {
		List<Grade> gradeList=new ArrayList<>();
		gradeList=semesterRegistrationDao.getAllRegisteredCoursesByStudentId(studentId);		
		return gradeList;
	}
	
	@Override
	public List<Grade> viewRegisteredCoursesInSemester(int studentId, int semesterId) throws StudentNotFoundException {
		List<Grade> gradeList=new ArrayList<>();
		gradeList=semesterRegistrationDao.getAllRegisteredCoursesInSemester(studentId, semesterId);		
		return gradeList;
	}
	
	
	@Override
	public List<Grade> viewGrades(int studentId) throws StudentNotFoundException {
		List<Grade> gradeList=new ArrayList<>();
		gradeList = studentDao.getGradesByStudentId(studentId);
		return gradeList;
	}
	
	@Override
	public List<Payment> getPayments(int studentId) throws StudentNotFoundException {
		List<Payment> paymentList=new ArrayList<>();
		paymentList = studentDao.getPaymentsByStudentId(studentId);
		return paymentList;
	}
	
	
	@Override
	public Payment payFee(int studentId,String paymentMethod) throws DuplicatePaymentEntryException, StudentCourseNotApprovedException, StudentNotFoundException{
		Payment p;
		
			if(studentDao.fetchStudentById(studentId).isCoursesApprovedByAdmin()==false) {
				throw new StudentCourseNotApprovedException(studentId);
			}
			
//			SemesterRegistrationDao semesterRegistrationDao = new SemesterRegistrationDaoImplementation();
			List<Grade> gradeList = semesterRegistrationDao.getAllRegisteredCoursesByStudentId(studentId);
//			printList2(gradeList);
			
//	        int fee=0;
//	        for (Grade g : gradeList) {
//				fee += g.getCourse().getCourseFee();
//			}
//			System.out.println("Fee-------" + fee);
			
//			Payment p = new Payment();
//			
//			PaymentInterfaceOperation paymentService = new PaymentService();
//			paymentService.showPaymentMethods(p);
//			
//			String paymentMethod=paymentService.makePayment();
			
//			PaymentDao paymentDao=new PaymentDaoImplementation();
			if(paymentDao.getPaymentStatusByStudentId(studentId).getPaymentStatus())
			{
			throw new DuplicatePaymentEntryException(studentId);
			}
			p=paymentDao.updateFeeStatus(studentId, paymentMethod);
			return p;
		
		
		
	}
	
	public void printList2(List<Grade> gradeList) {
		String[] columns = new String[] { "COURSE ID", "COURSE NAME", "COURSE FEE" };
		String[][] table = new String[gradeList.size() + 1][columns.length];
		table[0] = columns;

		for (int i = 0; i < gradeList.size(); i++) {
            table[i+1][0] = gradeList.get(i).getCourse().getId()+"";
            table[i+1][1] = gradeList.get(i).getCourse().getCourseName();
            table[i+1][2] = gradeList.get(i).getCourse().getCourseFee()+"";
		}
		TableWithLines.tableWithGivenColumnsLength(table, true, "REGISTERED COURSES", new int[] { 20, 20, 20 });
	}

}
