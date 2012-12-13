import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;

/**
 * <b>PhonePanel class</b>
 * <p>
 * Creates a GUI Panel that allows a user to load a PhoneContact database from a 
 * document and perform sorting and search functions on it.
 * @author James Wen - jrw2175
 */
public class PhonePanel extends JPanel {
	private PhoneContact[] friends = new PhoneContact[8];
	private JButton[] sortButtons = new JButton[4];
	private JButton activateSearch;
	//Sort Method Buttons
	private JButton selSortAsc;
	private JButton selSortDes; 
	private JButton insSortAsc;
	private JButton insSortDes;
	//Search Instructions
	private JLabel searchInstructions1;
	private JLabel searchInstructions2;
	private JLabel searchInstructions3;
	//Result Areas
	private JTextArea dataArea;      
	private JTextArea searchResult;
	private JScrollPane listDetails;
	
	private JFileChooser chooser;
	private String info = "";
	private JComboBox searchOptions;
	private String currentSearch;
	private JTextField queryFirstName;
	private JTextField queryLastName;
	//Main Component Panels
	private JPanel sortArea;
	private JPanel listArea;
	private JPanel searchResults;
	private JPanel searchQuery;
	/**
	 * Sets up the panel and configures the mouse and key (for mnemonics) listeners.
	 * @throws IOException - to catch IO errors
	 */ 
	public PhonePanel() throws IOException{
		//Component Creation
		selSortAsc = new JButton ("Selection Sort Ascending");
		selSortDes = new JButton ("Selection Sort Descending");
		insSortAsc = new JButton ("Insertion Sort Ascending");
		insSortDes = new JButton ("Insertion Sort Descending");
		activateSearch = new JButton ("Initiate Search.");
		String[] optionNames = {"Select a search method.", "Use Linear Search", 
								"Use Binary Search", "Use Interpolation Search"};
		searchOptions = new JComboBox(optionNames);
		searchOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		dataArea = new JTextArea (20, 30);
	    searchResult = new JTextArea(10, 10);
		queryFirstName = new JTextField("Search by first name.");
		queryLastName = new JTextField("Search by last name.");
		sortArea = new JPanel();
		listArea = new JPanel();
		searchResults = new JPanel();
		searchQuery = new JPanel();
		searchInstructions1 = new JLabel("Please enter both the first and last name");
		searchInstructions2 = new JLabel("for the person you wish to search for.");		
		searchInstructions3 = new JLabel("Proper capitalization is required.");	
		
		//Setting up the Sort Buttons (Mnemonics, order, text, listeners, etc.)
		sortButtons[0] = selSortAsc;
		sortButtons[1] = selSortDes;
		sortButtons[2] = insSortAsc;
		sortButtons[3] = insSortDes;
		selSortAsc.setMnemonic('s');
		selSortDes.setMnemonic('e');
		insSortAsc.setMnemonic('i');
		insSortDes.setMnemonic('n');
		selSortAsc.setToolTipText("Sort through selection sort into an ascending order.");
		selSortDes.setToolTipText("Sort through selection sort into an descending order.");
		insSortAsc.setToolTipText("Sort through insertion sort into an ascending order.");
		insSortDes.setToolTipText("Sort through insertion sort into an descending order.");
		selSortAsc.addActionListener(new selSortAscListener());
		selSortDes.addActionListener(new selSortDesListener());
		insSortAsc.addActionListener(new insSortAscListener());
		insSortDes.addActionListener(new insSortDesListener());
		searchOptions.addActionListener(new searchListener());
		queryFirstName.addActionListener(new firstNameListener());
	    queryLastName.addActionListener(new lastNameListener());
	    activateSearch.addActionListener(new searchOperator());
	    
	    //Selecting and parsing the document with PhoneContact database
	    parseFile();  
	    updateList();
	    dataArea.setText(info);	      
	    listDetails = new JScrollPane (dataArea);
	    listDetails.setPreferredSize (new Dimension (200,200));
	    
	    //Formatting the Components	    		
		GridLayout grid = new GridLayout (2,2);
		setLayout (grid);
	    listArea.add(listDetails);
	    searchQuery.add(searchOptions);
	    searchQuery.add(searchInstructions1);
	    searchQuery.add(searchInstructions2);
	    searchQuery.add(searchInstructions3);
	    searchQuery.add(queryFirstName);
	    searchQuery.add(queryLastName);
	    searchQuery.add(activateSearch);
	    sortArea.add(selSortAsc);
	    sortArea.add(selSortDes);
	    sortArea.add(insSortAsc);
	    sortArea.add(insSortDes);
	    searchResult.setText("Search Results:");
	    searchResults.add(searchResult);

	    //Setting Borders
	    sortArea.setBorder(BorderFactory.createLineBorder (Color.red, 1));
	    listArea.setBorder(BorderFactory.createLineBorder (Color.red, 1));
	    searchQuery.setBorder(BorderFactory.createLineBorder (Color.red, 1));
	    searchResults.setBorder(BorderFactory.createLineBorder (Color.red, 1));
	      
	    //Adding the main components
	    add(sortArea);
		add(listArea);
	    add(searchQuery);
	    add(searchResults);
	    
		setPreferredSize (new Dimension(550, 430));      
	    setBackground (Color.white);
	    setFocusable(true);
	   }
	
	   /**
	    * <b>parseFile</b>
	    * <p>
	    * Lets the user select the file that contains the PhoneContact database
	    * and parses it for the PhoneContact data
	    * @throws NullPointerException - in case file is not chosen
	    */
	   private void parseFile() throws NullPointerException, IOException{
		  //Makes user select the file
		  chooser = new JFileChooser();
	      int status = chooser.showOpenDialog (null);
		  File file = chooser.getSelectedFile();
		  //Scanner to read the file contents
		  Scanner scan = new Scanner(file);
	
		  int currentIndex = 0;
	      String firstName = "";
	      String lastName = "";
	      String phoneNumber = ""; 
		  scan.useDelimiter(",");	  
		  if (status != JFileChooser.APPROVE_OPTION) {
			  dataArea.setText ("No File Chosen");
	      }
	      else{
	      	while (scan.hasNext()) {//Parses the file contents and makes PhoneContacts
	      		lastName = scan.next();
	      		lastName = lastName.replaceAll("\n", "");
	      		firstName = scan.next();
	      		firstName = formattedString(firstName, true);
	      		scan.useDelimiter("\n");
	      		phoneNumber = (scan.next());
	      		phoneNumber = formattedString(phoneNumber, false);
	      		scan.useDelimiter(",");
	        		
	      		friends[currentIndex] = new PhoneContact (firstName, lastName, phoneNumber); 
	      		currentIndex++;
	      	}	
	      }
	   }
	
	   /**
	    *<b>updateList</b> 
		* Updates the PhoneContact data area with the file's parsed contents
		*/
	   public void updateList() {
		   info = "";
		   for (int i = 0; i < friends.length; i++) {
		      	info += friends[i].toString();
		      }
		   dataArea.setText(info); 
	   }

	   /**
	    *<b>formattedString</b> 
		* Formats the strings in the file contents properly
		* @param query - string to be formatted
		* @param name - whether the query is a name or not
		* @return formattedQuery - the formatted query
		*/
	   private static String formattedString(String query, boolean name) {
		   String formattedQuery = query;
		   if(name) {//if query is name
			   formattedQuery = formattedQuery.replaceFirst(" ", "");
			   formattedQuery = formattedQuery.replaceAll("\n", "");
		   }
		   else {//if query is a phone number
			   formattedQuery = formattedQuery.replaceFirst(", ", "");
			   formattedQuery = formattedQuery.replaceAll("\n", "");
		   }	
		   return formattedQuery;
	   }

	   /**
	    *<b>selSortAscListener class</b> 
		* An ActionListener that initiates an ascending Selection Sort on the 
		* parsed data when the Ascending Selection Sort button is clicked
		*/
	   private class selSortAscListener implements ActionListener{
		  public void actionPerformed(ActionEvent event) {
			  PhoneOperations.selectionSort(friends, true);
			  inactivateButtons(0);
			  updateList();
		  }
	   }
	   /**
	    *<b>selSortDesListener class</b> 
		* An ActionListener that initiates a descending Selection Sort on the 
		* parsed data when the Descending Selection Sort button is clicked
		*/
	   private class selSortDesListener implements ActionListener{
		  public void actionPerformed(ActionEvent event) {
			  PhoneOperations.selectionSort(friends, false);
			  inactivateButtons(1);
			  updateList();
		  } 
	   }
	   /**
	    *<b>insSortAscListener class</b> 
		* An ActionListener that initiates an ascending Insertion Sort on the 
		* parsed data when the Ascending Insertion Sort button is clicked
		*/
	   private class insSortAscListener implements ActionListener{
		  public void actionPerformed(ActionEvent event) {
			  PhoneOperations.insertionSort(friends, true);
			  inactivateButtons(2);
			  updateList();
		  }
	   }
	   /**
	    *<b>insSortDesListener class</b> 
		* An ActionListener that initiates a descending Insertion Sort on the 
		* parsed data when the Descending Insertion Sort button is clicked
		*/
	   private class insSortDesListener implements ActionListener{
		  public void actionPerformed(ActionEvent event) {
			  PhoneOperations.insertionSort(friends, false); 
			  inactivateButtons(3);
			  updateList();
		  }		   
	   }
	   /**
	    *<b>searchListener class</b> 
		* An ActionListener that determines which search method is to be used for
		* search queries
		*/
	   private class searchListener implements ActionListener{
		   public void actionPerformed(ActionEvent event) {
			   if(searchOptions.getSelectedIndex() == 1) {
				   currentSearch = "linear";
			   }
			   if(searchOptions.getSelectedIndex() == 2) {
				   currentSearch = "binary";
			   }
			   if(searchOptions.getSelectedIndex() == 3) {
				   currentSearch = "interpolation";
			   }
		   }
	   }
	   /**
	    *<b>firstNameListener class</b> 
		* An ActionListener that determines which what the query's first name is
		*/
	   private class firstNameListener implements ActionListener{
		   public void actionPerformed(ActionEvent event) {
			   String query = queryFirstName.getText();
		   }
	   }
	   /**
	    *<b>lastNameListener class</b> 
		* An ActionListener that determines which what the query's last name is
		*/
	   private class lastNameListener implements ActionListener{
		   public void actionPerformed(ActionEvent event) {
			   String query = queryLastName.getText();
		   }
	   }
	   /**
	    *<b>searchOperator class</b> 
		* An ActionListener that initiates a search with the query names of what is
		* currently in the first and last name fields
		*/
	   private class searchOperator implements ActionListener{
		   public void actionPerformed(ActionEvent event) {
			   String firstName = queryFirstName.getText();
			   String lastName = queryLastName.getText();
			   operateSearch(firstName, lastName);
		   }
	   }
	   /**
	    *<b>operateSearch</b> 
		* Starts the search with the desired query
		* @param firstName - the query's first name
		* @param lastName - the query's last name 
		*/
	   private void operateSearch(String firstName, String lastName){
		   PhoneContact targetQuery = performSearch(firstName, lastName, currentSearch);
		   if(targetQuery != null) {
			   searchResult.setText("Search Results: " + targetQuery.toString());
		   }
		   else {
			   searchResult.setText("Search Results: Query Not Found");
		   }
	   }
	   /**
	    *<b>performSearch/b> 
		* Operates the search with the desired query and chosen method and produces
		* the matched query target or indicates that there are no matches
		* @param firstName - the query's first name
		* @param lastName - the query's last name
		* @param searchType - the desired search method
		* @return targetContact - the desired target contact
		*/
	   private PhoneContact performSearch(String firstName, String lastName, String searchType) {
		   PhoneContact targetContact;
		   targetContact = new PhoneContact(firstName, lastName, "");
		   PhoneContact foundContact = new PhoneContact("","","");
		   if(currentSearch.equals("linear")) {
			   foundContact = (PhoneContact)PhoneOperations.linearSearch(friends, targetContact);
		   }
		   if(currentSearch.equals("binary")) {
			   foundContact = (PhoneContact)PhoneOperations.binarySearch(friends, targetContact);
		   }
		   if(currentSearch.equals("interpolation")) {	   
		   }
		   return foundContact;
	   }
	   /**
	    *<b>inactivateButtons</b> 
		* Deactivates all the sort buttons except for the one that was clicked
		* @param exception - the index of the sort button that was clicked
		*/
	   private void inactivateButtons(int exception) {
		   for (int k = 0; k < sortButtons.length; k++) {
			   if(k != exception) {
				   sortButtons[k].setEnabled(false);
			   }
		   }
	   }
}//End of PhonePanel Class