package readmusic;

import java.awt.Color;
import readmusic.ReadMusic.PartOfSheet;
import readmusic.ReadMusic.ScaleUsed;
import readmusic.ReadMusic.Accidental;
import static readmusic.ReadMusic.panel;

public class Engine{
    private ScaleUsed scaleUsed;
    private PartOfSheet partOfSheet;
    private PartOfSheet whereToShow;
    private Accidental accidental;
    private int pitch;
    private boolean isHalfNote, isBCEF;
    private int counter, rightChoice;
    private int[] standardRow = {1,3,5,6,8,10,12,13,15,17,18,20,22,24,25,27,
                    29,30,32,34,36,37,39,41,42,44,46,48,49};
    Engine(){
        scaleUsed = ScaleUsed.NONE;
        partOfSheet = PartOfSheet.GRAND;
        accidental = Accidental.NONE;
        isHalfNote = false;
        isBCEF = false;
    }
    
    void setScaleUsed(ScaleUsed sc){
        this.scaleUsed = sc;
    }
    ScaleUsed gettScaleUsed(){
        return scaleUsed;
    }
    
    void setPartOfSheet(PartOfSheet pos){
        this.partOfSheet = pos;
    }
    
    int pitchUp(int myPitch){
        isHalfNote = true;
        return ++myPitch;
    }
    
    int pitchDown(int myPitch){
        isHalfNote = true;
        return --myPitch;
    }

    int pitchUpBCEF(int myPitch){
        isBCEF = true;
        return ++myPitch;
    }
    
    int pitchDownBCEF(int myPitch){
        isBCEF = true;
        return --myPitch;
    }
                   
    void selectPitch(){
        int newPitch;
        isHalfNote = false; // A halfNote can be presented by a black key, a BCEF change can't,
        isBCEF = false;  // So this is to avoid white key to white key changing problems
        do{
            if (scaleUsed.equals(ScaleUsed.CHROMATIC)){
                switch(partOfSheet){
                    case GRAND : newPitch = (int)(Math.random()*49+1);
                        break;
                    case TREBLE : newPitch = (int)(Math.random()*28+22);
                        break;
                    case BASS : newPitch = (int)(Math.random()*29+1);
                        break;
                    default: newPitch = (int)(Math.random()*49+1); //same as GRAND
                        break;
                }
                isHalfNote = new boolean[]{false, true, false, true,
                    false, false, true, false, true, false, true, false}[(newPitch-1)%12];
            } else {
                switch(partOfSheet){
                    case GRAND : newPitch = standardRow[(int)(Math.random()*29)];
                        break;
                    case TREBLE : newPitch = standardRow[(int)(Math.random()*17)+12];
                        break;
                    case BASS : newPitch = standardRow[(int)(Math.random()*17)];
                        break;
                    default: newPitch = standardRow[(int)(Math.random()*29)]; //same as GRAND
                        break;
                }

                switch(scaleUsed){  //pitch corrections by scalesSetter
                    case NONE : isHalfNote = false;
                        break;
                    case SHARP7 :
                        if(newPitch % 12 == 0){
                            newPitch = pitchUpBCEF(newPitch);
                            break;
                        } //else fall through
                    case SHARP6 :
                        if (partOfSheet == partOfSheet.BASS && newPitch == 29){
                            newPitch = pitch;
                            //System.out.println("E_29");
                            //There is no such key in this setting -> next randomizing round
                            break;
                        }else if(newPitch % 12 == 5){
                            newPitch = pitchUpBCEF(newPitch);
                            break;
                        } //else fall through
                    case SHARP5 :
                        if(newPitch % 12 == 10){
                            newPitch = pitchUp(newPitch);
                            break;
                        } //else fall through            
                    case SHARP4 :
                        if(newPitch % 12 == 3){
                            newPitch = pitchUp(newPitch);
                            break;
                        } //else fall through
                    case SHARP3 :
                        if(newPitch % 12 == 8){
                            newPitch = pitchUp(newPitch);
                            break;
                        } //else fall through
                    case SHARP2 :
                        if(newPitch % 12 == 1 && newPitch != 49){
                            newPitch = pitchUp(newPitch);
                            break;
                        } else if(newPitch == 49){ //highest C key can't be modified
                            newPitch = pitch; //There is no such key -> next randomizing round
                            break;
                        } //else fall through
                    case SHARP1 :
                        if(newPitch % 12 == 6){
                            newPitch = pitchUp(newPitch);
                        }
                        break;
                    case FLAT7 :
                        if(newPitch % 12 == 6){
                            newPitch = pitchDownBCEF(newPitch);
                            break;
                        } //else fall through
                    case FLAT6 :
                        if(newPitch % 12 == 1 && newPitch != 1){
                            newPitch = pitchDownBCEF(newPitch);
                            break;
                        } else if(newPitch == 1){ //lowesr C key can't be modified downwards
                            newPitch = pitch; //There is no such key -> next randomizing round
                            break;
                        } //else fall through
                    case FLAT5 :
                        if(newPitch % 12 == 8){
                            newPitch = pitchDown(newPitch);
                            break;
                        } //else fall through
                    case FLAT4 :
                        if(newPitch % 12 == 3){
                            newPitch = pitchDown(newPitch);
                            break;
                        } //else fall through
                    case FLAT3 :
                        if (partOfSheet == partOfSheet.TREBLE && newPitch == 22){
                            newPitch = pitch;
                            //System.out.println("A_22");
                            //There is no such key in this setting -> next randomizing round
                            break;
                        }else if(newPitch % 12 == 10){
                            newPitch = pitchDown(newPitch);
                            break;
                        } //else fall through
                    case FLAT2 :
                        if(newPitch % 12 == 5){
                            newPitch = pitchDown(newPitch);
                            break;
                        } //else fall through
                    case FLAT1 :
                        if(newPitch % 12 == 0){
                        newPitch = pitchDown(newPitch);
                        }
                        break;
                    default: isHalfNote = false;
                        break;
                }   
            }
        } while (newPitch == pitch);
        pitch = newPitch;
        
        
        //For deciding which part of the sheet will be used:
        if (pitch > 29 || partOfSheet.equals(partOfSheet.TREBLE)){
            whereToShow = PartOfSheet.TREBLE;
        } else if (pitch == 29 && scaleUsed == ScaleUsed.FLAT7){
            whereToShow = PartOfSheet.TREBLE;
        } else if (pitch < 22 || partOfSheet.equals(partOfSheet.BASS)){
            whereToShow = PartOfSheet.BASS;
        } else {
            whereToShow = ((int)(Math.random()*2) == 0 ? PartOfSheet.TREBLE : PartOfSheet.BASS);
        }
        
        //Half notes - how to sign:

        if(isHalfNote && scaleUsed.equals(ScaleUsed.CHROMATIC)){
            accidental = ((int)(Math.random()*2) == 0 ? Accidental.SHARP : Accidental.FLAT);
        } else if (isHalfNote || isBCEF){
            accidental = (scaleUsed.name().contains("SHARP") ? Accidental.SHARP : Accidental.FLAT);
        } else {
            accidental = Accidental.NONE;
        }
        //System.out.println("pitch: " + pitch + "; isHalfNote: " 
          //  + isHalfNote + "; accidental: " + accidental +"; " + scaleUsed);
        panel.sheet.noteHead.setPosition(pitch, whereToShow, accidental, isBCEF);
        panel.repaint();
    }

    void checkSolution(int keyNumber, String name){
        if (keyNumber == pitch){
            panel.labelC.setForeground(Color.WHITE);
            panel.labelC.setText("Press the indicated key!");
            panel.labelB.setForeground(Color.WHITE);
            panel.labelB.setText(++counter + "  /  " + ++rightChoice);
            selectPitch();
        } else{
            panel.labelC.setForeground(Color.RED);
            if((keyNumber-pitch)%12==0){
                name = name.substring(0, name.indexOf('-'));
                panel.labelC.setText("Same note, different octave.");//("That's a different " + name);
            } else {
                panel.labelC.setText("Try again!");
            }
            panel.labelB.setForeground(Color.RED);
            panel.labelB.setText(++counter + "  /  " + rightChoice);
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
        accidental = Accidental.NONE;
    }  
}

