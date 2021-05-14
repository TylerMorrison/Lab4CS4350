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
            			System.out.print("DriverName: ");
            			String wsDriverName = scanner.nextLine().trim();
            			
            			System.out.print("Date 'YYYY-MM-DD': ");
            			String wsDate = scanner.nextLine().trim();
            			
            			weeklySchedule(wsDriverName, wsDate);
            			break;
            			
            		case "add-drive":
            			System.out.print("DriverName: ");
            			String adDriverName = scanner.nextLine().trim();
            			
            			System.out.print("DriverTelephoneNumber: ");
            			String adTelephone = scanner.nextLine().trim();
            			
            			addDrive(adDriverName, adTelephone);
            			break;
            			
            		case "add-bus": 
            			System.out.print("BusID: ");
            			String abBusID = scanner.nextLine().trim();
            			
            			System.out.print("Model: ");
            			String abModel = scanner.nextLine().trim();
            			
            			System.out.print("Year: ");
            			String cbYear = scanner.nextLine().trim();
            			
            			addBus(abBusID, abModel, cbYear);
            			break;
            			
            		case "delete-bus": 
            			System.out.print("BusID: ");
            			String dbBusID = scanner.nextLine();
            			
            			deleteBus(dbBusID);
            			break;
            			
            		case "insert-trip":
            			System.out.print("TripNumber: ");
            			String itTripNum = scanner.nextLine().trim();
            			
            			System.out.print("Date 'YYYY-MM-DD': ");
            			String itDate = scanner.nextLine().trim();
            			
            			System.out.print("Scheduled Start Time: ");
            			String itStart = scanner.nextLine().trim();
            			
            			insertTrip(itTripNum, itDate, itStart);
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
				System.out.format("%25s", resultCol.getColumnName(i));
			}
			
			System.out.println(" ");
			
			while(result.next()) {
				for(int i = 1; i<=resultCol.getColumnCount(); i++) {
					System.out.format("%25s", result.getString(i));
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
				if(result==0)
					System.out.println("Could not add the trip into the database.");
				else
					System.out.println("Successfully entered!");
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
    	String query = "SELECT * FROM TripStopInfo " + 
    					"WHERE TripNumber = " + TripNumber;
    			
    	try {
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData resultCol = result.getMetaData();
			
			for(int i=1; i<= resultCol.getColumnCount(); i++) {
				System.out.format("%20s", resultCol.getColumnName(i)+"\t");
			}
			System.out.println(" ");
			
			while(result.next()) {
				for(int i=1; i<=resultCol.getColumnCount(); i++) {
					System.out.format("%20s", result.getString(i) + "\t");
				}
				System.out.println(" ");
			}
			
			result.close();
			System.out.println("---------------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println(e);
		} 
    }
    
    private static void weeklySchedule(String driverName, String date) {
    	String query = "SELECT Date, ScheduledStartTime, ScheduledArrivalTime " +
    					"FROM TripOffering WHERE DriverName = '" + driverName + 
    					"' AND Date BETWEEN '" + date + "' AND DATE_ADD('" + date + "', INTERVAL 7 DAY)";
    	try {
    		ResultSet result = statement.executeQuery(query);
    		ResultSetMetaData resultCol = result.getMetaData();
    		
    		for (int i=1; i<=resultCol.getColumnCount(); i++) {
    			System.out.format("%25s", resultCol.getColumnName(i));
    		}
    		System.out.println(" ");
    		
    		while (result.next()) {
    			for(int i=1; i<=resultCol.getColumnCount(); i++) {
    				System.out.format("%25s", result.getString(i));
    			}
    			System.out.println(" ");
    			System.out.println("---------------------------------------------------------------------");
    		}
    		
    		result.close();
    	} catch (SQLException e) {
    		System.out.println(e);
    	}
    }
    
    private static void addDrive(String driverName, String telephone) {
	    	String query = "INSERT INTO Driver (DriverName, DriverTelephoneNumber) " +
	    					"VALUES ('" + driverName + "', '" + telephone + "')";
	    	try {
	    		int result = statement.executeUpdate(query);
	    		if(result == 0)
	    			System.out.println("Could not add the driver.");
	    		else
	    			System.out.println("Successfully added the driver!");
	    	}
	    	catch (SQLException e) {
	    		System.out.println(e);
	    	}
	    	System.out.println("---------------------------------------------------------------------");
    }
    
    private static void addBus(String busID, String model, String year) {
    	String query = "INSERT INTO Bus (BusID, Model, Year)" + 
    					"VALUES ('" + busID + "', '" + model + "', '" +
    					year + "')";
    	try {
    		int result = statement.executeUpdate(query);
    		if(result == 0)
    			System.out.println("Could not add the bus.");
    		else
    			System.out.println("Successfully added the bus!");
    	}
    	catch (SQLException e) {
    		System.out.println(e);
    	}
    	System.out.println("---------------------------------------------------------------------");
    }
    
    private static void deleteBus(String busID) {
    	String query = "DELETE FROM BUS " +
    					"WHERE BusID = " + busID;
    	try {
    		int result = statement.executeUpdate(query);
    		if(result == 0)
    			System.out.println("Could not delete the specified trip offering!");
    		else
    			System.out.println("Successfully deleted!");
    		System.out.println("---------------------------------------------------------------------");
    	} catch (SQLException e) {
    		System.out.println(e);
    	}
    }
    
    private static void insertTrip(String tripNum, String date, String startTime) {
    	String query = "SELECT ScheduledArrivalTime FROM TripOffering " +
    					"WHERE TripNumber = " + tripNum + " AND Date = '" +
    					date + "' AND ScheduledStartTime = '" + startTime + "'";
    	try {
    		String arrivalTime = "";
    		ResultSet result = statement.executeQuery(query);
    		if (result != null) {
    			result.next();
    			arrivalTime = result.getString(1);
    			System.out.println("The arrival time: " + arrivalTime);
    			System.out.print("Number of Stops: ");
    			int stops = Integer.parseInt(scanner.nextLine());
    			for (int i=1; i<=stops; i++) {
    				String tempArr;
    				if (i == stops) {
    					System.out.println("Scheduled Arrival Time for stop " + 
    										stops + " is " + arrivalTime);
    					tempArr = arrivalTime;
    				}
    				else {
    					System.out.print("Scheduled Arrival Time For Stop " + i + ": ");
    					tempArr = scanner.nextLine().trim();
    				}
    				
    				System.out.print("Actual Start Time For Stop " + i + ": ");
    				String actStart = scanner.nextLine().trim();
    				
    				System.out.print("Actual Arrival Time For Stop " + i + ": ");
    				String actArr = scanner.nextLine().trim();
    				
    				System.out.print("Number of Passengers In For Stop " + i + ": ");
    				int enter = Integer.parseInt(scanner.nextLine());
    				
    				System.out.print("Number of Passengers Out For Stop " + i + ": ");
    				int exit = Integer.parseInt(scanner.nextLine());
    				
    				query = "INSERT INTO ActualTripStopInfo VALUES('" + tripNum + 
    						"', '" + date + "', '" + startTime + "', '" + i +
    						"', '" + tempArr + "', '" + actStart + "', '" +
    						actArr + "', '" + enter + "', '" + exit + "')";
    				int res = statement.executeUpdate(query);
    	    		if(res == 0)
    	    			System.out.println("Could not add the actual data into the database.");
    	    		else
    	    			System.out.println("Successfully added to the database!");
    			}
    			
    			
    		}
    		else {
    			System.out.println("Trip Offering with provided Key does not exist.");
    		}
    		System.out.println("---------------------------------------------------------------------");
    	} catch (SQLException e) {
    		System.out.println(e);
    	}
    }
    
    
}