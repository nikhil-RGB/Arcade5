import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
@SuppressWarnings("unused")
public class ErrorManager extends Dialog implements utilities.Helper
{
/**
	 * SUID
	 */
	private static final long serialVersionUID = 9054538745029295374L;

public ErrorManager(Frame frm,String title)
{super(frm,title,true);}

public void display(String s,Throwable ex){
Label l=new Label(s);
TextArea ts=new TextArea(30,100);
for(int i=0;i<ex.getStackTrace().length;i++)
{ts.append(ex.getStackTrace()[i].toString()+"\n");
}
ts.setEditable(false);
l.setFont(new Font("algerian",Font.BOLD,26));
ts.setFont(new Font(Font.SANS_SERIF,Font.BOLD,21));
this.add(ts,BorderLayout.CENTER);
this.add(l,BorderLayout.NORTH);
Button but=new Button("CLOSE APP(ONLY CHOICE)");
but.setFont(new Font("algerian",Font.BOLD,26));
but.addActionListener((ev)->{System.exit(0);});
this.add(but,BorderLayout.SOUTH);
try{
Image bruuhh=ImageManager.nr_1;
this.setIconImage(bruuhh);
}
catch(Throwable bums){}
this.setSize(560,560);
this.setVisible(true);
}
}