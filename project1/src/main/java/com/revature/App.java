package com.revature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /*try(InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
        ) {
            System.out.println("Please enter your login information! Add a space between username and password");
            String loginInfo = br.readLine();
            Scanner scan = new Scanner(loginInfo);
            String username = scan.next();
            String password = scan.next();
            System.out.println("Credentials you entered: Username: " + username + " Password: " + password);
            System.out.println("Hello " + username + "! Please enter your reimbursment request. Add a space between your id, your manager's id, and the amount of money you want reimbursed.");
            String reimbursmentRequest = br.readLine();
            Scanner scanTwo = new Scanner(reimbursmentRequest);
            String employeeId = scanTwo.next();
            String managerId = scanTwo.next();
            String money = scanTwo.next();
            System.out.println("Credentials you entered: EmployeeId: " + employeeId + " ManagerId: " + managerId + " Money: " + money);
            System.out.println("Thank you for your reimbursment request!");
            scan.close();
            scanTwo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    	String url = "jdbc:postgresql://localhost:5432/ers";
    	String username = "postgres";
    	String password = "password";
    	try(Connection connection = DriverManager.getConnection(url, username, password))
    	{
    		Statement statement = connection.createStatement();
    		ResultSet rs = statement.executeQuery("select * from managers");
    		while(rs.next()) {
    			System.out.println(rs.getString("id"));
    			System.out.println(rs.getString("first_name"));
    			System.out.println(rs.getString("last_name"));
    			System.out.println(rs.getString("username"));
    			System.out.println(rs.getString("password"));
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
        }
    }
}
