package GUI.UserPanel;

import GUI.MakeColor.*;
import java.awt.Color;
import javax.swing.JPanel;

public class QuestionList extends JPanel {
    private MainFrame mainFrame;
    private JPanel menuPanel;


    public QuestionList(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Lưu tham chiếu đến MainFrame
        init();
    }
    private void init(){
        setLayout(null);
        setBackground(Ulti.MainColor);

        menuPanel = new JPanel();
        menuPanel.setBackground(Color.white);
        menuPanel.setBounds(0, 0, 214, 900);
        menuPanel.setLayout(null);

        

        setVisible(true);
    }
}
