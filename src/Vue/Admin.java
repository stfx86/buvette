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
import javax.swing.table.DefaultTableCellRenderer;

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
    private JTextField nomField, prixField, descField, catField;
    private JLabel imgPathLabel;

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
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Main panel with background
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/py.png");
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Create a content panel with proper padding
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        // Header panel with title and buttons
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Main content area
        JPanel mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setOpaque(false);

        // Form panel
        JPanel formPanel = createFormPanel();
        mainContent.add(formPanel, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = createTablePanel();
        mainContent.add(tablePanel, BorderLayout.CENTER);

        // Action buttons panel
        JPanel actionPanel = createActionPanel();
        mainContent.add(actionPanel, BorderLayout.SOUTH);

        contentPanel.add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Title
        JLabel titleLabel = new JLabel("Administration des Plats");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton usersButton = new JButton("Liste des Utilisateurs");
        styleButton(usersButton, new Color(70, 130, 180));
        usersButton.addActionListener(e -> showUserList());

        JButton backButton = new JButton("Retour");
        styleButton(backButton, new Color(169, 169, 169));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Home"));

        buttonPanel.add(usersButton);
        buttonPanel.add(backButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Ajouter/Modifier un Plat",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 16),
                Color.WHITE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name field
        JLabel nomLabel = new JLabel("Nom du plat:");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nomLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(nomLabel, gbc);

        nomField = new JTextField(20);
        nomField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomField.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(nomField, gbc);

        // Price field
        JLabel prixLabel = new JLabel("Prix (DH):");
        prixLabel.setFont(new Font("Arial", Font.BOLD, 14));
        prixLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(prixLabel, gbc);

        prixField = new JTextField(20);
        prixField.setFont(new Font("Arial", Font.PLAIN, 14));
        prixField.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(prixField, gbc);

        // Description field
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(descLabel, gbc);

        descField = new JTextField(20);
        descField.setFont(new Font("Arial", Font.PLAIN, 14));
        descField.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(descField, gbc);

        // Category field
        JLabel catLabel = new JLabel("Catégorie:");
        catLabel.setFont(new Font("Arial", Font.BOLD, 14));
        catLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(catLabel, gbc);

        catField = new JTextField(20);
        catField.setFont(new Font("Arial", Font.PLAIN, 14));
        catField.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(catField, gbc);

        // Image field
        JLabel imgLabel = new JLabel("Image:");
        imgLabel.setFont(new Font("Arial", Font.BOLD, 14));
        imgLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(imgLabel, gbc);

        JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        imgPanel.setOpaque(false);

        JButton chooseImageButton = new JButton("Fichier");
        styleButton(chooseImageButton, new Color(34, 139, 34));
        
        JButton chooseImageUrlButton = new JButton("URL");
        styleButton(chooseImageUrlButton, new Color(65, 105, 225));
        
        imgPathLabel = new JLabel("Aucune image sélectionnée");
        imgPathLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        imgPathLabel.setForeground(Color.WHITE);
        
        imgPanel.add(chooseImageButton);
        imgPanel.add(chooseImageUrlButton);
        imgPanel.add(imgPathLabel);
        
        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(imgPanel, gbc);

        // File chooser setup
        JFileChooser fileChooser = new JFileChooser("src/images/");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Images (jpg, jpeg, png)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(imageFilter);
        
        chooseImageButton.addActionListener(e -> handleFileImageSelection(fileChooser));
        chooseImageUrlButton.addActionListener(e -> handleUrlImageSelection());

        // Form buttons
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        formButtonPanel.setOpaque(false);
        
        JButton addButton = new JButton("Ajouter");
        styleButton(addButton, new Color(34, 139, 34));
        addButton.addActionListener(e -> addPlat());
        
        JButton clearButton = new JButton("Effacer");
        styleButton(clearButton, new Color(169, 169, 169));
        clearButton.addActionListener(e -> clearForm());
        
        formButtonPanel.add(addButton);
        formButtonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(formButtonPanel, gbc);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Liste des Plats",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 16),
                Color.WHITE));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setOpaque(false);
        searchPanel.setBackground(new Color(0, 0, 0, 150));
        
        JLabel searchLabel = new JLabel("Recherche:");
        searchLabel.setForeground(Color.WHITE);
        searchPanel.add(searchLabel);
        
        JTextField searchField = new JTextField(25);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(new Color(240, 240, 240));
        searchPanel.add(searchField);
        
        String[] searchTypes = {"Tous", "Nom", "Catégorie", "Prix"};
        JComboBox<String> searchTypeCombo = new JComboBox<>(searchTypes);
        searchPanel.add(searchTypeCombo);
        
        JButton searchButton = new JButton("Rechercher");
        styleButton(searchButton, new Color(70, 130, 180));
        searchPanel.add(searchButton);
        
        JButton clearButton = new JButton("Réinitialiser");
        styleButton(clearButton, new Color(169, 169, 169));
        searchPanel.add(clearButton);
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Nom", "Prix (DH)", "Description", "Catégorie", "Image"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        adminMenuTable = new JTable(tableModel);
        adminMenuTable.setFont(new Font("Arial", Font.PLAIN, 14));
        adminMenuTable.setRowHeight(30);
        adminMenuTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        adminMenuTable.getTableHeader().setForeground(Color.WHITE);
        adminMenuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        adminMenuTable.setIntercellSpacing(new Dimension(0, 5));
        adminMenuTable.setShowGrid(false);
        
        // Custom renderer
        adminMenuTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, 
                        hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(new Color(70, 130, 180));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(240, 248, 255, 200) : new Color(255, 255, 255, 200));
                    c.setForeground(Color.BLACK);
                }
                
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

        // Populate table
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(adminMenuTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(240, 240, 240, 200));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            String type = (String) searchTypeCombo.getSelectedItem();
            filterTable(query, type);
        });
        
        clearButton.addActionListener(e -> {
            searchField.setText("");
            refreshTable();
        });
        
        // Double-click to populate form
        adminMenuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = adminMenuTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        populateFormFromTable(selectedRow);
                    }
                }
            }
        });

        return tablePanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton modifyButton = new JButton("Modifier");
        styleButton(modifyButton, new Color(255, 140, 0));
        modifyButton.addActionListener(e -> modifyPlat());

        JButton deleteButton = new JButton("Supprimer");
        styleButton(deleteButton, new Color(220, 20, 60));
        deleteButton.addActionListener(e -> deletePlat());

        actionPanel.add(modifyButton);
        actionPanel.add(deleteButton);

        return actionPanel;
    }

    private void handleFileImageSelection(JFileChooser fileChooser) {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            imgPathLabel.setText(selectedFile.getName());
            imgPathLabel.setToolTipText(selectedImagePath);
        }
    }

    private void handleUrlImageSelection() {
        String imageUrl = JOptionPane.showInputDialog(this, 
                "Entrez l'URL de l'image:", 
                "Importer depuis URL", 
                JOptionPane.PLAIN_MESSAGE);
        
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            try {
                URL url = new URL(imageUrl);
                BufferedImage image = ImageIO.read(url);
                if (image != null) {
                    File tempFile = File.createTempFile("menu_img_", ".png");
                    ImageIO.write(image, "png", tempFile);
                    selectedImagePath = tempFile.getAbsolutePath();
                    imgPathLabel.setText(tempFile.getName());
                    imgPathLabel.setToolTipText(selectedImagePath);
                } else {
                    showError("Impossible de charger l'image depuis l'URL");
                }
            } catch (IOException ex) {
                showError("URL invalide ou erreur de téléchargement");
            }
        }
    }

    private void addPlat() {
        try {
            String nom = nomField.getText().trim();
            double prix = Double.parseDouble(prixField.getText().trim());
            String desc = descField.getText().trim();
            String cat = catField.getText().trim();
            String img = selectedImagePath.trim();
            
            if (validateDishInput(nom, prix, cat, img)) {
                Plat plat = new Plat(nom, prix, desc, cat, img);
                if (DB.addPlat(plat)) {
                    menu.add(plat);
                    menuData.computeIfAbsent(cat, k -> new ArrayList<>()).add(plat);
                    refreshTable();
                    clearForm();
                    updateSuggestions(nom, cat);
                    showSuccess("Plat ajouté avec succès!");
                } else {
                    showError("Erreur lors de l'ajout à la base de données");
                }
            }
        } catch (NumberFormatException ex) {
            showError("Veuillez entrer un prix valide");
        }
    }

    private void modifyPlat() {
        int selectedRow = adminMenuTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String nom = nomField.getText().trim();
                double prix = Double.parseDouble(prixField.getText().trim());
                String desc = descField.getText().trim();
                String cat = catField.getText().trim();
                String img = selectedImagePath.trim();
                
                if (validateDishInput(nom, prix, cat, img)) {
                    Plat selectedPlat = menu.get(selectedRow);
                    String oldCat = selectedPlat.getCategorie();
                    Plat newPlat = new Plat(nom, prix, desc, cat, img);
                    
                    if (DB.updatePlat(selectedPlat, newPlat)) {
                        // Update local data
                        menuData.get(oldCat).remove(selectedPlat);
                        selectedPlat.setNom(nom);
                        selectedPlat.setPrix(prix);
                        selectedPlat.setDescription(desc);
                        selectedPlat.setCategorie(cat);
                        selectedPlat.setImagePath(img);
                        menuData.computeIfAbsent(cat, k -> new ArrayList<>()).add(selectedPlat);
                        
                        refreshTable();
                        clearForm();
                        updateSuggestions(nom, cat);
                        showSuccess("Plat modifié avec succès!");
                    } else {
                        showError("Erreur lors de la mise à jour dans la base de données");
                    }
                }
            } catch (NumberFormatException ex) {
                showError("Veuillez entrer un prix valide");
            }
        } else {
            showError("Veuillez sélectionner un plat à modifier");
        }
    }

    private void deletePlat() {
        int selectedRow = adminMenuTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Êtes-vous sûr de vouloir supprimer ce plat?", 
                    "Confirmation", 
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                Plat plat = menu.get(selectedRow);
                if (DB.deletePlat(plat.getNom())) {
                    // Update local data
                    menu.remove(selectedRow);
                    menuData.get(plat.getCategorie()).remove(plat);
                    
                    if (menuData.get(plat.getCategorie()).isEmpty()) {
                        menuData.remove(plat.getCategorie());
                        categorySuggestions.remove(plat.getCategorie());
                    }
                    
                    nameSuggestions.remove(plat.getNom());
                    refreshTable();
                    showSuccess("Plat supprimé avec succès!");
                } else {
                    showError("Erreur lors de la suppression de la base de données");
                }
            }
        } else {
            showError("Veuillez sélectionner un plat à supprimer");
        }
    }

    private boolean validateDishInput(String nom, double prix, String cat, String img) {
        if (nom.isEmpty()) {
            showError("Veuillez entrer un nom pour le plat");
            return false;
        }
        if (prix < 0) {
            showError("Le prix ne peut pas être négatif");
            return false;
        }
        if (cat.isEmpty()) {
            showError("Veuillez entrer une catégorie");
            return false;
        }
        if (img.isEmpty()) {
            showError("Veuillez sélectionner une image");
            return false;
        }
        return true;
    }

    private void filterTable(String query, String type) {
        List<Plat> filtered = new ArrayList<>();
        
        for (Plat plat : menu) {
            boolean match = false;
            
            switch (type) {
                case "Nom":
                    match = plat.getNom().toLowerCase().contains(query.toLowerCase());
                    break;
                case "Catégorie":
                    match = plat.getCategorie().toLowerCase().contains(query.toLowerCase());
                    break;
                case "Prix":
                    try {
                        double price = Double.parseDouble(query);
                        match = plat.getPrix() == price;
                    } catch (NumberFormatException e) {
                        // Invalid price - no match
                    }
                    break;
                default: // "Tous"
                    match = plat.getNom().toLowerCase().contains(query.toLowerCase()) ||
                            plat.getCategorie().toLowerCase().contains(query.toLowerCase()) ||
                            String.valueOf(plat.getPrix()).contains(query);
            }
            
            if (match) {
                filtered.add(plat);
            }
        }
        
        updateTableData(filtered);
    }

    private void refreshTable() {
        updateTableData(menu);
    }

    private void updateTableData(List<Plat> plats) {
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

    private void populateFormFromTable(int row) {
        nomField.setText((String) tableModel.getValueAt(row, 0));
        prixField.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        descField.setText((String) tableModel.getValueAt(row, 2));
        catField.setText((String) tableModel.getValueAt(row, 3));
        selectedImagePath = (String) tableModel.getValueAt(row, 4);
        imgPathLabel.setText(new File(selectedImagePath).getName());
        imgPathLabel.setToolTipText(selectedImagePath);
    }

    private void clearForm() {
        nomField.setText("");
        prixField.setText("");
        descField.setText("");
        catField.setText("");
        selectedImagePath = "";
        imgPathLabel.setText("Aucune image sélectionnée");
        imgPathLabel.setToolTipText(null);
    }

    private void updateSuggestions(String nom, String cat) {
        if (!nameSuggestions.contains(nom)) {
            nameSuggestions.add(nom);
        }
        if (!categorySuggestions.contains(cat)) {
            categorySuggestions.add(cat);
        }
    }

    private void showUserList() {
        List<String> users = DB.listUsers();
        if (users.isEmpty()) {
            showInformation("Aucun utilisateur trouvé");
        } else {
            StringBuilder sb = new StringBuilder("<html><b>Utilisateurs enregistrés:</b><ul>");
            for (String user : users) {
                sb.append("<li>").append(user).append("</li>");
            }
            sb.append("</ul></html>");
            
            JOptionPane.showMessageDialog(this, sb.toString(), 
                    "Liste des Utilisateurs", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
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

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}