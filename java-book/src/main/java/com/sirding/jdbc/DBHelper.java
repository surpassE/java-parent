package com.sirding.jdbc;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sirding.util.ObjectUtilsExtend;


public class DBHelper {  
    public static final String url = "jdbc:mysql://127.0.0.1/test";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "root";  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public DBHelper() {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }
    
    public PreparedStatement getPreparedStatement() {
    	return this.pst;
    }
    
    public void select() throws Exception {
    	String sql = "SELECT * FROM db_blob";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()) {
    		int i = 1;
    		System.out.println(rs.getInt(i++));
    		Blob blob = rs.getBlob(i++);
    		if(blob != null) {
    			byte[] buf = blob.getBytes(1, (int)blob.length());
    			DbMsg dbMsg = ObjectUtilsExtend.bytesToObject(buf);
    			System.out.println(dbMsg);
    			System.out.println("name:" + dbMsg.getName() + ",age:" + dbMsg.getAge());;
    		}else {
    			System.out.println("msg is null");
    		}
    	}
    }
    
    public void save() throws Exception{
    	String sql = "insert into db_blob(msg) values(?)";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	DbMsg dbMsg = new DbMsg("Hello", 10);
    	byte[] buf = ObjectUtilsExtend.objectToBytes(dbMsg);
    	ByteArrayInputStream in = new ByteArrayInputStream(buf);
    	ps.setBlob(1, in);
    	ps.execute();
    }
    
    public static void main(String[] args) throws Exception {
    	DBHelper dbHelper = new DBHelper();
//    	dbHelper.save();
    	dbHelper.select();
    	System.out.println("game over...");
	}
}  
