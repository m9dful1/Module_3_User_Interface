import java.awt.*;
import java.io.*;
import java.util.Date;
import java.util.Random;
import javax.swing.*;

public class MainFrame extends JFrame {
    private JTextArea textArea;
    private Color initialGreenHue;

    public MainFrame() {
        // Initialize components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);  // Increased size for more visible background
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
        colorItem.addActionListener(e -> changeBackgroundColorAndTextBox());
        exitItem.addActionListener(e -> System.exit(0));

        // Set initial random green hue
        initialGreenHue = generateRandomGreenColor();
        colorItem.setText("Change Color (Green Hue)");
    }

    private void setupTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));  // Smaller than the frame
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().setLayout(new BorderLayout()); // Ensuring layout is BorderLayout
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private Color generateRandomGreenColor() {
        Random random = new Random();
        float hue = 0.25f + random.nextFloat() * 0.15f;  // Stay within the green spectrum
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    private void showDateTime() {
        textArea.setText(new Date().toString());
    }

    private void saveLog() {
        try (PrintWriter out = new PrintWriter(new FileWriter("log.txt"))) {
            out.println(textArea.getText());
            System.out.println("Current working directory: " + System.getProperty("user.dir"));
            JOptionPane.showMessageDialog(this, "Log saved successfully to 'log.txt'.", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save log: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeBackgroundColorAndTextBox() {
        Color newColor = generateRandomGreenColor();
        getContentPane().setBackground(newColor);
        textArea.setBackground(newColor.darker());
        textArea.setForeground(Color.WHITE);
        repaint();
        revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
