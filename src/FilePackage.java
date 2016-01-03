import javax.swing.*;
public class FilePackage{
    private JLabel name;
    private byte midi[];
    private String path;
    private Icon face;
    
    public FilePackage() {}
    
    public FilePackage(String n,byte m[],String p){
        face =new ImageIcon("PICTURE/ph.gif");
        name = new JLabel(n,face,SwingConstants.LEFT);
        midi=m;
        path=p;
    }
    
    public JLabel getName(){
        return name;
    }
    
    public byte[] getMidi(){
        return midi;
    }
    
    public String getPath(){
        return path;
    }
    
    public void setMidi(byte m[]){
        midi=m;
    }
    
    public void setPath(String s){
        path = s;
    }
}
