package Vue;

import DB.DB;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Comprehensive admin panel for dish management with database integration.
 */
public class Admin extends JPanel {
    private final List<Plat> menu;
    private final Map<String, List<Plat>> menuData;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final List<String> nameSuggestions;
    private final List<String> categorySuggestions;
    private String selectedImagePath;
    private DefaultTableModel tableModel;
    private JTable adminMenuTable;

    public Admin(List<Plat> menu, Map<String, List<Plat>> menuData, CardLayout cardLayout, JPanel cardPanel) {
        this.menu = menu;
        this.menuData = menuData;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.nameSuggestions = new ArrayList<>();
        this.categorySuggestions = new ArrayList<>();
        this.selectedImagePath = "";
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

        // Create toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setOpaque(false);
        
        JButton backButton = new JButton("Retour");
        styleButton(backButton, new Color(200, 200, 200));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Home"));
        
        JButton listUsersButton = new JButton("Lister Utilisateurs");
        styleButton(listUsersButton, new Color(0, 204, 102));
        listUsersButton.addActionListener(e -> showUserList());
        
        toolbar.add(backButton);
        toolbar.add(listUsersButton);
        backgroundPanel.add(toolbar, BorderLayout.NORTH);

        // Add search panel
        backgroundPanel.add(createAdminSearchPanel(), BorderLayout.CENTER);

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

        JButton chooseImageUrlButton = new JButton("URL");
        styleButton(chooseImageUrlButton);
        imgPanel.add(chooseImageUrlButton);
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
                imgPathLabel.setText(selectedFile.getName());
                imgPathLabel.setToolTipText(selectedImagePath);
            }
        });

        chooseImageUrlButton.addActionListener(e -> {
            String imageUrl = JOptionPane.showInputDialog(null, "Entrez l'URL de l'image :", 
                    "Choisir Image par URL", JOptionPane.PLAIN_MESSAGE);
            
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                try {
                    URL url = new URL(imageUrl);
                    BufferedImage image = ImageIO.read(url);
                    if (image != null) {
                        File tempFile = File.createTempFile("downloaded_image_", ".png");
                        ImageIO.write(image, "png", tempFile);
                        selectedImagePath = tempFile.getAbsolutePath();
                        imgPathLabel.setText(tempFile.getName());
                        imgPathLabel.setToolTipText(selectedImagePath);
                    } else {
                        showError("L'image n'a pas pu être chargée depuis l'URL fournie.");
                    }
                } catch (IOException ex) {
                    showError("URL invalide ou problème lors du téléchargement de l'image.");
                }
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

        // Table for displaying dishes
        String[] columns = {"Nom", "Prix (DH)", "Description", "Catégorie", "Image"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adminMenuTable = new JTable(tableModel);
        adminMenuTable.setFont(new Font("Arial", Font.PLAIN, 14));
        adminMenuTable.setRowHeight(25);
        adminMenuTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        adminMenuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Populate table
        refreshTable();

        JScrollPane tableScrollPane = new JScrollPane(adminMenuTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bottom buttons
        JPanel adminButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        adminButtons.setOpaque(false);

        JButton modifyButton = new JButton("Modifier");
        styleButton(modifyButton);
        adminButtons.add(modifyButton);

        JButton deleteButton = new JButton("Supprimer");
        styleButton(deleteButton, new Color(255, 80, 80));
        adminButtons.add(deleteButton);

        // Layout organization
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        centerPanel.add(adminButtons, BorderLayout.SOUTH);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        add(backgroundPanel, BorderLayout.CENTER);

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
                        refreshTable();
                        clearForm(new JTextField[]{nomField, prixField, descField, catField}, imgPathLabel);
                        
                        // Update suggestions
                        if (!nameSuggestions.contains(nom)) {
                            nameSuggestions.add(nom);
                        }
                        if (!categorySuggestions.contains(cat)) {
                            categorySuggestions.add(cat);
                        }
                        showSuccess("Plat ajouté avec succès !");
                    } else {
                        showError("Erreur lors de l'ajout du plat dans la base de données !");
                    }
                } else {
                    showError("Veuillez entrer des données valides et sélectionner une image !");
                }
            } catch (NumberFormatException ex) {
                showError("Prix invalide !");
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
                            refreshTable();
                            clearForm(new JTextField[]{nomField, prixField, descField, catField}, imgPathLabel);
                            
                            // Update suggestions
                            if (!nameSuggestions.contains(nom)) {
                                nameSuggestions.add(nom);
                            }
                            if (!categorySuggestions.contains(cat)) {
                                categorySuggestions.add(cat);
                            }
                            showSuccess("Plat modifié avec succès !");
                        } else {
                            showError("Erreur lors de la modification du plat dans la base de données !");
                        }
                    } else {
                        showError("Veuillez entrer des données valides et sélectionner une image !");
                    }
                } catch (NumberFormatException ex) {
                    showError("Prix invalide !");
                }
            } else {
                showError("Sélectionnez un plat à modifier !");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = adminMenuTable.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                        "Voulez-vous vraiment supprimer ce plat ?", 
                        "Confirmation", JOptionPane.YES_NO_OPTION);
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
                        refreshTable();
                        showSuccess("Plat supprimé avec succès !");
                    } else {
                        showError("Erreur lors de la suppression du plat dans la base de données !");
                    }
                }
            } else {
                showError("Sélectionnez un plat à supprimer !");
            }
        });

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
                        imgPathLabel.setText(new File(selectedImagePath).getName());
                        imgPathLabel.setToolTipText(selectedImagePath);
                    }
                }
            }
        });
    }

    private JPanel createAdminSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search Field
        JTextField searchField = new JTextField(25);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Search Options
        String[] searchTypes = {"Tous", "Nom", "Catégorie", "Prix"};
        JComboBox<String> searchTypeCombo = new JComboBox<>(searchTypes);
        
        // Search Button
        JButton searchButton = new JButton("Rechercher");
        styleButton(searchButton);
        
        // Clear Button
        JButton clearButton = new JButton("Réinitialiser");
        styleButton(clearButton, new Color(200, 200, 200));
        
        // Add components
        searchPanel.add(new JLabel("Recherche: "));
        searchPanel.add(searchField);
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        
        // Search action
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            String type = (String)searchTypeCombo.getSelectedItem();
            filterPlats(query, type);
        });
        
        // Clear action
        clearButton.addActionListener(e -> {
            searchField.setText("");
            filterPlats("", "Tous");
        });
        
        return searchPanel;
    }

    private void filterPlats(String query, String type) {
        List<Plat> filtered = new ArrayList<>();
        
        for (Plat plat : menu) {
            boolean match = false;
            
            switch(type) {
                case "Nom":
                    match = plat.getNom().toLowerCase().contains(query.toLowerCase());
                    break;
                case "Catégorie":
                    match = plat.getCategorie().toLowerCase().contains(query.toLowerCase());
                    break;
                case "Prix":
                    try {
                        double price = Double.parseDouble(query);
                        match = plat.getPrix() <= price;
                    } catch (NumberFormatException e) {
                        // Invalid price - no match
                    }
                    break;
                default: // "Tous"
                    match = true;
            }
            
            if (match) filtered.add(plat);
        }
        
        updatePlatsTable(filtered);
    }

    private void updatePlatsTable(List<Plat> plats) {
        tableModel.setRowCount(0);
        for (Plat plat : plats) {
            tableModel.addRow(new Object[]{
                plat.getNom(), 
                plat.getPrix(), 
                plat.getDescription(), 
                plat.getCategorie(), 
                plat.getImagePath()
            });
        }
    }

    private void refreshTable() {
        updatePlatsTable(menu);
    }

    private void showUserList() {
        List<String> users = DB.listUsers();
        if (users.isEmpty()) {
            showInformation("Aucun utilisateur trouvé dans la base de données.");
        } else {
            StringBuilder userList = new StringBuilder("Utilisateurs :\n");
            for (String user : users) {
                userList.append("- ").append(user).append("\n");
            }
            JOptionPane.showMessageDialog(this, userList.toString(), 
                    "Liste des Utilisateurs", JOptionPane.INFORMATION_MESSAGE);
        }
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
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInformation(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}