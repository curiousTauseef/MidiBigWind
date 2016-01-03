import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 
import java.io.*;
import javax.sound.midi.*;
import javax.sound.sampled.AudioSystem;

public class  NewPlay extends Thread implements MetaEventListener{
    private File midiFile;    
    private static boolean isLoop =true;
    private static int START=1;
    private static int PAUSE=2;
    private static int STOP=3;
    private static int precom=0;
    private Sequence seq;
    private Sequencer midi;
    private long MStopPosition=-1;
    private static final int END_OF_TRACK_MESSAGE = 47; //磁道信息尾
    
    public NewPlay() {  }
    
    public NewPlay(File file) /*throws MidiUnavailableException,InvalidMidiDataException,IOException*/{
        this.setFile(file);
        try{  
        midi=MidiSystem.getSequencer();
        midi.open();
        }catch(MidiUnavailableException e){
	    System.out.println( "獲取序列器失敗!" );
	}
        try{
        seq=MidiSystem.getSequence(this.midiFile);                    
        midi.setSequence(seq);    
        midi.addMetaEventListener(this);
        }catch(InvalidMidiDataException e){
	    System.out.println( "獲取MIDI流!" );
	}catch(IOException io){
	    System.out.println( "獲取file失敗!" );
	}
    }    
    public void run(){
        if(precom == 0){
            midi.start();
        }else{
            try {
                 this.sleep(1000);
            }catch (Exception ex) {
                    ex.printStackTrace();
            }
        }        
    }
    
    public long getMidiLength(){
        return midi.getMicrosecondLength();
    }
    
    public long getMidiPosition(){
        return midi.getMicrosecondPosition();
    }
    
    public void setMidiPosition(long midPosition){
        midi.setMicrosecondPosition(midPosition);
    }
    
    public void meta(MetaMessage meta) {
          //循環播放
          if( meta.getType() == END_OF_TRACK_MESSAGE ){
              
               if( midi != null && midi.isOpen() && isLoop ){
                      precom = PAUSE;
                      MStopPosition = 0;
                      start();                  
               }else{
		      stopmidi();
               }
          }		         
    }
    
    public synchronized void start(){
        if(precom == 0){
            System.out.println("in start, precom is "+precom);
            precom=START;
            midi.start();
        }else if(precom == PAUSE){
            System.out.println("in start, precom is "+precom);
            System.out.println("PAUSE TO START");
            precom = START;
            midi.setMicrosecondPosition(MStopPosition);
            midi.start();
        }else if(precom == START){
            System.out.println("in start, precom is "+precom);
            return;
        }else{
            System.out.println("in NewPlay's start , you have some wrong !!!");
        }      
    }
    
    public synchronized void pause(){
        if(precom == 0){
            System.out.println("in pause, precom is "+precom);
            return;
        }else if(precom == PAUSE || precom == STOP ){
            System.out.println("in pause, precom is "+precom);
            return;
        }else if(precom == START){
            precom = PAUSE;
            System.out.println("in pause, precom is "+precom);
            MStopPosition = midi.getMicrosecondPosition();
            System.out.println("pause Microsecond is "+MStopPosition);
            midi.stop();
        }else{
            System.out.println("in NewPlay's pause , you have some wrong !!!");
        }    
    }
    
    public synchronized void stopmidi(){
        if(precom == 0){
            return;
        }else if(precom == PAUSE || precom == START){
              precom =STOP;
              this.close();
          }else if(precom == STOP){
              return;
          }else{
            System.out.println("in NewPlay's stop , you have some wrong !!!");
        } 
    }
    
    public synchronized void close(){
        if( midi != null || midi.isOpen() ){              
                midi.stop();
                midi.close(); 
                precom = 0;
                try {
                  this.finalize();
                }catch ( Throwable ex ) {
                    System.out.println("play  finalized"); 
                }               
        }
    }
    
    public synchronized void setFile(File f) {
        this.midiFile = f;        
    }
        
    public File getFile(){
        return this.midiFile;
    }
      
    public synchronized void setIsLoop(boolean b){      
        isLoop = b;
    }

    public boolean getIsLoop(){
        return isLoop;
    }
    
}
