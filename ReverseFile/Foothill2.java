package assignment5;

import java.io.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

//------------------------------------------------------
// FileLeaner class -----------------------------------
public class Foothill2 {
	// ------- main --------------
	public static void main(String[] args) throws Exception {

		try {
			Scanner filein = new Scanner(new FileInputStream("reverseSpells3.txt"));

			while (filein.hasNext()) { // while there is another token to read
				String s = filein.next(); // reads in the String tokens "Hello"
											// "CSstudents"
				String r = filein.next(); // reads in the String tokens "There"
											// "goodbye"
				int x = filein.nextInt(); // reads in the int tokens 1234 6556
				System.out.println(s + ", " + r + ", " + x);
				// System.out.println(s );
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		System.out.println("Done");

		try {
			Scanner filein = new Scanner(new FileInputStream("reverseSpells3.txt"));

			// while there is another token to read
			String s = filein.next(); // reads in the String tokens "Hello"
										// "CSstudents"
			String r = filein.next(); // reads in the String tokens "There"
										// "goodbye"
			int x = filein.nextInt(); // reads in the int tokens 1234 6556
			System.out.println(s + ", " + r + ", " + x);
			s = filein.nextLine();
			System.out.println(s);
			System.out.println("Done");
			
			List<String> list = new ArrayList<String>();
			list.add("Hello");
			list.add("World");
			list.add("life");
			
			
			ListIterator<String> p = list.listIterator();
			
			while(p.hasNext()){
				System.out.println(p.next());
				p.add("df");
			}
			for(String str : list) {
				System.out.println(str);
			}
			
			
			System.out.println("print backwards");
			while(p.hasPrevious()){
				System.out.println(p.previous());
			}
			
			System.out.println("insert");
			list.add(3, "akkk");
			
			for(String str : list) {
				System.out.println(str);
			}
			Collections.binarySearch(list, "a");
			

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
} // end of class FileLeaner -----------------------------
	// ------------------------------------------------------