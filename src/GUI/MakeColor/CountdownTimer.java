package GUI.MakeColor;

import BLL.ExamBLL;
import DTO.ExamDTO;
import java.awt.event.*;
import java.sql.Time;
import javax.swing.*;

public class CountdownTimer extends JLabel {
    private long timeRemainingMillis;
    private Timer timer;
    private ExamDTO exam;
    private JFrame frame;

    public CountdownTimer(Time sqlTime, ExamDTO exam, JFrame frame) {
        // Convert java.sql.Time to milliseconds duration
        this.exam = exam;
        this.frame = frame;
        int hour = sqlTime.getHours();
        int minutes = sqlTime.getMinutes() + hour*60;
        int seconds = sqlTime.getSeconds();
        timeRemainingMillis = (minutes * 60L + seconds) * 1000;

        setText(formatTime(timeRemainingMillis));
        setFont(getFont().deriveFont(24f));
        setHorizontalAlignment(SwingConstants.CENTER);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeRemainingMillis -= 1000;

                if (timeRemainingMillis <= 0) {
                    timer.stop();
                    setText("00:00");
                    JOptionPane.showMessageDialog(null, "Hết giờ!");
                    JOptionPane.showMessageDialog(null, "Điểm kiểm tra: "+Float.toString(new ExamBLL().calculate(exam)), "Result", JOptionPane.INFORMATION_MESSAGE);
                    frame.setVisible(false);
                } else {
                    setText(formatTime(timeRemainingMillis));
                }
            }
        });

        timer.start();
    }

    public String formatTime(long millis) {
        int minutes = (int) (millis / 60000);
        int seconds = (int) ((millis % 60000) / 1000);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String formatTime() {
        int minutes = (int) (timeRemainingMillis / 60000);
        int seconds = (int) ((timeRemainingMillis % 60000) / 1000);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void stop() {
        if (timer != null) timer.stop();
    }

    public void reset(Time newSqlTime) {
        stop();
        timeRemainingMillis = (newSqlTime.getMinutes() * 60L + newSqlTime.getSeconds()) * 1000;
        setText(formatTime(timeRemainingMillis));
        timer.start();
    }

    public long getTimeRemainingMillis() {
        return timeRemainingMillis;
    }
}
