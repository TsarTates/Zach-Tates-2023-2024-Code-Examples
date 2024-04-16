import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class MainMenu {
private static String DATABASE = "IntroToDatabaseProject.db";
	
	public static Connection conn = null;
    public static Connection initializeDB(String databaseFileName) {
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; 
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful.");
            } else {
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		conn = initializeDB(DATABASE);
        boolean running = true;

        while (running) {
        	QOL.printDivider();
            System.out.println("Main Menu:");
            System.out.println("1. Rent Equipment");
            System.out.println("2. Return Equipment");
            System.out.println("3. Equipment Delivery");
            System.out.println("4. Equipment Pickup");
            System.out.println("5. Record Menu");
            System.out.println("6. Report Menu");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                	//Rent Equipment
                	EquipmentMenu.rentEquipment(scanner);
                	break;
                case 2:
                	//Return Equipment
                	EquipmentMenu.returnEquipment(scanner);
                	break;
                case 3:
                	//Equipment Delivery
                	EquipmentMenu.equipmentDelivery(scanner);
                	break;
                case 4:
                	//Equipment Pickup
                	EquipmentMenu.equipmentPickup(scanner);
                	break;
                case 5:
                	//Record Menu
                	RecordMenu.recordMenuLoop(scanner);
                	break;
                case 6:
                	ReportMenu.reportMenuLoop(scanner);
                	break;
                case 7:
                	//Exit
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
        try {
        	conn.close();
        }catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
}


