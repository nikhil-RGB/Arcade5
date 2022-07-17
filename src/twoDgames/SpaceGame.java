package twoDgames;
import java.awt.*;
import utilities.*;
import animations.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.Timer;
@SuppressWarnings({ "unused", "serial" })
public class SpaceGame extends MusicalArcadeGame implements ActionListener,Playable,AutoRulesManager
{
	static 
	{loadImages();}
	public static void main(String[] args)
	{   
		if(cgame!=null)
	    {
		JOptionPane.showMessageDialog(null,"Cannot create another game while an instance of SpaceGame is already active","Close previous game first",JOptionPane.ERROR_MESSAGE);
		return;
		}
		SpaceGame gg=new SpaceGame();
		gg.gameStart();
	}
static long tint;
public static SpaceGame cgame;
static File coll;
static Timer t;
SpaceShip sp;
GameBoard gb;
static JFrame mframe;
static int delayMS=10;
static ImageIcon sp_img;
static ImageIcon as_img;
static int width=500;
static int height=500;
public static class Asteroid extends Sprite {
	   public static ArrayList<Asteroid> asteroids;
	   public static ArrayList<Asteroid> buffer;
	   static
	   {
		   asteroids=new ArrayList<>(0);
		   buffer=new ArrayList<>(0);
	    }
		public Asteroid(int x, int y,boolean flg) 
		{
			super(x, y);
			super.width=45;
			super.height=45;
			if(flg)
			{asteroids.add(this);}
			else
			{buffer.add(this);}
			Random r=new Random();
			super.speed_x=r.nextInt(2)+1;
			super.speed_y=r.nextInt(3)+1;
		}

		@Override
		public void move() 
		{
		if(this.getBounds().intersects(right))
		{
			this.suddenChange();
			if(this.speed_x>0)
			{this.speed_x=-1*this.speed_x;}
		}
		else if(this.getBounds().intersects(left))
		{
			this.suddenChange();
			if(this.speed_x<0)
			{this.speed_x=this.speed_x*-1;}
		}
		if(this.getBounds().intersects(bottom))
		{
			this.suddenChange();
			if(this.speed_y>0)
			{this.speed_y=this.speed_y*-1;}
		}
		else if(this.getBounds().intersects(top))
		{
			this.suddenChange();
			if(speed_y<0)
			{this.speed_y=-1*speed_y;}
		}
		x+=speed_x;
		y+=speed_y;
		}
		public void collide(Asteroid r)
		{   
			if(cgame.sfx.isSelected())
			{   
				boolean othercond= (System.currentTimeMillis()-tint)>2000;
				if(othercond)
				{
				MusicalArcadeGame.playSound(coll);
				tint=System.currentTimeMillis();
				}
			}
			int x1=r.x;
			int y1=r.y;
			int x2=this.x;
			int y2=this.y;
			r.x-=2;
			r.y-=2;
			r.suddenChange();
			this.x+=2;
			this.y+=2;
			this.suddenChange();
		}
		public void completeChange()
		{
		this.speed_x=this.speed_x*-1;	
		this.speed_y=this.speed_y*-1;	
		}
	    public void suddenChange()
	    {
	    Random r=new Random();
	    super.speed_x=r.nextInt(4)+1;
	    super.speed_y=r.nextInt(4)+1;
	    if(r.nextBoolean())
	    {super.speed_x=-1*speed_x;}
	    if(r.nextBoolean())
	    {super.speed_y=-1*speed_y;}
	    }
	    public void yReflect()
	    {
	    	this.speed_y=this.speed_y*-1;
	    }
	    public void xReflect()
	    {
	    	
	    	this.speed_x=-1*this.speed_x;
	    }
	    public boolean checkCollisions(SpaceShip ast,int x) 
	    {
	    	Rectangle myPos=this.getBounds();
	    	if(myPos.intersects(ast.getBounds())) 
	    	{
	    		return true;
	    	}
	    	for(int k=x;k<asteroids.size();++k)
	    	{
	    		Asteroid obj=asteroids.get(k);
	    		Rectangle oPos=obj.getBounds();
	    		if(myPos.intersects(oPos))
	    		{collide(obj);}
	    	}
			return false;
			
	   }
	    public static void updateAsteroids(SpaceShip obj)
	    {
	    	for(int k=0;k<asteroids.size();++k)
	    	{
	    		Asteroid ast=asteroids.get(k);
	    		boolean flag=ast.checkCollisions(obj, k+1);
	    		if(flag) {
	    			t.stop();
	    			cgame.quitStrategy();
	    			endGame();
	    			return;
	    			     }
	    	}
	    	asteroids.addAll(buffer);
	    	buffer.clear();
	   }
	    public static void moveAndUpdateAll(SpaceShip obj)
	    {
	    	for(Asteroid as:asteroids)
	    	{as.move();}
	    	updateAsteroids(obj);
	    }
	}
public SpaceGame() 
{   
	super(DependancyManager2d.loadDependancy(0,DependancyManager2d.DependancyType.SOUND));
	cgame=this;
	JFrame frm=new JFrame("ESCAPE!");
	JButton pause=createThreadController();
	pause.setOpaque(false);
	pause.setMnemonic('p');
	JMenuBar jb=new JMenuBar();
	JMenu help=new JMenu("help");
	JMenuItem rles=new JMenuItem("Rules");
	rles.addActionListener((ev)->{
		t.stop();
		JOptionPane.showMessageDialog(null,getText(),"Rules",JOptionPane.INFORMATION_MESSAGE);
	    t.start();
	});
	help.add(rles);
	jb.add(help);
	frm.setJMenuBar(jb);
	mframe=frm;
	frm.setSize(width,height);
	frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	GameBoard obj=new GameBoard();
	obj.setLayout(new BorderLayout());
	obj.add(pause,BorderLayout.NORTH);
	obj.setBackground(Color.BLACK);
	obj.setFocusable(true);
	obj.addKeyListener(new KeyAdapter() 
	{
	public void keyPressed(KeyEvent ke)	
	{sp.keyPressed(ke);}
	public void keyReleased(KeyEvent ke)
	{sp.keyReleased(ke);}
	});
	frm.add(obj);
	gb=obj;
	Asteroid.asteroids.clear();
	Asteroid.buffer.clear();
	sp=new SpaceShip(width/2,height/2);
	new Asteroid(20,25,true);
	new Asteroid(200,350,true);
	new Asteroid(10,150,true);
	new Asteroid(10,300,true);
	frm.setResizable(false);
	frm.setVisible(true);
	frm.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent we)
		{
		 t.stop();
		 cgame=null;
         quitStrategy();		 
		}
		public void windowLostFocus(WindowEvent we)
		{t.stop();}
		public void windowGainedFocus(WindowEvent we)
		{t.start();}
	});
 super.playThemeSong();
 super.provideDefaultFrame(as_img.getImage(),true);
}
public void gameStart()
{
t=new Timer(delayMS,this);
t.start();
}
public static void endGame()
{
JOptionPane.showMessageDialog(null,"The game has ended,You died","Game ended",JOptionPane.INFORMATION_MESSAGE);	
mframe.setVisible(false);
mframe.dispose();
cgame=null;
}
public class GameBoard extends JPanel
{

	private static final long serialVersionUID = 1907401576237712862L;
public GameBoard()
{
	super();
	this.prepareBorders();
}

@Override
public void paintComponent(Graphics g)
{
super.paintComponent(g);
g.drawImage(sp_img.getImage(),sp.x, sp.y,null);
for(Asteroid obj:Asteroid.asteroids)
{g.drawImage(as_img.getImage(),obj.x,obj.y,null);}
}
public void prepareBorders()
{
Sprite.left=new Rectangle(0,0,10,height);
Sprite.bottom=new Rectangle(0,height-10,width,10);
Sprite.right=new Rectangle(width-10,0,10,height);
Sprite.top=new Rectangle(0,0,width,10);
}
}
@Override
public void actionPerformed(ActionEvent arg0) 
{
if(!cgame.gb.hasFocus())
{
cgame.gb.requestFocus();	
}
sp.move();
Asteroid.moveAndUpdateAll(sp);
gb.repaint();	
}
public static void loadImages()
{
sp_img=new ImageIcon(new ImageIcon("resources_n//2D_resources//astronaut.png").getImage().getScaledInstance(Sprite.swidth,Sprite.sheight,Image.SCALE_SMOOTH));
as_img=new ImageIcon(new ImageIcon("resources_n//2D_resources//asteroid.png").getImage().getScaledInstance(45,45,Image.SCALE_SMOOTH));
coll=DependancyManager2d.loadDependancy(1,DependancyManager2d.DependancyType.SOUND);
}
@Override
public String getText() {
String arr= "<html>This is game which checks your reflexes<br>"
		+ "What makes SpaceGame so unique is that The collisions of the asteroids<br>"
		+ "<br> do not employ classical mechanics, but causes sudden acceleration or retardation, making<br>"
		+ " it tough to predict its path. Survive as long as possible, move using arrow keys<br> "
		+ "You die if you get hit by an asteroid";
	return arr;
}
@Override
public File getRules() 
{
	
	return null;
}
@Override
public void quitStrategy()
{
super.stopThemeSong();
super.disableSoundControls();
super.getDefaultFrame().setVisible(false);
super.getDefaultFrame().dispose();
}
public JButton createThreadController()
{
	JButton btn=new JButton("Pause");
	btn.setPreferredSize(new Dimension(60,50));
	btn.setFont(new Font("algerian",Font.BOLD,12));
	btn.setBackground(Color.CYAN);
	btn.setForeground(Color.RED);
	ActionListener al=(ae)->
	{
		JButton src=(JButton)(ae.getSource());
		String s=src.getText();
		if(s.equals("Pause"))
		{
			t.stop();
			src.setText("Resume");
		    src.setForeground(Color.GREEN);
		}
		else
		{
		cgame.gb.requestFocus();
		t.start();
		src.setText("Pause");	
		src.setForeground(Color.RED);
		}
	};
	btn.addActionListener(al);
	return btn;
}
}