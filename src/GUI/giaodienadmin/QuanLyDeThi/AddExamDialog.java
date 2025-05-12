package GUI.giaodienadmin.QuanLyDeThi;

import DTO.ExamStructDTO;
import DTO.SubjectDTO;
import DAL.ExamStructDAL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class AddExamDialog extends BaseExamDialog implements ActionListener {
    private static final Logger LOGGER = Logger.getLogger(AddExamDialog.class.getName());

    public AddExamDialog(ExamManagementPanel parentPanel, ExamStructDAL examDAL) {
        super(parentPanel, examDAL, "Add New Exam");
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            handleSaveAction();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    private void handleSaveAction() {
        String examId = examIdField.getText().trim();
        String examName = examNameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String durationStr = durationField.getText().trim();
        Date startDate = (Date) startDatePicker.getModel().getValue();
        Date endDate = (Date) endDatePicker.getModel().getValue();
        

        // Validate inputs
        if (examId.isEmpty() || examName.isEmpty() || startDate == null || endDate == null || durationStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!");
            return;
        }

        // Validate exam ID uniqueness
        if (examDAL.exists(examId)) {
            JOptionPane.showMessageDialog(this, "Exam ID already exists!");
            return;
        }

        // Validate exam name
        if (examName.length() > 100) {
            JOptionPane.showMessageDialog(this, "Exam name must be 100 characters or less.");
            return;
        }

        // Validate dates
        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, "End date must be after or equal to start date!");
            return;
        }

        // Validate duration
        LocalTime duration;
        try {
            duration = ExamDialogUtils.parseDuration(durationStr);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        // Parse selected subject
        String selectedSubject = (String) subjectComboBox.getSelectedItem();
        String subjectID = selectedSubject != null ? selectedSubject.split(" - ")[0] : null;
        SubjectDTO subject = subjectDAL.get(subjectID);
        // Create DTO
        ExamStructDTO examDTO = new ExamStructDTO();
        examDTO.setID(examId);
        examDTO.setName(examName);
        examDTO.setDesc(description.isEmpty() ? "No description provided" : description);
        examDTO.setStart(startDate);
        examDTO.setEnd(endDate);
        examDTO.setExamTime(Time.valueOf(duration));
        examDTO.setSubject(subject);

        // Insert into database asynchronously
        saveButton.setEnabled(false);
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return examDAL.add(examDTO);
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(AddExamDialog.this, "Exam added successfully!");
                        parentPanel.loadExamData();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddExamDialog.this, "Failed to add exam.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddExamDialog.this, "Error adding exam: " + ex.getMessage());
                } finally {
                    saveButton.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
}