package GUI.giaodienadmin.QuanLyPhanQuyen;

import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import BLL.RoleBLL;
import BLL.UserBLL;
import DTO.RoleDTO;
import DTO.UserDTO;
import MICS.Enums;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;

public class RolePanel extends JPanel {
    private JTextField txtId, txtName;
    private JCheckBox[] checkBox;
    private JButton btnSave;
    private boolean isEditMode = false; 
    private RoleBLL roleBLL;

    public RolePanel() {
        roleBLL = new RoleBLL();
        initComponent();
    }

    public RolePanel(int roleId) {
        roleBLL = new RoleBLL();
        initComponent();
        loadData(roleId);
    }

    private void initComponent() {
        this.setLayout(new GridLayout(13, 2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lbId = new JLabel("Mã nhóm quyền");
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setText(Integer.toString(roleBLL.getNextId()));

        JLabel lbName = new JLabel("Tên nhóm quyền");
        txtName = new JTextField();

        checkBox = new JCheckBox[10];
        JLabel[] lb = new JLabel[10];

        lb[0] = new JLabel("Xem câu hỏi");
        checkBox[0] = new JCheckBox();

        lb[1] = new JLabel("Thêm câu hỏi");
        checkBox[1] = new JCheckBox();

        lb[2] = new JLabel("Cập nhật câu hỏi");
        checkBox[2] = new JCheckBox();

        lb[3] = new JLabel("Xóa câu hỏi");
        checkBox[3] = new JCheckBox();

        lb[4] = new JLabel("Xem đề");
        checkBox[4] = new JCheckBox();

        lb[5] = new JLabel("Thêm đề");
        checkBox[5] = new JCheckBox();

        lb[6] = new JLabel("Sửa đề");
        checkBox[6] = new JCheckBox();

        lb[7] = new JLabel("Xóa đề");
        checkBox[7] = new JCheckBox();

        lb[8] = new JLabel("Tham gia bài kiểm tra");
        checkBox[8] = new JCheckBox();
        
        lb[9] = new JLabel("Quản trị");
        checkBox[9] = new JCheckBox();

        btnSave = new JButton("Lưu");
        btnSave.setBackground(Color.decode("#4caf50"));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> save());

        this.add(lbId);
        this.add(txtId);
        this.add(lbName);
        this.add(txtName);
        for(int i = 0; i < 10; i++){
            this.add(lb[i]);
            this.add(checkBox[i]);
        }
        this.add(new JLabel()); 
        this.add(btnSave);
    }

    public boolean loadData(int roleId) {
        RoleDTO role = roleBLL.get(roleId);

        isEditMode = true;
        txtId.setText(Integer.toString(role.getID()));
        txtName.setText(role.getName());
        Boolean[] permit = role.getPermit();
        for(int i = 0; i < 8; i++)
            checkBox[i].setSelected(permit[i]);
        checkBox[8].setSelected(role.getTakeExam());
        checkBox[9].setSelected(role.getAdmin());
        return true;
    }

    private void save() {
        String name = txtName.getText().trim();
        Boolean[] permit = new Boolean[8];
        for(int i = 0; i < 8; i++)
            permit[i] = checkBox[i].isSelected();

        if (name.isEmpty()) {
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

        RoleDTO role = new RoleDTO();
        role.setID(Integer.parseInt(txtId.getText()));
        role.setName(txtName.getText());
        role.setPermit(permit);
        role.setTakeExam(checkBox[8].isSelected());
        role.setAdmin(checkBox[9].isSelected());
        boolean success;
        if (isEditMode) {
            success = roleBLL.update(role);
        } else {
            success = roleBLL.add(role);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.getWindowAncestor(this).dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Thất bại", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
