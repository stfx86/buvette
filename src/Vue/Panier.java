//package Vue;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Panier {
//    private List<Plat> plats;
//    private double totalPrice;
//
//    public Panier() {
//        plats = new ArrayList<>();
//        totalPrice = 0.0;
//    }
//
//    // Add a plat to the panier
//    public void addPlat(Plat plat) {
//        plats.add(plat);
//        totalPrice += plat.getPrix();
//        System.out.println("Vue.Panier.addPlat:: plat was added ");
//    }
//
//    // Remove a plat from the panier
//    public boolean removePlat(Plat plat) {
//        boolean removed = plats.remove(plat);
//        if (removed) {
//            totalPrice -= plat.getPrix();
//        }
//        return removed;
//    }
//
//    // Get all plats in the panier
//    public List<Plat> getPlats() {
//        return new ArrayList<>(plats); // Return a copy for encapsulation
//    }
//
//    // Get the total price of all items
//    public double getTotalPrice() { 
//        return totalPrice;
//    }
//
//    // Clear the panier
//    public void clear() {
//        plats.clear();
//        totalPrice = 0.0;
//    }
//
//    // Check if panier is empty
//    public boolean isEmpty() {
//        return plats.isEmpty();
//    }
//
//    // Get the number of items in the panier
//    public int getItemCount() {
//        return plats.size();
//    }
//
//    // Additional useful methods
//    public boolean contains(Plat plat) {
//        return plats.contains(plat);
//    }
//
//    // Get a formatted string of the panier contents
//    public String getPanierSummary() {
//        StringBuilder summary = new StringBuilder();
//        summary.append("Panier (").append(getItemCount()).append(" items):\n");
//        for (Plat plat : plats) {
//            summary.append("- ").append(plat.getNom())
//                  .append(" (").append(plat.getPrix()).append("€)\n");
//        }
//        summary.append("Total: ").append(getTotalPrice()).append("€");
//        return summary.toString();
//    }
//}











package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;

public class Panier extends JPanel {
    private List<Plat> plats;
    private Map<Plat, Integer> platQuantities;
    private double totalPrice;
public int a=222;
    private JPanel listPanel;
    private JScrollPane listScrollPane;
    private JLabel totalPriceLabel;

    private JPanel detailPanel;
    private JLabel imageLabel;
    private JLabel nameLabel, priceLabel, descLabel, categoryLabel;
    private Plat currentlyDisplayedPlat;

    public Panier() {
        plats = new ArrayList<>();
        platQuantities = new HashMap<>();
        totalPrice = 0.0;

        setLayout(new BorderLayout());

        // === LEFT SIDE: List of Plats ===
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listScrollPane = new JScrollPane(listPanel);

        // === RIGHT SIDE: Plat Details ===
        detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Plat Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel = new JLabel("Name: ");
        priceLabel = new JLabel("Price: ");
        descLabel = new JLabel("Description: ");
        categoryLabel = new JLabel("Category: ");

        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        detailPanel.add(imageLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        detailPanel.add(nameLabel, gbc);

        gbc.gridy = 2;
        detailPanel.add(priceLabel, gbc);

        gbc.gridy = 3;
        detailPanel.add(descLabel, gbc);

        gbc.gridy = 4;
        detailPanel.add(categoryLabel, gbc);

        detailPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (currentlyDisplayedPlat != null) {
                    updateImage(currentlyDisplayedPlat);
                }
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, detailPanel);
        splitPane.setResizeWeight(0.4);
        splitPane.setDividerLocation(0.4);

        JButton removeButton = new JButton("Remove Plat");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setPreferredSize(new Dimension(150, 40));

        JButton factureButton = new JButton("Facture");
        factureButton.setBackground(Color.BLUE);
        factureButton.setForeground(Color.WHITE);
        factureButton.setFont(new Font("Arial", Font.BOLD, 14));
        factureButton.setPreferredSize(new Dimension(150, 40));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(removeButton);
        buttonPanel.add(factureButton);

        totalPriceLabel = new JLabel("Total: 0.0€");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 14));

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(totalPriceLabel, BorderLayout.NORTH);

        removeButton.addActionListener(e -> {
            if (!plats.isEmpty()) {
                Plat platToRemove = plats.get(plats.size() - 1);
                removePlat(platToRemove); // Corrected line
            }
        });

        factureButton.addActionListener(e -> {
            generateFacture();
        });
    }

    public void addPlat(Plat plat) {
        if (!platQuantities.containsKey(plat)) {
            plats.add(plat);
            platQuantities.put(plat, 1);
        } else {
            platQuantities.put(plat, platQuantities.get(plat) + 1);
        }
        totalPrice += plat.getPrix();
        refreshList();
        updateTotalPrice();
    }

    public boolean removePlat(Plat plat) {
        if (!platQuantities.containsKey(plat)) return false;

        int quantity = platQuantities.get(plat);
        if (quantity > 0) {
            platQuantities.put(plat, quantity - 1);
            totalPrice -= plat.getPrix();
            refreshList();
            updateTotalPrice();
            return true;
        }
        return false;
    }

    private void refreshList() {
        listPanel.removeAll();

        int itemHeight = 70;

        for (Plat plat : plats) {
            int qty = platQuantities.getOrDefault(plat, 0);

            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setPreferredSize(new Dimension(360, itemHeight));
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemHeight));
            itemPanel.setBackground(new Color(245, 245, 245));
            itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            JLabel nameQtyLabel = new JLabel(plat.getNom() + " x" + qty);
            nameQtyLabel.setFont(new Font("Arial", Font.BOLD, 15));
            nameQtyLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

            JButton minusBtn = new JButton("-");
            JButton plusBtn = new JButton("+");
            minusBtn.setPreferredSize(new Dimension(50, 40));
            plusBtn.setPreferredSize(new Dimension(50, 40));
            minusBtn.setFont(new Font("Arial", Font.BOLD, 14));
            plusBtn.setFont(new Font("Arial", Font.BOLD, 14));

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
            btnPanel.setOpaque(false);
            btnPanel.add(minusBtn);
            btnPanel.add(plusBtn);

            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    itemPanel.setBackground(new Color(230, 230, 230));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    itemPanel.setBackground(new Color(245, 245, 245));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    showPlatDetails(plat);
                }
            });

            minusBtn.addActionListener(e -> {
                removePlat(plat);
                showPlatDetails(plat);
            });
            plusBtn.addActionListener(e -> {
                addPlat(plat);
                showPlatDetails(plat);
            });

            itemPanel.add(nameQtyLabel, BorderLayout.CENTER);
            itemPanel.add(btnPanel, BorderLayout.EAST);
            listPanel.add(itemPanel);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Plat plat : platQuantities.keySet()) {
            int quantity = platQuantities.get(plat);
            if (quantity > 0) {
                totalPrice += plat.getPrix() * quantity;
            }
        }
        totalPriceLabel.setText("Total: " + String.format("%.2f", totalPrice) + "€");
    }

    private void showPlatDetails(Plat plat) {
        currentlyDisplayedPlat = plat;

        nameLabel.setText("Name: " + plat.getNom());
        priceLabel.setText("Price: " + plat.getPrix() + "€");
        descLabel.setText("Description: " + plat.getDescription());
        categoryLabel.setText("Category: " + plat.getCategorie());

        updateImage(plat);
    }

    private void updateImage(Plat plat) {
        String imagePath = plat.getImagePath();
        ImageIcon icon = new ImageIcon(imagePath);

        int maxWidth = (int) (detailPanel.getWidth() * 0.8);
        int maxHeight = (int) (detailPanel.getHeight() * 0.3);

        if (maxWidth <= 0 || maxHeight <= 0) return;

        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
    }

    private void generateFacture() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== Order Receipt ===\n\n");

        boolean hasItems = false;
        for (Plat plat : plats) {
            int quantity = platQuantities.getOrDefault(plat, 0);
            if (quantity > 0) {
                hasItems = true;
                double subtotal = plat.getPrix() * quantity;
                receipt.append(String.format("%s x%d - €%.2f (each) = €%.2f\n",
                        plat.getNom(), quantity, plat.getPrix(), subtotal));
            }
        }

        if (!hasItems) {
            JOptionPane.showMessageDialog(this, "No items in the cart to generate a receipt.",
                    "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        receipt.append("\n");
        receipt.append(String.format("Total: €%.2f", totalPrice));

        JTextArea textArea = new JTextArea(receipt.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Order Receipt", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Panier");
        Panier panierPanel = new Panier();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panierPanel);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }
    
    
    public void refresh() {
    refreshList();
    updateTotalPrice();
    if (!plats.isEmpty()) {
        showPlatDetails(plats.get(plats.size() - 1)); // Show details of the last added plat
    } else {
        // Clear details if panier is empty
        nameLabel.setText("Name: ");
        priceLabel.setText("Price: ");
        descLabel.setText("Description: ");
        categoryLabel.setText("Category: ");
        imageLabel.setIcon(null);
        currentlyDisplayedPlat = null;
    }
}
}