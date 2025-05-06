package GUI.giaodienadmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DanhSach extends JPanel {
    private JButton[] buttons;
    private TrangChu trangchu;

    public DanhSach(TrangChu trangchu) {
        this.trangchu = trangchu;
        initComponent();
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public void initComponent() {
        this.setLayout(new GridLayout(5, 1, 5, 5));
        this.setPreferredSize(new Dimension(180, 800));
        this.setBackground(Color.decode("#34495e"));

        ArrayList<String> danhSachQuanLy = new ArrayList<>();
        danhSachQuanLy.add("Câu Hỏi");
        danhSachQuanLy.add("Quản Lý Bài Kiểm Tra");
        danhSachQuanLy.add("Quản Lý Người Dùng");

        buttons = new JButton[danhSachQuanLy.size()];
        for (int i = 0; i < danhSachQuanLy.size(); i++) {
            buttons[i] = new JButton(danhSachQuanLy.get(i));
            buttons[i].setBackground(Color.decode("#2ecc71"));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 10));
            buttons[i].addActionListener(trangchu);
            this.add(buttons[i]);
        }
    }
}