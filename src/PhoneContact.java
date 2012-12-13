/**
 * <b>PhoneContact class</b>
 * <p>
 * This class represents a phone contact from the database. 
 * <p>
 * The information for these must initially be extracted from a "database"
 * stored in a document.
 * @author James Wen - jrw2175
 */

public class PhoneContact implements Comparable {   
	private String firstName, lastName, phone;

	/**
	 * Constructs a phone contact object with a first name, last name, and phone
	 * number.
	 * @param first - the first name of the contact
	 * @param last - the last name of the contact
	 * @param telephone - the contact's phone number
	 */   
	public PhoneContact (String first, String last, String telephone){
		firstName = first;
		lastName = last;
		phone = telephone;
	}
	/**
	 * <b>toString</b>
	 * <p>
	 * Returns a string representation of the contact's information 
	 * @return string representation of contact's information
	 */ 
   public String toString (){
	   return lastName + ", " + firstName + "\t" + phone + "\n";
   }
	/**
	 * <b>equals</b>
	 * <p>
	 * Checks whether this contact's information is equal to another contact's
	 * information 
	 * @param another object to be compared to
	 * @return whether this contact has the same name info as another contact's
	 */ 
   public boolean equals (Object other){
	   return (lastName.equals(((PhoneContact)other).getLastName()) && 
			   firstName.equals(((PhoneContact)other).getFirstName()));   
   }
	/**
	 * <b>compareTo</b>
	 * <p>
	 * Checks whether this contact's information is equal to, less than, or 
	 * greater than another contact's
	 * @param another object to be compared to 
	 * @return the numerical representation of match or not 
	 */ 
   public int compareTo (Object other){
	  int result;
      String otherFirst = ((PhoneContact)other).getFirstName();
      String otherLast = ((PhoneContact)other).getLastName();
      if (lastName.equals(otherLast)) {
    	  result = firstName.compareTo(otherFirst);
      }
      else {
    	  result = lastName.compareTo(otherLast);
      }
      return result;  
   }
	/**
	 * <b>getFirstName</b>
	 * <p>
	 * Returns the contact's first name
	 * @return the contact's first name 
	 */ 
   public String getFirstName (){
	   return firstName;
   }
	/**
	 * <b>getFirstName</b>
	 * <p>
	 * Returns the contact's last name
	 * @return the contact's last name 
	 */ 
   public String getLastName (){
	   return lastName;
   }
	/**
	 * <b>intpForm</b>
	 * <p>
	 * Returns the phone contact's name's interpolated representation 
	 * <p>
	 * Each character represents a different value and each character place represents
	 * a different base exponent
	 * @return phone contact's interpolated sum representation 
	 */ 
   public double intpForm() {
	   String query = firstName;
	   query = query.toLowerCase();
	   double sum = 0;
	   double value = 0;
	   double basePlace = 0;
	   for (int i = query.length()-1; i >= 0; i--) {
		   sum += ((((double)query.charAt(i))-96) * (Math.pow(26,basePlace)));
		   basePlace++;
	   }
	   return sum;
   }
}//End of PhoneContact Class