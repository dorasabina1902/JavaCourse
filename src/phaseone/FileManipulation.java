package phaseone;

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Path;
import java.util.regex.*;

public class FileManipulation {

	boolean found = false;
	
	  public void findFile(String name,File file)
	    {
	        File[] list = file.listFiles();
	        if(list!=null)
	        for (File fil : list)
	        {
	            if (fil.isDirectory())
	            {
	                findFile(name,fil);
	            }
	            else if (name.equalsIgnoreCase(fil.getName()))
	            {
	                System.out.println("Found in " + fil.getParentFile());
	                break;
	            }
	            	
	            	System.out.println("Not the same as " + fil.getName());
	        }
	    }
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		System.out.println(" ---- WELCOME TO FILE MANIPULATION APP ----");
		System.out.println("Developer: Gheorghe Teodora Sabina");
		System.out.println("Choose your option from the following menu:");
		System.out.println("1. Retrieving the file names in an asc order -> write ASC");
		System.out.println("2. Add a user specified file from the application -> write ADD");
		System.out.println("3. Delete a user specified file from the application -> write DELETE");
		System.out.println("4. Search a user specified file from the application -> write SEARCH");
		System.out.println("5. Close the application -> write CLOSE");
		System.out.println(" ---- YOUR OPTION ---- ");

		Scanner scan = new Scanner(System.in);

		String optionChosen = scan.next();
		System.out.println(optionChosen);

		switch(optionChosen) {
		case "ASC":
			System.out.println(" ||| SORT FILES IN ASCENDING ORDER IN A SPECIFIED DIRECTORY ||| ");
			Scanner readerAsc = new Scanner(System.in);
			boolean successAsc = false;
			System.out.println("Enter path of directory to sort:"); 
			String dirAsc = readerAsc.nextLine();
			File folder = new File(dirAsc);
			if (folder.isDirectory()) {
				File[] fileList = folder.listFiles();
				Arrays.sort(fileList);
				for (File file:fileList) {
					System.out.println(file.getName());
				}
			}
			readerAsc.close();
			break;
		case "ADD":
			System.out.println(" ||| ADDING A NEW FILE TO A SPECIFIED DIRECTORY ||| ");
			Scanner reader = new Scanner(System.in);
			boolean success = false;
			System.out.println("Enter path of directory to create:"); 
			String dir = reader.nextLine();

			System.out.println("Enter file name to be created "); 
			String filename = reader.nextLine(); 
			File f = new File(filename); 
			if (f.exists()) { 
				System.out.println("File already exists"); } 
			else { 
				System.out.println("No such file exists, creating now"); 
				success = f.createNewFile(); 
				if (success) { 
					System.out.printf("Successfully created new file: %s%n", f); 
					} 
					else { 
						System.out.printf("Failed to create new file: %s%n", f); 
						} 
			} 
			reader.close();
			break;
		case "DELETE":
			System.out.println("||| DELETE FILE FROM A SPECIFIED DIRECTORY |||");
			Scanner reader2 = new Scanner(System.in);
			System.out.println("Enter path of directory to delete:"); 
			String dir2 = reader2.nextLine();
			System.out.println("Enter file name to be deleted "); 
			String filename2 = reader2.nextLine(); 
			dir2 = dir2 + "\\" + filename2;
			String path = FileSystems.getDefault().getPath(dir2).toString();
			//Files.delete(path);
			File file = new File(path);
			if (file.exists()) {
			    file.delete();
			    System.out.println("File deleted!");
			} else {
			    System.err.println(
			        "I cannot find '" + file + "' ('" + file.getAbsolutePath() + "')");
			}
			reader2.close();
			break;
		case "SEARCH":
			System.out.println("||| SEARCH FOR A FILE FROM A SPECIFIED DIRECTORY |||");
			Scanner reader3 = new Scanner(System.in);
			System.out.println("Enter path of directory to search in:"); 
			String dir3 = reader3.nextLine();
			System.out.println("Enter file name to be searched "); 
			String filenameToSearch = reader3.nextLine(); 
			FileManipulation ff = new FileManipulation();
			ff.findFile(filenameToSearch,new File(dir3));
			reader3.close();
			break;
		case "CLOSE":
			System.out.println(" --- APPLICATION CLOSED --- ");
			System.exit(0);
			break;
		default:
		        System.out.println("ERROR: Invalid Selection");
		}
			scan.close();

	}

}
