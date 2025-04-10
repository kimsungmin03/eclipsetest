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
	public int time;
	private static final String FILE_NAME = "C:\\Users\\kksm\\eclipse-workspace\\fund_application\\src\\user_history.txt";
	private String Name;
	private String User_id;
	private String User_pw;
	private int valance;
	HashMap<Stock_info, Integer> Owned_stock= new HashMap<Stock_info, Integer>();
	//user_info 만들기
    public User_info(String name, String id, String password) {
        this.User_id = id;
        this.Name = name;
        this.User_pw = password;
        
    }//유저 정보 객체 new로 만들기
    public User_info() {
    }
    public User_info(String name) {
    	this.Name=name;
    }
	
	String Get_Id() {
		return User_id;
	}
	String  Get_Pw() {
		return User_pw;
	}
	void add_stock(Stock_info name,int amount) {
		this.Owned_stock.put(name, amount);
	}

	
	public int get_stockamount(Stock_info Stock_name) {
		return this.Owned_stock.get(Stock_name);
	}
	
	public int get_usertime(){
		return this.time;
	}
	public void set_usertime(int time) {
		this.time=time;
	}
	
	
	int Get_valance() {
		return this.valance;
	}
	void set_valance(int money){
		this.valance=money;
	}
	void add_valance(int money){
		this.valance+=money;
	}
	public void save(Stock_info Stock_name,int amount,int stock_price, int time) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(Stock_name + "," +Integer.toString(amount) + "," + Integer.toString(stock_price) +"," + Integer.toString(time));  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
            writer.newLine();
        } catch (IOException e) {
            System.out.println("사용자 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }
	ArrayList<ArrayList<String>> History=new ArrayList();
	
	
	
	public List<String> load() { //users라는 이름의 List 객체를 선언하고, load 메서드를 호출
        List<User_info> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) { // 파일에서 한 줄씩 읽기(null이 아닐 경우 반복문 계속 실행)
                String[] parts = line.split(",");  // 쉼표로 문자열 분리
                if (parts.length == 4) { // 배열의 길이가 4인지 확인
                    users.add(parts[0]) ; // User 객체를 생성하여 리스트에 추가
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오는 동안 오류가 발생했습니다.");
        }
        return users;
    }
}
