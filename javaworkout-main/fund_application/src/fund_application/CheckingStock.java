package fund_application;

import java.util.List;
import java.util.Collections;
import java.util.HashMap;

import fund_application.User_info.UserStock;

public class  CheckingStock {
	public void Checkingstock() {
	}
	public void Checkingstock(User_info user) {
	}	
	String Stockname;
	int amount;
	int date;
	public HashMap<String, Integer> CheckStock(User_info user) {
	    HashMap<String, Integer> Stock_list = new HashMap<>();
	    User_info.UserStock name = user.new UserStock();
	    List<UserStock> lst = name.load();
	    Collections.reverse(lst);
	    for (UserStock stock : lst) {
	        String stockName = stock.getStockname();
	        int amount = stock.amount;
	        int current = Stock_list.getOrDefault(stockName, 0);

	        if ("buy".equals(stock.type)) {
	            Stock_list.put(stockName, current + amount);
	        } else if ("sell".equals(stock.type)) {
	            Stock_list.put(stockName, current - amount);
	        } else {
	            Stock_list.put(stockName, current + amount); // 기타 타입은 buy로 처리
	        }
	    }
	    return Stock_list;
	}
}
