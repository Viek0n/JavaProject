package GUI.giaodienadmin.QuanLyUser;

import DTO.UserDTO;
import DTO.RoleDTO;
import DAL.UserDAL;

import javax.swing.*;
import java.awt.*;

public class PanelAddUser extends JPanel {
    private JTextField loginNameField, nameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox, statusComboBox;
    private UserDAL userDAL;

    public PanelAddUser(UserDAL userDAL) {
        this.userDAL = userDAL;
        initializeFields();
    }

    private void initializeFields() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loginNameField = new JTextField();
        nameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(userDAL.getAll().toArray(new String[0])); // Assume this method exists
        statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        add(new JLabel("Login Name:"));
        add(loginNameField);

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Password:"));
        add(passwordField);

        add(new JLabel("Role:"));
        add(roleComboBox);

        add(new JLabel("Status:"));
        add(statusComboBox);
    }

    public void resetFields() {
        loginNameField.setText("");
        nameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
    }

    public boolean loadUserData(String userID) {
        UserDTO user = userDAL.getByLoginName(userID); // Assume this method exists
        if (user == null) {
            return false;
        }
        loginNameField.setText(user.getLoginName());
        nameField.setText(user.getName());
        roleComboBox.setSelectedItem(user.getRole().getName());
        statusComboBox.setSelectedItem(user.getStatus().name());
        passwordField.setText(""); // Do not show the password
        return true;
    }
}