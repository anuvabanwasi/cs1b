package assignment2;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Assignment2 {

	static Scanner inputStream;

	public static void main(String[] args) {
		/*
		 * Employee e = new Employee("John", "101-221-2121"); FTE fte = new
		 * FTE("Sarah", "321-12-1234", 10000); Contractor c = new
		 * Contractor("Jack", "562-78-9123", 21.5);
		 */

		inputStream = new Scanner(System.in);

		Employee e = getEmployeeDetails();
		Employee fte = getFTEDetails();
		Employee c = getContractorDetails();

		Employee[] employees = { e, fte, c };

		for (Employee employee : employees) {
			if (employee instanceof Contractor) {
				System.out.println("\nPrinting contractor details");
				double hoursWorked = getHoursWorked();
				System.out.println(employee.payCheck(hoursWorked));
			} else {
				System.out.println("\nPrinting employee details");
				System.out.println(employee.payCheck());
			}
		}
	}

	static double getHoursWorked() {
		String stringIn;
		System.out.print("Enter the number of hours worked ");
		stringIn = inputStream.nextLine();

		double numHrs = 0.0;
		try {
			numHrs = Double.parseDouble(stringIn);
		} catch (Exception e) {
			System.out.println("Invalid input");
		}

		return numHrs;
	}

	static Employee getFTEDetails() {
		System.out.println("\nEnter FTE details ");

		String name = getEmployeeName();
		String ssn = getEmployeeSSN();
		double salary = getYearlySalary();

		Employee e = new FTE(name, ssn, salary);
		return e;
	}

	static Employee getContractorDetails() {

		System.out.println("\nEnter contractor details ");

		String name = getEmployeeName();
		String ssn = getEmployeeSSN();
		double payRate = getPayRate();

		Employee e = new Contractor(name, ssn, payRate);
		return e;
	}

	static Employee getEmployeeDetails() {
		System.out.println("\nEnter employee details ");
		String name = getEmployeeName();
		String ssn = getEmployeeSSN();

		Employee e = new Employee(name, ssn);
		return e;
	}

	static String getEmployeeName() {
		String stringIn;
		System.out.print("Enter Name - ");
		stringIn = inputStream.nextLine();
		return stringIn;
	}

	static String getEmployeeSSN() {
		String stringIn;
		System.out.print("Enter SSN - Please enter 9 digit number");
		stringIn = inputStream.nextLine();

		return stringIn;
	}

	static double getYearlySalary() {
		String stringIn;
		System.out.print("Enter Salary - ");
		stringIn = inputStream.nextLine();
		double salary = 0.0;
		try {
			salary = Double.parseDouble(stringIn);
		} catch (Exception e) {
			System.out.println("Invalid input");
		}
		return salary;
	}

	static double getPayRate() {
		String stringIn;
		System.out.print("Enter PayRate - ");
		stringIn = inputStream.nextLine();

		double payRate = 0.0;
		try {
			payRate = Double.parseDouble(stringIn);
		} catch (Exception e) {
			System.out.println("Invalid input");
		}

		return payRate;
	}

}

class Employee {

	private String name;
	private String SSN;
	public static final String DEFAULT_SSN = "000000000";
	public static final int VALID_SSN_NUM_LEN = 9;

	Employee(String name, String ssn) {
		setName(name);
		if (!setSSN(ssn))
			SSN = DEFAULT_SSN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSSN() {
		return SSN;
	}

	public boolean setSSN(String ssn) {
		if (!validSSN(ssn))
			return false;

		SSN = ssn;
		return true;
	}

	public static boolean isNumber(String s) {
		for (int k = 0; k < s.length(); k++)
			if (!Character.isDigit(s.charAt(k)))
				return false;
		return true;
	}

	private static boolean validSSN(String s) {
		if (isNumber(s) && (s.length() == VALID_SSN_NUM_LEN))
			return true;
		return false;
	}

	public String payCheck() {

		String result = "Name : " + name + "\n";
		result = result + "SSN : " + SSN + "\n";
		return result;
	}

	public String payCheck(double hoursWorked) {

		return payCheck();
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", SSN=" + SSN + "]";
	}
}

class FTE extends Employee {
	private double salary;

	FTE(String name, String SSN, double salary) {
		super(name, SSN);
		this.salary = salary;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return super.toString() + " [salary=" + salary + "]";
	}

	public String payCheck() {
		DecimalFormat df = new DecimalFormat("#.##");
		String formattedPayCheck = df.format(getSalary() / 12.0);

		String result = super.payCheck();
		result = result + "Monthly Paycheck : $" + formattedPayCheck + "\n";

		return result;
	}

}

class Contractor extends Employee {

	private double payRate;

	Contractor(String name, String SSN, double payRate) {
		super(name, SSN);
		this.payRate = payRate;
	}

	public double getPayRate() {
		return payRate;
	}

	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}

	public String payCheck(double hours) {

		DecimalFormat df = new DecimalFormat("#.##");
		String formattedTotalPay = df.format(hours * getPayRate());

		String result = payCheck();
		result = result + "Total Pay : $" + formattedTotalPay + "\n";

		return result;
	}
}