package readmusic;

import javax.sound.midi.*;

public class Sounds {
    
    Synthesizer synth;
    MidiChannel[] midiCh;
    
    public Sounds(){
        try{
            synth = MidiSystem.getSynthesizer();
            synth.open();
        } catch (MidiUnavailableException mua){
            System.out.println(mua);
        }
        midiCh = synth.getChannels();
        Instrument[] ins = synth.getDefaultSoundbank().getInstruments(); //0-234

        synth.loadInstrument(ins[200]);
    }
    
    void playSound(int pitch){
        midiCh[0].noteOn(35+pitch, 100);
    }
    void stopSound(int pitch){
        midiCh[0].noteOff(35+pitch, 100);
    }
}
