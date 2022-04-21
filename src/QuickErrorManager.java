import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
@SuppressWarnings("unused")
public class QuickErrorManager implements utilities.Helper
{
public static void manage(String msg,String... instructions)
{
Frame frm=new Frame("A lil issue...");
frm.setIconImage(ImageManager.nr_1);
TextField txt=new TextField(msg);
txt.setForeground(Color.GREEN);
txt.setFont(new Font("algerian",Font.BOLD,30));
txt.setEditable(false);
TextArea txta=new TextArea(20,instructions.length+5);
txta.setBackground(Color.CYAN);
txta.setFont(new Font(Font.SANS_SERIF,Font.BOLD,21));
for(String s:instructions)
{txta.append(s+"\n");}
txta.setEditable(false);
Button close=new Button("Close,chill out,bruh");
close.setBackground(Color.BLUE);
close.setFont(new Font("algerian",Font.BOLD,30));
close.addActionListener((ev)->{frm.setVisible(false);frm.dispose();});
frm.add(txta);
frm.add(txt,BorderLayout.NORTH);
frm.add(close,BorderLayout.SOUTH);
frm.setSize(200,200);
frm.setVisible(true);
}
}