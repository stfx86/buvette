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

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                showSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                showSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
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