import java.util.*;
import javax.swing.*;
public class ListData{
   
        private String name="ListData";  // string like "list" used in printing
        private String paraentPath="no paraentPath";          //�s������|
        private LinkedList<String>  listFiles =  new LinkedList<String>();  //�s��File���W��
	private LinkedList<String>   listPath  =  new LinkedList<String>();  //�s��File�����|
        

        public ListData(){        }
  
        public ListData(String[] filesName,String filesPath){ 		
                paraentPath=filesPath;    //�]�w�����|
                for(int i=0; i<=filesName.length-1; i++){
                        
			listFiles.add(filesName[i]);                //�]�wfilesName
			listPath.add(filesPath+"/"+filesName[i]);   //�]�wfilesPath
		}	
        }

	public LinkedList<String> getAllFilesName(){
		return listFiles;
	}	

	public LinkedList<String> getAllFilesPath(){
		return listPath;
	}

	public String getFileParent(){
		return paraentPath;
	}

	public LinkedList<String> getSelectedFiles(int[] i){
                LinkedList<String>   listPartTime = new LinkedList<String>();
                if(i.length<=listFiles.size() && i.length>=1){                	
			for(int k=0; k< i.length; k++){
				listPartTime.add( listFiles.get( i[k] ) );
			}
		}
                return listPartTime;
	}

	public LinkedList<String> getSelectedFilesPath(int[] i){
		LinkedList<String>   listPartTime = new LinkedList<String>();
                System.out.println("i'size is "+i.length);
		if(i.length<=listFiles.size() && i.length>=1){			
			for(int k=0; k< i.length; k++){		
                            listPartTime.add( listPath.get( i[k] ) );
			}                       
		}
                return listPartTime;
	}

	public void removeSelectedFile(int i){            
		listFiles.remove(i);
		listPath.remove(i);
	}

	public void removeAll(){
		listFiles.clear();
		listPath.clear();
	}

	public static void main(String args[]) {	
            	ListData list= new ListData();
                System.out.println("ListData test");
        }

}