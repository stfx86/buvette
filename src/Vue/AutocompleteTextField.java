package Vue;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * Custom JTextField with autocomplete suggestions.
 */
public class AutocompleteTextField extends JTextField {
    private JPopupMenu popupMenu;
    private List<String> suggestions;
    private Timer showSuggestionsTimer;

    public AutocompleteTextField(int columns, List<String> suggestions) {
        super(columns);
        this.suggestions = suggestions;
        popupMenu = new JPopupMenu();
        setupAutocomplete();
    }

    private void setupAutocomplete() {
        // Timer to control delay before showing suggestions
        showSuggestionsTimer = new Timer(300, e -> showSuggestions());
        showSuggestionsTimer.setRepeats(false);  // Execute only once after delay

        // Add a focus listener to manage showing/hiding the popup
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                showSuggestions();
            }

            @Override
            public void focusLost(FocusEvent e) {
                SwingUtilities.invokeLater(() -> popupMenu.setVisible(false));
            }
        });

        // Document listener to show suggestions when the text changes
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                scheduleShowSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                scheduleShowSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                scheduleShowSuggestions();
            }
        });

        // Hide the suggestions popup if the user clicks anywhere outside the text field
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!getBounds().contains(e.getPoint())) {
                    popupMenu.setVisible(false);
                }
            }
        });
    }

    private void scheduleShowSuggestions() {
        // Reset the timer if text is changing
        showSuggestionsTimer.restart();
    }

    private void showSuggestions() {
        SwingUtilities.invokeLater(() -> {
            popupMenu.removeAll();
            String input = getText().trim().toLowerCase();
            List<String> filteredSuggestions = new ArrayList<>();

            // Filter suggestions based on the input text
            for (String suggestion : suggestions) {
                if (input.isEmpty() || suggestion.toLowerCase().contains(input)) {
                    filteredSuggestions.add(suggestion);
                }
            }

            // Add filtered suggestions to the popup
            for (String suggestion : filteredSuggestions) {
                JMenuItem item = new JMenuItem(suggestion);
                item.addActionListener(e -> {
                    setText(suggestion);
                    popupMenu.setVisible(false);
                });
                popupMenu.add(item);
            }

            // Show the popup if there are any filtered suggestions
            if (!filteredSuggestions.isEmpty()) {
                popupMenu.show(this, 0, getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        });
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
