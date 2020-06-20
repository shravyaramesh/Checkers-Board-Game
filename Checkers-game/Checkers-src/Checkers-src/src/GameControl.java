import java.util.ArrayList;

public class GameControl {
	
	static int x;
	static int y;
	static Squares squares[]=new Squares[32];//Maintain from 1 to 32 squares[0]=null
	Player P1, P2;
	Movements move;
	
	GameControl(){

		//Creating the squares.
		createSquares();
		
		//Creating new players.
		P1=new Player("Red");
		P2=new Player("Black");
		//In the above lines, each player was assigned 12 pieces.
		
		//Placing the checkers of respective players on the squares.
		placeCheckers(P1);
		placeCheckers(P2);

	} 
	
	boolean buttonClicked(int n, int click) {
		
		//Displaying which player's turn it is at every button click
		System.out.print("\nPlayer: "); 
		
		if(P1.turn==true)
		System.out.print(" P1 \n");
		
		else
		System.out.print(" P2 \n");

		ArrayList<Squares> checkingX=new ArrayList<Squares>();
		
		if(click==1) {
			
			System.out.println("\nEntered test status click 1");
			
			Movements.movesAvailable=false;
			Movements.lastMoveAdj=false;
			Movements.lastMoveJump=false;
			
			if(squares[n].occupied!=true) {
				System.out.println("Square not occupied");
				return false;
			}
			
			if(squares[n].occupied==true&&(squares[n].checker.getColour().equalsIgnoreCase(P1.getColour()))&&P2.turn==true){
				System.out.println("Player 1 is playing in Player 2's turn");
				return false;
			}
			
			if(squares[n].occupied==true&&(squares[n].checker.getColour().equalsIgnoreCase(P2.getColour()))&&P1.turn==true){
				System.out.println("Player 2 is playing in Player 1's turn");
				System.out.println();
				return false;
			}
			
			else {
				//if n is a valid source, it can be stored in the class variable.
				
				System.out.println("In click==1 of buttonClicked checking by passing possible n to\npossibleSquares\n");
				checkingX=possibleSquares(n);
				System.out.println("Back to click==1 of buttonClicked for click==1\n");
				if(checkingX.size()==0) {
					
					System.out.println("checkingX.size() is zero");
					return false;
				}

				this.x=n;
				System.out.println("Static int x: "+this.x);
				return true;
			}
			
		}
		
		if(click==2) {
			
			this.y=n;
			System.out.println("Static int y: "+this.y);
			if(P1.turn==true) {
				
				System.out.println("Player 1's turn. Tries to move from "+x+" index  to "+y+"index. ");
				//passing indexes as parameters.
				boolean flag=move.next(P1, squares, this.x, this.y);
				
				//Movement was made. 
				if(flag==true) {
					
					System.out.println("\nMovement was made. ");
					//But check if man touched the opponent's base
					if(squares[this.y].checker.getIsMan()&&(y+1)==1||(y+1)==2||(y+1)==3||(y+1)==4) {
						
						//He will now be made a king.
						squares[this.y].checker.setIsMan(false);
						
					}
					
					
					//If move was made by jumping
					if(Movements.lastMoveJump==true) {

						System.out.println("\nJump was made!");
						
						//If there are more jumps available then player retains his turn. 
						if(Movements.movesAvailable==true) {
							
							System.out.println("\nMoves are available so player retains his turn.");
							//The previous y value now becomes x value and new y value is accepted
							this.x=this.y;
							System.out.println("Old static y becomes static x.\nStatic int y: "+this.y+"\nStatic int x: "+this.x);
							P1.turn=true;
							P2.turn=false;
						}
						
						//Otherwise he loses his turn. 
						else if(Movements.movesAvailable==false) {
							System.out.println("\nNo more moves available so Player loses turn.");
							
							//Movements.lastMoveJump=false;
							
							P1.turn=false;
							P2.turn=true;
							
							
						}
						
					}
					
					else if(Movements.lastMoveAdj==true)
					{
						System.out.println("\nPlayer 1 only moved a checker to an adjacent square. \nPlayer 2 gets his chance now.");
						P1.turn=false;
						P2.turn=true;
						
					}
						
						//Flag is returned with a true value because the move was made.
						return flag;
				}

				
				//Movement was not made. Not too sure about this. 
				else {
					
					//If last jump made was a hop and no moves available then
					if(Movements.lastMoveJump==true&&Movements.movesAvailable==false)
					{
						//Next player gets his turn 
						System.out.println("Last move was a jump. No moves available here after.\nPlayer 2 gets his chance now.\n");
						P1.turn=false;
						P2.turn=true;
						
					}
					
					//the next player gets his turn 
					//Player retains his turn
					else {
						System.out.println("\nMovement was not made from "+x+" index to "+y+" index. \nPlayer 1 retains his turn.");
						P1.turn=true;
						P2.turn=false;
					}
					return flag;
				}
					
			}
			
			else if(P2.turn==true) {
				
				System.out.println("Player 2's turn. Tries to move from "+x+" index  to "+y+"index.");
				//Passing indexes as parameters
				boolean flag=move.next(P2, squares, this.x, this.y);
				
				
				if(flag==true) {
					//Movement was made
					System.out.println("Movement was made");
					
					//Check if the checker moved was a man
					if(squares[y].checker.getIsMan()&&(y+1)==32||(y+1)==31||(y+1)==30||(y+1)==29) {
						
						if(squares[y].checker.getIsMan())
							//Enters if the checker is a man. He will now be made a king.
							squares[y].checker.setIsMan(false);
						
					}
					
					//If move was made by jumping
					if(Movements.lastMoveJump==true) {

						System.out.println("Jump was made!");
						
						//If there are more jumps available then player retains his turn. 
						if(Movements.movesAvailable==true) {
							
							System.out.println("Moves are available so player retains his turn.");
							///The previous y value now becomes x value and new y value is accepted
							this.x=this.y;
							System.out.println("Old static y becomes static x.\nStatic int y: "+this.y+"\nStatic int x: "+this.x);
							P1.turn=false;
							P2.turn=true;
						}
						
						//Otherwise he loses his turn. 
						if(Movements.movesAvailable==false) {
							System.out.println("No more moves available so Player loses turn.");
							
							//Movements.lastMoveJump=false;
							
							P1.turn=true;
							P2.turn=false;
							
							
						}
						
					}
					
					//If move was made by stepping
					else if(Movements.lastMoveAdj==true)
					{
						System.out.println("Player 2 only moved a checker to an adjacent square. \nPlayer 1 gets his chance now.");
						P1.turn=true;
						P2.turn=false;
						
					}
						
						//Flag is returned with a true value because the move was made.
						return flag;
						
				}
				
				//Movement was not made
				else {
					
					//Player 2 retains his turn
					System.out.println("Movement was not made from "+x+" index to "+y+" index. \nPlayer 2 retains his turn.");
					P2.turn=true;
					P1.turn=false;
					return flag;
				}
			}
	
		}// end of click 2

		return false;
		
	}// End of buttonClicked()
	
	ArrayList<Squares> possibleSquares(int xindex){
		
		ArrayList<Integer> list=new ArrayList<Integer>();
		ArrayList<Squares> squarelist=new ArrayList<Squares>();
		
		//If player is red 
		if(P1.turn==true&&squares[xindex].checker.getColour().equalsIgnoreCase(P1.getColour()))
		{
			//If checker is a man
			if(squares[xindex].checker.getIsMan()==true)
			{
				list=Movements.possibleRedMan(P1,squares,xindex+1,Movements.isEven(xindex+1));
				
			}
			
			//If checker is a king
			else if (squares[xindex].checker.getIsMan()==false) {
				
				list=Movements.possibleMovesForKing(P1, squares, xindex+1,Movements.isEven(xindex+1));
				
			}
			
		}
			
		
		//If player is black
		if(P2.turn==true&&squares[xindex].checker.getColour().equalsIgnoreCase(P2.getColour())) {
			
			//If checker is a man
			if(squares[xindex].checker.getIsMan()==true) 
				list=Movements.possibleBlackMan(P2,squares,xindex+1,Movements.isEven(xindex+1));
			
			//If checker is a king
			else if (squares[xindex].checker.getIsMan()==false)	
				list=Movements.possibleMovesForKing(P2, squares, xindex+1,Movements.isEven(xindex+1));
			
		}
		

		for(int i=0;i<list.size();i++) {
			squarelist.add(squares[list.get(i)-1]);
		}
		
		
		return squarelist;
	}
	
	boolean isGameOver() {
		
		if(move.gameOver(P1,squares)==true||move.gameOver(P2,squares)==true) {
			P1.turn=false;
			P2.turn=false;
			return true;
		}
		else
			return false;
	}
	
	String getTurn() {
		if(P1.turn==true) {
			return "red";
		}
		if(P2.turn==true) {
			return "black";
		}
		else {
			return "none";
		}
	}
	
	int getScore(String colour) {
		
		if(colour.equalsIgnoreCase(P1.getColour())) {
			return P1.getScore();
		}
		else if(colour.equalsIgnoreCase(P2.getColour())) {
			return P2.getScore();
		}
		else
			return -1;
	}

	
	//Initialise the squares array
		static void createSquares(){
		
			int squareindex=0;
			
			boolean evenrow=true;
			
			for(int y=0;y<560;y=y+70) {
				
				//First row and all odd rows
				if(evenrow==true) {
					for(int x=0;x<=480;x=x+140) {
						squares[squareindex]=new Squares(squareindex+1,x,y,false,null);
						squareindex++;
					}
				}
				
				//Second row and all even rows
				else if(evenrow==false) {
					for(int x=70;x<=560;x=x+140) {
						squares[squareindex]=new Squares(squareindex+1,x,y,false,null);
						squareindex++;
					}
				}
				evenrow=!evenrow;
			}				
		}
			
	//Place Checkers on the squares
		static void placeCheckers(Player P){
			
			int c=0;
			for(int i=0;i<32;i++) {
				if(squares[i].snumber==P.checkers.get(c).getSNumber()) {
					squares[i].checker=P.checkers.get(c);
					squares[i].occupied=true;
					
					if(c<11)
					c++;
				}
			}
		}
		
		
}
