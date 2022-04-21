import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import utilities.*;
@SuppressWarnings("serial")
public class WordSearchNew extends MusicalArcadeGame implements utilities.Playable
{
@SuppressWarnings("unused")
private String[] wordsno;
private volatile TextField fini;
private JFrame frm;
private Panel pan;
private Panel keyboard;
private volatile JButton[][] letters;
private volatile String c_string;
private int loc1;
private int loc2;
protected int xp1;
protected int yp1;
public WordSearchNew(String[] wor,int x,int y) 
{
super(DependancyManager.loadDependancy(20));
xp1=x;
yp1=y;
wordsno=wor;    
letters=new JButton[x][y];
frm=new JFrame("Your puzzle here");
JButton def1;
frm.getRootPane().setDefaultButton(def1=this.createDefaultButton());
pan=new Panel();
pan.setLayout(new GridLayout(x,y,0,0));
keyboard=new Panel();
keyboard.setLayout(new GridLayout(8,4,3,3));
ActionListener ar=(ev)->
{
 JButton but=(JButton)(ev.getSource());
 Scanner sc=new Scanner(but.getActionCommand());                        
 loc1=sc.nextInt();                        
 loc2=sc.nextInt();
 playClickSound();
};
ActionListener alphabet=(ev)->
{
letters[loc1][loc2].setText(ev.getActionCommand());

};
for(int i=0;i<x;++i)
{
for(int j=0;j<y;++j)
{
letters[i][j]=new JButton("");
letters[i][j].setToolTipText("<html>Click here and then on a letter to input the letter here<br>"
		+ "Or hit enter to move to the next cell");
letters[i][j].setActionCommand(i+" "+j);
letters[i][j].setFont(new Font("algerian",Font.BOLD,30));
letters[i][j].setBackground(Color.YELLOW);
letters[i][j].addActionListener(ar);
pan.add(letters[i][j]);
}
}
for(int i=0;i<26;++i)
{
JButton tut=new JButton("  "+(char)(65+i)+"  ");
tut.setMnemonic((char)(65+i));
tut.setToolTipText("Press Alt+<this character> on the keyboard for quick input");
tut.addActionListener(alphabet);
tut.setBackground(Color.BLUE);
tut.setForeground(Color.WHITE);
tut.setFont(new Font("algerian",Font.BOLD,18));
keyboard.add(tut);
}
frm.add(keyboard,BorderLayout.EAST);
frm.add(pan);
frm.add(super.box,BorderLayout.WEST);
frm.add(def1,BorderLayout.SOUTH);
frm.setSize(410,350);
frm.addWindowListener(new WindowAdapter(){
@Override()
public void windowClosing(WindowEvent we)
{frm.setVisible(false);
 frm.dispose();
 stopThemeSong();
}
});
MenuBar m=new MenuBar();
Menu hlp=new Menu("Help");
MenuItem rles=new MenuItem("Rules");
rles.addActionListener((ev)->{
	showRules();
});
hlp.add(rles);
m.add(hlp);
Menu me=new Menu("Start Solving");
m.add(me);
MenuItem mi=new MenuItem("ACTIVATE SOLVER(Works after board is filled)");
ActionListener aas=(ev)->{if(isBoardFilled()){buildGUI2();}};
mi.addActionListener(aas);
me.add(mi);
frm.setMenuBar(m);
frm.setVisible(true);
playThemeSong();
frm.setIconImage(ImageManager.ws_1);
frm.setExtendedState(Frame.MAXIMIZED_BOTH);
frm.setResizable(false);
}
protected String[][] fetch(String word,int a,int b)
{ 
 String[] indices=new String[8];
 String[] words=new String[8];
 String[][] re={words,indices};
 String index="";
 String wd="";   
 int plen=yp1-(b);
 RIGHT:
 {
 if(word.length()>plen){break RIGHT;}
 for(int k=0;k<word.length();++k)
 {wd+=letters[a][b+k].getText().trim();
  index+=letters[a][b+k].getActionCommand()+",";  
    }
 }
 words[0]=wd;
 indices[0]=index;
 index="";
 wd="";
 plen=(yp1-plen)+1;
 LEFT:
 {
    if(word.length()>plen){break LEFT;}
    for(int k=0;k<word.length();++k)
    {wd+=letters[a][b-k].getText().trim();
     index+=letters[a][b-k].getActionCommand()+",";
    }
 }
 words[1]=wd;
 indices[1]=index;
 wd="";
 index="";
 plen=xp1-(a);
 DOWN:
 {
 if(word.length()>plen){break DOWN;}   
 for(int k=0;k<word.length();++k)   
 {wd+=letters[a+k][b].getText().trim();
  index+=letters[a+k][b].getActionCommand()+",";
}   
 }
 words[2]=wd;
 indices[2]=index;
 wd="";
 index="";
 plen=(xp1-plen)+1;
 UP:
 {
 if(word.length()>plen){break UP;}   
 for(int k=0;k<word.length();++k)   
 {wd+=letters[a-k][b].getText().trim();
  index+=letters[a-k][b].getActionCommand()+",";
}   
 }
 words[3]=wd;
 indices[3]=index;
 index="";
 wd="";
 plen=0;
 DOWN_LEFT_DIAGONAL:
 {
 try{   
    for(int k=0;k<word.length();++k)
    {wd+=letters[a+k][b-k].getText().trim();
     index+=letters[a+k][b-k].getActionCommand()+",";
    }
    }
    catch(IndexOutOfBoundsException ex)
    {
    wd="";
    index="";
    break DOWN_LEFT_DIAGONAL;
    }
 }
 words[4]=wd;
 indices[4]=index;
 index="";
 wd="";
 plen=0;
 DOWN_RIGHT_DIAGONAL:
 {
 try
 {
 for(int k=0;k<word.length();++k)   
 {wd+=letters[a+k][b+k].getText().trim();
  index+=letters[a+k][b+k].getActionCommand()+",";  
    }   
    
 }   
 catch(IndexOutOfBoundsException ex)
 {wd="";index="";
  break DOWN_RIGHT_DIAGONAL;  
  }   
  }
  words[5]=wd;
  indices[5]=index;
  index="";
  wd="";
  plen=0;
 UP_LEFT_DIAGONAL:
 {
 try{
    for(int k=0;k<word.length();++k)
    {wd+=letters[a-k][b-k].getText().trim();
     index+=letters[a-k][b-k].getActionCommand()+","; 
    }
    }   
 catch(IndexOutOfBoundsException ex)
 {wd="";index="";
  break UP_LEFT_DIAGONAL;
}   
}
words[6]=wd;
indices[6]=index;
index="";
wd="";
plen=0;
UP_RIGHT_DIAGONAL:
{ try{
    for(int k=0;k<word.length();++k)
    {wd+=letters[a-k][b+k].getText().trim();
     index+=letters[a-k][b+k].getActionCommand()+","; 
    }
    }   
 catch(IndexOutOfBoundsException ex)
 {wd="";index="";
  break UP_RIGHT_DIAGONAL;
}}
words[7]=wd;
indices[7]=index;
index="";
wd="";
plen=0;
//This is the end of the fetch method with gets possible matches at the current indices.
return re;
}
protected String requestWordIndices(String word)
{
for(int i=0;i<xp1;++i)
{
    for(int j=0;j<yp1;++j)
    {
    if(!letters[i][j].getText().trim().equalsIgnoreCase(word.charAt(0)+"")){continue;}
    String[][] container= fetch(word,i,j);
    for(int k=0;k<container[0].length;++k)
    {if(container[0][k].equalsIgnoreCase(word)){return container[1][k];}}
    }
}
return "";
}
protected static void buildGUI1()
{
int re[]=new int[2];    
Frame frm=new Frame("Input Size");
TextField txtX=new TextField("Enter number of Rows here");
TextField txtY=new TextField("Enter number of columns here");
ActionListener ar=(ev)->
{TextField src;
 String t=(src=(TextField)(ev.getSource())).getText();
 try{int x=Integer.parseInt(t);
     if(x<=0){throw new NumberFormatException();}
    }
 catch(NumberFormatException ex){src.setText("Invalid.Enter a natural number");return;}
 if(src==txtX){re[0]=Integer.parseInt(t);}
 else{re[1]=Integer.parseInt(t);}
 src.setEditable(false);
};
txtX.addActionListener(ar);
txtY.addActionListener(ar);
txtX.setFont(new Font("algerian",Font.BOLD,35));
txtY.setFont(new Font("algerian",Font.BOLD,35));
JButton ok=new JButton("OKAY");
ActionListener a1=(ev)->{
if((re[0]==0)||(re[1]==0))
{return;}
frm.setVisible(false);
frm.dispose();
new WordSearchNew(new String[0],re[0],re[1]);
};
ok.addActionListener(a1);
ok.setBackground(Color.BLUE);
ok.setFont(new Font("algerian",Font.BOLD,18));
Panel p=new Panel();
p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
p.add(txtX);
p.add(txtY);
frm.add(p);
frm.add(ok,BorderLayout.SOUTH);
frm.setSize(400,400);
frm.setResizable(false);
frm.setIconImage(ImageManager.ws_1);
frm.setVisible(true);
frm.addWindowListener(new WindowAdapter(){
    @Override
    public void windowClosing(WindowEvent we)
    {frm.setVisible(false);
     frm.dispose();
    }
});
}

protected void buildGUI2()
{
 Frame f=new Frame("Word Input Window");
 TextField text=new TextField("Enter the word here");
 fini=text;
 ActionListener ar=(ev)->{
     String s=text.getText().toUpperCase();
     for(int i=0;i<s.length();++i)
     {if(!(((65<=s.charAt(i))&&(s.charAt(i)<=90)))){text.setText("Invalid Word");return;}}
     if(s.length()==0) {text.setText("Invalid word.");return;}
     c_string=s;
     new SearcherThread("Colorizing").start();
    };
 text.addActionListener(ar);
 text.setFont(new Font("algerian",Font.BOLD,25));
 f.add(text);
 f.setSize(150,150);
 f.setIconImage(ImageManager.ws_1);
 f.setVisible(true);
 f.addWindowListener(new WindowAdapter(){
    @Override()
    public void windowClosing(WindowEvent we)
    {
     SwingUtilities.invokeLater(()->{
    	 makeColorNormal();
     });
     f.setVisible(false);
     f.dispose(); 
    }
    });
}
private class SearcherThread extends Thread
{
public SearcherThread(String name)
{super(name);}
@Override
public void run()
{
 for(int k=0;k<xp1;++k)
 {for(int j=0;j<yp1;++j)
  {
      if(letters[k][j].getBackground()!=Color.YELLOW)
      {letters[k][j].setBackground(Color.YELLOW);}  
  }  
    }
 String d=requestWordIndices(c_string);
 if(d.equals("")){fini.setText("Couldn't find");return;}
 ArrayList<String> s=new ArrayList<>(0);
 @SuppressWarnings("resource")
Scanner sr=new Scanner(d);
 sr.useDelimiter(",");
 while(sr.hasNext())
 {s.add(sr.next());}
 for(String st:s)
 {@SuppressWarnings("resource")
Scanner dip=new Scanner(st);
  JButton b=letters[dip.nextInt()][dip.nextInt()];
  b.setBackground(Color.GREEN);
 }
}
}

public boolean isBoardFilled()
{
for(int k=0;k<xp1;++k)
{for(int j=0;j<yp1;++j)
 {if(letters[k][j].getText().trim().isEmpty()){return false;}}
}

return true;
}

public static void main(String[] args)
{ try{buildGUI1();}
  catch(Throwable ex)
  {ErrorManager mm=new ErrorManager(new Frame("ERROR!"),"ISSUE IN WORD SEARCH!");
   mm.display("Issue is: "+ex.getClass().getName(),ex); 
   }
}
@Override
public File getRules() 
{
	
	return DependancyManager.loadDependancy(70);
}
public void showRules()
{
RulesManager obj=new RulesManager(getRules(),new int[] {8,14,25},DependancyManager.loadIconBatch(67,70),450,450);	
obj.displayRules();
}
public JButton createDefaultButton()
{
ActionListener ar=(ev)->
{
if(loc2+1<yp1)
{
 ++loc2;
 return;
}
else if(loc1+1<xp1)
{++loc1;
 loc2=0;
return;}
else
{loc1=0;loc2=0;
 return;
}
};
JButton enter=new JButton("Next cell");
enter.setFont(new Font("algerian",Font.BOLD,18));
enter.setBackground(Color.CYAN.darker());
enter.addActionListener(ar);
return enter;	
}
public void makeColorNormal()
 {
for(int i=0;i<letters.length;++i)	
 {
for(int j=0;j<letters[i].length;++j)	
   {
    letters[i][j].setBackground(Color.YELLOW);	
   }
 }
 }

}