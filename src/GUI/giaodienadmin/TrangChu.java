package GUI.giaodienadmin;

import GUI.giaodienadmin.QuanLyCauHoi.QuestionManagementPanel;
import GUI.giaodienadmin.QuanLyDeThi.ExamManagementPanel;
import GUI.giaodienadmin.QuanLyDeThi.PanelExemDetail;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TrangChu extends JFrame implements ActionListener {
    private JPanel pCenter;
    private JPanel JPHeader;
    private ThanhBar titleBar;
    private DanhSach leftSilde;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    JButton[] buttons;
    final int DEFAUT_WIDTH_JFrame = 1280, DEFAUT_HEIGHT_JFrame = 800;

    public TrangChu() {
        initComponent();
    }

    public JPanel getPCenter() {
        return pCenter;
    }

    public void setpCenter(JPanel pCenter) {
        this.pCenter = pCenter;
    }

    public void initComponent() {
        this.setTitle("Trang Chủ");
        this.getContentPane().setBackground(Color.decode("#f4f4f4"));
        this.setLayout(new BorderLayout(0, 0));

        // Title Bar
        titleBar = new ThanhBar();
        JPHeader = new JPanel(new BorderLayout());
        JPHeader.add(titleBar, BorderLayout.NORTH);
        this.add(JPHeader, BorderLayout.NORTH);
        
        leftSilde = new DanhSach(this);
        leftSilde.setBackground(Color.decode("#34495e"));
        this.add(leftSilde, BorderLayout.WEST);
        buttons = leftSilde.getButtons();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add panels to CardLayout
        PanelExemDetail panelExemDetail = new PanelExemDetail(cardPanel, cardLayout);
        cardPanel.add(new QuestionManagementPanel(cardPanel, cardLayout, panelExemDetail), "QuanLyCauHoi");
        cardPanel.add(panelExemDetail, "EditPanel");
        cardPanel.add(new ExamManagementPanel(), "QuanLyBaiKiemTra");
        this.add(cardPanel, BorderLayout.CENTER);
        setSize(DEFAUT_WIDTH_JFrame, DEFAUT_HEIGHT_JFrame);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                switch (i) {
                    case 0:
                        cardLayout.show(cardPanel, "QuanLyCauHoi");  
                        break;
                    case 1:
                        cardLayout.show(cardPanel, "QuanLyBaiKiemTra");
                        break;
                    case 2:
                        cardLayout.show(cardPanel, "QuanLyNguoiDung");
                        break;
                    case 3:
                        cardLayout.show(cardPanel, "QuanLyQuyen");
                        break;
                    case 4:
                        cardLayout.show(cardPanel, "ThamGiaBaiKiemTra");
                        break;
                    default:
                        System.out.println("Unknown button clicked!");
                }
            }
        }
    }
}