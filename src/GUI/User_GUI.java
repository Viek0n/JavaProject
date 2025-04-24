package GUI;

import MICS.Connect;
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
        Left_panel.setLayout(null);
        Main_panel.add(Left_panel);


        Test_title = new JLabel();
        Test_title.setText("Kiểm tra");
        Test_title.setForeground(Color.BLACK); // Đặt màu chữ
        Test_title.setFont(new Font("Arial", Font.BOLD, 20));
        Test_title.setBounds(21, 31, 131, 45);
        Left_panel.add(Test_title);

        Test_img = new JLabel();
        Test_img = AddImage.createImageLabel(Connect.img+"test.png", 120, 31, 50, 50);
        Left_panel.add(Test_img);


        Code_Exam1 = new JLabel("Mã Bài");
        Code_Exam1.setFont(new Font("Arial", Font.BOLD, 32));
        Code_Exam1.setForeground(Color.BLACK); // Đặt màu chữ
        Code_Exam1.setBounds(55,119,104,39);
        Left_panel.add(Code_Exam1);  
        
        // Tạo JLabel cho Code_Exam2 với góc bo tròn
        Code_Exam2 = new JLabel("ABC", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Ulti.MainColor); // Màu nền
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Góc bo tròn (20px)
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK); // Màu viền
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Góc bo tròn (20px)
            }
        };
        Code_Exam2.setFont(new Font("Arial", Font.BOLD, 32)); // Đặt font chữ
        Code_Exam2.setForeground(Color.BLACK); // Đặt màu chữ
        Code_Exam2.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa nội dung
        Code_Exam2.setBounds(17, 162, 180, 64); // Đặt vị trí và kích thước
        Code_Exam2.setOpaque(false); // Tắt chế độ nền mặc định

        // Thêm Code_Exam2 vào Left_panel
        Left_panel.add(Code_Exam2);
                

        
     

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