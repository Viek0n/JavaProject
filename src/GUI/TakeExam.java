package GUI;

import BLL.ExamBLL;
import DTO.ExamDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.CountdownTimer;
import GUI.MakeColor.RoundedButton;
import GUI.MakeColor.RoundedPanel;
import GUI.MakeColor.Ulti;
import MICS.Connect;
import MICS.Enums;
import java.awt.*;
import javax.swing.*;

public class TakeExam {

    private int FrameWidth = 1600;
    private int FrameHeight = 900;
    private ExamDTO exam;
    private int columns;
    private int currentQuestionIndex = 0;

    JFrame User_frame;
    JPanel Main_panel, Left_panel, Right_panel, Test_panel,dynamicNumberPanel,gridPanel;
    JLabel Test_title, Code_Exam1, Code_Exam2, Test_img;
    JTextField leftField,rightField; 
    RoundedButton[] answerButton;
    JButton[] cell;
    CountdownTimer countDown;

    public TakeExam(ExamDTO exam) {
        columns = 4;
        JLabel questionNumberLabel = new JLabel();
        JLabel questionLabel = new JLabel();
        this.exam = exam;
        cell = new JButton[exam.getQuestion().size()];
        answerButton = new RoundedButton[4];
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

        Test_img = AddImage.createImageLabel(Connect.img+"test.png", 76, 30, 80, 80);
        Left_panel.add(Test_img);

        // Tạo một JPanel để chứa các thành phần của DynamicNumberDisplay
        dynamicNumberPanel = new JPanel();
        dynamicNumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Căn giữa các thành phần
        dynamicNumberPanel.setBounds(25, 180, 150, 48); // Đặt vị trí và kích thước
        dynamicNumberPanel.setBackground(Color.white); // Đặt màu nền

        // Tạo các thành phần từ DynamicNumberDisplay
        leftField = new JTextField(""); // Số bên trái
        leftField.setFont(new Font("Arial", Font.BOLD, 30)); // Cỡ chữ lớn hơn
        leftField.setEditable(false);
        leftField.setFocusable(false);
        leftField.setBorder(null);
        leftField.setPreferredSize(new Dimension(40, 40)); // Đặt kích thước đủ lớn để hiển thị số
        leftField.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel slashLabel = new JLabel("/"); // Dấu "/"
        slashLabel.setFont(new Font("Arial", Font.BOLD, 30));

        rightField = new JTextField(Integer.toString(cell.length)); // Số bên phải
        rightField.setFont(new Font("Arial", Font.BOLD, 30)); // Cỡ chữ lớn hơn
        rightField.setEditable(false);
        rightField.setFocusable(false);
        rightField.setBorder(null);
        rightField.setPreferredSize(new Dimension(40, 40)); // Đặt kích thước đủ lớn để hiển thị số
        rightField.setHorizontalAlignment(SwingConstants.CENTER);

        // Thêm các thành phần vào dynamicNumberPanel
        dynamicNumberPanel.add(leftField);
        dynamicNumberPanel.add(slashLabel);
        dynamicNumberPanel.add(rightField);

        countDown = new CountdownTimer(exam.getRemainingTime(),exam);
        countDown.setBounds(50, 100, 100, 100);
        Left_panel.add(countDown);
        // Thêm dynamicNumberPanel vào Left_panel
        Left_panel.add(dynamicNumberPanel);   

        //Tạo ô hiển thị các câu đã làm
        gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;     

        // Tạo lưới 5x5 (25 ô vuông)
        //int totalCells = exam.getQuestion().size();

        for (int i = 0; i < cell.length; i++) {
            gbc.gridx = i % columns; // Cột
            gbc.gridy = i / columns; // Hàng
            final int questionIndex = i;
            cell[i] = ButtonFactory.createConfirmButton(Integer.toString(i+1), e -> {
                leftField.setText(Integer.toString(questionIndex+1));
                questionNumberLabel.setText("Câu " +Integer.toString(questionIndex+1) + ": ");
                questionLabel.setText("<html>"+exam.getQuestion().get(questionIndex).getText()+"</html>");
                currentQuestionIndex = questionIndex;
                for(int tmp = 0; tmp < 4; tmp++)
                    answerButton[tmp].setBackground(Color.WHITE);
                if(exam.getChoice().get(currentQuestionIndex) != -1)
                    answerButton[exam.getChoice().get(currentQuestionIndex)].setBackground(new Color(144, 238, 144));
                
                for(int index = 0; index < 4; index++){
                    char letter = (char) ('A' + (index));
                    answerButton[index].setText("<html>"+ Enums.AnswerID.valueOf(String.valueOf(letter)).toString()+". "+exam.getQuestion().get(currentQuestionIndex).getAns().get(index).getText()+"</html>");
                }
            });
            cell[i].setPreferredSize(new Dimension(40, 40));
            cell[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cell[i].setBackground(Color.LIGHT_GRAY); // Xám
            gridPanel.add(cell[i], gbc);
        }

        // Đặt vị trí cho gridPanel trong Left_panel
        gridPanel.setBounds(5, 190, 200, 440); // Đặt bên dưới dynamicNumberPanel
        gridPanel.setBackground(Color.white);
        //gridPanel.setBackground(Color.BLACK);
        Left_panel.add(gridPanel);


        RoundedButton submit = new RoundedButton("Nộp bài", 45);
        submit.setBounds(20, 750, 180, 70);
        submit.setBackground(Color.GREEN);
        submit.setFont(new Font("Arial", Font.BOLD, 20));
        submit.setForeground(Color.BLACK);
        submit.setVerticalAlignment(SwingConstants.CENTER);
        submit.setHorizontalAlignment(SwingConstants.CENTER);
        submit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có muốn nộp bài?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Điểm kiểm tra: "+Float.toString(new ExamBLL().calculate(exam)), "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // Thêm biểu tượng và nút Logout vào Left_panel
        Left_panel.add(submit);
     

        // Create Right_panel for questions and answers
        Right_panel = new JPanel();
        Right_panel.setLayout(null);
        Right_panel.setBackground(Ulti.MainColor); // Light blue
        Right_panel.setBounds(214, 0, 1386, 900);
        Main_panel.add(Right_panel);



        // Question number label
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 32));
        questionNumberLabel.setBounds(115, 100, 153, 47);
        Right_panel.add(questionNumberLabel);

        // Create RoundedPanel for the question
        RoundedPanel questionPanel = new RoundedPanel(50);
        questionPanel.setBackground(Color.WHITE);
        questionPanel.setBounds(115, 150, 1175, 374);
        questionPanel.setLayout(null);

        // Updated question text from the image
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));
        questionLabel.setForeground(Color.BLACK);
        questionLabel.setBounds(20, 20, 1135, 334);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(questionLabel);
        Right_panel.add(questionPanel);

        // Tọa độ ban đầu cho các nút
        int startX = 115; // Vị trí X của nút đầu tiên
        int startY = 550; // Vị trí Y của hàng đầu tiên
        int buttonWidth = 550; // Chiều rộng của nút
        int buttonHeight = 80; // Chiều cao của nút
        int gapY = 100; // Khoảng cách giữa các hàng

        // Tạo các nút trả lời bằng vòng lặp
        for (int i = 0; i < 4; i++) {
            answerButton[i] = new RoundedButton("", 30); // Góc bo tròn 30px
            answerButton[i].setFont(new Font("Arial", Font.BOLD, 20));
            answerButton[i].setBackground(Color.WHITE);
            answerButton[i].setForeground(Color.BLACK); 
            answerButton[i].setVerticalAlignment(SwingConstants.CENTER);
            answerButton[i].setHorizontalAlignment(SwingConstants.LEFT);
            // Tính toán vị trí của nút
            int x = (i % 2 == 0) ? startX : startX + buttonWidth + 50; // Cột trái hoặc phải
            int y = startY + (i / 2) * gapY; // Hàng trên hoặc dưới

            answerButton[i].setBounds(x, y, buttonWidth, buttonHeight);

            // Thêm nút vào Right_panel\
            //?
            Right_panel.add(answerButton[i]);
        }
        for (int index = 0; index < 4; index++) {
            final int answerIndex = index;
            answerButton[index].addActionListener(a -> {
                for(int tmp = 0; tmp < 4; tmp++)
                    answerButton[tmp].setBackground(Color.WHITE);
                cell[currentQuestionIndex].setBackground(new Color(144, 238, 144));
                exam.getChoice().set(currentQuestionIndex, answerIndex);
                if(exam.getChoice().get(currentQuestionIndex) != -1){
                    answerButton[exam.getChoice().get(currentQuestionIndex)].setBackground(new Color(144, 238, 144));
                }else{
                    answerButton[exam.getChoice().get(currentQuestionIndex)].setBackground(Color.white);
                }
            });
        }
      
     
        User_frame.add(Main_panel);
        User_frame.setVisible(true);
        cell[0].doClick();
    }
}