package readmusic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import static readmusic.ReadMusic.engine;
import static readmusic.ReadMusic.gold;
import static readmusic.ReadMusic.silver;
import static readmusic.ReadMusic.sounds;

public class PianoKeys extends JButton implements MouseListener{
    
    private int octave;
    private String note;
    private int place;
    private int mainNumber;
    private boolean quiz = false;
    private boolean playsSound;
    private Color myColor;
    private boolean textOn;
    
    public boolean isQuiz() {
        return quiz;
    }

    public void setQuiz(boolean quiz) {
        this.quiz = quiz;
    }
    
    void setPlace(int x){
        place = x;
    }
    
    int getPlace(){
        return place;
    }
    
    String getNote(){
        return note;
    }
    
    void turnTextOnOff(boolean spacingSwitch){
        textOn = !textOn;
        if (textOn && this.isEnabled()){
            setText(note);
            if (spacingSwitch){
                this.setForeground(octave <= 3 ? silver: gold);
            }
        }else{
            setText("");
            if (spacingSwitch) setForeground(Color.BLACK);
        }
    }
    
    void turnSoundOnOff(){
        playsSound = !(playsSound);
    }
    
    PianoKeys(String note, int octave, int num){
        this.mainNumber = num;
        this.note = note;
        this.octave = octave;
        setRolloverEnabled(false);
        setFocusable(false);
        setVerticalAlignment(SwingConstants.BOTTOM);
        setMargin(new Insets(0,0,0,0));
        if (note.endsWith("#")){
            myColor = Color.BLACK;
            setBackground(myColor);
            setForeground(Color.WHITE);
            setBorder(null);
            setFont(new Font("Serif", Font.PLAIN, 11));
        } else{ 
            myColor = Color.WHITE;
            setBackground(myColor);
            setForeground(Color.BLACK);
            setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        }
        setText("");
        addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println(mainNumber+"("+note+"-"+octave+")");
        
        if(e.getButton() ==1 && this.isEnabled()) {
            if (playsSound) sounds.playSound(mainNumber);
            if (quiz) engine.checkSolution(mainNumber, note+"-"+octave);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (playsSound) sounds.stopSound(mainNumber);
    }

    void setInactive(boolean inactive){
        boolean notGF = !this.getBackground().equals(silver) && !this.getBackground().equals(gold);
        if(inactive){
            this.setEnabled(false);
            this.stopSounds();
            if(notGF)this.setBackground(new Color(195, 195, 195, 235));
            this.setText("");
        } else{
            this.setEnabled(true);
            if(notGF)this.setBackground(myColor);
            if(textOn) this.setText(note);
        }
    }
    
    void stopSounds(){
        if (playsSound) sounds.stopSound(mainNumber);
    }
    
    //Out of service
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
}



