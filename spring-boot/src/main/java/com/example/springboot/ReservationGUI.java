package com.example.springboot;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

public class ReservationGUI {

    private static DefaultTableModel loadCSV() {
        String[] columnNames = {"Room Number", "Cost", "Room Type", "# of Beds", "Quality Level", "Bed Type", "Smoking Allowed"};
        Object[][] data = {{"101", "Occupied"}, {"102", "Vacant"}, {"103", "Occupied"}};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        model.setRowCount(0); // remove table
        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/RoomsAvailable.csv")))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                Vector<Object> correction = new Vector<>();
                correction.addAll(Arrays.asList(row).subList(0, 7));
                model.addRow(correction);
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Issue with loading file: " + ex.getMessage());
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Issue with loading file: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Issue with loading file: " + ex.getMessage());
            ex.printStackTrace();
        }
        return model;
    }


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
        reservationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        reservationLabel.setFont(new Font("Arial", Font.BOLD, 35));
        reservationLabel.setForeground(Color.DARK_GRAY);
        reservationLabel.setBorder(new EmptyBorder(0, 40, 350, 0));

        DefaultTableModel model = loadCSV();

        // Create a JTable with the model
        JTable table = new JTable(model);

        table.setRowHeight(20);

        // Set font size
        table.setFont(new Font("Arial", Font.PLAIN, 12));

        // Set column width
        table.getColumnModel().getColumn(0).setPreferredWidth(75); // Room Number column
        table.getColumnModel().getColumn(1).setPreferredWidth(40); // Cost
        table.getColumnModel().getColumn(3).setPreferredWidth(35); // # of beds
        table.getColumnModel().getColumn(4).setPreferredWidth(50); // Quality Level
        table.getColumnModel().getColumn(5).setPreferredWidth(50); // Smoking

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        table.setPreferredScrollableViewportSize(new Dimension(600, 275));
        table.setFillsViewportHeight(true);

        tablePanel.add(scrollPane);
        tablePanel.setBorder(new EmptyBorder(75, 0, 0, 85));

        JPanel bottomButtons = new JPanel(new FlowLayout());

        JButton addReservation = new JButton("Add Reservation");
        JButton deleteReservation = new JButton("Delete Reservation");

        bottomButtons.add(addReservation);
        bottomButtons.add(deleteReservation);


        // Add the scroll pane to the panel

        reservationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        reservationLabel.setFont(new Font("Arial", Font.BOLD, 35));
        reservationLabel.setForeground(Color.DARK_GRAY);
        reservationLabel.setBorder(new EmptyBorder(-350, 60, 0, 0));

        contentPane.add(reservationLabel, BorderLayout.WEST);
        contentPane.add(topLogoPanel, BorderLayout.NORTH);

        contentPane.add(bottomButtons, BorderLayout.SOUTH);
        contentPane.add(tablePanel, BorderLayout.EAST);


        frame.setVisible(true);


    }

}
