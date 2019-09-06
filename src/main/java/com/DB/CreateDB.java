package com.DB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateDB {
	
	
	 public static void createNewDatabase() {  
		   
	        String url = "jdbc:sqlite:Nikhil.db";  
	   
	        try {  
	            Connection conn = DriverManager.getConnection(url);  
	            if (conn != null) {  
	                conn.getMetaData();  
	                System.out.println("Nikhil database has been created.");  
	            }  
	   
	        } catch (SQLException e) {  
	            System.out.println("Error : "+e.getMessage());  
	        }  
	    }  
	
	 public static void dropDB() {
		 
		 File file = new File("Nikhil.db");
		 if(file.exists()){
			 file.delete();
		 }
	 }
	
	public static void createNewTable() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:Nikhil.db";  
          
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS usersNL (\n"  
                + " id integer PRIMARY KEY,\n"  
                + " name text NOT NULL,\n"
                + " email text , \n" 
                + " receiveNewsLetter integer , \n" 
                + " active integer NOT NULL DEFAULT 1, \n" 
                + " dateStartSubscription datetime NOT NULL DEFAULT current_timestamp, \n"
                + " dateEndSubscription datetime \n"
                + ");";  
          
        try{  
            Connection conn = DriverManager.getConnection(url);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
	
	public static Connection connect() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:Nikhil.db";  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return conn;  
    }  
   
	
	public static void insert(String name, String email, boolean receiveNewsLetter, String dateStartSubscription) {  
        String sql = "INSERT INTO usersNL(name, email, receiveNewsLetter, dateStartSubscription) VALUES(?,?,?,?)";  
   
        try{  
            Connection conn = connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, name);  
            pstmt.setString(2, email);  
            pstmt.setInt(3, receiveNewsLetter == true ? 1:0);  
            pstmt.setString(4, dateStartSubscription);  
            pstmt.executeUpdate();  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
	
	public static void selectAll(){  
        String sql = "SELECT * FROM usersNL";  
          
        try {  
            Connection conn = connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println("id: "+rs.getInt("id") +  
                		"\t name: " + rs.getString("name")+  
                		"\t email: " + rs.getString("email")+
                		"\t dateStartSubscription: " + rs.getString("dateStartSubscription")+
                		"\t dateEndSubscription: " + rs.getString("dateEndSubscription")
                		);  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
	
	/************************/
	public static int selectAllUsersSubscribedToday(){  
		
		int count=0;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs ;
        String sql = "SELECT COUNT(*) count FROM usersNL WHERE date(dateStartSubscription) = date('now')";  
          
        try {  
        	
            conn = connect();  
            pstmt = conn.prepareStatement(sql); 
            rs = pstmt.executeQuery();  
              
            if (rs.next()) {  
            	count=rs.getInt("count");
            	//logger.info("selectAllUsersSubscribedToday().NEW SUBSCRIBERS: "+count );  
            }  
        } catch (SQLException e) {  
        	//logger.error("selectAllUsersSubscribedToday().Error: "+e.getMessage());  
        }  
        
        return count;
    } 

	public static boolean userShouldReceiveNewLetter(String userEmail){  
		
		boolean reciveNewLetter=false;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs ;
        String sql = "SELECT active FROM usersNL WHERE email = ?";  
          
        try {  
            conn = connect();  
            pstmt = conn.prepareStatement(sql); 
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();  
              
            while (rs.next()) {
            	reciveNewLetter=rs.getInt("active")==1?true:false;
            	//logger.info("userShouldReceiveNewLetter().USER ACTIVE: "+reciveNewLetter );  
            }  
        } catch (SQLException e) {  
          	//logger.error("userShouldReceiveNewLetter().Error: "+e.getMessage());  
        }  
        
        return reciveNewLetter;
    }  
	
	public static int selectAllUsersSubscribedByDate(String date){ 
		
		int count=0;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs ;
        String sql = "SELECT COUNT(*) count FROM usersNL WHERE date(dateStartSubscription) <= ?";  
          
        try {  
            conn = connect();  
            pstmt = conn.prepareStatement(sql); 
            pstmt.setString(1, date);
            rs = pstmt.executeQuery();  
              
            while (rs.next()) {  
            	count=rs.getInt("count");
            	//logger.info("selectAllUsersSubscribedByDate().SUBSCRIBERS: "+count );  
            }  
        } catch (SQLException e) {  
        	//logger.error("selectAllUsersSubscribedByDate().Error: "+e.getMessage());  
        }  
        return count;
    }  
	
	public static void main(String[] args) {  
		  
		//BasicConfigurator.configure();
		dropDB();
		createNewDatabase();
		createNewTable();
        insert("Roger", "roger@gmail.com", true, "2019-07-23 17:57:26");  
        insert("Mike",  "mike@gmail.com",  true, "2019-07-26 17:57:26");  
        insert("Jerry", "jerry@gmail.com", true, "2019-07-26 17:57:26");  
        selectAll();
        selectAllUsersSubscribedToday();
        userShouldReceiveNewLetter("roger@gmail.com");
        selectAllUsersSubscribedByDate("2019-07-23");
    }  
}
