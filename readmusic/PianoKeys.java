package readmusic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import static readmusic.ReadMusic.engine;
import static readmusic.ReadMusic.gold;
import static readmusic.ReadMusic.silver;
import static readmusic.ReadMusic.sounds;

public class PianoKeys extends JButton implements MouseListener{
    
    private int octave;
    private String note, alias;
    private int place;
    private int mainNumber;
    private boolean quiz = false;
    private boolean playsSound;
    private Color myColor;
    private boolean textOn;
    private boolean spacing;
    
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
            if (!spacingSwitch){
                switch (engine.gettScaleUsed()){
                    case NONE: 
                        if(myColor == Color.WHITE)
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case CHROMATIC:
                        if (myColor == Color.BLACK){
                            setText(String.format("<html><center>%s<br>%s</center></html>", alias, note));
                        }else{
                            setText(String.format("<html><center>%s</center></html>", note));
                        }
                        break;
                    case SHARP1:
                        if((myColor == Color.WHITE && note!="F") || note == "F#")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case SHARP2:
                        if((myColor == Color.WHITE && note!="F" && note!="C") || note == "F#" || note == "C#")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case SHARP3:
                        if((myColor == Color.WHITE && note!="F" && note!="C" && note!="G") 
                                || note == "F#" || note == "C#" || note == "G#")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case SHARP4:
                        if((myColor == Color.BLACK && note!="A#")  //FGCD EAB   
                                || note == "E" || note == "A" || note == "B")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case SHARP5:
                        if((myColor == Color.BLACK)  //FGACD EB   
                                || note == "E" || note == "B")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case SHARP6:
                        if((myColor == Color.BLACK)  //FGACDE B   
                                || note == "B")
                            setText(String.format("<html><center>%s</center></html>", note));
                        else if(alias == "E#")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        break;
                    case SHARP7:
                        if(myColor == Color.BLACK)
                            setText(String.format("<html><center>%s</center></html>", note));
                        else if(alias == "E#" || alias == "B#")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        break;

                    case FLAT1:
                        if(myColor == Color.WHITE && note!="B")
                            setText(String.format("<html><center>%s</center></html>", note));
                        else if(alias =="Bb")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        break;
                    case FLAT2:
                        if(myColor == Color.WHITE && note!="B" && note!="E")
                            setText(String.format("<html><center>%s</center></html>", note));
                        else if(alias =="Bb" || alias == "Eb")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        break;   
                    case FLAT3:
                        if(myColor == Color.WHITE && note!="B" && note!="E" && note!="A")
                            setText(String.format("<html><center>%s</center></html>", note));
                        else if(alias =="Bb" || alias == "Eb" || alias =="Ab")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        break;
                    case FLAT4:
                        if(myColor == Color.BLACK && alias!="Gb")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        else if(note =="G" || note == "C" || note =="F")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case FLAT5:
                        if(myColor == Color.BLACK)
                            setText(String.format("<html><center>%s</center></html>", alias));
                        else if(note == "C" || note =="F")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case FLAT6:
                        if(myColor == Color.BLACK || alias == "Cb")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        else if(note =="F")
                            setText(String.format("<html><center>%s</center></html>", note));
                        break;
                    case FLAT7:
                        if(myColor == Color.BLACK || alias == "Cb" || alias == "Fb")
                            setText(String.format("<html><center>%s</center></html>", alias));
                        break;
                }

            }else{ //spacingSwitch==true
                setText(String.format("<html><center>%s</center></html>", note));
                this.setForeground(octave <= 3 ? silver: gold);
                spacing = true;
            }
        }else{
            setText("");
            if (spacingSwitch){
                setForeground(Color.BLACK);
                spacing = false;
            }
        }
    }
    
    void refresh(){
        if (!spacing){
            turnTextOnOff(false);
            turnTextOnOff(false); //on-off
        }
    }
    
    void turnSoundOnOff(){
        playsSound = !(playsSound);
    }
    
    PianoKeys(String[] note, int octave, int num){
        this.mainNumber = num;
        this.note = note[0];
        this.alias = note[1];
        this.octave = octave;
        setRolloverEnabled(false);
        setFocusable(false);
        setVerticalAlignment(SwingConstants.BOTTOM);
        setMargin(new Insets(0,0,0,0));
        if (note[0].endsWith("#")){
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
        if(e.getButton() == 1) {
            presser();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releaser();
    }
    
    void presser(){
        if(this.isEnabled()){
            getModel().setArmed(true);
            getModel().setPressed(true);
            if (playsSound) sounds.playSound(mainNumber);
            if (quiz) engine.checkSolution(mainNumber, note+"-"+octave);
        }
    }
    
    void releaser(){
        if (this.isEnabled()){
            getModel().setArmed(false);
            getModel().setPressed(false);
        }
        if (playsSound) sounds.stopSound(mainNumber);
    }

    void setInactive(boolean inactive, boolean gfOn){
        if(inactive){
            this.setEnabled(false);
            this.stopSounds();
            if(myColor == Color.BLACK){
                this.setBackground(new Color(255, 255, 255, 227));
            } else{
                this.setBackground(new Color(195, 195, 195, 130));
            }
            //setBorder(new LineBorder(new Color(255, 255, 255, 30)));
            this.setText("");
        } else{
            this.setEnabled(true);
            //this.setBorder(UIManager.getBorder("Button.border"));
            if(gfOn && mainNumber == 18){
                this.setBackground(silver);
            }else if(gfOn && mainNumber == 32){
                this.setBackground(gold);
            } else {
                this.setBackground(myColor);
            }
            if(textOn){
                textOn = false;
                this.turnTextOnOff(false);
            }
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



