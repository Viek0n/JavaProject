package GUI;

import javax.swing.*;
import BLL.UserBLL.*;
import MICS.*;
import java.awt.*;

public class User_GUI {

    private int FrameWidth = 1600;
    private int FrameHeight = 900;

    JFrame User_frame;
    JPanel Main_panel, Left_panel,Right_panel;
    JLabel Test_title, Code_Exam; 
    JButton Answer1, Answer2, Answer3, Answer4, Submit_Answer;
    
    public User_GUI(){
        // Tạo frame cho User
        User_frame = new JFrame();
        User_frame.setSize(FrameWidth, FrameHeight);
        User_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        User_frame.setResizable(false);

        //Tạo panel chứa tất cả thành phần
        Main_panel = new JPanel();
        Main_panel.setBackground(Ulti.MainColor);
        Main_panel.setLayout(null);

        //Tạo Left_panel chứa các thành phần về đề thi 
        Left_panel = new JPanel();
        Left_panel.setBackground(Color.white);
        Left_panel.setBounds(0, 0, 214, 900);
        Left_panel.setLayout(null);

        //Tạo bên trên left_panel là label Kiem tra
        Test_title = new JLabel("Kiểm Tra", SwingConstants.CENTER);
        Test_title = AddImage.createImageLabel(Connect.img+"test.png", 50, 50, 100, 100);

        





    }
}
