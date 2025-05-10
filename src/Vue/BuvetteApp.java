package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Main application class for the Buvette Management System.
 */
public class BuvetteApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel platsPanel; // Instance variable for platsPanel
    private List<Plat> menu;
    private Map<String, List<Plat>> menuData;
    private final String DATA_FILE = "menu.txt";
    private final String ADMIN_PASSWORD = "admin123";

    // Classe pour représenter un plat
    public static class Plat {
        String nom;
        double prix;
        String description;
        String categorie;
        String imagePath;

        Plat(String nom, double prix, String description, String categorie, String imagePath) {
            this.nom = nom;
            this.prix = prix;
            this.description = description;
            this.categorie = categorie;
            this.imagePath = imagePath;
        }

        @Override
        public String toString() {
            return nom + " - " + prix + "€ (" + description + ")";
        }
    }

    // Custom JTextField with autocomplete suggestions
    public static class AutocompleteTextField extends JTextField {
        private JPopupMenu popupMenu;
        private List<String> suggestions;

        public AutocompleteTextField(int columns, List<String> suggestions) {
            super(columns);
            this.suggestions = suggestions;
            popupMenu = new JPopupMenu();
            setupAutocomplete();
        }

        private void setupAutocomplete() {
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    showSuggestions();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    popupMenu.setVisible(false);
                }
            });

            getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    showSuggestions();
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    showSuggestions();
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    showSuggestions();
                }
            });
        }

        private void showSuggestions() {
            popupMenu.removeAll();
            String input = getText().trim().toLowerCase();
            List<String> filteredSuggestions = new ArrayList<>();

            for (String suggestion : suggestions) {
                if (input.isEmpty() || suggestion.toLowerCase().contains(input)) {
                    filteredSuggestions.add(suggestion);
                }
            }

            for (String suggestion : filteredSuggestions) {
                JMenuItem item = new JMenuItem(suggestion);
                item.addActionListener(e -> {
                    setText(suggestion);
                    popupMenu.setVisible(false);
                });
                popupMenu.add(item);
            }

            if (!filteredSuggestions.isEmpty()) {
                popupMenu.show(this, 0, getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        }

        public void setSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }
    }

    public BuvetteApp() {
        menu = new ArrayList<>();
        menuData = new LinkedHashMap<>();
        loadMenu(); // Charger le menu depuis le fichier
        initializeMenuData(); // Initialiser les catégories
        initializeUI();
    }

    /**
     * Initialize the UI with CardLayout for navigation.
     */
    private void initializeUI() {
        setTitle("Gestion de Buvettes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window to ensure visibility

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Vue d'accueil (client)
        JPanel homePanel = createHomePanel();
        // Vue de connexion admin
        JPanel loginPanel = createLoginPanel();
        // Vue admin
        JPanel adminPanel = createAdminPanel();

        cardPanel.add(homePanel, "Home");
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(adminPanel, "Admin");

        setContentPane(cardPanel);
        cardLayout.show(cardPanel, "Home");
    }

    /**
     * Initialize menu data with sample dishes if file is empty.
     */
    private void initializeMenuData() {
        if (menu.isEmpty()) {
            menuData.put("Pizzas", new ArrayList<>(Arrays.asList(
                    new Plat("Pizza Margherita", 30, "Classic pizza with tomato, mozzarella, and basil.", "Pizzas", "src/images/pizza1.jpeg"),
                    new Plat("Pizza Pepperoni", 40, "Spicy pepperoni with mozzarella and tomato sauce.", "Pizzas", "src/images/pizza2.jpeg")
            )));

            menuData.put("Burgers", new ArrayList<>(Arrays.asList(
                    new Plat("Cheeseburger", 35, "Beef patty with cheddar, lettuce, and tomato.", "Burgers", "src/images/burger1.jpeg"),
                    new Plat("Bacon Burger", 38, "Beef patty with crispy bacon and BBQ sauce.", "Burgers", "src/images/burger2.jpeg")
            )));

            menuData.put("Desserts", new ArrayList<>(Arrays.asList(
                    new Plat("Tiramisu", 25, "Coffee-flavored Italian dessert with mascarpone.", "Desserts", "src/images/dessert1.jpeg"),
                    new Plat("Crème Brûlée", 28, "Rich custard with a caramelized sugar crust.", "Desserts", "src/images/dessert2.jpeg")
            )));

            menuData.put("Drinks", new ArrayList<>(Arrays.asList(
                    new Plat("CocaCola", 10, "Refreshing carbonated soft drink.", "Drinks", "src/images/drink1.jpeg"),
                    new Plat("Orange Juice", 12, "Freshly squeezed orange juice.", "Drinks", "src/images/drink2.jpeg")
            )));

            // Populate the menu list
            for (List<Plat> plats : menuData.values()) {
                menu.addAll(plats);
            }
            saveMenu();
        } else {
            // Rebuild menuData from loaded menu
            for (Plat plat : menu) {
                menuData.computeIfAbsent(plat.categorie, k -> new ArrayList<>()).add(plat);
            }
        }
    }

    /**
     * Create the home panel (client view).
     */
    private JPanel createHomePanel() {
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/background.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        // Create a header panel to hold admin button and categories
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        // Admin button
        JButton adminButton = new JButton("Espace Administrateur");
        adminButton.setPreferredSize(new Dimension(200, 50));
        adminButton.setBackground(new Color(0, 153, 255));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.addActionListener(e -> {
            System.out.println("Admin button clicked, switching to Login panel");
            cardLayout.show(cardPanel, "Login");
        });
        // Hover effect for admin button
        adminButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                adminButton.setBackground(new Color(0, 120, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                adminButton.setBackground(new Color(0, 153, 255));
            }
        });
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(adminButton);
        headerPanel.add(topPanel);

        // Category buttons
        JPanel categoriesPanel = createCategoryButtons();
        headerPanel.add(categoriesPanel);

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Dishes panel
        platsPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        platsPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(platsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        // Display default category
        displayPlats("Pizzas");

        return backgroundPanel;
    }

    /**
     * Create category buttons panel.
     */
    private JPanel createCategoryButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setOpaque(false);

        for (String category : menuData.keySet()) {
            JButton button = new JButton(category);
            button.setPreferredSize(new Dimension(150, 40));
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));

            // Hover effect
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(0, 153, 255));
                    button.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }
            });

            // Click action
            button.addActionListener(e -> displayPlats(category));
            panel.add(button);
        }

        return panel;
    }

    /**
     * Display dishes for the selected category.
     */
    private void displayPlats(String category) {
        platsPanel.removeAll();
        List<Plat> plats = menuData.getOrDefault(category, Collections.emptyList());

        if (plats.isEmpty()) {
            platsPanel.add(new JLabel("Aucun plat disponible pour cette catégorie"));
        } else {
            for (Plat plat : plats) {
                platsPanel.add(createPlatCard(plat));
            }
        }

        platsPanel.revalidate();
        platsPanel.repaint();
    }

    /**
     * Create a card for a dish.
     */
    private JPanel createPlatCard(Plat plat) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Image
        try {
            ImageIcon icon = new ImageIcon(plat.imagePath);
            Image img = icon.getImage().getScaledInstance(230, 140, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(imageLabel);
        } catch (Exception e) {
            JLabel placeholder = new JLabel("Image non disponible");
            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(placeholder);
        }

        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Name
        JLabel nameLabel = new JLabel(plat.nom);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);

        // Price
        JLabel priceLabel = new JLabel(plat.prix + " DH");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(0, 153, 76));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(priceLabel);

        // Description
        JTextArea descLabel = new JTextArea(plat.description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setLineWrap(true);
        descLabel.setWrapStyleWord(true);
        descLabel.setEditable(false);
        descLabel.setOpaque(false);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setMaximumSize(new Dimension(230, 60));
        card.add(descLabel);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 255), 2));
                card.setBackground(new Color(230, 240, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                card.setBackground(Color.WHITE);
            }
        });

        return card;
    }

    /**
     * Create login panel for admin.
     */
    private JPanel createLoginPanel() {
        BackgroundPanel loginPanel = new BackgroundPanel("src/images/background.jpg");
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Connexion");
        loginButton.setBackground(new Color(0, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(0, 120, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(0, 153, 255));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(loginButton, gbc);

        JButton backButtonLogin = new JButton("Retour");
        backButtonLogin.setBackground(new Color(200, 200, 200));
        backButtonLogin.setFont(new Font("Arial", Font.BOLD, 14));
        backButtonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButtonLogin.setBackground(new Color(150, 150, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButtonLogin.setBackground(new Color(200, 200, 200));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(backButtonLogin, gbc);

        loginButton.addActionListener(e -> {
            if (passwordField.getText().equals(ADMIN_PASSWORD)) {
                cardLayout.show(cardPanel, "Admin");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButtonLogin.addActionListener(e -> cardLayout.show(cardPanel, "Home"));
        return loginPanel;
    }

    /**
     * Create admin panel for managing dishes.
     */
    private JPanel createAdminPanel() {
        BackgroundPanel adminPanel = new BackgroundPanel("src/images/background.jpg");
        adminPanel.setLayout(new BorderLayout(10, 10));

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
        List<String> nameSuggestions = new ArrayList<>();
        for (Plat plat : menu) {
            nameSuggestions.add(plat.nom);
        }
        List<String> categorySuggestions = new ArrayList<>(menuData.keySet());
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

        adminPanel.add(formPanel, BorderLayout.NORTH);

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
        adminPanel.add(tableScrollPane, BorderLayout.CENTER);

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

        adminPanel.add(adminButtons, BorderLayout.SOUTH);

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

        return adminPanel;
    }

    /**
     * Style a button with consistent design.
     */
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

    /**
     * Clear form fields.
     */
    private void clearForm(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    /**
     * Save menu to file.
     */
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

    /**
     * Load menu from file.
     */
    private void loadMenu() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    menu.add(new Plat(parts[0], Double.parseDouble(parts[1]), parts[2], parts[3], parts[4]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Fichier non trouvé ou vide
        }
    }

    /**
     * Panel for displaying a background image.
     */
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
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

    /**
     * A FlowLayout that wraps components to the next line.
     */
    static class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) {
                    targetWidth = Integer.MAX_VALUE;
                }
                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
                int maxWidth = targetWidth - horizontalInsetsAndGap;

                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;

                for (Component m : target.getComponents()) {
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        if (rowWidth + d.width > maxWidth) {
                            dim.width = Math.max(dim.width, rowWidth);
                            dim.height += rowHeight + vgap;
                            rowWidth = 0;
                            rowHeight = 0;
                        }
                        rowWidth += d.width + hgap;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }

                dim.width = Math.max(dim.width, rowWidth);
                dim.height += rowHeight + vgap;

                dim.width += horizontalInsetsAndGap;
                dim.height += insets.top + insets.bottom + vgap * 2;

                return dim;
            }
        } 
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BuvetteApp().setVisible(true));
    }
}