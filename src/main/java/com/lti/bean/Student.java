package com.lti.bean;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

	String name;
	String address = "";
	long mobileNumber;
	
	boolean isApprovedByAdmin = false;
	boolean isCoursesApprovedByAdmin = false;
	boolean isReportCardGenerated = false;
	boolean isBillGenerated = false;
	boolean paymentStatus=false;

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public boolean isBillGenerated() {
		return isBillGenerated;
	}

	public void setBillGenerated(boolean isBillGenerated) {
		this.isBillGenerated = isBillGenerated;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isReportCardGenerated() {
		return isReportCardGenerated;
	}

	public void setReportCardGenerated(boolean isReportCardGenerated) {
		this.isReportCardGenerated = isReportCardGenerated;
	}

	public boolean isApprovedByAdmin() {
		return isApprovedByAdmin;
	}

	public void setApprovedByAdmin(boolean isApprovedByAdmin) {
		this.isApprovedByAdmin = isApprovedByAdmin;
	}

	public boolean isCoursesApprovedByAdmin() {
		return isCoursesApprovedByAdmin;
	}

	public void setCoursesApprovedByAdmin(boolean isCoursesApprovedByAdmin) {
		this.isCoursesApprovedByAdmin = isCoursesApprovedByAdmin;
	}


	

}
