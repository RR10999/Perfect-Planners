package eventmanagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AddEvent extends JFrame implements ActionListener {
    // Declare components for the AddEvent page
    private JTextField customerNameField, contactNumberField, eventDateField, eventTimeField, expectedPeopleField;
    private JComboBox<String> eventVenueComboBox, eventThemeComboBox, extraProgramsComboBox, eventTypeComboBox,
            foodTypeComboBox, paymentMethodComboBox, dressCodeComboBox, eventStatusComboBox, cuisineTypeComboBox,
            transportationForEventComboBox, transportationForGuestsComboBox, accommodationForGuestsComboBox;
    private JButton submitButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/EventManagement";
    private static final String USER = "root";  // Replace with your database username
    private static final String PASS = "admin";  // Replace with your database password

    private BufferedImage backgroundImage; // To hold the background image

    public AddEvent() {
        // Load background image with low opacity
        backgroundImage = loadImage("assets/login.jpg"); // Replace with your actual image path

        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(50, 150, 250));  // Set background color for heading
        JLabel headingLabel = new JLabel("Add New Event", JLabel.CENTER);
        headingLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        headingLabel.setForeground(Color.WHITE);
        headingPanel.add(headingLabel);

        // Add heading panel to the top of the frame
        add(headingPanel, BorderLayout.NORTH);

        // Create a panel to hold the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for column-wise form layout
        formPanel.setBackground(Color.GRAY); // Set the background of the form panel to grey

        // Create GridBagConstraints for component positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add space between components
        gbc.anchor = GridBagConstraints.WEST;

        // Add components with labels and fields in two columns
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabeledComponent("Customer Name:", customerNameField = new JTextField(20)), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(createLabeledComponent("Contact Number:", contactNumberField = new JTextField(20)), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabeledComponent("Event Date (yyyy-MM-dd):", eventDateField = new JTextField(20)), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(createLabeledComponent("Event Time (HH:mm:ss):", eventTimeField = new JTextField(20)), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabeledComponent("Expected People:", expectedPeopleField = new JTextField(20)), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(createLabeledComboBox("Event Venue:", eventVenueComboBox = new JComboBox<>(new String[] { "Wedding", "Corporate", "Birthday", "Conference", "Party", "Other" })), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabeledComboBox("Event Theme:", eventThemeComboBox = new JComboBox<>(new String[] { "Wedding", "Corporate", "Birthday", "Conference", "Party", "Other" })), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(createLabeledComboBox("Extra Programs:", extraProgramsComboBox = new JComboBox<>(new String[] { "Live Band", "DJ", "Photo Booth", "Games", "None" })), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabeledComboBox("Event Type (Genre):", eventTypeComboBox = new JComboBox<>(new String[] { "Wedding", "Corporate", "Birthday", "Conference", "Party", "Other" })), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(createLabeledComboBox("Dress Code:", dressCodeComboBox = new JComboBox<>(new String[] { "Formal", "Casual", "Themed" })), gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabeledComboBox("Food Type:", foodTypeComboBox = new JComboBox<>(new String[] { "Veg", "Non-Veg", "Both" })), gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        formPanel.add(createLabeledComboBox("Cuisine Type:", cuisineTypeComboBox = new JComboBox<>(new String[] { "Indian", "Italian", "Chinese", "Mexican", "Other" })), gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createLabeledComboBox("Transportation for Event:", transportationForEventComboBox = new JComboBox<>(new String[] { "Bus", "Car", "Van", "None" })), gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        formPanel.add(createLabeledComboBox("Transportation for Guests:", transportationForGuestsComboBox = new JComboBox<>(new String[] { "Bus", "Car", "Van", "None" })), gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(createLabeledComboBox("Accommodation for Guests:", accommodationForGuestsComboBox = new JComboBox<>(new String[] { "Hotel", "Guest House", "None" })), gbc);

        gbc.gridx = 1; gbc.gridy = 7;
        formPanel.add(createLabeledComboBox("Payment Method:", paymentMethodComboBox = new JComboBox<>(new String[] { "Credit Card", "Cash", "Bank Transfer" })), gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(createLabeledComboBox("Event Status:", eventStatusComboBox = new JComboBox<>(new String[] { "Scheduled", "Completed", "Cancelled" })), gbc);

        // Submit button
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        submitButton = new JButton("Submit Event");
        styleButton(submitButton);
        formPanel.add(submitButton, gbc);

        // Wrap the formPanel inside JScrollPane for scrolling functionality
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scrollbar
        scrollPane.setOpaque(false); // Make the scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Make the viewport transparent

        // Add the scrollable content to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Frame settings
        setSize(1920, 1080); // Set the frame size to 1920x1080
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setVisible(true);  // Make the window visible
    }

    // Helper method to create a labeled component with margins
    private JPanel createLabeledComponent(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false); // Make the panel transparent
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    // Helper method to create a labeled combo box with margins
    private JPanel createLabeledComboBox(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false); // Make the panel transparent
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        panel.add(label);
        panel.add(comboBox);
        return panel;
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setBackground(new Color(50, 150, 250));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 24)); // Adjusted font size
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setFocusPainted(false); // Remove the focus paint when clicked
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 1)); // Make the border invisible
        button.setOpaque(true); // Make sure the background is opaque
        button.addActionListener(this);
    }

    // Method to load the background image with low opacity
    private BufferedImage loadImage(String path) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource(path)); // Load image from resource
            return originalImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Override paint method to add background image
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f); // Set opacity
            g2d.setComposite(alpha);
            g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    // Action listener to handle button clicks
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            // Get values from the fields
            String customerName = customerNameField.getText();
            String contactNumber = contactNumberField.getText();
            String eventDate = eventDateField.getText();
            String eventTime = eventTimeField.getText();
            String expectedPeople = expectedPeopleField.getText();

            // Get values from combo boxes
            String eventVenue = (String) eventVenueComboBox.getSelectedItem();
            String eventTheme = (String) eventThemeComboBox.getSelectedItem();
            String extraPrograms = (String) extraProgramsComboBox.getSelectedItem();
            String eventType = (String) eventTypeComboBox.getSelectedItem();
            String dressCode = (String) dressCodeComboBox.getSelectedItem();
            String foodType = (String) foodTypeComboBox.getSelectedItem();
            String cuisineType = (String) cuisineTypeComboBox.getSelectedItem();
            String transportationForEvent = (String) transportationForEventComboBox.getSelectedItem();
            String transportationForGuests = (String) transportationForGuestsComboBox.getSelectedItem();
            String accommodationForGuests = (String) accommodationForGuestsComboBox.getSelectedItem();
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            String eventStatus = (String) eventStatusComboBox.getSelectedItem();

            // Insert the data into the database
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                String query = "INSERT INTO events (customer_name, contact_number, event_date, event_time, expected_people, " +
                        "event_venue, event_theme, extra_programs, event_type, dress_code, food_type, cuisine_type, " +
                        "transportation_for_event, transportation_for_guests, accommodation_for_guests, payment_method, event_status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, customerName);
                stmt.setString(2, contactNumber);
                stmt.setString(3, eventDate);
                stmt.setString(4, eventTime);
                stmt.setString(5, expectedPeople);
                stmt.setString(6, eventVenue);
                stmt.setString(7, eventTheme);
                stmt.setString(8, extraPrograms);
                stmt.setString(9, eventType);
                stmt.setString(10, dressCode);
                stmt.setString(11, foodType);
                stmt.setString(12, cuisineType);
                stmt.setString(13, transportationForEvent);
                stmt.setString(14, transportationForGuests);
                stmt.setString(15, accommodationForGuests);
                stmt.setString(16, paymentMethod);
                stmt.setString(17, eventStatus);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Event added successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding event to the database: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new AddEvent(); // Run the AddEvent GUI application
    }
}
