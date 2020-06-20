import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

public class Movements{
	
	static Squares squares[]=new Squares[32];
	static ArrayList<Integer> multilist=new ArrayList<Integer>();
	static int multiindex=0;
	static public boolean lastMoveAdj=false;
	static public boolean lastMoveJump=false;
	static public boolean movesAvailable=false;
	static boolean tohighlight=false;
	
	public static Squares[] getSquares() {
		return squares;
	}
	
	static void displaySquares(Squares s[]) {
		
		for(int i=0;i<32;i++) {
			System.out.println("i  :  "+i+"s no: "+s[i].snumber+"  "+s[i].occupied);
		}
	}
	
	static boolean next(Player p, Squares[] s, int xindex, int yindex) {
				
		squares=s;
		
		System.out.println("\n\nNext has entered with index values: "+xindex+"\t->\t"+yindex);
		
		//Check if there's no checker on x
		if(!s[xindex].occupied==true) {
			System.out.println("There is no checker present on "+xindex);
			return false;
		}
		
		//Check if the checker x's color is not the same as the player's colour. If yes		
		if(!(s[xindex].checker.getColour().equalsIgnoreCase(p.getColour()))) {
			System.out.println("\nMoving wrong coin.\n");
			return false;
		}
		
		//Check if there's a checker on y
		if(s[yindex].occupied==true) {
			System.out.println("No checker on y "+yindex);
			return false;
		}
		//Check if x and y are within the range
		if(!(isWithinRange(xindex+1)&&isWithinRange(yindex+1)))
			return false;
		
		int a=isEven(xindex+1); //Part of the logic 
		
		//Now check if player is red man. if yes, call moveRedMan()
		if(p.getColour().equalsIgnoreCase("red")&&s[xindex].checker.getIsAlive()&&s[xindex].checker.getIsMan()) {
			return moveRedMan(p,s,xindex,yindex,a);
		}
		//Check if player is a king and is alive. if yes, call moveRedorBlackKing()
		if(s[xindex].checker.getIsAlive()&&(!s[xindex].checker.getIsMan())) {			
			return moveAKing(p,s,xindex,yindex,a);
		}
		
		//else callBlackKing()
		if(p.getColour().equalsIgnoreCase("black")&&s[xindex].checker.getIsAlive()&&(s[xindex].checker.getIsMan())) {
			return moveBlackMan(p,s,xindex,yindex,a);
		}
		
		return false;
	}
	
	
	static boolean moveRedMan(Player p,Squares s[], int xindex, int yindex, int a) {
		
		//If x is even a=-1. Else, if x is odd a=1
		
		ArrayList<Integer> list1=findAdjacentElements(xindex+1,a);
		ArrayList<Integer> list2=findOneHopAwayElements(xindex+1);
		
		ArrayList<Integer> newlist1= new ArrayList<Integer>();
		ArrayList<Integer> newlist2= new ArrayList<Integer>();
		
		//Filter out the ones greater than x in list 1 
		for(int i=0;i<list1.size();i++) {
			
			
			//Even when it is lesser there are a few conditions. 
			if(list1.get(i)<xindex+1) { 
				
				//Condition: In case it is not occupied, then add.
				if(squares[list1.get(i)-1].occupied==false)
					newlist1.add(list1.get(i));
			}
			
		}
		
		//Similarly remove the unwanted elements from list2
		for(int i=0;i<list2.size();i++) { 
			
			//Remove the adjacent elements that are greater than x itself. Because man cannot move backwards.
			if(list2.get(i)<xindex+1) { 
				
				//Even in the forward possibilities, you cannot move to that one hop location.
				
				//Condition: There no element present at that location.
				if(squares[list2.get(i)-1].occupied==false) {	
					
					//Find the common element.
					int common = getCommonSquare(xindex,list2.get(i)-1);
					common=common-1;
					
					//Condition 2b: The common element is occupied && of the opponent's color. Add to possibilities list.
					if(squares[common].occupied==true&&(!squares[common].checker.getColour().equalsIgnoreCase(p.getColour())))
						newlist2.add(list2.get(i));
				}
			}
		}
		
		if(lastMoveJump==true) {
			
			//Get rid of the adjacent elements. You need only list2.
			newlist1.clear();
			
			if(newlist2.size()==0) {
				movesAvailable=false;
			}
		}
		
			
		return makeMove(newlist1, newlist2, p,s,xindex,yindex);
	}

//Possible moves for red. No multihop included. Gets square number as input. Not square index.
static ArrayList<Integer> possibleRedMan(Player p, Squares squares[], int x,int a) {
	
			//If x is even a=-1. Else, if x is odd a=1
	
			ArrayList<Integer> list1=findAdjacentElements(x,a);
			ArrayList<Integer> list2=findOneHopAwayElements(x);
			
			ArrayList<Integer> newlist1= new ArrayList<Integer>();
			ArrayList<Integer> newlist2= new ArrayList<Integer>();
			
			//Filter out the ones greater than x in list 1 
			for(int i=0;i<list1.size();i++) {
				
				
				//Even when it is lesser there are a few conditions. 
				if(list1.get(i)<x) { 
					
					//Condition 2: In case it is not occupied, then add.
					if(squares[list1.get(i)-1].occupied==false)
						newlist1.add(list1.get(i));
				}
				
			}
			
			//Similarly remove the unwanted elements from list2
			for(int i=0;i<list2.size();i++) { 
				
				//Remove the adjacent elements that are greater than x itself. Because man cannot move backwards.
				if(list2.get(i)<x) { 
					
					//Even in the forward possibilities, you cannot move to that one hop location.
					
					//Condition 2: There no element present at that location.
					if(squares[list2.get(i)-1].occupied==false) {	
						
						//Find the common element.
						int common = getCommonSquare(x-1,list2.get(i)-1);
						common=common-1;
						
						//Condition 2b: The common element is occupied && of the opponent's color. Add to possibilities list.
						if(squares[common].occupied==true&&(!squares[common].checker.getColour().equalsIgnoreCase(p.getColour())))
							newlist2.add(list2.get(i));
					}
					
				}
			}
			
			if(tohighlight==false) {
				
				if(lastMoveJump==true) {
					
					//Get rid of the adjacent elements. You need only list2.
					newlist1.clear();
					
					//But if last move made was a jump and no more jumps are available. 
					if(newlist2.size()==0) {
						movesAvailable=false;
					}
				}
				
			}
			
			
			newlist1.addAll(newlist2);
			Collections.sort(newlist1);
			return newlist1;
}


	
	static boolean moveAKing(Player p,Squares s[], int xindex, int yindex, int a) {
		
		ArrayList<Integer> list1=findAdjacentElements(xindex+1,a);
		ArrayList<Integer> list2=findOneHopAwayElements(xindex+1);
		
		ArrayList<Integer> newlist1= new ArrayList<Integer>();
		ArrayList<Integer> newlist2= new ArrayList<Integer>();
		
		//Filter out the ones smaller than x in list 1. Black man cannot travel to a place that is smaller than the number on the square he is already in.
		for(int i=0;i<list1.size();i++) {
		
				//Condition 2: In case it is not occupied, then add.
				if(squares[list1.get(i)-1].occupied==false)
					newlist1.add(list1.get(i));
			
		}
		
		//Similarly remove the unwanted elements from list2
		for(int i=0;i<list2.size();i++) { 
			
				//Condition 2: There no element present at that location.
				if(squares[list2.get(i)-1].occupied==false) {	
					
					//Find the common element.
					int common = getCommonSquare(xindex,list2.get(i)-1);
					common=common-1;
					
					//Condition 2b: The common element is occupied && of the opponent's color. Add to possibilities list.
					if(squares[common].occupied==true&&(!squares[common].checker.getColour().equalsIgnoreCase(p.getColour())))
						newlist2.add(list2.get(i));
				}
				
		}
		
		if(lastMoveJump==true) {
			
			//Get rid of the adjacent elements. You need only list2.
			newlist1.clear();
			
			//But if last move made was a jump and no more jumps are available. 
			if(newlist2.size()==0) {
				movesAvailable=false;
			}
		}

		
		
		return makeMove(list1, list2, p,s,xindex,yindex);
	}
	
	static ArrayList<Integer> possibleMovesForKing(Player p, Squares squares[], int x,int a){
		
		ArrayList<Integer> list1=findAdjacentElements(x,a);
		ArrayList<Integer> list2=findOneHopAwayElements(x);
		
		ArrayList<Integer> newlist1= new ArrayList<Integer>();
		ArrayList<Integer> newlist2= new ArrayList<Integer>();
		
		//Filter out the ones smaller than x in list 1. Black man cannot travel to a place that is smaller than the number on the square he is already in.
		for(int i=0;i<list1.size();i++) {
		
				//Condition 2: In case it is not occupied, then add.
				if(squares[list1.get(i)-1].occupied==false)
					newlist1.add(list1.get(i));
			
		}
		
		//Similarly remove the unwanted elements from list2
		for(int i=0;i<list2.size();i++) { 
			
				//Condition 2: There no element present at that location.
				if(squares[list2.get(i)-1].occupied==false) {	
					
					//Find the common element.
					int common = getCommonSquare(x-1,list2.get(i)-1);
					common=common-1;
					
					//Condition 2b: The common element is occupied && of the opponent's color. Add to possibilities list.
					if(squares[common].occupied==true&&(!squares[common].checker.getColour().equalsIgnoreCase(p.getColour())))
						newlist2.add(list2.get(i));
				}
				
		}
		
		if(tohighlight==false) {
			
			if(lastMoveJump==true) {
				
				//Get rid of the adjacent elements. You need only list2.
				newlist1.clear();
				
				//But if last move made was a jump and no more jumps are available. 
				if(newlist2.size()==0) {
					movesAvailable=false;
				}
			}
			
		}
		
		

	
			newlist1.addAll(newlist2);
			Collections.sort(newlist1);
			return newlist1;
}

	
	
	
	static boolean moveBlackMan(Player p,Squares s[], int xindex, int yindex, int a) {
		
		//If x is even a=-1. Else, if x is odd a=1
		
		ArrayList<Integer> list1=findAdjacentElements(xindex+1,a);
		ArrayList<Integer> list2=findOneHopAwayElements(xindex+1);
		
		ArrayList<Integer> newlist1= new ArrayList<Integer>();
		ArrayList<Integer> newlist2= new ArrayList<Integer>();
		
		//Filter out the ones greater than x in list 1 
		for(int i=0;i<list1.size();i++) {
			
			
			//Even when it is greater, there are a few conditions. 
			if(list1.get(i)>xindex+1) { 
				
				//Condition 2: In case it is not occupied, then add.
				if(squares[list1.get(i)-1].occupied==false)
					newlist1.add(list1.get(i));
			}
			
		}
		
		//Similarly remove the unwanted elements from list2
		for(int i=0;i<list2.size();i++) { 
			
			//Remove the adjacent elements that are greater than x itself. Because man cannot move backwards.
			if(list2.get(i)>xindex+1) { 
				
				//Even in the forward possibilities, you cannot move to that one hop location.
				
				//Condition 2: There no element present at that location.
				if(squares[list2.get(i)-1].occupied==false) {	
					
					//Find the common element.
					int common = getCommonSquare(xindex,list2.get(i)-1);
					common=common-1;
					
					//Condition 2b: The common element is occupied && of the opponent's color. Add to possibilities list.
					if(squares[common].occupied==true&&(!squares[common].checker.getColour().equalsIgnoreCase(p.getColour())))
						newlist2.add(list2.get(i));
				}
			}
		}
		
		if(lastMoveJump==true) {
			
			//Get rid of the adjacent elements. You need only list2.
			newlist1.clear();
			
			//But if last move made was a jump and no more jumps are available. 
			if(newlist2.size()==0) {
				movesAvailable=false; // cant i directly say false
			}
		}

		
		return makeMove(newlist1, newlist2, p,s,xindex,yindex);
	}

	
	//Gets square numbers as input. not indexes. 
	static ArrayList<Integer> possibleBlackMan(Player p, Squares squares[], int x,int a) {
	
		ArrayList<Integer> list1=findAdjacentElements(x,a);
		ArrayList<Integer> list2=findOneHopAwayElements(x);
		
		ArrayList<Integer> newlist1= new ArrayList<Integer>();
		ArrayList<Integer> newlist2= new ArrayList<Integer>();
		
		//Filter out the ones smaller than x in list 1. Black man cannot travel to a place that is smaller than the number on the square he is already in.
		for(int i=0;i<list1.size();i++) {
			
			
			//If y is greater then its better. But there are a few conditions. 
			if(list1.get(i)>x) { 
				
				//Condition 2: In case it is not occupied, then add.
				if(squares[list1.get(i)-1].occupied==false)
					newlist1.add(list1.get(i));
			}
			
		}
		
		//Similarly remove the unwanted elements from list2
		for(int i=0;i<list2.size();i++) { 
			
			//Remove the adjacent elements that are smaller than x itself. Because man cannot move backwards.
			if(list2.get(i)>x) { 
				
				//Even in the forward possibilities, you cannot move to that one hop location.
				
				//Condition 2: There no element present at that location.
				if(squares[list2.get(i)-1].occupied==false) {	
					
					//Find the common element.
					int common = getCommonSquare(x-1,list2.get(i)-1);
					common=common-1;
					
					//Condition 2b: The common element is occupied && of the opponent's color. Add to possibilities list.
					if(squares[common].occupied==true&&(!squares[common].checker.getColour().equalsIgnoreCase(p.getColour())))
						newlist2.add(list2.get(i));
				}
				
			}
		}
		
		if(tohighlight==false) {
			if(lastMoveJump==true) {
				
				//Get rid of the adjacent elements. You need only list2.
				newlist1.clear();
				
				//But if last move made was a jump and no more jumps are available. 
				if(newlist2.size()==0) {
					movesAvailable=false;
				}
			}
			
		}
		

		
			newlist1.addAll(newlist2);
			Collections.sort(newlist1);
			return newlist1;
}
	
	//Checks for the element and makes the move for any checker. The two accepted lists are 1-32 but the x and y are indexes
	static boolean makeMove(ArrayList<Integer> list1, ArrayList<Integer> list2, Player p, Squares s[],int xindex, int yindex) {
		boolean flag1=search(yindex+1,list1);
		boolean flag2=search(yindex+1,list2);
		
		if(flag1==true) {
			move(p,s,xindex,yindex);
			lastMoveAdj=true;
			return true;
		}
		
		//flag2=true if the move may be a jump
		else if(flag2==true) {
	
			if(kill(p,s,xindex,yindex)==xindex) {
				//Since the jump was made
				move(p,s,xindex,yindex);
				lastMoveJump=true;
				lastMoveAdj=false;
				
				//change to king if man
				if(s[yindex].checker.getColour().equalsIgnoreCase("red")) {
					if(yindex==0||yindex==1||yindex==2||yindex==3) {
						if(s[yindex].checker.getIsMan()==true) {
							s[yindex].checker.setIsMan(false);
						}
					}
				}
				
				if(s[yindex].checker.getColour().equalsIgnoreCase("black")) {
					if(yindex==31||yindex==30||yindex==29||yindex==28) {
						if(s[yindex].checker.getIsMan()==true) {
							s[yindex].checker.setIsMan(false);
						}
					}
				}
				
				moreHopsUpdate(p,s,yindex);
				return true;
			}
			else {
				//Can't make the jump cuz theres no valid kill
				return false;
			}
		}
		else
			return false;
	
	}
	
	static void moreHopsUpdate(Player p,Squares[] s, int xindex) {
		
		ArrayList<Integer> list=new ArrayList<Integer>();
		int a=isEven(xindex+1); //Part of the logic 
		
		//Now check if player is red man. if yes, call moveRedMan()
		if(p.getColour().equalsIgnoreCase("red")&&s[xindex].checker.getIsAlive()&&s[xindex].checker.getIsMan()) {
			list=possibleRedMan(p,s,xindex+1,a);
		}
		//Check if player is a king and is alive. if yes, call moveRedorBlackKing()
		if(s[xindex].checker.getIsAlive()&&(!s[xindex].checker.getIsMan())) {			
			list= possibleMovesForKing(p,s,xindex+1,a);
		}
		
		//else callBlackKing()
		if(p.getColour().equalsIgnoreCase("black")&&s[xindex].checker.getIsAlive()&&(s[xindex].checker.getIsMan())) {
			list=possibleBlackMan(p,s,xindex+1,a);
		}
		
		//if available set movesAvailable to true.
		if(list.size()==0)
			movesAvailable=false;
		else if(list.size()>0)
			movesAvailable=true;
		else
			movesAvailable=false;
	}
				
	//Find the adjacent elements. Returning a list of adjacent elements. Parameters: int x, int a
	static ArrayList<Integer> findAdjacentElements(int x, int a){

		ArrayList<Integer> list=new ArrayList<Integer>();
		int m=x+a*4; 
		if(isValid(x,m,1))
		list.add(m);
		
		m=x-a*5;
		if(isValid(x,m,1))
		list.add(m);
		
		m=x+a*3;
		if(isValid(x,m,1))
		list.add(m);
		
		m=x-a*4;
		if(isValid(x,m,1))
		list.add(m);

		return list;
	}
	
	
	//Finding numbers multiple hops away. To display hints.
	static void findMultipleHopsAwayElements(ArrayList<Integer> list, int x){

		if(list.size()>0) {
			
			for(int i=0;i<list.size();i++) {
				
				//Check if element is already present in multihop. then add.
				if((!search(list.get(i), multilist))&&list.get(i)!=x)
				{
					multilist.add(list.get(i));
					multiindex++;
				}
				
				else {
					continue;
				}
				
				//test if element added 
				for(int j=0;j<multilist.size();j++) {
					System.out.print(multilist.get(j)+"\t");
				}
				
				if(findOneHopAwayElements(list.get(i)).size()>0)
					findMultipleHopsAwayElements(findOneHopAwayElements(list.get(i)),x);
				else {
					continue;
				}
			}
			
		}

	}
	
	//Checking if l and m lie on the same line. Basically their Math.ceil is the same or not. Non static.
	static boolean onSameRow(int l,int m) {
		boolean flag=false;
		if((int)Math.ceil(l/4.0)==(int)Math.ceil(m/4.0)) {
			flag=true;
		}
		return flag;
	}
	
	//Checking if m is within the board range. Non static.
	static boolean isWithinRange(int m) {
		return m<=32&&m>=1;
	}
	
	//Checking if m is a row is  either a row above or a row below x. Only then m would be adjacent to x. Nonstatic. 
	static boolean isOnAdjacentRow(int l,int m) {
		int lrow=(int)Math.ceil(l/4.0);
		int mrow=(int)Math.ceil(m/4.0);
		if(Math.abs(lrow-mrow)==1)
			return true;
		else
			return false;
	}
	
	//Checking if m is row away from x's row. Non static.
	static boolean isOneRowAway(int l, int m) {
		int lrow=(int)Math.ceil(l/4.0);
		int mrow=(int)Math.ceil(m/4.0);
		if(Math.abs(lrow-mrow)==2)
			return true;
		else
			return false;
	}
	
	/*Checking is m is within the range and not on the same line. Non static. 
	 *If validity if being checked for adjElements then 1 else 2 */
	static boolean isValid(int l, int m, int adOrHop) {
			boolean flag=true;
			if(adOrHop==1) {
				if(!isOnAdjacentRow(l,m)) {
					flag=false;
				}
			}
			
			if(adOrHop==2) {
				if(!isOneRowAway(l,m)) {
					flag=false;
				}
			}
			if(!isWithinRange(m))
				flag=false;
			return flag;
	}
	
	//Check is n is even. Non static. If n is even return a=-1. Else, return 1;
	static int isEven(int n){
		if((int)Math.ceil(n/4.0)%2==0)
			return -1;
		else
			return 1;
	}
	
	//Finding the elements a hop away. Returning a list of the necessary elements. Parameters: int x
	static ArrayList<Integer> findOneHopAwayElements(int x){

		ArrayList<Integer> list=new ArrayList<Integer>();
		int m=x+7; 
		if(isValid(x,m,2))
		list.add(m);
		
		m=x-7;
		if(isValid(x,m,2))
		list.add(m);
		
		m=x+9;
		if(isValid(x,m,2))
		list.add(m);
		
		m=x-9;
		if(isValid(x,m,2))
		list.add(m);

		return list;
	}
	
	//Find possible layover element before making the multihop
	 static int find(ArrayList<Integer> list, int n) {
		 for(int i=0;i<list.size();i++) {
			 if(list.get(i)==n) {
				 return n;
			 }
		 }
		 return -1;
	 }
	
	//Search for y in the array list. Return boolean value. Parameters: int y, ArrayList<>. non static.
	static boolean search(int y, ArrayList<Integer> list) {
		boolean flag=false;
		for(int i=0;i<list.size();i++) {
			if(list.get(i)==y)
				flag=true;
		}
		return flag;
	}
	
	//Checking if a position has a checker. Returns a boolean value. Parameters: int y, squares<>. Nonstatic.
	static boolean hasChecker(Squares[] s,int yindex) {
		boolean flag=false;
		if(s[yindex].occupied==true)
			flag=true;
		return flag;
	}
	
	//Checking if the checker belongs to the same team. Returns a boolean value. int n, squares<>
	static boolean isOpponent(Player p, Squares[] s, int nindex) {
		
		
		if(s[nindex].checker.getColour().equalsIgnoreCase(p.getColour()))
			return false;
		else
			return true;
	}
	
	//Moving the checker from x to y. Return type: void. Parameters: Squares[] s, int x, int y. Non static.
	static void move(Player p, Squares[] s, int xindex, int yindex) {
		if(hasChecker(s,xindex)&&!isOpponent(p,s,xindex)) {
			if(!hasChecker(s,yindex)) {
				s[yindex].occupied=true;
				s[yindex].checker=s[xindex].checker;
				s[xindex].occupied=false;
				s[xindex].checker=null;
			}
		}
	}
	
	static int checkersLeft(Player p, Squares s[]) {
		
		int sum=0;
		for(int i=0;i<32;i++) {
			if(s[i].occupied==true&&s[i].checker.getColour().equalsIgnoreCase(p.getColour())){
				sum++;
			}
		}
		return sum;
		
	}
	
	static boolean gameOver(Player p, Squares s[]) {
		
		if(checkersLeft(p,s)==0) {
			return true;
		}
		
		return false;
	}
	
	//get common element between x and y --- one hop away element
	static int getCommonSquare(int xindex,int yindex) {
		
				//Find if x and y are on even rows or odd rows.
				 int common=-1;
				
				//Generate the adjacent lists for x and y
				ArrayList<Integer> xlist=findAdjacentElements(xindex+1,isEven(xindex+1));
				ArrayList<Integer> ylist=findAdjacentElements(yindex+1,isEven(yindex+1));
				
				//Find the common adjacent element. This checker is probably the one you have to kill.
				int xsize=xlist.size();
				int ysize=ylist.size();
			
					for(int i=0;i<xsize;i++) {
						for(int j=0;j<ysize;j++) {
							if(xlist.get(i)==ylist.get(j)) {
								common=xlist.get(i);
							}
						}
					}
				
				//returns an snumber. Not an index.
				return common;
	}
	
	//Kill the opponent. Returns int x if can kill else -1. Parameters: Player p, int x, int y, squares<>. Non static.
	static int kill(Player p, Squares[] s, int xindex, int yindex) {
		
		
		//Find if x and y are on even rows or odd rows.
		int common=getCommonSquare(xindex,yindex);
		
		//If there is no checker on the common square
		if(common==-1) {
			return -1;
		}
		
		//Check if common element is the opponent's checker.
		if(s[common-1].occupied==true&&isOpponent(p,s,common-1)) {
			
			//Code to kill opponent.
			p.setScore(p.getScore()+1);			
			s[common-1].checker=null;
			s[common-1].occupied=false;
			return xindex;
		}
		else {
			return -1;
		}
	}
	
	
	
	static ArrayList<Squares> findAllMoveableCheckers(Player p, Squares s[]){
		
		ArrayList<Squares> moveable=new ArrayList<Squares>();
		ArrayList<Integer> list=new ArrayList<Integer>();
		ArrayList<Integer> listk=new ArrayList<Integer>();
		
		tohighlight=true;
		
		if(p.getColour().equalsIgnoreCase("red")) {
			
			for(int i=0;i<32;i++) {
			
				if(s[i].occupied==true&&s[i].checker.getColour().equalsIgnoreCase("red")) {
					
					if(s[i].checker.getIsMan())
						list=possibleRedMan(p,s,s[i].snumber,isEven(s[i].snumber));
					
					else if (s[i].checker.getIsMan()==false)
						listk=possibleMovesForKing(p,s,s[i].snumber,isEven(s[i].snumber));

					if(list.size()>0)
						moveable.add(s[i]);
					
					else if(listk.size()>0)
						moveable.add(s[i]);
					
					list.clear();
					listk.clear();
				
				}
			
			}
			
		}
		
		if(p.getColour().equalsIgnoreCase("black")) {
			
			for(int i=0;i<32;i++) {
			
				if(s[i].occupied==true&&s[i].checker.getColour().equalsIgnoreCase("black")) {
					
					if(s[i].checker.getIsMan())
						list=possibleBlackMan(p,s,s[i].snumber,isEven(s[i].snumber));
					
					else if (s[i].checker.getIsMan()==false)
						listk=possibleMovesForKing(p,s,s[i].snumber,isEven(s[i].snumber));
					
					if(list.size()>0)
						moveable.add(s[i]);
					
					if(listk.size()>0)
						moveable.add(s[i]);
					
					list.clear();
					listk.clear();
				}
			
			}
			
		}

		tohighlight=false;
		return moveable;
	}
}
