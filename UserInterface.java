import java.awt.*;
import java.io.*;
import java.util.Date;
import java.util.Random;
import javax.swing.*;

public class UserInterface extends JFrame {
    private JTextArea textArea;
    private Color initialGreenHue;
    private final Random random = new Random(); // For generating random hues

    public UserInterface() {
        // Initialize components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

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
        dateItem.addActionListener(e -> showDateTime());
        saveItem.addActionListener(e -> saveLog());
        colorItem.addActionListener(e -> changeBackgroundColor());
        exitItem.addActionListener(e -> System.exit(0));

        // Initialize and display initial random green hue
        initialGreenHue = generateRandomGreenColor();
        colorItem.setText(String.format("Change Color (%s)", colorToString(initialGreenHue)));
    }

    private void setupTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private Color generateRandomGreenColor() {
        // Generate hue within green spectrum (approximately)
        float hue = 0.25f + random.nextFloat() * 0.15f;
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    private String colorToString(Color color) {
        // Convert color to a string in RGB format
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void showDateTime() {
        textArea.setText(new Date().toString());
    }

    private void saveLog() {
        try (PrintWriter out = new PrintWriter(new FileWriter("log.txt"))) {
            out.println(textArea.getText());
            JOptionPane.showMessageDialog(this, "Log saved successfully to 'log.txt'.", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save log: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeBackgroundColor() {
        // Apply the initial random green hue
        getContentPane().setBackground(initialGreenHue);
        textArea.setBackground(initialGreenHue);
        textArea.setForeground(Color.WHITE); // Set text color to white for better visibility
        repaint(); // Ensure UI updates correctly
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserInterface().setVisible(true));
    }
}
