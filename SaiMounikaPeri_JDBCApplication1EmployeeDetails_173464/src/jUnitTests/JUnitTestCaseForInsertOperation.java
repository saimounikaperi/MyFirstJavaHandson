package jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import employeeCRUD.EmployeeDetails;
import junit.framework.Assert;

class JUnitTestCaseForInsertOperation {

	@Test
	void test() 
	{
		Scanner sc = new Scanner(System.in);
		Connection connection;
		PreparedStatement pstmt;
		ResultSet rs;
		String insertSQL = "Insert into Employee(EmployeeName, EmployeeAddress, DateOfJoining, Experience, DateOfBirth) values(?,?,?,?,?)";
		try
		{
			int rows = EmployeeDetails.insertSQL();
			Assert.assertEquals(1, rows);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
