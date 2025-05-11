package GUI.giaodienadmin;

import DTO.UserDTO;

import DTO.RoleDTO;
import DAL.UserDAL;
import DAL.RoleDAL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PanelAddUser extends JPanel {
    private JTextField txtLoginName, txtName;
    private JComboBox<String> cmbStatus, cmbRole;
    private JButton btnSave;
    private RoleDAL roleDAL;
    private UserDAL userDAL;
    private boolean isEditMode = false; // true nếu là sửa, false nếu là thêm

    public PanelAddUser() {
        roleDAL = new RoleDAL();
        userDAL = new UserDAL();
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new GridLayout(5, 2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblLoginName = new JLabel("Login Name:");
        txtLoginName = new JTextField();

        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField();

        JLabel lblStatus = new JLabel("Status:");
        cmbStatus = new JComboBox<>(new String[]{"ACTIVE", "INACTIVE"});

        JLabel lblRole = new JLabel("Role:");
        cmbRole = new JComboBox<>();
        loadRolesToComboBox();

        btnSave = new JButton("Save");
        btnSave.setBackground(Color.decode("#4caf50"));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveUser());

        this.add(lblLoginName); this.add(txtLoginName);
        this.add(lblName);      this.add(txtName);
        this.add(lblStatus);    this.add(cmbStatus);
        this.add(lblRole);      this.add(cmbRole);
        this.add(new JLabel()); this.add(btnSave);
    }

   private void loadRolesToComboBox() {
        List<UserDTO> users = userDAL.getAll();
        cmbRole.removeAllItems();
        for (UserDTO user : users) {
            cmbRole.addItem(user.getName());
        }
    }

    public boolean loadUserData(String userID) {
        UserDTO user = userDAL.getByLoginName(userID);
        if (user == null) return false;

        isEditMode = true;
        txtLoginName.setText(user.getLoginName());
        txtLoginName.setEnabled(false); // không cho sửa ID

        txtName.setText(user.getName());
        cmbStatus.setSelectedItem(user.getStatus().name());
        cmbRole.setSelectedItem(user.getRole().getName());
        return true;
    }

    private void saveUser() {
        String loginName = txtLoginName.getText().trim();
        String name = txtName.getText().trim();
        String statusStr = (String) cmbStatus.getSelectedItem();
        String roleName = (String) cmbRole.getSelectedItem();

        if (loginName.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDTO user = new UserDTO();
        user.setLoginName(loginName);
        user.setName(name);
        user.setStatus(UserDTO.Status.valueOf(statusStr));
        RoleDTO role = roleDAL.getByName(roleName);

        boolean success;
        if (isEditMode) {
            success = userDAL.update(user);
        } else {
            success = userDAL.add(user);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "User saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.getWindowAncestor(this).dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
