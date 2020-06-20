import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighlighterPanel extends JPanel{
	
	
	static ArrayList<Squares> listY;
	static ArrayList<Squares> listX;

	HighlighterPanel(ArrayList<Squares> listY,ArrayList<Squares> listX){
		
		this.listY=listY;
		this.listX=listX;
		this.setOpaque(false);
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);

	        for(int i=0;i<listY.size();i++){
				
	        	g.setColor(Color.GREEN);
	        	g.drawRect((listY.get(i).a)-1,(listY.get(i).b)-1,70-1,70-1);
	        	g.drawRect((listY.get(i).a)-2,(listY.get(i).b)-2,70-2,70-2);
	        	g.drawRect(listY.get(i).a,listY.get(i).b,70,70);
	        	
				}
	        
	        for(int i=0;i<listX.size();i++){
				
	        	g.setColor(Color.CYAN);
	        	g.drawRect((listX.get(i).a)-1,(listX.get(i).b)-1,70-1,70-1);
	        	g.drawRect((listX.get(i).a)-2,(listX.get(i).b)-2,70-2,70-2);
	        	g.drawRect(listX.get(i).a,listX.get(i).b,70,70);
	        	
				}
	        

	}
	
	public void updateHighlighter(ArrayList<Squares> listY) {
		this.listY=listY;
		repaint();
	}
	
	public void updateMoveableHighlighter(ArrayList<Squares> listX) {
		this.listX=listX;
		repaint();
	}
	

}
