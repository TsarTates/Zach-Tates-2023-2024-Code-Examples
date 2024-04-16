import java.util.Scanner;

public class EquipmentMenu {
	public static void returnEquipment(Scanner scanner) {
		//Not fully implemented
		QOL.placeholder();
		
		QOL.printDivider();
		System.out.println("Equipment Return Menu:");
		System.out.println("Enter Rental ID:");
		String rentalId = scanner.nextLine();
		
		
		
		System.out.println("Information entered correctly. \nEquipment returned.");
	}
	
	public static void rentEquipment(Scanner scanner) {
		QOL.printDivider();
		System.out.println("Equipment Rental Menu:");
		System.out.println("Enter Member ID:");
		String memberId = scanner.nextLine();
		
		System.out.println("Enter Equipment ID:");
		String equipmentId = scanner.nextLine();
		
		
		
		System.out.println("Information entered correctly. \nEquipment rented.");
	}
	
	public static void equipmentDelivery(Scanner scanner) {
			QOL.printDivider();
			System.out.println("Equipment Delivery Menu:");
			
			System.out.print("Please enter the serial number of the drone you would like to schedule: ");
			String droneSerialNumber = scanner.nextLine();
			
			System.out.print("Please enter the serial number of the equipment you would like to deliver: ");
			String equipmentSerialNumber = scanner.nextLine();
			
			System.out.print("Please enter the id of the user you would like to deliver to: ");
			String userId = scanner.nextLine();
			
			System.out.println("Information entered correctly. \nEquipment delivered.");
	}
	
	public static void equipmentPickup(Scanner scanner) {
		QOL.printDivider();
		System.out.println("Equipment Pickup Menu:");
		
		System.out.print("Enter the serial number of the equipment being picked up: ");
		String equip = scanner.nextLine();
		System.out.print("Enter rental confirmation number: ");
		int rental = scanner.nextInt();
		String sql1 = "UPDATE EQUIPMENT SET Status = FALSE WHERE Eserialno = ?;";
		String sql2 = "UPDATE RENTAL SET pickupOrDelivery = P, Order_History = Order_History +1 WHERE Serialno = ? AND Rentalno = ?;";
		System.out.println("Information entered correctly. \nEquipment pickedup.");
		//DatabaseConnections.ps_Add(sql1, equip);
		//DatabaseConnections.ps_Add(sql2, equip, rental);
	}
}
