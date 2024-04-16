public class Equipment {
	String name;
    String type;
    int quantity;
    String description;
    String modelNumber;
    String year;
    String serialNumber;
    String status;
    String warrantyExpiration;
    String manufacturer;
    int weight;
    int width;
    int length;
    int height;
    

	public Equipment(String name, String type, int quantity, String description, String modelNumber, String year, String serialNumber,
			String status, String warrantyExpiration, String manufacturer, int weight, int width, int length, int height) {
		this.name = name;
		this.type = type;
		this.quantity = quantity;
		this.description = description;
		this.modelNumber = modelNumber;
		this.year = year;
		this.serialNumber = serialNumber;
		this.status = status;
		this.warrantyExpiration = warrantyExpiration;
		this.manufacturer = manufacturer;
		this.weight = weight;
		this.width = width;
		this.length = length;
		this.height = height;
	}
}
