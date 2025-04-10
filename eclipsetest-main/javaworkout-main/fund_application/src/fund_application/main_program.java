package fund_application;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.math.*;
public class main_program {

	public static void main(String[] args) {
		
		Register_main reg=new Register_main();
		reg.Registermain();
		System.out.println(currunt_user);
		User_info user=new User_info(currunt_user);
		Time timer=new Time();
		
		
	}
	static String currunt_user;
	int time;
	int hour;
	int day=1;
	int month=1;
	int year=5000;
	int monthly[]= {31,28,31,30,31,30,31,31,30,31,30,31};
	

}
