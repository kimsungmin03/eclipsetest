package fund_application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import fund_application.User_info.UserStock;
import javafx.application.Application;

public class main_program {
    static String currunt_user;
    static String id;
    static String pw;
    static int balance;
    static int time = 0;
  
    public static void main(String[] args) {
        Register_main reg = new Register_main();
        reg.Registermain();
        System.out.println(currunt_user + "님 환영합니다");
        
        User_info user = loadUserData();
        Register_main.removeDuplicateUsersById();
        
        System.out.println(currunt_user + "/" + id + "/" + pw + "/" + balance + "/" + time);

        // 주식 거래 시스템 초기화
        Time timer = new Time();
        Stock_info st = new Stock_info();
        Vector<Stock_info> Stocklst = initStockList();
        Vector<Stock_info> stocks = loadStockList(Stocklst);
        
        launchStockGUI(user, stocks);
        // 메인 루프
        executeTradingLoop(user, stocks);
    }

    private static Vector<Stock_info> initStockList() {
        Vector<Stock_info> stocks = new Vector<>();
        String[] stockNames = {"영카콜라", "영플", "테슬웅", "웅비디아", "하이비", "삼숭", "한하", "카카우"};
        for (String name : stockNames) {
            stocks.add(new Stock_info(name));
        }
        return stocks;
    }

    private static void executeTradingLoop(User_info user, Vector<Stock_info> stocks) {
        Scanner sc = new Scanner(System.in);
        Trade td = new Trade();
        StockList sl = new StockList();
        
        while (true) {
            System.out.println("[1] 구매 [2] 판매 [3] 턴넘기기 [0] 종료");
            int choice = getValidInput(sc);
            
            processUserChoice(user, stocks, td, sl, choice, sc);
            if (choice == 0) break;
        }
    }

    private static int getValidInput(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("숫자를 입력하세요.");
                sc.nextLine();
            }
        }
    }

    private static void processUserChoice(User_info user, Vector<Stock_info> stocks, 
                                        Trade td, StockList sl, int choice, Scanner sc) {
        switch (choice) {
            case 1 -> handleBuy(user, td, sc);
            case 2 -> handleSell(user, td, sc);
            case 3 -> advanceTurn(stocks, sl);
            case 0 -> System.out.println("시스템 종료");
        }
    }

    private static Object handleSell(User_info user, Trade td, Scanner sc) {
		// TODO Auto-generated method stub
    	System.out.print("구매할 주식 번호: ");
        int stockIdx = sc.nextInt();
        System.out.print("구매 수량: ");
        int amount = sc.nextInt();
        // 실제 구현 시 주식 선택 로직 추가
        td.Sell_stock(user, new Stock_info(), amount);
		return null;
	}

	private static void handleBuy(User_info user, Trade td, Scanner sc) {
        System.out.print("구매할 주식 번호: ");
        int stockIdx = sc.nextInt();
        System.out.print("구매 수량: ");
        int amount = sc.nextInt();
        // 실제 구현 시 주식 선택 로직 추가
        td.Buy_stock(user, new Stock_info(), amount);
    }

    private static void advanceTurn(Vector<Stock_info> stocks, StockList sl) {
        boolean initialSave = true;
        for (Stock_info stock : stocks) {
            stock.Stock_change(stock.Pos_chance, stock.Neg_chance);
            sl.save(stock.Stock_name, stock.Pos_chance, stock.Neg_chance, stock.Stock_price, initialSave);
            initialSave = false;
        }
    }

    private static User_info loadUserData() {
        return new User_info(currunt_user, id, pw, balance, time);
    }

    private static Vector<Stock_info> loadStockList(Vector<Stock_info> Stocklst) {
        // 주식 데이터 로드 구현
    		for(Stock_info stock : Stocklst) {
    			StockList sl=new StockList();
    			sl.load(stock);
    		}
        return new Vector<>();
    }

    private static void launchStockGUI(User_info user, Vector<Stock_info> stocks) {
    	StockSimulatorEx.staticUser = user;
    	StockSimulatorEx.staticStocks = stocks;
        new Thread(() -> 
            Application.launch(StockSimulatorEx.class, 
                new String[]{user.Get_Id(), stocks.toString()})
        ).start();
    }
}
