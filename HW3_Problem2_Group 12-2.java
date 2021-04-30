import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;
public class sample {
public static void main(String[] args) throws SQLException {
// Connect to database
final String hostName = "ward0004-sql-server.database.windows.net";
final String dbName = "cs-dsa-4513-sql-db";
final String user = "ward0004";
final String password = "";
final String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",hostName, dbName, user, password);
try (final Connection connection = DriverManager.getConnection(url)) {
final String schema = connection.getSchema(); System.out.println("Successful connection -Schema:" + schema);
//Variables for the lower/upper bound for option 1 and User Input Prompts and Scanners
int quit = 0;
int lower = 0;
int upper = 0;
String prompt = "Select 1 for option 1, Select 2 for option 2, Select 3 to display performers or select 4 to quit.";
String prompt2 = "Enter pid, pname, and age";
String prompt3 = "Enter pid, pname, age and did";
Scanner myObj = new Scanner(System.in);  // Create a Scanner object
Scanner Obj = new Scanner(System.in);  // Create a Scanner object
//Option 4 
while (quit != 4) {

System.out.println(prompt);
int userInput = myObj.nextInt();  // Read user input
if (userInput == 4){
	quit = 4;
	System.exit(0);
}
//Option 3 Modified from the given code to output our version of Performer Table
if (userInput == 3) {
	
	 System.out.println("Query data example:");
	 System.out.println("=========================================");
	 final String selectSql = "SELECT * FROM Performer;";
	 try (final Statement statement = connection.createStatement();
	 final ResultSet resultSet = statement.executeQuery(selectSql)) {
	 System.out.println("Contents of the Performer table:");
	 while (resultSet.next()) {
	 System.out.println(String.format("%s | %s | %s | %s",
	 resultSet.getString(1),
	 resultSet.getString(2),
	 resultSet.getString(3),
	 resultSet.getString(4)));

	 }

	 }
	
}
//Option 1
if (userInput == 1) {
System.out.println(prompt2);
int p_id = myObj.nextInt(); 	
String p_name = Obj.nextLine(); 	
int p_age = myObj.nextInt(); 	
//Set lower and upper bound for ages
lower = p_age - 10;
upper = p_age + 10;
//call to the Stored Procedure, passing in the lower and upper bound for age and receive the return value years_of_experience
CallableStatement proc = connection.prepareCall("{ ? = call exp_avg(?, ?) }");
//set output parameter type to INT
proc.registerOutParameter(1,  Types.INTEGER);
//set input parameters
proc.setInt(2, lower);
proc.setInt(3,  upper);
//execute the statment
proc.execute();
//set experience to return value
int experience = proc.getInt(1);
//if nothing is returned calculate age
if (experience == 0) {
	experience = p_age - 18;
			
}
//if experience is less than 0 or greater than the age correct it
if(experience <= 0 || experience >= p_age) {
	experience = 1;
}
//call to the Stored Procedure to insert into the table
CallableStatement statement = connection.prepareCall("{call insert1(?, ?, ?, ?)}");
//set input parameters
statement.setInt(1, p_id);;
statement.setString(2, p_name);
statement.setInt(3, experience);
statement.setInt(4, p_age);
//execute and close the command
statement.execute();
statement.close();
}

//option2
if (userInput == 2) {
	
System.out.println(prompt3);
int p_id = myObj.nextInt(); 	
String p_name = Obj.nextLine(); 	
int p_age = myObj.nextInt(); 
int did = myObj.nextInt(); 
	
//call to the Stored Procedure, passing in the did and receive the return value years_of_experience
CallableStatement proc = connection.prepareCall("{ ? = call exp_avg2(?) }");
//set output parameter type to INT
proc.registerOutParameter(1,  Types.INTEGER);
//set input parameter
proc.setInt(2, did);
proc.execute();
//set experience to return value
int experience = proc.getInt(1);
//if nothing is returned set experience
if (experience == 0) {
	experience = p_age - 18;
			
}
//if experience is less than 0 or greater than age correct it
if(experience <= 0 || experience >= p_age) {
	experience = 1;
}

//insert into table passing in the 4 arguments	
CallableStatement statement = connection.prepareCall("{call insert1(?, ?, ?, ?)}");
//set input parameters
statement.setInt(1, p_id);;
statement.setString(2, p_name);
statement.setInt(3, experience);
statement.setInt(4, p_age);
//execute and close
statement.execute();
statement.close();
		
}
}
}
}
}