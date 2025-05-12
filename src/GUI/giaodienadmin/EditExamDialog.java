package GUI.giaodienadmin;

import DTO.ExamStructDTO;
import DAL.ExamStructDAL;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.sql.Time;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class EditExamDialog extends JDialog implements ActionListener {
    private JTextField examIdField, examNameField, descriptionField, durationField;
    private JButton saveButton, cancelButton;
    private ExamManagementPanel parentPanel;
    private ExamStructDAL examDAL;
    private ArrayList<ExamStructDTO> list;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    private ExamStructDTO examToEdit;

    public EditExamDialog(ExamManagementPanel parentPanel, ExamStructDTO examToEdit) {
        this.examDAL = new ExamStructDAL();
        this.parentPanel = parentPanel;
        this.examToEdit = examToEdit;
        this.list = examDAL.getAll();
        initComponent();
    }

    private void initComponent() {
        this.setTitle("Edit Exam");
        this.setSize(400, 400);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setModal(true);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Form fields
        inputPanel.add(new JLabel("Exam ID:"));
        examIdField = new JTextField(examToEdit.getID());
        examIdField.setEditable(false); // ID is non-editable
        inputPanel.add(examIdField);

        inputPanel.add(new JLabel("Exam Name:"));
        examNameField = new JTextField(examToEdit.getName());
        examNameField.setToolTipText("Enter exam name (up to 100 characters)");
        inputPanel.add(examNameField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField(examToEdit.getDesc());
        descriptionField.setToolTipText("Enter exam description (optional)");
        inputPanel.add(descriptionField);

        // Start Date Picker
        inputPanel.add(new JLabel("Start Date:"));
        UtilDateModel startDateModel = new UtilDateModel();
        startDateModel.setValue(examToEdit.getStart());
        startDateModel.setSelected(true);
        Properties startDateProperties = new Properties();
        startDateProperties.put("text.today", "Today");
        startDateProperties.put("text.month", "Month");
        startDateProperties.put("text.year", "Year");
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, startDateProperties);
        startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        inputPanel.add(startDatePicker);

        // End Date Picker
        inputPanel.add(new JLabel("End Date:"));
        UtilDateModel endDateModel = new UtilDateModel();
        endDateModel.setValue(examToEdit.getEnd());
        endDateModel.setSelected(true);
        Properties endDateProperties = new Properties();
        endDateProperties.put("text.today", "Today");
        endDateProperties.put("text.month", "Month");
        endDateProperties.put("text.year", "Year");
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, endDateProperties);
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
        inputPanel.add(endDatePicker);

        inputPanel.add(new JLabel("Duration (HH:mm:ss):"));
        durationField = new JTextField(examToEdit.getExamTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        durationField.setToolTipText("Enter duration (e.g., 01:00:00 or 1:00)");
        inputPanel.add(durationField);

        // Buttons
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            handleSaveAction();
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }

    private LocalTime parseDuration(String durationStr) {
        try {
            DateTimeFormatter strictFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return LocalTime.parse(durationStr, strictFormatter);
        } catch (Exception e1) {
            try {
                DateTimeFormatter flexibleFormatter = DateTimeFormatter.ofPattern("[H][:mm][:ss]");
                return LocalTime.parse(durationStr, flexibleFormatter);
            } catch (Exception e2) {
                throw new IllegalArgumentException("Invalid time format. Please use HH:mm:ss or H:mm.");
            }
        }
    }

    private void handleSaveAction() {
        String examId = examIdField.getText().trim();
        String examName = examNameField.getText().trim();
        String description = descriptionField.getText().trim();
        String durationStr = durationField.getText().trim();
        Date startDate = (Date) startDatePicker.getModel().getValue();
        Date endDate = (Date) endDatePicker.getModel().getValue();

        // Validate inputs
        if (examName.isEmpty() || startDate == null || endDate == null || durationStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!");
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
            duration = parseDuration(durationStr);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        // Update DTO
        ExamStructDTO examDTO = new ExamStructDTO();
        examDTO.setID(examId);
        examDTO.setName(examName);
        examDTO.setDesc(description.isEmpty() ? "No description provided" : description);
        examDTO.setStart(startDate);
        examDTO.setEnd(endDate);
        examDTO.setExamTime(Time.valueOf(duration));

        // Update database
        try {
            if (examDAL.update(examDTO)) {
                JOptionPane.showMessageDialog(this, "Exam updated successfully!");
                parentPanel.loadExamData();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update exam.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating exam: " + ex.getMessage());
        }
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                } else if (value instanceof Date) {
                    return dateFormatter.format(value);
                }
            }
            return "";
        }
    }
}