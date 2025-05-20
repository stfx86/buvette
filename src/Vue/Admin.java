package Vue;

import DB.DB;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Standalone class for the admin panel, handling dish management with database integration.
 */
public class Admin extends JPanel {
    private final List<Plat> menu;
    private final Map<String, List<Plat>> menuData;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final List<String> nameSuggestions;
    private final List<String> categorySuggestions;
    private String selectedImagePath; // Store the selected image path

    public Admin(List<Plat> menu, Map<String, List<Plat>> menuData, CardLayout cardLayout, JPanel cardPanel) {
        this.menu = menu;
        this.menuData = menuData;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.nameSuggestions = new ArrayList<>();
        this.categorySuggestions = new ArrayList<>();
        this.selectedImagePath = ""; // Initialize empty
        initializeData();
        initializeUI();
    }

    private void initializeData() {
        // Load dishes from database
        List<Plat> plats = DB.listPlats();
        menu.clear();
        menuData.clear();
        menu.addAll(plats);
        for (Plat plat : plats) {
            menuData.computeIfAbsent(plat.getCategorie(), k -> new ArrayList<>()).add(plat);
            if (!nameSuggestions.contains(plat.getNom())) {
                nameSuggestions.add(plat.getNom());
            }
            if (!categorySuggestions.contains(plat.getCategorie())) {
                categorySuggestions.add(plat.getCategorie());
            }
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/py.png");
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

        // Form fields
        JLabel nomLabel = new JLabel("Nom du plat :");
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nomLabel, gbc);

        JTextField nomField = new JTextField(20);
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

        JTextField prixField = new JTextField(20);
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

        JTextField descField = new JTextField(20);
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

        JTextField catField = new JTextField(20);
        catField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(catField, gbc);

        JLabel imgLabel = new JLabel("Image :");
        imgLabel.setForeground(Color.WHITE);
        imgLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(imgLabel, gbc);

        // Image selection panel (button + label)
        JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imgPanel.setOpaque(false);
        JButton chooseImageButton = new JButton("Choisir Image");
        styleButton(chooseImageButton);
        JLabel imgPathLabel = new JLabel("Aucune image sélectionnée");
        imgPathLabel.setForeground(Color.WHITE);
        imgPathLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        imgPanel.add(chooseImageButton);
        imgPanel.add(imgPathLabel);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(imgPanel, gbc);

        // File chooser for images
        JFileChooser fileChooser = new JFileChooser("src/images/");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Images (jpg, jpeg, png)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(imageFilter);
        chooseImageButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath = selectedFile.getAbsolutePath();
                imgPathLabel.setText(selectedFile.getName()); // Show file name only
                imgPathLabel.setToolTipText(selectedImagePath); // Full path in tooltip
            }
        });

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
            tableModel.addRow(new Object[]{plat.getNom(), plat.getPrix(), plat.getDescription(), plat.getCategorie(), plat.getImagePath()});
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

        JButton listUsersButton = new JButton("Lister Utilisateurs");
        styleButton(listUsersButton, new Color(0, 204, 102));
        adminButtons.add(listUsersButton);

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
                String img = selectedImagePath.trim();
                if (!nom.isEmpty() && prix >= 0 && !cat.isEmpty() && !img.isEmpty()) {
                    Plat plat = new Plat(nom, prix, desc, cat, img);
                    if (DB.addPlat(plat)) {
                        menu.add(plat);
                        menuData.computeIfAbsent(cat, k -> new ArrayList<>()).add(plat);
                        tableModel.addRow(new Object[]{plat.getNom(), plat.getPrix(), plat.getDescription(), plat.getCategorie(), plat.getImagePath()});
                        clearForm(new JTextField[]{nomField, prixField, descField, catField}, imgPathLabel);
                        // Update suggestions (for consistency)
                        if (!nameSuggestions.contains(nom)) {
                            nameSuggestions.add(nom);
                        }
                        if (!categorySuggestions.contains(cat)) {
                            categorySuggestions.add(cat);
                        }
                        JOptionPane.showMessageDialog(this, "Plat ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du plat dans la base de données !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer des données valides et sélectionner une image !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prix invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            clearForm(new JTextField[]{nomField, prixField, descField, catField}, imgPathLabel);
        });

        modifyButton.addActionListener(e -> {
            int selectedRow = adminMenuTable.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String nom = nomField.getText().trim();
                    double prix = Double.parseDouble(prixField.getText().trim());
                    String desc = descField.getText().trim();
                    String cat = catField.getText().trim();
                    String img = selectedImagePath.trim();
                    if (!nom.isEmpty() && prix >= 0 && !cat.isEmpty() && !img.isEmpty()) {
                        String oldCat = (String) tableModel.getValueAt(selectedRow, 3);
                        Plat selectedPlat = menu.get(selectedRow);
                        Plat newPlat = new Plat(nom, prix, desc, cat, img);
                        if (DB.updatePlat(selectedPlat, newPlat)) {
                            menuData.get(oldCat).remove(selectedPlat);
                            selectedPlat.setNom(nom);
                            selectedPlat.setPrix(prix);
                            selectedPlat.setDescription(desc);
                            selectedPlat.setCategorie(cat);
                            selectedPlat.setImagePath(img);
                            menuData.computeIfAbsent(cat, k -> new ArrayList<>()).add(selectedPlat);
                            tableModel.setValueAt(nom, selectedRow, 0);
                            tableModel.setValueAt(prix, selectedRow, 1);
                            tableModel.setValueAt(desc, selectedRow, 2);
                            tableModel.setValueAt(cat, selectedRow, 3);
                            tableModel.setValueAt(img, selectedRow, 4);
                            clearForm(new JTextField[]{nomField, prixField, descField, catField}, imgPathLabel);
                            // Update suggestions (for consistency)
                            if (!nameSuggestions.contains(nom)) {
                                nameSuggestions.add(nom);
                            }
                            if (!categorySuggestions.contains(cat)) {
                                categorySuggestions.add(cat);
                            }
                            JOptionPane.showMessageDialog(this, "Plat modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du plat dans la base de données !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer des données valides et sélectionner une image !", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    if (DB.deletePlat(selectedPlat.getNom())) {
                        menu.remove(selectedRow);
                        menuData.get(selectedPlat.getCategorie()).remove(selectedPlat);
                        if (menuData.get(selectedPlat.getCategorie()).isEmpty()) {
                            menuData.remove(selectedPlat.getCategorie());
                            categorySuggestions.remove(selectedPlat.getCategorie());
                        }
                        nameSuggestions.remove(selectedPlat.getNom());
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Plat supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du plat dans la base de données !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un plat à supprimer !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        listUsersButton.addActionListener(e -> {
            List<String> users = DB.listUsers();
            if (users.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun utilisateur trouvé dans la base de données.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder userList = new StringBuilder("Utilisateurs :\n");
                for (String user : users) {
                    userList.append("- ").append(user).append("\n");
                }
                JOptionPane.showMessageDialog(this, userList.toString(), "Liste des Utilisateurs", JOptionPane.INFORMATION_MESSAGE);
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
                        selectedImagePath = (String) tableModel.getValueAt(selectedRow, 4);
                        imgPathLabel.setText(new File(selectedImagePath).getName()); // Show file name
                        imgPathLabel.setToolTipText(selectedImagePath); // Full path in tooltip
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

    private void clearForm(JTextField[] fields, JLabel imgPathLabel) {
        for (JTextField field : fields) {
            field.setText("");
        }
        selectedImagePath = "";
        imgPathLabel.setText("Aucune image sélectionnée");
        imgPathLabel.setToolTipText(null);
    }}