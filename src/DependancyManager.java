import java.util.*;
import javax.swing.ImageIcon;
import java.io.*;
@SuppressWarnings("unused")
//Dependancy Managaer manages dependancies through a index-based list of String filepaths
public final class DependancyManager implements utilities.Helper
{
	private static final String[] dependancies= {//This list contains all String paths of resources
			//0
			"resources_n//FLAG_IMG.png",
			//1
			"resources_n//MINE_IMG.png",
			//2
			"resources_n//QUESTION_MARK_IMG.png",
			//3
			"resources_n//BLANK_IMG.png",
			//4
			"resources_n//Click.wav",
			//5
			"resources_n//Explosion.wav",
			//6
			"resources_n//m_song.wav",
			//7
			"resources_n//exploded_mine.png",
			//8
			"resources_n//CheckersIcon.png",
			//9
			"resources_n//CrossKnotsIcon.png",
			//10
			"resources_n//MetaTicTacToeIcom.png",
			//11
			"resources_n//NumberPuzzleIcon.png",
			//12
			"resources_n//NumberReactionIcon.png",
			//13
			"resources_n//QuizApplicationIcon.png",
			//14
			"resources_n//WordSearchIcon.png",
			//15
			"resources_n//CheckersSong.wav",
			//16
			"resources_n//MetaTicTacToeSong.wav",
			//17
			"resources_n//NumberPuzzleNewSong.wav",
			//18
			"resources_n//NumberReactionSong.wav",
			//19
			"resources_n//QuizApplicationSong.wav",
			//20
			"resources_n//WordSearchNewSong.wav",
			//21
			"resources_n//Rules//RuleImages//Checkers//CheckersCapture1.png",
			//22
			"resources_n//Rules//RuleImages//Checkers//CheckersCapture2.png",
			//23
			"resources_n//Rules//RuleImages//Checkers//CheckersHover.png",
			//24
			"resources_n//Rules//RuleImages//Checkers//CheckersMovement.png",
			//25
			"resources_n//Rules//RuleImages//Checkers//CheckersSave.png",
			//26
			"resources_n//Rules//CheckersRules.txt",
			//27
			"resources_n//Rules//RuleImages//NumberReactionNew//A.png",
			//28
			"resources_n//Rules//RuleImages//NumberReactionNew//B.png",
			//29
			"resources_n//Rules//RuleImages//NumberReactionNew//C.png",
			//30
			"resources_n//Rules//RuleImages//NumberReactionNew//D.png",
			//31
			"resources_n//Rules//RuleImages//NumberReactionNew//E.png",
			//32
			"resources_n//Rules//RuleImages//NumberReactionNew//F.png",
			//33
			"resources_n//Rules//RuleImages//NumberReactionNew//G.png",
			//34
			"resources_n//Rules//RuleImages//NumberReactionNew//H.png",
			//35
			"resources_n//Rules//RuleImages//NumberReactionNew//I.png",
			//36
			"resources_n//Rules//RuleImages//NumberReactionNew//J.png",
            //37
			"resources_n//Rules//NumberReactionNewRules.txt",
			//38
			"resources_n//Rules//RuleImages//NumberPuzzleNew//A.png",
			//39
			"resources_n//Rules//RuleImages//NumberPuzzleNew//B.png",
			//40
			"resources_n//Rules//RuleImages//NumberPuzzleNew//C.png",
			//41
			"resources_n//Rules//RuleImages//NumberPuzzleNew//D.png",
			//42
			"resources_n//Rules//NumberPuzzleNewRules.txt",
			//43
			"resources_n//Rules//RuleImages//MetaTicTacToeNew//A.png",
			//44
			"resources_n//Rules//RuleImages//MetaTicTacToeNew//B.png",
			//45
			"resources_n//Rules//RuleImages//MetaTicTacToeNew//C.png",
			//46
			"resources_n//Rules//RuleImages//MetaTicTacToeNew//D.png",
			//47
			"resources_n//Rules//RuleImages//MetaTicTacToeNew//E.png",
			//48
			"resources_n//Rules//MetaTicTacToeNew.txt",
			//49
			"resources_n//Rules//RuleImages//MineSweeper//A.png",
			//50
			"resources_n//Rules//RuleImages//MineSweeper//B.png",
			//51
			"resources_n//Rules//RuleImages//MineSweeper//C.png",
			//52
			"resources_n//Rules//RuleImages//MineSweeper//D.png",
			//53
			"resources_n//Rules//RuleImages//MineSweeper//E.png",
			//54
			"resources_n//Rules//RuleImages//MineSweeper//F.png",
			//55
			"resources_n//Rules//RuleImages//MineSweeper//G.png",
			//56
			"resources_n//Rules//RuleImages//MineSweeper//H.png",
			//57
			"resources_n//Rules//MineSweeperRules.txt",
			//58
			"resources_n//Rules//RuleImages//CrossKnotsNew//A.png",
			//59
			"resources_n//Rules//CrossKnotsNewRules.txt",
			//60
			"resources_n//Rules//RuleImages//QuizApplicationNew//A.png",
			//61
			"resources_n//Rules//RuleImages//QuizApplicationNew//B.png",
			//62
			"resources_n//Rules//RuleImages//QuizApplicationNew//C.png",
			//63
			"resources_n//Rules//RuleImages//QuizApplicationNew//D.png",
			//64
			"resources_n//Rules//RuleImages//QuizApplicationNew//E.png",
			//65
			"resources_n//Rules//RuleImages//QuizApplicationNew//F.png",
			//66
			"resources_n//Rules//QuizApplicationNewRules.txt",
			//67
			"resources_n//Rules//RuleImages//WordSearchNew//A.png",
			//68
			"resources_n//Rules//RuleImages//WordSearchNew//B.png",
			//69
			"resources_n//Rules//RuleImages//WordSearchNew//C.png",
			//70
			"resources_n//Rules//WordSearchNewRules.txt",
			//71
			"resources_n//crowning.wav"
   };
	//Checks dependencies existence
public static boolean checkDependancies()
{
for(String path:dependancies)	
{
File f=new File(path);	
if(!f.exists()) {return false;}
}
return true;
}
//Clones dependencies and returns it in a String[]
public static String[] cloneDependancies()
{
String[] deps=new String[dependancies.length];	
for(int k=0;k<dependancies.length;++k)
{deps[k]=dependancies[k];}
return deps;
}
//Returns filepath of requested dependancy
public static String getDependancy(int k)
{
return dependancies[k];	
}
//Gives File object of requested dependancy
public static File loadDependancy(int k)
{return new File(dependancies[k]);}
public static ImageIcon loadIconDependancy(int k)
{
	String f=DependancyManager.getDependancy(k);
	ImageIcon img=new ImageIcon(f);
	return img;
}
//Loads a batch of icons,start inclusive-end exclusive
public static ImageIcon[] loadIconBatch(int start,int end)
{
	ImageIcon[] imgs=new ImageIcon[end-start];
	for(int k=0;start<end;++start,++k)
	{
		imgs[k]=new ImageIcon(getDependancy(start));
	}
	return imgs;
}
}