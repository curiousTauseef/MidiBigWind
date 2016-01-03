import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.*;
import java.util.*;
import Arg.CoMtestONE;
import Arg.GM.GMtable;

public class ComposePanel extends JPanel  
{     

     private byte[] NEW_MIDI;
     private JLabel rhythmLb,tuneLb,midiLb,trackLb;
     private JCheckBox rockBtn,popSoftBtn,bluesBtn,countryBtn;
     private JRadioButton fourBtn,twentleBtn;
     private ButtonGroup beatSpeedGroup;     
     private JComboBox beat1CB,beat2CB,rhythmCB,tuneCB,midiCB,trackCB;
     private JPanel paramentPanel,musicTrackPanel,stytlePanel,beatSpeedPanel,elsePanel,btnPanel;
     private TitledBorder stytleTitle,musicTitle,musicTrackTitle,beatSpeedTitle,elseTitle;
     private Border loweredetched,loweredbevel;
     private JButton eEnsureBtn,eResetBtn,mEnsureBtn,mSaveBtn,mCancleBtn;
     private CoMtestONE comtest;  //把資料放入----------------------------------------------------------------
     private GMtable GM; //對照 gm_table
     private int[] INS_ARRAY;
     private JScrollPane scrollPane;
     private JTable table;     
     private TableColumn Column;
     private DefaultTableModel model;
     

     String rhythm[] = {"60~80"," 80~120","140~180"};
     String beat1[]  = {"16","32","48","60","72"};
     String beat2[]  = {"12","24","36","48","60"};
     String tune[]   = {"  C","  D","  E","  F","  G","  A","  B"};     

     private static int ROCK = 0, POP = 1, BLUE = 2, COUNTRY = 3;     //風格的選項
     private static int FIRST = 0, SECOND = 1, THIRD = 2;  //拍速的選項
     private static int C = 0,  D = 1, E = 2, F = 3, G = 4, A =5,  B = 6;   //音調選項
     private static int TWELVE=12, SIXTH=16, TWENTY_FOUR=24, THIRTY_TWO=32, THIRTY_SIX=36, FORTY_EIGHT=48,SIXTY=60, SEVENTY_TWO=72;

     public ComposePanel(){    
	 //set border
             loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
             loweredbevel = BorderFactory.createLoweredBevelBorder();


	 //set titleBorder
             musicTitle = BorderFactory.createTitledBorder(loweredetched," 音樂基本設定 ");
             musicTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
             stytleTitle = BorderFactory.createTitledBorder(loweredetched," 風格 ");
             stytleTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
             musicTrackTitle = BorderFactory.createTitledBorder(loweredbevel," 樂器 && Track 相關設定 ");
             musicTrackTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
             beatSpeedTitle = BorderFactory.createTitledBorder(loweredetched," 小節 ");
             beatSpeedTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
             elseTitle = BorderFactory.createTitledBorder(loweredetched,"音調 && 拍速");
             elseTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);
         

	 //set stytle
             stytlePanel = new JPanel(new FlowLayout());
             stytlePanel.setBorder(stytleTitle);
             rockBtn     = new JCheckBox("Rock",false);
             popSoftBtn  = new JCheckBox("POP Soft",false);
             bluesBtn    = new JCheckBox("Blues",false);
             countryBtn  = new JCheckBox("Country",false); 
             stytlePanel.add(rockBtn);
             stytlePanel.add(popSoftBtn);
             stytlePanel.add(bluesBtn);
             stytlePanel.add(countryBtn);
             stytlePanel.setBounds(10,30,300,60);
             
	 //set 小節
             beatSpeedPanel = new JPanel(null);
             beatSpeedPanel.setBorder(beatSpeedTitle);
             beatSpeedGroup = new ButtonGroup();
             fourBtn     = new JRadioButton(" 4倍",true);
             twentleBtn  = new JRadioButton("12倍",false); 
             beat1CB  = new JComboBox(beat1);
             beat2CB  = new JComboBox(beat2);
             beat2CB.setEnabled(false);
             fourBtn.setBounds(20,20,60,20);
             twentleBtn.setBounds(20,50,60,20);
             beat1CB.setBounds(90,20,80,20);
             beat2CB.setBounds(90,50,80,20);             
             beatSpeedGroup.add(fourBtn);         
             beatSpeedGroup.add(twentleBtn);                  
             beatSpeedPanel.add(fourBtn);
             beatSpeedPanel.add(beat1CB);
             beatSpeedPanel.add(twentleBtn);
             beatSpeedPanel.add(beat2CB);         
             beatSpeedPanel.setBounds(10,100,300,80);


	 //set 音調、拍速
             elsePanel = new JPanel(null);
             elsePanel.setBorder(elseTitle);

             tuneLb   = new JLabel("音調");
             rhythmLb = new JLabel("拍速");

             tuneCB  = new JComboBox(tune);
             rhythmCB  = new JComboBox(rhythm);

             elsePanel.add(tuneLb);
             elsePanel.add(tuneCB);    
             elsePanel.add(rhythmLb);
             elsePanel.add(rhythmCB);

             tuneLb.setBounds(10,45,70,20);
             tuneCB.setBounds(90,47,120,18);
             rhythmLb.setBounds(10,70,70,20);
             rhythmCB.setBounds(90,72,120,18);      

             elsePanel.setSize(250,125);
             elsePanel.setBounds(320,30,250,125);
         

         //參數區
             paramentPanel   = new JPanel(null);
             paramentPanel.setBorder(musicTitle);
             paramentPanel.setSize(580,190);
             paramentPanel.setBounds(10,5,580,190);

             eEnsureBtn = new JButton("創作");
             eResetBtn  = new JButton("重設");
             eEnsureBtn.setBounds(430,160,60,20);
             eResetBtn.setBounds(500,160,60,20);
             eEnsureBtn.addActionListener(new EnsureListener());
             eResetBtn.addActionListener(new ResetListener());
             
             paramentPanel.add(eEnsureBtn);
             paramentPanel.add(eResetBtn);
             paramentPanel.add(stytlePanel);
             paramentPanel.add(beatSpeedPanel);
             paramentPanel.add(elsePanel);
        

	 //track選擇區
             musicTrackPanel = new JPanel(null);
             musicTrackPanel.setBorder(musicTrackTitle);
             musicTrackPanel.setBounds(10,200,580,140);
             String[] columnNames = {"Track 編號", "樂器類別","樂器名稱"};
             Object[][] data = {};
             model = new DefaultTableModel(data, columnNames);
             table = new JTable(model);
             table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS  );
             table.setPreferredScrollableViewportSize(new Dimension(580,140));
             scrollPane = new JScrollPane(table);
             musicTrackPanel.add(scrollPane);
             scrollPane.setBounds(4,20,572,116);   
             
//             Column = new TableColumn();
//             Column.setCellEditor(new DefaultCellEditor(new JTextField("123")));
//             table.getModel().setValueAt(new JTextField("123"),0,0);
//             table.getColumnModel().addColumn(Column);
             //single-column設定區
//             TableColumn sportColumn = table.getColumnModel().getColumn(1);  
//             sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
         
	//按鈕區
             btnPanel = new JPanel(null);
             btnPanel.setBounds(395,345,190,20);
             mEnsureBtn = new JButton("創作");
             mSaveBtn   = new JButton("匯出");
             mCancleBtn = new JButton("取消");
             mEnsureBtn.addActionListener(new M_EnsureListener());
             mSaveBtn.addActionListener(new SaveListener());
             mCancleBtn.addActionListener(new CancleListener());
             btnPanel.add(mSaveBtn);
             btnPanel.add(mCancleBtn);
             mEnsureBtn.setBounds(0,0,60,20);
             mSaveBtn.setBounds(65,0,60,20);
             mCancleBtn.setBounds(130,0,60,20);
             
             fourBtn.setEnabled(false);
             twentleBtn.setEnabled(false);
             beat1CB.setEnabled(false);
             beat2CB.setEnabled(false);
             tuneCB.setEnabled(false);
             rhythmCB.setEnabled(false);
             mEnsureBtn.setEnabled(false);
             mSaveBtn.setEnabled(false);
             mCancleBtn.setEnabled(false);
             eEnsureBtn.setEnabled(false);
             eResetBtn.setEnabled(false);
             
             rockBtn.addActionListener(
                    new ActionListener(){
                            public void actionPerformed( ActionEvent event){
                                  selectOnlyOne(rockBtn);
                                  eEnsureBtn.setEnabled(true);
                                  eResetBtn.setEnabled(true);
                                  fourBtn.setSelected(true);
                                  beat1CB.setEnabled(true);
                                  beat2CB.setEnabled(false);
                                  tuneCB.setEnabled(true);
                                  rhythmCB.setSelectedIndex(2);
                            }
                    }
             );
             popSoftBtn.addActionListener(
                    new ActionListener(){
                            public void actionPerformed( ActionEvent event){
                                  selectOnlyOne(popSoftBtn);
                                  eEnsureBtn.setEnabled(true);
                                  eResetBtn.setEnabled(true);                               
                                  fourBtn.setSelected(true);
                                  beat1CB.setEnabled(true);
                                  beat2CB.setEnabled(false);
                                  tuneCB.setEnabled(true);
                                  rhythmCB.setSelectedIndex(0);
                            }
                    }
             );
             bluesBtn.addActionListener(
                    new ActionListener(){
                            public void actionPerformed( ActionEvent event){
                                 selectOnlyOne(bluesBtn); 
                                 eEnsureBtn.setEnabled(true);
                                 eResetBtn.setEnabled(true);                               
                                 twentleBtn.setSelected(true);
                                  beat1CB.setEnabled(false);
                                  beat2CB.setEnabled(true);
                                  tuneCB.setEnabled(true);
                                  rhythmCB.setSelectedIndex(1);                                 
                            }
                    }
             );
             countryBtn.addActionListener(
                    new ActionListener(){
                            public void actionPerformed( ActionEvent event){
                                  selectOnlyOne(countryBtn);
                                  eEnsureBtn.setEnabled(true);
                                  eResetBtn.setEnabled(true);
                                  fourBtn.setSelected(true);
                                  beat1CB.setEnabled(true);
                                  beat2CB.setEnabled(false);
                                  tuneCB.setEnabled(true);
                                  rhythmCB.setSelectedIndex(1);
                            }
                    }
             );
            
	 /*******環境設定區*************/
         this.setLayout(null);
         this.add(paramentPanel);         
         this.add(musicTrackPanel);     
         this.add(btnPanel);
         this.setSize(600,400);
         this.setVisible(true);
	 /*******環境設定區*************/
         
     }
    
    public JButton getSaveBtn(){
         return mSaveBtn;
     }
     
     public void selectOnlyOne(JCheckBox b){
        if(b.equals(rockBtn) ){
            rockBtn.setSelected(true);
            popSoftBtn.setSelected(false);
            bluesBtn.setSelected(false);
            countryBtn.setSelected(false);
        }
        if(b.equals(popSoftBtn)){
            popSoftBtn.setSelected(true);
            rockBtn.setSelected(false);
            bluesBtn.setSelected(false);
            countryBtn.setSelected(false);
        }
        if(b.equals(bluesBtn)){
            bluesBtn.setSelected(true);
            rockBtn.setSelected(false);
            popSoftBtn.setSelected(false);
            countryBtn.setSelected(false);
        }
        if(b.equals(countryBtn)){
            countryBtn.setSelected(true);
            rockBtn.setSelected(false);
            popSoftBtn.setSelected(false);
            bluesBtn.setSelected(false);
        }         
        stytlePanel.updateUI();
     }
     
     public int getStyle(){
          if(rockBtn.isSelected()){
             return 0;
         }else if(popSoftBtn.isSelected()){
             return 1;
         }else if(bluesBtn.isSelected()){
             return 2;
         }else if(countryBtn.isSelected()){
             return 3;
         }else{
              return -1;
         }      
     }
     
     public int getBAR(){
         int i,j,k;
         
         //選小節的倍數
         if(fourBtn.isSelected()){
            i=beat1CB.getSelectedIndex();
            j=-1;            
         }else{
            j = beat2CB.getSelectedIndex();
            i=-1;        
         }
         //選擇第幾小節
         if(j ==-1){
             k = Integer.parseInt(beat1[i]);
             
         }else{
             k = Integer.parseInt(beat2[j]);
         }                 
         return k;   
     }

     public int getKey(){
         return tuneCB.getSelectedIndex();
    }

    public int getTempo(){
        int i=-1;       
        switch(rhythmCB.getSelectedIndex()){          
            case 0:
                    i = (int) Math.floor(Math.random()*21+60);
                    break;
            case 1:
                    i = (int) Math.floor(Math.random()*41+80);
                    break;
            case 2:
                    i = (int) Math.floor(Math.random()*41+140);
                    break;            
            default:
                    break;
        }
         
        return i;
    }
    public byte[] getNEWMIDI(){ return NEW_MIDI;    }
    
    private class  EnsureListener implements ActionListener {
          public void actionPerformed (ActionEvent e)  {
                 try{
                 	String[] g=new String[3];                 	
                    comtest = new CoMtestONE(getStyle(),getTempo(),getKey(),getBAR());                    
                    NEW_MIDI = comtest.getMidi();
                                 
                 	INS_ARRAY = comtest.getINS();
                    //System.out.println(INS_ARRAY.length); 
                    while (model.getRowCount()!=0 ){
                    	model.removeRow(0);
                    }
                    
                    for (int i = 0;i<INS_ARRAY.length;i++){
                    	//System.out.println("\n"+INS_ARRAY[i]);
                    	GMtable Tab = new GMtable(INS_ARRAY[i]);
                    	
                    	String A = Tab.getFirstEntry();
                    	String B = Tab.getSecondEntry();
                    	String C = Tab.getThirdEntry();
                    	//System.out.println(A + B + C);
                    	g[0] = A;
                 		g[1] = B;
                 		g[2] = C;
                 		model.addRow(g);
                    }
                    
                    
                    //加入可以判斷要增加多少row-------------------------下面OK此即即可刪----------------------------------
                 	//有問題
                 	/*
                 	for(int i=0 ; i< INS_ARRAY.length ; i++){
                 		//System.out.println(new GMtable(INS_ARRAY[i]).getFirstEntry());
                 		g[0] = new GMtable(INS_ARRAY[i]).getFirstEntry();
                 		g[1] = new GMtable(INS_ARRAY[i]).getSecondEntry();
                 		g[2] = new GMtable(INS_ARRAY[i]).getThirdEntry();
                  		
                  		System.out.println(g[i]);
                  		
                  		model.addRow(g);
                 	} 
                 	*/
                     
                     /*
                     for(int k=0 ; k< instrument_table.length ; k++){                 
                    	G[0] = instrument_table[k];
		             	model.addRow(G);
                 	
                 	
                 	}*/
                     
                 }catch(Exception io){}
                 
                 eEnsureBtn.setEnabled(false);
                 eResetBtn.setEnabled(true);
                 mEnsureBtn.setEnabled(true);
                 mSaveBtn.setEnabled(true);
           	     mCancleBtn.setEnabled(true);
          }
     }
    
    private class  M_EnsureListener implements ActionListener {
          public void actionPerformed (ActionEvent e)  {
                 
          }
     }
    
    private class  ResetListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                
                while (model.getRowCount()!=0 ){
                    	model.removeRow(0);
                    }
                 tuneCB.setSelectedIndex(0);
                 rhythmCB.setSelectedIndex(0);
                 rockBtn.setSelected(false);
                 popSoftBtn.setSelected(false);
                 bluesBtn.setSelected(false);
                 countryBtn.setSelected(false);
                 tuneCB.setSelectedIndex(0);
                 rhythmCB.setSelectedIndex(0);
                 fourBtn.setSelected(true);
                 fourBtn.setEnabled(false);
                 twentleBtn.setEnabled(false);
                 beat1CB.setEnabled(false);
                 beat2CB.setEnabled(false);
                 tuneCB.setEnabled(false);
                 rhythmCB.setEnabled(false);
          }
     }
    
    private class  SaveListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                
          }
     }
    
    private class  CancleListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                 
                 
                 while (model.getRowCount()!=0 ){
                    	model.removeRow(0);
                    }
                 
                 rockBtn.setSelected(false);
                 popSoftBtn.setSelected(false);
                 bluesBtn.setSelected(false);
                 countryBtn.setSelected(false);
                 tuneCB.setSelectedIndex(0);
                 rhythmCB.setSelectedIndex(0);
                 fourBtn.setSelected(true);
                 fourBtn.setEnabled(false);
                 twentleBtn.setEnabled(false);
                 beat1CB.setEnabled(false);
                 beat2CB.setEnabled(false);
                 tuneCB.setEnabled(false);
                 rhythmCB.setEnabled(false);                      
                 mSaveBtn.setEnabled(false);
            	 mCancleBtn.setEnabled(false);    
          }
     }
}