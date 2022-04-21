package utilities;
import static utilities.MusicalArcadeGame.MotionPanel;
import java.io.LineNumberReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedWriter;
@SuppressWarnings("unused")
public class RulesManager
{
private JLabel[] labs;
private ArrayList<String> lines;
private String[] textMssgs;
private File file;
private int[] text_lines;
private Image[] image;
{lines=new ArrayList<>(0);}
public RulesManager(File f,int[] blines,ImageIcon[] img,int x,int y)
{
scaleImages(img,x,y);
this.file=f;
this.readLines();
this.text_lines=blines;
this.textMssgs=split();
this.labs=new JLabel[textMssgs.length+img.length];
for(int k=0,z=0,z1=0;k<this.labs.length;++k)
{
	if(k%2==0)
 {
  labs[k]=new JLabel(this.textMssgs[z]);
  ++z;
 }
	else
 {
		labs[k]=new JLabel(img[z1]);
		labs[k].setSize(x,y);
		z1++;
 }
 labs[k].setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
 labs[k].setHorizontalAlignment(SwingConstants.CENTER);
 labs[k].setVerticalAlignment(SwingConstants.CENTER);
}
}
public RulesManager(File f)
{   this.file=f;
	this.readLines();
	this.labs=new JLabel[0];
	labs[0]=new JLabel();
	String s="<html>";
	for(int k=0;k<this.lines.size();++k)
	{s+="  "+lines.get(k)+"<br>";}
	labs[0].setText(s);
	labs[0].setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
}
private String[] split()
{
String[] arr=new String[text_lines.length+1];
quickInit(arr);
for(int k=0,z=0;k<this.lines.size();++k)
 {
	if(this.contains(k)) {++z;}
 	arr[z]+="  "+this.lines.get(k)+"<br>";
 }
return arr;
}
private void scaleImages(ImageIcon[] images,int x,int y)
{
for(ImageIcon os:images)
{
	Image ss=os.getImage().getScaledInstance(x, y,Image.SCALE_SMOOTH);
    os.setImage(ss);	
}
}
private void readLines()
{
	Scanner reader=null;
try 
{
	reader=new Scanner(file);
    while(reader.hasNextLine())
    {lines.add(reader.nextLine());}
}
catch (FileNotFoundException e) 
{
	e.printStackTrace();
}
finally
{
	reader.close();
}

}
private static void quickInit(String[] ar)
{
for(int k=0;k<ar.length;++k)	
{
ar[k]="<html>";	
}
}
private  boolean contains(int l)
{
for(int k=0;k<this.text_lines.length;++k)	
{
if(this.text_lines[k]==l)	
{return true;}
}
return false;
}
public void displayRules()
{
JFrame jfrm=new JFrame("Rules");
JPanel mp=new MotionPanel(jfrm);
mp.setLayout(new BoxLayout(mp,BoxLayout.Y_AXIS));
for(int k=0;k<this.labs.length;++k)
{
mp.add(labs[k]);	
}
JScrollPane jsp=new JScrollPane(mp);
jfrm.add(jsp);
jfrm.setSize(450,450);
jfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
jfrm.setIconImage(new ImageIcon("resources_n//QuizApplicationIcon.png").getImage());
jfrm.setExtendedState(JFrame.MAXIMIZED_BOTH);
jfrm.setVisible(true);
}
}
