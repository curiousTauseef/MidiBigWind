import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 
import java.io.*;
import java.io.ObjectInputStream;
public class PlayListPanel extends JPanel{
    private JList list;
    private PlayList plist = new PlayList();
    private JLabel areaName;
    private JButton deletBtn,ensureBtn;
    
    private DefaultListModel listModel;
    private Border loweredetched;    
    public PlayListPanel() {
         this.setLayout(null); 
        
        //set border
        loweredetched = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        this.setBorder(loweredetched);
        
        /*****set areaName*****/
        areaName = new JLabel("  MIDI播放清單");
        areaName.setBounds(0,0,290,30);
        /*****set areaName*****/
        
        /*****set list*****/
        listModel = new DefaultListModel();
        list = new JList(listModel);  	       
        list.setCellRenderer(new IconListItemRenderer());
        list.setVisibleRowCount(7);        
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION);     
        JScrollPane s = new JScrollPane(list);
        s.setBounds(5,35,280,90);
        /*****set list*****/
        ensureBtn = new JButton("確認");
        ensureBtn.setBounds(148,135,60,20);
         /*******Deletbtn********/
        deletBtn = new JButton("刪除");
        deletBtn.setBounds(223,135,60,20);
         /*******Deletbtn********/

        
        /*******環境變數********/
        this.add(areaName);
        this.add(s);
        this.add(ensureBtn);
        this.add(deletBtn);
        this.setSize(290,160);
        this.setVisible(true);
        /*******環境變數********/
    }
    
    public JButton getEnsureBtn(){
        return ensureBtn;
    }
    
    public JButton getDeletBtn(){
        return deletBtn;
    }
    
    public void addListItem(FilePackage fp){
        plist.addItem(fp);       
        listModel.addElement(plist.getLastItem());
    }
    
    public JList getList(){
        return list;
    }
    
    public void setSelectedIndex(int index){
        list.setSelectedIndex(index);
    }
    
    public int getListSelectedIndex() {
        if(list.getSelectedIndex() >=0)
            return list.getSelectedIndex();
        else 
            return -1;
    }
    
    public int getListSelecdtNext(){
        int i =list.getSelectedIndex()+1;       
        if( i > list.getLastVisibleIndex()){
            return 0;
        }else{
            return i;
        }
    }
    
    public FilePackage getListSelecdtNextItem(){       
        int j =getListSelecdtNext();
        setSelectedIndex(j);
        return plist.getItem(j);        
    }
    
    public int getListSelecdtPre(){
        int  i =list.getSelectedIndex()-1;
        if( i < 0){
            return list.getLastVisibleIndex();
        }else{
           return i;
        }
    }
    
    public FilePackage getListSelecdtPreItem(){       
        int j = getListSelecdtPre();        
        setSelectedIndex(j);
        return plist.getItem(j);
    }

    public FilePackage getSelectItem(){        
        return plist.getItem(getListSelectedIndex());        
    }
   
    public void removeSelectItem(int select){
        plist.deletItem(select);
        try{
            listModel.remove(select);
        }catch(ArrayIndexOutOfBoundsException a){
            
        }catch(IndexOutOfBoundsException in){
            
        }    
    }
    
    public String getSelectItemString(){
      FilePackage b =  (FilePackage)list.getSelectedValue();
        String s =b.getPath();
        return s;
    }    
}
