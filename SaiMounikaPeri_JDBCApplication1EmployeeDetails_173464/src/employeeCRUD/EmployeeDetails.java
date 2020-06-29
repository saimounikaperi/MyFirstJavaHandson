package employeeCRUD;

import java.io.FileInputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class EmployeeDetails
{
	static Properties prop = new Properties();
	public void load() throws Exception
	{
			FileInputStream fs = new FileInputStream("Constants.properties");
			prop.load(fs);
	}
	static Object o = prop.getProperty("logger");
	static Logger logger = (Logger)o;
	static Object o4 = prop.getProperty("sc");
	static Scanner sc = (Scanner)o4;
	public static void main(String[] args)
	{
		Properties prop = new Properties();
		try
		{
			prop.load(new FileInputStream("Constants.properties"));
			
		}
		catch(Exception ex)
		{
			
		}
		Operations o = new Operations();
		int UserChoice = 0;
		try
		{
			while(UserChoice!=5)
			{
				logger.log(Level.INFO,"Please enter the equivalent choice options listed below:\n1. Create a new Employee\n2. Update Employee Information\n3. Delete and existing employee Information\n4. Retrieve the data of the employee\n5. Exit ");
				UserChoice = sc.nextInt();
				if(UserChoice == 1)
					o.insertSQL();
				else if(UserChoice == 2)
					o.updateSQL();
				else if(UserChoice == 3)
					o.deleteSQL();
				else if(UserChoice == 4)
					o.retrieveSQL();
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
