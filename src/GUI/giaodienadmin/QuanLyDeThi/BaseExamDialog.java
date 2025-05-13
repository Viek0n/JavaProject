package GUI.giaodienadmin.QuanLyDeThi;

import DTO.ExamStructDTO;
import DTO.SubjectDTO;
import DAL.ExamStructDAL;
import DAL.SubjectDAL;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public abstract class BaseExamDialog extends JDialog {
    protected JTextField examIdField, examNameField, durationField;
    protected JTextArea descriptionArea, randomDetailsArea, selectedQuestionsArea;
    protected JComboBox<String> subjectComboBox;
    protected JButton saveButton, cancelButton;
    protected ExamManagementPanel parentPanel;
    protected ExamStructDAL examDAL;
    protected SubjectDAL subjectDAL;
    protected JDatePickerImpl startDatePicker, endDatePicker;

    public BaseExamDialog(ExamManagementPanel parentPanel, ExamStructDAL examDAL, String title) {
        this.parentPanel = parentPanel;
        this.examDAL = examDAL;
        this.subjectDAL = new SubjectDAL();
        initComponent(title);
    }

    protected void initComponent(String title) {
        setTitle(title);
        setSize(500, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setModal(true);

        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Form fields
        inputPanel.add(new JLabel("Exam ID:"));
        examIdField = new JTextField();
        examIdField.setToolTipText("Enter unique exam ID");
        inputPanel.add(examIdField);

        inputPanel.add(new JLabel("Exam Name:"));
        examNameField = new JTextField();
        examNameField.setToolTipText("Enter exam name (up to 100 characters)");
        inputPanel.add(examNameField);

        inputPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setToolTipText("Enter exam description (optional)");
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(descriptionArea));

        inputPanel.add(new JLabel("Subject:"));
        ArrayList<SubjectDTO> subjects = subjectDAL.getAll();
        String[] subjectNames = subjects.stream().map(subject -> subject.getID() + " - " + subject.getName()).toArray(String[]::new);
        subjectComboBox = new JComboBox<>(subjectNames);
        subjectComboBox.setToolTipText("Select subject");
        inputPanel.add(subjectComboBox);

        inputPanel.add(new JLabel("Start Date:"));
        UtilDateModel startDateModel = new UtilDateModel(new Date());
        Properties startDateProperties = new Properties();
        startDateProperties.put("text.today", "Today");
        startDateProperties.put("text.month", "Month");
        startDateProperties.put("text.year", "Year");
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, startDateProperties);
        startDatePicker = new JDatePickerImpl(startDatePanel, new ExamDialogUtils.DateLabelFormatter());
        startDatePicker.setToolTipText("Select start date (yyyy-MM-dd)");
        inputPanel.add(startDatePicker);

        inputPanel.add(new JLabel("End Date:"));
        UtilDateModel endDateModel = new UtilDateModel(new Date());
        Properties endDateProperties = new Properties();
        endDateProperties.put("text.today", "Today");
        endDateProperties.put("text.month", "Month");
        endDateProperties.put("text.year", "Year");
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, endDateProperties);
        endDatePicker = new JDatePickerImpl(endDatePanel, new ExamDialogUtils.DateLabelFormatter());
        endDatePicker.setToolTipText("Select end date (yyyy-MM-dd)");
        inputPanel.add(endDatePicker);

        inputPanel.add(new JLabel("Duration (HH:mm:ss):"));
        durationField = new JTextField();
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
        saveButton.setBackground(Color.decode("#27ae60"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.decode("#c0392b"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}