package readmusic;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyTimer extends TimerTask {
    private int seconds;
    JLabel myLabel;
    Timer timer;
    MyPanel panel;
    
    MyTimer(JLabel label, int seconds, MyPanel panel){
        timer = new Timer();
        this.seconds = seconds;
        this.myLabel = label;
        this.panel = panel;
        timer.scheduleAtFixedRate(this, 0, 1000);
    }

    @Override
    public void run() {
        myLabel.setText(""+(seconds) + (seconds>1?" seconds left":" second left"));
        if(seconds <= 0){
            timer.cancel();
            panel.endSetting();
        }
        seconds--;
    }
    
}
