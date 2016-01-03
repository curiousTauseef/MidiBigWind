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
		String[] script = {"�C" , "��" , "��"};
		String[] level ={"C��","B��","A��"};
		OUTPUT = "�q�������סG\t" + script[LV-1] + "\n�־����������סG\t��" + script[INS_LV-1] + "\n��t�G\t\t��" + script[TM_LV-1] + "\n�����G\t\t" + level[LV-1];		
		return OUTPUT;
	}
}

/******************************************************************
 ******************************************************************
 							��
 ------------------------------------------------------------------
 ���š@�G�����B�A���B���C{1�� 2�� 3��}
 ���y�ơG�V�h�@���׶V��{4~6 ��MTrk �з�}
 			>6 = 3��
 			4~6= 2��
 			<4 = 1��
 ��ơG�V�� ���׶V��{80~120 �O�з�}
 			>120 	= 3��
 			80~120	= 2��
 			<80 	= 1��
 �־������G
 		�`�����־������²��G1��
 		�j��־��B����־����G2��
 		�X���n����			�G3��
 
 �W�h�G
 	�@�Ӽ־��n������{�A�H�@�ӤH���A���H�ֹΤH�O���A���A�W�L�h�x���A�C�󤭤H�h�O²��
 	�ӱ`�����־��Ҧp�N�L�B�����A���O���²��A�ӥj��־��h���A���A��L���X���H�n�έ���
 	�n�A�n�H�H�O��{�X�ӫh�D�`�x���C
 	�b��t�譱�A80~120��������t���²��A�W�L120����t�|�����		
 
 
 
 
 
 
 
 
 
 
 ******************************************************************
 ******************************************************************/