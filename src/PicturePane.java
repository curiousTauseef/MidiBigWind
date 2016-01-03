import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 import javax.swing.border.*; 
class PicturePane extends JPanel
{
   private Icon pImg;
   private String pN; 
   
   PicturePane() {
//    this.setLayout(null);
   }
   PicturePane(String imgPath,String name)
   {
      setLayout(null);
      setIcon(imgPath);
      setName(name);
      
      //設定圖片
      JLabel lbp = new JLabel("1111");
      lbp.setIcon(pImg);
      lbp.setToolTipText("This is "+pN);
      this.add(lbp);
      lbp.setBounds(0,0,100,100);     
      lbp.setSize(100,100);

     
      //設定文字
      JLabel lbname =new JLabel(pN);
      this.add(lbname);
      lbname.setBounds(0,100,100,100);
      lbname.setSize(100,100);      
      
       //設定相關背景
        Border blackline, raisedetched, loweredetched, raisedbevel, loweredbevel, empty;
        //blackline = BorderFactory.createLineBorder(Color.black);
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        //loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        //loweredbevel = BorderFactory.createLoweredBevelBorder();
        //empty = BorderFactory.createEmptyBorder();
        //Compound borders
       Border compound;
       Border redline = BorderFactory.createLineBorder(Color.red);
      //This creates a nice frame.
      compound = BorderFactory.createCompoundBorder( raisedetched, raisedbevel);
      //Add a red outline to the frame.
      compound = BorderFactory.createCompoundBorder(redline, compound);
      this.setBorder(compound);         
      this.setBackground(Color.GREEN);            
      this.setSize(100,100);  
      this.setVisible(true);
   }   
   
   public void setIcon(String imgPath)
   {
      pImg = new ImageIcon(imgPath);
   } 
   public void setName(String name)
   {
      pN=name;
   }  
 
   public Icon getImg()
   {
      return pImg;
   }
   
   public String getName()
   {
      return pN;
   }

}