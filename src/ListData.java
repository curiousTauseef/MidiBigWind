import java.util.*;
import javax.swing.*;
public class ListData{
   
        private String name="ListData";  // string like "list" used in printing
        private String paraentPath="no paraentPath";          //存放父路徑
        private LinkedList<String>  listFiles =  new LinkedList<String>();  //存放File的名稱
	private LinkedList<String>   listPath  =  new LinkedList<String>();  //存放File的路徑
        

        public ListData(){        }
  
        public ListData(String[] filesName,String filesPath){ 		
                paraentPath=filesPath;    //設定父路徑
                for(int i=0; i<=filesName.length-1; i++){
                        
			listFiles.add(filesName[i]);                //設定filesName
			listPath.add(filesPath+"/"+filesName[i]);   //設定filesPath
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