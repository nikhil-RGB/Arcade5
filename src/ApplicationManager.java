import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;
import javax.swing.border.*;
public class ApplicationManager implements utilities.Helper
{   
	public static String email="javakingxi@gmail.com";
	public static String url="https://github.com/nikhil-RGB";
	public static String[] tips= {"<html>Click here to play Cross n Knots<br>"
			+ "Your standard X and O game is here!","<html>Click here to play"
					+ "a quiz game i.e a game where you create quizzes for your<br>"
					+ "friends and vice versa!!","<html>Click to open a simple number puzzle, where you<br>"
							+ "sort numbers 1-16 in ascending order.","<html>Click to play a complex form "
									+ "of<br>tic tac toe, nested tic tac toe-With 9 BOARDS<br>IN 1 GRID",
									"<html>Click to use a word puzzle software, which searches<br> for particular words in a table of letters",
						"Click to play 2v2 Checkers!!","<html>Click to play a MODIFIED VERSION of chain reaction<br>EXPLOSION!!","<html>Click to play"
								+ "MineSweeper-The classic game<br>1987 version!"			
	};
public static void main(String[] args)
{
	boolean cont=DependancyManager.checkDependancies();
	if(!cont) 
	{
	QuickErrorManager.manage("ERROR!Missing Resources.","1)It seems the resources folder has been tampered with","2)The project cannot function with corrupted resources","3)Simply put kindly re-download the project to continue");	
	return; 
	}
	Frame frm=new Frame("Nikhil's App");
	MenuBar mb=new MenuBar();
	Menu rules=new Menu("Rules");
	mb.add(rules);
	MenuItem how=new MenuItem("How to access rules for each game");
	rules.add(how);
	how.addActionListener((ev)->{
		String text="<html>The rules option for each game, is present in<br>"
				+ " the 'help' menu of the menu bar of each game's individual<br> window"
				+ ", click on a game and then select Help->Rules<br>"
				+ "from the menu bar, to see it's rules.";
		JOptionPane.showMessageDialog(frm, text,"How to access rules",JOptionPane.INFORMATION_MESSAGE);
	});
	Menu more=new Menu("More");
	MenuItem morestuff=new MenuItem("About Developer");
	MenuItem moreApps=new MenuItem("More apps from Nikhil");
	MenuItem email=new MenuItem("Contact Developer");
	ActionListener mail=(evv)->{
		try 
		{
			Desktop.getDesktop().mail(new java.net.URI("mailto:"+ApplicationManager.email));
		}
		catch(Throwable obj)
		{JOptionPane.showMessageDialog(null,"Could not open your mailing app","Oops",JOptionPane.ERROR_MESSAGE);}
	};
	email.addActionListener(mail);
	ActionListener ard=(ev)->{
		
		try
		{
			Desktop.getDesktop().browse(new java.net.URI(ApplicationManager.url));
		}
		catch(Throwable obj)
		{JOptionPane.showMessageDialog(null,"Could not open Developer website","Oops",JOptionPane.ERROR_MESSAGE);}
	};
	morestuff.addActionListener(ard);
	moreApps.addActionListener(ard);
	more.add(moreApps);
	more.add(morestuff);
	more.add(email);
	MenuItem mi=new MenuItem("Open 2D game menu");
	mi.addActionListener((ev)->{
		frm.setVisible(false);
		frm.dispose();
		twoDgames.TwoDAppManager.main(new String[0]);
	});
	more.add(mi);
	mb.add(more);
	frm.setMenuBar(mb);
	ImageManager.main(new String[]{});
	int x=8;
	ImageIcon chki=DependancyManager.loadIconDependancy(x+0);
	ImageIcon crks=DependancyManager.loadIconDependancy(x+1);
	ImageIcon mtic=DependancyManager.loadIconDependancy(x+2);
	ImageIcon nump=DependancyManager.loadIconDependancy(x+3);
	ImageIcon numr=DependancyManager.loadIconDependancy(x+4);
	ImageIcon qapp=DependancyManager.loadIconDependancy(x+5);
	ImageIcon wsrch=DependancyManager.loadIconDependancy(x+6);
	ImageIcon minesw=DependancyManager.loadIconDependancy(1);    
//Indentation
    try{
JPanel holder=new JPanel(new GridLayout(0,1));
JScrollPane jscp=new JScrollPane(holder,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
JLabel disp=new JLabel("<html>Welcome to the Arcade!!<br>"
		+ "Enjoy a wide variety of games!!<br>"
		+ "Hover your mouse above a particular game");
disp.setForeground(Color.ORANGE);
disp.setFont(new Font(Font.SERIF,Font.BOLD,18));
Border bord=BorderFactory.createLineBorder(Color.RED, 5);
jscp.setViewportBorder(bord);
jscp.setColumnHeaderView(disp);
JButton[] b=new JButton[]{new JButton("CrossKnotsNew",crks),new JButton("QuizApplicationNew",qapp),new JButton("NumberPuzzleNew",nump),new JButton("MetaTicTacToeNew",mtic),new JButton("WordSearchNew",wsrch),new JButton("Checkers",chki),new JButton("NumberReactionNew",numr),new JButton("MineSweeper",minesw)};
PainterThread pt=new PainterThread(b[0],b[1],b[2],b[3],b[4],b[5],b[6],b[7]);
ActionListener ar=(ev)->{
try
{    
Method s=Class.forName(ev.getActionCommand()).getMethod("main",String[].class);
s.invoke(null,new String[1]);
}
catch(Exception ex)
{ex.printStackTrace();}
};
for(int i=0;i<b.length;++i)
{b[i].addActionListener(ar);
 b[i].setHorizontalTextPosition(SwingConstants.CENTER);
 b[i].setVerticalTextPosition(SwingConstants.TOP);
 b[i].setFont(new Font(Font.SANS_SERIF,Font.BOLD,36));
 b[i].setBackground(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
 holder.add(b[i]);
}
frm.add(jscp);
frm.setIconImage(ImageManager.nr_1);
frm.setSize(500,500);
frm.setResizable(false);
frm.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent we)
{   pt.stopChange();
    System.exit(0);}
});
ApplicationManager.initializeMenuTips(b,tips);
frm.setVisible(true);
pt.start();
}
catch(Throwable obj){
    ErrorManager ob=new ErrorManager(frm,"Application error");
    ob.display("Please help Nikhil,report it, there's a "+obj.getClass().getName()+"!!",obj);
}
}
public static class PainterThread extends Thread
{
 private Component[] ar;
 public volatile boolean stop;
@Override()
public void run()
{
    CHANGER:
for(;;)
{
if(stop){break CHANGER;}
try{
    Thread.sleep(2000);   
    }
    catch(InterruptedException ex){}
for(int i=0;i<ar.length;i++)
{
 if(stop){break CHANGER;}   
 Color c=ar[i].getBackground();
 int radd=(int)(Math.random()*50);
 int gradd=(int)(Math.random()*50);
 int bradd=(int)(Math.random()*50);
 int red=c.getRed()+radd;
 int green=c.getGreen()+gradd;
 int blue=c.getBlue()+bradd;
 if(!(red<=255&&green<=255&&blue<=255))
 {
 red=(int)(Math.random()*255);   
 green= (int)(Math.random()*255);
 blue=(int)(Math.random()*255);
}
ar[i].setBackground(new Color(red,green,blue));
}
}
}
public PainterThread(Component... args)
{
    ar=args;
    stop=false;
}
public void stopChange()
{
stop=true;
}
}
public static Button[][] copy2DArray(Button[][] input,Button[][] output)
{
for(int i=0;i<input.length;i++)
{
for(int j=0;j<input[i].length;++j)
{
output[i][j]=new Button(input[i][j].getLabel());
output[i][j].setFont(new Font("algerian",Font.BOLD,23));
}
}
return output;
}
//This method initializes an array of Strings for ToolTipText of given buttons
protected static void initializeMenuTips(JButton[] arrs,String[] tips)
{
	for(int k=0;k<arrs.length;++k)
	{arrs[k].setToolTipText(tips[k]);}
}
}
