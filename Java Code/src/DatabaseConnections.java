import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class DatabaseConnections {
	 
private static PreparedStatement ps;
	
    //Used for all static select queries
    public static void sqlStaticQuery(Connection conn, String sql){
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        	rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
	//used for all dynamic select queries
    public static void sqlDynamicQuery(Connection conn, PreparedStatement sql){
        try {
        	ResultSet rs = sql.executeQuery();
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        	rs.close();
        	ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Method for executing all add queries
    public static void sqlExecuteAdd(Connection conn, PreparedStatement sql) {
    	try {
    		int affectedRows = sql.executeUpdate();
    		
    		System.out.println(affectedRows + " Rows Affected");
    		ps.close();
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    //Method for executing all remove queries
    public static void sqlExecuteRemove(Connection conn, PreparedStatement sql) {
    	try {
    		int affectedRows = sql.executeUpdate();
    		
    		System.out.println(affectedRows + " Rows Affected");
    		ps.close();
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
   
    //Searches for each table
    public static void ps_SearchEquipment(String sql, String serialNumber) {
    	try {
    		ps = MainMenu.conn.prepareStatement(sql);
    		ps.setString(1, serialNumber);
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	sqlDynamicQuery(MainMenu.conn, ps);
    }
    
    public static void ps_SearchMember(String sql, String userId) {
    	try {
    		ps = MainMenu.conn.prepareStatement(sql);
    		ps.setString(1, userId);
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	sqlDynamicQuery(MainMenu.conn, ps);
    }
    
    public static void ps_SearchWarehouse(String sql, String address) {
    	try {
    		ps = MainMenu.conn.prepareStatement(sql);
    		ps.setString(1, address);
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	sqlDynamicQuery(MainMenu.conn, ps);
    }
    
    //Deletes for each table
    public static void ps_RemoveMember(String sql, String userId) {
    	try {
        	ps = MainMenu.conn.prepareStatement(sql);
        	ps.setString(1, userId);
        	}catch (SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	sqlExecuteRemove(MainMenu.conn, ps);
    }
    public static void ps_RemoveWarehouse(String sql, String address) {
    	try {
        	ps = MainMenu.conn.prepareStatement(sql);
        	ps.setString(1, address);
        	}catch (SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	sqlExecuteRemove(MainMenu.conn, ps);
    }
    public static void ps_RemoveEquipment(String sql, String serialNumber) {
    	try {
        	ps = MainMenu.conn.prepareStatement(sql);
        	ps.setString(1, serialNumber);
        	}catch (SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	sqlExecuteRemove(MainMenu.conn, ps);
    }
    
    
    //Need adds for each table
    public static void ps_AddWarehouse(String sql, Warehouse w) {
    	try {
    	ps = MainMenu.conn.prepareStatement(sql);
    	ps.setInt(1, w.storageCapacity);
    	ps.setInt(2, w.droneCapacity);
    	ps.setString(3, w.managerName);
    	ps.setString(4, w.phone);
    	ps.setString(5, w.wAddress);
    	ps.setString(6, w.city);
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	sqlExecuteAdd(MainMenu.conn, ps);
    }
    
    public static void ps_AddMember(String sql, Members m) {
    	try {
    	ps = MainMenu.conn.prepareStatement(sql);
    	ps.setInt(1, m.userId);
    	ps.setString(2, m.fName);
    	ps.setString(3, m.lName);
    	ps.setString(4, m.address);
    	ps.setString(5, m.phone);
    	ps.setString(6, m.email);
    	ps.setDate(7, Date.valueOf(m.startDate));
    	ps.setInt(8, m.warehouseDistance);
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	sqlExecuteAdd(MainMenu.conn, ps);
    }
    
    public static void ps_AddEquipment(String sql, Equipment eq) {
    	try {
    	ps = MainMenu.conn.prepareStatement(sql);
    	ps.setString(1, eq.description);
    	ps.setString(2, eq.type);
    	ps.setString(3, eq.modelNumber);
    	ps.setString(4, eq.year);
    	ps.setString(5, eq.serialNumber);
    	ps.setString(6, eq.status);
    	ps.setDate(7, Date.valueOf(eq.warrantyExpiration));
    	ps.setString(8, eq.manufacturer);
    	ps.setInt(9, eq.weight);
    	ps.setInt(10, eq.width);
    	ps.setInt(11, eq.length);
    	ps.setInt(12, eq.height);
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	sqlExecuteAdd(MainMenu.conn, ps);
    }
    
    public static void ps_RentalCheckoutReport(String sql, String member) {
    	try {
    		ps = MainMenu.conn.prepareStatement(sql);
    	}catch(SQLException e){
    		System.out.println(e.getMessage());
    	}
    	
    	sqlDynamicQuery(MainMenu.conn, ps);
    }
    
    public static void ps_EquipmentTypeReport(String sql, String eType, int eYear) {
    	try {
    		ps = MainMenu.conn.prepareStatement(sql);
    	}catch(SQLException e){
    		System.out.println(e.getMessage());
    	}
    	
    	sqlDynamicQuery(MainMenu.conn, ps);
    }
}
