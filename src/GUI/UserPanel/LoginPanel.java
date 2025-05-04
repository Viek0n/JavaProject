package GUI.UserPanel;

import BLL.*;
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
    private JLabel buttonTitle;
    private JLabel buttonTitle2;
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
        loginTitle.setBounds(330, 10, 340, 60);

        mssvField = new RoundedTextField(45);
        mssvField.setFont(new Font("Arial", Font.PLAIN, 28));
        mssvField.setBounds(70, 180, 858, 56);
        mssvField.setBackground(Color.white);
        mssvField.setForeground(Color.black);
        mssvField.setCaretColor(Color.black);
        mssvField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mssvField.setOpaque(false);

        passField = new RoundedTextField(45);
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
                JButton LoginconfirmButton = new RoundedButton("", 20);
                LoginconfirmButton.setBackground(new Color(255, 0, 0, 100));
                buttonTitle.setVisible(false);
                buttonTitle2.setVisible(true);
                if (new UserBLL().login(mssv, password) == Enums.UserError.NORMAL) {
                    // Chuyển sang HomePanel
                    mainFrame.showPanel("HomePanel");
                }
            }});

        buttonTitle = new JLabel("Xác Nhận", SwingConstants.CENTER);
        buttonTitle.setFont(new Font("Arial", Font.BOLD, 38));
        buttonTitle.setBounds(375, 355, 250, 75);

        buttonTitle2 = new JLabel("Something Wrong", SwingConstants.CENTER);
        buttonTitle2.setFont(new Font("Arial", Font.BOLD, 38));
        buttonTitle2.setBounds(375, 355, 250, 75);
        buttonTitle2.setVisible(false);

        JLabel arrow = AddImage.createImageLabel(Connect.img + "arrow.png", 20,20,70,70);

        
        JButton backbutton = ButtonFactory.createClearButton(mainFrame,20, 20, 70, 70,b -> {
            LoginPanel newLoginPanel = new LoginPanel(mainFrame);

            // Thêm LoginPanel mới vào CardLayout
            mainFrame.addPanel(newLoginPanel, "LoginPanel");
    
            // Chuyển sang LoginPanel mới
            mainFrame.showPanel("LoginPanel");
        });
        loginPanel.add(arrow);
        loginPanel.add(backbutton);
        loginPanel.add(buttonTitle2);
        loginPanel.add(loginTitle);
        loginPanel.add(buttonTitle);
        loginPanel.add(mssvField);
        loginPanel.add(passField);
        loginPanel.add(confirmButton);

        add(loginPanel);
    }
}