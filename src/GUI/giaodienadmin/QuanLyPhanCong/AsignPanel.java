package GUI.giaodienadmin.QuanLyPhanCong;

import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import BLL.RoleBLL;
import BLL.SubjectBLL;
import BLL.TaskBLL;
import BLL.UserBLL;
import DTO.RoleDTO;
import DTO.SubjectDTO;
import DTO.UserDTO;
import MICS.Enums;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class AsignPanel extends JPanel{
    private JComboBox<String> cmbUser, cmbSubject;
    private JButton btnSave;
    private SubjectBLL subjectBLL;
    private UserBLL userBLL;
    private TaskBLL taskBLL;

    public AsignPanel() {
        userBLL = new UserBLL();
        subjectBLL = new SubjectBLL();
        taskBLL = new TaskBLL();
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new GridLayout(3, 2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lbUser = new JLabel("Người dùng:");
        cmbUser = new JComboBox<>();

        JLabel lbSubject = new JLabel("Môn:");
        cmbSubject = new JComboBox<>();
        loadSubject();

        btnSave = new JButton("Lưu");
        btnSave.setBackground(Color.decode("#4caf50"));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> save());

        this.add(lbUser);    
        this.add(cmbUser);
        this.add(lbSubject);      
        this.add(cmbSubject);
        this.add(new JLabel()); 
        this.add(btnSave);
        loadSubject();
        loadUser();
    }

   private void loadSubject() {
        List<SubjectDTO> subjects = subjectBLL.getAll();
        cmbSubject.removeAllItems();
        for (SubjectDTO subject : subjects) {
            cmbSubject.addItem(subject.getID() + " - " + subject.getName());
        }
    }

    public void loadUser() {
        List<UserDTO> users = userBLL.getAll();
        cmbUser.removeAllItems();
        for (UserDTO user : users) {
            cmbUser.addItem(user.getLoginName() + " - " + user.getName());
        }
    }

    private void save() {
        String user = (String)cmbUser.getSelectedItem();
        String subject = (String)cmbSubject.getSelectedItem();
        boolean success = taskBLL.add(user.split(" - ")[0], subject.split(" - ")[0]);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.getWindowAncestor(this).dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Thất bại", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
