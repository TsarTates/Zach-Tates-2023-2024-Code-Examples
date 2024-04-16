
public class Warehouse {
	
	String city;
	String wAddress;
	String phone;
	String managerName;
	int storageCapacity;
	int droneCapacity;
	public Warehouse(String city, String wAddress, String phone, String managerName, 
			int storageCapacity, int droneCapacity) {
		this.city = city;
		this.wAddress = wAddress;
		this.phone = phone;
		this.managerName = managerName;
		this.storageCapacity = storageCapacity;
		this.droneCapacity = droneCapacity;
	}
}
