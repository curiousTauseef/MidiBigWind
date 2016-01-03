import java.util.*;
import javax.swing.*;
public class PlayList {
    private LinkedList<FilePackage> list ;   
    public PlayList() {   
        list = new LinkedList<FilePackage>();
    }
    
    public void addItem(FilePackage fp){
        list.add(fp);
    }
    
    public void deletItem(int indext){
        list.remove(indext);
    }
    
    public FilePackage getItem(int indext) {
        return list.get(indext);
        
    }
    
   public void setItem(int indext, byte m[]){
       list.get(indext).setMidi(m);
   } 
   
   
    
    public JLabel getLastItem(){
        return list.getLast().getName();
    }
    
    public void removeAll(){
        list.clear();
    }
}
