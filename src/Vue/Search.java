/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author dell
 */
public class Search  extends  JPanel{
    
    private JPanel createSearchPanel() {
    JPanel searchPanel = new JPanel(new GridBagLayout());
    searchPanel.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Search Field
    JTextField searchField = new JTextField(20);
    searchField.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    searchPanel.add(searchField, gbc);

    // Search Type Combo Box
    String[] searchTypes = {"Nom", "Catégorie", "Prix"};
    JComboBox<String> searchTypeCombo = new JComboBox<>(searchTypes);
    searchTypeCombo.setSelectedIndex(0);
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    searchPanel.add(searchTypeCombo, gbc);

    // Price Range Slider (hidden by default)
    JSlider priceSlider = new JSlider(0, 100, 50);
    priceSlider.setMajorTickSpacing(20);
    priceSlider.setMinorTickSpacing(5);
    priceSlider.setPaintTicks(true);
    priceSlider.setPaintLabels(true);
    priceSlider.setVisible(false);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    searchPanel.add(priceSlider, gbc);

    // Search Button
    JButton searchButton = new JButton("Rechercher");
    styleButton(searchButton);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    searchPanel.add(searchButton, gbc);

    // Clear Button
    JButton clearButton = new JButton("Réinitialiser");
    styleButton(clearButton, new Color(200, 50, 50));
    gbc.gridx = 0;
    gbc.gridy = 3;
    searchPanel.add(clearButton, gbc);

    // Show/hide price slider based on search type
    searchTypeCombo.addActionListener(e -> {
        String selected = (String) searchTypeCombo.getSelectedItem();
        priceSlider.setVisible("Prix".equals(selected));
        searchPanel.revalidate();
    });

    // Search action
    searchButton.addActionListener(e -> {
        String searchText = searchField.getText().trim();
        String searchType = (String) searchTypeCombo.getSelectedItem();
        
        switch (searchType) {
            case "Nom":
                searchByName(searchText);
                break;
            case "Catégorie":
                searchByCategory(searchText);
                break;
            case "Prix":
                searchByPrice(priceSlider.getValue());
                break;
        }
    });

    // Clear action
    clearButton.addActionListener(e -> {
        searchField.setText("");
        priceSlider.setValue(50);
        displayPlats("Pizzas"); // Show default category
    });

    // Search on Enter key
    searchField.addActionListener(e -> searchButton.doClick());

    return searchPanel;
}

private void searchByName(String name) {
    List<Plat> matchingPlats = new ArrayList<>();
    for (List<Plat> plats : menuData.values()) {
        for (Plat plat : plats) {
            if (plat.getNom().toLowerCase().contains(name.toLowerCase())) {
                matchingPlats.add(plat);
            }
        }
    }
    displaySearchResults(matchingPlats, "Résultats pour le nom: " + name);
}

private void searchByCategory(String category) {
    List<Plat> matchingPlats = new ArrayList<>();
    for (Map.Entry<String, List<Plat>> entry : menuData.entrySet()) {
        if (entry.getKey().toLowerCase().contains(category.toLowerCase())) {
            matchingPlats.addAll(entry.getValue());
        }
    }
    displaySearchResults(matchingPlats, "Résultats pour la catégorie: " + category);
}

private void searchByPrice(int maxPrice) {
    List<Plat> matchingPlats = new ArrayList<>();
    for (List<Plat> plats : menuData.values()) {
        for (Plat plat : plats) {
            if (plat.getPrix() <= maxPrice) {
                matchingPlats.add(plat);
            }
        }
    }
    displaySearchResults(matchingPlats, "Résultats pour prix ≤ " + maxPrice + " DH");
}

private void displaySearchResults(List<Plat> plats, String title) {
    platsPanel.removeAll();
    
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    titleLabel.setForeground(new Color(0, 100, 200));
    platsPanel.add(titleLabel);
    
    if (plats.isEmpty()) {
        platsPanel.add(new JLabel("Aucun résultat trouvé"));
    } else {
        for (Plat plat : plats) {
            JPanel card = PlatCardCreator.createPlatCard(plat, e -> {
                System.out.println(".platt added");
                panierPanel.addPlat(plat);
            });
            platsPanel.add(card);
        }
    }
    
    platsPanel.revalidate();
    platsPanel.repaint();
}
    
    
    
    
    
    
    
    
    
}
