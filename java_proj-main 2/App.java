import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class App extends JFrame {
    private final ArrayList<Event> events = new ArrayList<>();

    private JTextField titleField;
    private JTextField dateField;
    private JTextField venueField;
    private JTextField organizerField;
    private JTextField attendeesField;
    private JComboBox<String> typeBox;
    private JLabel totalEventsLabel;
    private JTable eventTable;
    private DefaultTableModel tableModel;

    public App() {
        setTitle("Simple Event Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(820, 520));

        buildUi();
        loadSampleEvents();
        refreshTable();
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    new App().setVisible(true);
                } catch (Throwable throwable) {
                    showStartupMessage(throwable);
                }
            });
        } catch (Throwable throwable) {
            showStartupMessage(throwable);
        }
    }

    private static void showStartupMessage(Throwable throwable) {
        String message = "The application could not start.\n" + throwable.getMessage()
            + "\nPlease run it in a desktop environment.";

        try {
            JOptionPane.showMessageDialog(null, message);
        } catch (Throwable ignored) {
            System.err.println(message);
        }
    }

    private void buildUi() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 244, 248));

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 93, 123));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Event Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        totalEventsLabel = new JLabel("Total Events: 0");
        totalEventsLabel.setForeground(Color.WHITE);
        totalEventsLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        totalEventsLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(totalEventsLabel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setOpaque(false);
        centerPanel.add(createFormPanel());
        centerPanel.add(createTablePanel());
        return centerPanel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(190, 200, 210)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel formTitle = new JLabel("Event Details");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        formTitle.setForeground(new Color(45, 93, 123));
        panel.add(formTitle, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridLayout(12, 1, 8, 8));
        fieldsPanel.setOpaque(false);

        titleField = new JTextField();
        dateField = new JTextField();
        venueField = new JTextField();
        organizerField = new JTextField();
        attendeesField = new JTextField();
        typeBox = new JComboBox<>(new String[] { "Conference", "Workshop", "Seminar", "Party", "Meeting" });

        fieldsPanel.add(createFieldLabel("Event Title"));
        fieldsPanel.add(titleField);
        fieldsPanel.add(createFieldLabel("Date"));
        fieldsPanel.add(dateField);
        fieldsPanel.add(createFieldLabel("Venue"));
        fieldsPanel.add(venueField);
        fieldsPanel.add(createFieldLabel("Organizer"));
        fieldsPanel.add(organizerField);
        fieldsPanel.add(createFieldLabel("Type"));
        fieldsPanel.add(typeBox);
        fieldsPanel.add(createFieldLabel("Number of Attendees"));
        fieldsPanel.add(attendeesField);

        panel.add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton clearButton = new JButton("Clear");

        addButton.addActionListener(event -> addEvent());
        updateButton.addActionListener(event -> updateEvent());
        clearButton.addActionListener(event -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(190, 200, 210)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel tableTitle = new JLabel("Event List");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        tableTitle.setForeground(new Color(45, 93, 123));
        panel.add(tableTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new Object[] { "Title", "Date", "Venue", "Organizer", "Type", "Attendees" },
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);
        eventTable.setRowHeight(24);
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                showSelectedEvent();
            }
        });

        panel.add(new JScrollPane(eventTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> deleteEvent());
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }

    private void loadSampleEvents() {
        events.add(new Event("Tech Conference", "12-04-2026", "City Hall", "Shreyansh", "Conference", 150));
        events.add(new Event("Java Workshop", "18-04-2026", "Lab 2", "Coding Club", "Workshop", 40));
        events.add(new Event("Music Party", "25-04-2026", "Community Center", "Student Union", "Party", 200));
    }

    private void addEvent() {
        Event event = readEventFromForm();
        if (event == null) {
            return;
        }

        events.add(event);
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Event added successfully.");
    }

    private void updateEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event to update.");
            return;
        }

        Event event = readEventFromForm();
        if (event == null) {
            return;
        }

        events.set(selectedRow, event);
        refreshTable();
        eventTable.setRowSelectionInterval(selectedRow, selectedRow);
        JOptionPane.showMessageDialog(this, "Event updated successfully.");
    }

    private void deleteEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event to delete.");
            return;
        }

        events.remove(selectedRow);
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Event deleted successfully.");
    }

    private Event readEventFromForm() {
        String title = titleField.getText().trim();
        String date = dateField.getText().trim();
        String venue = venueField.getText().trim();
        String organizer = organizerField.getText().trim();
        String type = typeBox.getSelectedItem().toString();
        String attendeesText = attendeesField.getText().trim();

        if (title.isEmpty() || date.isEmpty() || venue.isEmpty() || organizer.isEmpty() || attendeesText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return null;
        }

        int attendees;
        try {
            attendees = Integer.parseInt(attendeesText);
            if (attendees < 0) {
                JOptionPane.showMessageDialog(this, "Attendees cannot be negative.");
                return null;
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Attendees must be a number.");
            return null;
        }

        return new Event(title, date, venue, organizer, type, attendees);
    }

    private void showSelectedEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        Event event = events.get(selectedRow);
        titleField.setText(event.title);
        dateField.setText(event.date);
        venueField.setText(event.venue);
        organizerField.setText(event.organizer);
        typeBox.setSelectedItem(event.type);
        attendeesField.setText(String.valueOf(event.attendees));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Event event : events) {
            tableModel.addRow(new Object[] {
                event.title,
                event.date,
                event.venue,
                event.organizer,
                event.type,
                event.attendees
            });
        }

        totalEventsLabel.setText("Total Events: " + events.size());
    }

    private void clearFields() {
        titleField.setText("");
        dateField.setText("");
        venueField.setText("");
        organizerField.setText("");
        attendeesField.setText("");
        typeBox.setSelectedIndex(0);
        eventTable.clearSelection();
        titleField.requestFocus();
    }

    private static class Event {
        private final String title;
        private final String date;
        private final String venue;
        private final String organizer;
        private final String type;
        private final int attendees;

        private Event(String title, String date, String venue, String organizer, String type, int attendees) {
            this.title = title;
            this.date = date;
            this.venue = venue;
            this.organizer = organizer;
            this.type = type;
            this.attendees = attendees;
        }
    }
}
