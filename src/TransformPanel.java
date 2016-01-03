import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*; 
import java.io.*;
import java.util.*;
import Arg.TOMArg;
public class TransformPanel extends JPanel  
{     
    /*物件宣告*/
    private static int c=0;
    private int MIDI_STYLE;
    private byte T_data[],NewMidi[];
    private File f;
    private FileOutputStream  T_MIDI_OUT;
    private FileInputStream T_MIDI_IN;    
    private TOMArg TOMObj;
    private String pathName="";
    private OpenFile openFile; 
    private JPanel zeroPane,headPane,rightPane;
    private JScrollPane rightScrollPane;   
    private JButton btnPicture,btnColor;           //圖片按鈕,顏色按鈕    
    private JLabel spLb,sfLb,spfLb;
    private JButton fbtnTrans,fbtnSave,fbtnCancl;
    private Icon face;
    private ButtonGroup emotionRadioG = new ButtonGroup();
    private Border raisedbevel,loweredbevel;
    
    
    /*設定按圖片、顏色而產生的按鈕的button text*/       
    String rArr[][] ={{"PICTURE/抒情-悲傷.jpg","PICTURE/抒情-鋼琴(輕快).jpg","PICTURE/rock-high.jpg","PICTURE/rock-沉重.jpg","PICTURE/古典-幻想.jpg"},
                      {"PICTURE/紅.jpg","PICTURE/紫.jpg","PICTURE/黃.jpg","PICTURE/綠.jpg","PICTURE/橙.jpg"}};
    String mArr[][] ={ {"悲傷","輕快","high","沉重","幻想"},
                       {"紅-熱情、開朗","紫-神秘","黃-沉思","綠-自然","橙-堅強、自信"}};
   
    
    ButtonHandler handler = new ButtonHandler();
    RightButtonHandler rightHandler = new RightButtonHandler(); 

    public TransformPanel(){     
         this.setLayout(null);
         //set border
             raisedbevel = BorderFactory.createRaisedBevelBorder();
             loweredbevel = BorderFactory.createLoweredBevelBorder();
         
         /*設定好版面*/ 
             zeroPane =new JPanel(null);
             zeroPane.setBounds(10,10,570,50);

             headPane =new JPanel(null);        
             headPane.setBorder(raisedbevel); 
             headPane.setBounds(10,65,570,30);

             rightPane = new JPanel(new FlowLayout());
             rightScrollPane =new JScrollPane(rightPane);
             rightPane.setPreferredSize(new Dimension(520,710));
             rightPane.setBounds(10,105,570,240);          
             rightScrollPane.setBounds(10,105,570,240); 
             
          
         /*選檔功能(zeroPane)-內含spLb,sfLb,fbtnTrans,fbtnSave,fbtnCancl*/
             //set sfLb to zeroPane
             sfLb= new JLabel("請點選要轉換的midi路徑：");
             sfLb.setBounds(10,3,350,20);

             //set spLb to zeroPane
             face =new ImageIcon("yest.gif");
             spLb = new JLabel(face);
             spLb.setBounds(10,27,20,20);       
             

             //set spfLb to zeroPane
             spfLb = new JLabel("");
             spfLb.setBounds(40,27,320,20);
             spfLb.setBorder(loweredbevel);
             
             //set fbtnTrans to zeroPane
             fbtnTrans = new JButton("轉換");       
             fbtnTrans.setBounds(370,5,60,20);
             fbtnTrans.setFont(new Font("Serif", Font.PLAIN,12));
             fbtnTrans.addActionListener (new TransListener()); //依選取的MIDI檔，做音樂的轉換，並存成file
             fbtnTrans.setEnabled(false);
            
             //set fbtnSave to zeroPane
             fbtnSave = new JButton("匯出");
             fbtnSave.setBounds(435,5,60,20);
             fbtnSave.setFont(new Font("Serif", Font.PLAIN,12));
             fbtnSave.setEnabled(false); 
            
             //set fbtnCancl to zeroPane
             fbtnCancl = new JButton("取消");
             fbtnCancl.setBounds(500,5,60,20);
             fbtnCancl.setFont(new Font("Serif", Font.PLAIN,12));
             fbtnCancl.addActionListener (new CancleListener());    //重新設定
             fbtnCancl.setEnabled(false); 
             
             zeroPane.add(sfLb);
             zeroPane.add(spLb);             
             zeroPane.add(spfLb);
             zeroPane.add(fbtnTrans);
             zeroPane.add(fbtnSave);  
             zeroPane.add(fbtnCancl);
         
             this.add(zeroPane);
             this.add(headPane);
             this.add(rightScrollPane);
             this.setSize(600,400);
             this.setVisible(true);
             
             
         /*選方式(haedPane)-內含btnPicture,btnColor*/    
             //set btnPicture
             btnPicture = new JButton("情境傳達");
             btnPicture.setBounds(5,5,110,20);        
             btnPicture.addActionListener(handler);

             //set btnColor
             btnColor   = new JButton("顏色透視");
             btnColor.setBounds(120,5,110,20);         
             btnColor.addActionListener(handler);

             headPane.add(btnPicture);
             headPane.add(btnColor);

         
         /*把相對位於rightButton的圖的片放入,並把抒情的rightButton當預選*/       
             boolean a,b;
             for(int  i=0 ; i<rArr.length ; i++){
                  for(int  j=0 ; j< rArr[i].length ; j++) {  
                      /*把情境傳達的button都visible*/
                      if(i==0)   a=true;
                      else       a=false;
                      /*把第一個radioButton當預選*/
                      if(i==0 && j==0)   b=true;
                      else       b=false;
                      addlb(rArr[i][j].toString(),mArr[i][j].toString(),a,b);
                  }
             }             
     }  
    
    public JButton getTranstBtn() {
    	return fbtnTrans;
    }
    
    public JButton getSaveBtn(){
        return fbtnSave;
    }
    
    public String getpath(){
        return spfLb.getText();
    }
    
    public String getPathName(){
        return pathName;
    }
    public void setpath(String path){
        spfLb.setText(path);
    }
    
    public JButton getCanclBtn(){
    	return fbtnCancl;   
   }      
    
    public int getR(){
          int count =0;          
          int g=0;
          JRadioButton b = new JRadioButton() ;  
          JRadioButton c = new JRadioButton() ;
          for(int  i=0 ; i<rArr.length ; i++){
              for(int  j=0 ; j< rArr[i].length ; j++) {
                 b = (JRadioButton) rightPane.getComponent(count);
                 if( b.isSelected() ){
                     c=b ;
                     g=count;
                 }                      
                 count ++;
              }             
         }              
         return (g+1);
     }
    
    public String getSelected(){
        int i =getR();
        if( (i-1) <= 4){
            return mArr[0][i-1];
        }else{
            return mArr[1][i-6];
        }
    }
    
    public  void setbtnState(int bOffset,int offset,boolean state){   

           JRadioButton  b =new JRadioButton();   
           
           /*列出隸屬rightPane的button*/
           for(int h= bOffset ; h<offset ; h++){                    
                b = (JRadioButton) rightPane.getComponent(h);                   
                b.setVisible(state);
           }    
    }
    
    public void addlb(String pass,String description,boolean visible,boolean select){
         Border border = BorderFactory.createLineBorder(Color.black);
         Icon labImg = new ImageIcon(pass);
         JRadioButton b = new JRadioButton(description,labImg) ;         
         
        /*按鈕的週邊設定*/
         b.setBorder(border); 
         b.setHorizontalAlignment(SwingConstants.CENTER);
         b.setVerticalTextPosition(AbstractButton.BOTTOM);
         b.setHorizontalTextPosition(AbstractButton.CENTER);
         b.setPreferredSize(new Dimension(230,230)); 
         
         /*把b RadioButton加入到ButtonGroup、rightPane*/
         emotionRadioG.add(b);
         rightPane.add(b);         
         b.setVisible(visible);         
         b.addItemListener(rightHandler);
         
     }     
      
    public byte[] getNewMidi(){
        return NewMidi;
    }
    
    private class  TransListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                   openFile = new OpenFile(getpath());                   
                    try{
                            T_MIDI_IN = new FileInputStream(openFile.getFile()); 
                            T_data = new byte[T_MIDI_IN.available()];                            
                            T_MIDI_IN.read(T_data);   
                            T_MIDI_IN.close();
                            pathName = openFile.getFileName();
                    }
                    catch(FileNotFoundException fe){ System.out.println("A_FileOpen's FileNotFoundException");  }
                    catch(IOException ioe){   System.out.println("找不到檔案!");  }
                    
              		TOMObj = new TOMArg(T_data,getR());
                    NewMidi = TOMObj.getMIDIFile(); 
//                    f = new File(".\\test\\midi.mid");
//                    try{
//                     T_MIDI_OUT = new FileOutputStream(f);
//                     }catch(FileNotFoundException fe){
//                         System.out.println("file no exist");
//                     }
//                     try{
//                         T_MIDI_OUT.write(f.getMidi());
//                         T_MIDI_OUT.close();                     
//                     }catch(IOException io){} 
                    
                    fbtnTrans.setEnabled(false);
                    fbtnSave.setEnabled(true); 
                    fbtnCancl.setEnabled(true);
          }
     }   
    
    private class CancleListener implements ActionListener{
          public void actionPerformed (ActionEvent e) {
                    spfLb.setText("");
                    openFile = null;
                    fbtnTrans.setEnabled(true);
                    fbtnSave.setEnabled(false);
          }
     }    
    
    //暫留用來判斷itemStateChanged的問題------------------------------------------------------------------------
    private class RightButtonHandler implements ItemListener{         
         public void itemStateChanged(ItemEvent event)
         {
             c++;
             System.out.println("  "+c+"++---"+getR()+"-----++");
         }
     }
    
    private class ButtonHandler implements ActionListener{       
         public void actionPerformed(ActionEvent event){         
        
            //圖片
            if(event.getSource()==btnPicture){                
                setbtnState(5,10,false);
                setbtnState(0,5,true);          
            }
            
            //顏色
            if(event.getSource()==btnColor){                            
                setbtnState(0,5,false);
                setbtnState(5,10,true);                
            }  
            
         }
     }   
}