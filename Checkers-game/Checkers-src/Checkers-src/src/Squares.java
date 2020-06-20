
public class Squares {
	
	int snumber;
	int a,b;
	boolean occupied=false;
	Checkers checker;
	
	int blackbase[]= {1,2,3,4};
	int redbase[]= {29,30,31,32};
	
	Squares(int number,int a,int b,boolean occupied,Checkers checker){
		this.snumber=number;
		this.a=a;
		this.b=b;
		this.occupied=occupied;
		this.checker=checker;
	}
	
	boolean isOpponentBase(Squares square){
		boolean flag=false;
		for(int i=0;i<4;i++) {
			if(square.checker.getColour().equalsIgnoreCase("red")) {
				if(blackbase[i]==square.snumber) {
					flag=true;}
			}
			if(square.checker.getColour().equalsIgnoreCase("black")) {
				if(redbase[i]==square.snumber) {
					flag=true;}
			}
			
		}
		return flag;

	}
	
	
	
}
