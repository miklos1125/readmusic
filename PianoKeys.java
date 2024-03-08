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
        if (this.getText().equals("")){
            setText(note);
        }else{
            setText("");
        }
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
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
            setBorder(null);
            setFont(new Font("Serif", Font.PLAIN, 11));
        } else{  
            setBackground(Color.WHITE);
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
            sounds.playSound(mainNumber);
            if (quiz)
            engine.checkSolution(mainNumber, note+"-"+octave);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        sounds.stopSound(mainNumber);
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
}



