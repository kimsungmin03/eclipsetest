package fund_application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Stock_history {
	String StockName;
	int shp;
	int Stock_time;
	public static final String FILE_NAME=".Stock_history.txt";
	public Stock_history(String StockName, String shp, String Stock_time) {
		this.StockName=StockName;
		this.shp=Integer.parseInt(shp);
		this.Stock_time=Integer.parseInt(Stock_time);
	}
	private static void Stock_save(String Stock_name, String shp, String Stock_time) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(Stock_name + "," + shp+ "," + Stock_time );  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
            writer.newLine();
            System.out.println("Stock_history를 저장하였습니다.");
        } catch (IOException e) {
            System.out.println("Stock_history를 저장하는 동안 오류가 발생했습니다.");
        }
    }
    static List<Stock_history> load() { //users라는 이름의 List 객체를 선언하고, load 메서드를 호출
        List<Stock_history> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) { // 파일에서 한 줄씩 읽기(null이 아닐 경우 반복문 계속 실행)
                String[] parts = line.split(",");  // 쉼표로 문자열 분리
                if (parts.length == 3) { // 배열의 길이가 3인지 확인
                    users.add(new Stock_history(parts[0], parts[1], parts[2])); // User 객체를 생성하여 리스트에 추가
                }
   
            }
        } catch (IOException e) {
            System.out.println("Stock_history 정보를 불러오는 동안 오류가 발생했습니다.");
        }
        return users;
    }
}
