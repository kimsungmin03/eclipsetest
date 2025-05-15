package fund_application;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import fund_application.User_info.UserStock;

import java.math.*;
public class main_program {
	static String currunt_user;
	static String id;
	static String pw;
	static int balance;
	static int time=0;
	public static void main(String[] args) {
		
		Register_main reg=new Register_main();
		reg.Registermain();
		System.out.println(currunt_user+"님 환영합니다");
		User_info user=new User_info(currunt_user, id, pw, balance, time);
		System.out.println(currunt_user+"/"+ id+"/"+ pw+"/"+ balance+"/"+ time);
		
		Time timer=new Time();
		Stock_info Stockinfo=new Stock_info();
		Stock_info st=new Stock_info();
		Vector<Stock_info> Stocklst=new Vector<>();
		st.makestock();
		Trade td=new Trade();
		
		Scanner sc=new Scanner(System.in);
		Stock_info StockA=new Stock_info("영카콜라");
		Stock_info StockB=new Stock_info("영플");
		Stock_info StockC=new Stock_info("테슬웅");
		Stock_info StockD=new Stock_info("웅비디아");
		Stock_info StockE=new Stock_info("하이비");
		Stock_info StockF=new Stock_info("삼숭");
		Stock_info StockG=new Stock_info("한하");
		Stock_info StockH=new Stock_info("카카우");

		Stocklst.add(StockA);
		Stocklst.add(StockB);
		Stocklst.add(StockC);
		Stocklst.add(StockD);
		Stocklst.add(StockE);
		Stocklst.add(StockF);
		Stocklst.add(StockG);
		Stocklst.add(StockH);
		StockList sl=new StockList();
		 
		while (true){
		    System.out.println("[1] 구매 [2] 판매 [3] 턴넘기기 [0] 종료");
		    int choice = -1;
		    boolean validInput = false;
		    boolean end=false;
		    while (!validInput) {
		        try {
		            choice = sc.nextInt();
		            sc.nextLine(); // 버퍼 비우기
		            validInput = true;
		        } catch (Exception e) {
		            System.out.println("숫자를 입력하세요."); // 오류 메시지
		            sc.nextLine(); // 잘못된 입력 버퍼 비우기

		        }
		    }
		    for (Stock_info stock : Stocklst) {
				sl.load(stock);		
			}

			switch (choice) {
			case 1:
				td.Buy_stock(user, st, 1);
				break;
			case 2:
				td.Sell_stock(user, st, 1);
				break;
			case 3:
				user.add_turn();
				Boolean ini=true;
				 for (Stock_info stock : Stocklst) {
    				stock.Stock_change(stock.Pos_chance, stock.Neg_chance); // 각 객체의 메서드 실행
    				sl.save(stock.Stock_name, stock.Pos_chance, stock.Neg_chance, stock.Stock_price, ini);
    				ini=false;

				}

				 
				break;
			case 0:
				end=true;
				break;
			}
			Register_main.save(user);

			if (end==true)
				break;
		}
	}
	private static void Stock_event() {
		// TODO Auto-generated method stub
		
	}
	public static List<Stock_info> stockList = new ArrayList<>();
	public static void Add_List(String name) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
