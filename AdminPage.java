package eventmanagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.*;

public class AdminPage extends JFrame implements ActionListener {

    private JButton addClientButton;
    private JButton addEventButton;
    private JButton viewEventButton;
    private JButton viewAllEventButton;  // New variable for "View All Event"
    private JButton updateEventButton;   // New variable for "Update Event"
    private JButton deleteEventButton;
    private JButton exitButton;

    private BufferedImage backgroundImage;

    public AdminPage() {
        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Load and blur the background image
        backgroundImage = loadImage("assets/login.jpg"); // Relative path to image
        if (backgroundImage == null) {
            System.out.println("Background image not found. Make sure the path is correct.");
        }

        // Apply blur effect to the background image
        backgroundImage = applyGaussianBlur(backgroundImage);

        // Create a background panel and paint the image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Set size of background panel and add it to the frame
        backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
        setContentPane(backgroundPanel); // Add the background panel as the content pane

        // Add a transparent panel between the background and the content
        JPanel transparentPanel = new JPanel();
        transparentPanel.setOpaque(false); // Make this panel transparent
        transparentPanel.setBounds(0, 0, getWidth(), getHeight());

        // Add a semi-transparent panel to hold the buttons (for a blur-like effect)
        JPanel semiPanel = new JPanel();
        semiPanel.setLayout(new BoxLayout(semiPanel, BoxLayout.Y_AXIS)); // Vertical BoxLayout for buttons
        semiPanel.setOpaque(true); // Make this panel semi-transparent
        semiPanel.setBackground(new Color(0, 0, 0, 150)); // 60% opacity black

        // Increased width and height for the semi-transparent panel
        semiPanel.setBounds(200, 200, 1500, 800); // Adjusted panel size and position

        // Title label
        JLabel titleLabel = new JLabel("  Dashboard  ");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 60)); // Increased font size
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title label
        semiPanel.add(titleLabel);
        semiPanel.add(Box.createVerticalStrut(30)); // Space between title and buttons

        // // Add Client button
        // addClientButton = new JButton(" Add Client ");
        // styleButton(addClientButton);
        // semiPanel.add(addClientButton);
        // semiPanel.add(Box.createVerticalStrut(15)); // Space between buttons

        // Add Event button
        addEventButton = new JButton(" Add Event / Client ");
        styleButton(addEventButton);
        semiPanel.add(addEventButton);
        semiPanel.add(Box.createVerticalStrut(15));

        // View Event button
        viewEventButton = new JButton(" View Event ");
        styleButton(viewEventButton);
        semiPanel.add(viewEventButton);
        semiPanel.add(Box.createVerticalStrut(15));

        // View All Event button
        viewAllEventButton = new JButton(" View All Event ");
        styleButton(viewAllEventButton);
        semiPanel.add(viewAllEventButton);
        semiPanel.add(Box.createVerticalStrut(15));

        // Update Event button
        updateEventButton = new JButton(" Update Event ");
        styleButton(updateEventButton);
        semiPanel.add(updateEventButton);
        semiPanel.add(Box.createVerticalStrut(15));

        // Delete Event button
        deleteEventButton = new JButton(" Delete Event ");
        styleButton(deleteEventButton);
        semiPanel.add(deleteEventButton);
        semiPanel.add(Box.createVerticalStrut(15));

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(250, 50, 50));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(this);
        semiPanel.add(exitButton);

        // Add the semi-transparent panel to the background panel
        backgroundPanel.add(semiPanel, BorderLayout.CENTER);

        // Frame settings
        setSize(1920, 1080); // Set the frame size to 1920x1080
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setVisible(true);  // Make the window visible
    }

    // Method to load the image
    private BufferedImage loadImage(String path) {
        try {
            return javax.imageio.ImageIO.read(getClass().getResource(path)); // Use relative path
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to apply a Gaussian blur to the image
    private BufferedImage applyGaussianBlur(BufferedImage src) {
        int radius = 15;
        float[] matrix = new float[radius * radius];
        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                matrix[i * radius + j] = 1f / (radius * radius); // Simple blur matrix
            }
        }

        Kernel kernel = new Kernel(radius, radius, matrix);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(src, null);
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setBackground(new Color(50, 150, 250));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 50)); // Increased font size
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setFocusPainted(false); // Remove the focus paint when clicked
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 1)); // Make the border invisible
        button.setOpaque(true); // Make sure the background is opaque
        button.addActionListener(this);
    }

    // Action listener to handle button clicks
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addClientButton) {
            // Open AddClient page
            // new AddClient();  // No import needed if in the same package
        } else if (ae.getSource() == addEventButton) {
            new AddEvent();  // No import needed if in the same package
        } else if (ae.getSource() == viewEventButton) {
            new ViewEvent();  // No import needed if in the same package
        } else if (ae.getSource() == viewAllEventButton) {
            new ViewEvent();  // No import needed if in the same package
        } else if (ae.getSource() == updateEventButton) {
            new UpdateEvent();  // No import needed if in the same package
        } else if (ae.getSource() == deleteEventButton) {
            new DeleteEvent();  // No import needed if in the same package
        } else if (ae.getSource() == exitButton) {
            System.exit(0);  // Exit the application
        }
    }

    // Main method to run the page
    public static void main(String[] args) {
        new AdminPage(); // Run the administrator page
    }
}
