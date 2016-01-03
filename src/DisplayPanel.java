import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; 
import java.io.*;
import java.io.ObjectInputStream;
import java.util.*;

public class DisplayPanel extends JPanel{
    private JList list;
    private FileOutputStream  T_MIDI_OUT;    
    private PlayList DisplayPlist;
    private JLabel areaName;
    private JButton resetNameBtn,deletBtn,SaveBtn,addlistBtn;   
    private DefaultListModel listModel;
    private Border loweredetched;
    
    public DisplayPanel() {
        this.setLayout(null); 
        
        DisplayPlist = new PlayList();
        //set border
        loweredetched = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        this.setBorder(loweredetched);
        
        /*****set areaName*****/
        areaName = new JLabel("  MIDI�e�{��");
        areaName.setBounds(0,0,290,30);
        /*****set areaName*****/
        
        /*****set list*****/
        listModel = new DefaultListModel();
        list = new JList(listModel);  	
        list.setCellRenderer(new IconListItemRenderer());
        list.setVisibleRowCount(7);
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION);     
        JScrollPane s = new JScrollPane(list);
        s.setBounds(5,35,280,120);
        /*****set list*****/
        
        /*******resetName********///------------------------------------------------------------------------------�p������W���|�P�B
        resetNameBtn = new JButton("��W");
        resetNameBtn.setBounds(5,165,60,20);       
        resetNameBtn.addActionListener (
                new ActionListener() {   
                    public void actionPerformed( ActionEvent event){
                        if(getListSelectedIndex() < 0){
                               JOptionPane.showMessageDialog(null,"�п�����ءA�A����W","���ﶵ",JOptionPane.WARNING_MESSAGE);
                        }else{
                               String newName = JOptionPane.showInputDialog(null,"�O�_�ק� ",DisplayPlist.getItem(list.getSelectedIndex()).getName().getText());
                               if(newName == DisplayPlist.getItem(list.getSelectedIndex()).getName().getText()){
                                   return ;
                               }else{
                                   DisplayPlist.getItem(list.getSelectedIndex()).getName().setText(newName);
                               }                               
                               list.setModel(listModel);                            
                        }                       
                    }
                }
        );
        /*******resetName********/
        
        /*******Deletbtn********/
        deletBtn = new JButton("�R��");
        deletBtn.setBounds(70,165,60,20);
        deletBtn.addActionListener (
                new ActionListener() {   
                    public void actionPerformed( ActionEvent event){
                       int select=getListSelectedIndex();
                       if(select < 0){
                           JOptionPane.showMessageDialog(null,"�п�����ءA�A���R��","���ﶵ",JOptionPane.WARNING_MESSAGE);
                       }else{
                           DisplayPlist.deletItem(select);
                           listModel.remove(select);
                       }
                    }
                }
        );
        /*******Deletbtn********/
        
        /*******Savebtn********/ 
        SaveBtn = new JButton("�x�s");
        SaveBtn.setBounds(135,165,60,20); 
        SaveBtn.addActionListener(new SaveListener());      //�N�qlist��������ئs��MIDI�� 
        /*******Ensurebtn********/ 
        
        /*******AddPlayList********///-----------------------------------------------���D�H���S�O�\�ζܡH�]���A�ഫ�w�����\��F
        addlistBtn = new JButton("Add����");
        addlistBtn.setBounds(200,165,83,20);       
        /*******AddPlayList********/
        
        /*******�����ܼ�********/
        this.add(areaName);
        this.add(s);
        this.add(resetNameBtn);
        this.add(deletBtn);
        this.add(SaveBtn);
        this.add(addlistBtn);
        this.setSize(290,190);
        this.setVisible(true);
        /*******�����ܼ�********/
    }
    
    public JButton getAddListBtn(){
        return addlistBtn;
    }
    
    public int getListSelectedIndex(){
        if(list.getSelectedIndex() >=0)
            return list.getSelectedIndex();
        else 
            return -1;
    }
    
    public FilePackage getSelectedFP(){
        return DisplayPlist.getItem(getListSelectedIndex());
    }
    
    public void addListItem(FilePackage fp){    
        this.DisplayPlist.addItem(fp);
        this.listModel.addElement(DisplayPlist.getLastItem());
        this.list.setModel(this.listModel);
    }
    
    private class SaveListener implements ActionListener{
            public void actionPerformed (ActionEvent e) {
                if(getListSelectedIndex() < 0){
                    JOptionPane.showMessageDialog(null,"�п�����ءA�A��Save","���ﶵ",JOptionPane.WARNING_MESSAGE);
                }else{
                        JFileChooser fc = new JFileChooser();
                        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                        int result = fc.showSaveDialog( null );

                        try {
                         //set up file and stream   
                         File outFile =fc.getSelectedFile();
                         T_MIDI_OUT = new  FileOutputStream(outFile);                             
                         T_MIDI_OUT.write( DisplayPlist.getItem(list.getSelectedIndex()).getMidi() );                             
                         T_MIDI_OUT.close();                           
                        }catch(FileNotFoundException event){
                                System.out.println("�x�����ɮ�!");                            
                        }catch(IOException event){
                                System.out.println("�x�����ɮ�!");
                        }catch(Exception ex){
                                 System.out.println("�x�����ɮ�!");
                        }      
                        
                       System.out.println("OK");
                }
            }
    }
}
