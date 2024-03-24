package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReservationGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hotel Bearifonia");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel topLogoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ImageIcon icon = new ImageIcon("src/main/resources/bear_logo.png");
        JLabel logoLabel = new JLabel(icon);
        logoLabel.setBorder(new EmptyBorder(20, 20, 0, 20));
        topLogoPanel.add(logoLabel);

        JLabel hotelBeariforniaLabel = new JLabel("Hotel Bearifornia");
        hotelBeariforniaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hotelBeariforniaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        hotelBeariforniaLabel.setForeground(Color.DARK_GRAY);
        topLogoPanel.add(hotelBeariforniaLabel, BorderLayout.CENTER);

        JLabel reservationLabel = new JLabel("Reservations: ");
        reservationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        reservationLabel.setFont(new Font("Arial", Font.BOLD, 35));
        reservationLabel.setForeground(Color.DARK_GRAY);
        reservationLabel.setBorder(new EmptyBorder(-350, 60, 0, 0));

        contentPane.add(topLogoPanel, BorderLayout.NORTH);

        contentPane.add(reservationLabel, BorderLayout.WEST);

        frame.setVisible(true);


    }


}
