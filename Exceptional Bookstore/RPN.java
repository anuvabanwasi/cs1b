package assignment4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;


public class RPN
{
   public static void main(String[] args)
   {
      // establish main frame in which program will run
      Calculator myCalculator 
            = new Calculator("CS 1B RPN Calculator");
      myCalculator.setSize(250, 300);
      myCalculator.setLocationRelativeTo(null);
      myCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // show everything to the user
      myCalculator.setVisible(true);
   }
}

//Calculator class is derived from JFrame class
class Calculator  extends JFrame
{
   private JButton btnPlus, btnMinus, btnMult, btnDiv, btnEnter, btnEnter1;
   private JTextField txtISBN;
   private JTextField txtTitle;
   private JLabel lblInstructions;
   private JLabel lblISBN;
   private JLabel lblTitle;
   private JPanel pnlButtons, pnlTextEnter;
   private Stack stk;
   
   // Calculator constructor
   public Calculator(String title)
   {
      // pass the title up to the JFrame constructor
      super(title);
      
      // set up main components for the JFrame
      lblInstructions = new JLabel("Please enter book details");
      pnlTextEnter = new JPanel( new GridLayout(2, 2, 10, 10));
      pnlButtons = new JPanel( new GridLayout(1, 2, 20, 20));

      // use border layout and put three above components in frame
      setLayout (new BorderLayout(20, 10));
      
      add(pnlTextEnter, BorderLayout.CENTER );
      add(pnlButtons, BorderLayout.SOUTH);
      add(lblInstructions, BorderLayout.NORTH);

      // set up the components for the pnlTextEnter Panel
      btnEnter = new JButton("Save");
      btnEnter1 = new JButton("Reset");
      
      txtISBN = new JTextField("0.0", 15);  
      txtTitle = new JTextField("0.0", 15);
      
      lblISBN = new JLabel();
      lblISBN.setText("ISBN");
      
      lblTitle = new JLabel();
      lblTitle.setText("Title");
      
      // add components to pnlTextEnter Panel
      pnlTextEnter.add(lblISBN);
      pnlTextEnter.add(txtISBN);
     
      pnlTextEnter.add(lblTitle);
      pnlTextEnter.add(txtTitle);
      
       
      
      // set up the components for the pnlButtons Panel
      btnPlus = new JButton("+");
      btnMinus = new JButton("-");
      btnMult = new JButton("*");
      btnDiv = new JButton("/");
     
      // add components to pnlTextEnter Panel
      pnlButtons.add(btnEnter);
      pnlButtons.add(btnEnter1);
     
      // place borders around three sub-panels
      pnlTextEnter.setBorder(new TitledBorder("Book Info"));
      pnlButtons.setBorder(new TitledBorder("Save & Cancel"));
      
      // register Enter listeners (for pushing numbers)
      ActionListener enterListener = new EnterListener();
      txtISBN.addActionListener( enterListener );
 
      // register Operation listeners (for doing the math)
      ActionListener opListener = new OpListener();
      btnPlus.addActionListener( opListener );
      btnMinus.addActionListener( opListener );
      btnMult.addActionListener( opListener );
      btnDiv.addActionListener( opListener );
      
      btnEnter.addActionListener( enterListener );

      
      // create a stack
      stk = new Stack();    
   }

   // inner class for btnEnter and txtNumber Listener
   class EnterListener implements ActionListener
   {
      // event handler for JButton or Enter Key
      public void actionPerformed(ActionEvent e)
      {
         double dblA = 0;
         String strA;

         // read value from text fields into local variables
         strA = txtISBN.getText();
         txtISBN.setText("");  // clear
         txtISBN.requestFocus();

         // try to convert from String to double, return if fail
         try
         {
            dblA = Double.parseDouble(strA);
         }
         catch (NumberFormatException error)
         {
            return;
         }
     
         stk.push(dblA);
         
         /*if ( !stk.push(dblA) )
            txtNumber.setText( "Stack Overflow");*/
      }
   } // end inner class EnterListener
 
   // inner class for OpListener
   class OpListener implements ActionListener
   {
      // event handler for JButton or Enter Key
      public void actionPerformed(ActionEvent e)
      {
         String strResult;
         Double dbl1, dbl2, dblResult;

         // pop two items
         /*if (!stk.testStack(2))
            return;*/
         dbl2 = (Double) stk.pop();
         dbl1 = (Double) stk.pop();

         // do the math
         // if (e.getSource()== btnPlus)  // add "if"  for other ops
            dblResult = dbl1 + dbl2;

         // convert from double to  String
         NumberFormat tidy = NumberFormat.getInstance(Locale.US);
         tidy.setMaximumFractionDigits(10);
         strResult = tidy.format(dblResult);


         // write result back to TextA and push
         txtISBN.setText(strResult);
         stk.push(dblResult);
      }
   } // end inner class OpListener

} // end class Calculator