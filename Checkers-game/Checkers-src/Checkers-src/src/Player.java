import java.util.ArrayList;
/* While creating the object of the two players
 * score is initially set to '0' and 
 * player "red" takes the first turn */
public class Player {

	private String colour;
	private int score;
	boolean turn;
	private int noOfCheckers;
	
	ArrayList<Checkers> checkers=new ArrayList<Checkers>();
	ArrayList<String> moves=new ArrayList<String>();
	
	Player(String colour){
		score=0;
		noOfCheckers=12;
		this.colour=colour;
		int dest;
		
		if(colour.equalsIgnoreCase("Red")) {
			turn=true; 
			dest=21;
		}
		else {
			turn=false;
			dest=1;
		}
		createCheckers(dest,colour);
		
	}
	
	//Creating 12 new Checkers at their initial locations
	public void createCheckers(int destnumber, String colour) {
		for(int i=0;i<12;i++){
			checkers.add(new Checkers(colour,destnumber));
			destnumber++;
			//System.out.println(checkers[i].number);
		}
	}
	
	public int getScore(){
		return score;
		
	}
	public void setScore(int j) {
		score=j;
	}
	public String getColour(){
		return colour;
	}
	
	public int getNoOfCheckers() {
		//Check how many checkers are alive and return the no of players
		noOfCheckers=0;
		for(int i=0;i<12;i++) {
			if(checkers.get(i).getIsAlive()){
				noOfCheckers++;
			}
		}
		return noOfCheckers;
	}
	

}
