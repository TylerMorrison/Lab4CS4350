package de.lab4.mysql.first;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Main {
	private static Connection connect = null;
    private static Statement statement = null;
    private static Scanner scanner = new Scanner(System.in);
    private static String input = "";

    public static void main(String[] args) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost/lab4?"+"user=User&password=Password");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            
            showOptions();
            
            while(true) {
            	System.out.print("Command: ");
            	input = scanner.nextLine();
            	switch(input.toLowerCase().trim()) {
            		case "schedule": 
            			schedule();
            			break;
            			
            		case "delete-trip": 
            			deleteTrip();
            			break;
            			
            		case "add-trip": 
            			addTrip();
            			break;
            			
            		case "change-driver": 
            			changeDriver();
            			break;
            			
            		case "change-bus": 
            			changeBus();
            			break;
            			
            		case "stops": 
            			stops();
            			break;
            			
            		case "weekly-schedule": 
            			weeklySchedule();
            			break;
            			
            		case "add-drive": 
            			addDrive();
            			break;
            			
            		case "add-bus": 
            			addBus();
            			break;
            			
            		case "delete-bus": 
            			deleteBus();
            			break;
            			
            		case "insert-trip": 
            			insertTrip();
            			break;
            			
            		default:
            			System.out.println("Improper input command");
            			showOptions();
            	}
            }

        } catch (Exception e) {
            throw e;
        } finally {
            scanner.close();
        }

    }
    
    private static void showOptions() {
    	System.out.println("Available commands");
    	System.out.println("------------------");
    	System.out.println("schedule - "); //write description
    	System.out.println("delete-trip - ");
    	System.out.println("add-trip - ");
    	System.out.println("change-driver - ");
    	System.out.println("change-bus - ");
    	System.out.println("stops - ");
    	System.out.println("weekly-schedule - ");
    	System.out.println("add-drive - ");
    	System.out.println("add-bus - ");
    	System.out.println("delete-bus - ");
    	System.out.println("insert-trip - ");
    }

    private static void schedule() {

    }
    
    private static void deleteTrip() {
    	
    }
    
    private static void addTrip() {
    	
    }

    private static void changeDriver() {
    	
    }

    private static void changeBus() {
    	
    }
    
    private static void stops() {
    	
    }
    
    private static void weeklySchedule() {
    	
    }
    
    private static void addDrive() {
    	
    }
    
    private static void addBus() {
    	
    }
    
    private static void deleteBus() {
    	
    }
    
    private static void insertTrip() {
    	
    }
    
    
}
