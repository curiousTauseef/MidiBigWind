import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.sound.midi.*;

public class NewPlayPanel extends  JPanel {
    File midiFile=null;
    private JPanel  buttonPanel = new JPanel(null);
    private JSlider slider      = new JSlider(0,100,0);
    private JLabel playtimeLb   = new JLabel("00:00");
    private JLabel totaltimeLb  = new JLabel("00:00");
    private JButton playBttn    = new JButton(new ImageIcon("PlayPicture/Play.GIF"));   
    private JButton pauseBttn   = new JButton(new ImageIcon("PlayPicture/Pause.GIF"));
    private JButton stopBttn    = new JButton(new ImageIcon("PlayPicture/Stop.GIF"));
    private JButton prevBttn    = new JButton(new ImageIcon("PlayPicture/StepForward.GIF"));
    private JButton nextBttn    = new JButton(new ImageIcon("PlayPicture/StepBack.GIF"));
    private JButton loopBttn    = new JButton("Loop");
    private NewPlay play;
    private Thread controlThread;
    private boolean isLoop;
    private static long oldvalue=0,newvalue;
    
    public NewPlayPanel() {
            //slider
            slider.setBounds(30,50,450,25);
            slider.setPaintTicks(true);        
            this.add(slider);
            //totaltime
            totaltimeLb.setBounds(540,50,50,25);
            this.add(totaltimeLb);
            //playtime
            playtimeLb.setBounds(485,50,50,25);
            this.add(playtimeLb);
            
            buttonPanel.setBounds(20,15,365,25);           
            //play
            playBttn.setRolloverIcon(null);  
            playBttn.setContentAreaFilled(false);
            playBttn.setBounds(10,0,25,25);   
            playBttn.addActionListener(new playListener());
            buttonPanel.add(playBttn);                                 
            //pause
            pauseBttn.setRolloverIcon(null); 
            pauseBttn.setContentAreaFilled(false);
            pauseBttn.setBounds(45,0,25,25);
            pauseBttn.addActionListener(new pauseListener());
            buttonPanel.add(pauseBttn);            
            //stop
            stopBttn.setRolloverIcon(null);
            stopBttn.setContentAreaFilled(false);
            stopBttn.setBounds(80,0,25,25);
            stopBttn.addActionListener(new stopListener());
            buttonPanel.add(stopBttn);            
            //forward
            prevBttn.setRolloverIcon(null);
            prevBttn.setContentAreaFilled(false);
            prevBttn.setBounds(115,0,25,25);
            prevBttn.setEnabled(false);
            buttonPanel.add(prevBttn);            
            //back 
            nextBttn.setRolloverIcon(null);
            nextBttn.setContentAreaFilled(false);
            nextBttn.setBounds(150,0,25,25);  
            nextBttn.setEnabled(false);
            buttonPanel.add(nextBttn);
            //loop
            loopBttn.setRolloverIcon(null);
            loopBttn.setContentAreaFilled(false);
            loopBttn.setBounds(185,0,75,25);            
            loopBttn.addActionListener(new loopListener());
            buttonPanel.add(loopBttn);
      /*******環境變數********/
           this.setLayout(null);
           this.add(buttonPanel);
           Border raisedbevel = BorderFactory.createRaisedBevelBorder();
           this.setBorder(raisedbevel); 
           this.setSize(600,80);
           this.setVisible(true);
      /*******環境變數********/
    }
    
    public void setMidiFile(File f){
        this.midiFile = f ;
    }

    public JButton getPreBtn(){
        return prevBttn;
    }

    public JButton getNextBtn(){
	return nextBttn;
    }
    
    public String changeTime(long microsecond){
        long second,minumt;
        String Time="";
        second = (microsecond/1000000);
         minumt = second/60;
         second = second % 60;
        if(minumt >= 10){
             Time +=minumt + "：";
        }else{
             Time +="0"+minumt + "：";
        }
        if(second >= 10){
             Time +=second;
        }else{
             Time +="0"+second ;
        }
        return Time; 
    }
    
    private class ControlThread extends Thread{
        public  void run(){            
          while(true){         
            newvalue =play.getMidiPosition();
            if(Math.abs((slider.getValue()*1000-oldvalue)) > 1000){       
                play.setMidiPosition((long)(slider.getValue())*1000);
                oldvalue =slider.getValue()*1000;

            }else{
                playtimeLb.setText(changeTime(play.getMidiPosition()));
		slider.setValue((int)(newvalue/1000));
		oldvalue = newvalue;
            }
          }
       }
    } 
    
    private class  playListener implements ActionListener {
          public void actionPerformed (ActionEvent e) {  
                   if(play == null){      
                       play = new NewPlay(midiFile);  
                       totaltimeLb.setText(changeTime(play.getMidiLength()));
                       slider.setMaximum((int)play.getMidiLength()/1000);
                       controlThread = new ControlThread();
                       play.start();
                       play.setIsLoop(isLoop);
                       controlThread.start();
                   }else{
                       play.start();
                   }		
                   
                   prevBttn.setEnabled(true);
                   nextBttn.setEnabled(true);
          }
     } 
    
    private class  pauseListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
              play.pause();     
          }
     }
    
    private class  stopListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                    play.stopmidi();
                    System.out.println(" up play is "+play.getState().toString());
                    play=null;
                    controlThread=null;
                    slider.setValue(0);
          }
     }

    private class  loopListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                    
                    isLoop = !isLoop;
                    if(isLoop){  loopBttn.setText("Loop");}
                    else{ loopBttn.setText("N-Loop");}
          }
     }
    
}
