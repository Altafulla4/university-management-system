package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame implements ActionListener {

    JButton leave, exam, fee, logout;
    String username;

    StudentDashboard(String username) {
        this.username = username;

        setTitle("Student Dashboard");
        setSize(600, 400);
        setLocation(450, 200);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Welcome, " + username);
        heading.setBounds(200, 30, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        leave = new JButton("Apply Leave");
        leave.setBounds(100, 100, 150, 40);
        leave.addActionListener(this);
        add(leave);

        exam = new JButton("Examination");
        exam.setBounds(100, 160, 150, 40);
        exam.addActionListener(this);
        add(exam);

        fee = new JButton("Fee Structure");
        fee.setBounds(100, 220, 150, 40);
        fee.addActionListener(this);
        add(fee);

        logout = new JButton("Logout");
        logout.setBounds(100, 280, 150, 40);
        logout.addActionListener(this);
        add(logout);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == leave) {
            new StudentLeave();
        } else if (ae.getSource() == exam) {
            new ExaminationDetails(); // you can replace with your exam frame
        } else if (ae.getSource() == fee) {
            new FeeStructure(); // replace with your fee structure frame
        } else if (ae.getSource() == logout) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new StudentDashboard("Student");
    }
}
