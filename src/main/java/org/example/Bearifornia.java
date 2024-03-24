
package org.example;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Bearifornia {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hotel Bearifonia");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout()); // Use BorderLayout for positioning


        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ImageIcon icon = new ImageIcon("src/main/resources/bear_logo.png");
        JLabel logoLabel = new JLabel(icon);
        logoLabel.setBorder(new EmptyBorder(20, 0, 0, 20));
        panel.add(logoLabel);

        JLabel textLabel = new JLabel("Hotel Bearifornia");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 24));
        textLabel.setForeground(Color.DARK_GRAY);
        panel.add(textLabel, BorderLayout.CENTER);

        contentPane.add(panel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));


        JButton button1 = new JButton("Create Account");

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color buttonColor = Color.DARK_GRAY;
        Dimension buttonSize = new Dimension(150, 40);
        Border buttonBorder = BorderFactory.createLineBorder(Color.BLACK, 1);


        button1.setFont(buttonFont);
        button1.setForeground(buttonColor);
        button1.setBackground(Color.BLUE);
        button1.setPreferredSize(buttonSize);
        button1.setBorder(buttonBorder);


        JButton button2 = new JButton("Login");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonPanel);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }
}