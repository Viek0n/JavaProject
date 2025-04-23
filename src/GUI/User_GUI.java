package GUI;

import MICS.*;
import java.awt.*;
import javax.swing.*;

public class User_GUI {

    private int FrameWidth = 1600;
    private int FrameHeight = 900;

    JFrame User_frame;
    JPanel Main_panel, Left_panel, Right_panel, Test_panel;
    JLabel Test_title, Code_Exam1, Code_Exam2, Test_img;
    JButton Answer1, Answer2, Answer3, Answer4, Submit_Answer;

    public User_GUI() {
        // Tạo frame cho User
        User_frame = new JFrame();
        User_frame.setSize(FrameWidth, FrameHeight);
        User_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        User_frame.setResizable(false);

        // Tạo panel chứa tất cả thành phần
        Main_panel = new JPanel();
        Main_panel.setBackground(Ulti.MainColor);
        Main_panel.setLayout(null);

        // Tạo Left_panel chứa các thành phần về đề thi
        Left_panel = new JPanel();
        Left_panel.setBackground(Color.white);
        Left_panel.setBounds(0, 0, 214, 900);
        Left_panel.setLayout(new BoxLayout(Left_panel, BoxLayout.Y_AXIS));
        Main_panel.add(Left_panel);

        // Tạo ô panel kiểm tra nhỏ
        Test_panel = new JPanel();
        Test_panel.setBackground(Color.white);
        Test_panel.setBounds(0, 0, 150, 100);
        Test_panel.setLayout(null);
        Test_panel.setOpaque(false);

        // Tạo JPanel chứa cả chữ và ảnh
        JPanel Test_title_img_panel = new JPanel();
        Test_title_img_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Test_title_img_panel.setBounds(0, 0, 214, 60);
        Test_title_img_panel.setBackground(Color.white);

        // Tạo JLabel cho chữ "Kiểm tra"
        Test_title = new JLabel("Kiểm tra");
        Test_title.setFont(new Font("Arial", Font.BOLD, 24));
        Test_title.setForeground(Color.black);
        Test_img = AddImage.createImageLabel(Connect.img + "test.png", 0, 0, 50, 50);
        Test_title_img_panel.add(Test_title);
        Test_title_img_panel.add(Test_img);
        Test_panel.add(Test_title_img_panel);
        Left_panel.add(Box.createVerticalStrut(10));
        Left_panel.add(Test_panel);

        // Tạo JLabel cho Code_Exam1
        Code_Exam1 = new JLabel("Exam123", SwingConstants.CENTER);
        Code_Exam1.setFont(new Font("Arial", Font.PLAIN, 32));
        Code_Exam1.setForeground(Color.black);
        Code_Exam1.setBackground(Color.white);
        Code_Exam1.setOpaque(true);
        Code_Exam1.setAlignmentX(Component.CENTER_ALIGNMENT);

        Left_panel.add(Box.createVerticalStrut(10));
        Left_panel.add(Code_Exam1);

        // Tạo Right_panel chứa các thành phần về câu hỏi và đáp án
        Right_panel = new JPanel();
        Right_panel.setBackground(Ulti.MainColor);
        Right_panel.setBounds(214, 0, 1386, 900);
        Main_panel.add(Right_panel);

        User_frame.add(Main_panel);
        User_frame.setVisible(true);
    }

    public static void main(String[] args) {
        new User_GUI();
    }
}