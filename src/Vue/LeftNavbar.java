package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.AbstractBorder;

public class LeftNavbar extends JPanel {
    // Navigation buttons
    private JButton homeBtn;
    
    
    private JButton adminBtn;
      

  
//    private JButton panierBtn;
      JButton panierBtn;

    private JButton profileBtn;
    private JButton settingsBtn;
    private JButton logoutBtn;
    
    public JButton getHomeBtn() {
    return homeBtn;
}

public void setHomeBtn(JButton homeBtn) {
    this.homeBtn = homeBtn;
}

public JButton getAdminBtn() {
    return adminBtn;
}

public void setAdminBtn(JButton adminBtn) {
    this.adminBtn = adminBtn;
}

public JButton getPanierBtn() {
    return panierBtn;
}

public void setPanierBtn(JButton panierBtn) {
    this.panierBtn = panierBtn;
}

public JButton getProfileBtn() {
    return profileBtn;
}

public void setProfileBtn(JButton profileBtn) {
    this.profileBtn = profileBtn;
}

public JButton getSettingsBtn() {
    return settingsBtn;
}

public void setSettingsBtn(JButton settingsBtn) {
    this.settingsBtn = settingsBtn;
}

public JButton getLogoutBtn() {
    return logoutBtn;
}

public void setLogoutBtn(JButton logoutBtn) {
    this.logoutBtn = logoutBtn;
}
public JButton getHomebtn() {
    return homeBtn;
}

public void getHomebtn(JButton logoutBtn) {
    this.homeBtn = logoutBtn;
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private JButton activeButton;
    private Color backgroundColor = new Color(32, 42, 68);
    private Color buttonColor = new Color(50, 70, 100);
    private Color hoverColor = new Color(70, 130, 180);
    private Color activeColor = new Color(30, 144, 255);
    private Color textColor = Color.WHITE;
    
    public LeftNavbar(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(backgroundColor);
        setPreferredSize(new Dimension(250, Integer.MAX_VALUE));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        initializeUI();
        setupButtonActions();
    }

    private void initializeUI() {
        // Add application logo/header
        add(createLogoPanel());
        add(Box.createVerticalStrut(30));

        // Initialize navigation buttons
        homeBtn = createNavButton("Accueil", "🏠");
        homeBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        adminBtn = createNavButton("Admin", "🔒");
        adminBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        panierBtn = createNavButton("Panier", "🛒");
        panierBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        profileBtn = createNavButton("Profil", "👤");
        profileBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        settingsBtn = createNavButton("Paramètres", "⚙️");
        settingsBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        logoutBtn = createNavButton("Déconnexion", "🚪");
        logoutBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        // Add buttons to panel with spacing
        add(homeBtn);
        add(Box.createVerticalStrut(10));
        add(adminBtn);
        add(Box.createVerticalStrut(10));
        add(panierBtn);
        add(Box.createVerticalStrut(10));
        add(profileBtn);
        add(Box.createVerticalStrut(10));
        add(settingsBtn);
        add(Box.createVerticalGlue()); // Pushes logout to bottom
        add(logoutBtn);
        add(Box.createVerticalStrut(20));
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw logo background with subtle gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(40, 50, 80), 
                    getWidth(), 0, new Color(30, 40, 70)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2d.dispose();
            }
        };
        
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(230, 120));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            // Load logo image from resources
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoPanel.add(logoLabel);
        } catch (Exception e) {
            // Fallback text if image fails to load
            JLabel textLabel = new JLabel("Gestion Buvettes");
            textLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            textLabel.setForeground(textColor);
            textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoPanel.add(textLabel);
        }

        // Add subtitle
        JLabel subtitle = new JLabel("Gestion des stocks");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(180, 180, 180));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPanel.add(Box.createVerticalStrut(5));
        logoPanel.add(subtitle);

        return logoPanel;
    }

    private JButton createNavButton(String text, String icon) {
        JButton button = new JButton(icon + "  " + text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw button background
                if (this == activeButton) {
                    g2.setColor(activeColor);
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(buttonColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Draw icon and text
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 45));
        button.setMinimumSize(new Dimension(220, 45));
        button.setPreferredSize(new Dimension(220, 45));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        // Add subtle animation on hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != activeButton) {
                    animateButtonColor(button, buttonColor, hoverColor);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button != activeButton) {
                    animateButtonColor(button, hoverColor, buttonColor);
                }
            }
        });
        
        return button;
    }

    private void animateButtonColor(JButton button, Color from, Color to) {
        Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            private float alpha = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha >= 1) {
                    timer.stop();
                    return;
                }
                
                alpha += 0.1f;
                Color color = blendColors(from, to, alpha);
                button.setBackground(color);
                button.repaint();
            }
        });
        timer.start();
    }

    private Color blendColors(Color from, Color to, float ratio) {
        float inverseRatio = 1.0f - ratio;
        float red = from.getRed() * inverseRatio + to.getRed() * ratio;
        float green = from.getGreen() * inverseRatio + to.getGreen() * ratio;
        float blue = from.getBlue() * inverseRatio + to.getBlue() * ratio;
        return new Color((int)red, (int)green, (int)blue);
    }

    private void setupButtonActions() {
        homeBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "Home");
            setActiveButton(homeBtn);
        });
        
        adminBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "Admin");
            setActiveButton(adminBtn);
        });
        
        panierBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "Panier");
            setActiveButton(panierBtn);
        });
        
        profileBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "Profile");
            setActiveButton(profileBtn);
        });
        
        settingsBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "Settings");
            setActiveButton(settingsBtn);
        });
        
        logoutBtn.addActionListener(e -> {
            // Perform logout operations
            setAdminVisibility(false);
            cardLayout.show(cardPanel, "Login");
            setActiveButton(null);
        });
    }

    public void setActiveButton(JButton button) {
        // Animate the transition if changing buttons
        if (activeButton != null && activeButton != button) {
            animateButtonColor(activeButton, activeColor, buttonColor);
        }
        
        if (button != null && button != activeButton) {
            animateButtonColor(button, buttonColor, activeColor);
        }
        
        this.activeButton = button;
    }

    public void setAdminVisibility(boolean visible) {
        adminBtn.setVisible(visible);
        revalidate();
        repaint();
    }

    // Custom rounded border for buttons
    private static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        
        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius+1, radius+1, radius+2, radius);
        }
    }

    // External action setters
    public void setHomeAction(ActionListener listener) {
        homeBtn.addActionListener(listener);
    }
    
    public void setAdminAction(ActionListener listener) {
        adminBtn.addActionListener(listener);
    }
    
    public void setPanierAction(ActionListener listener) {
        panierBtn.addActionListener(listener);
    }
    
    public void setProfileAction(ActionListener listener) {
        profileBtn.addActionListener(listener);
    }
    
    public void setSettingsAction(ActionListener listener) {
        settingsBtn.addActionListener(listener);
    }
    
    public void setLogoutAction(ActionListener listener) {
        logoutBtn.addActionListener(listener);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Draw subtle gradient background
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(30, 40, 70), 
            getWidth(), 0, new Color(25, 35, 60)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw right border
        g2d.setColor(new Color(60, 80, 120));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
        
        g2d.dispose();
    }
}