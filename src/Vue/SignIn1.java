package Vue;

import DB.DB;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignIn1 extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberCheckbox;
    private JButton signInButton;
    private JButton signUpButton;
    private JLabel statusLabel;
    private JPanel leftPanel;
    private final Color PRIMARY_COLOR = new Color(32, 42, 68); // Base color
    private final Color ACCENT_COLOR = new Color(100, 149, 237); // Complementary accent

    public SignIn1() {
        initUI();
    }

    private void initUI() {
        setTitle("Buvette - Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove default window decorations
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Rounded corners

        // Add custom window dragging
        addWindowDragging();

        // Main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(240, 242, 245));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Left panel with animated gradient and logo
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_COLOR,
                        getWidth(), getHeight(), new Color(50, 60, 90));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 0));
        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));

        // Animated logo placeholder (replace with actual logo if available)
        JLabel logoLabel = new JLabel("Buvette", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Century Gothic", Font.BOLD, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBorder(new EmptyBorder(40, 0, 20, 0));

        JLabel subtitleLabel = new JLabel("Sign in to your favorite buvette", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(200, 200, 200));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(logoLabel);
        textPanel.add(subtitleLabel);

        leftPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right panel with login form and shadow
        JPanel rightPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                // Add subtle shadow
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRect(0, 0, 5, getHeight());
            }
        };
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        // Title
        JLabel signInTitle = new JLabel("Sign In");
        signInTitle.setFont(new Font("Century Gothic", Font.BOLD, 32));
        signInTitle.setForeground(PRIMARY_COLOR);
        rightPanel.add(signInTitle, gbc);

        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        usernameLabel.setForeground(PRIMARY_COLOR);
        rightPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        styleTextField(usernameField, "Enter your username");
        rightPanel.add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        passwordLabel.setForeground(PRIMARY_COLOR);
        rightPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        stylePasswordField(passwordField, "Enter your password");
        rightPanel.add(passwordField, gbc);

        // Remember me checkbox
        rememberCheckbox = new JCheckBox("Remember me");
        rememberCheckbox.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        rememberCheckbox.setBackground(Color.WHITE);
        rememberCheckbox.setForeground(PRIMARY_COLOR);
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(rememberCheckbox, gbc);

        // Sign in button
        signInButton = new JButton("SIGN IN");
        styleButton(signInButton, ACCENT_COLOR);
        signInButton.addActionListener(this::signInAction);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 15, 15, 15);
        rightPanel.add(signInButton, gbc);

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(200, 50, 50));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(statusLabel, gbc);

        // Sign up option
        JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signUpPanel.setOpaque(false);

        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        noAccountLabel.setForeground(PRIMARY_COLOR);

        signUpButton = new JButton("Sign Up");
        styleTextButton(signUpButton);
        signUpButton.addActionListener(e -> {
            new SignUp().setVisible(true);
            this.dispose();
        });

        signUpPanel.add(noAccountLabel);
        signUpPanel.add(signUpButton);
        rightPanel.add(signUpPanel, gbc);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add close button
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        titleBar.setBackground(new Color(245, 245, 245));
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setForeground(PRIMARY_COLOR);
        closeButton.addActionListener(e -> System.exit(0));
        titleBar.add(closeButton);
        add(titleBar, BorderLayout.NORTH);

        add(mainPanel);
    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        field.setForeground(PRIMARY_COLOR);
        field.setBackground(new Color(245, 245, 245));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        field.setText(placeholder);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT_COLOR, 2, true),
                        new EmptyBorder(9, 9, 9, 9)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                ));
            }
        });
    }

    private void stylePasswordField(JPasswordField field, String placeholder) {
        field.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        field.setForeground(PRIMARY_COLOR);
        field.setBackground(new Color(245, 245, 245));
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•');
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT_COLOR, 2, true),
                        new EmptyBorder(9, 9, 9, 9)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                ));
            }
        });
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Century Gothic", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add subtle scale animation on hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(
                        Math.max(bgColor.getRed() - 20, 0),
                        Math.max(bgColor.getGreen() - 20, 0),
                        Math.max(bgColor.getBlue() - 20, 0)
                ));
                button.setBorder(BorderFactory.createLineBorder(bgColor, 1, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
            }
        });
    }

    private void styleTextButton(JButton button) {
        button.setFont(new Font("Century Gothic", Font.BOLD, 14));
        button.setForeground(ACCENT_COLOR);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(ACCENT_COLOR);
            }
        });
    }

    private void signInAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("Enter your username") || password.equals("Enter your password")) {
            statusLabel.setText("Please enter both username and password");
            return;
        }

        if (DB.verifyUser(username, password)) {
            statusLabel.setForeground(new Color(50, 150, 50));
            statusLabel.setText("Login successful! Redirecting...");

            Timer timer = new Timer(1500, ev -> {
                BuvetteApp1 buvetteApp = new BuvetteApp1();
                buvetteApp.setVisible(true);
                this.dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            statusLabel.setForeground(new Color(200, 50, 50));
            statusLabel.setText("Invalid username or password");
        }
    }

    private void addWindowDragging() {
        final Point[] dragPoint = {null};
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragPoint[0] = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currPoint = e.getLocationOnScreen();
                setLocation(currPoint.x - dragPoint[0].x, currPoint.y - dragPoint[0].y);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignIn1().setVisible(true);
        });
    }
}