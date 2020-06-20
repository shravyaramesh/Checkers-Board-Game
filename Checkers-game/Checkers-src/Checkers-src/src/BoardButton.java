import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;

public class BoardButton extends JButton{
	int a,b;
	
    public BoardButton(int a,int b){
        super();  
        this.a=a;
        this.b=b;
        this.setBackground(new Color(230,177,55));
        this.setOpaque(true);
        this.setBorderPainted(false);
        //this.setForeground(Color.WHITE);
    }

}


