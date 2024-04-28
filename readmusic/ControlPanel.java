package readmusic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.*;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import readmusic.ReadMusic.ScaleUsed;
import static readmusic.ReadMusic.engine;
import static readmusic.ReadMusic.frame;
import static readmusic.ReadMusic.gold;
import static readmusic.ReadMusic.notes;
import static readmusic.ReadMusic.silver;
import static readmusic.ReadMusic.bigFont;
import static readmusic.ReadMusic.standardFont;

public class ControlPanel extends JPanel{
    
    PianoKeys[] keys = new PianoKeys[49];
    PianoKeys[] ivory = new PianoKeys[29];
    PianoKeys[] ebony = new PianoKeys[20];
    boolean areNotesOnKeys, areNotesOnScore, isMid_C_On, isGandF_On, areSpacingShown, 
            isBassOff, isTrebleOff, areCourtesyAccidentalsOn;
    SheetMusic sheet;
    JLabel labelA, labelB, labelC;
    JComboBox scalesSetter, parts_Setter, minutes_Setter;
    Image background, logo, icon, sign, blackscreen;
    JButton notesOnKeyboard, notesOnStaveLines, showMiddleC, showGandF, showSpacings,
            startButton, courtesyAccidentals, soundsButton;
    MyTimer timer;
    private int seconds;
    
    ControlPanel(){
        seconds = 60;
        URL u = this.getClass().getResource("pic/blackwood.png");
        background = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/lynxandsioux.png");
        logo = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/hiuz_sziu.png");
        icon = Toolkit.getDefaultToolkit().createImage(u);
        frame.setIconImage(icon);
        u = this.getClass().getResource("pic/hiuz_sziu_sziluett.png");
        sign = Toolkit.getDefaultToolkit().createImage(u);
        u = this.getClass().getResource("pic/blackglass.png");
        blackscreen = Toolkit.getDefaultToolkit().createImage(u);
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
        //controll board graphics:
        g2.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        g2.drawImage(logo, 720, 20, 240, 50, this);
        g2.drawImage(sign, 865, 230, 90, 80, this);
        g2.drawImage(blackscreen, 710, 80, 260, 130, this);
        //sheet music graphics:
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(300, 20, 400, 300, 25, 25);
        g2.translate(300,300);
        sheet.drawLines(g2);
        if(isMid_C_On) sheet.drawMiddleC(g2);
        if(isGandF_On) sheet.drawGF(g2);
        if(areNotesOnScore || areSpacingShown) sheet.drawNoteLetters(g2, areSpacingShown);
        if(isTrebleOff||isBassOff)sheet.drawVeil(g2, isTrebleOff, isBassOff);
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
        String[] s1 = {"No sign. (C Major / A minor)",
            "Chromatic scale", "1# (G Major / E minor)",
            "2# (D Major / B minor)", "3# (A Major/F-sharp minor)",
            "4# (E Major/C-sharp minor)", "5# (B Major/G-sharp minor)",
            "6# (F-sharp Major/D-sharp minor)", "7# (C-sharp Major/A-sharp minor)",
            "1b (F Major / D minor)", "2b (B-flat Major/G-minor)", 
            "3b (E-flat Major/C minor)", "4b (A-flat Major/F minor)",
            "5b (D-flat Major/B-flat minor)", "6b (G-flat Major/E-flat minor)", 
            "7b (C-flat Major/A-flat minor)"};
        scalesSetter = new JComboBox(s1);
        scalesSetter.setFont(new Font("Serif", Font.PLAIN, 12));
        scalesSetter.setBounds(15,40,175,30);
        scalesSetter.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent ie){
                int selection = scalesSetter.getSelectedIndex();
                ScaleUsed su;
                switch(selection){
                    case 0: su = ScaleUsed.NONE;
                        break;
                    case 1: su = ScaleUsed.CHROMATIC;
                        break;
                    case 2: su = ScaleUsed.SHARP1;
                        break;
                    case 3: su = ScaleUsed.SHARP2;
                        break;
                    case 4: su = ScaleUsed.SHARP3;
                        break;
                    case 5: su = ScaleUsed.SHARP4;
                        break;
                    case 6: su = ScaleUsed.SHARP5;
                        break;
                    case 7: su = ScaleUsed.SHARP6;
                        break;
                    case 8: su = ScaleUsed.SHARP7;
                        break;
                    case 9: su = ScaleUsed.FLAT1;
                        break;
                    case 10: su = ScaleUsed.FLAT2;
                        break;
                    case 11: su = ScaleUsed.FLAT3;
                        break;
                    case 12: su = ScaleUsed.FLAT4;
                        break;
                    case 13: su = ScaleUsed.FLAT5;
                        break;
                    case 14: su = ScaleUsed.FLAT6;
                        break;
                    case 15: su = ScaleUsed.FLAT7;
                        break;
                    default : su = ScaleUsed.NONE;
                }
                courtesyAccidentals.setEnabled(selection>1);
                if(areCourtesyAccidentalsOn){
                    areCourtesyAccidentalsOn = false;
                    courtesyAccidentals.setBackground(courtesyAccidentals.getForeground());
                    courtesyAccidentals.setForeground(Color.BLACK);
                    sheet.noteHead.turnCourtesyOnOff();
                }
                engine.setScaleUsed(su);
                sheet.setAccidentals(su);
                repaint();
            }    
            });
        this.add(scalesSetter);
        
        String[] s2 = {"Grand Staff", "Treble", "Bass"};
        parts_Setter = new JComboBox(s2);
        parts_Setter.setBounds(200,40,90,30);
        parts_Setter.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent ie){
                String selection = parts_Setter.getSelectedItem().toString();
                if (selection.equals("Treble")){
                    isBassOff = true; 
                    isTrebleOff = false;
                    engine.setPartOfSheet(ReadMusic.PartOfSheet.TREBLE);
                } else if (selection.equals("Bass")){
                    isBassOff = false; 
                    isTrebleOff = true;
                    engine.setPartOfSheet(ReadMusic.PartOfSheet.BASS);
                } else{
                    isBassOff = false; 
                    isTrebleOff = false;
                    engine.setPartOfSheet(ReadMusic.PartOfSheet.GRAND);
                }
                for (int i = 0; i<=20; i++){
                    keys[i].setInactive(isBassOff ? true: false);
                    
                }
                for (int i = 29; i<=48; i++){
                    keys[i].setInactive(isTrebleOff ? true: false);
                }
                repaint();
            }    
            });
        
        this.add(parts_Setter);
        
        String[] s3 = {"1 minute test time", "2 minutes test time", "half minute test time"};
        minutes_Setter = new JComboBox(s3);
        minutes_Setter.setBounds(160,280,130,30);
        minutes_Setter.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent ie){
                int selection = minutes_Setter.getSelectedIndex();
                switch (selection){
                    case 0 : seconds = 60;
                        break;
                    case 1 : seconds = 120;
                        break;
                    case 2 : seconds = 30;
                        break;
                    default : seconds = 60;
                        break;
                }
            }
        });
        this.add(minutes_Setter);
    }
    
    private void setButtons(){
        notesOnKeyboard = new JButton("Notes on keyboard");
        notesOnKeyboard.setBounds(15, 100, 130, 30);
        notesOnKeyboard.setMargin(new Insets(0,0,0,0));
        notesOnKeyboard.setFocusable(false);
        notesOnKeyboard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for (PianoKeys pk : keys){
                    pk.turnTextOnOff(false);
                }
                areNotesOnKeys =!areNotesOnKeys;
                if (areNotesOnKeys){
                    notesOnKeyboard.setBackground(Color.BLACK);
                    notesOnKeyboard.setForeground(Color.WHITE);
                } else{
                    notesOnKeyboard.setBackground(null);
                    notesOnKeyboard.setForeground(null);
                }
                if(!areNotesOnScore) showSpacings.setEnabled(!areNotesOnKeys);
                repaint();
            }
        });
        this.add(notesOnKeyboard);
        
        notesOnStaveLines = new JButton("Notes on stavelines");
        notesOnStaveLines.setBounds(15, 160, 130, 30);
        notesOnStaveLines.setMargin(new Insets(0,0,0,0));
        notesOnStaveLines.setFocusable(false);
        notesOnStaveLines.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for (PianoKeys pk : keys){
                    areNotesOnScore = !areNotesOnScore;
                    repaint();
                }
                if (areNotesOnScore){
                    notesOnStaveLines.setBackground(silver);
                    notesOnStaveLines.setForeground(gold);
                } else{
                    notesOnStaveLines.setBackground(null);
                    notesOnStaveLines.setForeground(null);
                }
                if(!areNotesOnKeys) showSpacings.setEnabled(!areNotesOnScore);
            }
        });
        this.add(notesOnStaveLines);
        
        showSpacings = new JButton("Show \"ACEG FACE\"");
        showSpacings.setBounds(15, 220, 130, 30);
        showSpacings.setMargin(new Insets(0,0,0,0));
        showSpacings.setFocusable(false);
        showSpacings.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                areSpacingShown = !areSpacingShown;
                int[]spacingKeys = {10, 13, 17, 20,  30, 34, 37, 41};
                for(int i : spacingKeys){
                    keys[i-1].turnTextOnOff(true);
                }
                showSpacings.setText((areSpacingShown ? "Hide" :"Show") + " \"ACEG FACE\"");
                showSpacings.setForeground(areSpacingShown ? gold : null);
                showSpacings.setBackground(areSpacingShown ? silver: null);
                notesOnStaveLines.setEnabled(!areSpacingShown);
                notesOnKeyboard.setEnabled(!areSpacingShown);
                repaint();
            }
        });
        this.add(showSpacings);
        
        courtesyAccidentals = new JButton("Courtesy accidentals");
        courtesyAccidentals.setBounds(160, 100, 130, 30);
        courtesyAccidentals.setMargin(new Insets(0,0,0,0));
        //courtesyAccidentals.setBackground(Color.WHITE);
        courtesyAccidentals.setForeground(Color.BLACK);
        courtesyAccidentals.setFocusable(false);
        courtesyAccidentals.setEnabled(false);
        courtesyAccidentals.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                areCourtesyAccidentalsOn = !areCourtesyAccidentalsOn;
                sheet.noteHead.turnCourtesyOnOff();
                Color backColor = courtesyAccidentals.getBackground();
                Color foreColor = courtesyAccidentals.getForeground();
                courtesyAccidentals.setBackground(foreColor);
                courtesyAccidentals.setForeground(backColor);
            }
        });
        this.add(courtesyAccidentals);
                
        showMiddleC = new JButton("Show middle C");
        showMiddleC.setBounds(160, 160, 130, 30);
        showMiddleC.setMargin(new Insets(0,0,0,0));
        showMiddleC.setFocusable(false);
        showMiddleC.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                isMid_C_On = !isMid_C_On;
                keys[24].setBackground(isMid_C_On ? Color.RED : Color.WHITE); 
                                                //Number 24 is middle C
                showMiddleC.setText((isMid_C_On ? "Hide" :"Show") + " middle C");
                showMiddleC.setBackground(isMid_C_On ? Color.RED : null);
                repaint();
            }
        });
        this.add(showMiddleC);
        
        showGandF = new JButton("Show G and F");
        showGandF.setBounds(160, 220, 130, 30);
        showGandF.setMargin(new Insets(0,0,0,0));
        showGandF.setFocusable(false);
        showGandF.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                isGandF_On = ! isGandF_On;
                keys[17].setBackground(isGandF_On ? silver : Color.WHITE); 
                                                //Number 17 is F
                keys[31].setBackground(isGandF_On ? gold : Color.WHITE); 
                                                //Number 31 is G
                showGandF.setText((isGandF_On ? "Hide" :"Show") + " G and F");
                showGandF.setBackground(isGandF_On ? gold : null);
                showGandF.setForeground(isGandF_On ? silver : null);
                repaint();
            }
        });
        this.add(showGandF);
        
        startButton = new JButton("Start");
        startButton.setBounds(745, 230, 80, 80);
        startButton.setMargin(new Insets(0,0,0,0));
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String buttonName = startButton.getText();
                switch(buttonName){
                    case "Start": startSettings();
                        break;
                    case "Quit": quitSettings();
                        break;
                    case "OK" : cleanUp();
                        break;
                }
                
                
            }
        });
        this.add(startButton);
        
        soundsButton = new JButton("turn Sounds ON");
        soundsButton.setBounds(15, 280, 130, 30);
        soundsButton.setMargin(new Insets(0,0,0,0));
        soundsButton.setFocusable(false);
        soundsButton.setBackground(gold);
        soundsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for(PianoKeys pk: keys){
                    pk.turnSoundOnOff();
                }
                boolean ison = soundsButton.getText().contains("OFF");
                soundsButton.setText("turn Sounds " +(ison ? "ON" : "OFF"));
                soundsButton.setBackground((ison ? (gold) : null));
            }
        });
        this.add(soundsButton);
    }
    
    private void setLabels(){
        labelA = new JLabel("",SwingConstants.CENTER);
        labelA.setFont(standardFont);
        labelA.setBounds(730, 90, 220, 30);
        labelA.setForeground(Color.WHITE);
        labelA.setOpaque(false);
        labelB = new JLabel("",SwingConstants.CENTER);
        labelB.setFont(bigFont);
        labelB.setBounds(730, 130, 220, 30);
        labelB.setOpaque(false);
        labelC = new JLabel("",SwingConstants.CENTER);
        labelC.setFont(standardFont);
        labelC.setBounds(730, 165, 220, 30);
        labelC.setForeground(Color.WHITE);
        labelC.setOpaque(false);
        this.add(labelA);
        this.add(labelB);
        this.add(labelC);
    }
    
    void startSettings(){
        labelC.setForeground(Color.WHITE);
        labelC.setText("Hit the corresponding key!");
        engine.selectPitch();
        timer = new MyTimer(labelA, seconds, (ControlPanel)(startButton.getParent()));
        startButton.setText("Quit");
        notesOnStaveLines.setEnabled(false);
        notesOnKeyboard.setEnabled(false);
        showSpacings.setEnabled(false);
        showMiddleC.setEnabled(false);
        showGandF.setEnabled(false);
        courtesyAccidentals.setEnabled(false);
        scalesSetter.setEnabled(false);
        parts_Setter.setEnabled(false);
        minutes_Setter.setEnabled(false);
        for(PianoKeys pk: keys){
            pk.setQuiz(true);
        }
    }
    
    void quitSettings(){
        timer.stop();
        timer = null;
        sheet.noteHead.erasePosition();
        labelC.setForeground(Color.RED);
        labelC.setText("Test interrupted");
        for(PianoKeys pk: keys){
            pk.stopSounds();
        }
        repaint();
        for(PianoKeys pk: keys){
            pk.setQuiz(false);
        }
        startButton.setText("OK");
    }
    
    void endSetting(){
        timer = null;
        sheet.noteHead.erasePosition();
        labelC.setText("");
        for(PianoKeys pk: keys){
            pk.stopSounds();
        }
        repaint();
        for(PianoKeys pk: keys){
            pk.setQuiz(false);
        }
        int mistakes = engine.getCounterNum()-engine.getRightChoices();
        String message1 = engine.getRightChoices()
                + (engine.getRightChoices() > 1 ? " points" : " point")  
                + "  &  " + mistakes + 
                (mistakes > 1 ? " mistakes," : " mistake,");
        int optionCounter = 0;
        if(areNotesOnKeys) optionCounter++;
        if(areNotesOnScore) optionCounter++;
        if(isMid_C_On) optionCounter++;
        if(isGandF_On) optionCounter++;
        if(areCourtesyAccidentalsOn) optionCounter++;
        if(areSpacingShown) optionCounter++;
        String message2 = (optionCounter==0 ? "with no":
                            "with " + optionCounter)
                +" helping option" +(optionCounter == 1 ? "." : "s.");

        labelA.setText("Your results are:");
        labelB.setForeground(Color.WHITE);
        labelB.setFont(standardFont);
        labelB.setText(message1);
        labelC.setForeground(Color.WHITE);
        labelC.setText(message2);
        //JOptionPane.showMessageDialog(frame, message,"TIME IS OVER",JOptionPane.INFORMATION_MESSAGE);
        startButton.setText("OK");
    }
    
    void cleanUp(){
        labelA.setText("");
        labelB.setFont(bigFont);
        labelB.setText("");
        labelC.setText("");
        scalesSetter.setEnabled(true);
        parts_Setter.setEnabled(true);
        minutes_Setter.setEnabled(true);
        if(!areSpacingShown)notesOnKeyboard.setEnabled(true);
        if(!areSpacingShown)notesOnStaveLines.setEnabled(true);
        if(!areNotesOnKeys && !areNotesOnScore)showSpacings.setEnabled(true);
        showMiddleC.setEnabled(true);
        showGandF.setEnabled(true);
        if(scalesSetter.getSelectedIndex() > 1)courtesyAccidentals.setEnabled(true);
        startButton.setText("Start");
        engine.reset();
    }   
}

