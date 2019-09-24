import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ddd  {
	
	  
	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    Date date = new Date();
		System.out.println(dateFormat.format(date));
	}
}