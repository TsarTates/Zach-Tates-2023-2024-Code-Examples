import java.util.*;
public class RecordMenu {
	static ArrayList<Equipment> equipmentList = new ArrayList<>();
	static ArrayList<Members> memberList = new ArrayList<>();
	static ArrayList<Warehouse> warehouseList = new ArrayList<>();
	public static void recordMenuLoop(Scanner scanner) {
		boolean running = true;
		while(running) {
			QOL.printDivider();
			System.out.println("RecordMenu:");
	        System.out.println("1. Search for Record");
	        System.out.println("2. Add Record");
	        System.out.println("3. Delete Record");
	        System.out.println("4. Return to Main Menu");
	        System.out.print("Choose an Option: ");
	        
	        int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch(choice) {
            case 1:
            	//Search
            	searchRecords(scanner);
            	break;
            case 2:
            	//Add
            	addRecord(scanner);
            	break;
            case 3:
            	//Delete
            	deleteRecord(scanner);
            	break;
            case 4:
            	//Return to Main Menu
            	running = false;
                break;
            	default:
            		System.out.println("Invalid option. Please try again.");
            }
		}
	}
	
	public static void searchRecords(Scanner scanner) {
		System.out.println("Which record would you like to search: ");
		System.out.println("1. Equipment");
		System.out.println("2. Members");
		System.out.println("3. Warehouse");
		System.out.print("Choose an option: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		switch(choice) {
		case 1:
			//Equipment
			searchEquipment(scanner);
			break;
		case 2:
			//Members
			searchMembers(scanner);
			break;
		case 3:
			//Warehouse
			searchWarehouse(scanner);
			break;
		default:
			System.out.println("Invalid option. Please try again.");
		}
		
	}
	
	public static void addRecord(Scanner scanner) {
		System.out.println("Which record would you like to add: ");
		System.out.println("1. Equipment");
		System.out.println("2. Members");
		System.out.println("3. Warehouse");
		System.out.print("Choose an option: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		switch(choice) {
		case 1:
			//Equipment
			addEquipment(scanner);
			break;
		case 2:
			//Members
			addMembers(scanner);
			break;
		case 3:
			//Warehouse
			addWarehouse(scanner);
			break;
		default:
			System.out.println("Invalid option. Please try again.");
		}
	}
	
	public static void deleteRecord(Scanner scanner) {
		System.out.println("Which record would you like to delete: ");
		System.out.println("1. Equipment");
		System.out.println("2. Members");
		System.out.println("3. Warehouse");
		System.out.print("Choose an option: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		switch(choice) {
		case 1:
			//Equipment
			removeEquipment(scanner);
			break;
		case 2:
			//Members
			removeMembers(scanner);
			break;
		case 3:
			//Warehouse
			removeWarehouse(scanner);
			break;
		default:
			System.out.println("Invalid option. Please try again.");
		}
	}
	
	private static void addEquipment(Scanner scanner) {
        System.out.print("Enter equipment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter equipment quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter equipment description: ");
        String description = scanner.nextLine();
        System.out.print("Enter model number: ");
        String modelNumber = scanner.nextLine();
        System.out.print("Enter equipment year: ");
        String year = scanner.nextLine();
        System.out.print("Enter serial number: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Enter status: ");
        String status = scanner.nextLine();
        System.out.print("Enter warranty expiration: ");
        String warrantyExpiration = scanner.nextLine();
        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter weight: ");
        int weight = scanner.nextInt();
        System.out.print("Enter width: ");
        int width = scanner.nextInt();
        System.out.print("Enter length: ");
        int length = scanner.nextInt();
        System.out.print("Enter height: ");
        int height = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Equipment equipment = new Equipment(name, type, quantity, description, modelNumber, year, serialNumber,
    			status, warrantyExpiration, manufacturer, weight, width, length, height);
        equipmentList.add(equipment);
        
        String sql = "INSERT INTO EQUIPMENT VALUES (?,?,?,?,?,?,?,?);";
		DatabaseConnections.ps_AddEquipment(sql, equipment);
    }
	
private static void addMembers(Scanner scanner) {
		System.out.print("Enter member first name: ");
		String fName = scanner.nextLine();
		
		System.out.print("Enter member last name: ");
		String lName = scanner.nextLine();
		
		System.out.print("Enter member address: ");
		String address = scanner.nextLine();
		
		System.out.print("Enter member user Id: ");
		int userId = scanner.nextInt();
		
		System.out.print("Enter member phone number: ");
		String phone = scanner.nextLine();
		
		System.out.print("Enter member email: ");
		String email = scanner.nextLine();
		
		System.out.print("Enter member start date: ");
		String startDate = scanner.nextLine();
		
		System.out.print("Enter member distance to warehouse: ");
		int warehouseDistance = scanner.nextInt();
		
		Members member = new Members(fName, lName, address, userId, phone, email, 
				startDate, warehouseDistance);
		
		String sql = "INSERT INTO MEMBER VALUES (?,?,?,?,?,?,?,?);";
		DatabaseConnections.ps_AddMember(sql, member);
	}
	
	private static void addWarehouse(Scanner scanner) {
		System.out.print("Enter warehouse city: ");
		String city = scanner.nextLine();
		
		System.out.print("Enter warehouse address: ");
		String wAddress = scanner.nextLine();
		
		System.out.print("Enter warehouse phone number: ");
		String phone = scanner.nextLine();
		
		System.out.print("Enter warehouse manager: ");
		String managerName = scanner.nextLine();
		
		System.out.print("Enter warehouse storage capacity: ");
		int storageCapacity = scanner.nextInt();
		
		System.out.print("Enter warehouse drone capacity: ");
		int droneCapacity = scanner.nextInt();
		
		Warehouse warehouse = new Warehouse(city, wAddress, phone, managerName, 
			storageCapacity, droneCapacity);
		String sql = "INSERT INTO WAREHOUSE VALUES (?,?,?,?,?,?);";
		DatabaseConnections.ps_AddWarehouse(sql, warehouse);
	}
	
	private static void removeEquipment(Scanner scanner) {
		
			System.out.print("Please enter the serial number of the equipment you would like to remove: ");
			String serialNumber = scanner.nextLine();
			String sql = "DELETE FROM EQUIPMENT WHERE Eserialno = ?;";
			DatabaseConnections.ps_RemoveEquipment(sql, serialNumber);
			
			
	}
	
	private static void removeMembers(Scanner scanner) {
		
			System.out.print("Please enter the user id of the member you would like to remove: ");
			String userId = scanner.nextLine();
			String sql = "DELETE FROM MEMBER WHERE User_id = ?;";
			DatabaseConnections.ps_RemoveMember(sql, userId);
			
			
	}
	
	private static void removeWarehouse(Scanner scanner) {
		
			System.out.print("Please enter the address of the warehouse you would like to remove: ");
			String address = scanner.nextLine();
			String sql = "DELETE FROM WAREHOUSE WHERE Waddress = ?;";
			DatabaseConnections.ps_RemoveWarehouse(sql, address);
	}
	
	private static void searchEquipment(Scanner scanner){
		
			System.out.print("Please enter the serial number of the equipment you would like to find: ");
			String serialNumber = scanner.nextLine();
			scanner.nextLine();
			
			String sql = "SELECT * FROM EQUIPMENT WHERE Eserialno = ?;";
			DatabaseConnections.ps_SearchEquipment(sql, serialNumber);

	}
	
	private static void searchMembers(Scanner scanner) {
			System.out.print("Please enter the user id of the member you would like to find: ");
			String userId = scanner.nextLine();
			String sql = "SELECT * FROM MEMBER WHERE User_id = ?;";
			DatabaseConnections.ps_SearchMember(sql, userId);
			
	}
	
	private static void searchWarehouse(Scanner scanner) {
			System.out.print("Please enter the address of the warehouse you would like to find: ");
			String address = scanner.nextLine();
			String sql = "SELECT * FROM WAREHOUSE WHERE Waddress = ?;";
			DatabaseConnections.ps_SearchWarehouse(sql, address);
	}
	
}
