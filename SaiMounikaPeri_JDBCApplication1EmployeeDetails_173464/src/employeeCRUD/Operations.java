package employeeCRUD;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Operations 
{	
	Properties prop = new Properties();
	public void load() throws Exception
	{
			FileInputStream fs = new FileInputStream("Constants.properties");
			prop.load(fs);
	}
	Object o = prop.getProperty("logger");
	Logger logger = (Logger)o;
	Object o1 = prop.getProperty("sc");
	Scanner sc = (Scanner)o1;
	Input ed = new Input();
	Object o2 = prop.getProperty("pstmt");
	PreparedStatement pstmt = (PreparedStatement)o2;
	Object o3 = prop.getProperty("stmt");
	Statement stmt = (Statement)o3;
	Object o4 = prop.getProperty("rs");
	ResultSet rs = (ResultSet)o4;
	final String EMPLOYEEID = prop.getProperty("EMPLOYEEID");
	final String EMPLOYEENAME = prop.getProperty("EMPLOYEENAME");
	final String EMPLOYEEADDRESS = prop.getProperty("EMPLOYEEADDRESS");
	final String YEAR = prop.getProperty("YEAR");
	final String DATEOFJOINING = prop.getProperty("DATEOFJOINING");
	final String DATEOFBIRTH = prop.getProperty("DATEOFBIRTH");
	final String EXPERIENCE = prop.getProperty("EXPERIENCE");
	
	public int insertSQL() throws Exception
	{
		sc.nextLine();
		ed.doInput();
		return 0;
	}
	public int updateSQL() throws Exception
	{
		Connection connection = ed.doConnection();
		logger.log(Level.INFO, "You choose to update the Address of an Employee.\n Please enter the Employee ID for which you want to perform an update");
		int empId = sc.nextInt();
		sc.nextLine();
		logger.log(Level.INFO,"Please enter the Employee Address that you want to update");
		String empAddr = sc.nextLine();
		String updateSQL = prop.getProperty("updateSQL");
		pstmt = connection.prepareStatement(updateSQL);
		pstmt.setString(1, empAddr);
		pstmt.setInt(2, empId);
		int rows1 = pstmt.executeUpdate();
		logger.log(Level.INFO, "Rows updated{0}", rows1);
		pstmt.close();
		connection.close();
		return rows1;
	}
	public int deleteSQL() throws Exception
	{
		Connection connection = ed.doConnection();
		logger.log(Level.INFO,"You choose to delete an Employee\nPlease enter the Employee ID which has to be deleted:");
		int empId = sc.nextInt();
		String deleteSQL = prop.getProperty("deleteSQL");
		pstmt = connection.prepareStatement(deleteSQL);
		pstmt.setInt(1, empId);
		int rows = pstmt.executeUpdate();
		logger.log(Level.INFO, "Rows deleted{0}", rows);
		pstmt.close();
		connection.close();
		return rows;
	}
	public void retrieveSQL() throws Exception
	{
		String data1 = null; int i=0,j=0;
		Connection connection = ed.doConnection();
		logger.log(Level.INFO, "You choose to retrieve all details from the Employees");
		String a[] = ed.dynamicQueryGenerator();
		stmt = connection.createStatement();
		boolean ret = stmt.execute(a[0]);
		String[] bind = a[1].split(" ");
		DateFormat df = new SimpleDateFormat(YEAR);
		while(i<=bind.length-1)
		{
			j = i+1;
			if(bind[i].equals(EMPLOYEEID))
			{
				int empId = Integer.parseInt(bind[i+1]);
				pstmt.setInt(j, empId);
			}
			else if(bind[i].equals(EMPLOYEENAME))
				pstmt.setString(j, bind[j]);
			else if(bind[i].equals(EMPLOYEEADDRESS))
				pstmt.setString((i+1), bind[i+1]);
			else if(bind[i].equals(DATEOFJOINING))
				pstmt.setDate((i+1), (java.sql.Date)df.parse(bind[i+1]));
			else if(bind[i].equals(EXPERIENCE))
				pstmt.setInt((i+1), Integer.valueOf(bind[i+1]));
			else if(bind[i].equals(DATEOFBIRTH))
				pstmt.setDate((i+1), (java.sql.Date)df.parse(bind[i+1]));
			i= i+2; 
		}
		rs = stmt.executeQuery(a[0]);
		logger.log(Level.INFO,"Employee ID\tEmployee Name\tEmployee Address\tDate Of Joining\tExperience\tDate Of Birth");
		while(rs.next())
		{
			logger.log(Level.INFO, rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getString(4)+"\t\t"+rs.getInt(5)+"\t\t"+rs.getString(6));
		}
		pstmt.close();
		connection.close();
	}
	
}
