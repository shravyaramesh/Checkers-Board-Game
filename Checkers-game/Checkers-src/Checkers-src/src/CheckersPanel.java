import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;



public class CheckersPanel extends JPanel{
	
	Squares squares[];
	public final static int BUTTONSIZE=70;
	
	CheckersPanel(Squares squares[]){
		
		this.squares=new Squares[32];
		this.squares=squares;
		this.setOpaque(false);
	}
	
protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//display the checkers
		displayCheckers(g);
		
	}
	
	
	//Display the checkers
		void displayCheckers(Graphics g) {
			
			for(int i=0;i<32;i++){
				
				if(squares[i].occupied==true) {
					if(squares[i].checker.getColour().equalsIgnoreCase("Red")) {
						
						if((squares[i].checker.getIsMan())==true) {
							g.setColor(new Color(255,215,0));
							g.drawOval(squares[i].a+3,squares[i].b+3,60,60);
							g.drawOval(squares[i].a+4,squares[i].b+4,59,59);
							g.drawOval(squares[i].a+5,squares[i].b+5,58,58);
							g.fillOval(squares[i].a+6,squares[i].b+6,57,57);
						}
						else if((squares[i].checker.getIsMan())==false) {
							
							g.setColor(new Color(184,134,11));
							g.fillOval(squares[i].a+3,squares[i].b+3,70-3,70-3);
							g.setColor(new Color(255,215,0));
							g.fillOval(squares[i].a+7,squares[i].b+7,60-2,60-2);

						}
						
					}
					
					else {
						if(squares[i].checker.getColour().equalsIgnoreCase("Black")) {
							
							if((squares[i].checker.getIsMan())==true) {
								g.setColor(new Color(235,235,235));
								g.drawOval(squares[i].a+3,squares[i].b+3,60,60);
								g.drawOval(squares[i].a+4,squares[i].b+4,59,59);
								g.drawOval(squares[i].a+5,squares[i].b+5,58,58);
								g.fillOval(squares[i].a+6,squares[i].b+6,57,57);
							}
							else if((squares[i].checker.getIsMan())==false) {
								
								g.setColor(new Color(199,199,199)); 
								g.fillOval(squares[i].a+3,squares[i].b+3,70-3,70-3);
								g.setColor(new Color(235,235,235));
								g.fillOval(squares[i].a+7,squares[i].b+7,60-2,60-2);
							    
							}
							
						}
					}
				}
				
			}
			
	}
		
		public void updateCheckers(Squares s[]) {
			squares=s;
			repaint();
		}
	

}
