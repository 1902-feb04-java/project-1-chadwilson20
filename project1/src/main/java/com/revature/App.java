package com.revature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try(InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
        ) {
            System.out.println("Please enter your login information! Add a space between username and password");
            String loginInfo = br.readLine();
            Scanner scanner = new Scanner(loginInfo);
            String username = scanner.next();
            String password = scanner.next();
            System.out.println("Credentials you entered: Username: " + username + " Password: " + password);
            System.out.println("Hello " + username + "! Please enter your reimbursment request. Add a space between your id, your manager's id, and the amount of money you want reimbursed.");
            String reimbursmentRequest = br.readLine();
            scanner = new Scanner(reimbursmentRequest);
            String employeeId = scanner.next();
            String managerId = scanner.next();
            String money = scanner.next();
            System.out.println("Credentials you entered: EmployeeId: " + employeeId + " ManagerId: " + managerId + " Money: " + money);
            System.out.println("Thank you for your reimbursment request!");
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
