package com.lesath.apps.db;

import java.sql.*;
import java.util.ArrayList;

import com.lesath.apps.model.TestTable;



public class ScheduleDAO {

	java.sql.Connection conn;

    public ScheduleDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    public boolean addSchedule(TestTable t) throws Exception {
        try {
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO testTable (x,y) values(?,?);");
            ps.setInt(1,  t.getX());
            ps.setInt(2,  t.getY());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
    public ArrayList<TestTable> getSchedule() throws Exception {
        
    	ArrayList<TestTable> rowList = new ArrayList<TestTable>();
    	
    	try {
    		Statement statement = conn.createStatement();
            String query = "SELECT * FROM testTable";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                TestTable row = generateConstant(resultSet);
                rowList.add(row);
            }
            resultSet.close();
            statement.close();
            return rowList;

        } catch (Exception e) {
        	rowList = null;
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
    private TestTable generateConstant(ResultSet resultSet) throws Exception {
        int x  = resultSet.getInt("x");
        int y = resultSet.getInt("y");
        return new TestTable(x,y);
    }
}
