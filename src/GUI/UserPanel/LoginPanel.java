package GUI.UserPanel;

import GUI.MakeColor.*;
import MICS.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {
    private JTextField mssvField;
    private JTextField passField;
    private MainFrame mainFrame; // Tham chiếu đến MainFrame

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        // Khung trắng
        RoundedPanel loginPanel = new RoundedPanel(100);
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(300, 211, 1000, 500);

        JLabel loginTitle = new JLabel("Đăng Nhập", SwingConstants.CENTER);
        loginTitle.setFont(new Font("Inter", Font.BOLD, 50));
        loginTitle.setBounds(330, 40, 340, 60);
        loginTitle.setForeground(Color.BLACK);
        loginTitle.setIcon(AddImage.createImageIcon(Connect.img + "graduation.png", 20,20,70,70));

        mssvField = new TextFieldWithIcon(30,AddImage.createImageIcon(Connect.img + "user.png", 20,20,45,45));
        mssvField.setFont(new Font("Arial", Font.PLAIN, 28));
        mssvField.setBounds(70, 150, 858, 56);
        mssvField.setBackground(Color.white);
        mssvField.setForeground(Color.black);
        mssvField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mssvField.setOpaque(false);

        passField = new TextFieldWithIcon(30,AddImage.createImageIcon(Connect.img + "lock.png", 20,20,45,45));
        passField.setFont(new Font("Arial", Font.PLAIN, 28));
        passField.setBounds(70, 267, 858, 56);
        passField.setBackground(Color.white);
        passField.setForeground(Color.black);
        passField.setCaretColor(Color.black);
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passField.setOpaque(false);

        JButton confirmButton =  ButtonFactory.createConfirmButton(mainFrame,20,375,355,250,75,Ulti.BananaLeaf,a -> {
            String mssv = mssvField.getText();
            String password = passField.getText();

            if (mssv.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                switch(mainFrame.userBLL.login(mssv, password)){
                    case Enums.UserError.NORMAL:
                                    // Tạo một LoginPanel mới
                    UserList newUserList = new UserList(mainFrame);

                    // Thêm LoginPanel mới vào CardLayout
                    mainFrame.addPanel(newUserList, "UserListPanel");

                    // Chuyển sang LoginPanel mới
                    mainFrame.showPanel("HomePanel");
                    break;

                    case Enums.UserError.LOCKED:
                        JOptionPane.showMessageDialog(this, "Người dùng đã bị khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;

                    case Enums.UserError.NOUSER:
                        JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;

                    case Enums.UserError.WRONGPASS:
                        JOptionPane.showMessageDialog(this, "Sai mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }});
            
        confirmButton.setText("Đăng Nhập");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 38));
        confirmButton.setForeground(Color.BLACK);

        loginPanel.add(loginTitle);
        loginPanel.add(mssvField);
        loginPanel.add(passField);
        loginPanel.add(confirmButton);

        add(loginPanel);
    }
}