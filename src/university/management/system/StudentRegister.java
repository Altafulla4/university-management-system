package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentRegister extends JFrame implements ActionListener {

    JTextField tfusername, tfpassword, tfrollno;
    JButton register, cancel;

    StudentRegister() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Student Registration");
        heading.setBounds(100, 30, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel lblrollno = new JLabel("Roll Number:");
        lblrollno.setBounds(60, 100, 100, 25);
        add(lblrollno);

        tfrollno = new JTextField();
        tfrollno.setBounds(180, 100, 150, 25);
        add(tfrollno);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(60, 150, 100, 25);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(180, 150, 150, 25);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password:");
        lblpassword.setBounds(60, 200, 100, 25);
        add(lblpassword);

        tfpassword = new JTextField();
        tfpassword.setBounds(180, 200, 150, 25);
        add(tfpassword);

        register = new JButton("Register");
        register.setBounds(60, 270, 120, 30);
        register.setBackground(Color.BLACK);
        register.setForeground(Color.WHITE);
        register.addActionListener(this);
        add(register);

        cancel = new JButton("Cancel");
        cancel.setBounds(200, 270, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);

        setSize(400, 400);
        setLocation(600, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == register) {
            String rollno = tfrollno.getText();
            String username = tfusername.getText();
            String password = tfpassword.getText();

            if (rollno.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            try {
                Conn c = new Conn();
                String checkQuery = "SELECT * FROM studentlogin WHERE username='" + username + "'";
                ResultSet rs = c.s.executeQuery(checkQuery);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Username already exists!");
                    return;
                }

                String query = "INSERT INTO studentlogin VALUES('" + username + "', '" + password + "', '" + rollno + "')";
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Registration Successful!");
                setVisible(false);
                new Login();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new StudentRegister();
    }
}
