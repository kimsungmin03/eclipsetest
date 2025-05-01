package fund_application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Stock_info {
	public void makestock() {
		this.Stock_name="태스트주식";
		this.Stock_price=100;
		this.Stock_information="태스트주식";
	}
	public static final String FILE_NAME=".Stock_info.txt";
	public String Stock_name;
	public int Stock_price;
	public int Stock_amount;
	private int Pos_chance;
	private int Neg_chance;
	private String Stock_information;
	public Stock_info(String Stock_name, int Stock_Price, String Stock_information) {
		this.Stock_name=Stock_name;
		this.Stock_price=Stock_Price;
		this.Stock_information=Stock_information;
	}
	public Stock_info() {
	}
	public int get_price() {
		return this.Stock_price;
	}

	
	Stock_info(int Stock_value) {
		this.Stock_price=Stock_value;
	}

	int Get_stockprice() {
		return this.Stock_price;
	}
	//주식 변동률
	void Chance_change(int Change_value){
		if(Change_value>0) {
			this.Pos_chance=this.Pos_chance+Change_value;
		}
		else {
			this.Neg_chance=this.Neg_chance-Change_value;
		}
	}
	
	
	public void save(String Stock_name,int stock_price, String Stock_info) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(Stock_name + "," +Integer.toString(stock_price) + "," + Stock_info);  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
            writer.newLine();
        } catch (IOException e) {
            System.out.println("사용자 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }
	
	
    public List<Stock_info> load() {
        List<Stock_info> stocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) { // 파일에서 한 줄씩 읽기
                String[] parts = line.split(","); // 쉼표로 문자열 분리
                if (parts.length == 3) {
                    String name = parts[0];
                    int price = Integer.parseInt(parts[1]);
                    String info = parts[2];
                    stocks.add(new Stock_info(name, price, info)); // 객체 생성 후 추가
                    main_program.Add_List(name);
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오는 동안 오류가 발생했습니다.");
        }
        return stocks;
    }
    
}

