package Ai;
import DB.* ;
import Vue.Plat;
import Vue.SignIn;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class FloatingChatIcon extends JPanel {
    private  String oldConvesation   = "" ;
    private static final int ICON_SIZE = 60;
    private static final Color ICON_COLOR = new Color(33, 150, 243); // Modern blue
    private static final Color ICON_HOVER_COLOR = new Color(66, 165, 245); // Lighter blue on hover
    private static final Color ICON_SYMBOL_COLOR = Color.WHITE;
    private static final int BADGE_SIZE = 20;
    private static final Color BADGE_COLOR = new Color(244, 67, 54);
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 80); // Subtle shadow
    private Image backgroundImage;

    private Point dragStart = null;
    private boolean isHovered = false;
    private JWindow window;
    private int unreadMessages = 0;
    private ChatPanel chatPanel;
    private boolean chatPanelVisible = false;
    private static final String API_KEY = "AIzaSyB3VFJjJMj6i0JG-HCjaxvbet1P52gD7to";
    private static final String GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    public FloatingChatIcon(JFrame parentFrame) {
        window = new JWindow(parentFrame);
        window.setBackground(new Color(0, 0, 0, 0));
        window.setAlwaysOnTop(true);

        chatPanel = new ChatPanel(parentFrame);

        setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
        setOpaque(false);

        window.add(this);
        window.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(screenSize.width - ICON_SIZE - 20, screenSize.height - ICON_SIZE - 50);

        addMouseListener();
        addMotionListener();

        // Load a modern icon (replace with your own high-quality icon path)
        backgroundImage = new ImageIcon("src/images/chat.png").getImage();

        window.setVisible(true);
    }

    public void setIconVisible(boolean visible) {
        window.setVisible(visible);
    }

    public void setUnreadMessageCount(int count) {
        this.unreadMessages = count;
        repaint();
    }

    private void addMouseListener() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragStart != null && e.getPoint().distance(dragStart) < 5) {
                    toggleChatPanel();
                }
                dragStart = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                setCursor(Cursor.getDefaultCursor());
                repaint();
            }
        };

        addMouseListener(mouseAdapter);
    }

    private void addMotionListener() {
        MouseMotionAdapter motionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart != null) {
                    Point windowLocation = window.getLocation();
                    windowLocation.x += e.getX() - dragStart.x;
                    windowLocation.y += e.getY() - dragStart.y;

                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    windowLocation.x = Math.max(0, Math.min(windowLocation.x, screenSize.width - ICON_SIZE));
                    windowLocation.y = Math.max(0, Math.min(windowLocation.y, screenSize.height - ICON_SIZE));
                    window.setLocation(windowLocation);
                }
            }
        };

        addMouseMotionListener(motionAdapter);
    }

    private void toggleChatPanel() {
        chatPanelVisible = !chatPanelVisible;
        chatPanel.setVisible(chatPanelVisible);

        if (chatPanelVisible) {
            setUnreadMessageCount(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw shadow
        g2d.setColor(SHADOW_COLOR);
        g2d.fillOval(4, 4, ICON_SIZE - 8, ICON_SIZE - 8);

        // Draw gradient background for icon
        GradientPaint gradient = new GradientPaint(
            0, 0, isHovered ? ICON_HOVER_COLOR : ICON_COLOR,
            0, ICON_SIZE, isHovered ? ICON_COLOR : ICON_HOVER_COLOR
        );
        g2d.setPaint(gradient);
        g2d.fillOval(0, 0, ICON_SIZE, ICON_SIZE);

        // Draw the icon image
        if (backgroundImage != null) {
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.drawImage(backgroundImage, 5, 5, ICON_SIZE - 10, ICON_SIZE - 10, this);
        }

        // Draw unread message badge
        if (unreadMessages > 0) {
            g2d.setColor(BADGE_COLOR);
            g2d.fillOval(ICON_SIZE - BADGE_SIZE, 0, BADGE_SIZE, BADGE_SIZE);
            g2d.setColor(ICON_SYMBOL_COLOR);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 14)); // Slightly larger badge font
            String badgeText = unreadMessages > 9 ? "9+" : String.valueOf(unreadMessages);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(badgeText);
            int textHeight = fm.getAscent();
            g2d.drawString(badgeText, ICON_SIZE - BADGE_SIZE + (BADGE_SIZE - textWidth) / 2, BADGE_SIZE - (BADGE_SIZE - textHeight) / 2);
        }

        g2d.dispose();
    }

    public void receiveMessage(String sender, String message) {
        chatPanel.addMessage(sender, message);

        if (!chatPanelVisible) {
            setUnreadMessageCount(unreadMessages + 1);
        }
    }

    public void dispose() {
        window.dispose();
        chatPanel.dispose();
    }

    private class ChatPanel {
        private JDialog dialog;
        private JTextArea chatHistory;
        private JTextField messageField;
        private JButton sendButton;
        private final HttpClient httpClient = HttpClient.newHttpClient();

        public ChatPanel(JFrame parentFrame) {
            dialog = new JDialog(parentFrame, "Chat", false);
            dialog.setSize(350, 450);
            dialog.setLayout(new BorderLayout());
            // Rounded corners for dialog
            dialog.setUndecorated(true); // Remove default window decorations
            dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            dialog.setShape(new RoundRectangle2D.Double(0, 0, 350, 450, 20, 20)); // Rounded corners

            // Modern chat history area with light background
            chatHistory = new JTextArea() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Light background
                    g2d.setColor(new Color(245, 245, 245)); // Very light gray
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    // Inner shadow
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            chatHistory.setOpaque(false); // Allow background to show
            chatHistory.setForeground(new Color(50, 50, 50)); // Dark gray text for contrast
            chatHistory.setFont(new Font("Roboto", Font.PLAIN, 14));
            chatHistory.setEditable(false);
            chatHistory.setLineWrap(true);
            chatHistory.setWrapStyleWord(true);
            chatHistory.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JScrollPane scrollPane = new JScrollPane(chatHistory) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(245, 245, 245));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(null);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            dialog.add(scrollPane, BorderLayout.CENTER);

            // Modern input panel with light background
            JPanel inputPanel = new JPanel(new BorderLayout(5, 5)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(245, 245, 245));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            inputPanel.setOpaque(false);
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Custom message input field with light background
            messageField = new JTextField() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // White background
                    g2d.setColor(Color.WHITE);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    // Border
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            messageField.setOpaque(false); // Allow background to show
            messageField.setForeground(new Color(50, 50, 50)); // Dark gray text
            messageField.setFont(new Font("Roboto", Font.PLAIN, 14));
            messageField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 1, true), // Blue border
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));

            sendButton = new JButton("Send");
            sendButton.setBackground(new Color(33, 150, 243)); // Blue
            sendButton.setForeground(Color.WHITE); // White text
            sendButton.setFont(new Font("Roboto", Font.BOLD, 14));
            sendButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            sendButton.setFocusPainted(false);
            sendButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    sendButton.setBackground(new Color(66, 165, 245)); // Lighter blue
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    sendButton.setBackground(new Color(33, 150, 243)); // Original blue
                }
            });

            inputPanel.add(messageField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);
            dialog.add(inputPanel, BorderLayout.SOUTH);

            positionDialog();

            sendButton.addActionListener(e -> sendMessage());
            messageField.addActionListener(e -> sendMessage());
        }

        private void positionDialog() {
            Point iconLocation = window.getLocation();
            dialog.setLocation(iconLocation.x - dialog.getWidth(), iconLocation.y - dialog.getHeight());
        }

        public static String getMessagePlats() {
            List<Plat> plats = DB.listPlats();
            StringBuilder message = new StringBuilder("Liste des plats :\n");

            for (Plat plat : plats) {
                message.append("Nom : ").append(plat.getNom()).append("\n")
                       .append("Prix : ").append(plat.getPrix()).append(" DH\n")
                       .append("Description : ").append(plat.getDescription()).append("\n")
                       .append("Catégorie : ").append(plat.getCategorie()).append("\n")
                       .append("Image : ").append(plat.getImagePath()).append("\n")
                       .append("-----------------------------\n");
            }

            return message.toString();
        }

        private void sendMessage() {
            String msg = 
                "You are Buvette ChatBot, a virtual assistant designed to help users with restaurant-related inquiries.\n"
              + "Your creator is Youssef.\n\n"

              + "Here is how you should answer certain questions:\n"
              + "- If the user asks: \"How are you?\" → Reply: \"I am Buvette ChatBot, always ready to help!\"\n"
              + "- If the user asks: \"Who made you?\" → Reply: \"I was created by Youssef.\"\n"
              + "- If the user asks: \"Who is talking?\" → Reply with the user's info below.\n\n"

              + "Here is the current user's information:\n"
              + "- Name: " + SignIn.user.getName() + "\n"
              + "- Email: " + SignIn.user.getEmail() + "\n"
              + "- Password: " + SignIn.user.getPassword() + "\n\n"

              + "Below is the full list of available dishes you can use to answer menu-related questions:\n"
              + getMessagePlats() + "\n"

              + "Use this information as your context to understand and respond to user messages appropriately." +

              "use this old conversation as your memory  " + oldConvesation ;

            String message = messageField.getText().trim();
            oldConvesation = oldConvesation.concat("user  : " + message + ",");
                    
            if (!message.isEmpty()) {
                addMessage("You", message);
                messageField.setText("");

                SwingUtilities.invokeLater(() -> {
                    try {
                        String ContextePluseMessage = msg.concat(message);
                     
                        String response = sendToGemini(ContextePluseMessage);
                        response.replace("Gemini","Buvette") ;
                        oldConvesation = oldConvesation.concat("chat bot  : " + response + " ,");
                        addMessage("Gemini", response);
                    } catch (Exception e) {
                        addMessage("System", "Error communicating with Gemini API: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        }

        private String sendToGemini(String message) throws Exception {
            String requestBody = String.format(
                "{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}",
                message.replace("\"", "\\\"")
            );

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            if (response.statusCode() == 200) {
                if (responseBody.contains("\"error\"")) {
                    int errorStart = responseBody.indexOf("\"message\":") + 10;
                    int errorEnd = responseBody.indexOf("\"", errorStart);
                    String errorMessage = responseBody.substring(errorStart, errorEnd);
                    throw new RuntimeException("Gemini API error: " + errorMessage);
                }

                String marker = "\"text\": \"";
                int textStart = responseBody.indexOf(marker) + marker.length();
                int textEnd = responseBody.indexOf("\"", textStart);
                if (textStart >= marker.length() && textEnd > textStart) {
                    String fulfillmentText = responseBody.substring(textStart, textEnd).replace("\\n", "\n");
                    if (!fulfillmentText.isEmpty()) {
                        return fulfillmentText;
                    } else {
                        throw new RuntimeException("Empty response from Gemini API");
                    }
                } else {
                    throw new RuntimeException("Invalid response format from Gemini API");
                }
            } else {
                throw new RuntimeException("Gemini API error: " + response.statusCode() + " - " + responseBody);
            }
        }

        public void addMessage(String sender, String message) {
            String formattedMessage = sender + ": " + message + "\n\n";
            chatHistory.append(formattedMessage);
            chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
        }

        public void setVisible(boolean visible) {
            if (visible) {
                positionDialog();
            }
            dialog.setVisible(visible);
            chatPanelVisible = visible;
        }

        public void dispose() {
            dialog.dispose();
        }
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Main Application");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("This is your main application"));
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        FloatingChatIcon chatIcon = new FloatingChatIcon(mainFrame);

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                chatIcon.receiveMessage("System", "Welcome to the application!");
                Thread.sleep(5000);
                chatIcon.receiveMessage("Gemini", "Need any help today?");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                chatIcon.dispose();
            }
        });
    }
}