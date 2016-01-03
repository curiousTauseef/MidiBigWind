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
         basesTitle = BorderFactory.createTitledBorder(loweredetched," �򥻸�T ");
         basesTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
         
         instrumentTitle = BorderFactory.createTitledBorder(loweredetched," ��    �� ");
         instrumentTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
         
         valueTrackTitle = BorderFactory.createTitledBorder(loweredetched," ��    �� ");
         valueTrackTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
         
         midiTitle = BorderFactory.createTitledBorder(loweredetched," MIDI���| ");
         midiTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
       
        //add trackFlb,trackSlb,rhythmFlb,rhythmSlb,instrumentFlb,instrumentSlb �[�JbasePanel
        basesPanel = new JPanel(null);
        trackFlb      = new JLabel("���y�� �G");
        trackSlb      = new JLabel("");
        rhythmFlb     = new JLabel("��    �t �G");
        rhythmSlb     = new JLabel("");
        instrumentFlb = new JLabel("��    �� �G");
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

        //�־�����T��J�]�w
        String[] columnNames = {"�־��W��"};
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
        fbtnReset=new JButton("���]");    //set Start-button to First Tab
        fbtnAnaly=new JButton("���R");    //set Analy-button to First Tab
        fbtnSave  =new JButton("�x�s");   //set Save-button to First Tab        
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
        fbtnReset.addActionListener (new ResetListener());    //���s�]�w
        fbtnAnaly.addActionListener (new AnalyListener());   //�qMIDI�ɸ��^���X���n�T���A��ܦb �򥻸�T�B�־��B�����BMIDI���|
        fbtnSave.addActionListener (new SaveListener());     //�N �򥻸�T�B�־��B�����BMIDI���| ���T���s��.txt��
        fbtnReset.setEnabled(false);
        fbtnAnaly.setEnabled(false);
        fbtnSave.setEnabled(false);
        
        /****�����ܼ�****/
        this.add(basesPanel);
        this.add(instrumentPanel);
        this.add(valuePanel);
        this.add(midiPanel);
        this.add(bttnPanel);
        this.setSize(600,390);
        this.setVisible(true); 
        /****�����ܼ�****/
        
     }    
	 //���R�ƥ�bug �i��n���g
     private class  AnalyListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                try {
                	openFile = new OpenFile(getpath());   //�HString�覡��J�|
                         //�]�w��T����J  
                                        
                 	A_MIDI_IN = new FileInputStream(openFile.getFile());
                    A_data = new byte[A_MIDI_IN.available()];
                    A_MIDI_IN.read(A_data);
                    AOMObj = new AOMArg(A_data);
                    A_MIDI_IN.close();
                 
                 
                 	JOptionPane.showMessageDialog(null,"���R����","���R",JOptionPane.WARNING_MESSAGE);
                 	int TRACK = AOMObj.getSUM_TRACK();
                 	int TEMPO = AOMObj.getTEMPO();
                 	int SUM_INS = AOMObj.getSUM_INS();
                 	int[] intINS = AOMObj.getintINS_ARRAY();
                 	                                
                	trackSlb.setText(TRACK + " MTrk��");
                 	rhythmSlb.setText(TEMPO + " / ��"); 
                 	instrumentSlb.setText(SUM_INS + "�ռ־�");
                 	instrument_table = AOMObj.getINS_Array();
                 	
                 	midiScore MIDSCORE = new midiScore(intINS,TRACK,TEMPO);                 
                 	JEP.setText(MIDSCORE.toString());
                 	//vilb.setText(MIDSCORE.toString());
                 	//System.out.println(MIDSCORE.toString());
		 			//�[�J�i�H�P�_�n�W�[�h��row-------------------------�U��OK���Y�Y�i�R----------------------------------
                 	String[] G = new String[1];
                 	for(int k=0 ; k< instrument_table.length ; k++){                 
                    	G[0] = instrument_table[k];
		             	model.addRow(G);
                 	}
                 	//model.addRow( instrument_table ); 
                 	fbtnSave.setEnabled(true);
                 
                }catch(Exception ioe){
                	//System.out.println("�䤣���ɮ�!");
                	JOptionPane.showMessageDialog(null,"�䤣���ɮ�","���~",JOptionPane.WARNING_MESSAGE);
                }
          }
     } 
     
     private class  SaveListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                int option = fc.showSaveDialog( null );
                if(option == JFileChooser.APPROVE_OPTION) { // ���T�w��
                        File outFile =fc.getSelectedFile();
                        //�x�s�ʧ@�A�n�ݤ��R���T����J�覡�өw------------------------------------------------
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
                else if(option == JFileChooser.CANCEL_OPTION ) { // ��������
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