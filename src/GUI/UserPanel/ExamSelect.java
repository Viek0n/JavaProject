package GUI.UserPanel;

import BLL.ExamBLL;
import BLL.ExamStructBLL;
import DTO.ExamStructDTO;
import GUI.MakeColor.AddImage;
import GUI.MakeColor.ButtonFactory;
import GUI.MakeColor.RoundedPanel;
import GUI.MakeColor.RoundedTextField;
import GUI.MakeColor.Ulti;
import MICS.Connect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.Date;

public class ExamSelect extends JPanel {
    private JPanel menuPanel;
    private RoundedTextField maBaiKT;
    private JLabel textInTheBox;
    private MainFrame mainFrame;

    public ExamSelect(MainFrame mainFrame, JPanel menuPanel) {
        this.mainFrame = mainFrame;
        this.menuPanel = menuPanel;
        init();
    }

    private void init() {
        setLayout(null);
        setBackground(Ulti.MainColor);

        int formWidth = 700;
        int formHeight = 320;

        // Panel chứa form
        JPanel formPanel = new RoundedPanel(60);
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setSize(formWidth, formHeight);

        // Label hướng dẫn
        textInTheBox = new JLabel("Nhập mã bài kiểm tra", SwingConstants.CENTER);
        textInTheBox.setFont(new Font("Arial", Font.BOLD, 32));
        textInTheBox.setBounds(50, 30, 600, 50);
        textInTheBox.setForeground(Color.BLACK);
        formPanel.add(textInTheBox);

        // Text field
        maBaiKT = new RoundedTextField(40);
        maBaiKT.setFont(new Font("Arial", Font.PLAIN, 38));
        maBaiKT.setBounds(50, 100, 600, 70);
        maBaiKT.setBackground(Color.white);
        maBaiKT.setForeground(Color.black);
        maBaiKT.setCaretColor(Color.black);
        maBaiKT.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        maBaiKT.setOpaque(false);
        formPanel.add(maBaiKT);

        // Confirm button
        JButton confirmButton = ButtonFactory.createConfirmButton(mainFrame, 0, 0, 0, 0, 0, Ulti.BananaLeaf, e -> {
            String testCode = maBaiKT.getText().trim();
            if (testCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                ExamStructDTO examStruct = new ExamStructBLL().get(testCode);
                if(examStruct!=null){
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        Date today = cal.getTime();

                        if (today.before(examStruct.getStart()) || today.after(examStruct.getEnd())) {
                            JOptionPane.showMessageDialog(this, "Chưa tới lúc kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Bạn có muốn làm bài kiểm tra không?",
                            "Kiểm tra",
                            JOptionPane.YES_NO_OPTION
                        );
                        if (choice == JOptionPane.YES_OPTION) {
                            mainFrame.addPanel(new TakeExam(
                                new ExamBLL().gen(mainFrame.userBLL.getCurrent().getLoginName(), examStruct),
                                mainFrame),
                            "takeExam");
                            mainFrame.showPanel("takeExam");
                        }
                    }
                }else
                JOptionPane.showMessageDialog(this, "Không tìm thấy bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        confirmButton.setBounds(200, 200, 300, 70);
        confirmButton.setText("Làm bài");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 32));
        confirmButton.setForeground(Color.BLACK);
        formPanel.add(confirmButton);

        add(formPanel);
        add(menuPanel);

        // Re-center when resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerForm(formPanel);
            }
        });
    }

    private void centerForm(JPanel panel) {
        int x = (getWidth() - panel.getWidth()) / 2 + 107; // Center, then offset right half of menu (214/2)
        int y = (getHeight() - panel.getHeight()) / 2;
        panel.setLocation(x, y);
    }
}
