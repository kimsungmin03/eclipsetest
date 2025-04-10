package fund_application;
import java.util.*;
public class Stock_info {
	public String Stock_name;
	public int Stock_price;
	public int Stock_amount;
	private int Pos_chance;
	private int Neg_chance; 
	public void Stock_info(String name, int price, int amount) {
		this.Stock_name=name;
		this.Stock_price=price;
		this.Stock_amount=amount;
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
	
}
