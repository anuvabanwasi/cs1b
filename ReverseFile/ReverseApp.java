package assignment5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ReverseApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
              
       
        ReverserFrame frame = new ReverserFrame();
        frame.setSize(new Dimension(500,700));
        frame.setTitle("Reverse App");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
}   

class ReverserFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton inputPoemBtn = new JButton("Choose Input File");
	private JButton outputPoemBtn = new JButton("Choose Output File");

	private JButton inputMajiBtn = new JButton("Choose Input File");
	private JButton outputMajiBtn = new JButton("Choose Output File");

	private JTextArea reversed = new JTextArea();

	Reverser<String> reversePoem = new Reverser<String>();
	Reverser<Float> reverseMaji = new Reverser<Float>();

	public ReverserFrame() {

		JPanel p0 = new JPanel(new GridLayout(2,1));
		
		// Create first panel
		JPanel p1 = new JPanel(new GridLayout(1, 2));
		// add buttons to first panel
		p1.add(inputPoemBtn);
		p1.add(outputPoemBtn);

		// set the border style for first panel
		p1.setBorder(new TitledBorder("Reverse Poem"));

		// Create second panel
		JPanel p2 = new JPanel(new GridLayout(1, 2));
		// add buttons to second panel
		p2.add(inputMajiBtn);
		p2.add(outputMajiBtn);

		// set the border style for second panel
		p2.setBorder(new TitledBorder("Reverse Maji"));

		// Create third panel
		JPanel p3 = new JPanel(new GridLayout(1, 1));
		reversed.setSize(400, 400);
		p3.add(reversed);
		
		p0.add(p1);
		p0.add(p2);
		
		// add panels to frame
		add(p0, BorderLayout.NORTH);
		add(p3, BorderLayout.CENTER);
		add(new JLabel("Please select and input file, followed by an output file for reversing"), BorderLayout.SOUTH);

		// register listener

		inputPoemBtn.addActionListener(new ButtonListener());
		inputPoemBtn.setActionCommand("inputPoem");

		outputPoemBtn.addActionListener(new ButtonListener());
		outputPoemBtn.setActionCommand("outputPoem");

		inputMajiBtn.addActionListener(new ButtonListener());
		inputMajiBtn.setActionCommand("inputMaji");

		outputMajiBtn.addActionListener(new ButtonListener());
		outputMajiBtn.setActionCommand("outputMaji");

		outputPoemBtn.setEnabled(false);
		outputMajiBtn.setEnabled(false);

	}

	// Handle button action
	private class ButtonListener implements ActionListener {
		String inputPath = "";
		String outputPath = "";

		public void actionPerformed(ActionEvent evt) {

			String cmd = evt.getActionCommand();
			if (cmd.equals("inputPoem")) {

				inputMajiBtn.setEnabled(false);
				outputMajiBtn.setEnabled(false);
				reversed.setText("");

				File file = chooseFile();
				if (file != null) {
					inputPath = file.getAbsolutePath();
					reversePoem.fileToStack(inputPath);
					outputPoemBtn.setEnabled(true);
				}
			} else if (cmd.equals("outputPoem")) {
				File file = chooseFile();

				if (file != null) {
					outputPath = file.getAbsolutePath();
					reversePoem.stackToFile(outputPath);
					populateTextArea();

					inputMajiBtn.setEnabled(true);
					outputMajiBtn.setEnabled(true);
				}
			} else if (cmd.equals("inputMaji")) {
				inputPoemBtn.setEnabled(false);
				outputPoemBtn.setEnabled(false);
				reversed.setText("");

				File file = chooseFile();

				if (file != null) {
					inputPath = file.getAbsolutePath();
					reverseMaji.fileToStack(file.getAbsolutePath());
					outputMajiBtn.setEnabled(true);
				}
			} else if (cmd.equals("outputMaji")) {
				File file = chooseFile();

				if (file != null) {
					outputPath = file.getAbsolutePath();
					reverseMaji.stackToFile(outputPath);

					populateTextArea();

					inputPoemBtn.setEnabled(true);
					outputPoemBtn.setEnabled(true);
				}
			}
		}

		private void populateTextArea() {
			File sourceFile = new File(outputPath);
			Scanner input;
			try {
				input = new Scanner(sourceFile);
				while (input.hasNext()) {
					reversed.append(input.nextLine());
					reversed.append("\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		private File chooseFile() {

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File inputFile = fileChooser.getSelectedFile();
				return inputFile;
			}
			return null;
		}
	}
}

class Reverser <E> {
	private Stack<E> stack;
	
	Reverser(){
		stack = new Stack<E>();
	}
	
	public void fileToStack(String fileName){
		// read file and push to stack		
		try {
			BufferedReader reader = new BufferedReader(  new FileReader(fileName) );
			
			for (int k = 0; reader.ready(); k++ ) {
	            stack.push( (E) reader.readLine());
			}

	         reader.close();
	         
		} catch (FileNotFoundException e) {
			System.out.println("\nOops. Problem opening the file!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("\nOops. Problem with file I/O!");
		}
	}
	
	public void stackToFile(String fileName){
		// pop from stack and push to file
		try {
			PrintWriter writer = new PrintWriter(fileName );
			while ( !stack.isEmpty() ) {
				String s = (String) stack.pop();
				writer.println((E)s);
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("\nOops. Problem opening the file!");
			e.printStackTrace();
		}
		
	}
	
}


//Stack
class Stack<E>
{
 // pointer to first node in stack
 private Node<E> top;
 
 // constructor
 public Stack()
 {
    top = null;
 }
 
 public boolean isEmpty()
 {
    return top == null;
 }
 
 public void push(E data)
 {   
    if (data == null) 
       return;
    // build a node and place it on the stack
    Node<E> newNode = new Node<E>(data);
    newNode.next = top;
    top = newNode;
 }  
 
 public E pop()
 {
    Node<E> temp;
    
    temp = top;
    if (isEmpty())
       return null;

    top = top.next; 
    return temp.getData();     
 }

 // console display
 public String toString()
 {
    Node<E> p;
    
    String showAll = "";
    // Display all the nodes in the stack
    for( p = top; p != null; p = p.next )
       showAll += p.toString() + "\n";
    return showAll;  
 }
}

//Node
class Node<E>
{
   public Node<E> next;
   private E data;
    
   // constructor
   public Node()
   {
      next = null;
   }
   public Node(E data)
   {
      setData(data);
   }
  
   public boolean setData(E data)
   {
      if (data == null)
         return false;
      this.data = data;
      return true;
   }
   
   public E getData()
   {
      return data;
   }
   
   // console display
   public String toString()
   {
      return data.toString();
   }
}