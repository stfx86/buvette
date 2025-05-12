package Vue ;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;


//public class PlatCardCreator {  // Added a class to contain the createPlatCard method
//    
//    public static JPanel createPlatCard(Plat plat) {  // Made method static and public
//        // Main Card Panel (Now with Glassmorphism!)
//        PlatCardPanel card = new PlatCardPanel();
//        
//        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
//        card.setPreferredSize(new Dimension(270, 360));
//        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Inner padding
//        
//        // ===== [1] FOOD IMAGE (With Rounded Corners & Shadow) =====
//        try {
//            ImageIcon icon = new ImageIcon(plat.getImagePath());
//            Image img = icon.getImage().getScaledInstance(240, 160, Image.SCALE_SMOOTH);
//            
//            // Round Image Effect
//            JLabel imageLabel = new JLabel() {
//                @Override
//                protected void paintComponent(Graphics g) {
//                    Graphics2D g2 = (Graphics2D) g;
//                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                    
//                    // Shadow Effect
//                    g2.setColor(new Color(0, 0, 0, 50));
//                    g2.fillRoundRect(2, 4, 236, 156, 15, 15);
//                    
//                    // Clip image to rounded rectangle
//                    Shape clip = new RoundRectangle2D.Float(0, 0, 236, 156, 15, 15);
//                    g2.setClip(clip);
//                    super.paintComponent(g2);
//                }
//            };
//            
//            imageLabel.setIcon(new ImageIcon(img));
//            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//            imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Spacing below
//            card.add(imageLabel);
//        } catch (Exception e) {
//            JLabel placeholder = new JLabel("Image not available");
//            placeholder.setFont(new Font("Arial", Font.ITALIC, 12));
//            placeholder.setForeground(new Color(120, 120, 120));
//            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
//            card.add(placeholder);
//        }
//        
//        // ===== [2] FOOD NAME (Elegant Typography) =====
//        JLabel nameLabel = new JLabel(plat.getNom());
//        nameLabel.setFont(new Font("Montserrat", Font.BOLD, 20)); // Modern font
//        nameLabel.setForeground(new Color(40, 40, 40)); // Dark gray
//        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        card.add(nameLabel);
//        
//        card.add(Box.createRigidArea(new Dimension(0, 8))); // Spacing
//        
//        // ===== [3] PRICE TAG (Gradient & Glow Effect) =====
//        JLabel priceLabel = new JLabel(plat.getPrix() + " DH") {
//            @Override
//            protected void paintComponent(Graphics g) {
//                Graphics2D g2 = (Graphics2D) g;
//                
//                // Gradient Background
//                GradientPaint gradient = new GradientPaint(
//                    0, 0, new Color(100, 200, 255),
//                    getWidth(), 0, new Color(50, 150, 255)
//                );
//                g2.setPaint(gradient);
//                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
//                
//                // White Text with Shadow
//                g2.setColor(Color.WHITE);
//                g2.setFont(new Font("Arial", Font.BOLD, 16));
//                FontMetrics fm = g2.getFontMetrics();
//                int x = (getWidth() - fm.stringWidth(getText())) / 2;
//                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
//                g2.drawString(getText(), x, y);
//            }
//        };
//        
//        priceLabel.setPreferredSize(new Dimension(100, 30));
//        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
//        card.add(priceLabel);
//        
//        card.add(Box.createRigidArea(new Dimension(0, 12))); // Spacing
//        
//        // ===== [4] DESCRIPTION (Smooth Scrollable Text) =====
//        JTextArea descLabel = new JTextArea(plat.getDescription());
//        descLabel.setFont(new Font("Arial", Font.PLAIN, 13));
//        descLabel.setLineWrap(true);
//        descLabel.setWrapStyleWord(true);
//        descLabel.setEditable(false);
//        descLabel.setOpaque(false);
//        descLabel.setForeground(new Color(80, 80, 80)); // Soft gray
//        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        descLabel.setMaximumSize(new Dimension(240, 60));
//        card.add(descLabel);
//        
//        // ===== [5] HOVER EFFECTS (Shadow Grow + Pulse Animation) =====
//        card.addMouseListener(new MouseAdapter() {
//            private Timer shadowTimer;
//            private int targetShadowSize = 0;
//            private int currentShadowSize = 0;
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                // Stop any existing timer
//                if (shadowTimer != null && shadowTimer.isRunning()) {
//                    shadowTimer.stop();
//                }
//
//                // Grow shadow effect
//                targetShadowSize = 10;
//                shadowTimer = new Timer(20, evt -> {
//                    if (currentShadowSize < targetShadowSize) {
//                        currentShadowSize++;
//                        ((PlatCardPanel)card).setShadowSize(currentShadowSize);
//                    } else {
//                        shadowTimer.stop();
//                    }
//                });
//                shadowTimer.start();
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                // Stop any existing timer
//                if (shadowTimer != null && shadowTimer.isRunning()) {
//                    shadowTimer.stop();
//                }
//
//                // Shrink shadow effect
//                targetShadowSize = 0;
//                shadowTimer = new Timer(20, evt -> {
//                    if (currentShadowSize > targetShadowSize) {
//                        currentShadowSize--;
//                        ((PlatCardPanel)card).setShadowSize(currentShadowSize);
//                    } else {
//                        shadowTimer.stop();
//                    }
//                });
//                shadowTimer.start();
//            }
//        });
//        
//        return card;
//    }
//}


























public class PlatCardCreator {
    
    public static JPanel createPlatCard(Plat plat, ActionListener addToPanierAction) {  // Added ActionListener parameter for the button action
        // Main Card Panel (Now with Glassmorphism!)
        PlatCardPanel card = new PlatCardPanel();
        
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(270, 360));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Inner padding
        
        // ===== [1] FOOD IMAGE (With Rounded Corners & Shadow) =====
        try {
            ImageIcon icon = new ImageIcon(plat.getImagePath());
            Image img = icon.getImage().getScaledInstance(240, 160, Image.SCALE_SMOOTH);
            
            // Round Image Effect
            JLabel imageLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Shadow Effect
                    g2.setColor(new Color(0, 0, 0, 50));
                    g2.fillRoundRect(2, 4, 236, 156, 15, 15);
                    
                    // Clip image to rounded rectangle
                    Shape clip = new RoundRectangle2D.Float(0, 0, 236, 156, 15, 15);
                    g2.setClip(clip);
                    super.paintComponent(g2);
                }
            };
            
            imageLabel.setIcon(new ImageIcon(img));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Spacing below
            card.add(imageLabel);
        } catch (Exception e) {
            JLabel placeholder = new JLabel("Image not available");
            placeholder.setFont(new Font("Arial", Font.ITALIC, 12));
            placeholder.setForeground(new Color(120, 120, 120));
            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(placeholder);
        }
        
        // ===== [2] FOOD NAME (Elegant Typography) =====
        JLabel nameLabel = new JLabel(plat.getNom());
        nameLabel.setFont(new Font("Montserrat", Font.BOLD, 20)); // Modern font
        nameLabel.setForeground(new Color(40, 40, 40)); // Dark gray
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);
        
        card.add(Box.createRigidArea(new Dimension(0, 8))); // Spacing
        
        // ===== [3] PRICE TAG (Gradient & Glow Effect) =====
        JLabel priceLabel = new JLabel(plat.getPrix() + " DH") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                
                // Gradient Background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(100, 200, 255),
                    getWidth(), 0, new Color(50, 150, 255)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // White Text with Shadow
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };
        
        priceLabel.setPreferredSize(new Dimension(100, 30));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        card.add(priceLabel);
        
        card.add(Box.createRigidArea(new Dimension(0, 12))); // Spacing
        
        // ===== [4] DESCRIPTION (Smooth Scrollable Text) =====
        JTextArea descLabel = new JTextArea(plat.getDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        descLabel.setLineWrap(true);
        descLabel.setWrapStyleWord(true);
        descLabel.setEditable(false);
        descLabel.setOpaque(false);
        descLabel.setForeground(new Color(80, 80, 80)); // Soft gray
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setMaximumSize(new Dimension(240, 60));
        card.add(descLabel);
        
        // ===== [5] ADD TO PANIER BUTTON =====
        JButton addButton = new JButton("Add to Panier");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(50, 150, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.addActionListener(addToPanierAction);  // Set the action for the button
        
        card.add(addButton);
        
        // ===== [6] HOVER EFFECTS (Shadow Grow + Pulse Animation) =====
        card.addMouseListener(new MouseAdapter() {
            private Timer shadowTimer;
            private int targetShadowSize = 0;
            private int currentShadowSize = 0;

            @Override
            public void mouseEntered(MouseEvent e) {
                // Stop any existing timer
                if (shadowTimer != null && shadowTimer.isRunning()) {
                    shadowTimer.stop();
                }

                // Grow shadow effect
                targetShadowSize = 10;
                shadowTimer = new Timer(20, evt -> {
                    if (currentShadowSize < targetShadowSize) {
                        currentShadowSize++;
                        ((PlatCardPanel)card).setShadowSize(currentShadowSize);
                    } else {
                        shadowTimer.stop();
                    }
                });
                shadowTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Stop any existing timer
                if (shadowTimer != null && shadowTimer.isRunning()) {
                    shadowTimer.stop();
                }

                // Shrink shadow effect
                targetShadowSize = 0;
                shadowTimer = new Timer(20, evt -> {
                    if (currentShadowSize > targetShadowSize) {
                        currentShadowSize--;
                        ((PlatCardPanel)card).setShadowSize(currentShadowSize);
                    } else {
                        shadowTimer.stop();
                    }
                });
                shadowTimer.start();
            }
        });
        
        return card;
    }
}
