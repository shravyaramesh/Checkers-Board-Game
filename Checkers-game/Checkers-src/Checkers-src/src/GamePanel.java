import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	Image im;
	Squares squares[];
	int livesp1;
	int livesp2;
	static int scorep1;
	static int scorep2;
	Font font;
	JLabel scorelabel1,scorelabel2;
	
	
	GamePanel(Image im, Squares squares[]){
		
		this.im = im;
		this.squares=new Squares[32];
		this.squares=squares;

		scorelabel1=new JLabel();
		scorelabel2=new JLabel();

		scorep1=0;
		scorep2=0;
		
	}
	
	public void paintComponent(Graphics g) {

		g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), this);
		
		//Game board
		g.setColor(new Color(238,232,170));
		g.fillRect(25,25,560,560);
		
		
		//Display score and lives of players
		font=new Font("SANSSERIF", Font.BOLD, 30);
		
		scorelabel1.setBounds(645, 100, 200, 70);
		scorelabel2.setBounds(880, 100,200, 70 );
		
		scorelabel1.setFont(font);
		scorelabel2.setFont(font);
		
		scorelabel1.setText("Score: "+scorep1);
		scorelabel2.setText("Score: "+scorep2);
		
		scorelabel1.setOpaque(false);
		scorelabel1.setBackground(new Color(230,177,55));
		
		scorelabel2.setOpaque(false);
		scorelabel2.setBackground(new Color(230,177,55));
		
		scorelabel1.setForeground(new Color(255,255,255));
		scorelabel2.setForeground(new Color(255,255,255));
		scorelabel1.setHorizontalAlignment(JLabel.CENTER);
		scorelabel2.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(scorelabel1);
		this.add(scorelabel2);
		
		//Score board
		g.setColor(new Color(230,177,55));
		g.fillRect(645, 100, 200, 70);
		

		g.setColor(new Color(230,177,55));
		g.fillRect(880, 100,200, 70 );

		scorelabel1.setText("Score: "+scorep1);
		scorelabel2.setText("Score: "+scorep2);
		
		
	}
	
	void updateScore(String p,int score) {
		
		if(p.equalsIgnoreCase("red")) {
			scorelabel1.setText("Score: "+score);
			scorep1=score;
		}
		if(p.equalsIgnoreCase("black")) {
			scorep2=score;
			scorelabel2.setText("Score: "+score);
		}
			
		
		repaint();
	}
	
	public void paintGameOver(int player) {
		
		JLabel gameover=new JLabel();
		gameover.setText("Player "+player+"wins!");
		font=new Font("SANSSERIF", Font.BOLD, 30);
		gameover.setBounds(645, 200, 435, 70);
		gameover.setBackground(new Color(230,177,55));
		gameover.setOpaque(true);
		
	}
	
}
