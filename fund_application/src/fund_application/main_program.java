package fund_application;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fund_application.User_info.StockHis;

import java.math.*;
public class main_program {

	public static void main(String[] args) {
		
		Register_main reg=new Register_main();
		reg.Registermain();
		System.out.println(currunt_user);
		User_info user=new User_info(currunt_user);
		Time timer=new Time();
		Stock_info Stockinfo=new Stock_info();
		
		Stock_info st=new Stock_info();
		st.makestock();
		
		Scanner sc=new Scanner(System.in);
		System.out.println("1.구매 2.판매");
		int choice=sc.nextInt();
		if(choice==1) {
			
		}
		
	}
	static String currunt_user;
	public static List<String> stockList = new ArrayList<>();
	public static void Add_List (String Stock_name) {
		stockList.add(Stock_name);
	}
	public List<String> load_list(){
		return this.stockList;
	}

}
