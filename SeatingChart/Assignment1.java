package assignment1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assignment1 {

	static Scanner inputStream;
	public static final int NUM_STUDENTS = 20;

	public static void main(String[] args) {
		String first;
		String last;
		int id;

		inputStream = new Scanner(System.in);

		SeatingChart sc = new SeatingChart();

		int option = 0;

		while (option != 5) {
			System.out.println("Please select an operation ");
			System.out.println("1 -> Add a student ");
			System.out.println("2 -> Find a student ");
			System.out.println("3 -> Swap a student ");
			System.out.println("4 -> Display Seating Chart ");
			System.out.println("5 -> Quit ");

			option = getOperation();
			switch (option) {
			case 1:
				first = getStudentFirstName();
				last = getStudentLastName();
				id = getStudentId();
				int seat = getSeatNum();

				sc.addStudent(first, last, id, seat);
				break;

			case 2:
				first = getStudentFirstName();
				int seatNum = sc.findStudent(first);
				System.out.println("\nStudent " + first + " is at seat " + seatNum);
				break;

			case 3:
				System.out.println("Enter student name");
				first = getStudentFirstName();
				System.out.println("Enter target seat");
				seat = getSeatNum();
				sc.swapStudent(first, seat);
				break;

			case 4:
				System.out.println(sc);
				break;

			case 5:
				break;

			default:
				break;

			}

		}

		/*
		 * sc.addStudent("A", "B", 11, 7); sc.addStudent("B", "C", 12, 3);
		 * sc.addStudent("C", "D", 13, 5); sc.addStudent("D", "E", 14, 1);
		 * System.out.println("******** Seating Chart **********");
		 * System.out.println(sc.toString());
		 * 
		 * System.out.println("******** Find student B **********"); int seatNumB =
		 * sc.findStudent("B"); System.out.println("Seat num of B ->  " + seatNumB);
		 * System.out.println();
		 * 
		 * System.out.println("******** Find student F **********"); int seatNumF =
		 * sc.findStudent("F"); System.out.println("Seat num of F -> " + seatNumF);
		 * System.out.println();
		 * 
		 * System.out.println("******** Move student A to empty position 0 **********");
		 * sc.swapStudent("A", 0); System.out.println(sc.toString());
		 * 
		 * System.out.println("******** Move student A to empty position 6 **********");
		 * sc.swapStudent("A", 6); System.out.println(sc);
		 * 
		 * System.out.
		 * println("******** Swap student A with student at position 1 **********");
		 * sc.swapStudent("A", 1); System.out.println(sc);
		 * 
		 * System.out.println("******** Add student F at position 2 **********");
		 * sc.addStudent("F", "G", 15, 2); System.out.println(sc);
		 * 
		 * System.out.println("******** Move student F to position 0 **********");
		 * sc.swapStudent("F", 0); System.out.println(sc);
		 * 
		 * System.out.
		 * println("******** Swap student F with student at position 3 **********");
		 * sc.swapStudent("F", 3); System.out.println(sc);
		 */
	}

	static String getStudentFirstName() {
		String stringIn;
		System.out.print("What's the student's first name? ");
		stringIn = inputStream.nextLine();
		return stringIn;
	}

	static String getStudentLastName() {
		String stringIn;
		System.out.print("What's the student's last name? ");
		stringIn = inputStream.nextLine();
		return stringIn;
	}

	static int getStudentId() {
		int id = -1;
		String stringIn;

		while (id < 0 || id > 9999) {
			try {
				System.out.print("What's the student's id #? (between 0 and 9999)");
				stringIn = inputStream.nextLine();
				id = Integer.parseInt(stringIn);
			} catch (NumberFormatException error) {
				System.out.println("********Please enter a valid student id between 0 and 9999");
				id = -1;
			}
		}

		return id;
	}

	static int getSeatNum() {
		String stringIn;

		int seatNum = -1;
		while (seatNum == -1) {
			try {
				System.out.print("What's the student's seatnum? (between 0 and " + (NUM_STUDENTS - 1) + ")");
				stringIn = inputStream.nextLine();
				seatNum = Integer.parseInt(stringIn);
				if (seatNum > NUM_STUDENTS - 1)
					seatNum = -1;

			} catch (NumberFormatException error) {
				System.out.println("********Please enter a valid seatNum from 0 to " + (NUM_STUDENTS - 1));
				seatNum = -1;
			}
		}

		return seatNum;

	}

	static int getOperation() {
		String stringIn;
		System.out.print("What operation would you like? ");
		stringIn = inputStream.nextLine();

		int option = 0;
		try {
			option = Integer.parseInt(stringIn);
		} catch (NumberFormatException error) {
			System.out.println("********Please enter a valid option ");
		}

		return option;

	}
}

class Student {

	public static final int MIN_LENGTH = 4;
	public static final int MAX_LENGTH = 40;
	public static final int MIN_ID = 0;
	public static final int MAX_ID = 9999;
	public static final String DEFAULT_FIRST_NAME = "first_name";
	public static final String DEFAULT_LAST_NAME = "last_name";

	public static final int DEFAULT_ID = 0;

	private String first;
	private String last;
	private int id;

	public Student(String first, String last, int id) {
		if (!setFirst(first))
			this.first = DEFAULT_FIRST_NAME;
		if (!setLast(last))
			this.last = DEFAULT_LAST_NAME;
		if (!setId(id))
			this.id = DEFAULT_ID;
	}

	Boolean setId(int id) {
		if (id < MIN_ID || id > MAX_ID)
			return false;
		this.id = id;
		return true;
	}

	Boolean setFirst(String first) {
		if (first == null || first.length() == 0 || !Character.isLetter(first.charAt(0)))
			return false;
		this.first = first;
		return true;
	}

	Boolean setLast(String last) {
		if (last == null || last.length() == 0 || !Character.isLetter(last.charAt(0)))
			return false;
		this.last = last;
		return true;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public int getId() {
		return id;
	}

	public void display() {
		System.out.println("Student: " + "\n  First Name: " + first + "\n  Last Name: " + last + "\n  Id: " + id);

	}

	public String toString() {
		String displayString = "Student: " + "\n  First Name: " + first + "\n  Last Name: " + last + "\n  Id: " + id;
		return displayString;

	}
}

class SeatingChart {

	public static final int NUM_STUDENTS = 20;

	public static int total_number_of_students;

	private Student[] students;

	Map<Integer, Integer> idToSeat;

	SeatingChart() {
		students = new Student[NUM_STUDENTS];
		idToSeat = new HashMap<Integer, Integer>();

	}

	public void addStudent(String first, String last, int id, int seatnum) {
		Student s = new Student(first, last, id);

		if (students[seatnum] == null)
			students[seatnum] = s;
		else {
			System.out.println("\nCannot add student at specified position since it is already occupied\n");
			return;
		}

		idToSeat.put(id, seatnum);

		total_number_of_students++;
	}

	public int findStudent(String first) {

		Student[] copy = copyArray(students);

		arraySort(copy);

		int posInSortedArr = binarySearch(copy, first, 0, SeatingChart.total_number_of_students - 1);

		if (posInSortedArr == -1) {
			System.out.println(first + " is NOT in student list.");
			return -1;
		}

		int id = copy[posInSortedArr].getId();
		return idToSeat.get(id);
	}

	private static void printArray(Student[] arr) {
		System.out.println("******Printing student details");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null)
				System.out.println(arr[i].toString());
		}
		System.out.println();
	}

	private Student[] copyArray(Student[] students) {
		Student[] copy = new Student[SeatingChart.total_number_of_students];

		int j = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i] != null) {
				copy[j] = students[i];
				j++;
			}
		}
		return copy;
	}

	public static int binarySearch(Student[] array, String first, int firstIndex, int lastIndex) {
		int middleIndex, result;

		if (firstIndex > lastIndex)
			return -1;

		middleIndex = (firstIndex + lastIndex) / 2;

		result = first.compareToIgnoreCase(array[middleIndex].getFirst());

		if (result == 0)
			return middleIndex; // found him!
		else if (result < 0)
			return binarySearch(array, first, firstIndex, middleIndex - 1);
		else
			return binarySearch(array, first, middleIndex + 1, lastIndex);
	}

	public void swapStudent(String first, int targetSeat) {
		int currentSeat = findStudent(first);

		if (currentSeat != -1) {
			if (students[targetSeat] == null) {
				move(targetSeat, currentSeat);
			} else {
				swap(students, currentSeat, targetSeat);
			}
		} else
			System.out.println("Could not find the student");
	}

	private void move(int targetSeat, int currentSeat) {
		Student currentStudent = students[currentSeat];

		int id = currentStudent.getId();

		students[currentSeat] = null;
		students[targetSeat] = currentStudent;
		idToSeat.put(id, targetSeat);
	}

	private void swap(Student[] students, int currentSeat, int targetSeat) {
		int currentStudentId = students[currentSeat].getId();
		int targetStudentId = students[targetSeat].getId();

		Student temp = students[currentSeat];
		students[currentSeat] = students[targetSeat];
		students[targetSeat] = temp;

		idToSeat.put(targetStudentId, currentSeat);
		idToSeat.put(currentStudentId, targetSeat);

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < NUM_STUDENTS; i++) {
			Student student = students[i];
			if (student != null) {
				sb.append("Seat Number " + i + " -> " + students[i].getFirst());
			} else {
				sb.append("Seat Number " + i + " empty ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	public static boolean floatLargestToTop(Student[] data, int top) {
		boolean changed = false;
		Student temp;

		for (int k = 0; k < top; k++)
			if (data[k] != null && data[k + 1] != null
					&& data[k].getFirst().compareToIgnoreCase(data[k + 1].getFirst()) > 0) {
				temp = data[k];
				data[k] = data[k + 1];
				data[k + 1] = temp;
				changed = true;
			}
		return changed;
	}

	public static void arraySort(Student[] array) {
		int everShrinkingTop;

		for (everShrinkingTop = array.length - 1; everShrinkingTop > 0; everShrinkingTop--)
			if (!floatLargestToTop(array, everShrinkingTop))
				return;
	}
}