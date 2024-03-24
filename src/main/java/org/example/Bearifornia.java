
package org.example;


import javax.swing.*;
import java.awt.*;

public class Bearifornia {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Swing GUI");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a JLabel for the logo picture
        ImageIcon icon = new ImageIcon("logo.png"); // Change "logo.png" to your logo file path
        JLabel logoLabel = new JLabel(icon);
        panel.add(logoLabel, BorderLayout.NORTH);

        // Create a JLabel with text "Hotel Bearifornia"
        JLabel textLabel = new JLabel("Hotel Bearifornia");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 24));
        textLabel.setForeground(Color.BLUE);
        panel.add(textLabel, BorderLayout.CENTER);

        // Add the panel to the content pane of the frame
        frame.getContentPane().add(panel);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);

    }
}