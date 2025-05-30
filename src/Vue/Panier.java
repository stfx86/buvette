package Vue;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Panier extends JPanel {
    // Color scheme

      private static final Color PRIMARY_COLOR = new Color(40, 167, 69);
    private static final Color SECONDARY_COLOR = new Color(33, 37, 41);
    private static final Color CARD_COLOR = new Color(255, 255, 255, 200);
    private static final Color TEXT_COLOR = new Color(73, 80, 87);

    // Data structures
    private final List<Plat> plats = new ArrayList<>();
    private final Map<Plat, Integer> platQuantities = new HashMap<>();
    private double totalPrice = 0.0;

    // UI Components
    private JPanel listPanel;
    private JScrollPane listScrollPane;
    private JLabel totalPriceLabel;
    private JPanel detailPanel;
    private JLabel imageLabel;
    private JLabel nameLabel, priceLabel, descLabel, categoryLabel;
    private Plat currentlyDisplayedPlat;

    public Panier() {
        // Set up main layout
        setLayout(new BorderLayout());
        setOpaque(false);
        
        // Create background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/py.png");
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        
        // Initialize UI components
        initializeUIComponents();
        
        // Create content panel with all components
        JPanel contentPanel = createContentPanel();
        contentPanel.setOpaque(false);
        
        // Add content to background panel
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add background panel to main panel
        add(backgroundPanel, BorderLayout.CENTER);
    }

    private void initializeUIComponents() {
        // Initialize all your components here
        totalPriceLabel = new JLabel("Total: 0.00€", SwingConstants.RIGHT);
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalPriceLabel.setForeground(Color.WHITE);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setOpaque(false);
        listScrollPane.getViewport().setOpaque(false);
        listScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150)),
                "Your Cart",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                Color.WHITE
        ));

        // Initialize detail panel components
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel = createDetailLabel("", Font.BOLD, 18);
        priceLabel = createDetailLabel("", Font.PLAIN, 16);
        priceLabel.setForeground(PRIMARY_COLOR);
        categoryLabel = createDetailLabel("", Font.ITALIC, 14);
        descLabel = createDetailLabel("", Font.PLAIN, 14);
        descLabel.setVerticalAlignment(SwingConstants.TOP);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        headerPanel.add(totalPriceLabel, BorderLayout.EAST);
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Main Content (Split Pane)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(0.5);
        splitPane.setOpaque(false);
        splitPane.setBorder(null);

        // Left Panel - Cart Items
        listScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        splitPane.setLeftComponent(listScrollPane);

        // Right Panel - Item Details
        detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBackground(new Color(255, 255, 255, 200));
        detailPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 180)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        detailPanel.add(imageLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(nameLabel, gbc);

        gbc.gridy = 2;
        detailPanel.add(priceLabel, gbc);

        gbc.gridy = 3;
        detailPanel.add(categoryLabel, gbc);

        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        detailPanel.add(new JScrollPane(descLabel), gbc);

        splitPane.setRightComponent(detailPanel);
        contentPanel.add(splitPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton removeButton = createActionButton("Remove Item", new Color(220, 53, 69, 200));
        removeButton.addActionListener(e -> {
            if (!plats.isEmpty()) {
                removePlat(plats.get(plats.size() - 1));
            }
        });

        JButton checkoutButton = createActionButton("Checkout", new Color(40, 167, 69, 200));
        checkoutButton.addActionListener(e -> generateFacture());

        JButton clearButton = createActionButton("Clear Cart", new Color(33, 37, 41, 200));
        clearButton.addActionListener(e -> {
            plats.clear();
            platQuantities.clear();
            totalPrice = 0.0;
            refresh();
        });

        footerPanel.add(removeButton);
        footerPanel.add(clearButton);
        footerPanel.add(checkoutButton);
        contentPanel.add(footerPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

 
    
    
    
    
    
    
    
    
    
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150)),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(
                        bgColor.getRed(),
                        bgColor.getGreen(),
                        bgColor.getBlue(),
                        230 // More opaque on hover
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JLabel createDetailLabel(String text, int style, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(SECONDARY_COLOR);
        return label;
    }

    private void setupEventHandlers() {
        // Additional event handlers can be added here
    }

    public void addPlat(Plat plat) {
        plats.add(plat);
        platQuantities.put(plat, platQuantities.getOrDefault(plat, 0) + 1);
        totalPrice += plat.getPrix();
        refresh();
    }

    public boolean removePlat(Plat plat) {
        if (!platQuantities.containsKey(plat)) {
            return false;
        }

        int quantity = platQuantities.get(plat);
        if (quantity > 1) {
            platQuantities.put(plat, quantity - 1);
        } else {
            platQuantities.remove(plat);
            plats.remove(plat);
            if (currentlyDisplayedPlat == plat) {
                currentlyDisplayedPlat = null;
            }
        }
        totalPrice -= plat.getPrix();
        refresh();
        return true;
    }

    private void refresh() {
        refreshList();
        updateTotalPrice();
        updateDetailsView();
    }

    
    
    
//    private void refreshList() {
//        listPanel.removeAll();
//
//        if (plats.isEmpty()) {
//            JLabel emptyLabel = new JLabel("Your cart is empty");
//            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
//            emptyLabel.setForeground(TEXT_COLOR);
//            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//            emptyLabel.setBorder(new EmptyBorder(50, 0, 50, 0));
//            listPanel.add(emptyLabel);
//        } else {
//            Set<Plat> uniquePlats = new HashSet<>(plats);
//            for (Plat plat : uniquePlats) {
//                int quantity = platQuantities.get(plat);
//                listPanel.add(createCartItemPanel(plat, quantity));
//            }
//        }
//
//        listPanel.revalidate();
//        listPanel.repaint();
//    }
//    
    
    
    
    
    
    private void refreshList() {
    listPanel.removeAll();

    if (plats.isEmpty()) {
        JLabel emptyLabel = new JLabel("Your cart is empty");
        emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        emptyLabel.setForeground(TEXT_COLOR);
        emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyLabel.setBorder(new EmptyBorder(50, 0, 50, 0));
        listPanel.add(emptyLabel);
    } else {
        Set<Plat> uniquePlats = new HashSet<>(plats);
        for (Plat plat : uniquePlats) {
            Integer quantity = platQuantities.get(plat);
            if (quantity != null) {
                listPanel.add(createCartItemPanel(plat, quantity));
            }
        }
    }

    listPanel.revalidate();
    listPanel.repaint();
}

    
    
    
    
    
    
    
    
    
    

    private JPanel createCartItemPanel(Plat plat, int quantity) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
        itemPanel.setBackground(CARD_COLOR);
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        itemPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Item image thumbnail
        JLabel thumbLabel = new JLabel();
        thumbLabel.setPreferredSize(new Dimension(60, 60));
        ImageIcon thumbIcon = new ImageIcon(plat.getImagePath());
        Image thumbImg = thumbIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        thumbLabel.setIcon(new ImageIcon(thumbImg));
        itemPanel.add(thumbLabel, BorderLayout.WEST);

        // Item info
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(plat.getNom());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(SECONDARY_COLOR);
        infoPanel.add(nameLabel, BorderLayout.NORTH);

        JLabel priceLabel = new JLabel(String.format("%.2f€", plat.getPrix()));
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceLabel.setForeground(PRIMARY_COLOR);
        infoPanel.add(priceLabel, BorderLayout.CENTER);

        itemPanel.add(infoPanel, BorderLayout.CENTER);

        // Quantity controls
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setOpaque(false);
        controlPanel.setBorder(new EmptyBorder(0, 10, 0, 0));

        JPanel quantityPanel = new JPanel(new BorderLayout());
        quantityPanel.setOpaque(false);

        JLabel quantityLabel = new JLabel("×" + quantity);
        quantityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        quantityLabel.setForeground(TEXT_COLOR);
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityPanel.add(quantityLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonPanel.setOpaque(false);

        JButton minusBtn = createQuantityButton("-");
        minusBtn.addActionListener(e -> {
            removePlat(plat);
            if (currentlyDisplayedPlat == plat || platQuantities.containsKey(plat)) {
                showPlatDetails(plat);
            }
        });

        JButton plusBtn = createQuantityButton("+");
        plusBtn.addActionListener(e -> {
            addPlat(plat);
            showPlatDetails(plat);
        });

        buttonPanel.add(minusBtn);
        buttonPanel.add(plusBtn);
        quantityPanel.add(buttonPanel, BorderLayout.SOUTH);
        controlPanel.add(quantityPanel, BorderLayout.EAST);
        itemPanel.add(controlPanel, BorderLayout.EAST);

        // Hover and click effects
        itemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemPanel.setBackground(new Color(241, 243, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                itemPanel.setBackground(CARD_COLOR);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showPlatDetails(plat);
            }
        });

        return itemPanel;
    }

    private JButton createQuantityButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(30, 25));
        return button;
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Plat plat : platQuantities.keySet()) {
            int quantity = platQuantities.get(plat);
            totalPrice += plat.getPrix() * quantity;
        }
        totalPriceLabel.setText(String.format("Total: %.2f€", totalPrice));
    }

    private void updateDetailsView() {
        if (plats.isEmpty()) {
            clearDetails();
        } else if (currentlyDisplayedPlat == null || !plats.contains(currentlyDisplayedPlat)) {
            showPlatDetails(plats.get(plats.size() - 1));
        }
    }

    private void clearDetails() {
        nameLabel.setText("");
        priceLabel.setText("");
        descLabel.setText("");
        categoryLabel.setText("");
        imageLabel.setIcon(null);
        currentlyDisplayedPlat = null;
    }

    private void showPlatDetails(Plat plat) {
        currentlyDisplayedPlat = plat;

        nameLabel.setText(plat.getNom());
        priceLabel.setText(String.format("%.2f€", plat.getPrix()));
        descLabel.setText("<html>" + plat.getDescription() + "</html>");
        categoryLabel.setText(plat.getCategorie());

        updateImage(plat);
    }

    private void updateImage(Plat plat) {
        String imagePath = plat.getImagePath();
        ImageIcon icon = new ImageIcon(imagePath);

        int maxWidth = (int) (detailPanel.getWidth() * 0.8);
        int maxHeight = (int) (detailPanel.getHeight() * 0.4);

        if (maxWidth <= 0 || maxHeight <= 0) {
            return;
        }

        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
    }

    private void generateFacture() {
        if (plats.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Your cart is empty. Add items before checkout.",
                    "Empty Cart",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("=== ORDER RECEIPT ===\n\n");
        receipt.append(String.format("%-30s %6s %10s %10s\n", "Item", "Qty", "Price", "Subtotal"));
        receipt.append(String.join("", Collections.nCopies(60, "-"))).append("\n");

        Set<Plat> uniquePlats = new HashSet<>(plats);
        for (Plat plat : uniquePlats) {
            int quantity = platQuantities.get(plat);
            double subtotal = plat.getPrix() * quantity;
            receipt.append(String.format("%-30s %6d %10.2f€ %10.2f€\n",
                    plat.getNom(), quantity, plat.getPrix(), subtotal));
        }

        receipt.append("\n");
        receipt.append(String.format("%58.2f€\n", totalPrice));
        receipt.append(String.join("", Collections.nCopies(60, "=")));

        JTextArea textArea = new JTextArea(receipt.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 350));

        JOptionPane.showMessageDialog(this, scrollPane, "Order Receipt", JOptionPane.PLAIN_MESSAGE);
    }

     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Shopping Cart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Create background panel with your image
            BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/py.png");
            backgroundPanel.setLayout(new BorderLayout());
            
            // Create and add the Panier panel
            Panier panier = new Panier();
            backgroundPanel.add(panier, BorderLayout.CENTER);
            
            // Add sample dishes
            panier.addPlat(new Plat("Pizza Margherita", 12.99, "Classic tomato and mozzarella", "Italian", "images/pizza.jpg"));
            panier.addPlat(new Plat("Caesar Salad", 8.99, "Fresh romaine with Caesar dressing", "Salad", "images/salad.jpg"));
            
            frame.add(backgroundPanel);
            frame.setSize(1000, 700);
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

