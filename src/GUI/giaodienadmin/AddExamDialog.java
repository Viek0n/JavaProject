package GUI.giaodienadmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
public class AddExamDialog extends JDialog implements ActionListener {
    private JTextField examIdField, examNameField, startDateField, endDateField, durationField;
    private JButton saveButton, cancelButton;
    private ExemManagementPanel parentPanel;

    public AddExamDialog(ExemManagementPanel parentPanel) {
        this.parentPanel = parentPanel;
        initComponent();
    }

    private void initComponent() {
        this.setTitle("Add New Exam");
        this.setSize(400, 300);
        this.setLayout(new GridLayout(6, 2, 10, 10));
        this.setLocationRelativeTo(null);

        // Form fields
        this.add(new JLabel("Exam ID:"));
        examIdField = new JTextField();
        this.add(examIdField);

        this.add(new JLabel("Exam Name:"));
        examNameField = new JTextField();
        this.add(examNameField);

        this.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateField = new JTextField();
        this.add(startDateField);

        this.add(new JLabel("End Date (YYYY-MM-DD):"));
        endDateField = new JTextField();
        this.add(endDateField);

        this.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        this.add(durationField);

        // Buttons
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        this.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        this.add(cancelButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            // Collect data and add to the table
            String examId = examIdField.getText().trim();
            String examName = examNameField.getText().trim();
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime duration = LocalTime.parse(durationField.getText().trim(), formatter);
            
          
            if (examId.isEmpty() || examName.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || duration == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
                return;
            }

            // Add the new exam to the table in the parent panel
            parentPanel.addExamToTable(new Object[]{examId, examName, startDate, endDate, duration});
            JOptionPane.showMessageDialog(this, "Exam added successfully!");
            this.dispose();
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }
}