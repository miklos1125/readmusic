package readmusic;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

public class MyTimer extends TimerTask {
    private int seconds;
    private JLabel myLabel;
    private Timer timer;
    private ControlPanel panel;
    
    MyTimer(JLabel label, int seconds, ControlPanel panel){
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
    
    void stop(){
        timer.cancel();
    }
    
}
