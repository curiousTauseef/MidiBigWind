package Arg;
import java.io.*;
import java.util.*;
public class TOMArg{
	
	private byte[] data,MIDI_INFO;
	private int MIDI_STYLE;
	
	public TOMArg(byte[]  MIDI_INPUT,int STYLE) {
		MIDI_INFO = MIDI_INPUT;//MIDI陣列
		MIDI_STYLE = STYLE;    //MIDI風格
		
		if (MIDI_STYLE == 1){TransToTypeOne();	
		}else if (MIDI_STYLE == 2){	TransToTypeTwo();
		}else if (MIDI_STYLE == 3){	TransToTypeThree();
		}else if (MIDI_STYLE == 4){ TransToTypeFour();
		}else { TransToTypeFive();	}
		
	}
	/**typeone 抒情悲傷**/
	private void TransToTypeOne (){
		boolean DoubleCH = false;/* 判斷是否為單聲道 */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**一、樂器轉換演算法**/
			/** 抓第十軌 **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){
					
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    			
    			int tem = (int)(Math.random()*100000 % 50);
    			int GT;
    			int GT2;
    			if (tem < 24){
    				GT = (int)Math.floor(Math.random()*15 + 8);//8-23的樂器
    				GT2 = (int)Math.floor(Math.random()*15 + 72);//72-87的樂器
    			}else{
    				GT = (int)Math.floor(Math.random()*1 + 24);//1-25的樂器
    				GT2 = (int)Math.floor(Math.random()*15 + 72);//72-87的樂器
    			}
    		   	/** 雙聲道情況 **/
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
    			/** 單聲道情況 **/
    			if (DoubleCH == false){
    				/**1.吉他類樂器**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					//System.out.println(GT);
    					//System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    		}
    		/**二、拍子轉換演算法 拍速：80~100 之間**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			//產生TEMPO
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
	/**typetwo 抒情輕快**/
	private void TransToTypeTwo (){
		boolean DoubleCH = false;/* 判斷是否為單聲道 */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**一、樂器轉換演算法**/
			/** 抓第十軌 **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	
    		   	int GT = (int)Math.floor(Math.random()*7);//0-7的樂器
    			int GT2 = (int)Math.floor(Math.random()*15 + 72);//72-87的樂器
    			
    		   	/** 雙聲道情況 **/
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
    			/** 單聲道情況 **/
    			if (DoubleCH == false){
    				/**1.吉他類樂器**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					//System.out.println(GT);
    					//System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    		}
    		/**二、拍子轉換演算法 拍速：110-130間**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//產生TEMPO
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
	/**typethree 搖滾high**/
	private void TransToTypeThree (){
		boolean DoubleCH = false;/* 判斷是否為單聲道 */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**一、樂器轉換演算法**/
			/** 抓第十軌 **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	
    		   	int GT = (int)Math.floor(Math.random()*2 + 28);//24-31的樂器
    			int GT2 = (int)Math.floor(Math.random()*7 + 48);//48-55的樂器
    			
    		   	/** 雙聲道情況 **/
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
    			/** 單聲道情況 **/
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
    		
    		/**二、拍子轉換演算法 拍速：130-150間**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//產生TEMPO
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
	/**搖滾 沉重**/
	private void TransToTypeFour (){
		boolean DoubleCH = false;/* 判斷是否為單聲道 */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**一、樂器轉換演算法**/
			/** 抓第十軌 **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	int GT = (int)Math.floor(Math.random()*2 + 28);//24-31的樂器
    			int GT2 = (int)Math.floor(Math.random()*7 + 48);//48-55的樂器
    		   	/** 雙聲道情況 **/
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
    			/** 單聲道情況 **/
    			if (DoubleCH == false){
    				/**1.吉他類樂器**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					System.out.println(GT);
    					System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    			
    		}
    		/**二、拍子轉換演算法 拍速：70-100**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//產生TEMPO
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
	/**古典 **/
	private void TransToTypeFive (){	
		boolean DoubleCH = false;/* 判斷是否為單聲道 */
		for ( int i = 0;i < MIDI_INFO.length;i ++ ){
			/**一、樂器轉換演算法**/
			/** 抓第十軌 **/
			if ( MIDI_INFO[i] == 0xC9-(0xff+1)){	
				
    		}else if ( MIDI_INFO[i] - (0xC0-(0xff+1)) >= 0 && MIDI_INFO[i] - (0xC0-(0xff+1)) < 16 )/*C0*/{
    		   	int GT = (int)Math.floor(Math.random()*3 + 7);//7-10的樂器
    			int GT2 = (int)Math.floor(Math.random()*15 + 72);//40-79的樂器
    		   	/** 雙聲道情況 **/
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
    			/** 單聲道情況 **/
    			if (DoubleCH == false){
    				/**1.吉他類樂器**/
    				if (MIDI_INFO[ i+1 ] < 31){
    					MIDI_INFO[ i+1 ] = (byte)GT;
    					System.out.println(GT);
    					System.out.println(MIDI_INFO[ i+1 ]);
    				}else if (MIDI_INFO[ i+1 ] >40){
    					MIDI_INFO[ i+1 ] = (byte)GT2;
    				}
    			}
    			
    		}
    		/**二、拍子轉換演算法 拍速：110**/
    		if ( MIDI_INFO[i] == 0xff-( 0xff+1 ) ){
    			if( MIDI_INFO[i+1] == 0x51 && MIDI_INFO[i+2] == 0x03 ){
    				//產生TEMPO
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
		if (MIDI_STYLE==0){STYLE = "抒情";} 
		else if (MIDI_STYLE==1) { STYLE ="輕快抒情"; }
		else if (MIDI_STYLE==2) { STYLE ="搖滾"; }
		else if (MIDI_STYLE==3) { STYLE ="沉悶搖滾"; }
		else { STYLE ="古典"; }
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
    		System.out.println("找不到檔案!");
    	}
	}
	*/
}