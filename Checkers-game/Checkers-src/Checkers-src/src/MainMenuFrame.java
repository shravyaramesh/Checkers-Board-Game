import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenuFrame extends JFrame implements ActionListener {
    
	private MyButton newGameBtn;
    private MyButton exitBtn;
    private JLabel welcomeLabel;
    private final int width = Toolkit.getDefaultToolkit().getScreenSize().width - 50;
    private final int height = Toolkit.getDefaultToolkit().getScreenSize().height - 20;
    Dimension frameSize = new Dimension(width,height);
    
    JLayeredPane layeredPane;
    JPanel backgroundPanel;

    MainMenuFrame(){
        this.setTitle("Welcome toCheckers!");
        this.setLocation(frameSize.width - 1445,frameSize.height - 950);

        layeredPane = new JLayeredPane();
        ImageIcon imc=new ImageIcon("./data/mainmenu.jpg");
        Image im=imc.getImage();
        backgroundPanel = new MainMenuBackgroundPanel(im);
        backgroundPanel.setBounds(0, 0, 1000,740);

        this.setLayout(null);

        welcomeLabel = new JLabel("Welcome To Checkers!");
        Font labelFont = new Font("SANSSERIF", Font.BOLD, 45);
        welcomeLabel.setFont(labelFont);
        welcomeLabel.setForeground(new Color(255,150,0));
        welcomeLabel.setBounds(230, 150, 550, 50);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        newGameBtn = new MyButton("New Game");
        newGameBtn.setBounds(300, 250, 400, 60);
        
        exitBtn = new MyButton("Exit");
        exitBtn.setBounds(300, 350, 400, 60);

        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(welcomeLabel, JLayeredPane.MODAL_LAYER);
        layeredPane.add(newGameBtn, JLayeredPane.MODAL_LAYER);
        layeredPane.add(exitBtn, JLayeredPane.MODAL_LAYER);

        this.setLayeredPane(layeredPane);

        newGameBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        
        this.setSize(1000,740);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameBtn){
            this.dispose();
            new GameFrame();
        }else if (e.getSource() == exitBtn){
            this.setVisible(false);
            this.dispose();
            System.exit(0);
        }
    }
    
    public static void main(String args[]) {
    	new MainMenuFrame();
    }
}
