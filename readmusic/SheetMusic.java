package readmusic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.net.URL;
import readmusic.ReadMusic.ScaleUsed;
import static readmusic.ReadMusic.gold;
import static readmusic.ReadMusic.silver;

public class SheetMusic {
    
    private Image treble; 
    private Image bass; 
    NoteHead noteHead;
    private ScaleUsed scale;
    
    static private SheetMusic instance;
    static final private int UNIT = 15;
    
    public static SheetMusic getSheetMusic(){
        if (instance == null){
            instance = new SheetMusic();
        }
        return instance;
    }
    
    private SheetMusic(){    
        noteHead = new NoteHead();
        scale = ScaleUsed.NONE;
        URL u = this.getClass().getResource("pic/trebleclef.png");
        treble = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/bassclef.png");
        bass = Toolkit.getDefaultToolkit().createImage(u);
    }
    
    public void setAccidentals(ScaleUsed scale){
        this.scale = scale;
    }
    
    void drawLines(Graphics2D g2){
        g2.setColor(Color.BLACK);
        for(int i = -2*UNIT; i >= -6*UNIT; i-=UNIT){
            g2.drawLine(20, i, 380, i);
        }
        for(int i = -11*UNIT; i >= -15*UNIT; i-=UNIT){
            g2.drawLine(20, i, 380, i);
        }
        g2.drawLine(20, -2*UNIT, 20, -6*UNIT);
        g2.drawLine(20, -11*UNIT, 20, -15*UNIT);
        g2.drawLine(380, -2*UNIT, 380, -6*UNIT);
        g2.drawLine(380, -11*UNIT, 380, -15*UNIT);
        g2.drawImage(treble, UNIT+7, -17*UNIT+4, 40, 110, ReadMusic.panel);
        g2.drawImage(bass, UNIT+7, -6*UNIT-1, 44, 50, ReadMusic.panel);
        if (noteHead.isAvailable()){
            noteHead.draw(g2);
        }
        drawAccidentals(g2);
    }
    
    void drawAccidentals(Graphics2D g2){
        int CALIB_VERTICAL = 3;
        int UNIT_VERTICAL = 10;
        int START_VERTICAL = UNIT*4+CALIB_VERTICAL;
        int SIZE = 22;
        switch (scale){
            case NONE : noteHead.setAccidental(false);
                break;
            case CHROMATIC : noteHead.setAccidental(true);
                break;
            case SHARP7 :   g2.drawImage(noteHead.sharp, START_VERTICAL+6*UNIT_VERTICAL, -14*UNIT+4, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.sharp, START_VERTICAL+6*UNIT_VERTICAL, -4*UNIT+4, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case SHARP6 :   g2.drawImage(noteHead.sharp, START_VERTICAL+5*UNIT_VERTICAL, -15*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.sharp, START_VERTICAL+5*UNIT_VERTICAL, -5*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case SHARP5 :   g2.drawImage(noteHead.sharp, START_VERTICAL+4*UNIT_VERTICAL, -13*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.sharp, START_VERTICAL+4*UNIT_VERTICAL, -3*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case SHARP4 :   g2.drawImage(noteHead.sharp, START_VERTICAL+3*UNIT_VERTICAL, -15*UNIT+4, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.sharp, START_VERTICAL+3*UNIT_VERTICAL, -5*UNIT+4, SIZE, SIZE, ReadMusic.panel);
                //fall through    
            case SHARP3 :   g2.drawImage(noteHead.sharp, START_VERTICAL+2*UNIT_VERTICAL, -16*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.sharp, START_VERTICAL+2*UNIT_VERTICAL, -6*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case SHARP2 :   g2.drawImage(noteHead.sharp, START_VERTICAL+UNIT_VERTICAL, -14*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.sharp, START_VERTICAL+UNIT_VERTICAL, -4*UNIT-3, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case SHARP1 :   g2.drawImage(noteHead.sharp, START_VERTICAL, -16*UNIT+4, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.sharp, START_VERTICAL, -6*UNIT+4, SIZE, SIZE, ReadMusic.panel);
                            noteHead.setAccidental(false);
                break;
            case FLAT7 :    g2.drawImage(noteHead.flat, START_VERTICAL+6*UNIT_VERTICAL, -12*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.flat, START_VERTICAL+6*UNIT_VERTICAL, -2*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case FLAT6 :    g2.drawImage(noteHead.flat, START_VERTICAL+5*UNIT_VERTICAL, -14*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.flat, START_VERTICAL+5*UNIT_VERTICAL, -4*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case FLAT5 :    g2.drawImage(noteHead.flat, START_VERTICAL+4*UNIT_VERTICAL, -13*UNIT, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.flat, START_VERTICAL+4*UNIT_VERTICAL, -3*UNIT, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case FLAT4 :    g2.drawImage(noteHead.flat, START_VERTICAL+3*UNIT_VERTICAL, -15*UNIT, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.flat, START_VERTICAL+3*UNIT_VERTICAL, -5*UNIT, SIZE, SIZE, ReadMusic.panel);
                //fall through    
            case FLAT3 :    g2.drawImage(noteHead.flat, START_VERTICAL+2*UNIT_VERTICAL, -13*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.flat, START_VERTICAL+2*UNIT_VERTICAL, -3*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case FLAT2 :    g2.drawImage(noteHead.flat, START_VERTICAL+UNIT_VERTICAL, -15*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.flat, START_VERTICAL+UNIT_VERTICAL, -5*UNIT-7, SIZE, SIZE, ReadMusic.panel);
                //fall through
            case FLAT1 :    g2.drawImage(noteHead.flat, START_VERTICAL, -14*UNIT, SIZE, SIZE, ReadMusic.panel);
                            g2.drawImage(noteHead.flat, START_VERTICAL, -4*UNIT, SIZE, SIZE, ReadMusic.panel);
                            noteHead.setAccidental(false);
                break;
        }
    }
    
    void drawNoteLetters(Graphics g2, boolean spacingsOn){
        char[] letters1 = {'B', 'D', 'F', 'A', 'C', 'E', 'G', 'B'};
        char[] letters2 = {'A', 'C', 'E', 'G', 'B', 'D', 'F', 'A', 'C',};
        char[] letters3 = {'D', 'F', 'A', 'C', 'E', 'G', 'B', 'D'};
        char[] letters4 = {'C', 'E', 'G', 'B', 'D', 'F', 'A', 'C', 'E'};
        g2.setFont(new Font("Serif", Font.BOLD, UNIT));
        if (spacingsOn){
            for(int i = 2; i<letters1.length-2; i++){
                g2.setColor(ReadMusic.gold);
                g2.drawString(String.valueOf(letters1[i]), 360, -137-i*UNIT);
                g2.setColor(ReadMusic.silver);
                g2.drawString(String.valueOf(letters3[i]), 360, -2-i*UNIT);
            }
        }else{
            for(int i = 0; i<letters1.length; i++){
                g2.setColor(ReadMusic.gold);
                g2.drawString(String.valueOf(letters1[i]), 340, -137-i*UNIT);
                g2.setColor(ReadMusic.silver);
                g2.drawString(String.valueOf(letters3[i]), 360, -2-i*UNIT);
            }

            g2.setColor(new Color(255,255,255, 180));
            g2.fillRect(357, -18*UNIT, 17, 10*UNIT);
            g2.fillRect(337, -9*UNIT, 17, 10*UNIT);
            for(int i = 0; i<letters2.length; i++){
                g2.setColor(ReadMusic.gold);
                g2.drawString(String.valueOf(letters2[i]), 360, -130-i*UNIT);
                g2.setColor(ReadMusic.silver);
                g2.drawString(String.valueOf(letters4[i]), 340, 6-i*15);
            }
        }
    }
    
    void drawMiddleC(Graphics2D g2){
        g2.setColor(Color.RED);
        for (int i = 25; i<=360; i+=30){
            g2.drawLine(i, -10*UNIT, i+20, -10*UNIT);
            g2.drawLine(i, -7*UNIT, i+20, -7*UNIT);
        }
        //g2.drawLine(160, -10*UNIT, 260, -10*UNIT);
        //g2.drawLine(160, -7*UNIT, 260, -7*UNIT);
    }
    
    void drawVeil(Graphics2D g2, boolean isTrebleVeil, boolean isBassVeil){
        g2.setColor(ReadMusic.veil);
        if(isTrebleVeil){
            g2.fillRect(0, -18*UNIT, 400, (int)(9.5*UNIT));
        }
        if(isBassVeil){
            g2.fillRect(0, (int)(-8.5*UNIT), 400, 9*UNIT);
        }
    }

    void drawGF(Graphics2D g2) {
        g2.setColor(gold);
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(60, -12*UNIT, 380, -12*UNIT);
        g2.setColor(silver);
        g2.drawLine(60, -5*UNIT, 380, -5*UNIT);
    }
}
