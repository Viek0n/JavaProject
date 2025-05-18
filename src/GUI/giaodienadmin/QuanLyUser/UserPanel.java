package GUI.giaodienadmin.QuanLyUser;

import BLL.UserBLL;
import BLL.RoleBLL;
import DTO.RoleDTO;
import DTO.UserDTO;
import MICS.*;
import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;

public class UserPanel extends JPanel {
    private JTextField txtLoginName, txtName, txtPass;
    private JComboBox<String> cmbStatus, cmbRole;
    private JButton btnSave;
    private RoleBLL roleBLL;
    private UserBLL userBLL;
    private boolean isEditMode = false; // true nếu là sửa, false nếu là thêm

    public UserPanel() {
        roleBLL = new RoleBLL();
        userBLL = new UserBLL();
        initComponent();
    }

    public UserPanel(String userId) {
        roleBLL = new RoleBLL();
        userBLL = new UserBLL();
        initComponent();
        loadUserData(userId);
    }

    private void initComponent() {
        this.setLayout(new GridLayout(6, 2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblLoginName = new JLabel("Tên đăng nhập:");
        txtLoginName = new JTextField();

        JLabel lblName = new JLabel("Tên người dùng:");
        txtName = new JTextField();

        JLabel lblPass = new JLabel("Mật khẩu:");
        txtPass = new JTextField("12345");

        JLabel lblStatus = new JLabel("Trạng thái:");
        cmbStatus = new JComboBox<>(new String[]{"HOATDONG", "KHOA"});

        JLabel lblRole = new JLabel("Nhóm quyền:");
        cmbRole = new JComboBox<>();
        loadRolesToComboBox();

        btnSave = new JButton("Lưu");
        btnSave.setBackground(Color.decode("#4caf50"));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveUser());

        this.add(lblLoginName); this.add(txtLoginName);
        this.add(lblName);      this.add(txtName);
        this.add(lblPass);      this.add(txtPass);
        this.add(lblStatus);    this.add(cmbStatus);
        this.add(lblRole);      this.add(cmbRole);
        this.add(new JLabel()); this.add(btnSave);
    }

   private void loadRolesToComboBox() {
        List<RoleDTO> roles = roleBLL.getAll();
        cmbRole.removeAllItems();
        for (RoleDTO role : roles) {
            cmbRole.addItem(role.getName());
        }
    }

    public boolean loadUserData(String userID) {
        UserDTO user = userBLL.getByLoginName(userID);
        if (user == null) return false;

        isEditMode = true;
        txtLoginName.setText(user.getLoginName());
        txtLoginName.setEnabled(false); // không cho sửa ID

        txtName.setText(user.getName());
        cmbStatus.setSelectedItem(user.getStatus().name());
        cmbRole.setSelectedItem(user.getRole().getName());
        txtPass.setText(user.getPass());
        return true;
    }

    private void saveUser() {
        String loginName = txtLoginName.getText().trim();
        String name = txtName.getText().trim();
        String statusStr = (String) cmbStatus.getSelectedItem();
        String roleName = (String) cmbRole.getSelectedItem();
        String pass = txtPass.getText().trim();

        if (loginName.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Pattern.matches("^[0-9\\s-_]+$", name)) {
            JOptionPane.showMessageDialog(this, "Tên chỉ có thể là chữ cái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chứ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        UserDTO user = new UserDTO(loginName, name, pass, Enums.StatusValue.valueOf(statusStr), roleBLL.getByName(roleName));
        boolean success;
        if (isEditMode) {
            success = userBLL.update(user);
        } else {
            success = userBLL.add(user);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.getWindowAncestor(this).dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Thất bại", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
