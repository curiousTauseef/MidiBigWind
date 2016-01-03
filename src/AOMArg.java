package Arg;
import java.io.*;
import java.util.*;
import Arg.GM.GMtable;
public class AOMArg{
	
	private byte[] MIDI_INFO;
	private int SUM_TRACK;
	private int TEMPO;
	private int SUM_INS;
	private int[] intINS_ARRAY;
	private String[] INS_Array;
	private GMtable GM = new GMtable();
	
	public AOMArg (byte[]  MIDI_INPUT){
		MIDI_INFO = MIDI_INPUT;
		setSum_TRACK();
		setTEMPO();
		setSUM_INS();
		
		for (int i = 0; i <INS_Array.length; i ++)
			System.out.println(INS_Array[i]);
	}
	private void setSum_TRACK(){
		for (int i = 0;i < MIDI_INFO.length;i++){
			
			if (MIDI_INFO[i] == 0x4D){
				if (MIDI_INFO[i+1] == 0x54 && MIDI_INFO[i+2] == 0x72 && MIDI_INFO[i+3] ==0x6B){
					
					SUM_TRACK += 1;
				}
			}
		}//end of for
		//System.out.println(SUM_TRACK);
		//for (int i=0;i<MIDI_INFO.length;i++) System.out.println(MIDI_INFO[i]);
	}
	private void setTEMPO(){
		boolean CheckFIRST = true;
		for (int i = 0;i < MIDI_INFO.length;i++){
			
			if (MIDI_INFO[i] == 0xFF-(0xff+1) ){
				
				if (MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03){
					
					int d1 = MIDI_INFO[i+3] * 65536;
					int d2 = MIDI_INFO[i+4] * 256;
					int d3 = MIDI_INFO[i+5];
					int T = (d1 + d2 + d3);  
					if (CheckFIRST == true) {	TEMPO = 60*1000000/T; CheckFIRST = false;	}
					//System.out.println(TEMPO);
				}
			}
		}//end of for
	}
	private void setSUM_INS(){
		boolean DoubleCH = false;/* �P�_�O�_�����n�D */ 
    	HashSet<String> allINSname = new HashSet<String>();
    	HashSet<Integer> allINSint = new HashSet<Integer>();	
    		for (int i = 0;i < MIDI_INFO.length;i++) {
    			
    			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){
    				SUM_INS += 1;
    				allINSname.add( "��h����" );
    			}
    			
    			if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		    	/** ���n�D���p **/
    				if ( MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) < 16 ){
    					SUM_INS += 1;
    					allINSname.add( GM.getINSinfo((int)MIDI_INFO[i+1]) );  
    					allINSint.add( (int)MIDI_INFO[i+1] );
    					DoubleCH = true;
    				}
    				/** ���n�D���p **/
    				if (DoubleCH == false){
    					SUM_INS += 1;
    					allINSname.add( GM.getINSinfo((int)MIDI_INFO[i+1]) );
    					allINSint.add( (int)MIDI_INFO[i+1] );
    				}	
    			}
    		}//end of for(ch)
    	INS_Array = SetToArrays( allINSname );
    	intINS_ARRAY = SetToArrays( allINSint );
	}
	
	//** getValue Method**
	public int getSUM_TRACK(){ return SUM_TRACK; };
	public int getTEMPO() {	return TEMPO; }
	public int getSUM_INS(){ return SUM_INS;	}
	public String[] getINS_Array() { return INS_Array;	}
	public int[] getintINS_ARRAY(){ return intINS_ARRAY;	}
	public String getMIDI_INFO() { return "Not yet!!";	} // <---����ݭק�
	
	
	//***�u��***//
    private String[] SetToArrays ( HashSet< String > allINSname ){
    	Iterator< String > Walker = allINSname.iterator(); //�إߤ@�Ө��X��  	
    	int ArraySize = allINSname.size();					//�}�C�ݭn���j�p
    	String[] allINSArray = new String[ArraySize];				//�إ߰}�C
    	/** Set�ഫ��Array **/
    	for (int i = 0; i < allINSArray.length; i ++){	
			allINSArray[i] = Walker.next();		
    	}		
    	return allINSArray;									//�^�ǰ}�C
    }
    private int[] SetToArrays ( HashSet< Integer > allINSname ){
    	Iterator< Integer > Walker = allINSname.iterator(); //�إߤ@�Ө��X��  	
    	int ArraySize = allINSname.size();					//�}�C�ݭn���j�p
    	int[] allINSArray = new int[ArraySize];				//�إ߰}�C
    	/** Set�ഫ��Array **/
    	for (int i = 0; i < allINSArray.length; i ++){	
			allINSArray[i] = Walker.next();		
    	}		
    	return allINSArray;									//�^�ǰ}�C
    }

    /*
    public static void main (String args[]) {
    	try {
    		FileInputStream Fi = new FileInputStream("D:\\TEST.mid"); 
    		byte[] test = new byte[Fi.available()];
    		Fi.read(test);
    		Fi.close();
    		new AOMArg(test);
    		
    	}catch(IOException e){
    		System.out.println("�䤣���ɮ�!");
    	}
    	
    }*/
    	
}