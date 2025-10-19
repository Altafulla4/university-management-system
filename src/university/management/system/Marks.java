package university.management.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Marks extends JFrame implements ActionListener {

    String rollno;
    JButton cancel, showResult;
    JComboBox<String> semesterDropdown;
    JTable marksTable;
    JScrollPane sp;

    Marks(String rollno) {
        this.rollno = rollno;

        setSize(800, 600);
        setLocation(500, 100);
        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Visveswarya Technological University Belagavi.");
        heading.setBounds(100, 10, 600, 25);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel subheading = new JLabel("Result of Examination 2022");
        subheading.setBounds(100, 50, 500, 20);
        subheading.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(subheading);

        JLabel lblrollno = new JLabel("Roll Number: " + rollno);
        lblrollno.setBounds(60, 100, 500, 20);
        lblrollno.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblrollno);

        semesterDropdown = new JComboBox<>();
        semesterDropdown.setBounds(60, 130, 200, 30);
        add(semesterDropdown);

        showResult = new JButton("Show Result");
        showResult.setBounds(280, 130, 150, 30);
        showResult.addActionListener(this);
        add(showResult);

        sp = new JScrollPane();
        sp.setBounds(60, 200, 600, 300);
        add(sp);

        cancel = new JButton("Back");
        cancel.setBounds(350, 520, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);

        loadSemesters();

        setVisible(true);
    }

    private void loadSemesters() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT DISTINCT semester FROM marks WHERE rollno = '" + rollno + "'");
            while (rs.next()) {
                semesterDropdown.addItem(rs.getString("semester"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResult(String semester) {
        try {
            Conn c = new Conn();

            // Fetch subjects for the selected semester
            ResultSet rs1 = c.s.executeQuery("SELECT * FROM subject WHERE rollno = '" + rollno + "' AND semester = '" + semester + "'");
            ArrayList<String> subjects = new ArrayList<>();

            if (rs1.next()) {
                for (int i = 1; i <= 5; i++) {
                    String subject = rs1.getString("subject" + i);  // Assuming columns are subject1, subject2, subject3, etc.
                    if (subject != null && !subject.isEmpty()) {
                        subjects.add(subject);
                    }
                }
            }

            // Fetch marks for the selected semester
            ResultSet rs2 = c.s.executeQuery("SELECT * FROM marks WHERE rollno = '" + rollno + "' AND semester = '" + semester + "'");
            ArrayList<String> marks = new ArrayList<>();

            if (rs2.next()) {
                for (int i = 1; i <= subjects.size(); i++) {
                    String mark = rs2.getString("marks" + i);  // Assuming columns are marks1, marks2, marks3, etc.
                    marks.add(mark != null ? mark : "N/A");
                }
            }

            // Display data in a table
            String[] columns = {"Subject", "Marks"};
            Object[][] data = new Object[subjects.size()][2];

            for (int i = 0; i < subjects.size(); i++) {
                data[i][0] = subjects.get(i);
                data[i][1] = marks.size() > i ? marks.get(i) : "N/A";
            }

            marksTable = new JTable(data, columns);
            marksTable.setFont(new Font("serif", Font.PLAIN, 18));
            marksTable.setRowHeight(30);

            sp.setViewportView(marksTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == showResult) {
            String semester = (String) semesterDropdown.getSelectedItem();
            showResult(semester);
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Marks("12345");  // Replace with a valid roll number to test
    }
}
