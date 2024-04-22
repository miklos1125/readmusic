package readmusic;

import static readmusic.ReadMusic.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PianoKeys extends JButton implements MouseListener{
    
    private int octave;
    private String note;
    private int place;
    private int mainNumber;
    boolean quiz = false;
    private boolean playsSound;
    private Color myColor;
    private boolean textOn;
    
    void setPlace(int x){
        place = x;
    }
    
    int getPlace(){
        return place;
    }
    
    String getNote(){
        return note;
    }
    
    void turnTextOnOff(){
        textOn = !textOn;
        if (textOn && this.isEnabled()){
            setText(note);
        }else{
            setText("");
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
        
        //setOpaque(true);
        //this.setVerticalTextPosition(SwingConstants.BOTTOM);
        //setText(String.valueOf(x)); setRolloverEnabled (false);
        
        //this.setEnabled(false);
        
        //this.setFocusPainted(false);
        //this.setFocusable(false);

        //this.setBorderPainted(false);
        //this.setContentAreaFilled(false);
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

    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    void setInactive(boolean inactive){
        if(inactive){
            this.setEnabled(false);
            this.stopSounds();
            this.setBackground(new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(), 235));
            this.setText("");
        } else{
            this.setEnabled(true);
            this.setBackground(myColor);
            if(textOn) this.setText(note);
        }
    }
    
    void stopSounds(){
        if (playsSound) sounds.stopSound(mainNumber);
    }
}



