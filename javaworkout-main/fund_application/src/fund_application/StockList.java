package fund_application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockList {
	final String FILE_NAME="StockList.txt";
	String StockName;
	int neg_chance;
	int pos_chance;
	StockList(String Stock_name, int pos_chance, int neg_chance){
		this.StockName=Stock_name;
		this.pos_chance=pos_chance;
		this.neg_chance=neg_chance;
	}
	public StockList() {
		// TODO Auto-generated constructor stub
	}
	public void save(String Stock_name,int pos_chance, int neg_chance, int stock , Boolean ini) {
		if (ini) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(Stock_name + "," +Integer.toString(pos_chance) + "," + Integer.toString(neg_chance)+","+Integer.toString(stock));  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
            

            writer.newLine();
        } catch (IOException e) {
            System.out.println("사용자 정보를 저장하는 동안 오류가 발생했습니다.");
        }
		}else {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true))) {
	            writer.write(Stock_name + "," +Integer.toString(pos_chance) + "," + Integer.toString(neg_chance)+","+Integer.toString(stock));  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
	            writer.newLine();
	        } catch (IOException e) {
	            System.out.println(Stock_name+"사용자 정보를 저장하는 동안 오류가 발생했습니다.");
	        }
			
		}
    }
	
	
    public List<StockList> load(Stock_info stock) {
        List<StockList> stocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) { // 파일에서 한 줄씩 읽기
                String[] parts = line.split(","); // 쉼표로 문자열 분리
                if (parts.length == 4) {
                	
                    String name = parts[0];
                    int pos = Integer.parseInt(parts[1]);
                    int neg = Integer.parseInt(parts[2]);
                    int price= Integer.parseInt(parts[3]);
                    if(name.equals(stock.Stock_name)) {
                    	stock.loaded_stock(pos, neg,price);

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오는 동안 오류가 발생했습니다.");
        }
        return stocks;
    }
}
