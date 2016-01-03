package Arg.Score;

public class midiScore{
	private int NUM_TRACK;
	private int NUM_TEMPO;
	private int[] DATA_ARRAY;
	private int TK_LV,TM_LV,INS_LV,LV;
	
	public midiScore(int[] array,int track,int tempo){
		NUM_TRACK = track;
		NUM_TEMPO = tempo;
		DATA_ARRAY = array;
		process();
	}
	private void process(){
		TK_LV = 0;
		TM_LV = 0;
		INS_LV = 0;
		
		if (NUM_TRACK > 6 ){
			TK_LV = 3;
		}else if(NUM_TRACK < 1){
			TK_LV = 1;
		}else{
			TK_LV = 2;
		}
		
		if (NUM_TEMPO >120){
			TM_LV = 3;
		}else if (NUM_TEMPO < 80){
			TM_LV = 1;
		}else{
			TM_LV = 2;
		}
		
		int ins_lv = 0;
		for (int i = 0 ; i < DATA_ARRAY.length;i++){
			if (DATA_ARRAY[i] < 40){
				ins_lv += 1;
			}else if (DATA_ARRAY[i] > 79){
				ins_lv += 3;
			}else {
				ins_lv += 2;
			}
		}
		INS_LV = ins_lv / DATA_ARRAY.length;
		LV = (TK_LV + TM_LV + INS_LV) / 3;
	}
	
	public String toString(){
		String OUTPUT = "" ;
		String[] script = {"低" , "中" , "高"};
		String[] level ={"C級","B級","A級"};
		OUTPUT = "歌曲難易度：\t" + script[LV-1] + "\n樂器平均難易度：\t偏" + script[INS_LV-1] + "\n拍速：\t\t偏" + script[TM_LV-1] + "\n評價：\t\t" + level[LV-1];		
		return OUTPUT;
	}
}

/******************************************************************
 ******************************************************************
 							評
 ------------------------------------------------------------------
 等級　：偏高、適中、偏低{1級 2級 3級}
 音軌數：越多　難度越高{4~6 個MTrk 標準}
 			>6 = 3級
 			4~6= 2級
 			<4 = 1級
 拍數：越快 難度越高{80~120 是標準}
 			>120 	= 3級
 			80~120	= 2級
 			<80 	= 1級
 樂器種類：
 		常見的樂器都比較簡單：1級
 		古典樂器、中國樂器類：2級
 		合成聲音類			：3級
 
 規則：
 	一個樂器聲音的表現，以一個人單位，五人樂團人力為適中，超過則困難，低於五人則是簡單
 	而常見的樂器例如吉他、鋼器，都是比較簡單，而古典樂器則為適中，其他像合成人聲或飛機
 	聲，要以人力表現出來則非常困難。
 	在拍速方面，80~120之間的拍速比較簡單，超過120的拍速會比較難		
 
 
 
 
 
 
 
 
 
 
 ******************************************************************
 ******************************************************************/