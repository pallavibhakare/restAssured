package utils;

public class AddUserPojo {

	private String accountno;
	private String departmentno;
	private String salary;
	private String pincode;
	
	public AddUserPojo(String accountno, String departmentno, String salary, String pincode) {
	this.accountno = accountno;
	this.departmentno = departmentno;
	this.salary = salary;
	this.pincode = pincode;
	}
	
	public String getAccountNo() {
		return accountno;
	}
	public String getDepartmentNo() {
		return departmentno;
	}
	public String getSalary() {
		return salary;
	}
	public String getPincode() {
		return pincode;
	}
}
