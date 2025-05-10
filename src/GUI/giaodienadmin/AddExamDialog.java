package GUI.giaodienadmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddExamDialog extends JDialog implements ActionListener {
    private JTextField examIdField, examNameField, startDateField, endDateField, durationField;
    private JButton saveButton, cancelButton;
    private ExemManagementPanel parentPanel;

    // Define a constant for the date format
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AddExamDialog(ExemManagementPanel parentPanel) {
        this.parentPanel = parentPanel;
        initComponent();
    }

    private void initComponent() {
        this.setTitle("Add New Exam");
        this.setSize(400, 300);
        this.setLayout(new GridLayout(6, 2, 10, 10));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Add this line

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

        //Set default button.
        this.getRootPane().setDefaultButton(saveButton);
        //Request focus to examIdField when the dialog is shown.
        examIdField.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            // Collect data and add to the table
            String examId = examIdField.getText().trim();
            String examName = examNameField.getText().trim();
            String startDateStr = startDateField.getText().trim();
            String endDateStr = endDateField.getText().trim();
            String durationStr = durationField.getText().trim();

            if (examId.isEmpty() || examName.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty() || durationStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
                return;
            }

            try {
                // Parse dates using the defined formatter
                LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
                LocalDate endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
                int duration = Integer.parseInt(durationStr);

                // Validate the data
                if (endDate.isBefore(startDate)) {
                    JOptionPane.showMessageDialog(this, "End date cannot be before start date!");
                    return;
                }
                if (duration <= 0) {
                    JOptionPane.showMessageDialog(this, "Duration must be a positive number!");
                    return;
                }

                // Add the new exam to the table in the parent panel.  Pass the parsed date Strings.
                parentPanel.addExamToTable(new Object[]{examId, examName, startDateStr, endDateStr, duration});
                JOptionPane.showMessageDialog(this, "Exam added successfully!");
                this.dispose(); // Close the dialog
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid duration. Please enter a valid number.");
            }
        } else if (e.getSource() == cancelButton) {
            this.dispose(); // Close the dialog
        }
    }
}

