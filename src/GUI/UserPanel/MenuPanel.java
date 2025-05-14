package GUI.UserPanel;

import GUI.MakeColor.*;
import GUI.giaodienadmin.QuanLyDeThi.ExamManagementPanel;
import GUI.giaodienadmin.QuanLyUser.UserManagementPanel;
import GUI.giaodienadmin.QuanLyCauHoi.QuestionManagementPanel;
import GUI.giaodienadmin.QuanLyCauHoi.PanelQuestionDetail;
import MICS.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
public class MenuPanel extends JPanel {
    private MainFrame mainFrame; // Tham chiếu đến MainFrame
    private boolean labelPrint;
     private JPanel contentPanel;


    public MenuPanel(MainFrame mainFrame,JPanel contentPanel) {
    this.mainFrame = mainFrame;
    this.contentPanel = contentPanel;
    labelPrint = false;
    init();
}

    
    private void init() {
        this.setBackground(Color.white);
        this.setBounds(0, 0, 214, 860);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new MatteBorder(0, 0, 0, 1, Color.lightGray));

        JLabel profile = new JLabel(AddImage.createImageIcon(Connect.img + "profile-user.png", 40, 40));
        profile.setMaximumSize(new Dimension(214, 80));
        profile.setText("Xin chào " + mainFrame.userBLL.getCurrent().getName() + "!");
        profile.setHorizontalAlignment(SwingConstants.LEFT);  
        profile.setFont(new Font("Arial", Font.BOLD, 14));
        profile.setOpaque(true);
        profile.setBackground(Color.lightGray);
        profile.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 0));
        this.add(profile);

        JButton home = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "homepage.png", 40, 40), e -> {
            mainFrame.addPanel(new HomePage(mainFrame, this), "HomePanel");
            mainFrame.showPanel("HomePanel");
        });
        home.setText("Trang chủ");
        home.setFont(new Font("Arial", Font.BOLD, 14));
        home.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        this.add(home);

        JLabel studentLabel = new JLabel("Sinh viên");
        studentLabel.setFont(new Font("Arial", Font.BOLD, 17));
        studentLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));

        JButton test = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "test.png", 40, 40), e -> {
            mainFrame.addPanel(new ExamSelect(mainFrame, this), "ExamSelect");
            mainFrame.showPanel("ExamSelect");
        });
        test.setText("Làm bài");
        test.setFont(new Font("Arial", Font.BOLD, 14));
        if(mainFrame.userBLL.getCurrent().getRole().getTakeExam()){
            this.add(studentLabel);
            this.add(test);
        }
            

        JLabel teacherLabel = new JLabel("Giảng viên");
        teacherLabel.setFont(new Font("Arial", Font.BOLD, 17));
        teacherLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        

        JButton analysis = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "analysis.png", 40, 40), e -> {
            mainFrame.addPanel(new ExamManagementPanel(this), "ExamManage");
            mainFrame.showPanel("ExamManage");
        });
        analysis.setText("Đề kiểm tra");
        analysis.setFont(new Font("Arial", Font.BOLD, 14));
        if(mainFrame.userBLL.getCurrent().getRole().getSeeExam()){
            this.add(teacherLabel);
            this.add(analysis);
        }

        JLabel manageLabel = new JLabel("Quản lý");
        manageLabel.setFont(new Font("Arial", Font.BOLD, 17));
        manageLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));

        JButton manageUser = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "user-manage.png", 40, 40), e -> {
            mainFrame.addPanel(new UserManagementPanel(this), "UserManage");
            mainFrame.showPanel("UserManage");
        });
        manageUser.setText("Người dùng");
        manageUser.setFont(new Font("Arial", Font.BOLD, 14));
        if(mainFrame.userBLL.getCurrent().getRole().getSeeUser()){
            if(!labelPrint){
                labelPrint=true;
                this.add(manageLabel);
            }
            this.add(manageUser);
        }
            

        
        

JButton manageQues = ButtonFactory.createClearButton(mainFrame, 214, 50,
    AddImage.createImageIcon(Connect.img + "question.png", 40, 40),
    e -> {
        QuestionManagementPanel questionManagementPanel = new QuestionManagementPanel(this,mainFrame.userBLL);
        mainFrame.addPanel(questionManagementPanel, "QuestionManage");
        mainFrame.showPanel("QuestionManage");
    });
        manageQues.setText("Câu hỏi");
        manageQues.setFont(new Font("Arial", Font.BOLD, 14));
        
        if(mainFrame.userBLL.getCurrent().getRole().getSeeQuest()){
            if(!labelPrint){
                labelPrint=true;
                this.add(manageLabel);
            }
            this.add(manageQues);
        }
            
        JButton manageRole = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "group.png", 40, 40), e -> {});
        manageRole.setText("Phân quyền");
        manageRole.setFont(new Font("Arial", Font.BOLD, 14));
        if(mainFrame.userBLL.getCurrent().getRole().getSeeRole()){
            if(!labelPrint){
                labelPrint=true;
                this.add(manageLabel);
            }
            this.add(manageRole);
        }
        
        JButton logoutButton = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "logout.png", 40, 40), e -> {
            mainFrame.addPanel(new LoginPanel(mainFrame), "LoginPanel");
            mainFrame.showPanel("LoginPanel");
        });
        this.add(Box.createVerticalGlue());
        logoutButton.setText("Đăng xuất");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(logoutButton, BorderLayout.SOUTH);
    }
}