//用ArrayList 實作 midi創作
package Arg;
import java.util.*;
import java.io.*;

public class CoMtestONE{
	
	final byte[][] KeyTable = {
		{ 48,50,52,53,55,57,59,71,72,74,76,77,79,81,73,74 },
		{ 49,51,53,54,56,67,60,72 },
		{ 50,52,54,55,57,68,61,73 },
		{ 51,53,55,56,58,69,62,74 },
		{ 52,54,56,57,59,70,63,75 },
		{ 53,55,57,58,60,71,64,76 },
		{ 54,56,58,59,61,72,65,77 },
		{ 55,57,59,60,62,73,66,78 },
		{ 56,58,60,61,63,74,67,79 },
		{ 57,59,61,62,64,75,68,80 },
		{ 58,60,62,63,65,76,69,81 },
		{ 59,61,63,64,66,77,70,82 },};
	final byte[][] BluesKeyTable = {
		{ 48,50,51,52,55,57,70 },
		{ 49,51,53,54,56,67,60,72 },
		{ 50,52,54,55,57,68,61,73 },
		{ 51,53,55,56,58,69,62,74 },
		{ 52,54,56,57,59,70,63,75 },
		{ 53,55,57,58,60,71,64,76 },
		{ 54,56,58,59,61,72,65,77 },
		{ 55,57,59,60,62,73,66,78 },
		{ 56,58,60,61,63,74,67,79 },
		{ 57,59,61,62,64,75,68,80 },
		{ 58,60,62,63,65,76,69,81 },
		{ 59,61,63,64,66,77,70,82 },};
		
	final Chord[][] ChordTable = {
		{new Chord(48,52,55),new Chord(50,53,57),new Chord(52,55,59),new Chord(53,57,71),new Chord(55,59,72),new Chord(57,71,74),new Chord(59,72,76)}
	//	{new Chord(49,53,56),new Chord(51,54,58),new Chord(53,56,60),new Chord(54,58,72),new Chord(56,60,73),new Chord(58,72,75),new Chord(60,73,77)}
	//	{new Chord(50,54,57),new Chord(52,55,59),new Chord(54,57,61),new Chord(55,59,73),new Chord(57,61,74),new Chord(59,73,76),new Chord(61,74,78)}
	//	{new Chord(51,55,58),new Chord(),new Chord(),new Chord(),new Chord(),new Chord(),new Chord()}
	//	{new Chord(52,56,59),new Chord(),new Chord(),new Chord(),new Chord(),new Chord(),new Chord()}}
	};
		
	byte[] ins,midi;
	int[] MUSICFORM;
	int tracks,tempo,nn,dd,style,key,bar;
	ArrayList<Byte> list = new ArrayList<Byte>();
	ArrayList<Byte> list2 = new ArrayList<Byte>();
	//FileOutputStream out = new FileOutputStream("D:\\TEST.mid");
	
	
	public CoMtestONE(int postStyle,int tempo,int InputKey,int BAR) throws IOException{
		//[[[[此部分做參數設定，作曲]]]]
		key = InputKey;
		style = postStyle;
		bar = BAR;
		//判別曲風，取的音軌數，再以音軌數取得一組樂器
		tracks = getTracks(style);
		ins = getIns(style);
		//產生曲式
		setMusicForm(bar,style);
		
		//[[[[編曲部分]]]]
		list = addMDhd(list,tracks);//第二個參數是使用者要的音軌
		list = addMDrk(list,tracks,tempo,4,4,ins);
		
		//轉成陣列
		midi = SetToArrays(list);
		//做 MTrk長度計算 以及修改
		setArrLength(midi);
		//檔案輸出
		//outFile(midi);
		
		//System.out.println(midi.length);	
	}
	
	//曲式產生器
	private void setMusicForm(int bar,int style){
		MUSICFORM = new int[bar];
		
		if (style == 0 || style == 3){//搖滾Rock及鄉村Country風格的曲式
			for (int i = 0;i < MUSICFORM.length;i ++){
				if (i % 4 == 0)
					MUSICFORM[i] = 1;
				else if (i % 4 == 3)
					MUSICFORM[i] = 5;
				else
					MUSICFORM[i] = (int)Math.floor(Math.random()*5+1);//1-6
				
			//System.out.println(MUSICFORM[i]);	
			}
		}
		if (style == 1){//抒情POP Soft風格的曲式
			for (int i = 0;i < MUSICFORM.length;i ++){
				if (i % 8 == 0) 
					MUSICFORM[i] = 1;
				else if (i % 8 == 4)
					MUSICFORM[i] = 4;
				else if (i % 8 == 7)
					MUSICFORM[i] = 7;
				else 
					MUSICFORM[i] = (int)Math.floor(Math.random()*5+1); // 1-6
			}
		}
		if (style == 2){//藍調Blues 風格的曲式
			for (int i = 0;i < MUSICFORM.length;i ++){
				if (i % 12 == 4 || i % 12 == 5 || i % 12 == 9){
					MUSICFORM[i] = 4;
				}else if (i % 12 == 8){
					MUSICFORM[i] = 5;
				}else {
					MUSICFORM[i] = 1;
				}
				//System.out.println(MUSICFORM[i]);
			}
		}
	}
	//依風格取得一組樂器
	private byte[] getIns(int style){
		
		ArrayList<Byte> insList = new ArrayList<Byte>();
		byte[] ins;
		//System.out.println(tracks);
		if (style == 0){//風格： 搖滾ROCK
			
			for (int i = 0 ; i < tracks ; i ++){
				if (i == 0){//主弦律
					insList.add((byte)Math.floor(Math.random()*2+28));
				}else if (i == 1){//Bass
					insList.add((byte)Math.floor(Math.random()*3+32));
				}else if (i == 2){//鼓
					insList.add((byte)0x01);
				}else if (i == 3){//吉他
					insList.add((byte)Math.floor(Math.random()*2+28));
				}else{//配樂 任意樂器
					insList.add((byte)Math.floor(Math.random()*111));
				}	
			}		
		}
		if (style == 1){//風格：抒情 POP Soft
		
			byte INSplay[] = {0,1,2,3,4,5,6,7,24,25,26,27,28,29,30,31};//POP的伴奏樂器陣列
			for (int i = 0 ; i < tracks ; i ++){
				if (i == 0){//主弦律
					insList.add((byte)Math.floor(Math.random()*31));
				}else if (i == 1){//伴奏樂器1
					insList.add(INSplay[(byte)Math.floor(Math.random()*INSplay.length)]);
				}else{//配樂 任意樂器
					insList.add((byte)Math.floor(Math.random()*111));
				}	
			}
			
		}
		if (style == 2){//風格：藍調 Bules
		
			byte INSplay_solo[] = {24,25,26,27,28,29,30,31};//主弦律樂器
			for (int i = 0 ; i < tracks ; i ++){
				if (i == 0){//主弦律
					insList.add(INSplay_solo[(byte)Math.floor(Math.random()*INSplay_solo.length)]);
				}else if (i == 1){//Bass
					insList.add((byte)Math.floor(Math.random()*3+32));
				}else if (i == 2){//鼓
					insList.add((byte)0x01);
				}/*se{
					insList.add((byte)Math.floor(Math.random()*7+16));
				}	*/
			}
		}
		if (style == 3){//風格：鄉村 Country
			
			byte INSplay[] = {24,25,27};
			for (int i = 0 ; i < tracks ; i ++){
				if (i == 0){//主弦律
					insList.add((byte)Math.floor(Math.random()*31));
				}else if (i == 1){//伴奏樂器1
					insList.add(INSplay[(byte)Math.floor(Math.random()*INSplay.length)]);
				}else if (i == 2){//Bass樂器
					insList.add((byte)Math.floor(Math.random()*3+32));
				}else{//鼓組
					insList.add((byte)0x01);
				}	
			}
		}
		ins = SetToArrays(insList);//把動態List轉成Array
		return ins;
	}
	//判定風格 取得track數
	private int getTracks(int style){
		
		int tracks = 0;
		if (style == 0) tracks = 4; //暫時設定風格一為6軌
		if (style == 1) tracks = 3;
		if (style == 2) tracks = 3;
		if (style == 3) tracks = 3;
		return tracks;
		
	}
	ArrayList<Byte> addMDhd(ArrayList<Byte> list,int tracks){
		
		list.add((byte)0x4D);list.add((byte)0x54);list.add((byte)0x68);list.add((byte)0x64);
		for (int i = 0 ;i < 3;i++)
			list.add((byte)0x00);
		list.add((byte)0x06);
		list.add((byte)0x00);list.add((byte)0x01);
		list.add((byte)0x00);list.add((byte)(tracks+1));//加上使用著要的音軌
		//**
		//**缺Division部分 總共16bits
		list.add((byte)0x01);list.add((byte)0xE0);
		return list;
		
	}
	ArrayList<Byte> addMDrk(ArrayList<Byte> list,int tracks,int tempo,int nn,int dd,byte[] ins){
		//做整首MID的初始化
		list = setDef(list,tempo,nn,dd);
		
		//開始做整首歌的編曲
		//搖滾曲風：
		//有 6組音軌
		if (style == 0){
			for (int i = 0;i< tracks ;i++){//trcks
				
				if (i == 0){
					list = settrack(list,(byte)i,ins[i]);//弦律
				}else if (i == 1){
					list = Bass(list,(byte)i,ins[i],style);//bass 32-39
				}else if (i == 2){
					list = Drum(list,(byte)0x09,ins[i],style);//Drum 01
				}else if (i == 3){
					list = GT(list,(byte)i,ins[i],style);//G1
				}
			}
		}
		if (style == 1){
			for (int i = 0;i< tracks ;i++){//trcks
				
				if (i == 0){
					list = settrack(list,(byte)i,ins[i]);//弦律
				}else if (i == 1){
					list = Bass(list,(byte)i,ins[i],style);//bass 32-39
				}else if (i == 2){
					list = Drum(list,(byte)0x09,ins[i],style);//Drum 01
				}
			}
		}
		if (style == 2){
			for (int i = 0;i< tracks ;i++){//trcks
				if (i == 0){
					list = settrack(list,(byte)i,ins[i]);//弦律
				}else if (i == 1){
					list = Bass(list,(byte)i,ins[i],style);//bass 32-39
				}else if (i == 2){
					list = Drum(list,(byte)0x09,ins[i],style);//Drum 01
				}else if (i == 3){
					list = GT(list,(byte)i,ins[i],style);//G1
				}
			}
		}
		if (style == 3){
			for (int i = 0;i< tracks ;i++){//trcks
				
				if (i == 0){
					list = settrack(list,(byte)i,ins[i]);//弦律
				}else if (i == 1){
					list = Bass(list,(byte)i,ins[i],style);//bass 32-39
				}else if (i == 2){
					list = Drum(list,(byte)0x09,ins[i],style);//Drum 01
				}/*else if (i == 3){
					list = GT(list,(byte)i,ins[i],style);//G1
				}*/
				
			}
		}
		return list;
	}
	///樂器編曲方法
	private ArrayList<Byte> Bass (ArrayList<Byte> list,byte trkNumber,byte ins,int style){
		
		list.add((byte)0x4D);list.add((byte)0x54);list.add((byte)0x72);list.add((byte)0x6B);
		for (int i = 0 ;i < 4;i++)	list.add((byte)0x00);//MTrk長度預設為0
		//調整音量 bass預設音量偏底
		list.add((byte)0x00);
		list.add((byte)(0xB0 + trkNumber));list.add((byte)0x07);list.add((byte)0x40);
		//樂器
		list.add((byte)0x00);//00
		list.add((byte)(0xC0 + trkNumber));list.add((byte)ins);
		
		list.add((byte)0x00);//00

		if (style == 0 || style == 1 || style ==3){
					 	
			for (int i = 0 ; i<bar ; i ++){ //小節迴圈
				
				Notes[] NotesArr = getNotesSet(1);
				int ArrSum = 0;
				for (int j = 0; j <NotesArr.length; j++ ) if (NotesArr[j] != null) ArrSum = ArrSum + 1; //抓出實際上 NotesArr的大小
				byte tone = KeyTable[key][MUSICFORM[i]-1];
				if (tone >= 52 ) tone = (byte)(tone - 12);
			
				for (int x = 0; x < ArrSum ; x ++){ // 製作弦律部分
					
					list.add((byte)(0x90 + trkNumber));
					list.add((byte)(tone));//暫時是c調的弦律
					list.add((byte)0x5f);				
					list.addAll(NotesArr[x].getList());// 
					list.add((byte)(0x80 + trkNumber));
					list.add((byte)(tone));
					list.add((byte)0x50);
					list.add((byte)0x00);
				}
			}
			
		}else if (style == 2){//Blues Bass:需要修改
			
			Notes BluesNotes = new Notes((byte)0x81,(byte)0x70);
			//Blues Bass 生成
			for (int i = 0 ; i< bar ; i ++){ //小節迴圈
					
				byte tone = KeyTable[key][MUSICFORM[i]-1];
				if (tone >= 52 ) tone = (byte)(tone - 12);
				//Blues Bass音階存入陣列
				byte[] BluesTone = new byte[8];
				int index = 0;
				for (int j = 4;j > (0-4) ; j --){
					int scale = j;
					if ( scale <= 0) scale = scale - 1;
					
					BluesTone[index] = tone;
					tone = (byte)( tone + scale);
					//System.out.println(BluesTone[index]);
					index += 1;	
				}
				
				for (int x = 0; x < BluesTone.length ; x ++){ // 製作弦律部分
					
					list.add((byte)(0x90 + trkNumber));
					list.add(BluesTone[x]);//暫時是c調的弦律
					list.add((byte)0x5f);				
					list.addAll(BluesNotes.getList());// 
					list.add((byte)(0x80 + trkNumber));
					list.add(BluesTone[x]);
					list.add((byte)0x50);
					list.add((byte)0x00);
				}
			}
			
		}
		list.add((byte)0xff);list.add((byte)0x2f);list.add((byte)0x00);
		return list;
	}
	
	private ArrayList<Byte> Drum (ArrayList<Byte> list,byte trkNumber,byte ins,int style){
		
		list.add((byte)0x4D);list.add((byte)0x54);list.add((byte)0x72);list.add((byte)0x6B);
		for (int i = 0 ;i < 4;i++)	list.add((byte)0x00);//MTrk長度預設為0
		list.add((byte)0x00);//00
		list.add((byte)(0xC0 + trkNumber));list.add((byte)ins);
		list.add((byte)0x00);//00
		
		Notes NotesArr = new Notes((byte)0x81,(byte)0x70);
		Notes BluesNotes = new Notes((byte)0x81,(byte)0x20);
		
		if (style == 0 || style ==3){
			for (int i = 0 ; i<bar ; i ++){ //小節迴圈
				for (int j = 0 ; j < 8 ;j ++){ //每一小節的設定
					if (j == 0 || j == 5){//大鼓
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);list.add((byte)0x5f);list.add((byte)0x00);
						list.add((byte)35);list.add((byte)0x5f);				
						list.addAll( NotesArr.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);list.add((byte)0x50);list.add((byte)0x00);
						list.add((byte)35);list.add((byte)0x55);
						list.add((byte)0x00);
					}else if (j == 2 || j == 6){//小鼓
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);list.add((byte)0x5f);list.add((byte)0x00);
						list.add((byte)40);list.add((byte)0x5f);				
						list.addAll( NotesArr.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);list.add((byte)0x50);list.add((byte)0x00);
						list.add((byte)40);list.add((byte)0x50);
						list.add((byte)0x00);
					}else { //high hit
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);
						list.add((byte)0x5f);				
						list.addAll( NotesArr.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);
						list.add((byte)0x50);
						list.add((byte)0x00);
					}	
				}
			}
		}
		if (style == 1){
			for (int i = 0 ; i<bar ; i ++){ //小節迴圈
				for (int j = 0 ; j < 8 ;j ++){ //每一小節的設定
					if (j == 0 || j == 5){//大鼓
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);list.add((byte)0x5f);list.add((byte)0x00);
						list.add((byte)35);list.add((byte)0x5f);				
						list.addAll( NotesArr.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);list.add((byte)0x50);list.add((byte)0x00);
						list.add((byte)35);list.add((byte)0x55);
						list.add((byte)0x00);
					}else if (j == 2 || j == 6){//小鼓
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);list.add((byte)0x5f);list.add((byte)0x00);
						list.add((byte)40);list.add((byte)0x5f);				
						list.addAll( NotesArr.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);list.add((byte)0x50);list.add((byte)0x00);
						list.add((byte)40);list.add((byte)0x50);
						list.add((byte)0x00);
					}else { //high hit
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);
						list.add((byte)0x5f);				
						list.addAll( NotesArr.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);
						list.add((byte)0x50);
						list.add((byte)0x00);
					}	
				}
			}
		}
		if (style == 2){
			for (int i = 0 ; i<bar ; i ++){ //小節迴圈
				for (int j = 0 ; j < 12 ;j ++){ //每一小節的設定
					if (j == 0 || j == 5 || j == 5){//大鼓
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);list.add((byte)0x5f);list.add((byte)0x00);
						list.add((byte)35);list.add((byte)0x5f);				
						list.addAll( BluesNotes.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);list.add((byte)0x50);list.add((byte)0x00);
						list.add((byte)35);list.add((byte)0x55);
						list.add((byte)0x00);
					}else if (j == 2 || j == 3 || j == 9){//小鼓
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);list.add((byte)0x5f);list.add((byte)0x00);
						list.add((byte)40);list.add((byte)0x5f);				
						list.addAll( BluesNotes.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);list.add((byte)0x50);list.add((byte)0x00);
						list.add((byte)40);list.add((byte)0x50);
						list.add((byte)0x00);
					}else { //high hit
						list.add((byte)(0x90 + trkNumber));
						list.add((byte)42);
						list.add((byte)0x5f);				
						list.addAll( BluesNotes.getList() );
						list.add((byte)(0x80 + trkNumber));
						list.add((byte)42);
						list.add((byte)0x50);
						list.add((byte)0x00);
					}	
				}
			}
		}
		list.add((byte)0xff);list.add((byte)0x2f);list.add((byte)0x00);//結束記號
		return list;
	}
	
	private ArrayList<Byte> GT(ArrayList<Byte> list,byte trkNumber,byte ins,int style){
		
		list.add((byte)0x4D);list.add((byte)0x54);list.add((byte)0x72);list.add((byte)0x6B);
		for (int i = 0 ;i < 4;i++)	list.add((byte)0x00);//MTrk長度預設為0
		list.add((byte)0x00);//00
		list.add((byte)(0xC0 + trkNumber));list.add((byte)ins);
		list.add((byte)0x00);//00
		
		if (style == 0){//搖滾曲風 power chord
		
			Notes BluesNotes = new Notes((byte)0x81,(byte)0x70);
			for (int i = 0 ; i<bar ; i ++){ //小節迴圈
				//Power Chord Style
				byte tone = KeyTable[key][MUSICFORM[i]-1];
				byte tone5 = (byte)( tone + 7); 
				
				for (int x = 0; x < 8 ; x ++){ // 製作弦律部分
					
					list.add((byte)(0x90 + trkNumber));
					list.add(tone);list.add((byte)0x5f);list.add((byte)0x00);
					list.add(tone5);list.add((byte)0x5f);				
					list.addAll(BluesNotes.getList());// 
					list.add((byte)(0x80 + trkNumber));
					list.add(tone);list.add((byte)0x50);list.add((byte)0x00);
					list.add(tone5);list.add((byte)0x50);
					list.add((byte)0x00);
				}
			}
		}
		if (style == 1){
			Notes[] NotesArr = getNotesSet(2);//GT 伴奏
			
			for (int i =0 ; i < bar ; i ++){
				
			}
			
		}
		if (style == 2){//Blues Style Chord
			
			Notes BluesNotes = new Notes((byte)0x83,(byte)0x60);
			Notes BluesNotes2 = new Notes((byte)0x81,(byte)0x20);
			for (int i = 0 ; i< bar ; i ++){ //小節迴圈
				//Power Chord Style
				byte tone = KeyTable[key][MUSICFORM[i]-1];
				byte tone3 = (byte)( tone + 4);
				byte tone5 = (byte)( tone + 3);
				byte tone7b = (byte)( tone + 3);
				
				for (int x = 0; x < 4 ; x ++){ // 製作弦律部分
					if (x == 1){
						for (int y = 0 ; y < 3 ;y ++){
							
							list.add((byte)(0x90 + trkNumber));
							list.add(tone);list.add((byte)0x5f);list.add((byte)0x00);
							list.add(tone3);list.add((byte)0x5f);/*list.add((byte)0x00);
							list.add(tone5);list.add((byte)0x5f);list.add((byte)0x00);
							list.add(tone7b);list.add((byte)0x5f);*/	
							list.addAll(BluesNotes2.getList());//
							list.add((byte)(0x80 + trkNumber));
							list.add(tone);list.add((byte)0x50);list.add((byte)0x00);
							list.add(tone3);list.add((byte)0x50);/*list.add((byte)0x00);	
							list.add(tone5);list.add((byte)0x50);list.add((byte)0x00);
							list.add(tone7b);list.add((byte)0x50);*/
							list.add((byte)0x00);
						}
						
					}else{
						list.add((byte)(0x90 + trkNumber));
						list.add(tone);list.add((byte)0x5f);list.add((byte)0x00);
						list.add(tone3);list.add((byte)0x5f);list.add((byte)0x00);
						list.add(tone5);list.add((byte)0x5f);list.add((byte)0x00);
						list.add(tone7b);list.add((byte)0x5f);	
						list.addAll(BluesNotes.getList());// 
						list.add((byte)(0x80 + trkNumber));
						list.add(tone);list.add((byte)0x50);list.add((byte)0x00);
						list.add(tone3);list.add((byte)0x50);list.add((byte)0x00);
						list.add(tone5);list.add((byte)0x50);list.add((byte)0x00);
						list.add(tone7b);list.add((byte)0x50);
						list.add((byte)0x00);
					}
					
				}
			}
		}		
		if (style == 3){
			
		}
		list.add((byte)0xff);list.add((byte)0x2f);list.add((byte)0x00);//結束記號
		return list;
	}
	
	private ArrayList<Byte> setDef(ArrayList<Byte> list,int tempo,int nn,int dd){
		
		list.add((byte)0x4D);list.add((byte)0x54);list.add((byte)0x72);list.add((byte)0x6B);//MTrk
		for (int i = 0 ;i < 3;i++)	list.add((byte)0x00);
		list.add((byte)0x17);
		
		list.add((byte)0x00);//meta 00
		list.add((byte)0xff);list.add((byte)0x51);list.add((byte)0x03);
		int T = 60*1000000/tempo;//設定拍子
		list.add((byte)(T/65536));list.add((byte)(T%65536/256));list.add((byte)(T%256)); 
		
		list.add((byte)0x00);//meta 00 
		list.add((byte)0xff);list.add((byte)0x03);
		list.add((byte)0x00);//內容00
		
		list.add((byte)0x00);//meta 00 + 拍子記號
		list.add((byte)0xff);list.add((byte)0x58);list.add((byte)0x04);
		list.add((byte)nn);list.add((byte)(dd/2));//此位元有問題
		list.add((byte)(T/65536));list.add((byte)(T%65536/256));
		
		list.add((byte)0x00);//meta 00 + 結束事件
		list.add((byte)0xff);list.add((byte)0x2f);list.add((byte)0x00);
		return list;
		
	}
	
	private ArrayList<Byte> settrack(ArrayList<Byte> list,byte trkNumber,byte ins){
		
		list.add((byte)0x4D);list.add((byte)0x54);list.add((byte)0x72);list.add((byte)0x6B);
		for (int i = 0 ;i < 4;i++)	list.add((byte)0x00);//MTrk長度預設為0
		
		list.add((byte)0x00);//00
		list.add((byte)(0xC0 + trkNumber));list.add((byte)ins);
		
		list.add((byte)0x00);//00
		/*隨機取音演算法部分*/
		if (style == 2){
			for (int i = 0 ; i < bar; i ++){
				Notes[] NotesArr = getNotesSet(0);
				int ArrSum = 0;
			
				for (int j = 0; j <NotesArr.length; j++ ) if (NotesArr[j] != null) ArrSum = ArrSum + 1; //抓出實際上 NotesArr的大小
		 	
				for (int x = 0; x < ArrSum ; x ++){ // 製作弦律部分
			
					byte temp = addMelody(BluesKeyTable[key],MUSICFORM[i]);
					list.add((byte)(0x90 + trkNumber));
					list.add(temp);//暫時是c調的弦律
					list.add((byte)0x5f);				
					list.addAll(NotesArr[x].getList());// 
					list.add((byte)(0x80 + trkNumber));
					list.add(temp);
					list.add((byte)0x50);
					list.add((byte)0x00);
				}
			}
		}else{
			for (int i = 0 ; i<bar ; i ++){ //
				Notes[] NotesArr = getNotesSet(0);
				int ArrSum = 0;
			
				for (int j = 0; j <NotesArr.length; j++ ) if (NotesArr[j] != null) ArrSum = ArrSum + 1; //抓出實際上 NotesArr的大小
		 	
				for (int x = 0; x < ArrSum ; x ++){ // 製作弦律部分
			
					byte temp = (byte)(addMelody(KeyTable[key],MUSICFORM[i]) + 12);
					list.add((byte)(0x90 + trkNumber));
					list.add(temp);//暫時是c調的弦律
					list.add((byte)0x5f);				
					list.addAll(NotesArr[x].getList());// 
					list.add((byte)(0x80 + trkNumber));
					list.add(temp);
					list.add((byte)0x50);
					list.add((byte)0x00);
				}
			}
		}
		list.add((byte)0xff);list.add((byte)0x2f);list.add((byte)0x00);//結束記號
		return list;
		
	}
	
	//隨機取音
	private byte addMelody(byte[] InputKeyTable,int FORM){ //隨機取音
		
		return InputKeyTable[ (int)(Math.random()*70%7) ] ;
	}
	//取得一小節的音符
	private Notes[] getNotesSet(int WhichINS){
		
		double value = 1.0; //一個小節最大容納音符數
		Notes[] NotesArr = new Notes[16];
		int i = 0;
		while (value != 0.0){ //當value不等於零的時候 繼續做
			 int getNotes = 0;
			 if (WhichINS == 0)	getNotes = (int)(Math.random()*500%5); //隨機取得音符
			 if (WhichINS == 1) getNotes = (int)(Math.random()*400%4); //bass
			 if (WhichINS == 2) getNotes = (int)(Math.random()*300%3+2); //GT 2,3,4
			 
			 if (getNotes == 0 && value == 1){
			 	value -= 1.0;
			 	NotesArr[i] = new Notes((byte)0x8f,(byte)0x00);
			 	i = i + 1;
			 	//System.out.println(i);
			 }
			 if (getNotes == 1 && value >= 0.5){
			 	value -= 0.5;
			 	NotesArr[i] = new Notes((byte)0x87,(byte)0x40);
			 	i = i + 1;
			  	//System.out.println(i);
			 }
			 if (getNotes == 2 && value >= 0.25){
			 	value -= 0.25;
			 	NotesArr[i] = new Notes((byte)0x83,(byte)0x60);
			 	i = i + 1;
			 	//System.out.println(i);
			 }
			 if (getNotes == 3 && value >= 0.125){
			 	value -= 0.125;
			 	NotesArr[i] = new Notes((byte)0x81,(byte)0x70);
			 	i = i + 1;
			 	//System.out.println(i);
			 }
			 if (getNotes == 4 && value >= 0.0625){
			 	value -= 0.0625;
			 	NotesArr[i] = new Notes((byte)0x78);
			 	i = i + 1;
			 	//System.out.println(i);
			 }
			 /*
			 if (getNotes == 5 && value >= 0.03125){
			 	value -= 0.03125;
			 	NotesArr[i] = new Notes((byte)0x3C);
			 	i = i + 1;
			 	//System.out.println(i);
			 }*/
			 
		}
		//System.out.println(NotesArr[31]);
		//System.out.println(value);
		return NotesArr;
	}
	
	/**設定MTrk長度的方法**/
 	void setArrLength(byte[] midi){
		int SumBytes = 0;
		int index = 0;
		//boolean firstTrk = true; 
		for (int i = 0;i < midi.length; i ++){
			if (midi[i] == (byte)0x4D){
				if (midi[i+1] == (byte)0x54 && midi[i+2] == (byte)0x72 && midi[i+3] == (byte)0x6B){
					
					index = i + 8;
					SumBytes = getSum(index,midi);
					midi[i+4] = (byte)(SumBytes/16777216);
					midi[i+5] = (byte)(SumBytes%16777216/65536);
					midi[i+6] = (byte)(SumBytes%65536/256);
					midi[i+7] = (byte)(SumBytes%256);
					//(T/65536)(T%65536/256)(T%256)
				}//if (midi[i+1] == (byte)0x54 && midi[i+2] == (byte)0x72 && midi[i+3] == (byte)0x6B && firstTrk == true){
					//firstTrk = false;	
				//	
			}
			SumBytes = 0;
			index = 0;
		}
	}
	private int getSum(int index,byte[] midi){
		int SumBytes = 0;
		while (SumBytes <= midi.length){
			if (midi[index] ==(byte)0xFF && midi[index+1] ==(byte)0x2F){
				break;
			}
			SumBytes += 1;
			index += 1;
		}
		/*while (midi[index] != (byte)0xFF){
			SumBytes = SumBytes + 1;
			index = index + 1; 
		}*/
		SumBytes = SumBytes + 3;
		return SumBytes;
	}
	//取得list 元素的Index
	int getIndex(ArrayList<Byte> list){
		
		int index = 0;
		Iterator<Byte> Walker = list.iterator();
		while (Walker.hasNext()){
			index = index + 1;
			Walker.next();
		}
		return index;
	}
	//輸出檔案
	void outFile(byte[] midi) throws IOException{
		//out.write(midi);
		//out.close();
	}
	//轉成陣列
	byte[] SetToArrays( ArrayList<Byte> list){
		
    	Iterator< Byte > Walker = list.iterator(); //建立一個走訪者  	
    	int ArraySize = list.size();					//陣列需要的大小
    	byte[] newlist = new byte[ArraySize];				//建立陣列
    	/** Set轉換成Array **/
    	for (int i = 0; i < newlist.length; i ++){	
			newlist[i] = Walker.next();		
    	}		
    	return newlist;									//回傳陣列
    	
    }
    public byte[] getMidi(){   	return midi;    }
    public int[] getINS(){
    	int[] intINS = new int[ins.length];
    	for (int i =0;i < ins.length ; i ++){
    		intINS[i] = (int)ins[i];
    	}
    	return intINS;
    }
    /*
	public static void main(String[] args){
		
		try{
			CoMtestONE app = new CoMtestONE(3,120,2,72);//風格、拍速、曲調、小節數
			
		}catch(IOException e){
			System.out.println("No!");
		}
		
	}*/
}
//音符類別
class Notes{
	ArrayList<Byte> list = new ArrayList<Byte>();
	
	public Notes(byte N1,byte N2){
		list.add(N1);
		list.add(N2);
	}
	public Notes(byte N1){
		list.add(N1);
	}
	
	ArrayList<Byte> getList(){
		
		return list;
	}
}
//和弦類別
class Chord{
	ArrayList<Byte> Chordlist = new ArrayList<Byte>();
	ArrayList<Byte> ChordNOTEOFF = new ArrayList<Byte>();
	
	public Chord(int InputOne,int InputTwo,int InputThree){
		
		Chordlist.add((byte)InputOne);Chordlist.add((byte)0x00);
		Chordlist.add((byte)InputTwo);Chordlist.add((byte)0x00);
		Chordlist.add((byte)InputThree);
		
		ChordNOTEOFF.add((byte)InputOne);ChordNOTEOFF.add((byte)0x00);
		ChordNOTEOFF.add((byte)InputTwo);ChordNOTEOFF.add((byte)0x00);
		ChordNOTEOFF.add((byte)InputThree);
	}
	ArrayList<Byte> getChordlist(){
		
		return Chordlist;
	}
	ArrayList<Byte> getChordNOTEOFF(){
		
		return ChordNOTEOFF;
	}
}