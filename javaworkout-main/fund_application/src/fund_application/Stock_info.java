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
	public final String FILE_NAME=".Stock_info"+GetStockName()+".txt";
	public String Stock_name;
	public int Stock_price;
	public int Stock_amount;
	private int time_index;
	public int Pos_chance;
	public int Neg_chance;
	public String Stock_information;
	private String GetStockName() {
		return Stock_name;
	}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock_info)) return false;
        return Stock_name.equals(((Stock_info)o).Stock_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Stock_name);
    }
	
	public Stock_info(String Stock_name, int Stock_Price) {
		this.Stock_name=Stock_name;
		this.Stock_price=Stock_Price;
		
	}
	public Stock_info() {
	}
	public int get_price() {
		return this.Stock_price;
	}
	public void loaded_stock(int pos, int neg, int price) {
		this.Pos_chance=pos;
		this.Neg_chance=neg;
		this.Stock_price=price;
	}
	public void loading_stock(int price) {
		this.Stock_price=price;
	}
    // ▼ equals & hashCode 추가 위치 ▼

	Stock_info(int Stock_value) {
		this.Stock_price=Stock_value;
	}
	public Stock_info(String Stock_name) {
		this.Stock_name=Stock_name;
	}
	//주식 변동률
	void Chance_change(int Change_value){
		if(Change_value>0) {
			this.Pos_chance+=Change_value;
		}
		else {
			this.Neg_chance+=Change_value;
		}
	}
	
	public void save(String Stock_name,int stock_price, Boolean ini) {
		if (ini){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(Stock_name + "," +Integer.toString(stock_price));  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Stock_info를 저장하는 동안 오류가 발생했습니다.");
        }
        }else {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true))) {
            writer.write(Stock_name + "," +Integer.toString(stock_price));  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Stock_info를 저장하는 동안 오류가 발생했습니다.");
        }
		}
    }
	
	
    public List<Stock_info> load(Stock_info stock) {
        List<Stock_info> stocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) { // 파일에서 한 줄씩 읽기
                String[] parts = line.split(","); // 쉼표로 문자열 분리
                if (parts.length == 2) {
                    String name = parts[0];
                    int price = Integer.parseInt(parts[1]);
                    if(name.equals(stock.Stock_name)) {
                    	stock.loading_stock(price);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Stock_info를 불러오는 동안 오류가 발생했습니다.");
        }
        return stocks;
    }
    private List<PriceChangeListener> listeners = new ArrayList<>();

    public void addPriceChangeListener(PriceChangeListener l) {
        listeners.add(l);
    }

    public void Stock_change(int pos, int neg) {
        // 기존 로직
    	double maxRate = (pos + neg) / 100.0; // 예: pos=3, neg=2 → 0.05(5%)
	    double rate = (Math.random() * 2 - 1) * maxRate; // -maxRate ~ +maxRate
	    this.Stock_price = (int)(this.Stock_price * (1 + rate));
	    System.out.println(this.Stock_price);
        notifyPriceChange();
    }

    private void notifyPriceChange() {
        listeners.forEach(l -> l.priceChanged(this.Stock_price));
    }

    public interface PriceChangeListener {
        void priceChanged(int newPrice);
    }


}

