package Arg.GM;
public class GMtable{
	
	int INS_NUM;
	
	public GMtable(){	}
	
	public GMtable(int ins){
		INS_NUM = ins;
	}
	
	public String getFirstEntry(){
		String NUM = "";
		if (INS_NUM < 10){
			NUM = "0" + INS_NUM + "";
		}else if (INS_NUM > 10){
			NUM = INS_NUM + "";	
		}
		return NUM;
	}
    public String getSecondEntry(){
    	String CLASS = "";
    	if (INS_NUM < 8){
    		CLASS = "鋼琴類樂器";
    	}else if (INS_NUM > 7 && INS_NUM <24){
    		CLASS = "色彩打擊樂器";
    	}else if (INS_NUM > 23 && INS_NUM < 31){
    		CLASS = "吉他類樂器";
    	}else if (INS_NUM > 30 && INS_NUM < 40){
    		CLASS = "貝斯類樂器";
    	}else if (INS_NUM > 39 && INS_NUM < 48){
    		CLASS = "弦樂器";
    	}else if (INS_NUM > 47 && INS_NUM < 56){
    		CLASS = "合奏/合唱類";
    	}else if (INS_NUM > 55 && INS_NUM < 64){
    		CLASS = "銅管";
    	}else if (INS_NUM > 63 && INS_NUM < 72){
    		CLASS = "笛";
    	}else if (INS_NUM > 71 && INS_NUM < 80){
    		CLASS = "合成主音";
    	}else if (INS_NUM > 79 && INS_NUM < 88){
    		CLASS = "簧管";
    	}else if (INS_NUM > 87 && INS_NUM < 96){
    		CLASS = "合成音色";
    	}else if (INS_NUM > 95 && INS_NUM < 104){
    		CLASS = "合成效果";
    	}else if (INS_NUM > 103 && INS_NUM < 112){
    		CLASS = "民間樂器";
    	}else if (INS_NUM > 111 && INS_NUM < 120){
    		CLASS = "打擊樂器";
    	}else{
    		CLASS = "Sound Effects 聲音效果";
    	}
    	
    	return CLASS;
    }
    public String getThirdEntry(){
    	String INS_NAME = this.getINSinfo(INS_NUM) + "";
    	return INS_NAME;
    }
    
    public String getINSinfo (int flag) {
    	int instruments = flag;
    	String INSname = "";
        
    	switch (instruments){
	       //鋼琴類	
			case 0: INSname +="Acoustic Grand Piano"; break;
			case 1: INSname +="Bright Acoustic Piano";break;
			case 2: INSname +="Electric Grand Piano";break;
			case 3: INSname +="Honky-tonk Piano";break;
			case 4: INSname +="Rhodes Piano";break;
			case 5: INSname +="Chorused Piano";break;
			case 6: INSname +="Harpsichord";break;
			case 7: INSname +="Clavichord";break;
	       //色彩打擊樂器 
			case 8: INSname +="Celesta";break;
			case 9: INSname +="Glockenspiel";break;
			case 10: INSname +="Music box";break;
			case 11: INSname +="Vibraphone"; break;
			case 12: INSname +="Marimba";break;
			case 13: INSname +="Xylophone"; break;
			case 14: INSname +="Tubular Bells";break;
			case 15: INSname +="Dulcimer";break;
			case 16: INSname +="Hammond Organ";break;
			case 17: INSname +="Percussive Organ"; break;
			case 18: INSname +="Rock Organ";break;
			case 19: INSname +="Church Organ";break;
			case 20: INSname +="Reed Organ";break;
			case 21: INSname +="Accordian";break;
			case 22: INSname +="Harmonica";break;
			case 23: INSname +="Tango Accordian";break;
			//吉他
			case 24: INSname +="Acoustic Guitar吉他";break;
			case 25: INSname +="Acoustic Guitar";break;
			case 26: INSname +="Electric Guitar";break;
			case 27: INSname +="Electric Guitar";break;
			case 28: INSname +="Electric Guitar";break;
			case 29: INSname +="Overdriven Guitar";break;
			case 30: INSname +="Distortion Guitar";break;
			//貝司
			case 31: INSname +="Guitar Harmonics";break;
			case 32: INSname +="Acoustic Bass";break;
			case 33: INSname +="Electric Bass";break;
			case 34: INSname +="Electric Bass"; break;
			case 35: INSname +="Fretless Bass";break;
			case 36: INSname +="Slap Bass 1";break;
			case 37: INSname +="Slap Bass 2";break;
			case 38: INSname +="Synth Bass 1";break;
			case 39: INSname +="Synth Bass 2";break;	
			//弦樂器
			case 40: INSname +="Violin" ;break;
			case 41: INSname +="Viola ";break;
			case 42: INSname +="Cello ";break;
			case 43: INSname +="Contrabass ";break;
			case 44: INSname +="Tremolo Strings";break;
			case 45: INSname +="Pizzicato Strings";break;
			case 46: INSname +="Orchestral Harp ";break;
			case 47: INSname +="Timpani";break;
			//合奏合唱 
			case 48: INSname +="Ensemble 1";break;
			case 49: INSname +="Ensemble 2";break;
			case 50: INSname +="Synth Strings 1";break;
			case 51: INSname +="Synth Strings 2";break;
			case 52: INSname +="Choir Aahs";break;
			case 53: INSname +="Voice Oohs ";break;
			case 54: INSname +="Synth Voice";break;
			case 55: INSname +="Orchestra Hit";break;
			//銅管 
			case 56: INSname +="Trumpet";break;
			case 57: INSname +="Trombone";break;
			case 58: INSname +="Tuba";break;
			case 59: INSname +="Muted Trumpet";break;
			case 60: INSname +="French Horn";break;
			case 61: INSname +="Brass Section";break;
			case 62: INSname +="Synth Brass 1";break;
			case 63: INSname +="Synth Brass 2";break;
			//簧管 ;
			case 64: INSname +="Soprano Sax";break;
			case 65: INSname +="Alto Sax";break;
			case 66: INSname +="Tenor Sax";break;
			case 67: INSname +="Baritone Sax";break;
			case 68: INSname +="Oboe";break;
			case 69: INSname +="EnglishHorn";break;
			case 70: INSname +="Bassoon";break;
			case 71: INSname +="Clarinet";break;
			//笛 
			case 72: INSname +="Piccolo";break;
			case 73: INSname +="Flute";break;
			case 74: INSname +="Recorder";break;
			case 75: INSname +="Pan Flute";break;
			case 76: INSname +="Bottle Blow";break;
			case 77: INSname +="Shakuhachi"; break;
			case 78: INSname +="Whistle";break;
			case 79: INSname +="Ocarina";break;
			//合成主音 
			case 80: INSname +="Lead 1 ";break;
			case 81: INSname +="Lead 2 ";break;
			case 82: INSname +="Lead 3 ";break;
			case 83: INSname +="Lead 4 ";break;
			case 84: INSname +="Lead 5 ";break;
			case 85: INSname +="Lead 6 ";break;
			case 86: INSname +="Lead 7 ";break;
			case 87: INSname +="Lead 8 ";break;
			//合成音色 
			case 88: INSname +="Pad 1  ";break;
			case 89: INSname +="Pad 2 ";break;
			case 90: INSname +="Pad 3 ";break;
			case 91: INSname +="Pad 4 ";break;
			case 92: INSname +="Pad 5 ";break;
			case 93: INSname +="Pad 6 ";break;
			case 94: INSname +="Pad 7 ";break;
			case 95:INSname +="Pad 8 ";break;
			//合成效果 
			case 96: INSname +="FX 1 ";break;
			case 97: INSname +="FX 2 ";break;
			case 98: INSname +="FX 3 ";break;
			case 99: INSname +="FX 4 ";break;
			case 100: INSname +="FX 5 (brightness) 合成效果 5 明亮 ";break;
			case 101: INSname +="FX 6 (goblins) 合成效果 6 鬼怪 ";break;
			case 102: INSname +="FX 7 (echoes) 合成效果 7" ;break;
			case 103: INSname +="FX 8 (sci-fi) 合成效果 8 科幻" ;break;
			//民間樂器 
			case 104: INSname +="Sitar 西塔爾";break;
			case 105: INSname +="Banjo ";break;
			case 106: INSname +="Shamisen 三昧線日本";break;
			case 107: INSname +="Koto 十三弦箏日本";break;
			case 108: INSname +="Kalimba 卡林巴 ";break;
			case 109: INSname +="Bagpipe 風笛 ";break;
			case 110: INSname +="Fiddle 民族";break;
			case 111: INSname +="Shanai 山奈 ";break;
			//打擊樂器 
			case 112: INSname +="Tinkle Bell 叮噹鈴 ";break;
			case 113: INSname +="Agogo";break;
			case 114: INSname +="Steel Drums 鋼鼓" ;break;
			case 115: INSname +="Woodblock 木魚 ";break;
			case 116: INSname +="Taiko Drum 太鼓 ";break;
			case 117: INSname +="Melodic Tom 通通鼓" ;break;
			case 118: INSname +="Synth Drum 合成鼓 ";break;
			case 119: INSname +="Reverse Cymbal 銅鈸" ;break;
			//Sound Effects 聲音效果 
			case 120: INSname +="Guitar Fret Noise 吉他換把雜音" ;break;
			case 121: INSname +="Breath Noise 呼吸聲";break;
			case 122: INSname +="Seashore 海浪聲";break;
			case 123: INSname +="Bird Tweet 鳥鳴";break;
			case 124: INSname +="Telephone Ring 電話鈴";break;
			case 125: INSname +="Helicopter 直升機";break;
			case 126: INSname +="Applause 鼓掌聲";break;
			case 127: INSname +="Gunshot 槍聲";break;		
			}
    		return INSname;
    	}
    
}