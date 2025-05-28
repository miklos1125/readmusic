package readmusic;

import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.midi.*;
import javax.sound.midi.MidiDevice.Info;
import static readmusic.ReadMusic.panel;

/**
 * Class for listing and connecting available MIDI instruments.
 * Handles detection and selection of MIDI devices.
 */
public class MidiConnection {
    
    ArrayList<Info> info;
    Transmitter transmitter;
    Receiver reciever;
    MidiDevice device;
    
    public MidiConnection(){
        info = new ArrayList<>(Arrays.asList(MidiSystem.getMidiDeviceInfo()));
        
        // A logic to keep only the usable devices:
        for(int i = info.size()-1; i >= 0; i--){
            
            try{
                device = MidiSystem.getMidiDevice(info.get(i));
                device.open();               
                transmitter = device.getTransmitter();
                makeReciever();
                transmitter.setReceiver(reciever);
                if (device instanceof Sequencer || device instanceof Synthesizer){
                    throw new Exception();
                }
            }catch (Exception mue){
                info.remove(i);
            }finally{
                if(transmitter != null){
                    transmitter.close();
                }
                if (device != null && device.isOpen()){
                    device.close();
                }
            }
        } 
    }
    
    String[] getAvailableDevices(){
        String [] toReturn = new String[info.size()];
        for(int i = 0; i < info.size(); i++){
            toReturn[i] = info.get(i).getName();
        }
        return toReturn;
    }
    
    void startConnection(int deviceNumber){
        try{
            device = MidiSystem.getMidiDevice(info.get(deviceNumber));
            device.open();
            transmitter = device.getTransmitter();
            makeReciever();
            transmitter.setReceiver(reciever);
            panel.labelA.setText(info.get(deviceNumber).getName() + " is connected.");
            new Thread(){
                @Override
                public void run(){
                    try{
                        Thread.sleep(3000);
                    } catch (Exception e){}
                panel.labelA.setText("");    
                }
            }.start();
        }catch(MidiUnavailableException mue){
            panel.labelA.setText("MIDI connection faliure.");
        }catch(Exception e){
            panel.labelA.setText("MIDI connection faliure.");
        }
    }
    
    void stopConnection(boolean refresh){
        if(transmitter != null){
            transmitter.close();
        }
        if (device != null && device.isOpen()){
            device.close();
        }
        panel.labelA.setText(refresh? "Refreshing MIDI list.":"MIDI is off.");
            new Thread(){
                @Override
                public void run(){
                    try{
                        Thread.sleep(3000);
                    } catch (Exception e){}
                panel.labelA.setText("");    
                }
            }.start();
        
    }
        
    void makeReciever(){
        reciever = new Receiver(){
            @Override
            public void send(MidiMessage message, long timeStamp) {
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    int note = sm.getData1();
                    // 36 is the offset between MIDI keycodes and the program's internal keycodes.
                    // The app handles keycodes in the range 36 (C2) to 84 (C6).
                    if (note >= 36 && note <= 84){  
                        // 0 is for silence/stop playing sound:
                        if(sm.getData2()>0){   
                            panel.keys[note-36].presser();
                        } else{
                            panel.keys[note-36].releaser();
                        }
                        //Activating keys by "note".
                        //Experimenting legacy code kept here...
                        /*System.out.println((sm.getData2()>0?"pushed" :"released" ) + " key: "+ note 
                            + "\n\t volume: " + sm.getData2() 
                            + "\n\t MIDI Channel: " +sm.getChannel()
                            + "\n\t command: "+ sm.getCommand());*/   
                    }
                }
            }
            @Override
            public void close() {
                //Not used.
            }
        };
    }
}
