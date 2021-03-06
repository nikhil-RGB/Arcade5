//Cross and Knots , Application 4 of Arcade 2.0.
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import utilities.MusicalArcadeGame;

import javax.sound.sampled.*;
@SuppressWarnings({ "unused", "serial" })
public class CrossKnotsNew extends MusicalArcadeGame implements utilities.Playable
{
public static final String[] winCombns={"0 1 2","3 4 5","6 7 8","0 3 6","1 4 7","2 5 8","0 4 8","2 4 6"};
private Frame frm;
private boolean win_state;
private String[] table;
private int count;
private Button[] buttons;
@SuppressWarnings("resource")
public static String checkWinner(String[] table)
{
String p="O";
for(int i=0;i<2;i++)
{
CHECKER:
for(int j=0;j<winCombns.length;++j)
{
Scanner ssc=new Scanner(winCombns[j]);
boolean flag=true;
while(ssc.hasNextInt())
{
if(!table[ssc.nextInt()].equalsIgnoreCase(p))
{flag=false;
 continue CHECKER;
}

}
if(flag){return p;}
}
p="X";
}
return "";
}
public CrossKnotsNew() 
{
super(DependancyManager.loadDependancy(16));
MenuBar mb=new MenuBar();
Menu hlp=new Menu("Help");
MenuItem rles=new MenuItem("Rules");
rles.addActionListener((ev)->
{
ImageIcon[] iim=new ImageIcon[] {DependancyManager.loadIconDependancy(58)};
int[] x=new int[] {0};
displayRules(x,iim,300,300);
});
hlp.add(rles);
mb.add(hlp);
frm=new Frame("TicTacToe");
frm.setMenuBar(mb);
Panel pan=new Panel();
pan.setLayout(new GridLayout(3,3));
table=new String[9];
init(table);
buttons=new Button[9];
ActionListener ar=(ev)->{
if(!((Button)(ev.getSource())).getLabel().isEmpty()){return;}
if(win_state){return;}
++count;
playClickSound();
Button but=(Button)(ev.getSource());
if(count%2!=0)
{
but.setLabel("X");
but.setBackground(Color.RED);
table[Integer.parseInt(but.getActionCommand())]="X";
}
else
{but.setLabel("O");
 but.setBackground(Color.BLUE);
 table[Integer.parseInt(but.getActionCommand())]="O";
}
String bruh=checkWinner(table);
if(!bruh.isEmpty()){
win_state=true;    
displayResults(bruh);
}
};

for(int k=0;k<9;++k)
{
buttons[k]=new Button("");
buttons[k].setActionCommand(k+"");
buttons[k].addActionListener(ar);
buttons[k].setFont(new Font("algerian",Font.BOLD,20));
pan.add(buttons[k]);
}
frm.add(pan);
frm.add(super.box,BorderLayout.EAST);
frm.setSize(340,250);
frm.setIconImage(ImageManager.ck_1);
frm.setResizable(false);
frm.setVisible(true);
frm.addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent we)
{
frm.setVisible(false);
frm.dispose();
stopThemeSong();
}
});
}
private static void init(String arr[])
{
for(int i=0;i<arr.length;++i)
{
arr[i]="";
}
}
private void displayResults(String winner)
{
super.disableSoundControls();
this.stopThemeSong();
Frame f=new Frame(winner+" WON");
try
{f.setIconImage(ImageManager.ck_1);}
catch(Exception ex){}
Label l=new Label(winner+ " WON!!");
l.setFont(new Font("algerian",Font.BOLD,36));
l.setBackground(Color.CYAN);
f.add(l);
f.setSize(400,200);
f.setResizable(false);
f.setVisible(true);
f.addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent we)
{
f.setVisible(false);
f.dispose();
}
});
}
public static void main(String[] args)
{    
    try{
    @SuppressWarnings("unused")
	CrossKnotsNew obj=new CrossKnotsNew();
    obj.playThemeSong();
     }
    catch(Throwable object)
    {ErrorManager ob=new ErrorManager(new Frame("Error"),"Problem in Cross And Knots");
     ob.display("OH NO...Cross Knots is throwing "+object.getClass().getName()+"!!",object);
    }
}
@Override
public File getRules() {
	return DependancyManager.loadDependancy(59);
}
}