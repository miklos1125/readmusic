package readmusic;

import java.awt.*;
import java.net.URL;

public class NoteHead {
    
    private int position;
    private boolean isSharp;
    static final private int unit = 15;
    
    static Image notehead; // = Toolkit.getDefaultToolkit().getImage("notehead.png");
    static Image sharp; // = Toolkit.getDefaultToolkit().getImage("sharp.png");
    
    NoteHead(){
        position = -1;
        isSharp = false;
      
        URL u = this.getClass().getResource("pic/notehead.png"); 
        notehead = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/sharp.png");
        sharp = Toolkit.getDefaultToolkit().createImage(u);
    }
    
    void erasePosition(){
        position = -1;
        isSharp = false;
    }
    
    void setPosition(int pitch, boolean isTreble){
        if (!isTreble){
            this.position = new int[]{0,0,1,1,2,3,3,4,4,5,5,6,7,7,8,8,9,
                                    10,10,11,11,12,12,13,14,14,15,15,16}[pitch-1];
        } else {
            this.position = new int[]{18,18,19,20,20,21,21,22,23,23,24,24,25,25,
                                26,27,27,28,28,29,30,30,31,31,32,32,33,34}[pitch-22];
        }
        isSharp = new boolean[]{false, true, false, true, false, false, true,
                                false, true, false, true, false}[(pitch-1)%12];
    }
    
    boolean isAvailable(){
        return position >= 0;
    }
    
    void draw(Graphics2D g2){
        if (isSharp){
            g2.drawImage(sharp, 180, -position*unit/2-10, 20, 20, ReadMusic.panel);
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
  
}
