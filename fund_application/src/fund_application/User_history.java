package fund_application;

public class User_history {
	String Stockname;
	int amount;
	boolean fav;
	int buydate;
	int selldate;
	public void buyhistory(int date, String Stockname, int amount){
        this.Stockname = Stockname;
        this.amount = amount;
        this.fav = fav;
        this.buydate = date;
	}
	public void sellhistory(int date, String Stockname, int amount){
        this.Stockname = Stockname;
        this.amount = amount;
        this.fav = fav;
        this.selldate = date;
	}
}
