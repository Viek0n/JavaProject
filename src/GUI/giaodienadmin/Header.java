package GUI.giaodienadmin;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.*;

public class Header extends JPanel{
  private JPanel pleft,pright;
  private JLabel lbtieude;
  final int DEFAUT_WIDTH_JPanel = 1280 , DEFAUT_HEIGHT_JPanel=50;
  public Header(){
    initComponent();
  }
  public void initComponent(){
    pleft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); 
    pleft.setBackground(Color.decode("#2c3e50"));
    lbtieude = new JLabel("Quản Lý Thi Trắc Nghiệm");
    lbtieude.setForeground(Color.WHITE);
    lbtieude.setPreferredSize(new java.awt.Dimension(300, 50));
    lbtieude.setHorizontalAlignment(SwingConstants.LEFT);
    lbtieude.setVerticalAlignment(SwingConstants.CENTER);
    lbtieude.setOpaque(true);
    lbtieude.setFont(new Font("Arial", Font.BOLD, 24));
    pleft.add(lbtieude);

    pright = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10)); 
    pright.setBackground(Color.decode("#2c3e50"));

    this.setLayout(new GridBagLayout());
    this.add(pleft, BorderLayout.WEST);
    this.add(pright, BorderLayout.EAST);
    this.setBackground(Color.decode("#2c3e50"));
    this.setOpaque(true);
    this.setSize(DEFAUT_WIDTH_JPanel,DEFAUT_HEIGHT_JPanel); 
  }
}

