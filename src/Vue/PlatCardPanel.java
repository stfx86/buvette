package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Timer;

// Assuming Plat is a custom class with these methods
// If Plat is in a different package, you'll need to import it
// e.g., import your.package.Plat;

class PlatCardPanel extends JPanel {  // Removed 'private' modifier - not allowed for top-level classes
    private int shadowSize = 0;

    public PlatCardPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Shadow Effect (Dynamic based on hover)
        if (shadowSize > 0) {
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize * 2, 
                            getHeight() - shadowSize * 2, 20, 20);
        }
        
        // Frosted Glass Effect (Translucent white with blur)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2d.setColor(new Color(255, 255, 255, 200)); // Semi-transparent white
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        
        // Border with subtle gradient
        GradientPaint borderGradient = new GradientPaint(
            0, 0, new Color(180, 180, 255, 100),
            getWidth(), getHeight(), new Color(100, 100, 255, 100)
        );
        g2d.setPaint(borderGradient);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        
        g2d.dispose();
    }

    public void setShadowSize(int size) {
        this.shadowSize = size;
        repaint();
    }
}

