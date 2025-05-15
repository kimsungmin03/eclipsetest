package fund_application;

import java.util.HashMap;
import java.util.List;

import fund_application.User_info.UserStock;

public class Trade {
	//주식 바로구매
	public void Buy_stock(User_info User_id, Stock_info stock_name, int amount){
		
		int valance=0;
		int stock_price=0;
		int time =User_id.get_time();
		
		valance=User_id.Get_balance();//현재 잔고 가져오기
		stock_price=stock_name.get_price();//주식 현재 거래가 가져오기
		
		if(valance>stock_price*amount) {//주식 구매액보다 잔고가 많은지 확인
			User_id.set_balance(valance-stock_price*amount);//주식 가격만큼 잔고에서 빼기
			User_id.add_stock(stock_name, amount);//주식 추가
			make_history(User_id, stock_name, Integer.toString(amount), Integer.toString(time), "buy");
			
			System.out.println(User_id.Get_Id()+"님의 주식에"+stock_name.Stock_name+"을추가했습니다");
		}
		else {
			System.out.println("잔액이 부족합니다.");
		}
	}
	//주식 에약구매
//	public void Buy_stock(User_info User_id,Stock_info stock_name, int amount, int price) {
//		
//		int time =User_id.get_usertime();
//		int valance=0;
//		int stock_price=0;
//		
//		valance=User_id.Get_valance();//현재 잔고 가져오기
//		stock_price=stock_name.get_price();//주식 현재 거래가 가져오기
//		
//		if(valance>=price*amount) {
//			User_id.set_valance(valance-price*amount);
//			int purchase_price=price*amount;
//			if(price>stock_price) {
//				System.out.println("예악구매완료");
//				User_id.add_valance(purchase_price-stock_price*amount);
//				User_id.add_stock(stock_name, amount);
//				User_id.save(stock_name, amount, stock_price, time);
//			}
//		}
//		else {
//			System.out.println("잔액이 부족합니다.");
//		}
//	}
	
	
	
	public void Sell_stock(User_info User_id,Stock_info stock_name, int amount) {
		int having_stock;
		CheckingStock check=new CheckingStock();
		HashMap<String, Integer> stockList = check.CheckStock(User_id);

		int stock_price=0;
		int time =User_id.get_time();
		String stockname=stock_name.Stock_name;
		stock_price=stock_name.get_price();
		having_stock=stockList.getOrDefault(stockname,0);
		System.out.println(having_stock);
		if(having_stock>=amount) {
			User_id.add_balance(stock_price*amount);
			User_id.add_stock(stock_name, having_stock-amount);
			System.out.println(stock_price+"에 판매완료");

			make_history(User_id, stock_name, Integer.toString(amount), Integer.toString(time), "sell");
			System.out.println(having_stock+"개 보유중");
		}
		
	}
	
	




	//	public void Sell_stock(User_info User_id,Stock_info stock_name, int amount, int price) {
//
//
//		int having_stock=0;
//		int stock_price=0;
//		int time =User_id.get_usertime();
//		
//		stock_price=stock_name.get_price();
//		having_stock=User_id.get_stockamount(stock_name);
//		if(having_stock>=amount) {
//			
//			User_id.add_stock(stock_name, having_stock-amount);
//			if(price<stock_price) {
//				
//				User_id.add_valance(stock_price*amount);
//				System.out.println(stock_price+"에 판매완료");
//
//				User_id.save(stock_name, amount, stock_price, time);
//			}
//		}
//		
//	}
	private List<UserStock> load_history(User_info username) {
		User_info.UserStock name=username.new UserStock();
		List<UserStock> stock=name.load();
		return stock;
	}
	
	private void make_history(User_info username,Stock_info stockname, String amount, String time, String type) {
		User_info.UserStock name = username.new UserStock();
		
			name.UserStock(Integer.parseInt(time), stockname.Stock_name, Integer.parseInt(amount),stockname.Stock_price, type);

		name.save();
	}
	
}
