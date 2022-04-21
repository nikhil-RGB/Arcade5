package twoDgames;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
@SuppressWarnings("unused")
public class TwoDAppManager 
{
static String[] descripns= new String[] {"Run from the asteroids!!!!"};
static ImageIcon[] icons;
	public static void main(String[] args)
	{   
		if(!DependancyManager2d.checkDependancies())
		{
			JOptionPane.showMessageDialog(null,"<html>Resource files corrupted or damaged,failed<br>to open 2D games menu","Resources corrupted",JOptionPane.ERROR_MESSAGE);
			return;
		}
		loadImageIcons();
		JFrame frm=new JFrame("Two-D games!");
		JPanel panel=new JPanel();
		JScrollPane jsp=new JScrollPane(panel);
		jsp.setViewportBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.RED,Color.BLUE));
		JLabel view=new JLabel("<html>Play 2-D games in this section<br>This section is under development");
		view.setFont(new Font("algerian",Font.BOLD,18));
		view.setForeground(Color.CYAN);
		jsp.setColumnHeaderView(view);;
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JButton jbt[]=new JButton[] {new JButton("Space Game")};
        ActionListener al=(ev)->{
        	 JButton obj=(JButton)(ev.getSource());
        	 try
        	{
				Method m=Class.forName(obj.getActionCommand()).getMethod("main",String[].class);
				m.invoke(null,new String[1]);
			} 
        	catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        	{
        		e.printStackTrace();
	            JOptionPane.showMessageDialog(null,"Could not open game","Error",JOptionPane.ERROR_MESSAGE);
			}
              
        };
       Random r=new Random();
   	 for(int k=0;k<jbt.length;++k)
   	 {	
   	JButton jbut=jbt[k];
   	jbut.addActionListener(al);
   	jbut.setActionCommand("twoDgames."+jbut.getText().replace(" ", ""));
   	jbut.setToolTipText(descripns[k]);
   	jbut.setFont(new Font(Font.SANS_SERIF,Font.BOLD,36));
   	int red=r.nextInt(250)+1;
   	int blue=r.nextInt(250)+1;
   	int green=r.nextInt(250)+1;
   	Color c=new Color(red,green,blue);
   	jbut.setBackground(c);
   	jbut.setForeground(c);
   	jbut.setHorizontalTextPosition(SwingConstants.CENTER);
    jbut.setIcon(icons[k]);
   	panel.add(jbut);
   	 }
   	 JMenu rules=new JMenu("Rules");
   	 JMenuItem mi=new JMenuItem("How to access rules");
   	 rules.add(mi);
   	 mi.addActionListener((ev)->
   	 {
   		String text="<html>The rules option for each game, is present in<br>"
				+ " the 'help' menu of the menu bar of each game's individual<br> window"
				+ ", click on a game and then select Help->Rules<br>"
				+ "from the menu bar, to see it's rules.";
		JOptionPane.showMessageDialog(frm, text,"Rules",JOptionPane.INFORMATION_MESSAGE);
   	 });
   	 JMenuBar mb=new JMenuBar();
   	 mb.add(rules);
   	frm.setJMenuBar(mb);
    frm.add(jsp);
    frm.setSize(500,500);
    frm.setResizable(false);
    frm.pack();
    frm.setVisible(true);
    frm.setIconImage(icons[0].getImage());
    frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
 public static void loadImageIcons()
 {
	 ImageIcon img=new ImageIcon(DependancyManager2d.loadDependancy(1, DependancyManager2d.DependancyType.IMAGE).getPath());
	 icons=new ImageIcon[] {img};
 }
}
