package com.example.springboot;


import javax.swing.*;
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
        logoLabel.setBorder(new EmptyBorder(20, 20, 0, 20));
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