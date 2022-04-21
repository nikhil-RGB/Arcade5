import javax.imageio.*;
import utilities.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
@SuppressWarnings("unused")
public final class NumberReactionNew extends JFrame implements utilities.Playable,utilities.MusicalArcadeGameUser
{ private static final long serialVersionUID=26092003L;
  public NumberReactionNew(){super();} 
  public NumberReactionNew(String name){super(name);}  
  static int clicks;
  static int pld;
  volatile static int win_state;
  private volatile static boolean aiMoved;
  static NumberReactionNew frame;
  volatile static Color p1;
  volatile static Color p2;
  static NumberReactionAIModel nrai;
  private static Scrollbar sc1;
  private static Scrollbar sc2;
  private static Scrollbar sc3;
  static Frame smallFrame;
  static JCheckBox ai_opponent;
  static JPanel board;
  static JPanel sideboard;
  static JTextArea box;
  static JTextField field;
  private static Thread nigger;
  static JButton[][] buttons;
  private static MusicalArcadeGame msg;
  private static class ThreadedBitch extends Thread
  {
  @Override
  public void run()
  {
   NumberReactionNew.explode(clicks);
   Color c=NumberReactionNew.getWinner();
  if(c!=null){NumberReactionNew.declareWinner(c);}
  }
  }
  private static class Action implements ActionListener
  {
  public void actionPerformed(ActionEvent ev)
  { 
	  try {
    if(win_state==1){return;}
    if((nigger!=null)&&nigger.isAlive())
    {field.setText("Input not allowed while a reaction is in an ongoing state.");return;}
    if((clicks%2!=0)&&(NumberReactionNew.ai_opponent.isSelected())&&(!aiMoved)) 
    {field.setText("Let your opponent play first");return;}
    @SuppressWarnings("resource")
    String s= new Scanner(ev.getActionCommand()).next(); 
    String nah;
    field.setText(nah=("Clicked at: Row= "+(Integer.parseInt(s.charAt(0)+"")+1)+", Column= "+(Integer.parseInt(s.charAt(1)+"")+1)));
    ++clicks;
    Color req;
    if(clicks%2==0)
    {   field.setText(nah+" [PLAYER 2]'s chance is over");
        req=p2;}
    else 
    {   field.setText(nah+" [PLAYER 1]'s chance is over");
        req=p1;
    }
    String command=ev.getActionCommand();
    String col = calculateColor(command);
    if(col.equals("")||col.equals(req.toString()))
    {
     msg.playClickSound();
     int index1=Integer.parseInt(command.charAt(0)+""); 
     int index2=Integer.parseInt(command.charAt(1)+"");
     JButton button=buttons[index1][index2];
     int num=Integer.parseInt(button.getText());
     int mass=calculateCriticalMass(index1,index2);
    box.append(nah);
    box.append("\n");
     if(num==mass){
         button.setText((Integer.parseInt(button.getText())+1)+"");nigger=new ThreadedBitch();
         nigger.start();
         }
     else{
         button.setBackground(req);button.setText(++num+"");
         button.setActionCommand(index1+""+index2+" "+req.toString());
         if(num==mass){button.setForeground(Color.WHITE);}
         button.repaint();}
     if(ai_opponent.isSelected()&&clicks%2!=0)
     {nrai.determineNextMove();}
    }
    else{field.setText("Invalid move.Try another box");--clicks;}
    }  
	  catch(NullPointerException ex) {;}
  }
  }
  public static Color getWinner()
  {
   int p1_boxes=0;
   int totalFilled=54;
   int p2_boxes=0;
   for(int i=0;i<buttons.length;++i)
   {
   for(int j=0;j<buttons[0].length;++j) 
   {
   String col=calculateColor(buttons[i][j].getActionCommand()); 
   if(col.equals("")) 
   {--totalFilled;}
   else if(col.equals(p1.toString()))
   {++p1_boxes;}
   else if(col.equals(p2.toString()))
   {++p2_boxes;}
   } 
   } 
   if(p1_boxes==totalFilled){return p1;} 
   else if(p2_boxes==totalFilled){return p2;}
   return null;
  }
  public static void declareWinner(Color c)
  {
  win_state=1;  
  ai_opponent.setEnabled(false);
  msg.disableSoundControls();
  msg.stopThemeSong();
  field.setText("VICTORY");  
  field.setFont(new Font("algerian",Font.BOLD,23));  
  field.setBackground(c);
  box.setBackground(c.darker());
  }
  //Recursive Call-removing infinite recursion-removed recursion
  private static void explode(int click_no)
  {
   boolean didItFuckingExplode=true;
   msg.playExplosionSound();
   JButton button_b;
   int mass;
   int num;
   Color req;
   JButton bruh;
  while(didItFuckingExplode)
   {
   didItFuckingExplode=false;
   for(int i=0;i<9;++i) 
   {
    for(int j=0;j<6;++j) 
    {
    button_b=buttons[i][j];
    mass=calculateCriticalMass(i,j);
    num=Integer.parseInt(button_b.getText());
    if(num==mass){button_b.setForeground(Color.WHITE);}
    if(!(num>mass)){continue;}
    int eloss=num-(mass+1);
    field.setText("Explosion!! At <Row= "+(i+1)+", Column= "+(j+1)+">");
    box.append("\nGrid Box EXIT system unlocked-\n <ROW: "+(i+1)+" COLUMN: "+(j+1)+" >\n");
    box.append(field.getText());
    box.append("\n");
    didItFuckingExplode=true;
    if(click_no%2==0)
        {
        req=p2;
        }
    else
        {
        req=p1;
        }
    if(eloss!=0)
    {
    box.append("CAUTION: ELEMENTAL LOSS!\n at: <Row: "+(i+1)+" Column: "+(j+1)+">\nElements lost: "+eloss);	
    }
    button_b.setText("0");
    button_b.setBackground(new JButton().getBackground());
    button_b.setActionCommand(i+""+j);
    button_b.setForeground(new JButton().getForeground());
    button_b.repaint();
    try{
        bruh=buttons[i+1][j];
        bruh.setText((Integer.parseInt(bruh.getText())+1)+"");
        bruh.setBackground(req);
        bruh.setActionCommand((i+1)+""+j+" "+req.toString());
        bruh.repaint();
    }
    catch(IndexOutOfBoundsException ex){;}
    try{
    bruh=buttons[i-1][j];
    bruh.setText((Integer.parseInt(bruh.getText())+1)+"");
    bruh.setBackground(req);
    bruh.setActionCommand((i-1)+""+j+" "+req.toString());
    bruh.repaint();
    }
    catch(IndexOutOfBoundsException ex){;}
    try{
    bruh=buttons[i][j+1];
    bruh.setText((Integer.parseInt(bruh.getText())+1)+"");
    bruh.setBackground(req);
    bruh.setActionCommand(i+""+(j+1)+" "+req.toString());
    bruh.repaint();
    }
    catch(IndexOutOfBoundsException ex){;}
    try
    {
    bruh=buttons[i][j-1];
    bruh.setText((Integer.parseInt(bruh.getText())+1)+"");
    bruh.setBackground(req);
    bruh.setActionCommand(i+""+(j-1)+" "+req.toString());
    bruh.repaint();
    }
    catch(IndexOutOfBoundsException ex){;}
       Color c=getWinner();
  if(c!=null){box.append("\nCELL LOCKDOWN INITIATED at\n Row: "+(i+1)+"Column: "+(j+1));return;}
    }
   }
  }
}
  private static int calculateCriticalMass(int i,int j)
  {if((i==0&&j==0)||(i==0&&j==5)||(i==8&&j==0)||(i==8&&j==5))
    {return 1;}
   else if(i==0||i==8||j==0||j==5)
    {return 2;}
   return 3; 
  }
  private static String calculateColor(String cmd)
  {
      if(isNumber(cmd))
      {return "";}
      @SuppressWarnings("resource")
	Scanner read=new Scanner(cmd);
      read.next();
      String c= read.nextLine();
      return c.trim(); 
    }
  private static boolean isNumber(String s)
  {
  try{Integer.parseInt(s);}  
  catch(NumberFormatException ex){return false;} 
  return true;  
  }
    private static void setColor()
  {
  Frame frame=new Frame("Please set Color");
  Button regular=new Button("REGULAR");
  Button Custom=new Button("CUSTOM");
  Panel ppp=new Panel();
  ppp.add(regular);
  ppp.add(Custom);
  CardLayout cd=new CardLayout();
  smallFrame=frame;
  Panel pan1=new Panel();
  Panel pan=new Panel();
  Panel main=new Panel();
  main.setLayout(cd);
  regular.addActionListener((ev)->{if(!(NumberReactionNew.p1==null&&NumberReactionNew.p2==null)){return;}cd.show(main,"REGULAR");});
  Custom.addActionListener((ev)->{if(!(NumberReactionNew.p1==null&&NumberReactionNew.p2==null)){return;}cd.show(main,"CUSTOM");});
  frame.add(ppp,BorderLayout.NORTH);
  Label l1=new Label("Player 1 Color",Label.LEFT);
  Label l2=new Label("Player 2 color",Label.LEFT);
  l1.setFont(new Font("algerain",(Font.BOLD|Font.ITALIC),36));
  l2.setFont(new Font("algerain",(Font.BOLD|Font.ITALIC),36));
  pan.setLayout(new FlowLayout());
  Panel pan2=new Panel();
  pan1.setLayout(new FlowLayout());
  pan2.setLayout(new BoxLayout(pan2,BoxLayout.Y_AXIS));
  Color[] cr1=new Color[]{Color.GREEN,Color.RED,Color.CYAN,Color.BLUE,Color.GRAY,new Color(238,130,23)};    
  Color[] cr2=new Color[]{Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW,new Color(210,105,30),new Color(0.76278f,1.0f,0.25f)};    
  String[] s1={"Green","Red","Cyan","Blue","Gray","Violet"};      
  String[] s2={"Magenta","Orange","Pink","Yellow","Chocolate Brown","Indigo"};
  CheckboxGroup c1=new CheckboxGroup();
  CheckboxGroup c2=new CheckboxGroup();
  for(int k=0;k<s1.length;++k)
  {
  Checkbox b1=new Checkbox(s1[k],c1,false);
  b1.setBackground(cr1[k]);
  b1.addItemListener((ev)->{NumberReactionNew.p1=((Component)(ev.getSource())).getBackground();
                           l1.setForeground(NumberReactionNew.p1);
    });
  pan1.add(b1);
  Checkbox b2=new Checkbox(s2[k],c2,false); 
  b2.setBackground(cr2[k]);
  b2.addItemListener((ev)->{NumberReactionNew.p2=((Component)(ev.getSource())).getBackground();
                            l2.setForeground(NumberReactionNew.p2);
    });
  pan.add(b2);
   }
  pan2.add(l1);
  pan2.add(pan1);
  pan2.add(l2);
  pan2.add(pan);
  main.add(pan2,"REGULAR");
  Panel custom=new Panel();
  custom.setLayout(new BoxLayout(custom,BoxLayout.Y_AXIS));
  Scrollbar red_hue=new Scrollbar(Scrollbar.HORIZONTAL,0,1,0,100);
  
  Scrollbar green_sat=new Scrollbar(Scrollbar.HORIZONTAL,0,1,0,100);
 
  Scrollbar blue_brit=new Scrollbar(Scrollbar.HORIZONTAL,0,1,0,100);
 
  sc1=red_hue;
  sc2=green_sat;
  sc3=blue_brit;
  Label lab1=new Label("RED");
  Label lab2=new Label("GREEN");
  Label lab3=new Label("BLUE");
  custom.add(lab1);
  custom.add(red_hue);
  custom.add(lab2);
  custom.add(green_sat);
  custom.add(lab3);
  custom.add(blue_brit);
  
  AdjustmentListener adj=(ad)->
  {
  Color ccc=calc(lab1,red_hue,green_sat,blue_brit);  
  red_hue.setBackground(ccc);  
  green_sat.setBackground(ccc);  
  blue_brit.setBackground(ccc);  
  };
  red_hue.addAdjustmentListener(adj);
  green_sat.addAdjustmentListener(adj);
  blue_brit.addAdjustmentListener(adj);
  blue_brit.setValue(0);
  green_sat.setValue(0);
  red_hue.setValue(0);
  blue_brit.setBackground(Color.BLACK);
  green_sat.setBackground(Color.BLACK);
  red_hue.setBackground(Color.BLACK);
  Button but=new Button("RED-GREEN-BLUE");
  but.setActionCommand("RGB");
  Button but1=new Button("HUE-SATURATION-BRIGHTNESS");
  but1.setActionCommand("HSB");
  Button OK=new Button("OK[PLAYER 1]");
  ActionListener ar=(ev)->{lab1.setText("HUE");lab2.setText("SATURATION");lab3.setText("BRIGHTNESS");
                           Color ck=calc(lab1,red_hue,green_sat,blue_brit);
                           red_hue.setBackground(ck);
                           green_sat.setBackground(ck);
                           blue_brit.setBackground(ck);
    };
  ActionListener ar2=(ev)->{lab1.setText("RED");lab2.setText("GREEN");lab3.setText("BLUE");
                           Color ck=calc(lab1,red_hue,green_sat,blue_brit);
                           red_hue.setBackground(ck);
                           green_sat.setBackground(ck);
                           blue_brit.setBackground(ck);
    };
  ActionListener okie=(ev)->{
                    Color c=calc(lab1,red_hue,green_sat,blue_brit);              
                    BRUH:
                    if(pld==0)
                    {p1=c;pld++;OK.setLabel("OK[PLAYER 2]");
                     red_hue.setValue(0);
                     green_sat.setValue(0);
                     blue_brit.setValue(0);
                     Color heh=Color.BLACK;
                     red_hue.setBackground(heh);
                     green_sat.setBackground(heh);
                     blue_brit.setBackground(heh);
                     
                    }
                    else
                    {if(c.equals(p1)){break BRUH;}p2=c;}
                    };
  custom.add(but);
  custom.add(but1);
  custom.add(OK);
  but.addActionListener(ar2);
  but1.addActionListener(ar);
  OK.addActionListener(okie);
  main.add(custom,"CUSTOM");
  frame.add(main,BorderLayout.CENTER);
  frame.setSize(new Dimension(450,450));
  try{
  Image bruh=ImageManager.nr_1;
  frame.setIconImage(bruh);
}
catch(Throwable ertyui){;}
  frame.setVisible(true);
  WindowListener YES_SENPAI_AH;
  frame.addWindowListener(YES_SENPAI_AH=(new WindowAdapter(){
    public void windowClosing(WindowEvent we)
    {
     if((p1==null)||(p2==null)){return;}
     frame.setVisible(false);
     frame.dispose();
    }
    }));  
  }
  public static Color calc(Label lab1,Scrollbar red_hue,Scrollbar green_sat,Scrollbar blue_brit)
  {
     if(lab1.getText().equals("RED"))
    {float r=((red_hue.getValue()/100.0f)*1.0f);
     float g=((green_sat.getValue()/100.0f)*1.0f);
     float b=((blue_brit.getValue()/100.0f)*1.0f);
     return new Color(r,g,b); 
     }
    else
    {
    float h=(red_hue.getValue()/100.0f)*1.0f;
    float s=(green_sat.getValue()/100.0f)*1.0f;
    float b=(blue_brit.getValue()/100.0f)*1.0f;
    return new Color(Color.HSBtoRGB(h,s,b));
    }
    }
  public static void main(String[] args)
   {
	  Thread ft=new Thread() {
    public void run() 
    {
    if(NumberReactionNew.frame!=null) 
    {
    	JOptionPane.showMessageDialog(null,"Please close the current NumberReaction Game to make a new one","Error",JOptionPane.ERROR_MESSAGE);
        return;
    }
    init();
    try{
    int resp=JOptionPane.showOptionDialog(null,"<html>Select a Color Selection Method"
    		+ "<br>Standard: Use to quickly select a standard Color"
    		+ "<br>(Very little control over Color properties)"
    		+ "<br>Customized: Fine grained control over Color-Recommended", "Choose a method", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,new String[] {"Standard","Customized"}, "Customized");
   if(resp==0)
   {
	   setColor();
	   while((p1==null||p2==null)){;}
	   smallFrame.setVisible(false);
	   smallFrame.dispose();
   }
   else
   {
	   NumberReactionNew.setColorAdvanced();
   }
   //Continued from here->
   nrai=new NumberReactionAIModel(p2,p1);
   GridLayout gd=new GridLayout(9,6,0,0);
   board.setLayout(gd);
   BoxLayout bot=new BoxLayout(sideboard,BoxLayout.Y_AXIS); 
   sideboard.setLayout(bot); 
   sideboard.add(new JScrollPane(field));
   field.setFont(new Font(Font.SANS_SERIF,Font.BOLD,12));
   field.setEditable(Boolean.FALSE);
   field.setToolTipText("The last move is tracked by this field");
   box.setEditable(Boolean.FALSE);
   box.setToolTipText("The players moves and in-game actions are recorded by this field");
   sideboard.add(new JScrollPane(box));
   box.setFont(new Font("sans serif",Font.PLAIN,16));
   NumberReactionNew.ai_opponent=new JCheckBox("AI player",false);
   ai_opponent.setBackground(Color.BLUE);
   ai_opponent.setFont(new Font("algerian",Font.BOLD,18));
   ai_opponent.setToolTipText("<html>This is a beta feature,and<br>the ai opponent will be very<br>weak at the game.");
   ai_opponent.addItemListener((ie)->
   {
	if(ie.getStateChange()==ItemEvent.SELECTED)   
	{
		if(clicks%2!=0)
		{nrai.determineNextMove();}
	}  
   });
   sideboard.add(ai_opponent);
   ActionListener buttonPressd= new Action();
   for(int i=0;i<9;++i)
   {
   for(int j=0;j<6;++j ) 
   {
   JButton button=new JButton(); 
   button.setActionCommand(i+""+j);
   button.setText("0");
   button.setToolTipText("<html>Pressing here will cause the cell<br>"
   		+ " count to increase by 1, or explode depending on the situation,as long as it<br>"
   		+ " belongs to you.");
   button.addActionListener(buttonPressd); 
   buttons[i][j]=button;
   button.setFont(new Font("algerian",Font.BOLD,24));
   board.add(button);
   }
   }
   frame.getContentPane().add(BorderLayout.CENTER,board);
   frame.getContentPane().add(BorderLayout.EAST,sideboard);
   MenuBar mb=new MenuBar();
   frame.setMenuBar(mb);
   Menu save=new Menu("Save");
   MenuItem saveAs=new MenuItem("Save as");
   save.add(saveAs);
   saveAs.addActionListener((ev)->{
	   JOptionPane.showMessageDialog(null,"This feature is not available in this version of the app","Error",JOptionPane.ERROR_MESSAGE);
   });
   Menu about=new Menu("About");
   Menu NR=new Menu("NumberReaction");
   MenuItem info=new MenuItem("Information & legal");
   info.addActionListener((ev)->{
	String mssg="<html>The code for this game is owned by Nikhil Narayanan,<br>"
			+ "individual JAVA GUI developer. However, the concept of this game is similar to"
			+ "that of chain reaction, and Nikhil DOES NOT take ownership for any concept<br>"
			+ "except the tweaked explosion mechanic. Read origins for more info.";
    JOptionPane.showMessageDialog(null,mssg,"Info & Legal",JOptionPane.PLAIN_MESSAGE);
    });
   MenuItem origins=new MenuItem("Origin");
   origins.addActionListener((ev)->
   {
	String origin="<html>Chain reaction is a deterministic combinatorial game of perfect information for 2 - 8 players.<br>" + 
			  "It was originally developed by Buddy-Matt Entertainment for Android. One can download it from the Play Store<br>"
			+ "NumberReaction is simply a remake of chain reaction for PC, with a tweaked explosion mechanism<br>"
			+ "(Read rules for differences between chain reaction and this game)";
    JOptionPane.showMessageDialog(null,origin,"Origins",JOptionPane.INFORMATION_MESSAGE);
   });
   MenuItem rules=new MenuItem("Rules");
   rules.addActionListener((ev)->{
   ImageIcon[] im=DependancyManager.loadIconBatch(27,37);
   int[] bnums=new int[] {4,9,18,29,33,41,53,62,67,75};
   RulesManager obj=new RulesManager(DependancyManager.loadDependancy(37),bnums,im,450,450); 
   obj.displayRules();
    });
   mb.add(save);mb.add(about);
   about.add(NR);
   NR.add(origins);
   NR.add(rules);
   about.add(info);
   msg=new MusicalArcadeGame(DependancyManager.loadDependancy(18));
   frame.add(msg.getJPanel(),BorderLayout.WEST);
   frame.setVisible(true);
   frame.setSize(560,500);
   frame.setResizable(false);
   frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
   try
   {
    Image im=ImageManager.nr_1;
    frame.setIconImage(im);
    }
   catch(Throwable ex){;}
   frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   frame.addWindowListener(new WindowAdapter() {
	   @Override
	   public void windowClosing(WindowEvent we)
	   {msg.stopThemeSong();
	    terminateGameS();
	   }
   });
   msg.playThemeSong();
}
catch(Throwable obj)
{
ErrorManager i=new ErrorManager(new Frame("Error!!"),"Number Reaction error");
i.display("NumberReaction issue!!",obj);
}
    }
	  };
	  ft.start();
}
@Override
public File getRules() {
	
	return DependancyManager.loadDependancy(37);
}
//An AI model for NumberReaction, meant to play against averagely skilled
//chain reaction player.
public static class NumberReactionAIModel
{
	private Color me;
	private Color opp;
public NumberReactionAIModel(Color p11,Color p22)
{
this.me=p11;
this.opp=p22;
}
public boolean isCriticalMass(int x,int y)
{
String ss=buttons[x][y].getText();
if(isNumber(ss)) 
{
if(calculateCriticalMass(x,y)==Integer.parseInt(ss)) 
{return true;}
}
return false;
}
public int[] explodableTile()
{
ArrayList<int[]> x=criticalMassCells();
for(int k=0;k<x.size();++k)
{
int[] ob=x.get(k);
ArrayList<JButton> arlst=adjacentCells(ob[0],ob[1]);
for(int k1=0;k1<arlst.size();++k1)
 {
  	JButton jbtn=arlst.get(k1);
  	int[] loc=locn(jbtn);
  	boolean flg=this.isCriticalMass(loc[0],loc[1]);
  	if(flg&&jbtn.getBackground().equals(this.opp))
  	{return ob;}
 }
}
return new int[0];
}
public int[] locn(JButton jbtn)
{
String s=jbtn.getActionCommand();	
int x=Integer.parseInt(s.charAt(0)+"");
int y=Integer.parseInt(s.charAt(1)+"");
return new int[] {x,y};
}
public ArrayList<JButton> adjacentCells(int x,int y)
{
ArrayList<JButton> bruh=new ArrayList<>(0);
try
{
JButton n1=buttons[x+1][y];
bruh.add(n1);
}
catch(Exception ex)
{}
try
{
JButton n1=buttons[x][y+1];
bruh.add(n1);
}
catch(Exception ex)
{}
try
{
JButton n1=buttons[x][y-1];
bruh.add(n1);
}
catch(Exception ex)
{}
try
{
JButton n1=buttons[x-1][y];
bruh.add(n1);
}
catch(Exception ex)
{}
return bruh;
}
public ArrayList<int[]> criticalMassCells()
{
    ArrayList<int[]> al=new ArrayList<int[]>(0);
	for(int i=0;i<9;++i)
	{
		for(int j=0;j<6;++j)
		{
			JButton butt=buttons[i][j];
			if(!me.equals(butt.getBackground()))
			{continue;}
			int x=calculateCriticalMass(i,j);
			String num=butt.getText();
			if(isNumber(num))
			{
				if(x==Integer.parseInt(num))
				{al.add(new int[]{i,j});}
			}
		}
	}
	return al;
}
public void performMove(JButton btn)
{
aiMoved=true;	
btn.doClick();
aiMoved=false;
}
public void determineNextMove()
{   Thread t=new Thread() {
	public void run()
	{
	try
	{
		Thread.sleep(1500);
	}
	catch (InterruptedException e) 
	{}
	int[] x=explodableTile();
	if(x.length!=0)
	{
	performMove(buttons[x[0]][x[1]]);
	return;
	}
	JButton re=randMove();
	performMove(re);
   }
  };
  t.start();
}
public JButton randMove()
{
JButton but=null;
Random rand=new Random();
do
{
int x=rand.nextInt(9);
int y=rand.nextInt(6);
but=buttons[x][y];
}
while(but.getBackground().equals(this.opp));
return but;
}
public void makeCritical()
{
//You may need a few more methods apart from this.completed later	
}
}
@Override()
public void terminateGame()
{terminateGameS();}
public static void terminateGameS()
{
NumberReactionNew.ai_opponent=null;
NumberReactionNew.aiMoved=false;
NumberReactionNew.board=null;
NumberReactionNew.box=null;
NumberReactionNew.buttons=null;
NumberReactionNew.clicks=0;
NumberReactionNew.field=null;
NumberReactionNew.frame=null;
NumberReactionNew.msg=null;
NumberReactionNew.nigger=null;
NumberReactionNew.nrai=null;
NumberReactionNew.p1=null;
NumberReactionNew.p2=null;
NumberReactionNew.pld=0;
NumberReactionNew.sc1=null;
NumberReactionNew.sc2=null;
NumberReactionNew.sc3=null;
NumberReactionNew.sideboard=null;
NumberReactionNew.smallFrame=null;
NumberReactionNew.win_state=0;
}
public static void init()
{
  
	  clicks=0;
	  pld=0;
	  win_state=0;
	  frame=new NumberReactionNew("NUMBER REACTION");
	  board=new JPanel();
	  sideboard=new JPanel();
	  box=new JTextArea(20,20);
	  field=new JTextField(13);
      buttons=new JButton[9][6];	

}
public static void setColorAdvanced()
{
 PlayerColorSelector pc=new PlayerColorSelector(2,"Select your respective Colors",null,true);
 pc.setSize(600,500);
 pc.setLocationRelativeTo(null);
 pc.setVisible(true);
 Hashtable<String,Color> ht=pc.results;
 NumberReactionNew.p1=ht.get(pc.getTabbedPane().getTitleAt(0));
 NumberReactionNew.p2=ht.get(pc.getTabbedPane().getTitleAt(1));
 }
}