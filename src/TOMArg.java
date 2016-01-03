package Arg;
import java.io.*;
import java.util.*;
public class TOMArg{
	
	private byte[] data,MIDI_INFO;
	private int MIDI_STYLE;
	
	public TOMArg(byte[]  MIDI_INPUT,int STYLE) {
		MIDI_INFO = MIDI_INPUT;//MIDI�}�C
		MIDI_STYLE = STYLE;    //MIDI����
		
		if (MIDI_STYLE == 1){TransToTypeOne();	
		}else if (MIDI_STYLE == 2){	TransToTypeTwo();
		}else if (MIDI_STYLE == 3){	TransToTypeThree();
		}else if (MIDI_STYLE == 4){ TransToTypeFour();
		}else { TransToTypeFive();	}
		
	}
	/**typeone �籡�d��**/
	private void TransToTypeOne (){
		boolean DoubleCH = false;/* �P�_�O�_�����n�D */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**�@�B�־��ഫ�t��k**/
			/** ��ĤQ�y **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){
					
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    			
    			int tem = (int)(Math.random()*100000 % 50);
    			int GT;
    			int GT2;
    			if (tem < 24){
    				GT = (int)Math.floor(Math.random()*15 + 8);//8-23���־�
    				GT2 = (int)Math.floor(Math.random()*15 + 72);//72-87���־�
    			}else{
    				GT = (int)Math.floor(Math.random()*1 + 24);//1-25���־�
    				GT2 = (int)Math.floor(Math.random()*15 + 72);//72-87���־�
    			}
    		   	/** ���n�D���p **/
    			if ( MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) < 16 ){    				
    				
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					MIDI_INFO[ i+3 ] = (byte)GT;	
    				}else if (MIDI_INFO[ i+2 ] > 40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    					MIDI_INFO[ i+3 ] = (byte)GT2;
    				}
    				DoubleCH = true;
    			}
    			/** ���n�D���p **/
    			if (DoubleCH == false){
    				/**1.�N�L���־�**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					//System.out.println(GT);
    					//System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    		}
    		/**�G�B��l�ഫ�t��k ��t�G80~100 ����**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			//����TEMPO
				int TEMPO = (int)Math.floor(Math.random()*15+80);
				int T = 60*1000000/TEMPO;
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				MIDI_INFO[i+3] = (byte)(T / 65536);
    				MIDI_INFO[i+4] = (byte)(T % 65536 / 256);
    				MIDI_INFO[i+5] = (byte)(T % 256);
    			
    			//	System.out.println(MIDI_INFO[i+5]);
    			}
    			
    		}
		}//end of for 
	}
	/**typetwo �籡����**/
	private void TransToTypeTwo (){
		boolean DoubleCH = false;/* �P�_�O�_�����n�D */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**�@�B�־��ഫ�t��k**/
			/** ��ĤQ�y **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	
    		   	int GT = (int)Math.floor(Math.random()*7);//0-7���־�
    			int GT2 = (int)Math.floor(Math.random()*15 + 72);//72-87���־�
    			
    		   	/** ���n�D���p **/
    			if ( MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) < 16 ){
    				
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					MIDI_INFO[ i+3 ] = (byte)GT;	
    				}else if (MIDI_INFO[ i+2 ] > 40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    					MIDI_INFO[ i+3 ] = (byte)GT2;
    				}
    				DoubleCH = true;
    			}
    			/** ���n�D���p **/
    			if (DoubleCH == false){
    				/**1.�N�L���־�**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					//System.out.println(GT);
    					//System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    		}
    		/**�G�B��l�ഫ�t��k ��t�G110-130��**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//����TEMPO
					int TEMPO = (int)Math.floor(Math.random()*20+110);
					int T = 60*1000000/TEMPO;
    				MIDI_INFO[i+3] = (byte)(T / 65536);
    				MIDI_INFO[i+4] = (byte)(T % 65536 / 256);
    				MIDI_INFO[i+5] = (byte)(T % 256);
    			//	System.out.println(MIDI_INFO[i+5]);
    			}
    			
    		}
		}//end of for
	}
	/**typethree �n�uhigh**/
	private void TransToTypeThree (){
		boolean DoubleCH = false;/* �P�_�O�_�����n�D */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**�@�B�־��ഫ�t��k**/
			/** ��ĤQ�y **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	
    		   	int GT = (int)Math.floor(Math.random()*2 + 28);//24-31���־�
    			int GT2 = (int)Math.floor(Math.random()*7 + 48);//48-55���־�
    			
    		   	/** ���n�D���p **/
    			if ( MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) < 16 ){
    				
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					MIDI_INFO[ i+3 ] = (byte)GT;	
    				}else if (MIDI_INFO[ i+2 ] > 40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    					MIDI_INFO[ i+3 ] = (byte)GT2;
    				}
    				DoubleCH = true;
    			}
    			/** ���n�D���p **/
    			if (DoubleCH == false){
    				
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					//System.out.println(GT);
    					//System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    			
    		}
    		
    		/**�G�B��l�ഫ�t��k ��t�G130-150��**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//����TEMPO
					int TEMPO = (int)Math.floor(Math.random()*20+130);
					int T = 60*1000000/TEMPO;
    				MIDI_INFO[i+3] = (byte)(T / 65536);
    				MIDI_INFO[i+4] = (byte)(T % 65536 / 256);
    				MIDI_INFO[i+5] = (byte)(T % 256);
    			//	System.out.println(MIDI_INFO[i+5]);
    			}
    			
    		}
		}//end of for 
	}
	/**�n�u �I��**/
	private void TransToTypeFour (){
		boolean DoubleCH = false;/* �P�_�O�_�����n�D */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**�@�B�־��ഫ�t��k**/
			/** ��ĤQ�y **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	int GT = (int)Math.floor(Math.random()*2 + 28);//24-31���־�
    			int GT2 = (int)Math.floor(Math.random()*7 + 48);//48-55���־�
    		   	/** ���n�D���p **/
    			if ( MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) < 16 ){
    				
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1] = (byte)GT;
    					MIDI_INFO[ i+3 ] = (byte)GT;	
    				}else if (MIDI_INFO[ i+2 ] > 40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    					MIDI_INFO[ i+3 ] = (byte)GT2;
    				}
    				DoubleCH = true;
    			}
    			/** ���n�D���p **/
    			if (DoubleCH == false){
    				/**1.�N�L���־�**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					System.out.println(GT);
    					System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    			
    		}
    		/**�G�B��l�ഫ�t��k ��t�G70-100**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//����TEMPO
					int TEMPO = (int)Math.floor(Math.random()*30+70);
					int T = 60*1000000/TEMPO;
    				MIDI_INFO[i+3] = (byte)(T / 65536);
    				MIDI_INFO[i+4] = (byte)(T % 65536 / 256);
    				MIDI_INFO[i+5] = (byte)(T % 256);
    				System.out.println(MIDI_INFO[i+5]);
    			}
    		}
		}//end of for 	
	}
	/**�j�� **/
	private void TransToTypeFive (){	
		boolean DoubleCH = false;/* �P�_�O�_�����n�D */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**�@�B�־��ഫ�t��k**/
			/** ��ĤQ�y **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	int GT = (int)Math.floor(Math.random()*3 + 7);//7-10���־�
    			int GT2 = (int)Math.floor(Math.random()*15 + 72);//40-79���־�
    		   	/** ���n�D���p **/
    			if ( MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[ i+3 ] - (0xC0-(0xff+1)) < 16 ){
    				
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					MIDI_INFO[ i+3 ] = (byte)GT;	
    				}else if (MIDI_INFO[ i+2 ] > 40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    					MIDI_INFO[ i+3 ] = (byte)GT2;
    				}
    				DoubleCH = true;
    			}
    			/** ���n�D���p **/
    			if (DoubleCH == false){
    				/**1.�N�L���־�**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					System.out.println(GT);
    					System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    			
    		}
    		/**�G�B��l�ഫ�t��k ��t�G110**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//����TEMPO
					int TEMPO = (int)Math.floor(Math.random()*20+130);
					int T = 60*1000000/TEMPO;
    				MIDI_INFO[i+3] = (byte)(T / 65536);
    				MIDI_INFO[i+4] = (byte)(T % 65536 / 256);
    				MIDI_INFO[i+5] = (byte)(T % 256);
    			//	System.out.println(MIDI_INFO[i+5]);
    			}
    			
    		}
		}//end of for 		
	}
	
	public byte[] getMIDIFile(){ return MIDI_INFO;	}
		
	public String getMIDI_STYLE(){
		String STYLE = "";
		if (MIDI_STYLE==0){STYLE = "�籡";} 
		else if (MIDI_STYLE==1) { STYLE ="���֧籡"; }
		else if (MIDI_STYLE==2) { STYLE ="�n�u"; }
		else if (MIDI_STYLE==3) { STYLE ="�I�e�n�u"; }
		else { STYLE ="�j��"; }
		return STYLE;
	}
	/*
	public static void main (String[] args){            
		try {
    		FileInputStream Fi = new FileInputStream("D:\\New.mid"); 
    		FileOutputStream Fo = new FileOutputStream("D:\\TEST00.mid");
    		byte[] test = new byte[Fi.available()];
    		Fi.read(test);
    		
    		TOMArg TOM = new TOMArg(test,3);
    		
    		Fo.write(TOM.getMIDIFile());
    		//System.out.println(TOM.getMIDI_STYLE());
    		Fi.close();
    		Fo.close();
    		
    	}catch(IOException e){
    		System.out.println("�䤣���ɮ�!");
    	}
	}
	*/
}