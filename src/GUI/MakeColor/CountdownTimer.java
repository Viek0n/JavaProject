package GUI.MakeColor;

import BLL.ExamBLL;
import DTO.ExamDTO;
import GUI.UserPanel.MainFrame;
import GUI.UserPanel.ScorePanel;
import java.awt.event.*;
import java.sql.Time;
import java.time.LocalTime;
import javax.swing.*;

public class CountdownTimer extends JLabel {
    private long timeRemainingMillis;
    private Timer timer;
    private ExamDTO exam;
    private MainFrame mainFrame;

    public CountdownTimer(Time sqlTime, ExamDTO exam, MainFrame mainFrame) {
        // Convert java.sql.Time to milliseconds duration
        LocalTime localTime = sqlTime.toLocalTime();
        this.exam = exam;
        this.mainFrame = mainFrame;
        int hour = localTime.getHour();
        int minutes = localTime.getMinute() + hour*60;
        int seconds = localTime.getSecond();
        timeRemainingMillis = (minutes * 60L + seconds) * 1000;

        setText(formatTime(timeRemainingMillis));
        setFont(getFont().deriveFont(24f));
        setHorizontalAlignment(SwingConstants.CENTER);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeRemainingMillis -= 1000;

                if (timeRemainingMillis <= 0) {
                    timeRemainingMillis = 0;
                    timer.stop();
                    setText("00:00");
                    JOptionPane.showMessageDialog(null, "Hết giờ!");
                    exam.setRemainingTime(new java.sql.Time(0,0,0));
                    mainFrame.addPanel(new ScorePanel(mainFrame, mainFrame.userBLL.getCurrent(), exam.calculateScore(), "00:00"), "ResultPanel");
                    new ExamBLL().add(exam);
                    mainFrame.showPanel("ResultPanel");
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

    public Time getTimeRemainingMillis() {
        long seconds = timeRemainingMillis / 1000;
        LocalTime localTime = LocalTime.ofSecondOfDay(seconds);
        Time sqlTime = Time.valueOf(localTime);
        return sqlTime;
    }
}
