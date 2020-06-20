import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

public class GameFrame extends JFrame implements ActionListener{
	
	public final static int WINDOWWIDTH = 1150;
    public final static int WINDOWHEIGHT =650;
    public static int XOFFSET;
    public static int YOFFSET;
    public final static int BUTTONSIZE=70;
    static GameControl status;
    static Squares squares[]=new Squares[32];
	static Player P1, P2;
	static int click=1;
	static int highX;
	static ArrayList<Squares> highlight, moveable;
    BoardButton b[]=new BoardButton[32];
    MyButton exit;
	JLayeredPane layeredPane;
	HighlighterPanel tp3;
	CheckersPanel tp2;
	GamePanel tp;
	
	GameFrame(){
		
		status=new GameControl();
		this.setTitle("LET'S PLAY CHECKERS");
		highlight=new ArrayList<Squares>();
		moveable=new ArrayList<Squares>();
		layeredPane=new JLayeredPane();

        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - WINDOWWIDTH) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - WINDOWHEIGHT) / 2);
		tp=new GamePanel(new ImageIcon("./data/background.png").getImage(),status.squares);
		tp2=new CheckersPanel(status.squares);
		JLabel player1=new JLabel("Player 1");
		JLabel player2=new JLabel("Player 2");
		
		Font font=new Font("SANSSERIF", Font.BOLD, 40);
		player1.setFont(font);
		player2.setFont(font);
		player1.setForeground(new Color(255,255,255));
		player2.setForeground(new Color(255,255,255));
		player1.setBounds(645, 25, 200, 70);
		player2.setBounds(880, 25,200, 70 );
		player1.setHorizontalAlignment(JLabel.CENTER);
		player2.setHorizontalAlignment(JLabel.CENTER);
		
		createButtons();
		
		moveable=Movements.findAllMoveableCheckers(status.P1, status.squares);
		
		for(int i=0;i<moveable.size();i++) {
			System.out.println(moveable.get(i).snumber);
		}
		
		System.out.println(highlight.size());
		tp3=new HighlighterPanel(highlight, moveable);
		tp3.setVisible(true);
		this.setLayout(null);
		
		tp.setBounds(0, 0, WINDOWWIDTH,WINDOWHEIGHT);
		tp2.setBounds(25,25,560,560);
		tp3.setBounds(25,25,560,560);
		
		for(int i=0;i<32;i++) {
			b[i].setBounds(b[i].a,b[i].b, BUTTONSIZE, BUTTONSIZE);
			b[i].addActionListener(this);
			layeredPane.add(b[i], JLayeredPane.DEFAULT_LAYER);
		}
		
		layeredPane.add(tp, layeredPane.DEFAULT_LAYER);
		layeredPane.add(player1,layeredPane.PALETTE_LAYER);
		layeredPane.add(player2,layeredPane.PALETTE_LAYER);
		layeredPane.add(tp2,layeredPane.PALETTE_LAYER);
		layeredPane.add(tp3,layeredPane.PALETTE_LAYER);
		
		
		
		exit=new MyButton("EXIT");
		exit.setBounds(1000,525, 100, 50);
		exit.addActionListener(this);
		layeredPane.add(exit,JLayeredPane.PALETTE_LAYER);
		
		
		
		
		this.setLayeredPane(layeredPane);
		this.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
		
	}
	
	//Initialise the buttons array
	void createButtons(){

			int buttonindex=0;
			boolean evenrow=true;
			
			for(int y=0+25;y<560+25;y=y+70) {
				
				if(evenrow==true) {
					for(int x=0+25;x<=480+25;x=x+140) {
						b[buttonindex]=new BoardButton(x,y);
						buttonindex++;
					}
				}
				else if(evenrow==false) {
					for(int x=70+25;x<=560+25;x=x+140) {
						b[buttonindex]=new BoardButton(x,y);
						buttonindex++;
					}
				}
				evenrow=!evenrow;
			}
			
				
				
		}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exit){
            this.setVisible(false);
            this.dispose();
            System.exit(0);
		}
		
		ArrayList<Integer> list=new ArrayList<Integer>();
		boolean valid=false;
		
		for(int i=0;i<32;i++) {
			if(e.getSource()==b[i]) {
	
				
				if(click==1) {
					
					moveable.clear();
					tp3.updateMoveableHighlighter(moveable);
					
					valid=status.buttonClicked(i, click); 
					
					
					if(valid==true) {
						
						System.out.println("Click 1: "+i+" index turned out to be valid");
						
						highX=i;
						System.out.println("highX: "+highX);
						highlight=status.possibleSquares(highX);
						tp3.updateHighlighter(highlight);
						
						//So get ready for second click
						System.out.println("Now we can accept click 2");
						click=2;
						tp3.setVisible(true);
					}
					
					//if click if not valid. that is if it is not occupied or clicked by wrong player. 
					else if(valid==false) {
						
						System.out.println("Click 1 turned out to be invalid\nWe are going to try to accept Click 1 again.");
						click=1;
						
						controlMoveable();
						tp3.updateMoveableHighlighter(moveable);
						
					}
				}
				
				
				else if(click==2) {
					
					System.out.println("\n\nClick 2: "+i+" index ");
					valid=status.buttonClicked(i, click);
					
					
					//Check if the y has a valid move. 
					if(valid==true) {
						
						//System.out.println("Click 2 turned out to be valid ");
						highlight.clear();
						tp3.updateHighlighter(highlight);
						
						//Update the checkers new positions
						squares=Movements.getSquares();
						tp2.updateCheckers(squares);
						
						tp.updateScore("black",status.getScore("black"));
						tp.updateScore("red",status.getScore("red"));
					
						if(status.isGameOver()) {
							
							System.out.println("Game over!");
							if(status.getScore("black")==12)
							tp.paintGameOver(2);
							else if(status.getScore("red")==12)
							tp.paintGameOver(1);
							click=-10000;
							
						}
						//if it was a step.
						if(Movements.lastMoveAdj==true) {
							
							//clear highlight
							highlight.clear();
							tp3.updateHighlighter(highlight);
							
							//then make click =1; so you can start accepting another x from clicking.

							click=1;
							controlMoveable();
							tp3.updateMoveableHighlighter(moveable);
						}
						
						//if it was a hop to i
						if(Movements.lastMoveJump==true) {
							//if there are moves available
							if(Movements.movesAvailable==true) {
								//make the i highX
								highX=i;
								System.out.println("highX: "+highX);
								
								System.out.println("It is entering the moves available block");
								click=2;
								//now highlight higX
								highlight=status.possibleSquares(highX);
								tp3.updateHighlighter(highlight);
							}
							
							//if no moves available
							if(Movements.movesAvailable==false) {
								//highlights clear
								highlight.clear();
								tp3.updateHighlighter(highlight);
								
								click=1;
								controlMoveable();
								tp3.updateMoveableHighlighter(moveable);
							}
						}			
						
					}
					
					else if(valid==false) {
						
						if(Movements.lastMoveJump==true&&Movements.movesAvailable==true) {
							
							highlight=status.possibleSquares(highX);
							tp3.updateHighlighter(highlight);
							click=2;
						}
						else {
							
							highlight.clear();
							tp3.updateHighlighter(highlight);
							click=1;
							
						}		
					}
					
					tp3.setVisible(true);
				}
				
			}
			
		}

		
	}
	
	
	public void controlMoveable() {
		String turnstring=status.getTurn();
		
		System.out.println("Turn: "+turnstring);
		if(turnstring.equalsIgnoreCase("red")) 
			moveable=Movements.findAllMoveableCheckers(status.P1, status.squares);

		else if(turnstring.equalsIgnoreCase("black"))
			moveable=Movements.findAllMoveableCheckers(status.P2, status.squares);
			
		else
			moveable.clear();
		
		for(int i =0;i<moveable.size();i++)
			System.out.println(moveable.get(i).snumber+"Occupied: "+moveable.get(i).occupied);
	}
	
	
public static void main(String args[]) {
	new GameFrame();
}

}
