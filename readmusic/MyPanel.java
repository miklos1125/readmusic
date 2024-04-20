package readmusic;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import static readmusic.ReadMusic.*;

public class MyPanel extends JPanel{
    
    PianoKeys[] keys = new PianoKeys[49];
    PianoKeys[] ivory = new PianoKeys[29];
    PianoKeys[] ebony = new PianoKeys[20];
    boolean notesOnKeys, notesOnScore, midC;
    SheetMusic sheet;
    JLabel labelA, labelB, labelC;
    JComboBox scales, parts;
    Image background, logo;
    JButton notesOnKeyboard, notesOnStaveLines, showMiddleC, startButton, soundsButton;
    MyTimer timer;
    final int SECONDS;
    
    MyPanel(){
        //setBackground(Color.GRAY);
        SECONDS = 60;
        URL u = this.getClass().getResource("pic/blackwood.png");
        background = Toolkit.getDefaultToolkit().createImage(u);
        URL u2 = this.getClass().getResource("pic/bosendorfer.png");
        logo = Toolkit.getDefaultToolkit().createImage(u2);
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
        g2.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        g2.drawImage(logo, 720, 30, 240, 50, this);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(300, 20, 400, 300, 25, 25);
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
        String[] s1 = {"No sign. (C Major / A Minor)",
            "Chromatic scale", "1#  (G Major / E Minor)",
            "2#  (D Major / B Minor)", "3#  (A Major/F-sharp Minor)",
            "4#  (E Major/C-sharp Minor)", "5#  (B Major/G-sharp Minor)"};
        scales = new JComboBox(s1);
        scales.setFont(new Font("Serif", Font.PLAIN, 12));
        scales.setBounds(15,40,175,30);
        this.add(scales);
        
        String[] s2 = {"Grand Staff", "Treble", "Bass"};
        parts = new JComboBox(s2);
        parts.setBounds(200,40,90,30);
        this.add(parts);
    }
    
    private void setButtons(){
        notesOnKeyboard = new JButton("Notes on keyboard");
        notesOnKeyboard.setBounds(80, 100, 120, 30);
        notesOnKeyboard.setMargin(new Insets(0,0,0,0));
        notesOnKeyboard.setFocusable(false);
        notesOnKeyboard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for (PianoKeys pk : keys){
                    pk.turnTextOnOff();
                }
                notesOnKeys =!notesOnKeys;
            }
        });
        this.add(notesOnKeyboard);
        
        notesOnStaveLines = new JButton("Notes on stavelines");
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
        
        showMiddleC = new JButton("Show middle C");
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
        
        startButton = new JButton("Start");
        startButton.setBounds(780, 280, 120, 30);
        startButton.setMargin(new Insets(0,0,0,0));
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                panel.labelC.setText("Hit the corresponding key!");
                engine.selectPitch();
                timer = new MyTimer(labelA, SECONDS, (MyPanel)(startButton.getParent()));
                startButton.setEnabled(false);
                showMiddleC.setEnabled(false);
                notesOnStaveLines.setEnabled(false);
                notesOnKeyboard.setEnabled(false);
                scales.setEnabled(false);
                parts.setEnabled(false);
                for(PianoKeys pk: keys){
                    pk.quiz = true;
                }
            }
        });
        this.add(startButton);
        
        soundsButton = new JButton("Sounds on");
        Color gold = new Color(233,206,0);
        soundsButton.setBounds(80, 280, 120, 30);
        soundsButton.setMargin(new Insets(0,0,0,0));
        soundsButton.setFocusable(false);
        soundsButton.setBackground(gold);
        soundsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for(PianoKeys pk: keys){
                    pk.turnSoundOnOff();
                }
                boolean ison = soundsButton.getText().contains("off");
                soundsButton.setText("Sounds " +(ison ? "on" : "off"));
                soundsButton.setBackground((ison ? (gold) : null));
            }
        });
        this.add(soundsButton);
    }
    
    private void setLabels(){
        Font big = new Font(Font.DIALOG, Font.BOLD, 24);
        Font standard = new Font(Font.DIALOG, Font.BOLD, 16);
        labelA = new JLabel("",SwingConstants.CENTER);
        labelA.setFont(standard);
        labelA.setBounds(780, 100, 120, 30);
        labelA.setForeground(Color.WHITE);
        labelA.setOpaque(false);
        labelB = new JLabel("",SwingConstants.CENTER);
        labelB.setFont(big);
        labelB.setBounds(780, 160, 120, 30);
        labelB.setOpaque(false);
        labelC = new JLabel("",SwingConstants.CENTER);
        labelC.setFont(standard);
        labelC.setBounds(730, 220, 220, 30);
        labelC.setForeground(Color.WHITE);
        labelC.setOpaque(false);
        this.add(labelA);
        this.add(labelB);
        this.add(labelC);
    }
    
    void endSetting(){
        timer = null;
        sheet.noteHead.erasePosition();
        labelC.setText("");
        repaint();
        for(PianoKeys pk: keys){
            pk.quiz = false;
        }
        String message ="You collected: " + engine.getRightChoices() 
                + (engine.getRightChoices() > 1 ? " points" : " point") +"\n" 
                + "out of " + engine.getCounterNum() + " attempts,\n"
                + (notesOnKeys||notesOnScore||midC ? "with the help of:\n":
                            "and haven't used any help.")
                +(notesOnKeys ? "notes on the keyboard;\n":"")
                +(notesOnScore ? "notes on the stavelines;\n ":"")
                +(midC ? "showing middle C;":"");
        
        JOptionPane.showMessageDialog(this, message,"TIME IS OVER",JOptionPane.INFORMATION_MESSAGE);
        labelA.setText("");
        labelB.setText("");
        scales.setEnabled(true);
        parts.setEnabled(true);
        notesOnKeyboard.setEnabled(true);
        notesOnStaveLines.setEnabled(true);
        showMiddleC.setEnabled(true);
        startButton.setEnabled(true);
        engine.reset();
    }
}