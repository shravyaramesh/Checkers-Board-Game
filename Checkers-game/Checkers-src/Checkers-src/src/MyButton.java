import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class MyButton extends JButton {
	 public MyButton(String text){
	        super(text);
	        Font f = new Font("SERIF", Font.BOLD, 20);
	        this.setFont(f);
	        
	        this.setBackground(Color.WHITE);
	        this.setOpaque(true);
	        this.setBorderPainted(false);
	        this.setForeground(new Color(255,165,0));
	    }
}
