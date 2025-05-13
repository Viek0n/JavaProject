package GUI.giaodienadmin.QuanLyUser;

import DTO.UserDTO;
import DTO.RoleDTO;
import DAL.UserDAL;
import DAL.RoleDAL;
import MICS.Enums.StatusValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUser extends JDialog implements ActionListener {
    private JTextField loginNameField, nameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox, statusComboBox;
    private JButton saveButton, cancelButton;
    private UserDAL userDAL;
    private RoleDAL roleDAL;
    private UserDTO userToEdit;

    public EditUser(Window owner, UserDTO user, UserDAL userDAL, RoleDAL roleDAL) {
        super(owner, "Edit User", ModalityType.APPLICATION_MODAL);
        this.userDAL = userDAL;
        this.userDAL = userDAL;
        this.roleDAL = roleDAL;

        initializeFields();
        populateFields();
        addEventListeners();

        setSize(400, 300);
        
    }

    private void initializeFields() {
        loginNameField = new JTextField(20);
        loginNameField.setEditable(false); // Login name should not be editable
        nameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        roleComboBox = new JComboBox<>(roleDAL.getAll().toArray(new String[0])); // Assume this method exists
        statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Login Name:"));
        inputPanel.add(loginNameField);

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        inputPanel.add(new JLabel("Role:"));
        inputPanel.add(roleComboBox);

        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

       setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateFields() {
        loginNameField.setText(userToEdit.getLoginName());
        nameField.setText(userToEdit.getName());
        roleComboBox.setSelectedItem(userToEdit.getRole().getName());
        statusComboBox.setSelectedItem(userToEdit.getStatus().name());
        passwordField.setText(""); // Do not show the password
    }

    private void addEventListeners() {
        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            handleSaveAction();
        }
    }

    private void handleSaveAction() {
        String name = nameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleComboBox.getSelectedItem();
        String status = (String) statusComboBox.getSelectedItem();

        // Validate required fields
        if (name.isEmpty() || (password.isEmpty() && userToEdit.getLoginName() == null)) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate password length
        if (!password.isEmpty() && password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Update user details
            userToEdit.setName(name);
            if (!password.isEmpty()) {
                userToEdit.setPass(password); // Assume password hashing is handled in UserDAL
            }
            RoleDTO roleDTO = roleDAL.getByName(role); // Fetch role by name
            userToEdit.setRole(roleDTO);
            userToEdit.setStatus(StatusValue.valueOf(status.toUpperCase()));

            // Save to database
            if (userDAL.update(userToEdit)) {
                JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update user!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}