//New Addition to original project, for the classic implementation of ULTIMATE TIC TAC TOE,
//Arcade conversion from 2.0 to 3.0
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;
import utilities.*;
import javax.swing.*;
@SuppressWarnings("unused")
public class MetaTicTacToeNew extends MusicalArcadeGame implements java.io.Serializable,SavableArcadeGame,Playable
{
private static final long serialVersionUID=9788940032L;
 private static MetaTicTacToeNew current_game;
 protected MenuItem save;//MenuItem for save
 protected MenuItem load;//MenuItem for load
 private static final String[] UNIVERSAL_WIN_COMBOS= {"0 1 2","3 4 5","6 7 8","0 3 6","1 4 7","2 5 8","0 4 8","2 4 6"};
 private final Frame GLOBAL_BOARD;
 private Panel[] local_boards;
 private int button_count;
 private String[] global_table;
 private int lbc;
 private Panel internalHolder;
 protected MetaTicTacToeNew()
 {
 super(DependancyManager.loadDependancy(16));
 this.rapidInit();    
 button_count=0;
 lbc=0;
 GLOBAL_BOARD=new Frame("ULTIMATE TIC TAC TOE");
 GLOBAL_BOARD.setBackground(Color.BLUE);
 Panel pp=new Panel();
 internalHolder=pp;
 pp.setLayout(new GridLayout(3,3,5,5));
 GLOBAL_BOARD.add(BorderLayout.CENTER,pp);
 GLOBAL_BOARD.add(super.box,BorderLayout.EAST);
 local_boards=new Panel[9];
 GameSaver.SerializableActionListener al=new GameSaver.SerializableActionListener(){
 private static final long serialVersionUID=9008772L;
	 public void actionPerformed(ActionEvent ev)
	                 {
		                  Button source=(Button)(ev.getSource());
                          if(!checkValidity(source)){return;}
                          if(button_count%2==0)
                          {source.setLabel("X");}
                          else{source.setLabel("O");}
                          ++button_count;
                          playClickSound();
                          lbc=Integer.parseInt(source.getActionCommand().charAt(2)+"");
                          int kpop;
                          String win=current_game.checkLocalWinner(kpop=Integer.parseInt(source.getActionCommand().charAt(0)+""));
                          if(!win.equals(" ")){global_table[kpop]=win;
                                               removeLocalBoardAt(kpop,win);
                                               String bruh=MetaTicTacToeNew.checkGlobalWinner(global_table);
                                               if(!bruh.equals(" ")){displayEnd(bruh+" Wins!");}
                            }
                           if(!isGBoardPlayable()){displayEnd("DRAW");} 
                     }
 };
 GameSaver.SerializableMouseListener m=new GameSaver.SerializableMouseListener(){
    private static final long serialVersionUID=334566981L;
    @Override 
    public void mouseEntered(MouseEvent me){
        Color c;
        Button b=(Button)me.getSource();
        boolean result= current_game.checkValidity(b);
        if(result){c=Color.GREEN;}
        else{c=Color.RED;}
        b.setBackground(c);}
    public void mouseExited(MouseEvent me){Button b=(Button)me.getSource();b.setBackground(Color.YELLOW);}
    };
 for(int k=0;k<9;++k)
 {
 local_boards[k]=new Panel();
 local_boards[k].setLayout(new GridLayout(3,3));
 for(int k1=0;k1<9;++k1)
 {
  Button b=new Button(" ");  
  b.setActionCommand(k+" "+k1);   
  b.setFont(new Font("algerian",Font.BOLD,30));
  b.setBackground(Color.YELLOW);
  b.addMouseListener(m);
  b.addActionListener(al);
  local_boards[k].add(b);
 }
 pp.add(local_boards[k]);
 }

 GLOBAL_BOARD.addWindowListener(new GameSaver.SerializableWindowListener(){
	 private static final long serialVersionUID=445588172L;
	 public void windowClosing(WindowEvent we)
	{
	current_game=null;
	GLOBAL_BOARD.setVisible(false);
    GLOBAL_BOARD.dispose();
    stopThemeSong();
    }});
 MenuBar mb=new MenuBar();
 Menu men=new Menu("Save/Load Game");
 this.initializeSaveMenu();
 men.add(save);
 men.add(load);
 Menu hlp=new Menu("Help");
 MenuItem rles=new MenuItem("Rules");
 rles.addActionListener(new GameSaver.SerializableActionListener() {
	private static final long serialVersionUID = -2548929485098548397L;

	@Override
	public void actionPerformed(ActionEvent ev) {
		ImageIcon mc[]=DependancyManager.loadIconBatch(43, 48);
		int[] cc=new int[] {10,15,26,30,32};
		displayRules(cc,mc,400,400);
	}
});
 hlp.add(rles);
 mb.add(men);
 mb.add(hlp);
 GLOBAL_BOARD.setMenuBar(mb);
 GLOBAL_BOARD.setSize(550,500);
 GLOBAL_BOARD.setResizable(Boolean.FALSE);
 GLOBAL_BOARD.setIconImage(ImageManager.mt_1);
 GLOBAL_BOARD.setVisible(Boolean.TRUE);
}
private boolean isLocalBoardFilled(int bno)
{
Component[] boxes=local_boards[bno].getComponents();
for(int k=0;k<9;++k)
{if(((Button)boxes[k]).getLabel().equals(" ")){return false;}}
return true;
}
 public static void main(String[] args)
 {
	 if(current_game!=null)
	 {
		 JOptionPane.showMessageDialog(null,"Please Close the previous board of meta tic tac toe, to start a new one","Error",JOptionPane.ERROR_MESSAGE);
		 return;
	}
 try{
 current_game=new MetaTicTacToeNew();   
 current_game.playThemeSong();
    }
 catch(Throwable ex)
 {ErrorManager em=new ErrorManager(new Frame("Error!"),"Issue in Meta Tic Tac Toe");
  em.display("Issue name: "+ex.getClass().getName(),ex);
    }  
 }
 private boolean checkValidity(Button source)
 {
	if(!source.getLabel().equals(" ")) {return false;} 
    String s=source.getActionCommand();
    int bhn=Integer.parseInt(s.charAt(0)+"");
    LAB:
    if((((bhn!=lbc)&&(!this.isLocalBoardFilled(lbc))))||(!source.getLabel().equals(" "))){
    if((button_count==0)||(!global_table[lbc].equals(" "))){break LAB;}
    return false;}
    return true;
 }
 protected static Button[] buttonConversionCycle(Component[] ar)
 {
    Button[] arr=new Button[ar.length];
    for(int k=0;k<arr.length;++k)
    {arr[k]=(Button)ar[k];}
    return arr;
 }
 @SuppressWarnings("resource")
private String checkLocalWinner(int board)
 {
 Button[] buttons=MetaTicTacToeNew.buttonConversionCycle(local_boards[board].getComponents());    
 String s="X";
 for(int k=0;k<2;++k)
 {
 for(int i=0;i<UNIVERSAL_WIN_COMBOS.length;++i)
 {
Scanner sr=new Scanner(UNIVERSAL_WIN_COMBOS[i]);
 boolean flag=true;
 for(int k1=0;k1<3;++k1)
 {if(!buttons[sr.nextInt()].getLabel().equals(s)){flag=false;break;}}
 if(flag){return s;} 

}   
s="O";    
    }   
 return " ";   
 }
 private void removeLocalBoardAt(int bnum,String win)
 {
   internalHolder.remove(bnum); 
   Label l;
   internalHolder.add(l=new Label("\t   "+win),bnum); 
   l.setFont(new Font("algerian",Font.BOLD,60));
   l.setBackground(Color.RED);
   internalHolder.validate();
 }
 protected void rapidInit()
 {global_table=new String[9];
  for(int k=0;k<global_table.length;++k){global_table[k]=" ";}   
    
 }
 @SuppressWarnings("resource")
public static String checkGlobalWinner(String[] table)
{
String p="O";
for(int i=0;i<2;i++)
{
CHECKER:
for(int j=0;j<UNIVERSAL_WIN_COMBOS.length;++j)
{
Scanner ssc=new Scanner(UNIVERSAL_WIN_COMBOS[j]);
boolean flag=true;
while(ssc.hasNextInt())
{
if(!table[ssc.nextInt()].equalsIgnoreCase(p))
{
 flag=false;
 continue CHECKER;
}

}
if(flag){return p;}
}
p="X";
}
return " ";
}
protected boolean isGBoardPlayable()
{
for(int k=0;k<9;++k)
{
Panel p=this.local_boards[k];
Button[] bb=buttonConversionCycle(p.getComponents());
for(int k1=0;k1<9;++k1)
{if(bb[k1].getLabel().equals(" ")){return true;}}
}
return false;
}
private void displayEnd(String msg)
{
 current_game=null;
 GLOBAL_BOARD.setVisible(false);
 GLOBAL_BOARD.dispose();
 this.stopThemeSong();
 Frame frame=new Frame(msg);
 Label l=new Label(msg);
 l.setFont(new Font("algerian",Font.BOLD,45));
 l.setBackground(Color.BLUE);
 l.setForeground(Color.GREEN);
 frame.setBackground(Color.YELLOW);
 frame.add(l);
 frame.setSize(200,500);
 frame.setResizable(false);
 frame.setVisible(true);
 frame.addWindowListener(new WindowAdapter(){
     @Override()
     public void windowClosing(WindowEvent we){frame.setVisible(false);
    frame.dispose();
    }});
}
//This method initializes the menu bars for save and load.
public void initializeSaveMenu()
{
this.save=new MenuItem("save");	
this.load=new MenuItem("load");
GameSaver.SerializableActionListener ar=new GameSaver.SerializableActionListener() {
	private static final long serialVersionUID=5553445617L;
	@Override
	public void actionPerformed(ActionEvent ev) {
		MenuItem src=(MenuItem)(ev.getSource());
		GameSaver obj=new GameSaver();
		if(src==save)
		{obj.applySaveGUI(MetaTicTacToeNew.current_game,ImageManager.mt_1);}
		else
		{obj.applyOutputGUI(getName(), true, ImageManager.mt_1, ImageManager.mt_1);
		 GLOBAL_BOARD.setVisible(false);
		 GLOBAL_BOARD.dispose();
		 stopThemeSong();
		 Thread th=new Thread() 
		 {
		 public void run() 
		 {
		while(obj.object==null) 
		 {}
		MetaTicTacToeNew.current_game=(MetaTicTacToeNew)obj.object;
		if(current_game.getMusicControl().isSelected())
		{current_game.playThemeSong();}
		 }
		 };
		th.start();
		}
	}	
 };
this.save.addActionListener(ar);
this.load.addActionListener(ar);
}
@Override
public String getName() 
{
	return "MetaTicTacToeNew";
}
@Override
public Component getMainFrame() 
{
	return this.GLOBAL_BOARD;
}
@Override
public File getRules()
{
return DependancyManager.loadDependancy(48);	
}
}
//END OF SIDE PROJECT