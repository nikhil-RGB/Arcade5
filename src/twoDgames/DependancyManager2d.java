package twoDgames;
import javax.swing.*;
import java.awt.*;
import java.io.File;
@SuppressWarnings("unused")
public class DependancyManager2d
{
public static String[] image_deps= new String[]
		{
				//1:
				"resources_n//2D_resources//astronaut.png",
				//2:
				"resources_n//2D_resources//asteroid.png"
		};
public static String[] text_deps=new String[] {};
public static String[] sound_deps=new String[] {
		//1
		"resources_n//CheckersSong.wav",
		//2
		"resources_n//2D_resources//colln.wav"
};
public static enum DependancyType
{
IMAGE,TEXT,SOUND	
}
public static File loadDependancy(int k,DependancyType dt)
{
String[] arr;
if(dt.equals(DependancyType.IMAGE))
{arr=image_deps;}
else if(dt.equals(DependancyType.SOUND))
{arr=sound_deps;}
else
{arr=text_deps;}
File f=new File(arr[k]);
return f;
}
public static boolean checkDependancies()
{
	String[][] chs=new String[][] {image_deps,text_deps,sound_deps};
	for(int i=0;i<chs.length;++i)
	{
		for(int j=0;j<chs[i].length;++j)
		{
			File f=new File(chs[i][j]);
			if(!f.exists())
			{
				return false;
			}
		}
	}
	return true;
}
}
