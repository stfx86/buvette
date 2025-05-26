    package Vue;


import Ai.FloatingChatIcon;


import Application.MainPrincipal;

//import Application.MainPrincipal;


import DB.DB;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


 
public class BuvetteApp1 extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel platsPanel;
    private List<Plat> menu;
    private Map<String, List<Plat>> menuData;
   
   
   private  Panier panierPanel = new Panier();  
   
    

    // Create a panier
//    Panier myPanier = new Panier();

    private LeftNavbar leftNavbar; // Added field

    public BuvetteApp1() {
        menu = new ArrayList<>();
        menuData = new LinkedHashMap<>();
        
        loadMenu();
        initializeMenuData();
        initializeUI();
        this.setMinimumSize(new Dimension(999,999));
        
        FloatingChatIcon chatIcon = new FloatingChatIcon(this);
      
    }

    private void initializeUI() {
        setTitle("Gestion de Buvettes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Création du conteneur principal
        JPanel mainContainer = new JPanel(new BorderLayout());

        // Initialisation du CardLayout pour le contenu dynamique
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Création des différents panels
        JPanel homePanel = createHomePanel();
        JPanel loginPanel = createLoginPanel();
        JPanel adminPanel = createAdminPanel();
        JPanel profilePanal = createProfilePanel();
        panierPanel = (Panier)createPanierPanel();

        // Ajout des panels au CardLayout
        cardPanel.add(homePanel, "Home");
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(adminPanel, "Admin");
        cardPanel.add(profilePanal, "Profile");        
        cardPanel.add(panierPanel, "Panier");

        // Création et configuration de la LeftNavbar
        leftNavbar = new LeftNavbar(cardLayout, cardPanel); // Initialize the field
        mainContainer.add(leftNavbar, BorderLayout.WEST);

        // Configuration des actions de navigation
        setupNavigationActions(leftNavbar);

        // Ajout du cardPanel au centre
        mainContainer.add(cardPanel, BorderLayout.CENTER);

        // Configuration du content pane
        setContentPane(mainContainer);

        // Masquer le bouton admin par défaut
        leftNavbar.setAdminVisibility(true);

        // Afficher le panel Home par défaut et activer son bouton
        cardLayout.show(cardPanel, "Home");
        leftNavbar.setActiveButton(leftNavbar.getHomeBtn());
    }
   private JPanel createPanierPanel(){
       
       return this.panierPanel;
   }

    private JPanel createProfilePanel(){
    return new Profile() ;
            }
    
    
    
    private void setupNavigationActions(LeftNavbar navbar) {
        // Home button
        navbar.setHomeAction(e -> {
            cardLayout.show(cardPanel, "Home");
            navbar.setActiveButton(navbar.getHomebtn());
        });

        // Admin button
        navbar.setAdminAction(e -> {
//            MainPrincipal.main(new String[0]);
//            cardLayout.show(cardPanel, "Admin");
            
            navbar.setActiveButton(navbar.getAdminBtn());
            
            
        });
        if(!DB.isAdmin(SignIn.user.getName(), SignIn.user.getPassword())){
             navbar.getAdminBtn().setEnabled(false);
        }
         

        // Panier button
          navbar.setPanierAction(e -> {
              System.out.println("buttton action ");
              
           
            cardLayout.show(cardPanel, "Panier");
                         

            navbar.setActiveButton(navbar.panierBtn);
        });
        

        // Logout button
        navbar.setLogoutAction(e -> {
    int confirmed = JOptionPane.showConfirmDialog(
        null,
        "Êtes-vous sûr de vouloir vous déconnecter ?",
        "Confirmation de déconnexion",
        JOptionPane.YES_NO_OPTION
    );

    if (confirmed == JOptionPane.YES_OPTION) {
        // Logique de déconnexion
        navbar.setAdminVisibility(false);
        new SignIn().setVisible(true);
        dispose();
        cardLayout.show(cardPanel, "Login");
        navbar.setActiveButton(null);
    }
});

    }

    public void onAdminLoginSuccess() {
        leftNavbar.setAdminVisibility(true);
        cardLayout.show(cardPanel, "Admin");
        leftNavbar.setActiveButton(leftNavbar.getAdminBtn());
    }

























    private void initializeMenuData() {
        if (menu.isEmpty()) {
            // Initialize default dishes and save to database
            List<Plat> pizzas = new ArrayList<>(Arrays.asList(
                    new Plat("Pizza Margherita", 30, "Classic pizza with tomato, mozzarella, and basil.", "Pizzas", "src/images/pizza1.jpeg"),
                    new Plat("Pizza Pepperoni", 40, "Spicy pepperoni with mozzarella and tomato sauce.", "Pizzas", "src/images/pizza2.jpeg")
            ));
            List<Plat> burgers = new ArrayList<>(Arrays.asList(
                    new Plat("Cheeseburger", 35, "Beef patty with cheddar, lettuce, and tomato.", "Burgers", "src/images/burger1.jpeg"),
                    new Plat("Bacon Burger", 38, "Beef patty with crispy bacon and BBQ sauce.", "Burgers", "src/images/burger2.jpeg")
            ));
            List<Plat> desserts = new ArrayList<>(Arrays.asList(
                    new Plat("Tiramisu", 25, "Coffee-flavored Italian dessert with mascarpone.", "Desserts", "src/images/dessert1.jpeg"),
                    new Plat("Crème Brûlée", 28, "Rich custard with a caramelized sugar crust.", "Desserts", "src/images/dessert2.jpeg")
            ));
            List<Plat> drinks = new ArrayList<>(Arrays.asList(
                    new Plat("CocaCola", 10, "Refreshing carbonated soft drink.", "Drinks", "src/images/drink1.jpeg"),
                    new Plat("Orange Juice", 12, "Freshly squeezed orange juice.", "Drinks", "src/images/drink2.jpeg")
            ));

            menuData.put("Pizzas", pizzas);
            menuData.put("Burgers", burgers);
            menuData.put("Desserts", desserts);
            menuData.put("Drinks", drinks);

            for (List<Plat> plats : menuData.values()) {
                for (Plat plat : plats) {
                    if (DB.addPlat(plat)) {
                        menu.add(plat);
                    } else {
                        System.err.println("Failed to add plat to database: " + plat.getNom());
                    }
                }
            }
        } else {
            for (Plat plat : menu) {
                menuData.computeIfAbsent(plat.getCategorie(), k -> new ArrayList<>()).add(plat);
            }
        }
    }

    private JPanel createHomePanel() {
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/py.png");
        backgroundPanel.setLayout(new BorderLayout());
          
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
/*
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
        
        */
        //JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel topPanel = new JPanel(new WrapLayout(FlowLayout.LEFT));
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200)); // or desired height// or RIGH  
        
        topPanel.setOpaque(false);
        //topPanel.add(adminButton);
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
                //  JPanel card = PlatCardCreator.createPlatCard(plat);
                  JPanel card = PlatCardCreator.createPlatCard(plat, new ActionListener() {
                   @Override
                    public void actionPerformed(ActionEvent e) {
                        
                       // Empty action listener - no code needed here
                       System.out.println(".platt added");
                       

                      panierPanel.addPlat(plat);

                            
                      }
                    });

                platsPanel.add(card);
            }
        }

        platsPanel.revalidate();
        platsPanel.repaint();
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
            String password = new String(passwordField.getPassword()).trim();
            if (DB.verifyAdmin("admin", password)) {
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

    private void loadMenu() {
        try {
            menu.addAll(DB.listPlats());
        } catch (Exception e) {
            System.err.println("Error loading plats from database:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des plats depuis la base de données !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Test database connection before starting
        if (!DB.testConnection()) {
            JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de données !", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        SwingUtilities.invokeLater(() -> {
           BuvetteApp1  buvetteApp =  new BuvetteApp1()  ;
           buvetteApp.setMinimumSize(new Dimension(800,600));
           buvetteApp.setVisible(true);
            
        
        
        
        });
    }
}