package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DbConnection {

	public static void main(String[] args) {

		System.out.println(DbConnection.DbConnect());
		
		System.out.println(LocalDate.now());
		
		String dateStr = "2012-02-12 00:05:59";
	
		DateTimeFormatter inputFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate localDateTime = inputFormat.parseLocalDate(dateStr);
		
		DateTimeFormatter outputFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate localDate = outputFormat.parseLocalDate(localDateTime.toString());
		System.out.println(localDateTime);
		System.out.println(localDate);
		
		
		ArrayList<String> list1 = new ArrayList<>();
		ArrayList<String> list2 = new ArrayList<>();
		ArrayList<ArrayList<String>> list3 = new ArrayList<ArrayList<String>>();
		
		list1.addAll(Arrays.asList("a", "b", "c"));
		list2.addAll(Arrays.asList("d", "e", "f"));
		
		System.out.println(list1);
		System.out.println(list2);
		
		
		list3.add(list1);
		System.out.println(list3);
		
		list3.add(list2);
		System.out.println(list3);
		
		String myStr = "Udip Yakkha Rai";
		String[] listStr = myStr.split(" ");
		for (String itemStr : listStr) {
			System.out.println(itemStr);
		}
		System.out.println("Hello" + System.currentTimeMillis());
		
		list1.clear();
		list1.addAll(Arrays.asList("a", "a", "a"));
		System.out.println(list1.size());
		
		
		String a = "b";
		String c = null;
		
		if(a.equals(c) || a.equals("A")) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}

	}

	public static Connection DbConnect() {

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_management_system", "root", "");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return conn;
	}
}
