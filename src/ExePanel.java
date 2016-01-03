import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 
import java.io.*;
import java.io.ObjectInputStream;
public class ExePanel extends JPanel{
    private JList list;
    private JLabel areaName;
    private JButton addBtn,ensureBtn,deletBtn,addlistBtn;
    private FilePackage fp;
    private PlayList plist = new PlayList();
    private DefaultListModel listModel;
    private OpenFile openFile; 
    private Border loweredetched;
    
    public ExePanel(){
        this.setLayout(null);  
        
        //set border
        loweredetched = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        this.setBorder(loweredetched);
        
        /*****set list*****/
        listModel = new DefaultListModel();
        list = new JList(listModel);  	
        list.setVisibleRowCount(7);
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION);     
        JScrollPane s = new JScrollPane(list);
        s.setBounds(5,35,280,120);
        /*****set list*****/
        
        /*****set areaName*****/
        areaName = new JLabel("  MIDI�����");
        areaName.setBounds(0,0,290,30);
        /*****set areaName*****/
        
        /*******Addbtn********/
        addBtn = new JButton("�פJ");
        addBtn.setBounds(5,165,60,20);       
        addBtn.addActionListener (
                new ActionListener() {   
                    public void actionPerformed( ActionEvent event){  
                        
                        // ���ͤ@���ɡA�åHFilePackage�ʸ˰_�ӥ[�J��plist�A�A�qplist���XJLabel���list��
                        openFile = new OpenFile();    
                        try{
                            File f = openFile.getFile();                         
                            if(f.exists()){
                               fp = new FilePackage(f.getName().replaceAll(".mid",""),null,f.getAbsolutePath());
                               plist.addItem(fp);
                               list.setCellRenderer(new IconListItemRenderer());
                               listModel.addElement(plist.getLastItem());
                           }else{
                               System.out.println("not ok");
                           }
                       }catch(Exception ioe){
                            System.out.println("in exePanel, you don't chose any file");
                       }
                        
                    }
                }
        );
        /*******Addbtn********/
        
        /*******Ensurebtn********/// 
        ensureBtn = new JButton("�T�w");
        ensureBtn.setBounds(70,165,60,20);        
        /*******Ensurebtn********/ 
        
        /*******Deletbtn********/
        deletBtn = new JButton("�R��");
        deletBtn.setBounds(135,165,60,20);
        deletBtn.addActionListener  (
                new ActionListener() {   
                    public void actionPerformed( ActionEvent event)  {
                       //�R����select��--------�qplist�Mlist��Ӥ@�_�R 
                       int select=getListSelectedIndex();
                       if(select < 0){
                           JOptionPane.showMessageDialog(null,"�п�����ءA�A���R��","���ﶵ",JOptionPane.WARNING_MESSAGE);
                       }else{
                           plist.deletItem(select);                  
                           listModel.remove(select);
                       }
                    }
                }
        );
        /*******Deletbtn********/
        
        /*******AddPlayList********///--------------------------------------------------------------------��application 
        addlistBtn = new JButton("Add����");
        addlistBtn.setBounds(200,165,83,20);
        /*******AddPlayList********/
        
        /*******�����ܼ�********/
        this.add(s); 
        this.add(areaName);
        this.add(addBtn);
        this.add(ensureBtn);
        this.add(deletBtn);
        this.add(addlistBtn);
        this.setSize(290,190);
        this.setVisible(true);
        /*******�����ܼ�********/
    }
    
    public JButton getAddBtn(){
        return addBtn;
    }
    
    public JButton getEnsureBtn(){
        return ensureBtn;
    }
    
    public JButton getDeletBtn(){
        return deletBtn;
    }
    
    public JButton getAddPlayBtn(){
        return addlistBtn;
    }
    
    public int getListSelectedIndex() {
            return list.getSelectedIndex();
    }
    
    public PlayList getPlayList(){
        return plist;
    }
    
    public FilePackage getSelectedFP(){
        return plist.getItem(getListSelectedIndex());
    }
    
    public String getSelctedPath(){
        if(getListSelectedIndex() >=0 ){
            return plist.getItem(getListSelectedIndex()).getPath();
        }else{
            return "no item can be selected";
        }
    }

    public JList getList(){
	return list;
    }    
    
    public DefaultListModel getListModel(){
	return listModel;
    }
}
