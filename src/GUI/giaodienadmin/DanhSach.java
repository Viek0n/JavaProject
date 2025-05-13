package GUI.giaodienadmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.geom.RoundRectangle2D;
import java.awt.RenderingHints;

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
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setPreferredSize(new Dimension(200, 800));
        this.setBackground(Color.decode("#2c3e50")); // Dark background

        ArrayList<String> danhSachQuanLy = new ArrayList<>();
        danhSachQuanLy.add("Câu Hỏi");
        danhSachQuanLy.add("Quản Lý Bài Kiểm Tra");
        danhSachQuanLy.add("Quản Lý Người Dùng");

        buttons = new JButton[danhSachQuanLy.size()];
        for (int i = 0; i < danhSachQuanLy.size(); i++) {
            buttons[i] = createButton(danhSachQuanLy.get(i));
            buttons[i].addActionListener(trangchu);
            this.add(buttons[i]);
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(Color.decode("#3498db")); // Blue
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(10, 15, 10, 10));
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded rectangle for the entire panel background
        RoundRectangle2D roundedRect = new RoundRectangle2D.Double(
                0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners with radius 20
        g2d.setColor(getBackground()); // Use the panel's background color
        g2d.fill(roundedRect);

        g2d.dispose();
    }
}

