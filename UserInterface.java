import java.awt.*;
import java.io.*;
import java.util.Date;
import java.util.Random;
import javax.swing.*;


public class UserInterface extends JFrame {
    private JTextArea textArea; // Text area for displaying log
    private Color initialGreenHue; // Initial random green hue
    private final Random random = new Random(); // For generating random hues

    public UserInterface() {
        // Initialize components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        setSize(500, 400); // Set window size
        setLocationRelativeTo(null); // Center window

        // Menu bar and items
        JMenuBar menuBar = new JMenuBar(); 
        JMenu menu = new JMenu("Options"); 
        JMenuItem dateItem = new JMenuItem("Show Date/Time");
        JMenuItem saveItem = new JMenuItem("Save Log");
        JMenuItem colorItem = new JMenuItem("Change Color");
        JMenuItem exitItem = new JMenuItem("Exit"); 

        // Add menu items to menu
        menu.add(dateItem); 
        menu.add(saveItem);
        menu.add(colorItem);
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Setup text area with scroll pane and margins
        setupTextArea(); 

        // Menu actions
        dateItem.addActionListener(e -> showDateTime()); // Show date and time
        saveItem.addActionListener(e -> saveLog()); // Save log
        colorItem.addActionListener(e -> changeBackgroundColor()); // Change background color
        exitItem.addActionListener(e -> System.exit(0)); // Exit the program

        // Initialize and display initial random green hue
        initialGreenHue = generateRandomGreenColor(); // Generate random green color
        colorItem.setText(String.format("Change Color (%s)", colorToString(initialGreenHue))); // Update menu item text
    }

    private void setupTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true); // Wrap text at line boundaries
        textArea.setWrapStyleWord(true); // Wrap text at word boundaries
        JScrollPane scrollPane = new JScrollPane(textArea); // Add text area to scroll pane
        scrollPane.setPreferredSize(new Dimension(300, 200)); // Set preferred size
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        getContentPane().setLayout(new BorderLayout()); // Set layout to border layout
        getContentPane().add(scrollPane, BorderLayout.CENTER); // Add scroll pane to frame
    } 

    private Color generateRandomGreenColor() {
        // Generate hue within green spectrum (approximately)
        float hue = 0.25f + random.nextFloat() * 0.15f; // 0.25 to 0.40
        return Color.getHSBColor(hue, 1.0f, 1.0f); // Convert HSB to RGB
    }

    private String colorToString(Color color) {
        // Convert color to a string in RGB format
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void showDateTime() {
        textArea.setText(new Date().toString()); // Set text to current date and time
    }

    private void saveLog() { 
        try (PrintWriter out = new PrintWriter(new FileWriter("log.txt"))) { // Open file for writing
            out.println(textArea.getText()); // Write text area content to file
            JOptionPane.showMessageDialog(this, "Log saved successfully to 'log.txt'.", "Save Successful", JOptionPane.INFORMATION_MESSAGE); // Display success message
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save log: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Display error message
        }
    }

    private void changeBackgroundColor() {
        // Apply the initial random green hue
        getContentPane().setBackground(initialGreenHue); // Set background color
        textArea.setBackground(initialGreenHue); // Set text area background color
        textArea.setForeground(Color.WHITE); // Set text color to white for better visibility
        repaint(); // Ensure UI updates correctly
    }

    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> new UserInterface().setVisible(true)); // Create and display the UI
    }
}
