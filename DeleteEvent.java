package eventmanagement;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class DeleteEvent extends JFrame {

    // Declare components for the DeleteEvent page
    private JTextField customerNameField, contactNumberField, eventDateField, eventTimeField, expectedPeopleField;
    private JTextField eventVenueField, eventThemeField, extraProgramsField, eventTypeField,
            foodTypeField, paymentMethodField, dressCodeField, eventStatusField, cuisineTypeField,
            transportationForEventField, transportationForGuestsField, accommodationForGuestsField;

    private JButton fetchButton, DeleteButton;

    public DeleteEvent() {
        setTitle("Delete Event Detail's");
        setLayout(new BorderLayout());
        JLabel headingLabel = new JLabel("Delete Event Detail's", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        headingLabel.setForeground(new Color(0, 102, 204)); // Optional color change
        add(headingLabel, BorderLayout.NORTH);

        // Create form panel with GridBagLayout for structured component placement
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Adding components to the form
        addComponentsToForm(gbc, formPanel);

        // Wrap formPanel in JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for the Delete button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        DeleteButton = new JButton("Delete");
        DeleteButton.addActionListener(e -> deleteEventDetails());
        styleButton(DeleteButton);

        buttonPanel.add(DeleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Frame settings
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void addComponentsToForm(GridBagConstraints gbc, JPanel formPanel) {
        // Add fields and buttons to form panel with their respective labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        contactNumberField = new JTextField(20);
        formPanel.add(createLabeledComponent("Contact Number:", contactNumberField), gbc);

        // Fetch Button
        gbc.gridx = 1;
        gbc.gridy = 0;
        fetchButton = new JButton("Fetch");
        fetchButton.addActionListener(e -> fetchEventDetails());
        styleButton(fetchButton);
        formPanel.add(fetchButton, gbc);

        // Adding remaining fields in a structured manner
        gbc.gridx = 0;
        gbc.gridy = 1;
        customerNameField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Customer Name:", customerNameField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        eventDateField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Event Date (yyyy-MM-dd):", eventDateField), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        eventTimeField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Event Time (HH:mm:ss):", eventTimeField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        expectedPeopleField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Expected People:", expectedPeopleField), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        eventVenueField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Event Venue:", eventVenueField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        eventThemeField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Event Theme:", eventThemeField), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        extraProgramsField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Extra Programs:", extraProgramsField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        eventTypeField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Event Type (Genre):", eventTypeField), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        dressCodeField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Dress Code:", dressCodeField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        foodTypeField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Food Type:", foodTypeField), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        cuisineTypeField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Cuisine Type:", cuisineTypeField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        transportationForEventField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Transportation for Event:", transportationForEventField), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        transportationForGuestsField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Transportation for Guests:", transportationForGuestsField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        accommodationForGuestsField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Accommodation for Guests:", accommodationForGuestsField), gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        paymentMethodField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Payment Method:", paymentMethodField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        eventStatusField = createNonEditableTextField();
        formPanel.add(createLabeledComponent("Event Status:", eventStatusField), gbc);
    }

    // Helper method to create a non-editable text field
    private JTextField createNonEditableTextField() {
        JTextField textField = new JTextField(20);
        textField.setEditable(false);
        return textField;
    }

    // Helper method to fetch event details from the database based on contact number
    private void fetchEventDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EventManagement", "root", "admin");
            String contactNumber = contactNumberField.getText().trim();

            if (!contactNumber.isEmpty()) {
                String query = "SELECT * FROM Events WHERE contact_number = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, contactNumber);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    populateFields(rs);
                } else {
                    JOptionPane.showMessageDialog(this, "No event found for the given contact number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                conn.close();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a contact number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Populate fields with database values
    private void populateFields(ResultSet rs) throws SQLException {
        customerNameField.setText(rs.getString("customer_name"));
        contactNumberField.setText(rs.getString("contact_number"));
        eventDateField.setText(rs.getString("event_date"));
        eventTimeField.setText(rs.getString("event_time"));
        expectedPeopleField.setText(rs.getString("expected_people"));
        eventVenueField.setText(rs.getString("event_venue"));
        eventThemeField.setText(rs.getString("event_theme"));
        extraProgramsField.setText(rs.getString("extra_programs"));
        eventTypeField.setText(rs.getString("event_type"));
        dressCodeField.setText(rs.getString("dress_code"));
        foodTypeField.setText(rs.getString("food_type"));
        cuisineTypeField.setText(rs.getString("cuisine_type"));
        transportationForEventField.setText(rs.getString("transportation_for_event"));
        transportationForGuestsField.setText(rs.getString("transportation_for_guests"));
        accommodationForGuestsField.setText(rs.getString("accommodation_for_guests"));
        paymentMethodField.setText(rs.getString("payment_method"));
        eventStatusField.setText(rs.getString("event_status"));

        // Make fields editable after fetching data
        enableFieldsForEditing();
    }

    // Enable all fields for editing after data is fetched
    private void enableFieldsForEditing() {
        customerNameField.setEditable(true);
        eventDateField.setEditable(true);
        eventTimeField.setEditable(true);
        expectedPeopleField.setEditable(true);
        eventVenueField.setEditable(true);
        eventThemeField.setEditable(true);
        extraProgramsField.setEditable(true);
        eventTypeField.setEditable(true);
        dressCodeField.setEditable(true);
        foodTypeField.setEditable(true);
        cuisineTypeField.setEditable(true);
        transportationForEventField.setEditable(true);
        transportationForGuestsField.setEditable(true);
        accommodationForGuestsField.setEditable(true);
        paymentMethodField.setEditable(true);
        eventStatusField.setEditable(true);
    }

    // Helper method to Delete the event details in the database
    private void deleteEventDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EventManagement", "root", "admin");

            // SQL query to delete an event based on the contact number
            String query = "DELETE FROM Events WHERE contact_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, contactNumberField.getText());  // Use contact number to identify the event to delete

            // Execute the query and get the number of rows affected
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Event deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();  // Clear the fields after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "No event found with the given contact number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting event details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to clear all input fields
    private void clearFields() {
        customerNameField.setText("");
        contactNumberField.setText("");
        eventDateField.setText("");
        eventTimeField.setText("");
        expectedPeopleField.setText("");
        eventVenueField.setText("");
        eventThemeField.setText("");
        extraProgramsField.setText("");
        eventTypeField.setText("");
        dressCodeField.setText("");
        foodTypeField.setText("");
        cuisineTypeField.setText("");
        transportationForEventField.setText("");
        transportationForGuestsField.setText("");
        accommodationForGuestsField.setText("");
        paymentMethodField.setText("");
        eventStatusField.setText("");

        // Make the fields non-editable after clearing them
        disableFields();
    }

// Method to make fields non-editable
    private void disableFields() {
        customerNameField.setEditable(false);
        eventDateField.setEditable(false);
        eventTimeField.setEditable(false);
        expectedPeopleField.setEditable(false);
        eventVenueField.setEditable(false);
        eventThemeField.setEditable(false);
        extraProgramsField.setEditable(false);
        eventTypeField.setEditable(false);
        dressCodeField.setEditable(false);
        foodTypeField.setEditable(false);
        cuisineTypeField.setEditable(false);
        transportationForEventField.setEditable(false);
        transportationForGuestsField.setEditable(false);
        accommodationForGuestsField.setEditable(false);
        paymentMethodField.setEditable(false);
        eventStatusField.setEditable(false);
    }

    // Helper method to create a labeled component
    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.BOLD, 24));
        label.setForeground(Color.BLACK);
        panel.add(label);
        component.setFont(new Font("Tahoma", Font.PLAIN, 24));
        panel.add(component);
        return panel;
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("Tahoma", Font.BOLD, 24));
        button.setBackground(new Color(0, 204, 102));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setPreferredSize(new Dimension(250, 50));
    }

    public static void main(String[] args) {
        new DeleteEvent();
    }
}
