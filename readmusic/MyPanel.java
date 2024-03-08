package readmusic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static readmusic.ReadMusic.*;

public class MyPanel extends JPanel{
    
    PianoKeys[] keys = new PianoKeys[49];
    PianoKeys[] ivory = new PianoKeys[29];
    PianoKeys[] ebony = new PianoKeys[20];
    boolean notesOnScore, midC;
    SheetMusic sheet;
    Label labelA, labelB;
    JComboBox scales, parts;
    
    MyPanel(){
        setBackground(Color.GRAY);
        setLayout(null);
        setDoubleBuffered(true);
        setKeyboard();
        setSettings();
        setButtons();
        setLabels();
        sheet = SheetMusic.getSheetMusic();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRect(300,20,400,300);
        g2.translate(300,300);
        sheet.drawLines(g2);
        if(notesOnScore) sheet.drawNoteLetters(g2);
        if(midC) sheet.drawMiddleC(g2);
        g2.translate(-300,-300);   
    }
    
    private void setKeyboard(){
        int i = 0, note = i, octave = 2;
        for (; i < keys.length; i++){
            keys[i] = new PianoKeys(notes[note], octave, i+1);
            note++;
            if (note >= notes.length){
                note = 0;
                octave++;
            }
        }
        int counterW = 0, counterB = 0;
        for (PianoKeys pk : keys){
            if(pk.getNote().endsWith("#")){
                pk.setPlace(counterB); 
                ebony[counterB++] = pk;
            } else {
                pk.setPlace(counterW);
                ivory[counterW++] = pk;
            }
        }
        
        JLayeredPane lp = frame.getLayeredPane();
        
        int modifier = 0;
        for (PianoKeys pk: ebony){
            pk.setBounds(pk.getPlace()*33+38+modifier, 350, 19, 100);  
            lp.add(pk, new Integer(2));
            if (pk.getNote().equals("D#") || pk.getNote().equals("A#")){
                modifier += 33;
            }
        }
        
        for (PianoKeys pk: ivory){
            pk.setBounds(pk.getPlace()*33+15, 350, 33, 150);
            lp.add(pk, new Integer(1));
        }
    }
    
    private void setSettings(){
        scales = new JComboBox(new String[]{"Chromatic scale",
            "No sign. (C Major / A Minor)", "1#  (G Major / E Minor)",
            "2#  (D Major / B Minor)", "3#  (A Major/F-sharp Minor)",
            "4#  (E Major/C-sharp Minor)", "5#  (B Major/G-sharp Minor)"});
        scales.setFont(new Font("Serif", Font.PLAIN, 12));
        scales.setBounds(20,40,150,30);
        this.add(scales);
        
        parts = new JComboBox(new String[]{"Grand Staff", "Treble", "Bass"});
        parts.setBounds(180,40,90,30);
        this.add(parts);
    }
    
    private void setButtons(){
        JButton notesOnKeyboard = new JButton("Notes on keyboard");
        notesOnKeyboard.setBounds(80, 100, 120, 30);
        notesOnKeyboard.setMargin(new Insets(0,0,0,0));
        notesOnKeyboard.setFocusable(false);
        notesOnKeyboard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for (PianoKeys pk : keys){
                    pk.turnTextOnOff();
                }
            }
        });
        this.add(notesOnKeyboard);
        
        JButton notesOnStaveLines = new JButton("Notes on stavelines");
        notesOnStaveLines.setBounds(80, 160, 120, 30);
        notesOnStaveLines.setMargin(new Insets(0,0,0,0));
        notesOnStaveLines.setFocusable(false);
        notesOnStaveLines.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for (PianoKeys pk : keys){
                    notesOnScore = !notesOnScore;
                    repaint();
                }
            }
        });
        this.add(notesOnStaveLines);
        
        JButton showMiddleC = new JButton("Show middle C");
        showMiddleC.setBounds(80, 220, 120, 30);
        showMiddleC.setMargin(new Insets(0,0,0,0));
        showMiddleC.setFocusable(false);
        showMiddleC.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for (PianoKeys pk : keys){
                    midC = !midC;
                    if (keys[24].getBackground().equals(Color.WHITE)){
                        keys[24].setBackground(Color.RED);
                    } else {
                        keys[24].setBackground(Color.WHITE);
                    }
                    
                    repaint();
                }
            }
        });
        this.add(showMiddleC);
        
        JButton startButton = new JButton("Start");
        startButton.setBounds(80, 280, 120, 30);
        startButton.setMargin(new Insets(0,0,0,0));
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                engine.selectPitch();
                startButton.setEnabled(false);
                for(PianoKeys pk: keys){
                    pk.quiz = true;
                }
            }
        });
        this.add(startButton);
    }
    
    private void setLabels(){
        labelA = new Label();
        labelA.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        labelA.setBounds(750, 80, 180, 50);
        labelB = new Label();
        labelB.setFont(new Font(Font.SERIF, Font.PLAIN, 50));
        labelB.setBounds(800, 200, 150, 40);
        this.add(labelA);
        this.add(labelB);
    }
}