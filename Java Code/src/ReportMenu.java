import java.sql.Connection;
import java.util.Scanner;
public class ReportMenu {
	public static void reportMenuLoop(Scanner scanner) {

		boolean running = true;

		while (running) {

		QOL.printDivider();

		System.out.println("Report Menu:");

		System.out.println("1. Rental Checkout Report");

		System.out.println("2. Popular Item Report");

		System.out.println("3. Popular Manufacturer Report");

		System.out.println("4. Popular Drone Report");

		System.out.println("5. Most Active Rental Member and Rented Items");

		System.out.println("6. Equipment by Type Report");

		System.out.println("7. Return to main menu");

		System.out.print("Choose an Option: ");

		int choice = scanner.nextInt();

		scanner.nextLine(); // Consume newline

		switch (choice) {

		case 1:

		//Rental checkout

		rentalCheckoutReport(scanner);

		break;

		case 2:

		//Popular item

		popularItemReport(scanner);

		break;

		case 3:

		//Popular Manufacturer

		popularManufacturerReport(scanner);

		break;

		case 4:

		//Popular Drone

		popularDroneReport(scanner);

		break;

		case 5:

		//Active rental member and # of rented items

		activeRentalMemNumReport(scanner);

		break;

		case 6:

		//equipment by type

		equipmentTypeReport(scanner);

		break;

		case 7:

		//Return to Main Menu

		running = false;

		break;

		default:

		System.out.println("Invalid option. Please try again.");

		}

		}

		}

		public static void rentalCheckoutReport(Scanner scanner) {

		System.out.println(

		"Enter the User ID for which you would like to see rental checkouts: ");

		String member = scanner.nextLine();

		String sql = "SELECT COUNT(E.Eserialno)FROM RENTAL as R, EQUIPMENT as E JOIN EQUIPMENT ON R.Rentalno = E.Rentalno WHERE R.Userid = ?;";

		DatabaseConnections.ps_RentalCheckoutReport(sql, member);

		}

		public static void popularItemReport(Scanner scanner) {

		String sql = "SELECT E.Eserialno, SUM(R.Rentalno) AS numberOfRentals FROM RENTAL AS R JOIN EQUIPMENT AS E ON R.Serialno = E.Eserialno GROUP BY E.Eserialno LIMIT 1;";

		DatabaseConnections.sqlStaticQuery(MainMenu.conn, sql);

		}

		public static void popularManufacturerReport(Scanner scanner) {

		String sql = "SELECT EQUIPMENT.Manufacturer, SUM(Equipment.Rentalno) AS numberOfRentals FROM  Equipment  GROUP BY EQUIPMENT.Eserialno LIMIT 1";

		DatabaseConnections.sqlStaticQuery( MainMenu.conn,sql);

		}

		private static void popularDroneReport(Scanner scanner) {

		String sql = "SELECT DRONE.Dserialno, SUM(RENTAL.Rentalno) AS numberOfRentals FROM DRONE, RENTAL JOIN EQUIPMENT ON DRONE.Dserialno=EQUIPMENT.Eserialno GROUP BY DRONE.Dserialno LIMIT 1;";

		DatabaseConnections.sqlStaticQuery(MainMenu.conn, sql);

		}

		private static void activeRentalMemNumReport(Scanner scanner) {

		String sql = "SELECT m.User_ID, r.Order_history AS Total_Checked_Out\n FROM Member m\nJOIN Rental r ON m.User_iD = r.UseriD\nGROUP BY r.Order_history\nORDER BY COUNT(r.Order_history) DESC\r\n"

		+ " LIMIT 1";

		DatabaseConnections.sqlStaticQuery(MainMenu.conn, sql);

		}

		private static void equipmentTypeReport(Scanner scanner) {

		System.out.print("Enter equipment type: ");

		String eType = scanner.nextLine();

		System.out.print("Enter latest year you would like to include: ");

		int eYear = scanner.nextInt();

		String sql = "SELECT Edescription FROM Equipment WHERE Etype = ? AND Year<=?";

		DatabaseConnections.ps_EquipmentTypeReport(sql, eType, eYear);

		}
}
