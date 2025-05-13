package GUI.giaodienadmin.QuanLyDeThi;

import DTO.ExamStructDTO;
import DTO.SubjectDTO;
import DAL.ExamStructDAL;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Pattern;
public class EditExamDialog extends BaseExamDialog implements ActionListener {
    private static final Logger LOGGER = Logger.getLogger(AddExamDialog.class.getName());

    public EditExamDialog(ExamManagementPanel parentPanel, ExamStructDAL examDAL) {
        super(parentPanel, examDAL, "Edit Exam");
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
    // Initialize logger
    Logger logger = Logger.getLogger(getClass().getName());

    // Get input values
    String examName = examNameField.getText().trim();
    String description = descriptionArea.getText().trim();
    String durationStr = durationField.getText().trim();
    Date startDate = (Date) startDatePicker.getModel().getValue();
    Date endDate = (Date) endDatePicker.getModel().getValue();

    // Validate inputs
    if (examName.isEmpty() || startDate == null || endDate == null || durationStr.isEmpty() || subjectComboBox.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Please fill in all required fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
        logger.warning("Validation failed: Missing required fields");
        return;
    }

    // Validate exam name length and format
    if (examName.length() > 100) {
        JOptionPane.showMessageDialog(this, "Exam name must be 100 characters or less.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        logger.warning("Validation failed: Exam name too long");
        return;
    }
    if (!Pattern.matches("^[a-zA-Z0-9\\s-_]+$", examName)) {
        JOptionPane.showMessageDialog(this, "Exam name can only contain letters, numbers, spaces, hyphens, or underscores.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        logger.warning("Validation failed: Invalid characters in exam name");
        return;
    }

    // Check for duplicate exam name (excluding current exam)
   

    // Validate dates
    if (startDate.after(endDate)) {
        JOptionPane.showMessageDialog(this, "End date must be after or equal to start date!", "Validation Error", JOptionPane.ERROR_MESSAGE);
        logger.warning("Validation failed: Invalid date range");
        return;
    }

    // Validate duration
    LocalTime duration;
    try {
        duration = LocalTime.parse(durationStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
        if (duration.toSecondOfDay() < 300) { // Minimum 5 minutes
            JOptionPane.showMessageDialog(this, "Exam duration must be at least 5 minutes.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            logger.warning("Validation failed: Duration too short");
            return;
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Invalid duration format. Use HH:mm:ss.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        logger.warning("Validation failed: Invalid duration format - " + durationStr);
        return;
    }

    // Parse selected subject
    String selectedSubject = (String) subjectComboBox.getSelectedItem();
    String subjectID = selectedSubject != null ? selectedSubject.split(" - ")[0] : null;
    SubjectDTO subject = subjectDAL.get(subjectID);
    if (subject == null) {
        JOptionPane.showMessageDialog(this, "Invalid subject selected.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        logger.warning("Validation failed: Invalid subject - " + subjectID);
        return;
    }

    // Confirmation dialog
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to save changes to this exam?", "Confirm Save", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        logger.info("User cancelled save operation");
        return;
    }

    // Update DTO
    ExamStructDTO examDTO = new ExamStructDTO();
    examDTO.setID(examDAL.getNextId());
    examDTO.setName(examName);
    examDTO.setDesc(description.isEmpty() ? null : description);
    examDTO.setStart(startDate); // Consider converting to UTC or storing timezone
    examDTO.setEnd(endDate);
    examDTO.setExamTime(Time.valueOf(duration));
    examDTO.setSubject(subject);

    // Add audit information (assuming user context is available)
   // examDTO.setLastUpdatedBy(); // Implement getCurrentUserID() as needed
    //examDTO.setLastUpdatedAt(new Date());

    // Show progress indicator
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
        // Update database
        if (examDAL.update(examDTO)) {
            JOptionPane.showMessageDialog(this, "Exam updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            logger.info("Exam updated successfully: " + examName + " (ID: " + examDTO.getID() + ")");
            parentPanel.loadExamData();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update exam.", "Error", JOptionPane.ERROR_MESSAGE);
            logger.severe("Failed to update exam: " + examName);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error updating exam: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        logger.severe("Exception while updating exam: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        // Reset cursor
        setCursor(Cursor.getDefaultCursor());
    }
}}