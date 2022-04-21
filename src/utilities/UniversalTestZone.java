package utilities;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;
public final class UniversalTestZone implements Helper {

	public static void main(String[] args) {
		colorFrameTest();
	}
    public static void colorFrameTest()
    {   
    	ArrayList<Color> fb=new ArrayList<>(0);
    	fb.add(Color.RED);
    	fb.add(Color.GREEN);
    	ArrayList<String> name=new ArrayList<String>(0);
    	name.add("1-James");
    	name.add("2-Charles");
    	name.add("3-Wheaton");
    	name.add("4-Charlie");
    	PlayerColorSelector pc=new PlayerColorSelector(4,"Select Colors!",fb,null,true,name);
		pc.setSize(500,500);
		pc.setLocationRelativeTo(null);
        pc.setVisible(true);
        Hashtable<String,Color> hsht=pc.results;
        JButton[] btns=new JButton[4];
        for(int k=0;k<btns.length;++k)
        {
        	btns[k]=new JButton("Press me");
        	btns[k].setBackground(hsht.get(pc.pane.getTitleAt(k)));
        }
    	JFrame frm=new JFrame();
    	Box box=Box.createVerticalBox();
    	for(JButton btn:btns)
    	box.add(btn);
    	frm.add(box);
    	frm.setSize(200, 200);
    	frm.pack();
    	frm.setVisible(true);
    }
}
