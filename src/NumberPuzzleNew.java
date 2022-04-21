import java.util.*;
import java.time.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.event.*;
import javax.swing.*;
import utilities.SavableArcadeGame;
import utilities.GameSaver;
import utilities.MusicalArcadeGame;
import utilities.Playable;
@SuppressWarnings("unused")
public final class NumberPuzzleNew extends MusicalArcadeGame implements SavableArcadeGame,java.io.Serializable,Playable
{
 private static final long serialVersionUID=4005L;
 private static volatile NumberPuzzleNew ongoingGame;
 private volatile boolean end=false;
 private TimerThread state;
 private Frame puzzle;
 private Label counter;
 private Label timer;
 private Button[][] buttons;
 private Panel box;
 public class TimerThread extends GameSaver.SerializableThread implements java.io.Serializable 
 {   private static final long serialVersionUID=22222222222L;
	 @Override()
	 public void run()
	 {
		 for(;;)
		 {
         if(end) {break;}  		 
	     String s=timer.getText();
		 try {
		 Thread.sleep(1000);
		 }
		 catch(InterruptedException ex)
		 {}
		Scanner timere=new Scanner(s);
		int min=timere.nextInt();
		timere.next();
		timere.next();
		int sec=timere.nextInt();
		timere.close();
		++sec;
		if(sec==60)
		{sec=0;++min;}
		timer.setText(min+" Minutes : "+sec+" Seconds");
		 }
		
		 }
 }
 public NumberPuzzleNew() 
 {
super(DependancyManager.loadDependancy(17));
puzzle=new Frame("Number puzzle");	
box=new Panel();
MenuBar mb=new MenuBar();
Menu m=new Menu("Save/Load game");
Menu hlp=new Menu("Help");
MenuItem rles=new MenuItem("Rules");
rles.addActionListener(new GameSaver.SerializableActionListener() {
	private static final long serialVersionUID = 199999234001L;

	@Override
	public void actionPerformed(ActionEvent ev)
	{
	ImageIcon[] ic=DependancyManager.loadIconBatch(38, 42);	
	int[] xs=new int[] {9,18,22,30};
	displayRules(xs,ic,600,600);
	}
});
hlp.add(rles);
MenuItem mt=new MenuItem("Save game");
MenuItem mt1=new MenuItem("Load game");
m.add(mt);
m.add(mt1);
mb.add(m);
mb.add(hlp);
puzzle.setMenuBar(mb);
mt.addActionListener(new GameSaver.SerializableActionListener() 
{
	private static final long serialVersionUID=56009875670L;
public void actionPerformed(ActionEvent ae)	
{
if(end) {return;}
GameSaver saver=new GameSaver();	
saver.applySaveGUI(ongoingGame,ImageManager.np_1);
}
});
mt1.addActionListener(new GameSaver.SerializableActionListener()
		{
	    private static final long serialVersionUID=34445767991L;
		@Override
		public void actionPerformed(ActionEvent ae)
		{
			
			GameSaver gs=new GameSaver();
			gs.applyOutputGUI(getName(), true,ImageManager.np_1,ImageManager.np_1);
			puzzle.setVisible(false);
			puzzle.dispose();
			stopThemeSong();
			Thread t=new Thread() {
				public void run()
				{while(gs.object==null) {}
				NumberPuzzleNew.ongoingGame=(NumberPuzzleNew)gs.object;
				NumberPuzzleNew.ongoingGame.state.start();
				if(ongoingGame.music.isSelected())
				{ongoingGame.playThemeSong();}
				}
			};
			t.start();
		}
		});
//add Action Listener
timer=new Label("0 Minutes : 0 Seconds");
timer.setFont(new Font("algerian",Font.BOLD,20));
counter=new Label("0 Moves");
counter.setFont(new Font("algerian",Font.BOLD,20));
puzzle.setResizable(false);  
puzzle.addWindowListener(new GameSaver.SerializableWindowListener() {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public void windowClosing(WindowEvent we) 
	{
	puzzle.setVisible(false);
	puzzle.dispose();
	stopThemeSong();
	}
});
box.setLayout(new GridLayout(4,4,1,1));
buttons=new Button[4][4];
int c=1;
	GameSaver.SerializableMouseListener ml=new GameSaver.SerializableMouseListener() {
	private static final long serialVersionUID = 2479978966327056500L;
	@Override
	public void mouseEntered(MouseEvent ve)
	{
	if(end) {return;}
	Button b=(Button)(ve.getSource());
	if(b.getLabel().equals("")) {return;}
	String s=b.getActionCommand();
	Scanner sc=new Scanner(s);
	int n1=sc.nextInt();
	int n2=sc.nextInt();
	sc.close();
	if(ongoingGame.isAdjacentEmpty(n1, n2)!=null)
	{ongoingGame.buttons[n1][n2].setBackground(Color.GREEN);}
	else
	{ongoingGame.buttons[n1][n2].setBackground(Color.RED);}
	}
	@Override
	public void mouseExited(MouseEvent me)
	{
	if(end) {return;}
	Button b=(Button)(me.getSource());
	if(b.getLabel().equals(""))
	{return;}
	b.setBackground(UIManager.getColor("Button.background"));
	}
};
GameSaver.SerializableActionListener ar=new GameSaver.SerializableActionListener(){
	
	private static final long serialVersionUID = -4099347249831170349L;

	public void actionPerformed(ActionEvent ve)
	{
  if(end) {return;}
  boolean flag=false;
  String emp="";
  Scanner sc=new Scanner(ve.getActionCommand());
  int c1=sc.nextInt();
  int c2=sc.nextInt();
  sc.close();
  emp=isAdjacentEmpty(c1,c2);
  if(emp!=null) {flag=true;}
  if(flag) {
	playClickSound();
	String t=buttons[c1][c2].getLabel();
	buttons[c1][c2].setLabel("");
	buttons[c1][c2].setBackground(Color.WHITE);
	Scanner scc=new Scanner(emp);
	int x1,x2;
	buttons[x1=scc.nextInt()][x2=scc.nextInt()].setLabel(t);
	buttons[x1][x2].setBackground(UIManager.getColor("Button.background"));
	scc.close();
	Scanner sest=new Scanner(counter.getText());		
	 counter.setText(""+ (1+(sest.nextInt()))+" Moves");
	 sest.close();
	if(gameOver(buttons)) {end=true;displayResults();}
}  
    
	}
};
FINAL:
for(int i=0;i<4;++i)
{
for(int j=0;j<4;++j)	
{
if(i==3&&j==3) {break FINAL;}	
buttons[i][j]=new Button(c+"");
buttons[i][j].addActionListener(ar);
buttons[i][j].addMouseListener(ml);
buttons[i][j].setActionCommand(i+" "+j);
buttons[i][j].setFont(new Font("algerian",Font.BOLD,20));
box.add(buttons[i][j]);
++c;
}
}
buttons[3][3]=new Button("");
buttons[3][3].addActionListener(ar);
buttons[3][3].addMouseListener(ml);
buttons[3][3].setActionCommand(3+" "+3);
buttons[3][3].setFont(new Font("algerian",Font.BOLD,20));
box.add(buttons[3][3]);
shuffle(buttons);
makeBlank(buttons);
puzzle.add(box);
puzzle.add(super.box,BorderLayout.EAST);
puzzle.add(counter,BorderLayout.NORTH);
puzzle.add(timer,BorderLayout.SOUTH);
puzzle.setSize(400,350);
puzzle.setIconImage(ImageManager.np_1);
puzzle.setVisible(true);
}
	public static void main(String[] args) 
	{
		Thread obj=new Thread() {
			
		public void run() {
	    try{
    ongoingGame=new NumberPuzzleNew();
    ongoingGame.playThemeSong();
    ongoingGame.state=ongoingGame.new TimerThread();
    ongoingGame.state.run();
              }
              catch(Throwable obj)
              {ErrorManager em=new ErrorManager(new Frame(""),"Number puzzle error");
                em.display("Number Puzzle error",obj);
                }
		}
		};
		obj.start();
	}
	private static void makeBlank(Button[][] arrays)
	{
		for(int i=0;i<arrays.length;++i)
		{
		for(int j=0;j<4;j++)	
		{
			if(arrays[i][j].getLabel().equals(""))
			{arrays[i][j].setBackground(Color.WHITE);return;}
		}	
		}
	}
   private static void shuffle(Button[][] array)
   {
  int a1,a2;
  a1=a2=3;
  int rand=80+(int)(Math.random()*30);
  //int rand=3;//Comment out later.
  for(int i=0;i<rand;++i)
  {
	ArrayList<String> var=new ArrayList<>(0);  
	String[] arr=new String[] {a1+" "+(a2+1),a1+" "+(a2-1),(a1+1)+" "+a2,(a1-1)+" "+a2}; 
	for(int k=0;k<arr.length;++k)  
	{ Scanner sss=new Scanner(arr[k]);
		try {(array[sss.nextInt()][sss.nextInt()]).getLabel();
		var.add(arr[k]);
		}
		catch(IndexOutOfBoundsException ex) {}
		finally {sss.close();}
	}	
	int stuff=(int)(Math.random()*var.size());
	String the=(String)(var.get(stuff));
	Scanner bruh=new Scanner(the);
	int n1=bruh.nextInt();
	int n2=bruh.nextInt();
	bruh.close();
	array[a1][a2].setLabel(array[n1][n2].getLabel());
	array[n1][n2].setLabel("");	
	a1=n1;
	a2=n2;
	
  }  
	   
   }
   public String isAdjacentEmpty(int c1,int c2)
   {
	   
	   String emp=null;
	   try
   {
	   if(buttons[c1][c2+1].getLabel().isEmpty())	  
	   {
	    emp=c1+" "+(1+c2);
	   }	  
	    }
	     catch(IndexOutOfBoundsException ex)
	     {}
	     try
	     {
	   if(buttons[c1][c2-1].getLabel().isEmpty())	  
	   {
	    emp=c1+" "+(c2-1);
	   }	  
	     }
	     catch(IndexOutOfBoundsException ex)
	     {}
	     try
	     {
	   if(buttons[c1+1][c2].getLabel().isEmpty())	  
	   {
	    emp=(1+c1)+" "+c2;
	   }	  
	    }
	     catch(IndexOutOfBoundsException ex)
	     {}
	     try
	     {
	   if(buttons[c1-1][c2].getLabel().isEmpty())	  
	   {
	    emp=(c1-1)+" "+c2;
	   }	  
	    }
	     catch(IndexOutOfBoundsException ex)
	     {}
	   return emp;}
   private static final boolean gameOver(Button[][] board)
   {  
   int c=1;
   BRUH:
	   for(int i=0;i<4;i++)
	   {
		  for(int j=0;j<4;++j) 
		  { if(i==3&&j==3) {break BRUH;}
			  if(!board[i][j].getLabel().equals(c+"")) {return false;}
			  ++c;
		  }
		   
	   }
   if(!board[3][3].getLabel().equals("")) {return false;}
	   return true;
   }
   public void displayResults() 
   {puzzle.setVisible(false);
    puzzle.dispose();
    stopThemeSong();
    while(state.isAlive())
    {
    ;
    }
    Frame f=new Frame("PUZZLE SOLVED");
    Label l=new Label("You have finished solving the puzzle");
    f.add(l);
    l.setFont(new Font("algerian",Font.BOLD,32));
    l.setBackground(Color.BLUE);
    Label l1=new Label("Time taken= "+timer.getText());
    Label l2=new Label("Hits: "+counter.getText());
    l1.setFont(new Font("algerian",Font.BOLD,20));
    l2.setFont(new Font("algerian",Font.BOLD,20));
    f.addWindowListener(new GameSaver.SerializableWindowListener() {
    	/**
		 * serialversionUID
		 */
		private static final long serialVersionUID = 133333L;

		public void windowClosing(WindowEvent we)
    	{f.setVisible(false);
    	 f.dispose();
    	}
    });
    f.add(l1,BorderLayout.NORTH);
    f.add(l2,BorderLayout.SOUTH);
    f.setSize(600,200);
    f.setResizable(false);
    try
   { f.setIconImage(ImageManager.qa_1);}
   catch(Throwable e){}
    f.setVisible(true);
   }
@Override
public String getName() {
	
	return "NumberPuzzleNew";
}
@Override
public Component getMainFrame() {
	
	return this.puzzle;
}
@Override
public Optional<SavableArcadeGame> returnCurrentGame()
{return Optional.ofNullable(NumberPuzzleNew.ongoingGame);}
@Override
public File getRules() {
	return DependancyManager.loadDependancy(42);
}
}