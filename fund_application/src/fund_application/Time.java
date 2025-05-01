package fund_application;

public class Time {
	int time;
	int day;
	int year;
	void Set_time(int time) {
		this.time=time;
	}
	void Add_time() {
		this.time+=1;
	
	}// 기본= 1일단위
	public int init_time(int given_time) {
		
		int userTurn=0;
		this.day=(given_time%60)+1;
		this.year=(int)(given_time/60);
		return userTurn;
	}
	public void time() {
		
	}
	public int Get_time() {return this.time;}
	public int Get_year() {return this.year;}
	public int Get_day() {return this.day;}
	
}
