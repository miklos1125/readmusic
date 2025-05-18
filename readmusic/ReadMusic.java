package readmusic;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;

public class ReadMusic{
    
    enum ScaleUsed{NONE, CHROMATIC, SHARP1, SHARP2, SHARP3, SHARP4, SHARP5, SHARP6, SHARP7,
                            FLAT1, FLAT2, FLAT3, FLAT4, FLAT5, FLAT6, FLAT7};
    enum PartOfSheet{GRAND, TREBLE, BASS};
    enum Accidental{NONE, SHARP, FLAT, NATURAL};
    static JFrame frame;
    static ControlPanel panel;
    static String[][] notes = {{"C", "B#"},{"C#", "Db"}, {"D", ""}, {"D#", "Eb"}, 
                                {"E", "Fb"}, {"F", "E#"}, {"F#", "Gb"}, {"G", ""}, 
                                {"G#", "Ab"}, {"A", ""}, {"A#", "Bb"}, {"B", "Cb"}};
    static Sounds sounds;
    static Engine engine;
    static Color gold, silver, veil;
    static Font bigFont, standardFont, mediumFont;
    
    public static void main(String[] args) {
        frame = new JFrame("Music Reader");
        frame.setBounds(100, 100, 1060, 570);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        gold = new Color(233,206,0);
        silver = new Color(105, 118, 180);
        veil = new Color(255,255,255,235);
        bigFont = new Font(Font.DIALOG, Font.BOLD, 24);
        mediumFont = new Font(Font.DIALOG, Font.PLAIN, 21);
        standardFont = new Font(Font.DIALOG, Font.BOLD, 16);
        panel = new ControlPanel();
        frame.setContentPane(panel);
        frame.setVisible(true);
        sounds = new Sounds();
        engine = new Engine();
     }     
}



