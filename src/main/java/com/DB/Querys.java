package com.DB;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;
import com.classes.User;
   
public class Querys {  
	
	public Querys() {
		super();
	}

	public String addUser(User user) { 

		String result = "Your subscription has been done";
		boolean userExist = validateUserExist(user.getEmail());
		
		if (!userExist) {
		
			Connection conn;
			PreparedStatement pstmt;
	        String sql = "INSERT INTO usersNL(name, email, receiveNewsLetter) VALUES(?,?,?)";  
	   
	        try{  
	            conn = connect();  
	            pstmt = conn.prepareStatement(sql);  
	            pstmt.setString(1, user.getName());  
	            pstmt.setString(2, user.getEmail());  
	            pstmt.setInt(3, user.isReceiveNewsLetter() ? 1:0);  
	            pstmt.executeUpdate();  
	        } catch (SQLException e) {  
	        	System.err.println("ERROR addUser():"+e.getMessage());
	            e.printStackTrace();
	        }  
		}else {
			result = "The user already exist, try with another email";
		}
		return result;
    }  
	
	public boolean validateUserExist(String email){  
		
		boolean exist=false;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs ;
        String sql = "SELECT COUNT(*) count FROM usersNL WHERE email = ?";  
          
        try {  
        	
            conn = connect();  
            pstmt = conn.prepareStatement(sql); 
            pstmt.setString(1, email);  
            rs = pstmt.executeQuery();  
              
            while (rs.next()) {  
            	exist=rs.getInt("count")==0?false:true;
            	System.out.println("validateUserExist().UserExist: "+exist );  
            }  
        } catch (SQLException e) {  
        	System.err.println("selectAllUsersSubscribedToday().Error: "+e.getMessage()); 
        }  
        
        return exist;
    } 
	
	public int selectAllUsersSubscribedToday(){  
		
		int count=0;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs ;
        String sql = "SELECT COUNT(*) count FROM usersNL WHERE date(dateStartSubscription) = date('now')";  
          
        try {  
        	
            conn = connect();  
            pstmt = conn.prepareStatement(sql); 
            rs = pstmt.executeQuery();  
              
            while (rs.next()) {  
            	count=rs.getInt("count");
            	System.out.println("selectAllUsersSubscribedToday().NEW SUBSCRIBERS: "+count );  
            }  
        } catch (SQLException e) {  
        	System.err.println("selectAllUsersSubscribedToday().Error: "+e.getMessage()); 
        }  
        
        return count;
    } 

	public boolean userShouldReceiveNewLetter(String userEmail){  
		
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
            	System.out.println("userShouldReceiveNewLetter().USER ACTIVE: "+reciveNewLetter );  
            }  
        } catch (SQLException e) {  
        	System.err.println("userShouldReceiveNewLetter().Error: "+e.getMessage());  
        }  
        
        return reciveNewLetter;
    }  
	
	public int selectAllUsersSubscribedByDate(String date){ 
		
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
            	System.out.println("selectAllUsersSubscribedByDate().SUBSCRIBERS: "+count );  
            }  
        } catch (SQLException e) {  
        	System.err.println("selectAllUsersSubscribedByDate().Error: "+e.getMessage());  
        }  
        return count;
    }  
	
	public static Connection connect() {  
		
        Connection conn = null;  
        try {  
        	Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/nikhil.mehra/Desktop/Newsletter/nikhilDemo/Nikhil.db");  
            
        } catch (SQLException | ClassNotFoundException e) {  
        	System.err.println("connect().Error: "+e.getMessage());  
        }  
        return conn;  
    }  
}  