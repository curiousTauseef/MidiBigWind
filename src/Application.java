import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;
import javax.swing.UIManager;
import Arg.GM.GMtable;

public class Application extends JFrame{  
       
    private static  UIManager.LookAndFeelInfo looks[];
    private FileOutputStream output;
    private JButton enterButton, openButton;
    private TablePanel table ;
    private NewPlayPanel playPanel;
    private PlayList ApplicationPlist = new PlayList();
    private ExePanel   exePanel;    
    private DisplayPanel disPlayPanel;
    private PlayListPanel playListPanel;
    private FilePackage fp;
    private OpenFile openFile; 
    private JList list;
    private static int C_count=0;
    private static int D_count=0;
    
    /** setMenuBar **/
    private JMenu fileMenu,viewMenu,favoritesMenu,helpMenu,newItem,changItem;
    private JMenuItem exitItem,openItem,anotherItem,anaItem,transItem,comItem,midiaItem,miditItem,midicItem,expalinItem,gmItem;
    private JMenuBar bar;

    /*Logo*/
    private JLabel logolb;
    private Icon logo,title;
     
    private String s[]={"test1","test2"};
    
    /** 建構元 **/
    public Application() {   	
            super("米迪大搬風");            
            setLayout(null);
            setMenu();		//設定Menu
            setPanel();		//模擬分割Frame
            looks = UIManager.getInstalledLookAndFeels();        
            changeTheLookAndFeel(3);

        //************Transform的功能設定***************//     
            table.tPanel.getSaveBtn().addActionListener(new T_SaveListListener());            
        //************Transform的功能設定***************//  

        //************Compose的功能設定***************//     
            table.cPanel.getSaveBtn().addActionListener(new C_SaveListListener());            
        //************Transform的功能設定***************//     

        //************ExePanel、DisPlayPanel、PlayListPanel的功能設定***************//                
            exePanel.getEnsureBtn().addActionListener(new E_EnsureListener());       
            exePanel.getAddPlayBtn().addActionListener(new E_AddPlayListListener());
            disPlayPanel.getAddListBtn().addActionListener(new D_AddPlayListListener());  
            playListPanel.getEnsureBtn().addActionListener(new D_EnsureListListener());
            playListPanel.getDeletBtn().addActionListener(new D_DeletListListener());            
        //************ExePanel、DisPlayPanel、PlayListPanel的功能設定***************//   
            
        //************NewPlayPanel的功能設定***************//
            playPanel.getPreBtn().addActionListener(new P_PreListListener());
	    playPanel.getNextBtn().addActionListener(new P_NextListListener());
        //************NewPlayPanel的功能設定***************//

	//************Menu的功能設定***************//
	    exitItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){  
                    	System.exit( 0 );
            		
		    }
		}
	    );  

	    openItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){  
                    	// 產生一個檔，並以FilePackage封裝起來加入到plist，再從plist取出JLabel放到list裡
                        openFile = new OpenFile();                       
                        File f = openFile.getFile(); 
                        if(f.exists()){
                           fp = new FilePackage(f.getName(),null,f.getAbsolutePath());
                           exePanel.getPlayList().addItem(fp);
                           exePanel.getList().setCellRenderer(new IconListItemRenderer());
                           exePanel.getListModel().addElement(exePanel.getPlayList().getLastItem());
                       }else{
                           System.out.println("not ok");
                       }
            		
		    }
		}
	    );
            
            anaItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){  
                    table.fPanel.getvalueLb().setText("");
                    table.fPanel.setpathLb("");
                    table.fPanel.gettrackLb().setText("");
                    table.fPanel.getrhythmLb().setText("");
                    table.fPanel.getinstrumentLb().setText("");
                    //樂器地方未設定(table or label)------------------------------------------------------------
                    
                    while (table.fPanel.getTableModel().getRowCount()!=0){
                    	table.fPanel.getTableModel().removeRow(0);
                    }
                    table.fPanel.getSaveBtn().setEnabled(false);
            		
		    }
		}
	    );
            
            transItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){                      
                    table.tPanel.setpath("");
                    table.tPanel.getTranstBtn().setEnabled(true);
                    table.tPanel.getSaveBtn().setEnabled(false);            		
		    }
		}
	    );
            
            comItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){                      
                    /*尚未設定好*/         	
                    }    
		}
	    );
            
            expalinItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){                      
                           JOptionPane.showMessageDialog(null,
                                   "創作日期：2006/10/15\n版本名稱：米迪大搬風\n目的：專題需求\n作者：\n\t陳家健\n\t賴宜蓁\n\t蘇振容\n\t莊淑雯\n"
                                   ,"版本介紹",JOptionPane.INFORMATION_MESSAGE);		
		    }
		}
	    );

	    midiaItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){                      
                          table.setSelectedIndex(0) ;
		    }
		}
	    );

	   miditItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){                      
                          table.setSelectedIndex(1) ;
		    }
		}
	    );

	   midicItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){                      
                          table.setSelectedIndex(2) ; 	
		    }
		}
	    );
            
            gmItem.addActionListener(
		new ActionListener() {   
                    public void actionPerformed( ActionEvent event){
                    	String gm = "GM表\n";
                    	GMtable GM_Table = new GMtable();
						for (int i = 0 ; i < 128 ; i ++){
							if (i % 3 == 0){
								gm += "第" + (i+1) + "項：" + GM_Table.getINSinfo(i) + "\t";
								gm += "第" + (i+2) + "項：" + GM_Table.getINSinfo(i+1) + "\t";
								gm += "第" + (i+3) + "項：" + GM_Table.getINSinfo(i+2) + "\n";
							}else {
								
							}
							
						}

                        JPanel p = new JPanel(null);
                        JLabel gt = new JLabel(gm);
                        //gt.setEditable(false);
                        //gt.setEnabled(false);
                        JScrollPane scrol = new JScrollPane(gt);  
                        //gt.setBounds(0,0,150,200);                      
                        scrol.setBounds(0,0,100,400);
                        p.add(scrol);
                        p.setSize(200,400);
                        JOptionPane.showMessageDialog(null,p,"GM表",JOptionPane.INFORMATION_MESSAGE);
		    }
		}
	    );
	//************Menu的功能設定***************//

            this.setJMenuBar(bar);
            this.add(logolb);
            this.add(table);
            this.add(playPanel);
            this.add(exePanel);
            this.add(disPlayPanel);
            this.add(playListPanel);
            this.setSize(900,600);
            this.setVisible(true);  
    } //constructor
	
  
    /** 設定TalbePanel **/
    void setPanel(){
        //add logoPanel to application
        Icon logo = new ImageIcon( "PICTURE/logo.jpg" );
        logolb = new JLabel("",logo,SwingConstants.CENTER);
        logolb.setBounds(0,0,600,60);
        
        //add tablePanel to application
    	table = new TablePanel();
        table.setBounds(0,60,600,400);
        
        //add playPanel to application
        playPanel=new NewPlayPanel();
        playPanel.setBounds(0,460,600,80);       
        
        //add exePanel to application        
        exePanel =new ExePanel();
        exePanel.setBounds(601,0,290,190);
        
        //add disPlayPanel to application
        disPlayPanel = new DisplayPanel();
        disPlayPanel.setBounds(601,190,290,190);
        
        //add playListPanel to application
        playListPanel = new PlayListPanel();
        playListPanel.setBounds(601,380,290,160);
    }

    /** 設定MenuBar方法 **/
    void setMenu(){	
    	fileMenu      =new JMenu("檔案");
       	viewMenu      =new JMenu("檢視");
       	favoritesMenu =new JMenu("工具");
       	helpMenu      =new JMenu("幫助");
       	openItem      =new JMenuItem("匯入檔案");
	anotherItem   =new JMenuItem("另存新檔");
	newItem       =new JMenu("開啟新檔");
	anaItem       =new JMenuItem("分析_開啟新檔");
	transItem     =new JMenuItem("轉換_開啟新檔");
	comItem       =new JMenuItem("歌曲創作_開啟新檔");
       	exitItem      =new JMenuItem("結束");
	changItem     =new JMenu("版面切換");
	midiaItem     =new JMenuItem("midi解析");
	miditItem     =new JMenuItem("曲風轉換");
	midicItem     =new JMenuItem("歌曲創作");
        expalinItem   =new JMenuItem("說明");
        gmItem        =new JMenuItem("GM表");
      	fileMenu.add(openItem);
	fileMenu.add(anotherItem);
	fileMenu.add(newItem );	
      	fileMenu.add(exitItem);
	newItem.add(anaItem); 
	newItem.add(transItem); 
	newItem.add(comItem); 
	viewMenu.add(changItem);
	changItem.add(midiaItem);
	changItem.add(miditItem);
	changItem.add(midicItem);
        //favoritesMenu.add(gmItem);
        helpMenu.add(expalinItem);
      	bar =new JMenuBar();
        bar.setBorderPainted(true);
        bar.setBackground(Color.getHSBColor((float)0.6,(float)0.3,(float)1.0));
        bar.add(fileMenu);
        bar.add(viewMenu);
        //bar.add(favoritesMenu);
        bar.add(helpMenu);
        fileMenu.setBorderPainted(true);
        fileMenu.setBackground(Color.getHSBColor((float)0.6,(float)0.3,(float)1.0));
//        fileMenu.set

    }       
    
    private void changeTheLookAndFeel(int value){
        //change look feel
        try{
            UIManager.setLookAndFeel( looks[value].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }
        //process problems changing look and feel
        catch(Exception exception){
            exception.printStackTrace();
        }
    }
    
    private class T_SaveListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) {
           FilePackage fp = new FilePackage(table.tPanel.getPathName().replaceAll(".mid","")+"~"+table.tPanel.getSelected(),table.tPanel.getNewMidi(),null);
           disPlayPanel.addListItem(fp);   
       }
    }  

    private class C_SaveListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) {
           C_count ++;
           FilePackage fp = new FilePackage("創作歌曲 ~ "+C_count,table.cPanel.getNEWMIDI(),null);
           
           if(table.cPanel.getNEWMIDI() != null ){
                System.out.println("table.cPanel.getNEWMIDI() IS OK");
           }
           else{
                System.out.println("table.cPanel.getNEWMIDI() IS NOT OK");
           }

           disPlayPanel.addListItem(fp);   
       }
    }
    
    private class D_AddPlayListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) {
           int dIndex = disPlayPanel.getListSelectedIndex();
           if(dIndex < 0){
               JOptionPane.showMessageDialog(null,"請選取項目，再按Add Play","未選項",JOptionPane.WARNING_MESSAGE);
           }else{
               FilePackage fp = disPlayPanel.getSelectedFP();           
               playListPanel.addListItem(fp);
           }
            
       }
    }  
        
    private class E_EnsureListener implements ActionListener{
       public void actionPerformed (ActionEvent e) {
           int tIndex = table.getTabIndex();
           String path = exePanel.getSelctedPath();           
           if(path == "no item can be selected"){
                JOptionPane.showMessageDialog(null,"請選取項目，再按確認","未選項",JOptionPane.WARNING_MESSAGE);
           }else if(tIndex == 0){    //因點選AnalyzePanel，故傳至AnalyzePanel的pathLabel
               table.fPanel.getAnalyBtn().setEnabled(true);
               table.fPanel.getResetBtn().setEnabled(true);
               table.fPanel.setpathLb(path);
           }
           else if(tIndex == 1){
               table.tPanel.getTranstBtn().setEnabled(true);
               table.tPanel.getCanclBtn().setEnabled(true);
               table.tPanel.setpath(path);
           }
       }
    }   
    
    private class E_AddPlayListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) {       
           int fIndex = exePanel.getListSelectedIndex();           
           if(fIndex < 0){
                JOptionPane.showMessageDialog(null,"請選取項目，再按AddPlay","未選項",JOptionPane.WARNING_MESSAGE);
           }else{
               FilePackage fp = exePanel.getSelectedFP();
               playListPanel.addListItem(fp);     
           }
       }
    }
    
    private class D_EnsureListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) { 
           if(playListPanel.getListSelectedIndex() < 0){
               JOptionPane.showMessageDialog(null,"請選取項目，再按確認","未選項",JOptionPane.WARNING_MESSAGE);
               return ;
           }
           File ff;
           FilePackage f = playListPanel.getSelectItem();
           if(f.getPath() != null){
               ff = new File(f.getPath());
               playPanel.setMidiFile(ff); 
           }
           else if(f.getMidi() != null){
                 D_count ++;
                 ff = new File(".\\test\\midi"+D_count+".mid");
                 try{
                     output = new FileOutputStream(ff);
                 }catch(FileNotFoundException fe){
                     System.out.println("file no exist");
                 }
                 try{
                     output.write(f.getMidi());
                     output.close();                     
                 }catch(IOException io){} 
                 f.setPath(ff.getAbsolutePath());     //設定路徑                 
                 playPanel.setMidiFile(ff);                 
         }else{
              JOptionPane.showMessageDialog(null,"檔案有誤，請刪除原本的檔案","檔案有誤",JOptionPane.WARNING_MESSAGE);
         }
       }
    }
    
    private class D_DeletListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) { 
           int select=playListPanel.getListSelectedIndex();
           int pre=playListPanel.getListSelecdtPre();
           if(select <0){
               JOptionPane.showMessageDialog(null,"請選取項目，再按刪除","未選項",JOptionPane.WARNING_MESSAGE);
               return ;
           }else{
               FilePackage fp =  playListPanel.getSelectItem();
               String s = fp.getPath();
               byte [] midi = fp.getMidi();
               if(!(fp  == null || midi == null))  {
                   try{
                        File f = new File(s);
                        f.deleteOnExit();
                   }catch(Exception ex){
                        System.out.println("找不到檔案!");
                   }                          
               }
               playListPanel.setSelectedIndex(pre);
               playListPanel.removeSelectItem(select);
           }

           if( playListPanel.getList().getLastVisibleIndex() < 0){
               System.out.println("here");
               playPanel.getPreBtn().setEnabled(false);
               playPanel.getNextBtn().setEnabled(false);
           }
       }
    }  
 
    private class P_PreListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) { 
           File ff;
           FilePackage f =  playListPanel.getListSelecdtNextItem();
           if(f.getPath() != null){
               ff = new File(f.getPath());
               playPanel.setMidiFile(ff); 
           }
           else if(f.getMidi() != null){
                 D_count ++;
                 ff = new File(".\\test\\midi"+D_count+".mid");
                 try{
                     output = new FileOutputStream(ff);
                 }catch(FileNotFoundException fe){
                     System.out.println("file no exist");
                 }
                 try{
                     output.write(f.getMidi());
                     output.close();                     
                 }catch(IOException io){} 
                 f.setPath(ff.getAbsolutePath());     //設定路徑                 
                 playPanel.setMidiFile(ff);                 
         }else{
              JOptionPane.showMessageDialog(null,"檔案有誤，請刪除原本的檔案","檔案有誤",JOptionPane.WARNING_MESSAGE);
         }
       }
    }    

    private class P_NextListListener implements ActionListener{
       public void actionPerformed (ActionEvent e) { 
           File ff;
           FilePackage f = playListPanel.getListSelecdtNextItem();
           if(f.getPath() != null){
               ff = new File(f.getPath());
               playPanel.setMidiFile(ff); 
           }
           else if(f.getMidi() != null){
                 D_count ++;
                 ff = new File(".\\test\\midi"+D_count+".mid");
                 try{
                     output = new FileOutputStream(ff);
                 }catch(FileNotFoundException fe){
                     System.out.println("file no exist");
                 }
                 try{
                     output.write(f.getMidi());
                     output.close();                     
                 }catch(IOException io){} 
                 f.setPath(ff.getAbsolutePath());     //設定路徑                 
                 playPanel.setMidiFile(ff);                 
         }else{
              JOptionPane.showMessageDialog(null,"檔案有誤，請刪除原本的檔案","檔案有誤",JOptionPane.WARNING_MESSAGE);
         }
       }
    }

    public static void main(String args[]) {	
			Application application = new Application();
    		        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        application.setResizable(false);
                        application.setDefaultLookAndFeelDecorated(true);    
                        application.setDefaultLookAndFeelDecorated(true);
                        JFrame.setDefaultLookAndFeelDecorated(true);

    }
}




