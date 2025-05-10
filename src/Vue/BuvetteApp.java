package Vue;

import javax.swing.*;
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
    private JPanel platsPanel;
    private List<Plat> menu;
    private Map<String, List<Plat>> menuData;
    private final String DATA_FILE = "menu.txt";
    private final String ADMIN_PASSWORD = "admin123";

    public BuvetteApp() {
        menu = new ArrayList<>();
        menuData = new LinkedHashMap<>();
        loadMenu();
        initializeMenuData();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestion de Buvettes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel homePanel = createHomePanel();
        JPanel loginPanel = createLoginPanel();
        JPanel adminPanel = createAdminPanel();

        cardPanel.add(homePanel, "Home");
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(adminPanel, "Admin");

        setContentPane(cardPanel);
        cardLayout.show(cardPanel, "Home");
    }

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

            for (List<Plat> plats : menuData.values()) {
                menu.addAll(plats);
            }
            saveMenu();
        } else {
            for (Plat plat : menu) {
                menuData.computeIfAbsent(plat.categorie, k -> new ArrayList<>()).add(plat);
            }
        }
    }

    private JPanel createHomePanel() {
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/background.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JButton adminButton = new JButton("Espace Administrateur");
        adminButton.setPreferredSize(new Dimension(200, 50));
        adminButton.setBackground(new Color(0, 153, 255));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.addActionListener(e -> {
            System.out.println("Admin button clicked, switching to Login panel");
            cardLayout.show(cardPanel, "Login");
        });
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

        JPanel categoriesPanel = createCategoryButtons();
        headerPanel.add(categoriesPanel);

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        platsPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        platsPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(platsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        displayPlats("Pizzas");

        return backgroundPanel;
    }

    private JPanel createCategoryButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setOpaque(false);

        for (String category : menuData.keySet()) {
            JButton button = new JButton(category);
            button.setPreferredSize(new Dimension(150, 40));
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));

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

            button.addActionListener(e -> displayPlats(category));
            panel.add(button);
        }

        return panel;
    }

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

    private JPanel createPlatCard(Plat plat) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

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

        JLabel nameLabel = new JLabel(plat.nom);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);

        JLabel priceLabel = new JLabel(plat.prix + " DH");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(0, 153, 76));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(priceLabel);

        JTextArea descLabel = new JTextArea(plat.description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setLineWrap(true);
        descLabel.setWrapStyleWord(true);
        descLabel.setEditable(false);
        descLabel.setOpaque(false);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setMaximumSize(new Dimension(230, 60));
        card.add(descLabel);

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

    private JPanel createAdminPanel() {
        return new Admin(menu, menuData, cardLayout, cardPanel);
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
            // File not found or empty
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BuvetteApp().setVisible(true));
    }
}