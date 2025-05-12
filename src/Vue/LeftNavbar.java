/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 *
 * @author stof
 */




public class LeftNavbar extends JPanel {

    public JButton homeBtn;
    public JButton adminBtn;
    public JButton panierBtn;
    public JButton logoutBtn;

    public LeftNavbar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(230, 230, 250));
        setPreferredSize(new Dimension(200, getHeight()));

        // Initialize buttons
        homeBtn = createNavButton("Page Principale");
        adminBtn = createNavButton("Espace Admin");
        panierBtn = createNavButton("Panier");
        logoutBtn = createNavButton("Déconnexion");

        // Add buttons to the panel
        add(Box.createVerticalStrut(30));
        add(homeBtn);
        add(Box.createVerticalStrut(10));
        add(adminBtn);
        add(Box.createVerticalStrut(10));
        add(panierBtn);
        add(Box.createVerticalGlue());
        add(logoutBtn);
        add(Box.createVerticalStrut(20));
    }

    private JButton createNavButton(String title) {
        JButton button = new JButton(title);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    // Optional: Set action listeners externally
    public void setHomeAction(ActionListener listener) {
        homeBtn.addActionListener(listener);
    }

    public void setAdminAction(ActionListener listener) {
        adminBtn.addActionListener(listener);
    }

    public void setPanierAction(ActionListener listener) {
        panierBtn.addActionListener(listener);
    }

    public void setLogoutAction(ActionListener listener) {
        logoutBtn.addActionListener(listener);
    }
}