import java.sql.*;
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
            			System.out.print("Start Location Name: ");
            			String schStartLocationName = scanner.nextLine().trim();
            			
            			System.out.print("Destination Name: ");
            			String schDestinationName = scanner.nextLine().trim();
            			
            			System.out.print("Date 'YYYY-MM-DD': ");
            			String schDate = scanner.nextLine().trim();
            			
            			schedule(schStartLocationName, schDestinationName, schDate);
            			break;
            			
            		case "delete-trip": 
            			System.out.print("Trip#: ");
            			String dtTripNumber = scanner.nextLine().trim();
            			
            			System.out.print("Date 'YYYY-MM-DD': ");
            			String dtDate = scanner.nextLine().trim();

            			System.out.print("Scheduled Start Time: ");
            			String dtScheduledStartTime = scanner.nextLine().trim();
            			
            			deleteTrip(dtTripNumber, dtDate, dtScheduledStartTime);
            			break;
            			
            		case "add-trip": 
            			System.out.print("Trip#: ");
            			String atTripNumber = scanner.nextLine().trim();
            			
            			System.out.print("Date 'YYYY-MM-DD': ");
            			String atDate = scanner.nextLine().trim();
            			
            			System.out.print("Scheduled Start Time: ");
            			String atScheduledStartTime = scanner.nextLine().trim();
            			
            			System.out.print("Scheduled Arrival Time: ");
            			String atScheduledArrivalTime = scanner.nextLine().trim();
            			
            			System.out.print("Driver Name: ");
            			String atDriverName = scanner.nextLine().trim();
            			
            			System.out.print("BusID: ");
            			String atBusID = scanner.nextLine().trim();
            			
            			addTrip(atTripNumber, atDate, atScheduledStartTime, atScheduledArrivalTime, atDriverName, atBusID);
            			break;
            			
            		case "change-driver": 
            			System.out.print("TripNumber: ");
            			String cdTripNumber = scanner.nextLine().trim();
            			
            			System.out.print("Date 'YYYY-MM-DD': ");
            			String cdDate = scanner.nextLine().trim();
            			
            			System.out.print("Scheduled Start Time: ");
            			String cdScheduledStartTime = scanner.nextLine().trim();
            			
            			System.out.print("New Driver: ");
            			String cdNewDriver = scanner.nextLine().trim();
            			
            			changeDriver(cdTripNumber, cdDate, cdScheduledStartTime, cdNewDriver);
            			break;
            			
            		case "change-bus": 
            			System.out.print("TripNumber: ");
            			String cbTripNumber = scanner.nextLine().trim();
            			
            			System.out.print("Date 'YYYY-MM-DD': ");
            			String cbDate = scanner.nextLine().trim();
            			
            			System.out.print("Scheduled Start Time: ");
            			String cbScheduledStartTime = scanner.nextLine().trim();
            			
            			System.out.print("New Bus Number: ");
            			String cbNewBusNumber = scanner.nextLine().trim();
            			
            			changeBus(cbTripNumber, cbDate, cbScheduledStartTime, cbNewBusNumber);
            			break;
            			
            		case "stops": 
            			System.out.print("TripNumber: ");
            			String sTripNumber = scanner.nextLine().trim();
            			
            			stops(sTripNumber);
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

    private static void schedule(String StartLocationName, String DestinationName, String Date) {
    	String query = ("SELECT * " +
    					"FROM TripOffering, Trip " +
    					"WHERE Trip.StartLocationName LIKE '" + StartLocationName + "' AND " +
    					"Trip.DestinationName LIKE '" + DestinationName + "' AND " +
    					"TripOffering.Date = '" + Date + "' AND "+
    					"Trip.TripNumber = TripOffering.TripNumber "+
    					"Order by ScheduledStartTime");
    	
    	try {
			
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData resultCol = result.getMetaData();
			
			for(int i = 1; i<=resultCol.getColumnCount(); i++) {
				System.out.print(resultCol.getColumnName(i) + "\t");
			}
			
			System.out.println(" ");
			
			while(result.next()) {
				for(int i = 1; i<=resultCol.getColumnCount(); i++) {
					System.out.print(result.getString(i)  + "\t");
				}
				System.out.println("");
			}
			
			result.close();
			System.out.println("---------------------------------------------------------------------");
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}

    }
    
    private static void deleteTrip(String TripNumber, String Date, String ScheduledStartTime) {
    	String query = "DELETE TripOffering " + 
    					"FROM TripOffering  " + 
    					"WHERE TripNumber = '" + TripNumber + "' AND " + 
    					"Date = '" + Date + "' AND " +
    					"ScheduledStartTime = '" + ScheduledStartTime + "'";
    	
    	try {
			int result = statement.executeUpdate(query);
			if(result == 0)
				System.out.println("Could not delete the specified trip offering!");
			else
				System.out.println("Sucessfully deleted!");
			System.out.println("---------------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println(e);
		}
    }
    
    private static void addTrip(String TripNumber, String Date, String ScheduledStartTime, String ScheduledArrivalTime, String DriverName, String BusID) {
    	boolean adding = true;
    	while(adding) {
    		String query = "INSERT INTO TripOffering VALUES ('" + TripNumber + "', '" + Date + "', '" + ScheduledStartTime + "', '" + 
    						ScheduledArrivalTime + "', '" + DriverName + "', '" + BusID + "')";
    		try {
				int result = statement.executeUpdate(query);
				System.out.println(result);
			} catch (SQLException e) {
				System.out.println(e);
			}
    		
    		System.out.println("---------------------------------------------------------------------");
    		
    		System.out.print("Want to add another trip offering? (yes/no): ");
    		String input = scanner.nextLine();
    		input = input.trim().toLowerCase();
    		if(input.equals("y") || input.equals("yes")) {
    			System.out.print("Trip#: ");
    			TripNumber = scanner.nextLine().trim();
    			
    			System.out.print("Date 'YYYY-MM-DD': ");
    			Date = scanner.nextLine().trim();
    			
    			System.out.print("Scheduled Start Time: ");
    			ScheduledStartTime = scanner.nextLine().trim();
    			
    			System.out.print("Scheduled Arrival Time: ");
    			ScheduledArrivalTime = scanner.nextLine().trim();
    			
    			System.out.print("Driver Name: ");
    			DriverName = scanner.nextLine().trim();
    			
    			System.out.print("BusID: ");
    			BusID = scanner.nextLine().trim();
    		} else {
    			adding = false;
    		}
    	}
    }

    private static void changeDriver(String TripNumber, String Date, String ScheduledStartTime, String NewDriver) {
    	String query = "UPDATE TripOffering " + 
    					"SET DriverName='" + NewDriver + "' " + 
    					"WHERE TripNumber = '" + TripNumber + "' AND " +
    					"Date = '" + Date + "' AND " + 
    					"ScheduledStartTime = '" + ScheduledStartTime + "'";
    	
    	try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Could not change the specified driver!");
			} else
				System.out.println("Sucessfully deleted!");
			System.out.println("---------------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println(e);
		}
    }

    private static void changeBus(String TripNumber, String Date, String ScheduledStartTime, String NewBusNumber) {
    	String query = "UPDATE TripOffering " + 
				"SET BusID='" + NewBusNumber + "' " + 
				"WHERE TripNumber = '" + TripNumber + "' AND " +
				"Date = '" + Date + "' AND " + 
				"ScheduledStartTime = '" + ScheduledStartTime + "'";

		try {
			int result = statement.executeUpdate(query);
			if(result == 0) {
				System.out.println("Could not change the specified bus ID!");
			} else
				System.out.println("Sucessfully deleted!");
			System.out.println("---------------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println(e);
		}
    }
    
    private static void stops(String TripNumber) {
    	String query = "SELECT * FROM TripStopInfo" + 
    					"WHERE TripNumber = '" + TripNumber + "' " + 
    					"ORDER BY SequenceNumber";
    			
    	try {
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData resultCol = result.getMetaData();
			
			for(int i=1; i<= resultCol.getColumnCount(); i++) {
				System.out.print(resultCol.getColumnName(i)+"\t");
			}
			System.out.println(" ");
			
			while(result.next()) {
				for(int i=1; i<=resultCol.getColumnCount(); i++) {
					System.out.print(result.getString(i) + "\t");
				}
				System.out.println(" ");
			}
			
			result.close();
			System.out.println("---------------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println(e);
		} 
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
