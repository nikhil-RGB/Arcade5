//Local space for testing purposeses.
package utilities;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.*;
@SuppressWarnings("unused")
public final class TestZone
{

	public static void printResourceContents(String s1,int x)
	{
      File f=new File(s1);
      File[] arr=f.listFiles();
      int k=x;
      for(File q:arr)
      {   System.out.println("//"+k);
    	  String n=q.getName();
    	  String s=q.getPath();
    	  System.out.println("\""+q.getPath().replace("\\", "//")+"\""+",");  
          ++k;
      }
	}
	public static void main(String[] args)
	{
		
		printLineNumberedFile(new File("resources_n//Rules//WordSearchNewRules.txt"));
		//printResourceContents("resources_n//Rules",67);
		//Color rr=colorTest();
		//if(rr!=null)
		//{fileTest(rr);}
		//mentalHelp();
		//loopRename("resources_n//Rules//RuleImages//QuizApplicationNew");
	}
	public static void printLineNumberedFile(File f)
	{
		try {
			Scanner sc=new Scanner(f);
			for(int i=0;sc.hasNextLine();++i)
			{System.out.println(i+") "+sc.nextLine());}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void loopRename(String path)
	{
	File f=new File(path);
	f.listFiles(new LexiFilter());
	f.listFiles(new LexiFilter1());
	}
public static Color colorTest()
{
return JColorChooser.showDialog(null, "Col", Color.RED);	
}
public static void fileTest(Color r)
{
JFileChooser jfc=new JFileChooser();
jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
jfc.setMultiSelectionEnabled(true);
int x=jfc.showDialog(null,"Select");
if(x==JFileChooser.APPROVE_OPTION)
{   for(int k=0;k<jfc.getSelectedFiles().length;++k)
	System.out.println(jfc.getSelectedFiles()[k]);
}
}
public static void mentalHelp()
{
if(Desktop.isDesktopSupported()&&Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
{
try {
	Desktop.getDesktop().browse(new java.net.URI("https://www.talkspace.com/blog/psychiatrist-near-me-how-to-find/"));
} catch (IOException | URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
}
}
public static class LexiFilter implements FileFilter
{

	@Override
	public boolean accept(File f) {
		f.renameTo(new File(f.getPath().substring(0, f.getPath().lastIndexOf(File.separatorChar))+File.separatorChar+((char)(f.getName().charAt(0)+1))+".png"));
		return true;
	}
}

public static class LexiFilter1 implements FileFilter
{

	@Override
	public boolean accept(File arg0) 
	{
	    String fs=arg0.getPath().substring(0,arg0.getPath().lastIndexOf(File.separatorChar));
	    int s=Integer.parseInt(arg0.getName().replace(".png",""));
		char ch=(char)s;
		String pp=""+ch+".png";
		arg0.renameTo(new File(fs+File.separator+pp));
	    return true;
	}
	
}
}