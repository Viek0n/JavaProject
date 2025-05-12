package GUI.giaodienadmin.QuanLyDeThi;

import DTO.ExamStructDTO;
import DTO.SubjectDTO;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class EditExamDialog extends BaseExamDialog implements ActionListener {
    private JTextField examIdField, examNameField, descriptionField, durationField;
    private JButton saveButton, cancelButton;
    private ExamManagementPanel parentPanel;
    private ExamStructDAL examDAL;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    private ExamStructDTO examToEdit;

    public EditExamDialog(ExamManagementPanel parentPanel, ExamStructDTO examToEdit) {
        super(parentPanel, new ExamStructDAL(), "Edit Exam");
        this.parentPanel = parentPanel;
        this.examToEdit = examToEdit;
        this.examDAL = new ExamStructDAL();
        initComponent(); // Initialize UI components
    }

    private void initComponent() {
        setTitle("Edit Exam");
        setSize(400, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setModal(true);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Exam ID
        inputPanel.add(new JLabel("Exam ID:"));
        examIdField = new JTextField(examToEdit.getID());
        examIdField.setEditable(false);
        inputPanel.add(examIdField);

        // Exam Name
        inputPanel.add(new JLabel("Exam Name:"));
        examNameField = new JTextField(examToEdit.getName());
        examNameField.setToolTipText("Enter exam name (up to 100 characters)");
        inputPanel.add(examNameField);

        // Description
        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField(examToEdit.getDesc());
        descriptionField.setToolTipText("Enter exam description (optional)");
        inputPanel.add(descriptionField);

        // Start Date
        inputPanel.add(new JLabel("Start Date:"));
        UtilDateModel startDateModel = new UtilDateModel();
        startDateModel.setValue(examToEdit.getStart());
        startDateModel.setSelected(true);
        Properties dateProperties = new Properties();
        dateProperties.put("text.today", "Today");
        dateProperties.put("text.month", "Month");
        dateProperties.put("text.year", "Year");
        startDatePicker = new JDatePickerImpl(new JDatePanelImpl(startDateModel, dateProperties), new DateLabelFormatter());
        inputPanel.add(startDatePicker);

        // End Date
        inputPanel.add(new JLabel("End Date:"));
        UtilDateModel endDateModel = new UtilDateModel();
        endDateModel.setValue(examToEdit.getEnd());
        endDateModel.setSelected(true);
        endDatePicker = new JDatePickerImpl(new JDatePanelImpl(endDateModel, dateProperties), new DateLabelFormatter());
        inputPanel.add(endDatePicker);

        // Duration
        inputPanel.add(new JLabel("Duration (HH:mm:ss):"));
        durationField = new JTextField(examToEdit.getExamTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        durationField.setToolTipText("Enter duration (e.g., 01:00:00 or 1:00)");
        inputPanel.add(durationField);

         inputPanel.add(new JLabel("Random Details:"));
        randomDetailsArea = new JTextArea(3, 20);
        randomDetailsArea.setToolTipText("Enter random details (format: ChapID,Diff,Quantity; e.g., CH1,EASY,5;CH2,MEDIUM,3)");
        randomDetailsArea.setLineWrap(true);
        randomDetailsArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(randomDetailsArea));

        inputPanel.add(new JLabel("Selected Questions:"));
        selectedQuestionsArea = new JTextArea(3, 20);
        selectedQuestionsArea.setToolTipText("Enter selected question IDs (one per line)");
        selectedQuestionsArea.setLineWrap(true);
        selectedQuestionsArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(selectedQuestionsArea));

        // Buttons
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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
        String description = descriptionField.getText().trim();
        String durationStr = durationField.getText().trim();
        Date startDate = (Date) startDatePicker.getModel().getValue();
        Date endDate = (Date) endDatePicker.getModel().getValue();

        // Validate inputs
        if (examName.isEmpty() || startDate == null || endDate == null || durationStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!");
            return;
        }

        if (examName.length() > 100) {
            JOptionPane.showMessageDialog(this, "Exam name must be 100 characters or less.");
            return;
        }

        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, "End date must be after or equal to start date!");
            return;
        }

        // Parse duration
        LocalTime duration;
        try {
            duration = ExamDialogUtils.parseDuration(durationStr);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        // Parse subject
        String selectedSubject = (String) subjectComboBox.getSelectedItem();
        if (selectedSubject == null) {
            JOptionPane.showMessageDialog(this, "Please select a subject!");
            return;
        }
        String subjectID;
        try {
            subjectID = selectedSubject.split(" - ")[0];
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid subject format!");
            return;
        }
        SubjectDTO subject = subjectDAL.get(subjectID);
        if (subject == null) {
            JOptionPane.showMessageDialog(this, "Selected subject not found!");
            return;
        }

        // Update DTO
        ExamStructDTO examDTO = new ExamStructDTO();
        examDTO.setID(examId);
        examDTO.setName(examName);
        examDTO.setDesc(description.isEmpty() ? null : description); // Allow null description
        examDTO.setStart(startDate);
        examDTO.setEnd(endDate);
        examDTO.setExamTime(Time.valueOf(duration));
        examDTO.setSubject(subject);

        // Update database
        try {
            if (examDAL.update(examDTO)) {
                JOptionPane.showMessageDialog(this, "Exam updated successfully!");
                parentPanel.loadExamData();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update exam.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating exam: " + ex.getMessage());
            ex.printStackTrace(); // Log for debugging
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