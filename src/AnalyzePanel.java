import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import Arg.AOMArg;
import javax.swing.table.DefaultTableModel;
import Arg.Score.midiScore;

public class AnalyzePanel extends JPanel {

    private byte A_data[];
    private FileInputStream A_MIDI_IN;
    private AOMArg AOMObj;
    private OpenFile openFile;
    private JPanel basesPanel,instrumentPanel,valuePanel,midiPanel,bttnPanel;    
    private JLabel trackFlb,trackSlb,rhythmFlb,rhythmSlb,instrumentFlb,instrumentSlb;
    private JLabel vilb,pathlb;
    private JButton fbtnReset,fbtnAnaly,fbtnSave;
    private Border loweredetched,loweredbevel;
    private TitledBorder basesTitle,instrumentTitle,valueTrackTitle,midiTitle;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private String[] instrument_table={};
    
    private JEditorPane JEP;
    
     public AnalyzePanel(){

       	this.setLayout(null);
        //set border
         loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
         loweredbevel = BorderFactory.createLoweredBevelBorder();
        
        //set titleBorder
         basesTitle = BorderFactory.createTitledBorder(loweredetched," 基本資訊 ");
         basesTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
         
         instrumentTitle = BorderFactory.createTitledBorder(loweredetched," 樂    器 ");
         instrumentTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
         
         valueTrackTitle = BorderFactory.createTitledBorder(loweredetched," 評    價 ");
         valueTrackTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
         
         midiTitle = BorderFactory.createTitledBorder(loweredetched," MIDI路徑 ");
         midiTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
       
        //add trackFlb,trackSlb,rhythmFlb,rhythmSlb,instrumentFlb,instrumentSlb 加入basePanel
        basesPanel = new JPanel(null);
        trackFlb      = new JLabel("音軌數 ：");
        trackSlb      = new JLabel("");
        rhythmFlb     = new JLabel("拍    速 ：");
        rhythmSlb     = new JLabel("");
        instrumentFlb = new JLabel("樂    數 ：");
        instrumentSlb = new JLabel("");
    
        basesPanel.add(trackFlb);
        basesPanel.add(trackSlb);
        basesPanel.add(rhythmFlb);
        basesPanel.add(rhythmSlb);
        basesPanel.add(instrumentFlb);
        basesPanel.add(instrumentSlb);
        basesPanel.setBorder(basesTitle);
        trackFlb.setBounds(25,25,60,25);
        trackSlb.setBounds(95,25,120,25);
        trackSlb.setBorder(loweredbevel);
        rhythmFlb.setBounds(25,60,60,25);
        rhythmSlb.setBounds(95,60,120,25);
        rhythmSlb.setBorder(loweredbevel);
        instrumentFlb.setBounds(25,95,60,25);
        instrumentSlb.setBounds(95,95,120,25);
        instrumentSlb.setBorder(loweredbevel);
        basesPanel.setBounds(25,10,250,150);
        
        //instrumentPanel
        instrumentPanel = new JPanel(null);
        instrumentPanel.setBorder(instrumentTitle);
        instrumentPanel.setBounds(290,10,270,150);

        //樂器的資訊放入設定
        String[] columnNames = {"樂器名稱"};
        Object[][] data = {};
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS  );        
        scrollPane = new JScrollPane(table);
        instrumentPanel.add(scrollPane);
        table.setBounds(2,0,265,130);
        scrollPane.setBounds(2,17,265,130);

        //valuePanel
        valuePanel = new JPanel(null);
        vilb = new JLabel();
        //vilb.setBounds(10,20,440,50);
        JEP = new JEditorPane();
        JEP.setBounds(10,20,440,80);
        JEP.setEditable(false);
        valuePanel.add(JEP);
        valuePanel.setBorder(valueTrackTitle);
        valuePanel.setBounds(25,170,460,110);
        
        
        //midiPanel
        midiPanel = new JPanel(null);
        pathlb = new JLabel("");
        pathlb.setBounds(10,20,440,40);        
        midiPanel.add(pathlb);
        midiPanel.setBorder(midiTitle);
        midiPanel.setBounds(25,290,460,70);
        
        //bttnPanel]
        bttnPanel = new JPanel(null);
        fbtnReset=new JButton("重設");    //set Start-button to First Tab
        fbtnAnaly=new JButton("分析");    //set Analy-button to First Tab
        fbtnSave  =new JButton("儲存");   //set Save-button to First Tab        
        bttnPanel.add(fbtnReset);
        bttnPanel.add(fbtnAnaly);
        //bttnPanel.add(fbtnSave);
        fbtnAnaly.setBounds(0,0,60,20);
        fbtnReset.setBounds(0,30,60,20);   
        fbtnSave.setBounds(0,60,60,20);
        fbtnReset.setFont(new Font("Serif", Font.PLAIN,12));
        fbtnAnaly.setFont(new Font("Serif", Font.PLAIN,12));
        fbtnSave.setFont(new Font("Serif", Font.PLAIN,12));       
        bttnPanel.setBounds(500,270,60,80);
        fbtnReset.addActionListener (new ResetListener());    //重新設定
        fbtnAnaly.addActionListener (new AnalyListener());   //從MIDI檔裡擷取出重要訊息，顯示在 基本資訊、樂器、評價、MIDI路徑
        fbtnSave.addActionListener (new SaveListener());     //將 基本資訊、樂器、評價、MIDI路徑 的訊息存成.txt檔
        fbtnReset.setEnabled(false);
        fbtnAnaly.setEnabled(false);
        fbtnSave.setEnabled(false);
        
        /****環境變數****/
        this.add(basesPanel);
        this.add(instrumentPanel);
        this.add(valuePanel);
        this.add(midiPanel);
        this.add(bttnPanel);
        this.setSize(600,390);
        this.setVisible(true); 
        /****環境變數****/
        
     }    
	 //分析事件bug 可能要重寫
     private class  AnalyListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                try {
                	openFile = new OpenFile(getpath());   //以String方式輸入徑
                         //設定資訊的輸入  
                                        
                 	A_MIDI_IN = new FileInputStream(openFile.getFile());
                    A_data = new byte[A_MIDI_IN.available()];
                    A_MIDI_IN.read(A_data);
                    AOMObj = new AOMArg(A_data);
                    A_MIDI_IN.close();
                 
                 
                 	JOptionPane.showMessageDialog(null,"分析完畢","分析",JOptionPane.WARNING_MESSAGE);
                 	int TRACK = AOMObj.getSUM_TRACK();
                 	int TEMPO = AOMObj.getTEMPO();
                 	int SUM_INS = AOMObj.getSUM_INS();
                 	int[] intINS = AOMObj.getintINS_ARRAY();
                 	                                
                	trackSlb.setText(TRACK + " MTrk數");
                 	rhythmSlb.setText(TEMPO + " / 分"); 
                 	instrumentSlb.setText(SUM_INS + "組樂器");
                 	instrument_table = AOMObj.getINS_Array();
                 	
                 	midiScore MIDSCORE = new midiScore(intINS,TRACK,TEMPO);                 
                 	JEP.setText(MIDSCORE.toString());
                 	//vilb.setText(MIDSCORE.toString());
                 	//System.out.println(MIDSCORE.toString());
		 			//加入可以判斷要增加多少row-------------------------下面OK此即即可刪----------------------------------
                 	String[] G = new String[1];
                 	for(int k=0 ; k< instrument_table.length ; k++){                 
                    	G[0] = instrument_table[k];
		             	model.addRow(G);
                 	}
                 	//model.addRow( instrument_table ); 
                 	fbtnSave.setEnabled(true);
                 
                }catch(Exception ioe){
                	//System.out.println("找不到檔案!");
                	JOptionPane.showMessageDialog(null,"找不到檔案","錯誤",JOptionPane.WARNING_MESSAGE);
                }
          }
     } 
     
     private class  SaveListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                int option = fc.showSaveDialog( null );
                if(option == JFileChooser.APPROVE_OPTION) { // 按確定鍵
                        File outFile =fc.getSelectedFile();
                        //儲存動作，要看分析其資訊的輸入方式而定------------------------------------------------
                        try { 
                            FileWriter fw = new FileWriter(outFile) ;
                            fw.write(AOMObj.getMIDI_INFO());
                            fw.close();
                        } 
                        catch (Exception ioe) {     
                             System.out.println("File    Not Found");  
                             return;  
                        }       
                }
                else if(option == JFileChooser.CANCEL_OPTION ) { // 按取消鍵
                    return;
                } 
                fbtnSave.setEnabled(false);
          }           
     }
     
     private class  ResetListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                    vilb.setText("");
                    pathlb.setText("");
                    trackSlb.setText("");
                    rhythmSlb.setText("");
                    instrumentSlb.setText("");                    
                   	
                    while (model.getRowCount()!=0 ){
                    	model.removeRow(0);
                    }

                    fbtnSave.setEnabled(false);
          }
     } 
    
     
     public DefaultTableModel getTableModel(){
        return model;   
     }
     
     public JButton getResetBtn(){
        return fbtnReset;   
     }
     
     public JButton getAnalyBtn(){
         return fbtnAnaly;
     }
     
     public JButton getSaveBtn(){
         return fbtnSave;
     }
  
    public JLabel gettrackLb(){
        return trackSlb;
    }
    
    public JLabel getrhythmLb(){
        return rhythmSlb;
    }
    
    public JLabel getinstrumentLb(){
        return instrumentSlb;
    }
 
     public JLabel getvalueLb(){
         return vilb;
     }
     
     public String getpath(){
         return pathlb.getText();
     }
     
     public void setpathLb(String path){
         pathlb.setText(path); 
     }

}