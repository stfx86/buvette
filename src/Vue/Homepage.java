package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
        
        

/**
 * Homepage for displaying a restaurant menu with prices and descriptions.
 */
public class Homepage extends JFrame {

    private BackgroundPanel backgroundPanel;
    private JPanel contentPanel;
    private JPanel platsPanel;
    private Map<String, List<Plat>> menuData;

    public Homepage() {
        // Initialize background
        backgroundPanel = new BackgroundPanel("src/images/background.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // Load menu data
        initializeMenuData();

        // Create content panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        // Create category buttons
        JPanel categoriesPanel = createCategoryButtons();
        contentPanel.add(categoriesPanel, BorderLayout.NORTH);

        // Create dishes panel
        platsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        platsPanel.setOpaque(false);

        // Add scroll pane for dishes
        JScrollPane scrollPane = new JScrollPane(platsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        // Set up window
        setContentPane(backgroundPanel);
        setTitle("Restaurant Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Display dishes for the default category
        displayPlats("Pizzas");
    }

    /**
     * Initializes the menu data with sample dishes.
     */
    private void initializeMenuData() {
        menuData = new LinkedHashMap<>();

        menuData.put("Pizzas", Arrays.asList(
            new Plat("Pizza Margherita", "30 DH", "Classic pizza with tomato, mozzarella, and basil.", "src/images/pizza1.jpeg"),
            new Plat("Pizza Pepperoni", "40 DH", "Spicy pepperoni with mozzarella and tomato sauce.", "src/images/pizza2.jpeg")
        ));

        menuData.put("Burgers", Arrays.asList(
            new Plat("Cheeseburger", "35 DH", "Beef patty with cheddar, lettuce, and tomato.", "src/images/burger1.jpeg"),
            new Plat("Bacon Burger", "38 DH", "Beef patty with crispy bacon and BBQ sauce.", "src/images/burger2.jpeg")
        ));

        menuData.put("Desserts", Arrays.asList(
            new Plat("Tiramisu", "25 DH", "Coffee-flavored Italian dessert with mascarpone.", "src/images/dessert1.jpeg"),
            new Plat("Crème Brûlée", "28 DH", "Rich custard with a caramelized sugar crust.", "src/images/dessert2.jpeg")
        ));

        menuData.put("Drinks", Arrays.asList(
            new Plat("Coca-Cola", "10 DH", "Refreshing carbonated soft drink.", "src/images/drink1.jpeg"),
            new Plat("Orange Juice", "12 DH", "Freshly squeezed orange juice.", "src/images/drink2.jpeg")
        ));
    }

    /**
     * Creates the category buttons panel.
     * @return JPanel containing category buttons
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

            // Click action to display dishes
            button.addActionListener(e -> displayPlats(category));
            panel.add(button);
        }

        return panel;
    }

    /**
     * Displays dishes for the selected category.
     * @param category The category name
     */
    private void displayPlats(String category) {
        platsPanel.removeAll();
        List<Plat> plats = menuData.getOrDefault(category, Collections.emptyList());

        if (plats.isEmpty()) {
            platsPanel.add(new JLabel("No dishes available for this category"));
        } else {
            for (Plat plat : plats) {
                platsPanel.add(createPlatCard(plat));
            }
        }

        platsPanel.revalidate();
        platsPanel.repaint();
    }

    /**
     * Creates a card for a dish.
     * @param plat The dish
     * @return JPanel representing the dish card
     */
    private JPanel createPlatCard(Plat plat) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Image
        try {
            ImageIcon icon = new ImageIcon(plat.getImagePath());
            Image img = icon.getImage().getScaledInstance(230, 140, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(imageLabel);
        } catch (Exception e) {
            JLabel placeholder = new JLabel("Image not available");
            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(placeholder);
        }

        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Name
        JLabel nameLabel = new JLabel(plat.getNom());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);

        // Price
        JLabel priceLabel = new JLabel(plat.getPrix());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(0, 153, 76));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(priceLabel);

        // Description
        JTextArea descLabel = new JTextArea(plat.getDescription());
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
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Homepage().setVisible(true));
    }
}

/**
 * Class representing a dish with name, price, description, and image path.
 */


/**
 * Panel for displaying a background image.
 */
class BackgroundPanel extends JPanel {
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