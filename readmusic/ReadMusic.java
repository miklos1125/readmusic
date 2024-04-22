package readmusic;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import static readmusic.ReadMusic.*;

public class ReadMusic{
    
    static JFrame frame;
    static MyPanel panel;
    static String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    static Sounds sounds;
    static GameEngine engine;
    static Color gold, silver, veil;
    
    public static void main(String[] args) {
        frame = new JFrame("Read Music Sheets");
        frame.setBounds(100, 100, 1000, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        gold = new Color(233,206,0);
        silver = new Color(95, 108, 140);
        veil = new Color(255,255,255,235);
        panel = new MyPanel();
        frame.setContentPane(panel);
        frame.setVisible(true);
        sounds = new Sounds();
        engine = new GameEngine();
        //URL u = frame.getClass().getResource("pic/pianoicon.png");
        Image icon = Toolkit.getDefaultToolkit().getImage("pic/trebleclef.png");
        frame.setIconImage(icon);
    }     
}

class GameEngine{
    int pitch;
    boolean isTreble;
    private int counter, rightChoice;
    int[] standardRow = {1,3,5,6,8,10,12,13,15,17,18,20,22,24,25,27,
                    29,30,32,34,36,37,39,41,42,44,46,48,49};
                   
    void selectPitch(){
        int newPitch;
        //This part can be changed to Enums:
        String setting = panel.scales.getSelectedItem().toString();
        String settingParts = panel.parts.getSelectedItem().toString();
        do{
            if (setting.equals("Chromatic scale")){
                switch(settingParts){
                    case "Treble": newPitch = (int)(Math.random()*28+22);
                        break;
                    case "Bass": newPitch = (int)(Math.random()*29+1);
                        break;
                    default: newPitch = (int)(Math.random()*49+1);
                        break;
                }
                
            } else {
                switch(settingParts){
                    case "Treble": newPitch = standardRow[(int)(Math.random()*17)+12];
                        break;
                    case "Bass": newPitch = standardRow[(int)(Math.random()*17)];
                        break;
                    default: newPitch = standardRow[(int)(Math.random()*29)];
                        break;
                }
                
                //System.out.println(newPitch +" main");
            
                switch(setting){  //pitch corrections by scales
                    case "No sign. (C Major / A Minor)" : //none
                        break;
                    case "5#  (B Major/G-sharp Minor)" :
                        if(newPitch == 10||newPitch == 22||newPitch == 34||newPitch == 46){
                            newPitch++;
                            break;
                        } //else fall through            
                    case "4#  (E Major/C-sharp Minor)" :
                        if(newPitch == 3||newPitch == 15||newPitch == 27||newPitch == 39){
                            newPitch++;
                            break;
                        } //else fall through
                    case "3#  (A Major/F-sharp Minor)" :
                        if(newPitch == 8||newPitch == 20||newPitch == 32||newPitch == 44){
                            newPitch++;
                            break;
                        } //else fall through
                    case "2#  (D Major / B Minor)" :
                        if(newPitch == 1||newPitch == 13||newPitch == 25||newPitch == 37){
                            newPitch++;
                            break;
                        } else if(newPitch == 49){ //highest C key can't be modified
                            newPitch = pitch;
                            break;
                        } //else fall through
                    case "1#  (G Major / E Minor)" :
                        if(newPitch == 6||newPitch == 18||newPitch == 30||newPitch == 42){
                        newPitch++;
                        }
                        break;
                }
            }
        } while (newPitch == pitch);
        pitch = newPitch;
        if (pitch>29 || settingParts.equals("Treble")){
            isTreble = true;
        } else if (pitch<22|| settingParts.equals("Bass")){
            isTreble = false;
        } else {
            isTreble = (int)(Math.random()*2) == 0;
        }
        panel.sheet.noteHead.setPosition(pitch, isTreble);
        panel.repaint();
        //System.out.println(pitch + " " + (isTreble? "treble" : "bass"));
    }
    /*String name argument isn't used yet*/
    void checkSolution(int keyNumber, String name){
        if (keyNumber == pitch){
            panel.labelC.setForeground(Color.WHITE);
            panel.labelC.setText("Hit the corresponding key!");
            panel.labelB.setForeground(Color.WHITE);
            panel.labelB.setText(++counter + " / " + ++rightChoice);
            selectPitch();
        } else{
            panel.labelC.setForeground(Color.RED);
            if((keyNumber-pitch)%12==0){
                name = name.substring(0, name.indexOf('-'));
                panel.labelC.setText("That's a different " + name);
            } else {
                panel.labelC.setText("Try again!");
            }
            panel.labelB.setForeground(Color.RED);
            panel.labelB.setText(++counter + " / " + rightChoice);
        }
    }
    
    public int getCounterNum(){
        return counter;
    }
            
    public int getRightChoices(){
        return rightChoice;
    }
    
    void reset(){
        counter = 0;
        rightChoice = 0;
        pitch = 0;
    }
    
    
}




