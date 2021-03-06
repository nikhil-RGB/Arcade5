
//to create a program which allows a player to play checkers with a friend or against the computer
import java.util.*;
import utilities.GameSaver;
import utilities.GameSaver.SerializableWindowListener;
import utilities.MusicalArcadeGame;
import utilities.RulesManager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
@SuppressWarnings("unused")
public final class Checkers extends utilities.MusicalArcadeGame implements utilities.SavableArcadeGame,java.io.Serializable,utilities.Playable
{
public JFrame cfrm;
private static final long serialVersionUID=1000L;
private ArrayList<JButton> valids;
protected String winner;
protected boolean game_ended;
private int counter=0;
private boolean continued_capture=false;
private int pos_row;
private int pos_col;
private JButton[] locs;
private JFrame frm;	
private JPanel panel;
private JButton[][] board;
protected static final  Image rc;
protected static final  Image bc;
static
{
	Frame awt_compo=new Frame("Checkers initialization");
    awt_compo.setSize(0,0);
    awt_compo.setResizable(false);
    awt_compo.setVisible(true);
    Image mm=awt_compo.createImage(50,50);
    Graphics temp=mm.getGraphics();
    temp.setColor(Color.BLACK);
    temp.fillRect(0, 0, 50, 50);
    temp.setColor(Color.RED);
    temp.fillOval(0, 0, 50, 50);
    temp.setColor(Color.ORANGE);
    temp.fillOval(12,12,25,25);
    rc=mm;
    mm=awt_compo.createImage(50,50);
    temp=mm.getGraphics();
    temp.setColor(Color.BLACK);
    temp.fillRect(0, 0, 50, 50);
    temp.setColor(Color.GRAY);
    temp.fillOval(0,0,50,50);
    temp.setColor(Color.WHITE);
    temp.fillOval(12, 12, 25,25);
    bc=mm;
    awt_compo.setVisible(false);
    awt_compo.dispose();
}
  public Checkers()
  {  
	  super(DependancyManager.loadDependancy(15));
	  JPanel sidepane=new JPanel();
	  locs=new JButton[4];
	  valids=new ArrayList<>(0);
     GameSaver.SerializableActionListener ar=(ev)->{//Block Lambda used
     JButton src=(JButton)(ev.getSource());
     if(src.getText().isEmpty()) {return;}
     this.continued_capture=false;
     this.pos_col=0;
     this.pos_row=0;
     this.valids=new ArrayList<>(0);
     String s=src.getActionCommand();
     Scanner reader=new Scanner(s);	
     int x=reader.nextInt();
     int y=reader.nextInt();
     int x1=reader.nextInt();
     int y1=reader.nextInt();
     String type=reader.next();
     String op=reader.next();
     if(op.equals("capture"))
     {
    	int x2=reader.nextInt(); 
    	int y2=reader.nextInt();
    	board[x2][y2].setIcon(null);
    	board[x2][y2].setText("");
     }
     reader.close();
     board[x][y].setIcon(null);
     board[x][y].setText("");
     board[x1][y1].setText(type);
     if(type.replace("k", "").equals("rc")) {board[x1][y1].setIcon(new ImageIcon(Checkers.rc));}
     else {board[x1][y1].setIcon(new ImageIcon(Checkers.bc));}
     for(int k=0;k<4;++k)
     {
    	 
    	 locs[k].setText("");
    	 locs[k].setActionCommand("");
    	 
     }
     this.crown(board[x1][y1]);
     if((!piecesLeft(Checkers.getContrary(type)))||(!movesLeft(Checkers.getContrary(type))))
     {
    	 winner=type.replace("k", "");
    	 game_ended=true;
    	 displayResults(winner);
    	 return;
     }
     else if(!movesLeft(type))
     {
     winner=getContrary(type);
     game_ended=true;
     displayResults(winner);
     return;
     }
     if(this.canCapture(board[x1][y1])&&op.equals("capture"))
     {this.continued_capture=true;
      this.pos_row=x1;
      this.pos_col=y1;
     }
     else
     {++counter;
     this.setValidButtons(Checkers.getContrary(type));
     }
     
     };
     for(int k=0;k<4;++k)
     {
      	 locs[k]=new JButton("");
      	 locs[k].setBackground(Color.GREEN);
      	 locs[k].setFont(new Font("algerian",Font.BOLD,23));
         locs[k].addActionListener(ar); 
         locs[k].setToolTipText("<html>Clicking on this option will lead to movement<br>"
         		+ "of checker to indicated grid box.Hover your mouse on the checker to see"
         		+ "<br>options again");
    	sidepane.add(locs[k]);
     }
     
     sidepane.setLayout(new BoxLayout(sidepane,BoxLayout.Y_AXIS));
     sidepane.add(super.box);
	 frm=new JFrame("Let's play checkers");
	 panel=new JPanel();
	 panel.setBackground(Color.BLACK);
	 panel.setLayout(new GridLayout(8,8));
	 board=new JButton[8][8];
	 Color q=Color.WHITE;
	 GameSaver.SerializableMouseListener ml=(new GameSaver.SerializableMouseListener()
	 {private static final long serialVersionUID=11092002L;
	   private boolean can_capture;
	   private Object  pmoves;
		public void mouseEntered(MouseEvent ce)
		{
			if(game_ended) {return;}
		JButton jbtn=(JButton)(ce.getSource());
		if(jbtn.getText().isEmpty()) {return;}
		if((counter%2==0)&&(jbtn.getText().contains("rc"))) {return;}
		else if((counter%2!=0)&&(jbtn.getText().contains("bc"))) {return;}
		if(continued_capture&&(!jbtn.getActionCommand().equals(pos_row+" "+pos_col)))
		{return;}
		if((!continued_capture)&&(valids.size()>0)&&(!valids.contains(jbtn)))
		{return;}
        if(moves(jbtn)==null) {
        	pmoves=null;
        	can_capture=false;
        	return;}
		
		
			if(canCapture(jbtn)) {
			ArrayList<ArrayList<JButton>> arr=captureSequences(jbtn);
			pmoves=arr;
			can_capture=true;
			for(int kk=0;kk<arr.size();++kk)	
			{
			ArrayList<JButton> bttns=arr.get(kk);
			bttns.get(0).setBackground(Color.RED);
			bttns.get(1).setBackground(Color.GREEN);
			bttns.get(1).setText(kk+"");
			}	
			}
			else 
			{
				
			@SuppressWarnings("unchecked")
			ArrayList<JButton> jbtnns=(ArrayList<JButton>)(moves(jbtn));
			pmoves=jbtnns;
			can_capture=false;
			for(int k=0;k<jbtnns.size();++k)	
			{
				jbtnns.get(k).setBackground(Color.GREEN);;
				jbtnns.get(k).setText(k+"");
			}
				
			}
			
		
		}
		public void mousePressed(MouseEvent ce)
		{this.mouseExited(ce);}
		public void mouseExited(MouseEvent ce)
		{   
			if(game_ended) {return;}
			JButton jbtn=(JButton)(ce.getSource());
			if(jbtn.getText().isEmpty()) {return;}
			if((counter%2==0)&&(jbtn.getText().contains("rc"))) {return;}
			else if((counter%2!=0)&&(jbtn.getText().contains("bc"))) {return;}
			if(continued_capture&&(!jbtn.getActionCommand().equals(pos_row+" "+pos_col)))
			{return;}
			if((!continued_capture)&&(valids.size()>0)&&(!valids.contains(jbtn)))
			{return;}
	        if(pmoves==null) {return;}
		if(can_capture)	
		{@SuppressWarnings("unchecked")
		ArrayList<ArrayList<JButton>> object=(ArrayList<ArrayList<JButton>>)(pmoves);
		 for(int k=0;k<object.size();++k)
		 {
			 ArrayList<JButton> buttons=object.get(k);
			 buttons.get(0).setBackground(getRequiredColor(buttons.get(0)));
			 buttons.get(1).setBackground(getRequiredColor(buttons.get(1)));
			 buttons.get(1).setText("");
		 }
		
		}
		else
		{
			@SuppressWarnings("unchecked")
			ArrayList<JButton> arrs=(ArrayList<JButton>)(pmoves); 
			for(int k=0;k<arrs.size();++k)
			{
				arrs.get(k).setBackground(getRequiredColor(arrs.get(k)));
				arrs.get(k).setText("");
				
			}
			
		}
			
			
		}
	 }
	 );
	 GameSaver.SerializableActionListener acr=(e)->{
    if(game_ended) {return;}	 
	JButton b=(JButton)(e.getSource());
	if(b.getText().isEmpty()) {return;}
	if(counter%2==0&&b.getText().contains("rc"))
	{return;}
	else if(counter%2!=0&&b.getText().contains("bc"))
	{return;}
	if(continued_capture&&(!b.getActionCommand().equals(this.pos_row+" "+this.pos_col)))
	{return;}
	if((!continued_capture)&&(valids.size()>0)&&(!valids.contains(b)))
	{return;}
	for(int k=0;k<4;++k) {locs[k].setText("");locs[k].setActionCommand("");}
	Object obj=this.moves(b);
	if(obj==null) 
	{
	return;	
	}
	if(this.canCapture(b))
	{
	@SuppressWarnings("unchecked")
	ArrayList<ArrayList<JButton>> arr=(ArrayList<ArrayList<JButton>>)(obj);	
	for(int k=0;k<arr.size();++k)
	{
		ArrayList<JButton> sequence=arr.get(k);
		String acncmd=b.getActionCommand()+" "+sequence.get(1).getActionCommand()+" "+b.getText()+" capture "+sequence.get(0).getActionCommand();
		locs[k].setText(k+"");
		locs[k].setActionCommand(acncmd);
		
	}
		
	}
	else {
		
		@SuppressWarnings("unchecked")
		ArrayList<JButton> arrs=(ArrayList<JButton>)(obj);
		for(int k=0;k<arrs.size();++k)
		{
		 String acncmd=b.getActionCommand()+" "+arrs.get(k).getActionCommand()+" "+b.getText()+" normal ";
		locs[k].setText(k+"");
		locs[k].setActionCommand(acncmd);
		}
	}
	this.playClickSound();
	 };
	 for(int i=0;i<8;++i) 
	 {  
		 for(int j=0;j<8;++j)
		 {  
			 if(j==0)
			 {if(i%2==0) {q=Color.WHITE;}
			 else 
			 {
			q=Color.black;	 
		     }
			 }
			 else 
			 {
				 if(q.equals(Color.BLACK)) {q=Color.WHITE;}
				 else {q=Color.BLACK;}
				 
			 }
		  	 board[i][j]=new JButton("");
			 board[i][j].setActionCommand(i+" "+j);
			 board[i][j].addActionListener(acr);
			 board[i][j].addMouseListener(ml);
			 board[i][j].setBackground(q);
			 board[i][j].setToolTipText("<html>Clicking on a cell with a checker having valid"
			 		+ " available<br>moves will lead to the apperance of options on the right side<br>"
			 		+ "of the board.Check it out!");
			 board[i][j].setForeground(Color.BLUE);
			 board[i][j].setFont(new Font("algerian",Font.BOLD,20));
			 panel.add(board[i][j]);			 
		 }
		 
	 }
	  this.fillBoard();
	  JMenuBar bs=new JMenuBar();
	  JMenu save=new JMenu("Save Game/Load game");
	  JMenuItem saveas=new JMenuItem("Save as");
	  JMenuItem load=new JMenuItem("Load game");
	  GameSaver.SerializableActionListener n,n1;
	  saveas.addActionListener(n=(ev)->{if(game_ended) {return;};
	  new GameSaver().applySaveGUI(this,ImageManager.chk_1);});
	  load.addActionListener(n1=(ev)->{
	  GameSaver gs=new GameSaver();
	  gs.applyOutputGUI(this.getName(),true,Checkers.rc,Checkers.rc);
	  this.cfrm.setVisible(false);
	  this.cfrm.dispose();
	  this.stopThemeSong();
	  Thread t=new Thread() 
	  {
		  public void run()
		  {
		   while(gs.object==null)
		   {}
		   MusicalArcadeGame msg=(MusicalArcadeGame)(gs.object);
		   if(msg.getMusicControl().isSelected())  
		   {msg.playThemeSong();}	  
		  }
	  };
	  t.start();
	  });
	  save.add(load);
	  save.add(saveas);
	  bs.add(save);
	  JMenu hlp=new JMenu("Help");
	  JMenuItem rules=new JMenuItem("Rules");
	  rules.addActionListener(new GameSaver.SerializableActionListener() {
		private static final long serialVersionUID=2334567811L;
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		ImageIcon im1=new ImageIcon(DependancyManager.getDependancy(23));
		ImageIcon im2=new ImageIcon(DependancyManager.getDependancy(24));
		ImageIcon im3=new ImageIcon(DependancyManager.getDependancy(21));	
		ImageIcon im4=new ImageIcon(DependancyManager.getDependancy(22));
		ImageIcon im5=new ImageIcon(DependancyManager.getDependancy(25));
		displayRules(new int[] {47,52,54,57},new ImageIcon[] {im1,im2,im3,im4,im5},400,400);
		}
	});
	  hlp.add(rules);
	  bs.add(hlp);
	  frm.setJMenuBar(bs);
	  frm.setIconImage(ImageManager.chk_1);
	  frm.add(panel);
	  frm.add(sidepane,BorderLayout.EAST);
	  frm.setSize(1000, 1000);
	  frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
	  frm.setResizable(false);
	  frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  frm.setVisible(true);
	  playThemeSong();
	  frm.addWindowListener(new GameSaver.SerializableWindowListener() 
	  
	  {
		 private static final long serialVersionUID=22233445111L; 
		 @Override 
		 public void windowClosing(WindowEvent we)
		  {
			 stopThemeSong();
		  }
	  });
	  cfrm=frm;
  }
  private void fillBoard()
  {
	ImageIcon[] arr=new ImageIcon[] {new ImageIcon(rc),new ImageIcon(bc)};
	int x=12;  
	String s="rc";
	int i=0;int j=0;
	for(int i1=0;i1<2;++i1)
	{
	PEWDS:
	{
	for(;i<8;++i)
	{
	for(j=0;j<8;++j)	
	{
	if(board[i][j].getBackground().equals(Color.BLACK))	
	{
		
	board[i][j].setIcon(arr[i1]);
	board[i][j].setText(s);
	--x;
	}
	}
	if(x==0) {break PEWDS;}
	}
	}
	x=12;
	s="bc";
	i=5;j=0;
	}
  }
  public ArrayList<ArrayList<JButton>> getDiagonals(int x, int y,Diagonal pref)
  { 
 	 ArrayList<ArrayList<JButton>> st=new ArrayList<>(0); 
	 int x1=x;
     int y1=y;
    ArrayList<JButton> ps1=new ArrayList<>(0);
	ArrayList<JButton> ps=new ArrayList<> (0);//Note the use of diamond operator(introduced in JDK 9)
    for(int k=0;k<2;++k)
    {
    	if(Diagonal.BOTTOM.equals(pref)) 
    	{
    		if(x1<7&&y1<7) {ps.add(board[++x1][++y1]);}
    		if(x<7&&y>0) {ps1.add(board[++x][--y]);}
    		
    	}
    	else if(Diagonal.TOP.equals(pref))
    	{
    		if(x1>0&&y1>0) {ps.add(board[--x1][--y1]);}
    	    if(x>0&&y<7) {ps1.add(board[--x][++y]);}
    	}
    	
    }
    st.add(ps);
    st.add(ps1);
	return st;
  }
  protected ArrayList<ArrayList<JButton>> captureSequences(JButton gbox)
  {   ArrayList<ArrayList<JButton>> array=new ArrayList<>(0);
	  String n=gbox.getActionCommand();
	  Scanner b=new Scanner(n);
	  int x1=b.nextInt();
	  int y1=b.nextInt();
	  b.close();
	  boolean for_king= gbox.getText().startsWith("k");
	  ArrayList<ArrayList<JButton>> upwards=this.getDiagonals(x1, y1, Diagonal.TOP);
	  ArrayList<ArrayList<JButton>> downwards=this.getDiagonals(x1, y1, Diagonal.BOTTOM);
      for(int k1=0;k1<2;++k1)
	  
    {
    	  for(int k=0;k<2;++k)
      {
    	 //Begin of capture sequence loop 
    	  ArrayList<JButton> obj=(downwards.get(k));
    	  if(obj.size()<2) {continue;}
    	  boolean flag=true;
    	
 if(!(!(((obj.get(0))).getText().equals(""))&&(!gbox.getText().replace("k","").equals(((obj.get(0))).getText().replace("k","")))))     		  
    		  {flag=false;}
    		  
 if(!((obj.get(1))).getText().equals("")) 
              {flag=false;}
    		  
  if(flag) {array.add(obj);}
    	  
      }
    if(gbox.getText().equals("rc")) {return array;} 	  
    else if((k1==0)&&gbox.getText().equals("bc")) {array.clear();} 	  
    downwards=new ArrayList<>(upwards);
    
    }
	  return array;
  }
  
   public boolean canCapture(JButton jbut)
   {
	   
	  ArrayList<ArrayList<JButton>> capt_seq= this.captureSequences(jbut);
	  if(capt_seq.size()==0)
	  {return false;}
	   
	   
	   return true;
   }
   protected Object moves(JButton butt)
   {
	boolean for_king=butt.getText().startsWith("k");   
	if(canCapture(butt))
	{return captureSequences(butt);}
	ArrayList<JButton> butts=new ArrayList<>(0);
	String s=butt.getActionCommand();
	Scanner r=new Scanner(s);
	int x1=r.nextInt();
	int y1=r.nextInt();
	r.close();
	if(x1<7&&y1<7&&(for_king||butt.getText().equals("rc")))
	    {
		JButton but=board[x1+1][y1+1];
		if(but.getText().isEmpty())
		{butts.add(but);}
		}
	if(x1<7&&y1>0&&(for_king||butt.getText().equals("rc"))) 
	     {
		JButton but=board[x1+1] [y1-1];
		if(but.getText().isEmpty())
		{butts.add(but);}
		 }
	if(x1>0&&y1<7&&(for_king||butt.getText().equals("bc")))
	    {
		JButton but=board[x1-1][y1+1];
		if(but.getText().isEmpty())
		{butts.add(but);}
		}
	if(x1>0&&y1>0&&(for_king||butt.getText().equals("bc")))
	    {
		JButton but=board[x1-1][y1-1];
		if(but.getText().isEmpty())
		{butts.add(but);}
		}   
	if(butts.size()==0)
	{return null;}
	return butts;  
   }
   public Color getRequiredColor(JButton ar)
   {
	   String gg=ar.getActionCommand();
	   Scanner read= new Scanner(gg);
	   int x1=read.nextInt();
	   int y1=read.nextInt();
	   read.close();
	   if(y1>0) {
		Color n=board[x1][y1-1].getBackground();
		if(n.equals(Color.WHITE))
		{return Color.black;}
		else
		{return Color.white;}
	   }
	   else
	   {
		Color n=board[x1][y1+1].getBackground();   
		 if(n.equals(Color.WHITE))  
		 {return Color.black;}
		 else
		 {return Color.white;}
	   }
   }
   protected boolean crown(JButton butt)
    {
	   String s=butt.getActionCommand();
	   int x=Integer.parseInt((s.charAt(0)+""));
	   if(butt.getText().equals("rc")&&x==7)
	   {
		butt.setText("krc");
		if(this.sfx.isSelected())
		{MusicalArcadeGame.playSound(DependancyManager.loadDependancy(71));}
		JOptionPane.showMessageDialog(butt,"A Red King Has Been Crowned here!","Coronation!",JOptionPane.INFORMATION_MESSAGE);
		return true;
	   }
	   else if(butt.getText().equals("bc")&&x==0)
	   {
		   butt.setText("kbc");
		   if(this.sfx.isSelected())
		   {MusicalArcadeGame.playSound(DependancyManager.loadDependancy(71));}
		   JOptionPane.showMessageDialog(butt,"A Black King Has Been Crowned here!","Coronation!",JOptionPane.INFORMATION_MESSAGE); 
		   return true;
	   }
	   return false;
    }
   protected boolean piecesLeft(String pl)
   {
	   for(int i=0;i<8;++i)
	   {
		   for(int j=0;j<8;++j )
		   {
			   if(board[i][j].getText().contains(pl.replace("k", ""))) 
			   {return true;}
			   
		   }
		   
	   }
	   
	   return false;
   }
   protected boolean movesLeft(String pl)
   {
	   pl=pl.replace("k","");
	   for(int i=0;i<8;++i)
	   {
		   for(int j=0;j<8;++j)
		   {
		if(board[i][j].getText().contains(pl))   
		{
			if(moves(board[i][j])!=null) 
		    {
				return true;
			} 
		}
		   }   
		   
	   }
	   return false;
	   }
   protected static String getContrary(String type)
   {
	if(type.contains("rc"))
	{return "bc";}
	else if(type.contains("bc"))
	{return "rc";}
	   return "";
   }
   protected void displayResults(String winner)
   {
	   stopThemeSong();
	   this.disableSoundControls();
	   JFrame dialog=new JFrame(winner+" wins!!!");
	   JLabel lab=new JLabel();
	   if(winner.equals("rc"))
   	   {lab.setIcon(new ImageIcon(rc));
   	    lab.setText("RED WINS");
   	   }
	   else
	   {
		   lab.setIcon(new ImageIcon(bc));
		   lab.setText("BLACK WINS");
	   }
	   lab.setFont(new Font("algerian",Font.BOLD,25));
	   lab.setForeground(Color.BLUE);
	  lab.setVerticalTextPosition(SwingConstants.TOP);
	  dialog.add(lab);
	  dialog.setSize(200,200);
	  dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  dialog.setResizable(false);
	  dialog.setIconImage(ImageManager.chk_1);
	  dialog.setVisible(true);
   }
   protected boolean setValidButtons(String type)
   {
	   boolean flag=false;
	   valids=new ArrayList<JButton>(0);
	   for(int i=0;i<8;++i)
	   {
		   for(int j=0;j<8;++j)
		   {
			   JButton btf=board[i][j];
			   if(btf.getText().contains(type)&&this.canCapture(btf))
			   {flag=true;
			    valids.add(btf);
			   }
		   }
		   
	   }
	   return flag;
   }
	public static void main(String[] args) 
	{
		try {
		SwingUtilities.invokeLater(()->new Checkers());
		    }
		catch(Throwable obj)
		{
		   ErrorManager em=new ErrorManager(new Frame("Checkers error"),"Runtime error,yikes!");	
			em.display("There seems to be a slight issue.Report this", obj);
	    }
	}
	protected static enum Diagonal
	{TOP,BOTTOM}
	@Override
	public String getName() {
		
		return "Checkers";
	}
	@Override
	public Component getMainFrame()
	{
		
		return this.cfrm;
	}
	@Override
	public File getRules() {
	
		return DependancyManager.loadDependancy(26);
	}
	
}
//End of Basic methods