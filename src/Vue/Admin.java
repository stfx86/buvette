package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Standalone class for the admin panel, handling dish management.
 */
public class Admin extends JPanel {
    private final List<Plat> menu;
    private final Map<String, List<Plat>> menuData;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final String DATA_FILE = "menu.txt";
    private final List<String> nameSuggestions;
    private final List<String> categorySuggestions;

    public Admin(List<Plat> menu, Map<String, List<Plat>> menuData, CardLayout cardLayout, JPanel cardPanel) {
        this.menu = menu;
        this.menuData = menuData;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.nameSuggestions = new ArrayList<>();
        for (Plat plat : menu) {
            nameSuggestions.add(plat.nom);
        }
        this.categorySuggestions = new ArrayList<>(menuData.keySet());
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/background.jpg");
        backgroundPanel.setLayout(new BorderLayout(10, 10));

        // Form panel for adding/editing dishes
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Gestion des Plats",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 16), Color.WHITE));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Suggestions for autocomplete
        List<String> descriptionSuggestions = Arrays.asList(
                "Delicious and fresh dish",
                "Classic recipe with a modern twist",
                "Made with premium ingredients",
                "Perfect for sharing"
        );
        List<String> priceSuggestions = Arrays.asList("10", "15", "20", "25", "30", "35", "40");
        List<String> imageSuggestions = Arrays.asList(
                "src/images/pizza1.jpeg",
                "src/images/pizza2.jpeg",
                "src/images/burger1.jpeg",
                "src/images/burger2.jpeg",
                "src/images/dessert1.jpeg",
                "src/images/dessert2.jpeg",
                "src/images/drink1.jpeg",
                "src/images/drink2.jpeg"
        );

        // Form fields with autocomplete
        JLabel nomLabel = new JLabel("Nom du plat :");
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nomLabel, gbc);

        AutocompleteTextField nomField = new AutocompleteTextField(20, nameSuggestions);
        nomField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nomField, gbc);

        JLabel prixLabel = new JLabel("Prix (DH) :");
        prixLabel.setForeground(Color.WHITE);
        prixLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(prixLabel, gbc);

        AutocompleteTextField prixField = new AutocompleteTextField(20, priceSuggestions);
        prixField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(prixField, gbc);

        JLabel descLabel = new JLabel("Description :");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(descLabel, gbc);

        AutocompleteTextField descField = new AutocompleteTextField(20, descriptionSuggestions);
        descField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(descField, gbc);

        JLabel catLabel = new JLabel("Catégorie :");
        catLabel.setForeground(Color.WHITE);
        catLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(catLabel, gbc);

        AutocompleteTextField catField = new AutocompleteTextField(20, categorySuggestions);
        catField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(catField, gbc);

        JLabel imgLabel = new JLabel("Chemin image :");
        imgLabel.setForeground(Color.WHITE);
        imgLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(imgLabel, gbc);

        AutocompleteTextField imgField = new AutocompleteTextField(20, imageSuggestions);
        imgField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(imgField, gbc);

        // Buttons for form actions
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        formButtonPanel.setOpaque(false);

        JButton addButton = new JButton("Ajouter");
        styleButton(addButton);
        formButtonPanel.add(addButton);

        JButton clearButton = new JButton("Effacer");
        styleButton(clearButton, new Color(200, 200, 200));
        formButtonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(formButtonPanel, gbc);

        backgroundPanel.add(formPanel, BorderLayout.NORTH);

        // Table for displaying dishes
        String[] columns = {"Nom", "Prix (DH)", "Description", "Catégorie", "Image"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        JTable adminMenuTable = new JTable(tableModel);
        adminMenuTable.setFont(new Font("Arial", Font.PLAIN, 14));
        adminMenuTable.setRowHeight(25);
        adminMenuTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        adminMenuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Populate table
        for (Plat plat : menu) {
            tableModel.addRow(new Object[]{plat.nom, plat.prix, plat.description, plat.categorie, plat.imagePath});
        }

        JScrollPane tableScrollPane = new JScrollPane(adminMenuTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backgroundPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom buttons
        JPanel adminButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        adminButtons.setOpaque(false);

        JButton modifyButton = new JButton("Modifier");
        styleButton(modifyButton);
        adminButtons.add(modifyButton);

        JButton deleteButton = new JButton("Supprimer");
        styleButton(deleteButton, new Color(255, 80, 80));
        adminButtons.add(deleteButton);

        JButton backButtonAdmin = new JButton("Retour");
        styleButton(backButtonAdmin, new Color(200, 200, 200));
        adminButtons.add(backButtonAdmin);

        backgroundPanel.add(adminButtons, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(e -> {
            try {
                String nom = nomField.getText().trim();
                double prix = Double.parseDouble(prixField.getText().trim());
                String desc = descField.getText().trim();
                String cat = catField.getText().trim();
                String img = imgField.getText().trim();
                if (!nom.isEmpty() && prix >= 0 && !cat.isEmpty()) {
                    Plat plat = new Plat(nom, prix, desc, cat, img);
                    menu.add(plat);
                    menuData.computeIfAbsent(cat, k -> new ArrayList<>()).add(plat);
                    tableModel.addRow(new Object[]{plat.nom, plat.prix, plat.description, plat.categorie, plat.imagePath});
                    saveMenu();
                    clearForm(nomField, prixField, descField, catField, imgField);
                    // Update suggestions
                    nameSuggestions.add(nom);
                    nomField.setSuggestions(nameSuggestions);
                    if (!categorySuggestions.contains(cat)) {
                        categorySuggestions.add(cat);
                        catField.setSuggestions(categorySuggestions);
                    }
                    JOptionPane.showMessageDialog(this, "Plat ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer des données valides !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prix invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            clearForm(nomField, prixField, descField, catField, imgField);
        });

        modifyButton.addActionListener(e -> {
            int selectedRow = adminMenuTable.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String nom = nomField.getText().trim();
                    double prix = Double.parseDouble(prixField.getText().trim());
                    String desc = descField.getText().trim();
                    String cat = catField.getText().trim();
                    String img = imgField.getText().trim();
                    if (!nom.isEmpty() && prix >= 0 && !cat.isEmpty()) {
                        String oldCat = (String) tableModel.getValueAt(selectedRow, 3);
                        Plat selectedPlat = menu.get(selectedRow);
                        menuData.get(oldCat).remove(selectedPlat);
                        selectedPlat.nom = nom;
                        selectedPlat.prix = prix;
                        selectedPlat.description = desc;
                        selectedPlat.categorie = cat;
                        selectedPlat.imagePath = img;
                        menuData.computeIfAbsent(cat, k -> new ArrayList<>()).add(selectedPlat);
                        tableModel.setValueAt(nom, selectedRow, 0);
                        tableModel.setValueAt(prix, selectedRow, 1);
                        tableModel.setValueAt(desc, selectedRow, 2);
                        tableModel.setValueAt(cat, selectedRow, 3);
                        tableModel.setValueAt(img, selectedRow, 4);
                        saveMenu();
                        clearForm(nomField, prixField, descField, catField, imgField);
                        // Update suggestions
                        if (!nameSuggestions.contains(nom)) {
                            nameSuggestions.add(nom);
                            nomField.setSuggestions(nameSuggestions);
                        }
                        if (!categorySuggestions.contains(cat)) {
                            categorySuggestions.add(cat);
                            catField.setSuggestions(categorySuggestions);
                        }
                        JOptionPane.showMessageDialog(this, "Plat modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer des données valides !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Prix invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un plat à modifier !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = adminMenuTable.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer ce plat ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Plat selectedPlat = menu.get(selectedRow);
                    menu.remove(selectedRow);
                    menuData.get(selectedPlat.categorie).remove(selectedPlat);
                    if (menuData.get(selectedPlat.categorie).isEmpty()) {
                        menuData.remove(selectedPlat.categorie);
                        categorySuggestions.remove(selectedPlat.categorie);
                        catField.setSuggestions(categorySuggestions);
                    }
                    nameSuggestions.remove(selectedPlat.nom);
                    nomField.setSuggestions(nameSuggestions);
                    tableModel.removeRow(selectedRow);
                    saveMenu();
                    JOptionPane.showMessageDialog(this, "Plat supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un plat à supprimer !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButtonAdmin.addActionListener(e -> cardLayout.show(cardPanel, "Home"));

        // Double-click table row to populate form
        adminMenuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = adminMenuTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        nomField.setText((String) tableModel.getValueAt(selectedRow, 0));
                        prixField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
                        descField.setText((String) tableModel.getValueAt(selectedRow, 2));
                        catField.setText((String) tableModel.getValueAt(selectedRow, 3));
                        imgField.setText((String) tableModel.getValueAt(selectedRow, 4));
                    }
                }
            }
        });

        add(backgroundPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        styleButton(button, new Color(0, 153, 255));
    }

    private void styleButton(JButton button, Color baseColor) {
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
    }

    private void clearForm(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void saveMenu() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Plat plat : menu) {
                writer.write(plat.nom + ";" + plat.prix + ";" + plat.description + ";" + plat.categorie + ";" + plat.imagePath);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}