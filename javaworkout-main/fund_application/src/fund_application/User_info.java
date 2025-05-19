package fund_application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User_info {
	private final int base=100000;
	public int time;
	private String Name;
	private String User_id;
	private String User_pw;
	private int balance;
	HashMap<String, Integer> Owned_stock = new HashMap<>();
	//user_info 만들기
    public User_info(String name, String id, String password) {
        this.User_id = id;
        this.Name = name;
        this.User_pw = password;
        this.balance=base;
        this.time=0;
        
    }//유저 정보 객체 new로 만들기
    public User_info(String name, String id, String password, int balance, int time) {
        this.User_id = id;
        this.Name = name;
        this.User_pw = password;
        this.balance=balance;
        this.time=time;
        
    }//유저 정보 객체 new로 만들기
    public void add_turn() {
    	this.time+=1;
    	
    }
    public User_info() {
    }
    public User_info(String name) {
    	this.User_id=name;
    }
	String Get_name() {
		return this.Name;
	}
	String Get_Id() {
		return this.User_id;
	}
	String  Get_Pw() {
		return this.User_pw;
	}
	void add_stock(Stock_info name, int amount) {
	    this.Owned_stock.put(name.Stock_name, amount);
	}

	
	
	
	public int get_time(){
		return this.time;
	}
	public void set_usertime(int time) {
		this.time=time;
	}
	
	
	int Get_balance() {
		return this.balance;
	}
	void set_balance(int money){
		this.balance=money;
	}
	void add_balance(int money){
		this.balance+=money;
	}
	
		class UserStock{
			public UserStock() {}
			public String FILE_NAME="./user_history_" + Get_Id() + ".txt";;
			
			String Stockname;
			int amount;
			boolean fav;
			int date;
			int stockprice;
			String type;
			public void UserStock(int date, String Stockname, int amount, int price, String type){
		        this.Stockname = Stockname;
		        this.amount = amount;
		        this.date = date;
		        this.stockprice=price;
		        this.type=type;
			}
			public String getStockname() {
				return this.Stockname;
			}
			public int getAmount() {
				return this.amount;
			}
			public void save() {
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
		            writer.write(Stockname + "," +Integer.toString(amount) +"," + Integer.toString(date)+"," + Integer.toString(stockprice)+","+type);  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
		            writer.newLine();
		        } catch (IOException e) {
		            System.out.println("사용자 정보를 저장하는 동안 오류가 발생했습니다.");
		        }
		    }
			ArrayList<ArrayList<String>> History=new ArrayList();
			public UserStock(String stock_name, String amount , String time, String stockprice, String type) {
				this.Stockname = stock_name;
			    this.amount = Integer.parseInt(amount);
			    this.date = Integer.parseInt(time);
			    this.stockprice = Integer.parseInt(stockprice);
			    this.type=type;
				
			}
			
			
			
			public UserStock(int time, String stock_name, int amount, int price, String type) {
				// TODO Auto-generated constructor stub
				this.Stockname = Stockname;
		        this.amount = amount;
		        this.date = date;
		        this.stockprice=price;
		        this.type=type;
			}
			public List<UserStock> load() { //users라는 이름의 List 객체를 선언하고, load 메서드를 호출
		        List<UserStock> users = new ArrayList<>();
		        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
		            String line;
		            while ((line = reader.readLine()) != null) { // 파일에서 한 줄씩 읽기(null이 아닐 경우 반복문 계속 실행)
		                String[] parts = line.split(",");  // 쉼표로 문자열 분리
		                if (parts.length == 5) { // 배열의 길이가 4인지 확인
		                    users.add(new UserStock(parts[0], parts[1], parts[2], parts[3], parts[4])) ; // User 객체를 생성하여 리스트에 추가
		                }
		            }
		        } catch (IOException e) {
		            System.out.println("사용자 정보를 불러오는 동안 오류가 발생했습니다.");
		        }
		        return users;
		    }
			
		}

		public void set_username(String name) {
			this.Name=name;
		}
		public void Set_Pw(String password) {
			this.User_pw=password;
		}
		

}
