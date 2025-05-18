package readmusic;

import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.midi.*;
import javax.sound.midi.MidiDevice.Info;
import javax.swing.Action;
import static readmusic.ReadMusic.panel;

public class MidiConnection {
    
    ArrayList<Info> info;
    Transmitter transmitter;
    Receiver reciever;
    MidiDevice device;
    
    public MidiConnection(){
        info = new ArrayList<>(Arrays.asList(MidiSystem.getMidiDeviceInfo()));
        
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
                    if (note >= 36 && note <= 84){
                        if(sm.getData2()>0){
                            panel.keys[note-36].presser();
                        } else{
                            panel.keys[note-36].releaser();
                        }
                        //Billentyűk aktiválása 'note' alapján.
                        /*System.out.println((sm.getData2()>0?"Lenyomott" :"Felengedett" ) + " billentyű: "+ note 
                            + "\n\t hangerő: " + sm.getData2() 
                            + "\n\t MIDI Channel: " +sm.getChannel()
                            + "\n\t Parancs: "+ sm.getCommand());*/
                        
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
