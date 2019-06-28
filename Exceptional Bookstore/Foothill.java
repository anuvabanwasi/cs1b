package assignment4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Foothill {
	public static void main(String[] args){
		BookStoreFrame myFrame = new BookStoreFrame("Book Store");
		myFrame.setSize(500, 300);
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
		// show everything to the user
		myFrame.setVisible(true);
	}
}

class BookStoreFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblInstructions;
	private JPanel pnlInput;
	private JPanel pnlButtons;

	private JTextField txtISBN;
	private JTextField txtTitle;
	private JTextField txtLastName;
	private JTextField txtPrice;

	private JLabel lblISBN;
	private JLabel lblTitle;
	private JLabel lblLastName;
	private JLabel lblPrice;

	private JButton btnSave;
	private JButton btnReset;

	private BookStore bookStore;

	public BookStoreFrame(String title) {

		// pass the title up to the JFrame constructor
		super(title);

		NumberFormat amtFormat = NumberFormat.getNumberInstance();
		amtFormat.setMaximumFractionDigits(2);
		 
		// set up main components for the JFrame
		lblInstructions = new JLabel("Please enter book details");
		pnlInput = new JPanel(new GridLayout(4, 2, 10, 10));
		pnlButtons = new JPanel(new GridLayout(1, 2, 20, 20));

		// use border layout and put three above components in frame
		setLayout(new BorderLayout(20, 10));

		add(pnlInput, BorderLayout.NORTH);
		add(pnlButtons, BorderLayout.CENTER);
		add(lblInstructions, BorderLayout.SOUTH);

		// set up the components for the pnlTextEnter Panel
		btnSave = new JButton("Save");
		btnReset = new JButton("Reset");

		txtISBN = new JTextField("", 15);
		txtTitle = new JTextField("", 15);
		txtLastName = new JTextField("", 15);
		txtPrice = new JTextField("", 15);

		lblISBN = new JLabel("ISBN");
		lblTitle = new JLabel("Title");
		lblLastName = new JLabel("Author Last Name");
		lblPrice = new JLabel("Price");

		// add components to pnlTextEnter Panel
		pnlInput.add(lblISBN);
		pnlInput.add(txtISBN);

		pnlInput.add(lblTitle);
		pnlInput.add(txtTitle);

		pnlInput.add(lblLastName);
		pnlInput.add(txtLastName);

		pnlInput.add(lblPrice);
		pnlInput.add(txtPrice);

		// set up the components for the pnlButtons Panel

		// add components to buttons Panel
		pnlButtons.add(btnSave);
		pnlButtons.add(btnReset);

		// place borders around the panels
		pnlInput.setBorder(new TitledBorder("Book Information"));
		pnlButtons.setBorder(new TitledBorder("Save & Cancel"));

		// register reset listeners (for doing the clear)
		ActionListener resetListener = new ResetListener();

		// register Save and Reset listeners
		ActionListener enterListener = new EnterListener();
		btnSave.addActionListener(enterListener);
		btnReset.addActionListener(resetListener);

		bookStore = new BookStore();
	}

	// inner class for btnEnter and txtNumber Listener
	class EnterListener implements ActionListener {
		// event handler for JButton or Enter Key
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == btnSave) {
				try {
					
					String isbn = txtISBN.getText();
					String title = txtTitle.getText();
					String lastName = txtLastName.getText();
					String price = txtPrice.getText();
						
					Book book = new Book();
					book.setISBN(isbn);
					book.setTitle(title);
					book.setLastName(lastName);
					double amount = Double.parseDouble(price);

					book.setPrice(amount);
					
					if(bookStore.addBook(book)) {
						lblInstructions.setForeground(Color.BLUE);
						lblInstructions.setText("Success! Added book to book store");
						
						JOptionPane.showMessageDialog(null, "Success! Added book " + book  );
						
					} else {
						lblInstructions.setForeground(Color.RED);
						lblInstructions.setText("Unable to add book to book store");
					}

				} catch(InvalidInputException exception){
					lblInstructions.setForeground(Color.RED);
					lblInstructions.setText(exception.getMessage());
					if(exception.getMessage().contains("ISBN"))
						txtISBN.requestFocus();
					else if(exception.getMessage().contains("Title"))
						txtTitle.requestFocus();
					else
						txtLastName.requestFocus();
					
				} catch (NumberFormatException exception) {
					lblInstructions.setForeground(Color.RED);
					lblInstructions.setText("Please enter a valid price");
					txtPrice.requestFocus();
				} catch (OutOfRangeException exception) {
					lblInstructions.setForeground(Color.RED);
					lblInstructions.setText(exception.getMessage());
					txtISBN.requestFocus();
				} catch (InvalidISBN13Exception exception) {
					lblInstructions.setForeground(Color.RED);
					lblInstructions.setText(exception.getMessage());
					txtISBN.requestFocus();
				}
			}
		}
	} // end inner class EnterListener

	// inner class for ResetListener
	class ResetListener implements ActionListener {
		// event handler for JButton or Reset Key
		public void actionPerformed(ActionEvent e) {

			txtISBN.setText("");
			txtTitle.setText("");
			txtLastName.setText("");
			txtPrice.setText("");

			txtISBN.requestFocus();

		}
	} // end inner class ResetListener

	class PriceListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			format();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		public void format() {
			try {
				
				String dblPrice = txtPrice.getText();
				
				double amount = Double.parseDouble(dblPrice);

				NumberFormat tidy = NumberFormat.getInstance(Locale.US);
				tidy.setMaximumFractionDigits(2);
				txtPrice.setText(tidy.format(amount));
			} catch (NumberFormatException exception) {
				lblInstructions.setText("Please enter a valid price");
				lblInstructions.setForeground(Color.RED);
			}
		}
	}
}

class InvalidInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidInputException(String str) {
		super(str);
	}

}


class InvalidISBN13Exception extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidISBN13Exception(String str) {
		super(str);
	}

}


class OutOfRangeException extends Exception{

	public OutOfRangeException(String str) {
		super(str);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}


class BookStore {
	private List<Book> books;

	BookStore(){
		books = new ArrayList<Book>();
	}
	
	public boolean addBook(Book book){
		System.out.println("adding book to the book store ");
		
		boolean added = books.add(book);
		
		if(added)
			return true;
		else
			return false;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Bookstore [books=" + books + "]";
	}
}


class Book {
	
	private String isbn;
	private String title;
	private String lastName;
	private double price;
	
	Book(){ }
	
	Book(String isbn, String title, String lastName, double price) throws OutOfRangeException, InvalidInputException, InvalidISBN13Exception{
	
		setISBN(isbn);
		setTitle(title);
		setLastName(lastName);
		setPrice(price);
	}
	
	public String getISBN() throws IOException {
		   
		   return isbn;
	}
	
	public void setISBN(String ISBN) throws OutOfRangeException, InvalidISBN13Exception, InvalidInputException {
		
		String isbn = "";
		
		if(ISBN == null || ISBN.length() == 0)
			throw new InvalidInputException("Please enter a valid ISBN");
		
		for(int i = 0 ; i < ISBN.length(); i++){
			if(!Character.isDigit(ISBN.charAt(i)) && ISBN.charAt(i) != '-')
				throw new OutOfRangeException("ISBN must have 10 or 13 numeric digits");
			else if(Character.isDigit(ISBN.charAt(i)) || ISBN.charAt(i) == '-'){
				isbn = isbn + ISBN.charAt(i);
			}
		}
		
		String isbnWithoutDashes = getIsbnWithoutDashes(isbn);
		
		if (isbnWithoutDashes.length() < 10)
			throw new OutOfRangeException("ISBN is smaller than 10 digits. Please enter an ISBN of 10 digits or 13 digits");
		else if (isbnWithoutDashes.length() == 11 || isbnWithoutDashes.length() == 12)
			throw new OutOfRangeException("ISBN must have 10 or 13 numeric digits");
		else if (isbnWithoutDashes.length() > 13)
			throw new OutOfRangeException("ISBN is larger than 13 digits. Please enter an ISBN of 10 digits or 13 digits");
		
		if(isbnWithoutDashes.length() == 13) {
			
			if(!isValidIsbn13(isbn))
				throw new InvalidISBN13Exception("First 3 digits of a 13 digit ISBN are invalid (must be either 978 or 979)");
		}
		
		this.isbn = isbn;
		
	}	
	
	private String getIsbnWithoutDashes(String isbn) {
		return isbn.replace("-", "");
	}

	private boolean isValidIsbn13(String isbn) {
		if(isbn.startsWith("978") || isbn.startsWith("979"))
			return true;
		else 
			return false;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) throws InvalidInputException{
		if(title == null || title.length() == 0 || title == "")
			throw new InvalidInputException("Title must have at least 1 character");
		this.title = title;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) throws InvalidInputException {
		
		if(lastName == null || lastName.length() == 0 || lastName == "")
			throw new InvalidInputException("Author last name must have at least 1 character");
		
		this.lastName = lastName;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price){
		
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Book [ISBN=" + isbn + ", title=" + title + ", lastName=" + lastName + ", price=" + price + "]";
	}
}
