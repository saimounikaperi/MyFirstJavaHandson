package employeeCRUD;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Input
{
	Properties prop = new Properties();
	public void load() throws Exception
	{
			FileInputStream fs = new FileInputStream("Constants.properties");
			prop.load(fs);
	}
	Object o = prop.getProperty("logger");
	Logger logger = (Logger)o;
	Object o1 = prop.getProperty("connection");
	Connection connection = (Connection)o;
	Object o3 = prop.getProperty("pstmt");
	PreparedStatement pstmt = (PreparedStatement)o3;
	Object o4 = prop.getProperty("sc");
	Scanner sc = (Scanner)o4;
	final String EMPLOYEEID = prop.getProperty("EMPLOYEEID");
	final String EMPLOYEENAME = prop.getProperty("EMPLOYEENAME");
	final String EMPLOYEEADDRESS = prop.getProperty("EMPLOYEEADDRESS");
	final String YEAR = prop.getProperty("YEAR");
	final String SUCCESS = prop.getProperty("SUCCESS");
	final String DATEOFJOINING = prop.getProperty("DATEOFJOINING");
	final String DATEOFBIRTH = prop.getProperty("DATEOFBIRTH");
	final String EXPERIENCE = prop.getProperty("EXPERIENCE");
	String insertSQL = prop.getProperty("insertSQL");
	Input ed = new Input();
	public Connection doConnection() throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeeDetails","root","Qwerty@123");
		logger.log(Level.INFO,"Connection established. Press Enter");
		return connection;
	}
	public void doInput() throws Exception
	{
		Connection connection = ed.doConnection();	
		boolean Flag1 = false, Flag2, Flag3, Flag4, Flag5;
		logger.log(Level.INFO,"Please enter the details in order with a pipe after every detail- Employee Name, Employee Address, Employee Date of Joining, Experience and Employee Date of Birth ");
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
			logger.log(Level.WARNING,"You have entered an invalid name.. Please re-enter the valid employee name");
			Flag1 = false;
		}
		String empAddress = "";
		if(splitStrings[1].equals(""))
		{
			logger.log(Level.WARNING,"The Employee Address cannot be null...Please re-enter the correct Employee Address...");
			Flag5 = false;
		}
		else
		{
			empAddress = splitStrings[1];
			Flag5 = true;
		}
		java.sql.Date convSQLDate = null;
		if(!(splitStrings[2].equals("")))
		{
			try
			{
				SimpleDateFormat  sdf = new SimpleDateFormat(YEAR);
				sdf.setLenient(false);
				java.util.Date convDate = sdf.parse(splitStrings[2]);
				convSQLDate = new java.sql.Date(convDate.getTime());
				Flag3 = true;
			}
			catch(Exception ex)
			{
				logger.log(Level.WARNING,"You have entered a wrong date.. please enter the correct date in YYYY-MM-DD Format");
				Flag3 = false;
			}
		}
		else
		{
			logger.log(Level.WARNING,"The date you have entered is null.. please enter the correct date format YYYY-MM-DD");
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
				logger.log(Level.WARNING,"You have entered an invalid experience. Please re-enter the experience in years only..");
				Flag2 = false;
			}
		}
		else
		{
			logger.log(Level.WARNING,"The experience you have entered is null.. please enter the correct experience in years");
			Flag2 = false;
		}
		java.sql.Date convSQLDate1 = null;
		if(!(splitStrings[4].equals("")))
		{
			try
			{
				SimpleDateFormat  sdf = new SimpleDateFormat(YEAR);
				sdf.setLenient(false);
				java.util.Date convDate1 = sdf.parse(splitStrings[4]);
				convSQLDate1 = new java.sql.Date(convDate1.getTime());
				Flag4 = true;
			}
			catch(Exception ex)
			{
				logger.log(Level.WARNING,"You have entered a wrong date.. please enter the correct date in YYYY-MM-DD Format");
				Flag4 = false;
			}
		}
		else
		{
			logger.log(Level.WARNING,"The date you have entered is null.. please enter the correct date format YYYY-MM-DD");
			Flag4 = false;
		}
			
		if(!Flag1 || !Flag2 || !Flag3 || !Flag4 || !Flag5)
		{
			logger.log(Level.WARNING, "You haven't entered the details properly... Please re-enter the details properly in order with pipe after every entry Employee Name, Employee Address, Date of Joining(YYYY-MM-DD), Experience(Years), Date of Birth(YYYY-MM-DD)");
		    ed.doInput();
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
			logger.log(Level.INFO, "rows created {0}", rows);
			pstmt.close();
			connection.close(); 
		}		
	}
	public String doTestInput(String[] data) 
	{
		boolean Flag1=false,Flag2 = false,Flag3 = false,Flag4 = false,Flag5 = false;
		try
		{
			for(int i=0; i<=data.length-1; i=i+2)
			{
				if(data[i].isEmpty() || data[i].equals(""))
					Flag1=true;
				else if(!(data[i].equals(EMPLOYEEID) || data[i].equals(EMPLOYEENAME) || data[i].equals(DATEOFJOINING)|| data[i].equals(EXPERIENCE) || data[i].equals(DATEOFBIRTH)))
					Flag1=true;
				else if(data[i].equals(EMPLOYEEID) && Integer.valueOf(data[i+1]) instanceof Integer)
					Flag2=true;
				else if(data[i].equals(EXPERIENCE) && Integer.valueOf(data[i+1]) instanceof Integer )
					Flag3=true;
				else if(data[i].equals(EMPLOYEENAME) && data[i+1].chars().allMatch(Character::isLetter))
					Flag4=true;
				else if(data[i].equals(DATEOFJOINING) || data[i].equals(DATEOFBIRTH))
				{
					DateFormat df = new SimpleDateFormat(YEAR);
					df.setLenient(false);
					df.parse(data[i+1]);
					Flag5=true;
				}
				else
					Flag1=true;
			}
			if(Flag2 || Flag3 || Flag4 || Flag5)
				return SUCCESS;
			else
				return "The data entered is of invalid data type. Please enter the correct data format...";
		}
		catch(Exception ex)
		{
			return "The data entered is of invalid data type. Please enter the correct data format...";	
		}
	}
							
	public  String[] dynamicQueryGenerator()
	{
		String sql1 = "Select * from where ", d2 = null;
		StringBuilder d1 = new StringBuilder();
		String[] queryWords;
		int j=0,l=0;
		logger.log(Level.INFO,"Please enter the Column names similar way as mentioned along with values seperated by a pipe if more than one column. \nThe column names are EmployeeID, EmployeeName, EmployeeAddress, DateOfJoining, Experience, DateOfBirth ");
		sc.nextLine();
		String queryInput = sc.nextLine();
		if(queryInput == null ||queryInput.equals(""))
		{
			logger.log(Level.WARNING, "You have not entered any input. Please re-enter the data again. The input cannot be null...");
			ed.dynamicQueryGenerator();
		}
		queryWords = queryInput.split("\\|");	
		String[] data = new String[queryWords.length];
		String[] d = new String[2*queryWords.length];
		
		for(int i=0;i<=queryWords.length-1; i++)
		{
				if(queryWords[i].isEmpty() || queryWords[i].equals(""))
				{
					logger.log(Level.SEVERE ,"The input cannot be null. Please re-enter the data with correct column name and column value");
					ed.dynamicQueryGenerator();
				}
				else
				{
					data = queryWords[i].split("\\=");	
					for(int k=0;l<2;j++,l++,k++)
						d[j]= data[k];
					l=0;
				}
		}	
		String returnQuery = ed.doTestInput(d);
		if(returnQuery.equals(SUCCESS))
		{
			if(queryWords.length >1)
			{
				queryInput = queryInput.replace("=", "= '");
				queryInput = queryInput.replace("|", "' and ");
				sql1 += queryInput + "'";
			}
			else
			{
				queryInput = queryInput.replace("=", "= '");
				sql1 += queryInput + "'";
			}
			for(String str : d)
			{
				d1 = d1.append(str);
				d1 = d1.append(" ");
			}
			d2 = d1.toString();
		}
		else
		{
			System.out.println(returnQuery);
			ed.dynamicQueryGenerator();
		}
		return new String[] {sql1,d2};
	}
	
}
