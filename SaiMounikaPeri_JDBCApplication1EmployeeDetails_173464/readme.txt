1. All the SQL Statements are mentioned in the code itself (Eg: Insert, Select, Update and Delete)
2. Creating table query used initially to create a table in a database is attached as sql file in this zip folder
Flow of code:
----------------
1. The code starts with giving options to the user in selecting his choice to insert, update,delete or retrieve the data.
2. If user chooses insert the code makes a connection to mysql database and starts inserting the row using preparedstatement.
3. If user chooses to update the code then update method is triggered and Employee Address is udated using the EmployeeID
4. If user choosed to delete the code then delete method is riggered and user entered EmployeeID is deleted
5. If User chooses to retrieve the data then data is selected based on the employee ID given by the user.
JUnit Test:
------------
The Junit test calls initially the connection method and establishes a connection to the database. Then the insert method is triggered and no of rows inserted ae returned which is trested based on the assertEquals method.
