package eventmanagement;


import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ViewEvent extends JFrame {

    // Declare components for the ViewEvent page
    private JTextField customerNameField, contactNumberField, eventDateField, eventTimeField, expectedPeopleField;
    private JTextField eventVenueField, eventThemeField, extraProgramsField, eventTypeField,
            foodTypeField, paymentMethodField, dressCodeField, eventStatusField, cuisineTypeField,
            transportationForEventField, transportationForGuestsField, accommodationForGuestsField;

    private JButton fetchButton;

    public ViewEvent() {
        setTitle("View Events");
        setLayout(new BorderLayout());
        JLabel headingLabel = new JLabel("View Details", SwingConstants.CENTER);
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
        new ViewEvent();
    }
}
