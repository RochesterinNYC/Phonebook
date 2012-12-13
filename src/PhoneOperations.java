/**
 * <b>PhoneOperations class</b>
 * <p>
 * This class contains the static sorting and searching operations that can be 
 * used on a list of phone contacts (or other comparable-implementing objects). 
 * <p>
 * All the methods of this class can be called without instantiating an object
 * of this class.
 * @author James Wen - jrw2175
 */
public class PhoneOperations {

	/**
	 * <b>selectionSort</b>
	 * <p>
	 * Sorts a list of Comparable implementing objects into an ascending order or
	 * descending order through the selection sort method
	 * @param list - the array of Comparable implementing objects
	 * @param ascending - whether the order of sorting is to be ascending or not
	 */    
	 public static void selectionSort (Comparable[] list, boolean ascending){
		int min;
		Comparable temp;
		for (int index = 0; index < list.length-1; index++){
			min = index;
			for (int scan = index+1; scan < list.length; scan++) {
				if(ascending) {
					if (list[scan].compareTo(list[min]) < 0) {
						min = scan;
					}
				}
				else {
					if (list[scan].compareTo(list[min])*-1 < 0) {
						min = scan;
					}
				}
			}
			// Swap the values
			temp = list[min];
			list[min] = list[index];
			list[index] = temp;
		}
	 }
	/**
	 * <b>insertionSort</b>
	 * <p>
	 * Sorts a list of Comparable implementing objects into an ascending order or
	 * descending order through the insertion sort method
	 * @param list - the array of Comparable implementing objects
	 * @param ascending - whether the order of sorting is to be ascending or not
	 */
	 public static void insertionSort (Comparable[] list, boolean ascending){
		 for (int index = 1; index < list.length; index++){
			 Comparable key = list[index];
			 int position = index;
			 // Shift larger values to the right        
			 if(ascending) {
				 while (position > 0 && key.compareTo(list[position-1]) < 0){
					 list[position] = list[position-1];
					 position--;
				 }
			 }
			 else {
				 while (position > 0 && key.compareTo(list[position-1])*-1 < 0){
					 list[position] = list[position-1];
					 position--;
				 }
			 }
				 
	         list[position] = key;
		 }
	 }

	 /**
	  * <b>linearSearch</b>
	  * <p>
	  * Searches through a list of Comparable implementing objects using the linear
	  * search method
	  * @param list - the array of Comparable implementing objects
	  * @param target - the designed query that's being searched for
	  * @return the target query (or null if not found)
	  */
	 public static Comparable linearSearch (Comparable[] list, Comparable target) {
		 int index = 0;
		 boolean found = false;
		 while (!found && index < list.length) {
			 if (list[index].compareTo(target) == 0) {
				 found = true;
			 }
			 else {
				 index++;
			 }
		 }
		 if (found) {
			 return list[index];
		 }
		 else {
			 return null;   
		 }
	 }

	 /**
	  * <b>binarySearch</b>
	  * <p>
	  * Searches through a list of Comparable implementing objects using the binary
	  * search method
	  * @param list - the array of Comparable implementing objects
	  * @param target - the designed query that's being searched for
	  * @return the target query (or null if not found)
	  */
	 public static Comparable binarySearch (Comparable[] list, Comparable target) {
		 int min = 0;
		 int max = list.length-1;
		 int mid = 0;
		 boolean found = false;
		 while (!found && min <= max) {
			 mid = (min+max) / 2;
			 if (list[mid].compareTo(target) == 0) {
				 found = true;
			 }
			 else {
				 if (target.compareTo(list[mid]) < 0) {
					 max = mid-1;
				 }
				 else {
					 min = mid+1;
				 }
			}
		}
	    if (found) {
	    	return list[mid];
	    }
	    else {
	    	return null;   
	    } 
	 }
	 
	 /**
	  * <b>interpolationSearch</b>
	  * <p>
	  * Searches through a list of Comparable implementing objects using the 
	  * interpolation search method
	  * @param list - the array of PhoneContact objects
	  * @param target - the designed query that's being searched for
	  * @return the target query (or null if not found)
	  */
	 public static double interpolationSearch (PhoneContact[] list, PhoneContact target) throws IllegalStateException {
		 // Returns index of target in list, or -1 if not found
		 double start = 0;
		 double end = list[list.length - 1].intpForm();
		 double interval;
		 
		 while (list[(int)start].intpForm() <= target.intpForm() && list[(int)end].intpForm() >= target.intpForm()) {
			 if ((list[(int)end].intpForm() - list[(int)start].intpForm())==0) {
				 throw new IllegalStateException();
			 }
			 
			 interval = start + ((target.intpForm() - list[(int)start].intpForm()) * (end - start)) /
				 (list[(int)end].intpForm() - list[(int)start].intpForm());
			 if (list[(int)interval].intpForm() < target.intpForm()) {
				 start = interval + 1;
			 }
		     else if (list[(int)interval].intpForm() > target.intpForm()) {
		    	 end = interval - 1;
		     }
		     else {
		    	 return interval;
		     } 
		 }
		 if (list[(int)start] == target) {
			 return start;
		 }
		 else {
			 return -1;// Not found
		 }
	 }
}//End of PhoneOperations Class