package employeeCRUD;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

public class EmployeeDetails 
{
	static int UserChoice = 0;
	
	static Scanner sc = new Scanner(System.in);
	static Connection connection;
	static PreparedStatement pstmt;
	static ResultSet rs;
	static String insertSQL = "Insert into Employee(EmployeeName, EmployeeAddress, DateOfJoining, Experience, DateOfBirth) values(?,?,?,?,?)";
	static String updateSQL = "Update Employee set EmployeeAddress = ? where EmployeeID = ?";
	static String deleteSQL = "Delete from Employee where EmployeeID = ?";
	static String selectSQL = "Select * from Employee where EmployeeID = ?";
	public static Connection Connection() throws Exception
	{
		Connection connection;
		PreparedStatement pstmt;
		ResultSet rs;
	//	Class.forName("com.mysql.jdbc.Driver");
	//	connection = DriverManager.getConnection("jdbc:sqlserver://localhost:3306/EmployeeDetails","root","root");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\sqlexpress","root","root");
		System.out.println("Connection established. Press Enter");
		return connection;
	}
	public static void input() throws ParseException, Exception
	{
		Connection connection = EmployeeDetails.Connection();	
		boolean Flag1 = false, Flag2, Flag3, Flag4, Flag5;
		System.out.println("Please enter the details in order with a pipe after every detail- Employee Name, Employee Address, Employee Date of Joining, Experience and Employee Date of Birth ");
		String completeDetails = sc.nextLine();
		String[] splitStrings = completeDetails.split("\\|");
		
		String empName = splitStrings[0];
		if(empName.chars().allMatch(Character::isLetter))
		{
			empName = splitStrings[0];
			Flag1=true;
		}
		else if(empName.chars().allMatch(Character::isLetterOrDigit) || empName.equals(""))
		{
			System.out.println("You have entered an invalid name.. Please re-enter the valid employee name");
			Flag1 = false;
		}
		String empAddress = "";
		String str = splitStrings[1];
		if(splitStrings[1].equals(""))
		{
			System.out.println("The Employee Address cannot be null...Please re-enter the correct Employee Address...");
			Flag5 = false;
		}
		else
		{
			empAddress = splitStrings[1];
			Flag5 = true;
		}
		java.sql.Date convSQLDate = null;
		if(!(splitStrings[2].equals(null)))
		{
			try
			{
				SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-mm-dd");
				sdf.setLenient(false);
				java.util.Date convDate = sdf.parse(splitStrings[2]);
				convSQLDate = new java.sql.Date(convDate.getTime());
				Flag3 = true;
			}
			catch(Exception ex)
			{
				System.out.println("You have entered a wrong date.. please enter the correct date in YYYY-MM-DD Format");
				Flag3 = false;
			}
		}
		else
		{
			System.out.println("The date you have entered is null.. please enter the correct date format YYYY-MM-DD");
			Flag3 = false;
		}
					
		int expr = 0;
		if(!(splitStrings[3].equals("")))
		{
			try
			{
				expr = Integer.parseInt(splitStrings[3]);
				Flag2 = true;
			}
			catch(Exception ex)
			{
				System.out.println("You have entered an invalid experience. Please re-enter the experience in years only..");
				Flag2 = false;
			}
		}
		else
		{
			System.out.println("The experience you have entered is null.. please enter the correct experience in years");
			Flag2 = false;
		}
		java.sql.Date convSQLDate1 = null;
		if(!(splitStrings[4].equals("")))
		{
			try
			{
				SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-mm-dd");
				sdf.setLenient(false);
				java.util.Date convDate1 = sdf.parse(splitStrings[4]);
				convSQLDate1 = new java.sql.Date(convDate1.getTime());
				Flag4 = true;
			}
			catch(Exception ex)
			{
				System.out.println("You have entered a wrong date.. please enter the correct date in YYYY-MM-DD Format");
				Flag4 = false;
			}
		}
		else
		{
			System.out.println("The date you have entered is null.. please enter the correct date format YYYY-MM-DD");
			Flag4 = false;
		}
			
		if(!Flag1 || !Flag2 || !Flag3 || !Flag4 || !Flag5)
		{
			System.out.println("You haven't entered the details properly... please re-enter the details properly in order with pipe after every entry Employee Name, Employee Address, Date of Joining(YYYY-MM-DD), Experience(Years), Date of Birth(YYYY-MM-DD)");
		    EmployeeDetails.input();
		}
		else
		{
			pstmt = connection.prepareStatement(insertSQL);
			
			pstmt.setString(1, empName);
			pstmt.setString(2, empAddress);
			pstmt.setDate(3,convSQLDate);
			pstmt.setInt(4, expr);
			pstmt.setDate(5, convSQLDate1);

			int rows = pstmt.executeUpdate();
			System.out.println(rows+ "rows created");
			pstmt.close();
			connection.close(); 
		}		
	}
	public static String testInput(String[] data)
	{
		java.sql.Date DOJ, DOB;
		for(int i=0; i<=data.length-1; i=i+2)
		{
			if(data[i].isEmpty() || data[i].equals(""))
				return "The input cannot be null. Please re-enter the data with correct column name and column value";
			else
			{
				if(!(data[i].equals("EmployeeID") || data[i].equals("EmployeeName") || data[i].equals("DateOfJoining")|| data[i].equals("Experience") || data[i].equals("DateOfBirth")))
					return "You have not entered the correct column name. Please enter the correct column name(Case-Sensitive). \nThe column names are : EmployeeID, EmployeeName, EmployeeAddress, DateOfJoining, Experience, DateOfBirth";
				else
				{
					try
					{
						if(Integer.valueOf(data[i+1]) instanceof Integer && data[i].equals("EmployeeID"))
							return "Success!!";
						else if(Integer.valueOf(data[i+1]) instanceof Integer && data[i].equals("Experience"))
							return "Success!!";
						else if(data[i].equals("EmployeeName") && data[i+1].chars().allMatch(Character::isLetter))
							return "Success!!";
						else if(data[i].equals("DateOfJoining"))
						{
							DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
							df.setLenient(false);
							DOJ = (java.sql.Date) df.parse(data[i+1]);
							return "Success!!";
						}
						else if(data[i].equals("DateOfBirth"))
						{
							DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
							df.setLenient(false);
							DOB = (java.sql.Date) df.parse(data[i+1]);
							return "Success!!";
						}
					}
					catch(Exception e)
					{
						return "The data entered is of invalid data type. Please enter the correct data format...";
					}
				}		
			}
		}
		return "Success!!";
	}
							
	public static String dynamicQueryGenerator()
	{
		String StringSql1 = "Select * from EmployeeDetails where ";
		String StringSql2, data1 = null;
		System.out.println("Please enter the Column names similar way as mentioned along with values seperated by a pipe if more than one column. \nThe column names are EmployeeID, EmployeeName, EmployeeAddress, DateOfJoining, Experience, DateOfBirth ");
		sc.nextLine();
		String queryInput = sc.nextLine();
		if(queryInput == null ||queryInput.equals(""))
		{
			System.out.println("You have not entered any input. Please re-enter the data again. The input cannot be null...");
			EmployeeDetails.dynamicQueryGenerator();
		}
		String[] queryWords = queryInput.split("\\|");
		String[] data = new String[queryWords.length];
		String[] d = new String[2*queryWords.length];
		int j=0,l=0;
		for(int i=0;i<=queryWords.length-1; i++)
		{
				if(queryWords[i].isEmpty() || queryWords[i].equals(""))
				{
					System.out.println("The input cannot be null. Please re-enter the data with correct column name and column value");
					EmployeeDetails.dynamicQueryGenerator();
				}
				else
				{
					data = queryWords[i].split("\\=");	
					for(int k=0;l<2;j++,l++)
						d[j]= data[k++];
					l=0;
				}
		}	
		String returnQuery = EmployeeDetails.testInput(d);
		if(returnQuery.equals("Success!!"))
		{
			if(queryWords.length >1)
			{
				queryInput = queryInput.replace("=", "= '");
				queryInput = queryInput.replace("|", "' and ");
				StringSql1 = StringSql1 + queryInput;
			}
			else
			{
				queryInput = queryInput.replace("=", "= '");
				queryInput = queryInput + "'";
				StringSql1 = StringSql1 + queryInput;
			}
			for(int i=0;i<=data.length-1;i++)
				data1 += data[i];
			EmployeeDetails.pstmtData(data1);
		}
		else
		{
			System.out.println(returnQuery);
			EmployeeDetails.dynamicQueryGenerator();
		}
		return StringSql1;
	}
	public static String pstmtData(String data1)
	{
		return data1;
	}
	public static int insertSQL() throws Exception
	{
	
		int rows= 0;
		sc.nextLine();

		EmployeeDetails.input();
		 
		return 0;
	}
	public static int updateSQL() throws Exception
	{
		Connection connection = EmployeeDetails.Connection();
		System.out.println("You choose to update the Address of an Employee");
		System.out.println("Please enter the Employee ID for which you want to perform an update");
		int empId = sc.nextInt();
		sc.nextLine();
		System.out.println("Please enter the Employee Address that you want to update");
		String empAddr = sc.nextLine();
		pstmt = connection.prepareStatement(updateSQL);
		pstmt.setString(1, empAddr);
		pstmt.setInt(2, empId);
		int rows1 = pstmt.executeUpdate();
		System.out.println(rows1+ "Rows updated");
		pstmt.close();
		connection.close();
		return rows1;
	}
	public static int deleteSQL() throws Exception
	{
		Connection connection = EmployeeDetails.Connection();
		System.out.println("You choose to delete an Employee");
		System.out.println("Please enter the Employee ID which has to be deleted:");
		int EmployeeID = sc.nextInt();
		pstmt = connection.prepareStatement(deleteSQL);
		pstmt.setInt(1, EmployeeID);
		int rows = pstmt.executeUpdate();
		System.out.println(rows + "Rows Deleted");
		pstmt.close();
		connection.close();
		return rows;
	}
	public static void retrieveSQL() throws Exception
	{
		String data1 = null;
		Connection connection = EmployeeDetails.Connection();
		System.out.println("You choose to retrieve all details from the Employees");
		String selectSQL = EmployeeDetails.dynamicQueryGenerator();
		String pstmtData = EmployeeDetails.pstmtData(data1);
		pstmt = connection.prepareStatement(selectSQL);
		String[] bind = pstmtData.split(" ");
		DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
		for(int i=0;i<=bind.length-1;i= i+2)
		{
			if(bind[i].equals("EmployeeID"))
				pstmt.setInt((i+1), Integer.valueOf(bind[i+1]));
			else if(bind[i].equals("EmployeeName"))
				pstmt.setString((i+1), bind[i+1]);
			else if(bind[i].equals("EmployeeAddress"))
				pstmt.setString((i+1), bind[i+1]);
			else if(bind[i].equals("DateOfJoining"))
				pstmt.setDate((i+1), (java.sql.Date)df.parse(bind[i+1]));
			else if(bind[i].equals("Experience"))
				pstmt.setInt((i+1), Integer.valueOf(bind[i+1]));
			else if(bind[i].equals("DateOfBirth"))
				pstmt.setDate((i+1), (java.sql.Date)df.parse(bind[i+1]));
		}
		rs = pstmt.executeQuery();
		System.out.println("Employee ID\tEmployee Name\tEmployee Address\tDate Of Joining\tExperience\tDate Of Birth");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getString(4)+"\t\t"+rs.getInt(5)+"\t\t"+rs.getString(6));
		}
		pstmt.close();
		connection.close();
	}
	public static void main(String[] args)
	{
		try
		{
			while(UserChoice!=5)
			{
				System.out.println("Please enter the equivalent choice options listed below: ");
				System.out.println("1. Create a new Employee");
				System.out.println("2. Update Employee Information");
				System.out.println("3. Delete and existing employee Information");
				System.out.println("4. Retrieve the data of the employee");
				System.out.println("5. Exit");
				UserChoice = sc.nextInt();
				if(UserChoice == 1)
					EmployeeDetails.insertSQL();
				else if(UserChoice == 2)
					EmployeeDetails.updateSQL();
				else if(UserChoice == 3)
					EmployeeDetails.deleteSQL();
				else if(UserChoice == 4)
					EmployeeDetails.retrieveSQL();
				else
					break;
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		}
}
