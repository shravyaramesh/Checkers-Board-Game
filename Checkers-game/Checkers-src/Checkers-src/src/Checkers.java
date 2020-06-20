//A player has 12 checkers each.
//The state of the checkers is maintained in order to keep track of the abilities of 
//the chekers and the winner
public class Checkers {
	private String colour;
	private int snumber;
	private boolean isMan;
	private boolean isAlive;
	
	Checkers(String colour,int number){
		this.colour=colour;
		this.snumber=number;
		isMan=true;
		isAlive=true;
	}
	
	boolean getIsMan(){
		return isMan;
	}
	
	void setIsMan(boolean flag){
		isMan=flag;
	}
	
	boolean getIsAlive(){
		return isAlive;
	}
	
	void setIsAlive(boolean flag) {
		isAlive=flag;
	}
	
	int getSNumber() {
		return snumber;
	}
	void setSNumber(int n){
		snumber=n;		
	}
	
	String getColour() {
		return colour;
	}
	
}
