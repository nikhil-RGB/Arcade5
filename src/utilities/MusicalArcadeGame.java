//This interface specifies the required methods
//if any class specifies music for a theme song
//or sfx sounds.
package utilities;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;
public class MusicalArcadeGame implements java.io.Serializable 
{
private static final long serialVersionUID = 10085488108915908L;
protected File f_song;
protected JPanel box;
protected JCheckBox music;
protected JCheckBox sfx;
private JFrame mframe;
private transient Clip clp;
public static void playSound(File f)
{
AudioInputStream p;
try {
	p = AudioSystem.getAudioInputStream(f);
	Clip cp=AudioSystem.getClip();
	cp.open(p);
	cp.start();
} 
catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
{
JOptionPane.showMessageDialog(null, "Cannot play sounds","Error",JOptionPane.ERROR_MESSAGE);	
}
}
public void quitStrategy()
{
this.stopThemeSong();
this.disableSoundControls();
}
public MusicalArcadeGame()
{
Font font=new Font("algerian",Font.BOLD,20);
music=new JCheckBox("MUSIC",true);
sfx=new JCheckBox("SFX",true);
music.setBackground(Color.GREEN);
music.setFont(font);
sfx.setBackground(Color.GREEN);
sfx.setFont(font);
GameSaver.SerializableItemListener il=new GameSaver.SerializableItemListener() {
	private static final long serialVersionUID=234667891109L;
	@Override
	public void itemStateChanged(ItemEvent ie) {
		JCheckBox src=(JCheckBox)(ie.getSource());
		if(ie.getStateChange()==ItemEvent.DESELECTED)
		{
			src.setBackground(Color.red);
		    if(src==music) {stopThemeSong();}
		}
		else
		{
		 src.setBackground(Color.green);
		 if(src==music) {playThemeSong();}
		}
		
	}
};
music.addItemListener(il);
sfx.addItemListener(il);
music.setToolTipText("<html>This control can be used for swithing music of the game<br>"
		+ " on/off.Green means on,red means off.Click to toggle between states.");
sfx.setToolTipText("<html>This cotrol can be used to switch on/off sounds like in-game explosions,"
		+ "click sounds etc.Click to toggle states.");
box=new JPanel();
box.setLayout(new BoxLayout(box,BoxLayout.Y_AXIS));
box.add(music);
box.add(sfx);
}
public MusicalArcadeGame(File f)
{
	this();
	f_song=f;
}
//Begins playing the theme song of the specified game
public void playThemeSong() 
{
	if((clp!=null)&&(clp.isRunning())) {return;}
	try 
	{
		AudioInputStream as=AudioSystem.getAudioInputStream(f_song);
		this.clp=AudioSystem.getClip();
		clp.open(as);
		clp.start();
		clp.loop(Clip.LOOP_CONTINUOUSLY);
	} 
	catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
	{
	e.printStackTrace();
	}	
}
//Stops the theme song of the so-specified game
public void stopThemeSong() 
{
	if((clp!=null)&&(!clp.isRunning())) {return;}
	if(clp==null) {return;}
	clp.stop();
    clp.close();
}
//Optional method-for playing sfx click,by default plays resources_n//Click.wav
public void playClickSound()
{
if(!sfx.isSelected()) {return;}
File f=new File("resources_n//Click.wav");
try
{
	AudioInputStream m=AudioSystem.getAudioInputStream(f);
	Clip cp=AudioSystem.getClip();
	cp.open(m);
	cp.start();
}
catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
{
	e.printStackTrace();
}

}
//Optional method-for obtaining a reference to the playing Clip object.
public Clip getReferencedClip()
{return clp;}
//Gets panel
public JPanel getJPanel()
{return this.box;}
public void setMusicFile(File m)
{
this.f_song=m;	
}
public File getMusicFile()
{
return this.f_song;
}
public JCheckBox getMusicControl()
{return music;}
public JCheckBox getSfxControl()
{return sfx;}
public void setSfxControl(JCheckBox s)
{sfx=s;}
public void setMusicControl(JCheckBox b)
{music=b;}
public void playExplosionSound()
{
if(!sfx.isSelected()) {return;}
try {
	AudioInputStream ais=AudioSystem.getAudioInputStream(new File("resources_n//Explosion.wav"));
	Clip cp=AudioSystem.getClip();
	cp.open(ais);
	cp.start();
   } 
catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
{
e.printStackTrace();	
}	
}
public void disableSoundControls()
{this.music.setEnabled(false);
 this.sfx.setEnabled(false);
}
public Frame provideDefaultFrame(Image ico,boolean con)
{
	JFrame jfrm=new JFrame("Music/sfx controller");
	jfrm.setLocationRelativeTo(null);
	jfrm.setAutoRequestFocus(false);
	jfrm.setIconImage(ico);
	MotionPanel mp=new MotionPanel(jfrm);
	mp.setLayout(new BoxLayout(mp,BoxLayout.Y_AXIS));
	mp.add(music);
	mp.add(sfx);
	jfrm.add(mp);
	jfrm.setSize(50,110);
	jfrm.setResizable(false);
	jfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	mframe=jfrm;
	jfrm.setVisible(con);
	return jfrm;
}
public Frame getDefaultFrame()
{return mframe;}
public static class MotionPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Point initialClick;
    @SuppressWarnings("unused")
	private JFrame parent;

    public MotionPanel(final JFrame parent){
    this.parent = parent;

    addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            initialClick = e.getPoint();
            getComponentAt(initialClick);
        }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {

            // get location of Window
            int thisX = parent.getLocation().x;
            int thisY = parent.getLocation().y;

            // Determine how much the mouse moved since the initial click
            int xMoved = e.getX() - initialClick.x;
            int yMoved = e.getY() - initialClick.y;

            // Move window to this position
            int X = thisX + xMoved;
            int Y = thisY + yMoved;
            parent.setLocation(X, Y);
        }
    });
    }
}
}