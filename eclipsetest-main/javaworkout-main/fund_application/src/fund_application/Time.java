package fund_application;

public class Time {
	int time;
	void Set_time(int time) {
		this.time=time;
	}
	void Add_time() {
		this.time+=1;
	}
	int hour;
	int day=1;
	int month=1;
	int year=5000;
	int monthly[]= {31,28,31,30,31,30,31,31,30,31,30,31};
	public void Cal_time(int time){
		this.hour=time%24;
		this.day=(time/24);
		int day=this.day;
		for(int i=0;i<12;i++) {
			if(day-monthly[i]>0) {
				day-=monthly[i];
				month+=1;
				if(month>12) {
					month-=12;
					year+=1;
				}
			}
			if(day>31&&i==11) {
				i=0;
			}
		}
		this.hour=hour;
		this.day=day;
		this.month=month;
		this.year=year;	
	}
	public void time() {
		
	}
	public int Get_time() {return this.time;}
	public int Get_year() {return this.year;}
	public int Get_month() {return this.month;}
	public int Get_day() {return this.day;}
	public int Get_hour() {return this.hour;}
	
}
