package com.lti.service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Payment;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.dao.AdminDao;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.CourseDao;
import com.lti.dao.CourseDaoImplementation;
import com.lti.dao.SemesterRegistrationDao;
import com.lti.dao.SemesterRegistrationDaoImplementation;
import com.lti.dao.PaymentDao;
import com.lti.dao.PaymentDaoImplementation;
import com.lti.dao.ProfessorDao;
import com.lti.dao.ProfessorDaoImplementation;
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
import com.lti.exception.UserNotDeletedException;
import com.lti.exception.UserNotFoundException;
import com.lti.utils.TableWithLines;

@Repository
public class AdminService implements AdminInterfaceOperation {
	
	@Autowired
	CourseDao coursedao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ProfessorDao professorDao;
	
	@Autowired
	AdminDao adminDao;
	
	@Autowired
	StudentDao studentDao;
	
	@Autowired
	SemesterRegistrationDao semesterRegistrationDao;
	
	// Function to view all the courses present in the course catalogue
	@Override
	public List<Course> viewCourses() {
		
		List<Course> courseList = coursedao.getAllCourses();
		
		if(courseList.size()==0) {
			System.out.println("->course table is empty<-");
			return null;
		}

//		String[] columns = new String[] { "COURSE ID", "COURSE NAME", "COURSE FEE", "SEMESTER", "PROFESSOR ID" };
//		String[][] table = new String[courseList.size() + 1][columns.length];
//		table[0] = columns;
//
//		for (int i = 0; i < courseList.size(); i++) {
//			table[i + 1][0] = courseList.get(i).getId() + "";
//			table[i + 1][1] = courseList.get(i).getCourseName();
//			table[i + 1][2] = courseList.get(i).getCourseFee() + "";
//			table[i + 1][3] = courseList.get(i).getSemesterId() + "";
//			table[i + 1][4] = courseList.get(i).getProfessorId() + "";
//		}
//		TableWithLines.tableWithGivenColumnsLength(table, true, "COURSE LIST", new int[] { 20, 20, 20, 20, 20 });
	return courseList;
	}
	
	// Function to view all the courses present in the course catalogue for a particular semester
	// @Param semesterId
	@Override
	public void viewCourses(int semesterId) {
		CourseDao coursedao = new CourseDaoImplementation();
		List<Course> courseList = coursedao.getAllCourses(semesterId);
		
		if(courseList.size()==0) {
			System.out.printf("->course table is empty for semester id %d<-\n", semesterId);
			return;
		}

		String[] columns = new String[] { "COURSE ID", "COURSE NAME", "COURSE FEE", "SEMESTER" };
		String[][] table = new String[courseList.size() + 1][columns.length];
		table[0] = columns;

		for (int i = 0; i < courseList.size(); i++) {
			table[i + 1][0] = courseList.get(i).getId() + "";
			table[i + 1][1] = courseList.get(i).getCourseName();
			table[i + 1][2] = courseList.get(i).getCourseFee() + "";
			table[i + 1][3] = courseList.get(i).getSemesterId() + "";
		}

		TableWithLines.tableWithGivenColumnsLength(table, true, "COURSE LIST", new int[] { 20, 20, 20, 20 });
	}

	// Function to add a new course in the catalogue
	// @Param courseId, courseName, courseFee, courseDepartment, courseCredit, semesterId
	// handle DuplicateCourseEntry
	@Override
	public void addCourse(Course course) throws DuplicateCourseEntryException {
	
//			this.viewCourses();
			
//			CourseDao coursedao = new CourseDaoImplementation();

//			System.out.println("Enter course info (Add Course):");
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("Course id:");
//			int id = sc.nextInt();
//
//			System.out.println("Course name:");
//			String name = sc.next();
//
//			System.out.println("Course fee:");
//			int fee = sc.nextInt();
//
//			System.out.println("Course department:");
//			String department = sc.next();
//
//			System.out.println("Course credit:");
//			int credit = sc.nextInt();
//
//			System.out.println("Enter semester id for which you want to add the course:");
//			int semesterId = sc.nextInt();

			coursedao.insertCourse(course.getId(), course.getCourseName(),course.getProfessorId(), course.getCourseFee(), course.getDepartment(), course.getCredit(), course.getSemesterId());

//			this.viewCourses(semesterId);
		

	}
	
	// Function to remove a course from the catalogue
	// @Param courseId
	// handle CourseNotFound
	@Override
	public Course deleteCourse(int id) throws CourseNotFoundException {

		Course course=null;
	
//			this.viewCourses();
//
//			System.out.println("Enter course info (Delete Course):");
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("Course id:");
//			int id = sc.nextInt();

//			CourseDao coursedao = new CourseDaoImplementation();
			course=coursedao.deleteCourse(id);

//			this.viewCourses();
		
    return course;
		
	}
	
	// Function to modify the course information/content
	// @Param courseId, courseName, courseFee, courseDepartment, courseCredit, status, professorId semesterId
	// handle CourseNotFound
	@Override
	public Course updateCourse(Course course) throws CourseNotFoundException {
		
		
//			this.viewCourses();
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("Course id:");
//			int id = sc.nextInt();
//
//			CourseDao coursedao = new CourseDaoImplementation();
//			Course c = coursedao.fetchCourseById(id);
//			
//			if(c==null) {
//				System.out.printf("->course not found with this course id: %s<-\n", id);
//			}
//
//			System.out.println("Enter your choice to update specific field in the selected course:");
//			System.out.println("1.Course name");
//			System.out.println("2.Course fee");
//			System.out.println("3.Course department");
//			System.out.println("4.Course credit");
//			System.out.println("5.Course Status");
//			System.out.println("6.Professor id");
//			System.out.println("7.Semester");
//
//			int choice = sc.nextInt();
//
//			switch (choice) {
//			case 1:
//				System.out.println("Enter updated Course name:");
//				String name = sc.next();
//				c.setCourseName(name);
//				break;
//
//			case 2:
//				System.out.println("Enter updated Course fee:");
//				int fee = sc.nextInt();
//				c.setCourseFee(fee);
//				break;
//
//			case 3:
//				System.out.println("Enter updated Course department:");
//				String department = sc.next();
//				c.setDepartment(department);
//				break;
//
//			case 4:
//				System.out.println("Enter updated Course credit:");
//				int credit = sc.nextInt();
//				c.setCredit(credit);
//				break;
//
//			case 5:
//				System.out.println("Enter updated Course status:");
//				boolean status = sc.nextBoolean();
//				c.setStatus(status);
//				break;
//
//			case 6:
//				System.out.println("Enter updated Professor id:");
//				int professorId = sc.nextInt();
//				c.setProfessorId(professorId);
//				break;
//
//			case 7:
//				System.out.println("Enter updated Semester:");
//				int semesterId = sc.nextInt();
//				c.setSemesterId(semesterId);
//				break;
//			}

//			CourseDao coursedao=new CourseDaoImplementation();
			coursedao.updateCourse(course);
//			this.viewCourses();
			return course;
		
		
	}
	
	// Function to assign a professor to a course
	// @Param courseId, professorId
	// handle CourseNotFound, ProfessorNotFound
	@Override
	public boolean assignCourseToProfessor(int professorId,int courseId) throws CourseNotFoundException,ProfessorNotFoundException {

		
//			this.viewCourses();
//			
//			System.out.println("admin assigning course to professor");
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("Course id:");
//			int courseId = sc.nextInt();
//
//			System.out.println("Professor id:");
//			int professorId = sc.nextInt();
		    if(coursedao.fetchCourseById(courseId)==null)
		    {
		    	throw new CourseNotFoundException(courseId);
		    }
		    if(professorDao.getProfessorById(professorId)==null)
		    {
		    	throw new ProfessorNotFoundException(professorId);
		    }

			AdminDao adminDao = new AdminDaoImplementation();
			adminDao.assignCourseToProfessor(courseId, professorId);
			return true;
//			this.viewCourses();
		
	}
	
	// Function to view all the professors
	@Override
	public List<Professor> viewProfessorList() {
		ProfessorDao professordao = new ProfessorDaoImplementation();
		List<Professor> professorList = professordao.getAllProfessors();
		
		if(professorList.size()==0) {
			System.out.println("->Professor table is empty<-");
			return null;
		}

//		String[] columns = new String[] { "ID", "NAME", "EMAIL" };
//		String[][] table = new String[professorList.size() + 1][columns.length];
//		table[0] = columns;
//
//		for (int i = 0; i < professorList.size(); i++) {
//			table[i + 1][0] = professorList.get(i).getId() + "";
//			table[i + 1][1] = professorList.get(i).getName();
//			table[i + 1][2] = professorList.get(i).getEmailId();
//		}
//		TableWithLines.tableWithGivenColumnsLength(table, true, "PROFESSOR LIST", new int[] { 20, 20, 20 });
		return professorList;
	}
	
	// Function to add a professor in the system
	// @Param professorId, professorName, professorEmail, professorPassword
	// handle DuplicateProfessorEntry
	@Override
	public Professor addProfessor(Professor professor) throws DuplicateUserEntryException, DuplicateProfessorEntryException, ProfessorNotAddedException {

		Professor addedProfessor=null;
		
			//this.viewProfessorList();

			//System.out.println("Enter professor info (Add Professor):");
			//Scanner sc = new Scanner(System.in);

			//System.out.println("professor id:");
			//int id = sc.nextInt();

			//System.out.println("professor name:");
			//String name = sc.next();

			//System.out.println("professor email:");
			//String email = sc.next();

			//System.out.println("professor password:");
			//String password = sc.next();

			//String role = "professor";
			
//			UserDao userDao = new UserDaoImplementation();
			userDao.insertUser(professor.getEmailId(), professor.getPassword(), professor.getRole());
			
//			ProfessorDao professordao = new ProfessorDaoImplementation();
			addedProfessor=professorDao.insertProfessor(professor.getId(), professor.getName(), professor.getEmailId(), professor.getPassword(), professor.getRole());
			// this.viewProfessorList();
		
		return addedProfessor;
	}
	
	// Function to remove a professor from the system
	// @Param ProfessorId
	// handle ProfessorNotFound
	@Override
	public Professor deleteProfessor(int professorId) throws UserNotFoundException, UserNotDeletedException,ProfessorNotFoundException,ProfessorNotDeletedException {
		Professor professor=null;
		
//			this.viewProfessorList();
//			
//			System.out.println("Enter professor info (Delete Professor):");
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("professor id:");
//			int professorId = sc.nextInt();
//			
//			ProfessorDao professordao = new ProfessorDaoImplementation();
//			Professor p = professordao.getProfessorById(professorId);
//			
//			UserDao userDao = new UserDaoImplementation();
			userDao.deleteUser(professorDao.getProfessorById(professorId).getEmailId());
			professor=professorDao.deleteProfessor(professorId);
//			
//			this.viewProfessorList();
		
		
		
		return professor;
	}

	@Override
	public List<Student> viewStudentList() {
		StudentDao studentDao = new StudentDaoImplementation();
		List<Student> studentList = studentDao.fetchAllStudents();
		return studentList;
	}

	@Override
	public void viewPendingCourseApprovalStudentList() {
		StudentDao studentDao = new StudentDaoImplementation();
		List<Student> studentList = studentDao.fetchAllStudents();

		String[] columns = new String[] { "ID", "NAME", "EMAIL", "COURSES APPROVED" };
		String[][] table = new String[studentList.size() + 1][columns.length];
		table[0] = columns;

		for (int i = 0; i < studentList.size(); i++) {
			table[i + 1][0] = studentList.get(i).getId() + "";
			table[i + 1][1] = studentList.get(i).getName();
			table[i + 1][2] = studentList.get(i).getEmailId();
			if (studentList.get(i).isCoursesApprovedByAdmin()) {
				table[i + 1][3] = "Yes";
			} else {
				table[i + 1][3] = "No";
			}
		}
		TableWithLines.tableWithGivenColumnsLength(table, true, "STUDENT LIST", new int[] { 20, 20, 20, 20 });
	}
	
	// Function to approve a student registration in the system
	// @param studentId
	// handle StudentNotFound, DuplicateUserEntry
	@Override
	public Student approveStudentRegistration(int studentId) throws DuplicateUserEntryException,StudentNotFoundException {
			Student student=null;
	
//			System.out.println("admin approving student registration");
//			viewStudentList();
//
//			System.out.println("Enter Student info (Approve Student registration):");
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("student id:");
//			int studentId = sc.nextInt();

			adminDao.approveStudentInDB(studentId);			
			Student st = studentDao.fetchStudentById(studentId);
			student=st;
			userDao.insertUser(st.getEmailId(), st.getPassword(), st.getRole());

//			viewStudentList();
		
		return student;
	}

	// Function to approve student registered courses by admin
	// @param studentId
	// handle StudentNotFound, NoCourseFoundForStudent
	@Override
	public Student approveStudentCourseRegistration(int studentId) throws StudentNotFoundException {
		Student student=null;
		
//			System.out.println("admin approving student courses");
//			viewPendingCourseApprovalStudentList();
//
//			System.out.println("Enter Student info (Approve Student Course registration):");
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("student id:");
//			int studentId = sc.nextInt();

//			AdminDao adminDao = new AdminDaoImplementation();
			adminDao.approveStudentCourseRegistration(studentId);

//			SemesterRegistrationDao semesterRegistrationDao = new SemesterRegistrationDaoImplementation();
			List<Grade> gradeList = semesterRegistrationDao.getAllRegisteredCoursesByStudentId(studentId);
			
			for(Grade grade : gradeList) {
				System.out.println("student is getting registered for: " + grade.getCourse().getCourseName());
				semesterRegistrationDao.approveStudentRegisteredCourse(studentId, grade.getCourse().getId());
			}
			student=studentDao.fetchStudentById(studentId);
//			viewPendingCourseApprovalStudentList();
		
		return student;
	}
	
	// Function to cancel a course for the semester if student strength < 3
	// @param courseId
	// handle CourseNotFound, NoStudentFoundIntoCourse
	@Override
	public Course cancelCourse(int courseId) throws CourseNotFoundException {
		Course updatedCourse=null;
	
//			CourseDao coursedao = new CourseDaoImplementation();
//			this.viewCourses();
//
//			System.out.println("Enter course id which you want to cancel:");
//
//			Scanner sc = new Scanner(System.in);
//			int courseId = sc.nextInt();
			
			SemesterRegistrationDao semesterRegistrationDao = new SemesterRegistrationDaoImplementation();
			List<Student> studentList = semesterRegistrationDao.getAllStudentsByCourseId(courseId);

			int number_of_students = studentList.size();
			if (number_of_students >= 3) {
				System.out.println("You cannot cancel this course: number of students >= 3");
			} else {
				 updatedCourse=coursedao.setStatus(courseId);
				System.out.println("admin cancelling course: number of students < 3");
			}
		
		
		return updatedCourse;
	}

	@Override
	public Student findStudent(int id, List<Student> studentList) {
		for (Student s : studentList) {
			if (s.getId() == id) {
				return s;
			}
		}
		return null;
	}
	
	// Function to show report card of student
	// @param studentId
	// handle StudentNotFound
	@Override
	public void showReportCard(Student st) {
		
		try {
			String title = "College Name (2022-23)";
			int[] colsLength = new int[] { 20, 20, 20 };

			// fill map with columns length
			Map<Integer, Integer> columnLengths = TableWithLines.fillMapWithColumnsLength(colsLength);
			String line = TableWithLines.getBorderLine(columnLengths);
			String titleLine = TableWithLines.getTitleLine(title, line.length() - 1);

			// print college title and student information row
			System.out.print(line);
			System.out.print(titleLine);
			System.out.print(line);

			String[] columns1 = new String[] { "SID: " + st.getId(), "", "NAME: " + st.getName() };
			System.out.printf("| %-20s   %-20s   %-20s |\n", (Object[]) columns1);
			
			StudentDao studentDao = new StudentDaoImplementation();
			List<Grade> gradeList = studentDao.getGradesByStudentId(st.getId());

			// print grade table
			String[] columns = new String[] { "COURSE ID", "COURSE NAME", "COURSE GRADE" };
			String[][] table = new String[gradeList.size() + 1][columns.length];
			table[0] = columns;

			for(int i=0; i<gradeList.size(); i++){
	            table[i+1][0] = gradeList.get(i).getCourse().getId()+"";
	            table[i+1][1] = gradeList.get(i).getCourse().getCourseName();
	            table[i+1][2] = gradeList.get(i).getGrade();
	        }
			TableWithLines.tableWithGivenColumnsLength(table, true, "STUDENT GRADES", colsLength);
		}
		catch (StudentNotFoundException snfEx) {
			System.out.printf("->Student not found with this id: %d<-\n", snfEx.getStudentId());
        }
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	// Function to generate the report card for students for the semester
	// @param studentId
	// handle StudentNotFound
	@Override
	public Student generateReportCard(int studentId) throws StudentNotFoundException,CoursesNotApprovedException,StudentNotGradedException,PaymentNotMadeException{
		Student student=null;
		
//			System.out.println("admin generating report Card...");
//
//			viewStudentList();
//
//			System.out.println("Enter Student info (Generate Report Card):");
//
//			Scanner sc = new Scanner(System.in);
//
//			System.out.println("student id:");
//			int studentId = sc.nextInt();
			
			
			
			if(studentDao.isStudentGraded(studentId)==false) {
				throw new StudentNotGradedException(studentId);
			}
			
			PaymentDao paymentDao = new PaymentDaoImplementation();
			boolean paymentStatus = false;
			Payment p = paymentDao.getPaymentStatusByStudentId(studentId);
			if(p!=null && p.getPaymentStatus()==true) {
				paymentStatus = true;
			}
			Student st = new Student();
			st = studentDao.fetchStudentById(studentId);
			
			if(st==null) {
				throw new StudentNotFoundException(studentId);
			}
			else if(st.isCoursesApprovedByAdmin()==false) {
				throw new CoursesNotApprovedException(studentId);
			}
			else if(paymentStatus==false) {
				throw new PaymentNotMadeException(studentId);
			}
			else {
				showReportCard(st);
			}
			adminDao.generateReportCard(studentId);
			student=studentDao.fetchStudentById(studentId);
			
//		}
//		catch (StudentNotFoundException snfEx) {
//			System.out.printf("->Student not found with this id: %d<-\n", snfEx.getStudentId());
//        }
//		catch (CoursesNotApprovedException e) {
//			System.out.printf("->courses not approved for student id: %d<-\n", e.getStudentId());
//		}
//		catch (StudentNotGradedException e) {
//			System.out.printf("->Student with id: %d not graded in all courses<-\n", e.getStudentId());
//		}
//		catch (PaymentNotMadeException e) {
//			System.out.printf("->Student with id: %d has not made payment yet<-\n", e.getStudentId());
//		}
//		catch (InputMismatchException inEx) {
//			System.out.println("->input type is not acceptable<-");
//        }
//		catch (Exception e) {
//			System.out.println("->unknown error<-");
//		}
		return student;
	}
	
	// Function to generate bill of student
	// @param studentId
	// handle StudentNotFound, NoCourseFoundForStudent, DuplicatePaymentEntry
	@Override
	public int generateBill(int studentId) throws StudentNotFoundException,DuplicatePaymentEntryException,CoursesNotApprovedException {
			List<Grade> gradeList = semesterRegistrationDao.getAllRegisteredCoursesByStudentId(studentId);
			
			int fee = 0;
			for (Grade g : gradeList) {
				fee += g.getCourse().getCourseFee();
			}

			PaymentDao paymentdao = new PaymentDaoImplementation();
			paymentdao.generateBill(studentId, fee);
			return fee;

	}

	@Override
	public void printList(List<Grade> gradeList) {
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
	
	public Course getCourseById(int courseId) {
		return coursedao.fetchCourseById(courseId);
	}
}
