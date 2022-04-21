import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.Timer;
import utilities.*;
/*
 * MineSweeper is a game which implements the classic
 * microsoft windows MineSweeper game.MineSweeper is 
 * Independent of Arcade3's Image management and error
 * management mechanics,it handles all this by itself.
 * The save mechanics are NOT independent of Arcade3's save
 * management ..Therefore, Minesweeper CANNOT be 
 * deployed seperatey,Unless save feature is removed
 * or restructured.
 * Difficulty levels:Beginner has a total of ten mines
 * and the board size is 9 × 9 
 * Intermediate has 40 mines and  13 × 15 
 * Finally, expert has 99 mines and is always 30 × 16 
 * AUTHOR:Nikhil Narayanan
 * Version:1.0.0
 * Part of:ARCADE 3
 * Dependencies: Images in Dependencies/MineSweeper folder.
 * is checked at runtime by Dependency manager
 */
@SuppressWarnings("unused")
public final class MineSweeper implements java.io.Serializable,SavableArcadeGame,Playable,AutoMusicalGame {
public MineSweeper current_game;//Holds the value of ongoing game object
public static ImageIcon mine_icon;
protected boolean game_ended;//This field holds false if game is going on and true if game has ended.	
protected boolean game_won;//The value is true if the game ends in a victory, and false if the game ends in a loss.
private static final long serialVersionUID=2134445677L;//The SVUID used for serialization of the obj
private JFrame board;//main board
private JPanel viewpor;//Main view within JFrame
private JButton[][] grid_boxes;//represents all boxes in the grid
private JPanel side_panel;//Container to hold the flag, question mark buttons,etc
private JCheckBox flag;//Two state flag button
private JCheckBox question_mark;//Two state qmark button
private JCheckBox uncover;//Two state button to uncover a gridbox
private ButtonGroup group;//Button grp to hold two state buttons.
private JLabel info;//prompts user to see components tooltips.
private JLabel timer;//holds the time in timer.
private JLabel mine_disp;//Holds the number of mines in the board as a display.
private JMenuBar mb;//represents the menu bar of main frame.
protected int t_mines;//represents number of mines in board
protected int rows_num;//represents rows in board
protected int cols_num;//represents columns in board
protected int[][] mine_indices;//holds DDA of mine indices in the form{{rowp,colp},{rowp1,colp1}...}
public final ImageIcon flg;//Holds the image of a flag used for flagging mines
public final ImageIcon qmark;//Holds the image of a qmark
public final ImageIcon mine;//Holds the image of a mine
public final ImageIcon blank;//Holds the cover image for cells
public final ImageIcon exp_mine;//Holds the image of an exploded mine
public static File msong;//theme song of mine sweeper(classic-1987)
public static File explosion;//Holds the explosion sound
public static File click_sound;//Holds the click sound
public ImageIcon uncovered;//Holds the icon set for a cleared cell
private SerializableTimer tep;//Holds Timer
private StopWatch stp;//Holds required timer Task.
private transient Clip cc;//Holds the clip which plays the MineSweeper theme song
private transient volatile ArrayList<Integer[]> clear_queue;//Holds the queue of buttons to cleared
public JCheckBox music;
private JCheckBox sfx;
private transient int xsp;//Holds temp x value
private transient int ysp;//Hods temp y value
//instance initialization block
{
flg=new ImageIcon(DependancyManager.getDependancy(0));	
qmark=new ImageIcon(DependancyManager.getDependancy(2));
mine=new ImageIcon(DependancyManager.getDependancy(1));
blank=new ImageIcon(DependancyManager.getDependancy(3));
exp_mine=new ImageIcon(DependancyManager.getDependancy(7));
crop(flg,60,70);
crop(qmark,60,70);
}
static 
{
	MineSweeper.mine_icon=new ImageIcon(DependancyManager.getDependancy(1));
    MineSweeper.msong=DependancyManager.loadDependancy(6);
    MineSweeper.click_sound=DependancyManager.loadDependancy(4);
    MineSweeper.explosion=DependancyManager.loadDependancy(5);
}
//The main method which calls MineSweeper's constructor on The swing thread.
public static void main(String[] args)
{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run()
			{ 
				try {
				MineSweeper.chooseDifficulty();
			       }
			   catch(Throwable obj)
			    {
				 ErrorManager er=new ErrorManager(new Frame("Error"),"Error in MineSweeper");  
				 er.display("Error in MineSweeper, please cotact developer",obj);  
			    }
			}
			
			
		});
}
//Prepares a rows*cols minesweeper boards by initializing all fields and displaying 
//board
public MineSweeper(int rows,int cols,int mines)
{
	current_game=this;
	board=new JFrame("Play MineSweeper");
	viewpor=new JPanel();
	viewpor.setLayout(new GridLayout(rows,cols));
	side_panel=new JPanel();
	side_panel.setLayout(new BoxLayout(side_panel,BoxLayout.Y_AXIS));
	ButtonGroup bg=new ButtonGroup();
	uncover=new JCheckBox("UNCOVER OFF");
	uncover.setBackground(Color.YELLOW);
	uncover.setFont(new Font("algerian",Font.BOLD,20));
	uncover.addItemListener(new GameSaver.SerializableItemListener() {
		private static final long serialVersionUID=2348990000L;
		@Override
		public void itemStateChanged(ItemEvent ae) {
			if(ae.getStateChange()==ItemEvent.SELECTED)
			{uncover.setBackground(Color.GREEN);
			 uncover.setText("UNCOVER ON");
			}
			else
			{
				uncover.setBackground(Color.YELLOW);
				uncover.setText("UNCOVER OFF");
			}
		}
	});
	String tt1="<html>Clicking on this button will lead to the uncovering of<br>"
			+ "cells you click now, if the cell contains a mine<br>"
			+ "you lose, so use with caution.<br>To switch this property off,click flag/qmark option";
	uncover.setToolTipText(tt1);
	bg.add(uncover);
	flag=new JCheckBox("FLAG OFF",flg);
	flag.setHorizontalTextPosition(SwingConstants.CENTER);
	flag.setBackground(Color.YELLOW);
	flag.setFont(new Font("algerian",Font.BOLD,20));
	flag.addItemListener(new GameSaver.SerializableItemListener() 
	{private static final long serialVersionUID=2009835567L;
		public void itemStateChanged(ItemEvent ae)
		{
			if(ae.getStateChange()==ItemEvent.SELECTED)
			{
			 flag.setBackground(Color.GREEN);
			 flag.setText("FLAG ON");
			}
			else
			{
			flag.setBackground(Color.YELLOW);
			flag.setText("FLAG OFF");
			}
		}
		
	});
	String ttp="<html>Clicking on this button will cause any cell<br>"
			+ "clicked afterwards to be marked as a mine.<br>"
			+ "This feature can be turned off by clicking on another option only<br>"
			+"to clear a flagged cell,turn on uncover<br>and click on it again."
			;
	flag.setToolTipText(ttp);
	bg.add(flag);
	question_mark=new JCheckBox("QUESTION MARK IS OFF",qmark);
	question_mark.setForeground(Color.BLUE);
	question_mark.setHorizontalTextPosition(SwingConstants.CENTER);
	question_mark.setBackground(Color.YELLOW);
	question_mark.setFont(new Font("algerian",Font.BOLD,20));
	question_mark.addItemListener(new GameSaver.SerializableItemListener() {
		private static final long serialVersionUID=234555244139L;
		@Override
		public void itemStateChanged(ItemEvent ev) {
			if(ev.getStateChange()==ItemEvent.SELECTED)
			{question_mark.setBackground(Color.GREEN);
			 question_mark.setText("QUESTION MARK IS ON");
			}
			else
			{
				question_mark.setBackground(Color.YELLOW);
				question_mark.setText("QUESTION MARK IS OFF");
			}
		}
	});
	String ttp1="<html>Click this button to switch on qmark cursor.<br>"
			+ "This means that any cell you click on now will be<br>"
			+ "marked witha question mark, indicating<br>"
			+ "you arent sure whether its a mine.<br>"
			+ "to turn this property off, click the flag/uncover option"
			+"to clear a question marked cell,turn<br> on uncover<br>and click on it again."
			;
	question_mark.setToolTipText(ttp1);
	bg.add(question_mark);
	uncover.setSelected(true);
	info=new JLabel("<html>Feeling confused with the controls?<br>Hover your mouse above a particular<br>control for a tip<br>SAFE FIRST MOVE:<br>Row: ");
	grid_boxes=new JButton[rows][cols];
	ActionListener ar=new GameSaver.SerializableActionListener() {
		private static final long serialVersionUID=234444112900L;
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			if(game_ended) {return;}
			JButton gridbox=(JButton)(ev.getSource());
			String s=gridbox.getActionCommand();
			Scanner sc=new Scanner(s);
			int x1=sc.nextInt();
			int y1=sc.nextInt();
			String tok=sc.next();
			sc.close();
			if(isCleared(x1, y1)) {return;}
			if(sfx.isSelected())
			{MineSweeper.playClickSound();}
			if(uncover.isSelected())
			{
				if(gridbox.getIcon()==flg||gridbox.getIcon()==qmark)
				{
				 gridbox.setIcon(blank);
				 return;
				}
			if(tok.equals("M")) 
			{
			xsp=x1;
			ysp=y1;
			endGame(false);
			//Added method calls in endGame() here which makes mines in the board explode graphically
			return;
			}
			Thread t=new Thread() {
			public void run()
			{
				clearAdjacentTiles(x1,y1);
				if(isboardUncovered()) {endGame(true);}	
			}
			};
			t.start();
			//continue from here
			}
			else if(flag.isSelected())
			{
		gridbox.setIcon(flg);
		Thread chk=new Thread()
		{
				public void run()
			 {
			if(isboardUncovered()) 
			  {
				endGame(true);
			  }
			 }
		};
		chk.start();
			}
			else
			{gridbox.setIcon(qmark);}
			
		}
			
	};
		
	for(int i=0;i<rows;++i)
	{
		for(int j=0;j<cols;++j)
		{
			grid_boxes[i][j]=new JButton("");
			grid_boxes[i][j].setActionCommand(i+" "+j+" "+0);
			grid_boxes[i][j].setForeground(Color.RED.darker());
			grid_boxes[i][j].setIcon(blank);
			grid_boxes[i][j].setFont(new Font("algerian",Font.PLAIN,20));
			grid_boxes[i][j].setToolTipText("<html>Clicking here will result in<br>uncovering what's under this cell<br>Careful!If there's a mine here, you will lose.<br>if you are unsure,it is advisable to press the FLAG button and<br>then click here, to flag this spot as a mine.<br>You can also use qmark to signify<br>you dont know what this cell is");
			grid_boxes[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
			grid_boxes[i][j].addActionListener(ar);
			viewpor.add(grid_boxes[i][j]);
		}
		 
	}
    t_mines=mines;
    rows_num=rows;
    cols_num=cols;
    setMines();
    timer=new JLabel("00:00");
    timer.setForeground(Color.BLUE.darker());
    timer.setFont(new Font("algerian",Font.BOLD,35));
    timer.setToolTipText("<html>This is a timer which shows how much time you're<br>taking to solve the puzzle in the format<br>Minutes:Seconds");
    mine_disp=new JLabel("Number of mines: "+t_mines);
    mine_disp.setFont(new Font("algerian",Font.BOLD,20));
    mine_disp.setForeground(Color.RED.darker());
    mine_disp.setToolTipText("This label holds the number of mines in the board.");
    initializeSoundControls();
    //components added here
    side_panel.add(flag);
    side_panel.add(question_mark);
    side_panel.add(uncover);
    side_panel.add(info);
    side_panel.add(timer);
    side_panel.add(mine_disp);
    side_panel.add(music);
    side_panel.add(sfx);
    board.add(side_panel,BorderLayout.EAST);
    board.add(viewpor);
    this.mb=new JMenuBar();
    JMenu men=new JMenu("Save/Load game");
    men.setToolTipText("Contains options to save/load a previous game");
    JMenuItem itm=new JMenuItem("Save game");
    itm.setToolTipText("Click to save current game");
    JMenuItem itm1=new JMenuItem("Load game");
    itm1.setToolTipText("Click to load a previous game.");
    men.add(itm);
    men.add(itm1);
    mb.add(men);
    ActionListener arls=new GameSaver.SerializableActionListener() 
    {

		//Add save/load gui code here
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent ev) 
		{
			JMenuItem src=(JMenuItem)(ev.getSource());
			GameSaver opt=new GameSaver();
			if(src==itm)
			{
			if(game_ended) {return;}
			opt.applySaveGUI(current_game,ImageManager.msw_1);	
			}
			else
			{
				opt.applyOutputGUI(getName(), true,mine.getImage(),mine.getImage());
				board.setVisible(false);
				board.dispose();
				stopThemeSong();
				Thread tea=new Thread() 
				{
					public void run()
					{
						while(opt.object==null) {}
						MineSweeper p=(MineSweeper)(opt.object);
						p.tep.schedule(p.stp, 1000, 1000);
						if(p.music.isSelected())
						{p.startThemeSong();}
					}
				};
				tea.start();
			}
		}
		
    };
    itm.addActionListener(arls);
    itm1.addActionListener(arls);
    JMenu hlp=new JMenu("Help");
    JMenuItem rles=new JMenuItem("Rules");
    rles.addActionListener(new GameSaver.SerializableActionListener() {
		private static final long serialVersionUID = 10998761221L;

		@Override
		public void actionPerformed(ActionEvent ev)
		{
		ImageIcon[] icns=DependancyManager.loadIconBatch(49,57);	
		int[] x=new int[] {9,16,25,29,38,42,51,54};	
		displayRules(x,icns,650,650);
		}
	});
    hlp.add(rles);
    mb.add(hlp);
    board.setJMenuBar(mb);
    board.setSize(600,600);
    board.setExtendedState(JFrame.MAXIMIZED_BOTH);
    board.setIconImage(mine.getImage());
    board.setResizable(false);
    //flagMines();//test call
    int[] s=this.safePlay();
    info.setText(info.getText()+ ++s[0] +"<br>Column "+ ++s[1]);
    board.addWindowListener(new GameSaver.SerializableWindowListener() 
    {
    	private static final long serialVersionUID=3266178901L;
    	@Override
    	public void windowClosing(WindowEvent we)
    	{
    		stopThemeSong();
    	}
    });
    startThemeSong();
    board.setVisible(true);
    board.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    crop(mine,grid_boxes[0][0]);
    crop(flg,grid_boxes[0][0]);
    crop(qmark,grid_boxes[0][0]);
    crop(exp_mine,grid_boxes[0][0]);
    setClearedIcon();
    //startPlaying();//test call
    //The following are test method calls and must usually be commented out, unless testing app
    //showBoard();//test call
    // displayMineIndices();//test call
   this.tep=new SerializableTimer(true);
   this.tep.schedule(stp=new StopWatch(),1000,1000);
}
//sets mines in the board, is called in constructor,should
//be called only after grid_boxes is initialized with default values
//and fields t_mines,rows_num,cols_num are set.
public void setMines()
{
Random num_gen=new Random();	
mine_indices=new int[t_mines][2];
for(int i=0;i<t_mines;++i)
{
	int x1,y1;
	do 
	{
	x1=num_gen.nextInt(rows_num);
	y1=num_gen.nextInt(cols_num);
	}
	while(contains(new int[] {x1,y1}));
	mine_indices[i][0]=x1;
	mine_indices[i][1]=y1;
	JButton but=grid_boxes[x1][y1];
	but.setActionCommand(x1+" "+y1+" M");
	setUpMarkers(x1,y1);
	}

}
//This method sets up the numbers which are indicative of mines
public void setUpMarkers(int x,int y)
{
	ArrayList<Integer[]> indices=getAdjacentIndices(x,y);
    for(Integer[] obj:indices)
    {
    	JButton c_cell=grid_boxes[obj[0]][obj[1]];
    	increaseCount(c_cell);
    }

}
//Gets the 8 adjacent grid boxes to the current grid box.
//This method is required while setting up markers,
//and is called in setUpMarkers(int,int)
public ArrayList<Integer[]> getAdjacentIndices(int r,int c)
{
ArrayList<Integer[]> array=new ArrayList<>(0);	
if(isValid(r+1,c+1))
{array.add(new Integer[] {r+1,c+1});}
if(isValid(r+1,c))
{array.add(new Integer[] {r+1,c});}
if(isValid(r+1,c-1))
{array.add(new Integer[] {r+1,c-1});}
if(isValid(r-1,c+1))
{array.add(new Integer[] {r-1,c+1});}
if(isValid(r-1,c))
{array.add(new Integer[]{r-1,c});}
if(isValid(r-1,c-1))
{array.add(new Integer[] {r-1,c-1});}
if(isValid(r,c+1))
{array.add(new Integer[] {r,c+1});}
if(isValid(r,c-1))
{array.add(new Integer[] {r,c-1});}
	return array;
}
//This method increases the number in the given grid box indicating +1 to 
//total number of mines placed close to a grid box
public boolean increaseCount(JButton gridbox)
{
	String s=gridbox.getActionCommand();
	Scanner reader=new Scanner(s);
	int x1=reader.nextInt();
	int y1=reader.nextInt();
	String m=reader.next();
	reader.close();
	if(m.equals("M")) {return false;}
	m=(Integer.parseInt(m)+1)+"";
	gridbox.setActionCommand(x1+" "+y1+" "+m);
	return true;
	}
//This method checks if a particular co-ordinate is valid.
public boolean isValid(int x,int y)
{
if((x<rows_num)&&(y<cols_num)&&(x>=0)&&(y>=0))
{return true;}
return false;
}
//This method shows the contents of the particular Grid box
public void show(JButton butt)
{
	if(butt.getIcon()==uncovered||butt.getIcon()==mine)
	{
		//System.out.println("UNCOVERED REATTEMPT at "+butt.getActionCommand());//Usually comment this out
		return;
	}
	Scanner re=new Scanner(butt.getActionCommand());
	re.next();
	re.next();
	String s;
	s=re.next();
	re.close();
	butt.setText(s);
	butt.setIcon(uncovered);
	if(s.equals("M")) {butt.setIcon(mine);}
}
//This method displays the contents of all grid boxes on the board.
public void showBoard()
{
	for(int k=0;k<rows_num;++k)
	{
		
		for(int j=0;j<cols_num;++j)
		{
			show(grid_boxes[k][j]);
			
		}
     }
}
//Crops the specified icon to fit into the component and returns the edited icon(refrence is not changed)
public static ImageIcon crop(ImageIcon img,JComponent cont)
{
Image img1=img.getImage().getScaledInstance(cont.getWidth(),cont.getHeight(),Image.SCALE_SMOOTH);	
img.setImage(img1);
return img;
}
//Crops the specified icon to width=w,hieght=h
public static ImageIcon crop(ImageIcon img,int w,int h)
{
Image img1=img.getImage().getScaledInstance(w,h,Image.SCALE_SMOOTH);	
img.setImage(img1);
return img;
}
//This method displays the positions of mines on system console,is used while testing the app.
public void displayMineIndices()
{
for(int i=0;i<mine_indices.length;++i)	
{
	
System.out.println("ROW: "+(mine_indices[i][0]+1) +" COLUMN: "+(1+mine_indices[i][1]));

}

}
//This method is useful for  checking whether a specefic index has already been specified for a mine
public boolean contains(int[] obj)
{
	for(int i=0;i<mine_indices.length;++i)
	{
	 int[] arr=mine_indices[i];
	 if(arr==null) {break;}
	 if((arr[0]==obj[0])&&(arr[1]==obj[1]))
	 {return true;}
	}
	return false;
}
//This method removes any indices which were cleared from the output generated by
//the getAdjacentIndices() method.
protected ArrayList<Integer[]> removeClearedIndices(ArrayList<Integer[]> indices,boolean val)
{	
indices.removeAll(Collections.singleton(null));
for(int k=0;k<indices.size();++k)
{
	Integer[] arr=indices.get(k);
	JButton but=grid_boxes[arr[0]][arr[1]];
	Icon f=but.getIcon();
    if((isCleared(arr[0],arr[1]))||(val&&((f==flg)||(f==qmark)))) 
    {indices.remove(k);
    --k;
    }//Exception should occur here-SOLVED
}
indices.removeAll(Collections.singleton(null));
return indices;
}
//This method checks if a cell is uncovered or not.
public boolean isCleared(int x,int y)
{
JButton ass=grid_boxes[x][y];
if(ass.getIcon()==uncovered)
{return true;}
return false;
}
//This method clears the 8 tiles surrounding the tile passed to the method,does not use recursive method calls
public void clearAdjacentTiles(int x,int y)
{
JButton butt=grid_boxes[x][y];
StringTokenizer st=new StringTokenizer(butt.getActionCommand());
st.nextToken();
st.nextToken();
int num=Integer.parseInt(st.nextToken());
butt.setText(num+"");
butt.setIcon(uncovered);
if(num!=0) 
{
return;
}
clear_queue=new ArrayList<>(0);
clear_queue.addAll(removeClearedIndices(getAdjacentIndices(x, y),true));
while(clear_queue.size()>0)
{
Integer[] ob=clear_queue.get(0);
butt=grid_boxes[ob[0]][ob[1]];
st=new StringTokenizer(butt.getActionCommand());
st.nextToken();
st.nextToken();
num=Integer.parseInt(st.nextToken());
butt.setText(num+"");
butt.setIcon(uncovered);
if(num!=0) {}
else
{clear_queue.addAll(removeClearedIndices(getAdjacentIndices(ob[0],ob[1]),true));}
 clear_queue.remove(0);
}
}
//This method creates the icon required for a cleared grid box
private void setClearedIcon()
{
Image im=board.createImage(200, 200);	
Graphics temp=im.getGraphics();
temp.setColor(new Color(0.3F,(float)Math.random(),(float)Math.random()));
temp.fillRect(0, 0, 200, 200);
uncovered=new ImageIcon(im);
}
//This method flags all mines from the beginning of the game-USE ONLY FOR TESTING
private void flagMines()
{
flag.setSelected(true);	
for(int k=0;k<t_mines;++k)
{
  	int x=mine_indices[k][0];
  	int y=mine_indices[k][1];
    grid_boxes[x][y].doClick();
}
uncover.setSelected(true);
}
//This method clears off the board-USE ONLY WITH setFlags()method for testing
private void startPlaying()
{
this.uncover.setSelected(true);	
for(int i=0;i<this.rows_num;i++)	
{
	
for(int j=0;j<this.cols_num;j++)
{
	
JButton gbox=this.grid_boxes[i][j];
if(gbox.getIcon()!=this.flg)
{
	gbox.doClick();
}

}

}
}
//This method should be called the moment the game ends as it sets the value of the required fields.
public void endGame(boolean won)
{
this.stopThemeSong();
this.game_ended=true;
this.music.setEnabled(false);
this.sfx.setEnabled(false);
this.uncover.setEnabled(false);
this.flag.setEnabled(false);
this.question_mark.setEnabled(false);
this.tep.cancel();
ImageIcon f1=new ImageIcon(DependancyManager.getDependancy(0));
ImageIcon m1=new ImageIcon(DependancyManager.getDependancy(1));
JFrame frm=new JFrame("RESULTS!!");
JLabel in=new JLabel("");
in.setFont(new Font("algerian",Font.BOLD,30));
if(won)
{
	in.setText("<html>YOU WON!!<br>Congrats on disarming all mines!!");
    in.setForeground(Color.GREEN);
    in.setIcon(f1);
    frm.setIconImage(f1.getImage());
}
else
{
	Thread t=new Thread() 
	{
		public void run()
		{
	playExplosionSequence(xsp, ysp);
	in.setText("<html>You Lost.<br>Better Luck <br>next time!!");
    in.setForeground(Color.BLUE);
    in.setIcon(m1);
    frm.setIconImage(m1.getImage());
    showBoard();
    frm.setVisible(true);
    crop(f1,in);
    crop(m1,in);
        }
	};
	t.start();
}
in.setVerticalTextPosition(SwingConstants.CENTER);
in.setHorizontalTextPosition(SwingConstants.CENTER);
frm.add(in);
frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
frm.setSize(250, 250);
frm.setResizable(false);
if(won)
{
frm.setVisible(true);
crop(f1,in);
crop(m1,in);
}
//Indentation gap.
}
//This method checks if the board is solved
protected boolean isboardUncovered()
{
for(int i=0;i<this.rows_num;++i)	
{
for(int j=0;j<this.cols_num;++j)	
{
JButton gbox=this.grid_boxes[i][j];
Icon ico=gbox.getIcon();
Scanner sc=new Scanner(gbox.getActionCommand());
sc.next();sc.next();
String tok=sc.next();
sc.close();
if(!this.isCleared(i, j))
{if(!((ico==this.flg)&&(tok.equals("M"))))
{return false;}	
}
}
}
return true;
}
//This method provides a safe first play,that is a set of indices free of mines to accomodate players
//first move
public int[] safePlay()
{
Random gen=new Random();	
boolean rflag=true;
int re[] =null;
while(rflag)
{
int x=gen.nextInt(this.rows_num);
int y=gen.nextInt(this.cols_num);
re=new int[] {x,y};
rflag=this.contains(re);
}
return re;
}
//Displays the gui which helps the user choose a difficulty level
protected static void chooseDifficulty()
{
Font obj;
String ttp="This game mode has ";
String ttp1=" rows and ";
String ttp2=" colums with ";
JFrame frm=new JFrame("Choose Difficulty");	
JPanel pan=new JPanel();
JButton easy=new JButton("Easy");
easy.setBackground(Color.GREEN);
easy.setToolTipText("<html>"+ttp+"9"+ttp1+"<br>9"+ttp2+"10 mines");
easy.setFont(obj=new Font(Font.SANS_SERIF,Font.BOLD,35));
JButton med=new JButton("Intermediate");
med.setBackground(Color.ORANGE);
med.setToolTipText("<html>"+ttp+"13"+ttp1+"<br>15"+ttp2+"40 mines");
med.setFont(obj);
JButton hard=new JButton("EXPERT!");
hard.setBackground(Color.RED.darker());
hard.setForeground(Color.CYAN);
hard.setFont(new Font("algerian",Font.BOLD,50));
hard.setToolTipText("<html>The hardest of 'em all<br>"+ttp+"30"+ttp1+"<br>16"+ttp2+"99 mines");
hard.setIcon(MineSweeper.mine_icon);
hard.setHorizontalTextPosition(SwingConstants.CENTER);
ActionListener ar=(ev)->
{
JButton src=(JButton)(ev.getSource());	
int rw;
int col;
int mnes;
if(src==easy)
{
  	rw=9;
  	col=9;
  	mnes=10;
}
else if(src==med)
{
  rw=13;	
  col=15;
  mnes=40;
}
else
{
rw=30;	
col=16;
mnes=99;
}
new MineSweeper(rw,col,mnes);
frm.setVisible(false);
frm.dispose();
};
easy.addActionListener(ar);
med.addActionListener(ar);
hard.addActionListener(ar);
pan.setLayout(new GridLayout(3,1));
pan.add(easy);
pan.add(med);
pan.add(hard);
frm.add(pan);
frm.setSize(350,350);
frm.setResizable(false);
frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
frm.setIconImage(MineSweeper.mine_icon.getImage());
frm.setVisible(true);
crop(MineSweeper.mine_icon,hard.getWidth()-60,hard.getHeight());
}
//This is a inner class which supports the TimerTask property
public class StopWatch extends TimerTask implements java.io.Serializable
{
private static final long serialVersionUID=900877636L;
	@Override
	public void run() 
	{
	Scanner sc=new Scanner(timer.getText());
	sc.useDelimiter(":");
	int min=sc.nextInt();
	int sec=sc.nextInt();
	sc.close();
	if(sec==60)
	{sec=0;
     min++;	
	}
	else
	{sec++;}
	String m=min+"";
	String s=sec+"";
	if(min<10)
	{m="0"+m;}
	if(sec<10)
	{s="0"+s;}
	timer.setText(m+":"+s);
	}
}
//Implemented methods of super interface ArcadeGame
@Override
public String getName() 
{
	return "MineSweeper";
}
//Implemented method of super-interface ArcadeGame
@Override
public Component getMainFrame() 
{
	return this.board;
}
//This class is Timer's subclass, but adds no functionality except serialization
public class SerializableTimer extends java.util.Timer implements java.io.Serializable
{
	private static final long serialVersionUID=2334400099L;
	public SerializableTimer()
	{super();}
	public SerializableTimer(boolean daeThread)
	{super(daeThread);}
}
//plays out the graphical sequence of mines exploding along with sound
protected void playExplosionSequence(int x,int y)
{
JButton mcButton=this.grid_boxes[x][y];	
mcButton.setIcon(this.exp_mine);
if(sfx.isSelected())
{MineSweeper.playExplosionSound();}
try {
	Thread.sleep(500);
    } 
catch (InterruptedException e) 
{
QuickErrorManager.manage("Little problem with mine going boom", "Looks like we couldnt play the mine explosion animation","No worries,just click on the blue button to close this dialog.");	
}
for(int i=0;i<mine_indices.length;++i)
{
int[] arr=this.mine_indices[i];
int x1=arr[0];
int y1=arr[1];
if((x1==x)&&(y1==y)) {continue;}
mcButton=this.grid_boxes[x1][y1];
mcButton.setIcon(this.exp_mine);
if(sfx.isSelected())
{MineSweeper.playExplosionSound();}
try {
	Thread.sleep(500);
    } 
catch (InterruptedException e) 
{
QuickErrorManager.manage("Little problem with mine going boom", "Looks like we couldnt play the mine explosion animation","No worries,just click on the blue button to close this dialog.");	
}
}
}
//Plays the "explosion" sound effect
private static void playExplosionSound()
{
	try {
		AudioInputStream as= AudioSystem.getAudioInputStream(MineSweeper.explosion);
		Clip clp=AudioSystem.getClip();
		clp.open(as);
		clp.start();
	    }
	catch (UnsupportedAudioFileException|IOException|LineUnavailableException e )
	{
	QuickErrorManager.manage("Audio File loading error", "Looks like someone's tampered with the audio resource files","Explosion sound effect will not play");	
	}	
}
//Plays the "click" sound effect.
private static void playClickSound()
{
try {
	AudioInputStream as= AudioSystem.getAudioInputStream(MineSweeper.click_sound);
	Clip clp=AudioSystem.getClip();
	clp.open(as);
	clp.start();
    }
catch (UnsupportedAudioFileException|IOException|LineUnavailableException e )
{
QuickErrorManager.manage("Audio File loading error", "Looks like someone's tampered with the audio resource files","Click sound effect will not play");	
}
}
//Starts the theme song of the game
private void startThemeSong()
{
	try {
		AudioInputStream as= AudioSystem.getAudioInputStream(MineSweeper.msong);
		cc=AudioSystem.getClip();
		cc.open(as);
		cc.start();
		cc.loop(Clip.LOOP_CONTINUOUSLY);
	    }
	catch (UnsupportedAudioFileException|IOException|LineUnavailableException e )
	{
	QuickErrorManager.manage("Audio File loading error", "Looks like someone's tampered with the audio resource files","Music will not play");	
	}
}
//Stops the theme song of the game
private void stopThemeSong()
{
cc.stop();
cc.close();
}
private void initializeSoundControls()
{
Font f=new Font("algerian",Font.BOLD,30);
this.sfx=new JCheckBox("SFX Sounds",true);
this.sfx.setBackground(Color.GREEN);
this.sfx.setFont(f);
this.music=new JCheckBox("Music",true);
this.music.setBackground(Color.GREEN);
this.music.setFont(f);
GameSaver.SerializableItemListener ir=new GameSaver.SerializableItemListener() 
{
	private static final long serialVersionUID=440990409211L;
	@Override
	public void itemStateChanged(ItemEvent ie) 
	{
		JCheckBox src=(JCheckBox)(ie.getSource());
		int ch=ie.getStateChange();
		if(ch==ItemEvent.SELECTED)
		{
			src.setBackground(Color.GREEN);
			if(src==music) 
			{
				startThemeSong();
			}
		}
		else
		{
			src.setBackground(Color.RED);
			if(src==music)
			{
				stopThemeSong();
			}
		}
	}
};
this.music.addItemListener(ir);
this.sfx.addItemListener(ir);
this.music.setToolTipText("<html>This is a checkbox which indicates whether<br>"
		+"you prefer the minecraft theme song to play<br>"
		+"if it is green,the theme song will be played<br>"
		+"if it is red,the theme song will not play.<br>Click to alter states");
this.sfx.setToolTipText("<html>This checkbox indicates whether or not you<br>"
		+ "want to hear the click sounds,explosion sounds,etc<br>"
		+ "If it is red you will not hear the sounds<br>"
		+ "If it is green the sounds will be played<br>"
		+ "Click to switch states");
//Indentation gap.
}
@Override
public File getRules() {
return DependancyManager.loadDependancy(57);
}
}