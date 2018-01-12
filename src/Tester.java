/**  
* Tester.java a simple class which creates instances of the Person and Student class, ensuring they are compatible in the same array and can be called
* @author  Sean O'Mahoney - 16042079
* @version 1.0 
* @see Class: Person, Student
*/
public class Tester {

	public static void main(String[] args) {
		Person[] People = new Person[5];
		People[0] = new Person("Bob Billton", "M", "27-04-1995", "N/A", "M9 0PN");
		People[1] = new Person("Robyn Smith", "F", "03-10-1996", "31 Langworthy Road", "M6 1QAA");
		People[2] = new Student("Sean Omahoney", "M", "12-06-1997", "Manchester", "M10 5QA", 5, "Computer Science", "01-10-2016", 9000, "sean@mail.com");
		People[3] = new Person("Eli Jones", "F", "21-09-1993", "31 Johnston St", "M1 2PS");
		People[4] = new Student("Sam Smith", "M", "23-08-1998", "Manchester", "M30 1PM", 19, "Computer Science", "01-10-2016", 1000, "sam@mail.com");
		for (Person s : People) {
		    System.out.println(s.getName() + ": " + s.type());
		}
	}
}
