package utilities;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
@SuppressWarnings("unused")
public final class GameSaver implements Helper
{
public volatile SavableArcadeGame object;	
public boolean saveGame(SavableArcadeGame obj,String filename)
{
File dir=new File(obj.getName());
dir.mkdir();
dir=new File(obj.getName()+"//"+filename+".ser");
try
{
	boolean s=dir.createNewFile();
	if(!s) {return false;}
	FileOutputStream strm=new FileOutputStream(dir.getPath());
	ObjectOutputStream objs=new ObjectOutputStream(strm);
	objs.writeObject(obj);
	objs.close();
	strm.close();
} 
catch (IOException e) 
{   e.printStackTrace();
	return false;
}
return true;
}
public void applySaveGUI(SavableArcadeGame object,Image ico)
{
	JFrame frm=new JFrame("Save your game");
	JTextField f=new JTextField("Enter the name of the save file");
    f.setToolTipText("<html>Press Enter once you are done<br>This name is a unique identifier of<br>your game.Do not use file extensions");
    f.addActionListener((ev)->{
    	JTextField tf=(JTextField)(ev.getSource());
    	if(!this.saveGame(object, tf.getText())) 
    	{tf.setText("Please use a different name.");return;}
    	else
    	{frm.setVisible(false);
    	 frm.dispose();
    	}
    });
    frm.add(f);
    frm.setSize(200,100);
    frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frm.setIconImage(ico);
    frm.setVisible(true);
}
public Optional<SavableArcadeGame> readGame(String filename,String name)
{
	if(!new File(name+"//"+filename+".ser").exists()) {return Optional.empty();}
	SavableArcadeGame object;
	FileInputStream fs=null;
	ObjectInputStream os=null;	
	try {
		fs=new FileInputStream(name+"//"+filename+".ser");
		os=new ObjectInputStream(fs);
        object=(SavableArcadeGame)(os.readObject());
	}
     catch(IOException|ClassNotFoundException|ClassCastException e) {return Optional.empty();}
	finally 
	{
		try {
			os.close();
			fs.close();
		}
		catch(IOException ex) 
		{}
	}
return Optional.of(object);
}
public void applyOutputGUI(String nam,boolean va,Image ic,Image iska_icon)
{
JFrame frm=new JFrame("Load your game");	
JTextField saviour=new JTextField("Enter name here");
saviour.setToolTipText("<html>This is where you enter your filename,<br> press enter once done");
saviour.addActionListener((ev)->{
JTextField jtf=(JTextField)(ev.getSource());	
Optional<SavableArcadeGame> obj=this.readGame(jtf.getText(),nam);	
if(!obj.isPresent()) {saviour.setText("Invalid name.");return;}
else 
{
	SavableArcadeGame read_game=obj.get();
    frm.setVisible(false);
    frm.dispose();
    if(va)
    {read_game.getMainFrame().setVisible(true);
     Component cmp=read_game.getMainFrame();
     if(cmp instanceof java.awt.Frame)
     {((Frame)(cmp)).setIconImage(ic);}
     else
     {((JFrame)(cmp)).setIconImage(ic);}
     File f=new File(nam+"//"+jtf.getText()+".ser");
     f.delete();
     object=read_game;
     }
}
});
frm.setSize(200,100);
frm.add(saviour);
frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
frm.setIconImage(iska_icon);
frm.setVisible(true);
}
public static interface SerializableActionListener extends ActionListener,Serializable
{
public static final long serialVersionUID=1002L;	
}
public static class SerializableMouseListener extends MouseAdapter implements Serializable
{
private static final long serialVersionUID = 1001L;
}
public static class SerializableWindowListener extends WindowAdapter implements Serializable
{private static final long serialVersionUID=223343334221332L;}
public static class SerializableThread extends Thread
{
	

}
public static interface SerializableItemListener extends ItemListener,Serializable
{
public static final long serialVersionUID=33L;	
}
}