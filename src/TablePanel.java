import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class TablePanel extends JTabbedPane {
	
    /** Panel 成員 **/
    AnalyzePanel fPanel;
    TransformPanel tPanel;
    ComposePanel cPanel;
    private int tabindex=0;
    private static  UIManager.LookAndFeelInfo looks[];
   
    public TablePanel() {   
	   	fPanel = new AnalyzePanel();
	   	tPanel = new TransformPanel();
	   	cPanel = new ComposePanel();

     	this.addTab("MIDI解析", null, fPanel, "MIDI解析");
     	this.addTab("曲風轉換", null, tPanel,"曲風轉換");
     	this.addTab("歌曲創作", null, cPanel, "歌曲創作");
        this.addChangeListener(
                 new ChangeListener() {
                      public void stateChanged(ChangeEvent event) {
                          JTabbedPane sourceTabbedPane = (JTabbedPane) event.getSource();
                          tabindex = sourceTabbedPane.getSelectedIndex();                          
                      }
                 }
         );

       looks = UIManager.getInstalledLookAndFeels();
       try{
            UIManager.setLookAndFeel( looks[3].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }
        //process problems changing look and feel
        catch(Exception exception){
            exception.printStackTrace();
        }
     
     	this.setSize(600,400);
     	this.setVisible(true);
     }
    
   public int getTabIndex(){
       return tabindex;
   }

}