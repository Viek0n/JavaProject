package GUI.UserPanel;

import GUI.MakeColor.*;
import GUI.UserPanel.LichSu.ExamHistoryPanel;
import GUI.giaodienadmin.QuanLyCauHoi.QuestionManagementPanel;
import GUI.giaodienadmin.QuanLyDeThi.ExamStructManagementPanel;
import GUI.giaodienadmin.QuanLyMonHoc.SubjectManagementPanel;
import GUI.giaodienadmin.QuanLyPhanCong.AsignManagementPanel;
import GUI.giaodienadmin.QuanLyPhanQuyen.RoleManagementPanel;
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
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
public class MenuPanel extends JPanel {
    public MainFrame mainFrame; // Tham chiếu đến MainFrame
    private boolean[] labelPrint;
    private JButton test, testHistory, feedback, exam, manageUser ,asignUser, manageSubject, manageQues, manageRole;
    private JButton selectedButton = null;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        labelPrint = new boolean[4];
        for(int i = 0; i < 4; i++)
            labelPrint[i] = false;
        init();
    }

    private void init() {
        this.setBackground(Color.white);
        this.setBounds(0, 0, 214, 860);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new MatteBorder(0, 0, 0, 2, Ulti.LightGray));

        JLabel studentLabel = createLabel("Sinh viên");
        JLabel teacherLabel = createLabel("Giảng viên");
        JLabel manageLabel = createLabel("Quản lý");
        JLabel adminLabel = createLabel("Quản trị");

        JLabel profile = new JLabel(AddImage.createImageIcon(Connect.img + "profile-user.png", 40, 40));
        profile.setMaximumSize(new Dimension(214, 80));
        profile.setText("Xin chào " + mainFrame.userBLL.getCurrent().getName() + "!");
        profile.setHorizontalAlignment(SwingConstants.LEFT);  
        profile.setFont(new Font("Arial", Font.BOLD, 14));
        profile.setOpaque(true);
        profile.setBackground(Ulti.LightGray);
        profile.setBorder(BorderFactory.createEmptyBorder(18, 15, 18, 0));
        this.add(profile);

        JButton home = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "homepage.png", 40, 40));
        home.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setBackground(Color.LIGHT_GRAY);
                selectedButton.setForeground(Color.BLACK);
            }
            home.setForeground(Color.RED);
            selectedButton = home;
            mainFrame.addPanel(new HomePage(mainFrame, this), "HomePanel");
            mainFrame.showPanel("HomePanel");
        });
        home.setText("Trang chủ");
        home.setFont(new Font("Arial", Font.BOLD, 14));
        home.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        this.add(home);

        test = createButton(mainFrame, "test.png", () -> new ExamSelect(mainFrame, this), "ExamSelect", "Làm bài");
        testHistory = createButton(mainFrame, "test-history.png", () -> new ExamHistoryPanel( this), "ExamHistory", "Lịch sử làm bài");
        feedback = createButton(mainFrame, "feedback.png", () -> new ExamStructManagementPanel(this), "FeedbackManage", "Phản hồi");
        exam = createButton(mainFrame, "analysis.png", () -> new ExamStructManagementPanel(this), "ExamManage", "Đề kiểm tra");
        manageUser = createButton(mainFrame, "user-manage.png", () -> new UserManagementPanel(this), "UserManage", "Người dùng");
        asignUser = createButton(mainFrame, "assign.png", () -> new AsignManagementPanel(this), "AsignManagement", "Phân công");
        manageSubject = createButton(mainFrame, "subject.png", () -> new SubjectManagementPanel(this), "SubjectManage", "Môn học");
        manageQues = createButton(mainFrame, "question.png", () -> new QuestionManagementPanel(this), "QuestionManage", "Câu hỏi");
        manageRole = createButton(mainFrame, "group.png", () -> new RoleManagementPanel(this), "RoleManage", "Phân quyền");

        if(mainFrame.userBLL.getCurrent().getRole().getTakeExam()){
            if(!labelPrint[0]){
                this.add(studentLabel);
                labelPrint[0] = true;
            }
            this.add(test);
            this.add(testHistory);
        }
        
        if(mainFrame.userBLL.getCurrent().getRole().getSeeExam()){
            if(!labelPrint[1]){
                this.add(teacherLabel);
                labelPrint[1] = true;
                this.add(exam);
            }
            //this.add(feedback);
            if(!labelPrint[2]){
                this.add(manageLabel);
                labelPrint[2] = true;
            }
        }
        
        if(mainFrame.userBLL.getCurrent().getRole().getSeeQuest()){
            if(!labelPrint[2]){
                this.add(manageLabel);
                labelPrint[2] = true;
            }
            this.add(manageQues);
        }

        if(mainFrame.userBLL.getCurrent().getRole().getAdmin()){
            if(!labelPrint[3]){
                this.add(adminLabel);
                labelPrint[3] = true;
            }
            this.add(manageSubject);
            this.add(manageUser);
            this.add(manageRole);
            this.add(asignUser);
        }

        // Nút quay lại LoginPanel
        JButton logoutButton = ButtonFactory.createClearButton(mainFrame, 214, 50,AddImage.createImageIcon(Connect.img + "logout.png", 40, 40), e -> {
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Đăng xuất?",
                "Đăng xuất",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                mainFrame.addPanel(new LoginPanel(mainFrame), "LoginPanel");
                mainFrame.showPanel("LoginPanel");
                JOptionPane.showMessageDialog(this, "Đăng xuất thành công!");
            }
        });
        this.add(Box.createVerticalGlue());
        logoutButton.setText("Đăng xuất");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(logoutButton, BorderLayout.SOUTH);
        home.doClick();
    }

    private JLabel createLabel(String str){
        JLabel label = new JLabel(str);
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        return label;
    }
    private JButton createButton(MainFrame mainFrame, String src, Supplier<JPanel> panelSupplier, String panelName, String text) {
        JButton button = ButtonFactory.createClearButton(mainFrame, 214, 50, AddImage.createImageIcon(Connect.img + src, 40, 40));
        button.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setBackground(Color.LIGHT_GRAY);
                selectedButton.setForeground(Color.BLACK);
            }
            button.setForeground(Color.RED);
            selectedButton = button;

            mainFrame.addPanel(panelSupplier.get(), panelName);
            mainFrame.showPanel(panelName);
        });
        
        button.setText(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
}