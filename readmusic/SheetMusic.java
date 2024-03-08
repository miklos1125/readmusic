package readmusic;

import java.awt.*;
import java.net.URL;

public class SheetMusic {
    
    private Image treble; //= Toolkit.getDefaultToolkit().getImage("trebleclef.png");
    private Image bass; // = Toolkit.getDefaultToolkit().getImage("bassclef.png");
    NoteHead noteHead;
    
    static private SheetMusic instance;
    static final private int unit = 15;
    
    public static SheetMusic getSheetMusic(){
        if (instance == null){
            instance = new SheetMusic();
        }
        return instance;
    }
    
    private SheetMusic(){    
        noteHead = new NoteHead();
        
        URL u = this.getClass().getResource("pic/trebleclef.png");
        treble = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/bassclef.png");
        bass = Toolkit.getDefaultToolkit().createImage(u);
    }
    
    void drawLines(Graphics2D g2){
        g2.setColor(Color.BLACK);
        for(int i = -2*unit; i >= -6*unit; i-=unit){
            g2.drawLine(20, i, 380, i);
        }
        for(int i = -11*unit; i >= -15*unit; i-=unit){
            g2.drawLine(20, i, 380, i);
        }
        g2.drawLine(20, -2*unit, 20, -6*unit);
        g2.drawLine(20, -11*unit, 20, -15*unit);
        g2.drawLine(380, -2*unit, 380, -6*unit);
        g2.drawLine(380, -11*unit, 380, -15*unit);
        g2.drawImage(treble, unit+10, -17*unit+4, 40, 110, ReadMusic.panel);
        g2.drawImage(bass, unit+10, -6*unit-1, 50, 50, ReadMusic.panel);
        if (noteHead.isAvailable()){
            noteHead.draw(g2);
        }
    }
    
    void drawNoteLetters(Graphics g2){
        char[] letters1 = {'B', 'D', 'F', 'A', 'C', 'E', 'G', 'B'};
        char[] letters2 = {'A', 'C', 'E', 'G', 'B', 'D', 'F', 'A', 'C',};
        char[] letters3 = {'D', 'F', 'A', 'C', 'E', 'G', 'B', 'D'};
        char[] letters4 = {'C', 'E', 'G', 'B', 'D', 'F', 'A', 'C', 'E'};
        g2.setFont(new Font("Serif", Font.BOLD, unit));
        for(int i = 0; i<letters1.length; i++){
            g2.setColor(Color.BLUE);
            g2.drawString(String.valueOf(letters1[i]), 340, -137-i*unit);
            g2.setColor(Color.GREEN);
            g2.drawString(String.valueOf(letters3[i]), 360, -2-i*unit);
        }
        
        g2.setColor(new Color(255,255,255, 180));
        g2.fillRect(357, -18*unit, 17, 10*unit);
        g2.fillRect(337, -9*unit, 17, 10*unit);
        for(int i = 0; i<letters2.length; i++){

            g2.setColor(Color.BLUE);
            g2.drawString(String.valueOf(letters2[i]), 360, -130-i*unit);
            g2.setColor(Color.GREEN);
            g2.drawString(String.valueOf(letters4[i]), 340, 6-i*15);
        }
    }
    
    void drawMiddleC(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.drawLine(20, -10*unit, 380, -10*unit);
        g2.drawLine(20, -7*unit, 380, -7*unit);
    }
}
