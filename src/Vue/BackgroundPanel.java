package Vue ;


import javax.swing.*;





import java.awt.*;


public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Constructeur pour charger l'image
    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner l'image sur tout le JPanel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
