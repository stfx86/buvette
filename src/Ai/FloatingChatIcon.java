package Ai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * A floating chat icon that can be dragged around the screen and opens a chat panel when clicked.
 * Integrated with Google Gemini API for chat responses, no Gson dependency.
 */
public class FloatingChatIcon extends JPanel {
    private static final int ICON_SIZE = 60;
    private static final Color ICON_COLOR = new Color(25, 118, 210); // Material blue
    private static final Color ICON_HOVER_COLOR = new Color(30, 136, 229);
    private static final Color ICON_SYMBOL_COLOR = Color.WHITE;
    private static final int BADGE_SIZE = 20;
    private static final Color BADGE_COLOR = new Color(244, 67, 54); // Material red

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
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(isHovered ? ICON_HOVER_COLOR : ICON_COLOR);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, ICON_SIZE, ICON_SIZE);
        g2d.fill(circle);

        g2d.setColor(ICON_SYMBOL_COLOR);
        int margin = ICON_SIZE / 5;
        int centerX = ICON_SIZE / 2;
        int centerY = ICON_SIZE / 2;

        int[] xPoints = {margin, ICON_SIZE - margin, ICON_SIZE - margin, centerX + 5, centerX - 5, margin};
        int[] yPoints = {margin, margin, centerY + 5, centerY + 5, ICON_SIZE - margin, ICON_SIZE - margin};
        g2d.fillPolygon(xPoints, yPoints, xPoints.length);

        if (unreadMessages > 0) {
            g2d.setColor(BADGE_COLOR);
            g2d.fillOval(ICON_SIZE - BADGE_SIZE, 0, BADGE_SIZE, BADGE_SIZE);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            FontMetrics fm = g2d.getFontMetrics();
            String badgeText = unreadMessages > 9 ? "9+" : String.valueOf(unreadMessages);
            int textX = ICON_SIZE - BADGE_SIZE / 2 - fm.stringWidth(badgeText) / 2;
            int textY = BADGE_SIZE / 2 + fm.getAscent() / 2 - 1;
            g2d.drawString(badgeText, textX, textY);
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
            dialog.setSize(300, 400);
            dialog.setLayout(new BorderLayout());

            chatHistory = new JTextArea();
            chatHistory.setEditable(false);
            chatHistory.setLineWrap(true);
            chatHistory.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(chatHistory);
            dialog.add(scrollPane, BorderLayout.CENTER);

            JPanel inputPanel = new JPanel(new BorderLayout());
            messageField = new JTextField();
            sendButton = new JButton("Send");

            inputPanel.add(messageField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);
            dialog.add(inputPanel, BorderLayout.SOUTH);

            positionDialog();

            sendButton.addActionListener(e -> sendMessage());
            messageField.addActionListener(e -> sendMessage());

            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    setVisible(false);
                    chatPanelVisible = false;
                }
            });
        }

        private void positionDialog() {
            Point iconLocation = window.getLocation();
            dialog.setLocation(iconLocation.x - dialog.getWidth(), iconLocation.y - dialog.getHeight());
        }

        private void sendMessage() {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                addMessage("You", message);
                messageField.setText("");

                SwingUtilities.invokeLater(() -> {
                    try {
                        String response = sendToGemini(message);
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
                // Check for error in response
                if (responseBody.contains("\"error\"")) {
                    int errorStart = responseBody.indexOf("\"message\":") + 10;
                    int errorEnd = responseBody.indexOf("\"", errorStart);
                    String errorMessage = responseBody.substring(errorStart, errorEnd);
                    throw new RuntimeException("Gemini API error: " + errorMessage);
                }

                // Extract text from candidates[0].content.parts[0].text
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
            String formattedMessage = sender + ": " + message + "\n";
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