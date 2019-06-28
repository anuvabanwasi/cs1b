package assignment5;

import java.io.*;
import java.text.*;
import java.util.Scanner;

//------------------------------------------------------
// FileLeaner class -----------------------------------
public class Foothill1
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      BufferedReader myFileIn;
      PrintWriter myFileOut;
      final int SIZE = 20;
      int k;
      float[] myFloats;
      String[] myStrings;
      DecimalFormat fourSigDigs = new DecimalFormat( "###.0");
   
      
      // ------- build a test array of floats 
      myFloats = new float[SIZE];
      for (k = 0; k < SIZE; k++)
         myFloats[k] 
            = (float) (100.*Math.random() / (Math.random() + 1) );
   
      try
      {  
         // ------- create and write the file
         myFileOut = new PrintWriter("reverseSpells3.txt" );
         for (k = 0; k < myFloats.length; k++)
            myFileOut.println( "Float #" + k + " = "
                 + fourSigDigs.format(myFloats[k]) );
         myFileOut.close();
      
         // ------- open and read the file
         myStrings = new String[myFloats.length];
         myFileIn = new BufferedReader( 
            new FileReader("reverseSpells3.txt") );

         for ( k = 0; myFileIn.ready(); k++ )
            myStrings[k] = myFileIn.readLine();
         myFileIn.close();
    
         // ------- show the user
         for (k = 0; k < SIZE; k++)
            System.out.println(myStrings[k]);
      }
      catch( FileNotFoundException e)
      {
         System.out.println("\nOops. Problem opening the file!");
         e.printStackTrace();
      } 
      
      
      try {
    	    Scanner filein = new Scanner(new FileInputStream("reverseSpells3.txt"));
    	    
    	    while (filein.hasNext()) {      // while there is another token to read
    	        String s = filein.next();   // reads in the String tokens "Hello" "CSstudents"
    	        String r = filein.next();   // reads in the String tokens "There" "goodbye"
    	        int x = filein.nextInt();   // reads in the int tokens 1234  6556
    	        System.out.println(s + ", " + r + ", " + x);
    	    }
      } catch (IOException e){
    	  e.printStackTrace();
    	  
      }
   }
} // end of class FileLeaner -----------------------------
//------------------------------------------------------