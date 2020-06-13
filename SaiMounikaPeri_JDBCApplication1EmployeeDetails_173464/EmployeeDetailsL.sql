SELECT * FROM employeedetails.employee;

Create table EmployeeDetails (
EmployeeID int NOT NULL AUTO_INCREMENT,
EmployeeName varchar(255) NOT NULL,
EmployeeAddress varchar(255) NOT NULL,
DateOfJoining DATE NOT NULL,
Experience int NOT NULL,
DateOfBirth DATE NOT NULL ,
Primary Key (EmployeeID)
) 