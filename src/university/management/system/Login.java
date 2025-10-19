package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, cancel;
    JTextField tfusername;
    JPasswordField tfpassword;
    JComboBox<String> userType; // Admin / Student

    Login() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbluserType = new JLabel("Login As");
        lbluserType.setBounds(40, 20, 100, 20);
        add(lbluserType);

        userType = new JComboBox<>(new String[]{"Admin", "Student"});
        userType.setBounds(150, 20, 150, 20);
        add(userType);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 60, 100, 20);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 60, 150, 20);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 100, 100, 20);
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(150, 100, 150, 20);
        add(tfpassword);

        login = new JButton("Login");
        login.setBounds(40, 160, 120, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        login.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(180, 160, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 200, 200);
        add(image);

        setSize(600, 300);
        setLocation(500, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String role = (String) userType.getSelectedItem();
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            if (role.equals("Student")) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Are you already registered?",
                        "Student Login",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.NO_OPTION) {
                    // Redirect to registration
                    setVisible(false);
                    new StudentRegister();
                    return;
                }
            }

            try {
                Conn c = new Conn();
                String query;

                if (role.equals("Admin")) {
                    query = "SELECT * FROM login WHERE username='" + username + "' AND password='" + password + "'";
                } else {
                    query = "SELECT * FROM studentlogin WHERE username='" + username + "' AND password='" + password + "'";
                }

                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    setVisible(false);
                    if (role.equals("Admin")) {
                        new Project(); // full access
                    } else {
                        new StudentDashboard(username); // student limited access
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
