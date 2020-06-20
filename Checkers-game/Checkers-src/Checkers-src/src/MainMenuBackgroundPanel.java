import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class MainMenuBackgroundPanel extends JPanel {
    Image im;
    public MainMenuBackgroundPanel(Image im){
        this.im = im;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
