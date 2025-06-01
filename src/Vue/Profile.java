package Vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import DB.*;

/**
 * Profile class represents a user profile panel with editable information
 * including name, email, and password. It provides a modern UI with rounded
 * corners and smooth animations.
 */
public class Profile extends JPanel {
    
    // User data
    private User user;
    
    // UI Components
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JButton changeNameBtn;
    private JButton changePasswordBtn;
    private JButton changeEmailBtn;
    private JButton saveChangesBtn;
    private JPanel contentPanel;
   
    /**
     * Constructor initializes the profile panel with all UI components
     */
    public Profile() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(new Color(30, 35, 50)); // Dark background
        
        // Create content panel with modern card-like appearance
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 45, 60)); // Slightly lighter background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        contentPanel.setLayout(new GridBagLayout());
        
        // Initialize and setup components
        initComponents();
        setupLayout();
        setupListeners();
        
        // Add content panel to main panel
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes all UI components with appropriate styles
     */
    private void initComponents() {
        // Initialize labels with modern font and color
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Color labelColor = new Color(220, 220, 220);
        
        // Name label showing current user's name
        nameLabel = new JLabel("Name: " + SignIn.user.getName());
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(labelColor);
        
        // Email label showing current user's email (or "Not set" if null)
        emailLabel = new JLabel("Email: " + (SignIn.user.getEmail() != null ? SignIn.user.getEmail() : "Not set"));
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(labelColor);
        
        // Button styling
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color buttonBg = new Color(70, 130, 180); // Steel blue
        Color buttonFg = Color.WHITE;
        
        // Create styled buttons with system icons
        changeNameBtn = createModernButton("Edit Name", buttonFont, buttonBg, buttonFg);
        changePasswordBtn = createModernButton("Change Password", buttonFont, buttonBg, buttonFg);
        changeEmailBtn = createModernButton("Edit Email", buttonFont, buttonBg, buttonFg);
        saveChangesBtn = createModernButton("Save Profile", buttonFont, new Color(50, 150, 100), Color.WHITE); // Green for save
        
        // Set icons using system defaults
        changeNameBtn.setIcon(new TextIcon("✏", buttonFont, buttonFg)); // Pencil icon
         changeNameBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        changePasswordBtn.setIcon(new TextIcon("🔒", buttonFont, buttonFg)); // Lock icon
         changePasswordBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        changeEmailBtn.setIcon(new TextIcon("✉", buttonFont, buttonFg)); // Envelope icon
         changeEmailBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        saveChangesBtn.setIcon(new TextIcon("💾", buttonFont, Color.WHITE)); // Floppy disk icon
        saveChangesBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
    }
    
    /**
     * Creates a modern styled button with rounded corners and hover effects
     */
    private JButton createModernButton(String text, Font font, Color bg, Color fg) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bg.brighter());
                } else {
                    g2.setColor(bg);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };
        
        button.setFont(font);
        button.setForeground(fg);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    /**
     * Sets up the layout of all components using GridBagLayout
     */
    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Header section
        JLabel header = new JLabel("Profile Settings");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(header, gbc);
        
        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(70, 130, 180));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 20, 0);
        contentPanel.add(separator, gbc);
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // User info section - Name
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        contentPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        contentPanel.add(changeNameBtn, gbc);
        
        // User info section - Email
        gbc.gridy = 3;
        gbc.gridx = 0;
        contentPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        contentPanel.add(changeEmailBtn, gbc);
        
        // Security section header
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel securityHeader = new JLabel("Security");
        securityHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        securityHeader.setForeground(new Color(200, 200, 200));
        contentPanel.add(securityHeader, gbc);
        
        // Security section - Password button
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        contentPanel.add(changePasswordBtn, gbc);
        
        // Save button at bottom
        gbc.gridy = 6;
        gbc.insets = new Insets(30, 15, 0, 15);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(saveChangesBtn, gbc);
    }

    /**
     * Sets up action listeners for all buttons
     */
    private void setupListeners() {
        changeNameBtn.addActionListener(this::changeNameAction);
        changePasswordBtn.addActionListener(this::changePasswordAction);
        changeEmailBtn.addActionListener(this::changeEmailAction);
        saveChangesBtn.addActionListener(this::saveChangesAction);
    }

    /**
     * Handles name change action
     */
    private void changeNameAction(ActionEvent e) {
        String newName = showModernInputDialog("Edit Name", "Enter your new name:", SignIn.user.getName());
        if (newName != null && !newName.trim().isEmpty()) {
            SignIn.user.setName(newName);
            nameLabel.setText("Name: " + newName);
        }
    }

    /**
     * Handles password change action with validation
     */
    private void changePasswordAction(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setOpaque(false);
        
        JPasswordField currentPass = new JPasswordField();
        JPasswordField newPass = new JPasswordField();
        JPasswordField confirmPass = new JPasswordField();
        
        panel.add(createInputField("Current Password:", currentPass));
        panel.add(createInputField("New Password:", newPass));
        panel.add(createInputField("Confirm New Password:", confirmPass));
        
        int result = JOptionPane.showOptionDialog(
            this, 
            panel, 
            "Change Password", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE, 
            null, 
            new Object[]{"Change Password", "Cancel"}, 
            "Change Password");
        
        if (result == JOptionPane.OK_OPTION) {
            String currentPassStr = new String(currentPass.getPassword());
            String newPassStr = new String(newPass.getPassword());
            String confirmPassStr = new String(confirmPass.getPassword());

            if (!currentPassStr.equals(SignIn.user.getPassword())) {
                showErrorDialog("Current password is incorrect!");
                return;
            }

            if (!newPassStr.equals(confirmPassStr)) {
                showErrorDialog("New passwords do not match!");
                return;
            }

            if (newPassStr.trim().isEmpty()) {
                showErrorDialog("New password cannot be empty!");
                return;
            }

            SignIn.user.setPassword(newPassStr);
            showSuccessDialog("Password changed successfully!");
            DB.changePassword(SignIn.user.getName(), newPassStr);
        }
    }

    /**
     * Handles email change action
     */
    private void changeEmailAction(ActionEvent e) {
        String currentEmail = SignIn.user.getEmail() != null ? SignIn.user.getEmail() : "";
        String newEmail = showModernInputDialog("Edit Email", "Enter your new email:", currentEmail);
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            SignIn.user.setEmail(newEmail);
            DB.updateEmail(SignIn.user.getName(), SignIn.user.getEmail());
            emailLabel.setText("Email: " + newEmail);
        }
    }

    /**
     * Handles save changes action
     */
    private void saveChangesAction(ActionEvent e) {
        DB.updateUserInformation(
            SignIn.user.getName(), 
            SignIn.user.getName(),
            SignIn.user.getPassword(), 
            SignIn.user.getEmail()
        );
        showSuccessDialog("Profile changes saved successfully!");
    }
    
    /**
     * Creates a modern styled input dialog
     */
    private String showModernInputDialog(String title, String message, String initialValue) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        
        JTextField textField = new JTextField(initialValue);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180)), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        
        int result = JOptionPane.showOptionDialog(
            this, 
            panel, 
            title, 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE, 
            null, 
            new Object[]{"Confirm", "Cancel"}, 
            "Confirm");
        
        if (result == JOptionPane.OK_OPTION) {
            return textField.getText();
        }
        return null;
    }
    
    /**
     * Creates an input field with label
     */
    private JPanel createInputField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if (field instanceof JTextField) {
            ((JTextField)field).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Shows an error dialog
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
            this, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows a success dialog
     */
    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(
            this, 
            message, 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sets user data to display in the profile
     */
    public void setUserData(String name, String email) {
        nameLabel.setText("Name: " + name);
        emailLabel.setText("Email: " + email);
    }
    
    /**
     * TextIcon class for rendering Unicode characters as icons
     */
    private static class TextIcon implements Icon {
        private final String text;
        private final Font font;
        private final Color color;
        
        public TextIcon(String text, Font font, Color color) {
            this.text = text;
            this.font = font;
            this.color = color;
        }
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(font);
            g2.setColor(color);
            FontMetrics fm = g2.getFontMetrics();
            int textX = x + (getIconWidth() - fm.stringWidth(text)) / 2;
            int textY = y + ((getIconHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(text, textX, textY);
            g2.dispose();
        }
        
        @Override
        public int getIconWidth() {
            return font.getSize() + 4;
        }
        
        @Override
        public int getIconHeight() {
            return font.getSize() + 4;
        }
    }
}