package readmusic;

import java.awt.*;
import java.net.URL;
import readmusic.ReadMusic.Accidental;
import readmusic.ReadMusic.PartOfSheet;
import static readmusic.ReadMusic.mediumFont;

public class NoteHead {
    
    private int position;
    private boolean showAccidental, showCourtesyBrackets;
    private Accidental myAccidental;
    static final private int unit = 15;
    
    static Image notehead;
    static Image sharp; 
    static Image flat;
    
    NoteHead(){
        position = -1;
        showAccidental = false;
      
        URL u = this.getClass().getResource("pic/notehead.png"); 
        notehead = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/sharp.png");
        sharp = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/flat.png");
        flat = Toolkit.getDefaultToolkit().createImage(u);
    }
    
    void erasePosition(){
        position = -1;
        showAccidental = false;
    }
    
    void setAccidental(boolean showAccidentalBeforeNoteHead){
        showAccidental = showAccidentalBeforeNoteHead;
    }
    
    void turnCourtesyOnOff(){
        showCourtesyBrackets = !showCourtesyBrackets;
    }
    
    void setPosition(int pitch, PartOfSheet whereToShow, Accidental accidental, boolean isBCEF){
        int selector;
        switch(accidental){
            case FLAT : selector = pitch;
                break;
            case SHARP : selector = isBCEF ? pitch-2: pitch-1;
                break;
            case NONE : //fall through
            case NATURAL : //fall through
            default : selector = pitch - 1;
                break;
        }
        if (whereToShow.equals(PartOfSheet.BASS)){
            this.position = new int[]{0,0,1,1,2,3,3,4,4,5,5,6,7,7,8,8,9,
                                    10,10,11,11,12,12,13,14,14,15,15,16}[selector];
        } else if (whereToShow.equals(PartOfSheet.TREBLE)){
            selector -= 21;
            this.position = new int[]{18,18,19,20,20,21,21,22,23,23,24,24,25,25,
                                26,27,27,28,28,29,30,30,31,31,32,32,33,34}[selector];
        }
        myAccidental = accidental;
    }
    
    boolean isAvailable(){
        return position >= 0;
    }
    
    void draw(Graphics2D g2){
        if(showAccidental || showCourtesyBrackets){
            if (myAccidental.equals(Accidental.SHARP)){
                if(showCourtesyBrackets) drawCourtresyBrackets(g2);
                g2.drawImage(sharp, 178, -position*unit/2-10, 20, 20, ReadMusic.panel);
            } else if (myAccidental.equals(Accidental.FLAT)){
                if(showCourtesyBrackets) drawCourtresyBrackets(g2);
                g2.drawImage(flat, 178, -position*unit/2-15, 20, 24, ReadMusic.panel);
            }
        }
        //extra lines
        switch(position){
            case 0:
                g2.drawLine(200,0*unit-1,223,0*unit-1);
                g2.drawLine(200,0*unit,223,0*unit);
            case 1:
            case 2:
                g2.drawLine(200,-1*unit-1,223,-1*unit-1);
                g2.drawLine(200,-1*unit,223,-1*unit);
                break;
            case 16:
                g2.drawLine(200,-8*unit-1,223,-8*unit-1);
                g2.drawLine(200,-8*unit,223,-8*unit);
            case 14:
            case 15:
                g2.drawLine(200,-7*unit-1,223,-7*unit-1);
                g2.drawLine(200,-7*unit,223,-7*unit);
                break;
            case 18:
                g2.drawLine(200,-9*unit-1,223,-9*unit-1);
                g2.drawLine(200,-9*unit,223,-9*unit);
            case 19:
            case 20:
                g2.drawLine(200,-10*unit-1,223,-10*unit-1);
                g2.drawLine(200,-10*unit,223,-10*unit);
                break;
            case 34:
                g2.drawLine(200,-17*unit-1,223,-17*unit-1);
                g2.drawLine(200,-17*unit,223,-17*unit);
            case 32:
            case 33:
                g2.drawLine(200,-16*unit-1,223,-16*unit-1);
                g2.drawLine(200,-16*unit,223,-16*unit);
                break;
        }
        g2.drawImage(notehead, 200, -position*unit/2-12, 24, 24, ReadMusic.panel);
    }
    
    void drawCourtresyBrackets(Graphics2D g2){
        g2.setFont(mediumFont);
        g2.drawString("(", 173, -position*unit/2+6);
        g2.drawString(")", 196, -position*unit/2+6);
    }
}
