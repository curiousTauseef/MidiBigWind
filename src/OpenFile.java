import java.io.*;
import javax.swing.*;
public class OpenFile {
    private int option=0;
    private JFileChooser fileChooser;
    private File file,fileDirectory;
    private String filePath,fileName;
    public OpenFile(){
        		
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES  );
        option = fileChooser.showOpenDialog( null);       
        
  	if ( option == JFileChooser.CANCEL_OPTION ){    // clicked Cancel button on dialog  	
                file = null;
 	}else if ( option == JFileChooser.APPROVE_OPTION  ){   // clicked Ensure button on dialog       
	        file = fileChooser.getSelectedFile(); // get selected file
                try{
                    filePath = file.getCanonicalPath();
                    fileName = file.getName();
                }
                catch(IOException ioe){
                    System.out.println("filePath not found.");
                    System.exit(0);
                }
        }else if( option == JFileChooser.ERROR_OPTION ){
		if ( file == null || file.getName().equals( "" ) ){    // open error if invalid 
                     fileChooser.cancelSelection();
                     JOptionPane.showMessageDialog( null, "Invalid File","Invalid File", JOptionPane.ERROR_MESSAGE );                     
                }               
	}
    }
    
    public OpenFile(String FilePath){
        file = new File(FilePath);
        try{
            fileName = file.getName();
            filePath = file.getCanonicalPath();
        }catch(IOException ioe){
                    System.out.println("filePath not found.");
                    System.exit(0);
        }
    }
    
    public File getFile() {
       return file;
   }
    
    public File getFileCurrentDirectory(){
        return  fileChooser.getCurrentDirectory();
    }
    
    public String getAbsolutePath(){       
      return  filePath;       
    }
    
    public String getFileName(){
        return fileName;
    }
}