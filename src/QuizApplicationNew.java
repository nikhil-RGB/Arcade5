 
/*
 * AUTHOR:NIKHIL NARAYANAN
 * TYPE:NON-NETWORK DESKTOP APPLICATION
 * PART OF: ARCADE 2.0 ALPHA VERSION
 * NAME:QUIZ APPLICATION 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import utilities.*;
@SuppressWarnings("unused")
public final class QuizApplicationNew extends Object implements Serializable,utilities.Playable
{
   private static final long serialVersionUID =11092003L;
   private static final Image ico;
	private  Frame frm;
	private static int c=0;
	private static QuizApplicationNew savable;
   private  Choice questionnos;
   private  TextField question;
   private  TextField ans1;
   private  TextField ans2;
   private  TextField ans3;
   private  TextField ans4;
   int cc=1;
   private  Button done;
   private  Button add;
   private  Button delete;
   protected ArrayList<String> questions;
   private  ArrayList <String[]> answers;
   protected String password;
   private  String user;
   static
   {
	   ico=new ImageIcon(DependancyManager.getDependancy(13)).getImage();
   }
   public static void main(String[] args)
   {    
       try{
   	QuizApplicationNew.showInitialDisplay();
        }
        catch(Throwable obj)
        {
        ErrorManager mj=new ErrorManager(new Frame("ERROR"),"Error in Quiz app");
        mj.display("HELP!!Quiz app doesnt feel so good...",obj);
        }
}
   //Constructor:
   public QuizApplicationNew()
   {}
	public void main() 
	{
   frm=new Frame("Create your very own quiz");
   frm.setIconImage(ico);
   questionnos=new Choice();
   questionnos.add("Q1");
   questionnos.setFont(new Font("comicsans",Font.ITALIC,27));
   question=new TextField(15);
   question.setText("Enter your question here");
   question.setFont(new Font("algerian",Font.BOLD,60));
   ans1=new TextField(5);
   ans1.setText("Correct answer here");
   ans1.setBackground(Color.GREEN);
   ans1.setFont(new Font("algerian",Font.BOLD,30));
   ans2=new TextField(5);
   ans2.setText("Wrong answer here");
   ans2.setBackground(Color.RED);
   ans2.setFont(new Font("algerian",Font.BOLD,30));
   ans3=new TextField(5);
   ans3.setText("Wrong answer here");
   ans3.setBackground(Color.RED);
   ans3.setFont(new Font("algerian",Font.BOLD,30));
   ans4=new TextField(5);
   ans4.setText("Wrong answer here");
   ans4.setBackground(Color.RED);
   ans4.setFont(new Font("algerian",Font.BOLD,30));
   done=new Button("Save");
   done.setFont(new Font("algerian",Font.BOLD,25));
   done.addActionListener(new SerializableActionListener() {
   	private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ev)
   		{int index=questionnos.getSelectedIndex();
                                 questions.set(index,question.getText());
                                 answers.set(index,new String[] {ans1.getText(),ans2.getText(),ans3.getText(),ans4.getText()});
                                
   		}
   });
   add=new Button("Add question");
   add.setFont(new Font("algerian",Font.BOLD,25));
   delete=new Button("Delete question");
   delete.setBackground(Color.RED);
   delete.setFont(new Font("algerian",Font.BOLD,25));
   delete.addActionListener(new SerializableActionListener() {
   	private static final long serialVersionUID=2L;
   		public void actionPerformed(ActionEvent ev)
   		{
   delete.setLabel("delete");	
   if(!(questions.size()>1&&answers.size()>1)) {delete.setLabel("Cannot Delete Q1");return;}
   int vals=questionnos.getSelectedIndex();	
   questions.remove(vals);	
   answers.remove(vals);
   questionnos.remove(vals);
   int lauda=questionnos.getItemCount();
   questionnos.removeAll();
   for(int k=0;k<lauda;++k)
   {
    questionnos.add("Q"+(k+1)+"");
   }
   questionnos.select(questionnos.getItemCount()-1);
   question.setText((String)questions.get(lauda-1));
   ans1.setText((String)answers.get(lauda-1)[0]);
   ans2.setText((String)answers.get(lauda-1)[1]);
   ans3.setText((String)answers.get(lauda-1)[2]);
   ans4.setText((String)answers.get(lauda-1)[3]);
   --cc;
   add.setVisible(true);
   }
   });
   questions=new ArrayList<>(0);
   questions.add("Question");
   answers=new ArrayList<>(0);
   answers.add(new String[] {"Answer","Answer","Answer","Answer"});
   Panel p1=new Panel();
   p1.setLayout(new GridLayout(3,3,1,1));
   p1.add(questionnos);
   Panel p2=new Panel();
   p2.setLayout(new GridLayout(2,2,5,5));
   p2.add(ans1);
   p2.add(ans2);
   p2.add(ans3);
   p2.add(ans4);
   Panel side=new Panel();
   side.setLayout(new BoxLayout(side,BoxLayout.Y_AXIS));
   side.add(p1);
   side.add(done);
   side.add(add);
   side.add(delete);
   Panel main = new Panel();
   main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
   main.add(question);
   main.add(p2);
   frm.add(main);
   frm.add(side,BorderLayout.EAST);
   add.addActionListener(new SerializableActionListener()
   		{
		private static final long serialVersionUID = 3L;

			public void actionPerformed(ActionEvent ev)
   		{
   for(int k=0;k<2;++k)	
   {   questions.ensureCapacity(cc);
       if(k==1) {questions.add(cc-1,"");}
   	questions.set(cc-1,question.getText());
       answers.ensureCapacity(cc);
       if(k==1) {answers.add(cc-1,new String[0]);}
       answers.set(cc-1,new String[] {ans1.getText(),ans2.getText(),ans3.getText(),ans4.getText()});
       question.setText("Enter Question here");
       ans1.setText("Correct answer here");
       ans2.setText("Wrong answer here");
       ans3.setText("Wrong answer here");
       ans4.setText("Wrong answer here");
       ++cc;
   }
   --cc;
   String bruh="Q"+cc;
   questionnos.add(bruh);
   questionnos.select(bruh);

   }
   });
   questionnos.addItemListener(new SerializableItemListener()
   		{
   	private static final long serialVersionUID=5L;
   		public void itemStateChanged(ItemEvent ev)
   		{
   int value = Integer.parseInt(questionnos.getSelectedItem().replace("Q",""))-1;	
   String q= (String)questions.get(value); 	
   question.setText(q);
   ans1.setText(((String[])(answers.get(value)))[0]);
   ans2.setText(((String[])(answers.get(value)))[1]);
   ans3.setText(((String[])(answers.get(value)))[2]);
   ans4.setText(((String[])(answers.get(value)))[3]);
   if((value+1)!=cc) {add.setVisible(false);}
   else 
   {add.setVisible(true);}
   }
   		}
   		);
   frm.setSize(100, 100);
   
   frm.addWindowListener(new SerializableWindowAdapter() {
   	private static final long serialVersionUID=56L;
   	public void windowClosing(WindowEvent we)
   {frm.setVisible(false);
    frm.dispose();
    }	
   });
   MenuBar mb=new MenuBar();
   frm.setMenuBar(mb);
   Menu fin;
   mb.add(fin=(new Menu("FINISH")));
   MenuItem nagatoro;
   fin.add(nagatoro=(new MenuItem("Save and Construct")));
   nagatoro.addActionListener(new SerializableActionListener() 
           {
   	private static final long serialVersionUID=35567L;
   		public void actionPerformed(ActionEvent ev)
   		{savable.save();}
           }
   		);
   frm.setVisible(true);
	}
public void save()
{
Frame svFrame=new Frame("Save credentials");
svFrame.setIconImage(ico);
svFrame.setResizable(false);
svFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent we) {svFrame.setVisible(false);svFrame.dispose();}});
svFrame.setLayout(new BoxLayout(svFrame,BoxLayout.Y_AXIS));
Panel passy =new Panel();
passy.setLayout(new FlowLayout());
TextField passwd=new TextField(30);
passwd.setText("Enter password for saving");
passwd.setEchoChar('?');
passwd.addActionListener((ev)->{
	this.password=passwd.getText();
	if(user==null) {return;}
	svFrame.setVisible(false);
	svFrame.dispose();
	serialize();
	});
TextField usern=new TextField(30);
usern.setText("Location in local storage");
usern.addActionListener((evy)->{
	File f=new File(usern.getText());
	try {
		if(!f.createNewFile()) {return;}
	    } 
	catch (IOException e) {return;}
	this.user=usern.getText();
	usern.setEditable(false);
	if(password==null) {return;}
	svFrame.setVisible(false);
	svFrame.dispose();
	serialize();
});
Button see= new Button("see");
see.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ve)
	{
	if(ve.getActionCommand().equalsIgnoreCase("see"))	
	{passwd.setEchoChar((char) 0);
	 see.setLabel("Unsee");
	}
	else
	{passwd.setEchoChar('?');
	 see.setLabel("see");
	}	
	}
});
passy.add(passwd);
passy.add(see);
svFrame.add(usern);
svFrame.add(passy);
svFrame.setSize(300,300);
svFrame.setVisible(true);

}	
public void serialize()
{
	
try {
	FileOutputStream out;
	out=new FileOutputStream(new File(this.user));
	ObjectOutputStream obo=new ObjectOutputStream(out);
	obo.writeObject(this);
	obo.close();
	out.close();
} 
catch (IOException e) {System.out.println(e.getMessage());e.printStackTrace();;}

}
public static boolean editStart(String filepath)
{
Frame sex=new Frame("Confirm your identity");
sex.setIconImage(ico);
sex.setResizable(false);
sex.addWindowListener(new WindowAdapter() 
{
public void windowClosing(WindowEvent we){sex.setVisible(false);sex.dispose();}	
}
);
sex.setResizable(false);
File f=new File(filepath);
if(!f.exists()) {return false;}
try {
	FileInputStream fs=new FileInputStream(f);
	ObjectInputStream ps=new ObjectInputStream(fs);
	QuizApplicationNew object= (QuizApplicationNew) ps.readObject();
	ps.close();
	fs .close();
	sex.setLayout(new FlowLayout());
	TextField fx=new TextField(20);
	fx.setText("Enter your password here");
	fx.addActionListener((ev)->{
		if(!fx.getText().equals(object.password))
	    {sex.setVisible(false);
	    sex.dispose();
	    }
	    else { f.delete();
	    sex.setVisible(false);sex.dispose();
	    savable= object;
	    object.password=null;
	    object.user=null;
	    object.frm.setVisible(true);}
		});
	sex.add(fx);
	sex.setSize(200,200);
	sex.setVisible(Boolean.TRUE);
  
} catch (IOException|ClassNotFoundException ex) {
	return false;
}
return true;
}
public static void showInitialDisplay()
{
MenuBar mb=new MenuBar();
Menu ms=new Menu("How to use/Help");
MenuItem mi=new MenuItem("Rules");
mi.addActionListener((ev)->{
showRules();
});
ms.add(mi);
mb.add(ms);
Frame f=new Frame("Welcome to NIKHIL'S quiz application");
f.setMenuBar(mb);
f.setIconImage(ico);
Label l1=new Label("QUIZ APPLICATION");
f.setLayout(new BoxLayout(f,BoxLayout.Y_AXIS));
l1.setFont(new Font("algerian",(Font.BOLD|Font.ITALIC),34));
l1.setForeground(Color.BLUE);
Label l2=new Label("See how well you know STUFF");
l2.setBackground(Color.YELLOW);
l2.setFont(new Font("algerian",(Font.ITALIC),20));
Button create=new Button("CREATE QUIZ");
create.setFont(new Font("algerian",Font.PLAIN,15));
create.addActionListener((ev)->{
	f.setVisible(false);
	f.dispose();
	QuizApplicationNew obj=new QuizApplicationNew();
	savable=obj;
   obj.main();
});
Button edit=new Button("EDIT");
edit.setFont(new Font("algerian",Font.PLAIN,15));
edit.addActionListener((ev)->{
	f.setVisible(false);
	f.dispose();
	Frame toggi=new Frame("Path?");
	toggi.setIconImage(ico);
	toggi.setResizable(false);
	toggi.setLayout(new FlowLayout());
	TextField haritha=new TextField(20);
	haritha.setText("Enter file path here:");
	haritha.addActionListener((pe)->{
	boolean brui=editStart(haritha.getText());
	if(!brui) {haritha.setText("Invalid or corrupt filepath");}	
	else {toggi.setVisible(false);toggi.dispose();}
	});
	toggi.addWindowListener(new WindowAdapter() 
	{
		public void windowClosing(WindowEvent we)	
		{toggi.setVisible(false);toggi.dispose();}
			
		});
	toggi.add(haritha);
	toggi.setSize(200, 200);
	toggi.setVisible(true);
});
Button solving=new Button("Solve a quiz");
solving.setBackground(Color.CYAN);
solving.setFont(new Font("algerian",Font.BOLD,27));
solving.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ev)
	{
		f.setVisible(false);
		f.dispose();
		solver();
	}
});
f.add(l1);
f.add(l2);
f.add(create);
f.add(edit);
f.add(solving);
f.addWindowListener(new WindowAdapter() {@Override public void windowClosing(WindowEvent we)
    {f.setVisible(false);
    f.dispose();
}});
f.setSize(500,500);
f.setVisible(true);
}
public static class BooleanValue
{
private boolean fl=false;	
public void setTrue()
{fl=true;}
public void setFalse() 
{fl=false;}
public boolean get()
{return fl;}
}
public static  interface SerializableActionListener extends ActionListener,Serializable
{
}
public static interface SerializableItemListener extends ItemListener,Serializable
{
}
public static class SerializableWindowAdapter extends WindowAdapter implements Serializable
{
private static final long serialVersionUID=456L;
}
public static void solver()
{Frame pater=new Frame("Solving time");
pater.setIconImage(ico);
 TextField text=new TextField(20); 
 text.setText("Enter path");
 text.addActionListener((ev)->{String pa=text.getText();
             File f=new File(pa);
             try {
             FileInputStream fis=new FileInputStream(f);
             ObjectInputStream ois=new ObjectInputStream(fis);
             QuizApplicationNew obj=(QuizApplicationNew)ois.readObject();
             ois.close();
             fis.close();
             pater.setVisible(false);
             pater.dispose();
             showSolveUI(obj);
             }
             catch(IOException | ClassNotFoundException ex)
             {
            	 text.setText("Can't resolve to a quiz.");	 
                 return;	 
             }
 });
 pater.setLayout(new FlowLayout());
 pater.setResizable(false);
 pater.addWindowListener(new WindowAdapter() 
 {
	 public void windowClosing(WindowEvent we)
	 {
		pater.setVisible(false); 
		pater.dispose(); 
	 }
 });
 pater.add(text);
 pater.setSize(250,250);
 pater.setVisible(true);
}
public static String[] randomize(String[] array)
{
	ArrayList<String> ar=new ArrayList<>(0);
    for(int i=0;i<array.length;++i)
    {ar.add(array[i]);}
    String array2[]=new String[array.length];
    for(int k=0;k<array2.length;++k)
    {
    	int index=(int)(Math.random()*ar.size());
    	array2[k]=(String)(ar.get(index));
    	ar.remove(index);
    }
    array=array2;
    return array;
}
public static void showSolveUI(QuizApplicationNew object)
{	
final ArrayList<String> answered=new ArrayList<>(0);
final ArrayList<String> c_answers=new ArrayList<>(0);
QuizApplicationNew.c=0;
Frame fri=new Frame("Begin");
fri.setIconImage(ico);
fri.setLayout(new BoxLayout(fri,BoxLayout.Y_AXIS));
Panel pan=new Panel();
pan.setLayout(new GridLayout(2,2,5,5));
Label questionBox= new Label();
questionBox.setText((String)object.questions.get(0));
questionBox.setFont(new Font("algerian",Font.BOLD,25));
questionBox.setForeground(Color.MAGENTA);
Button ans1=new Button();
Button ans2=new Button();
Button ans3=new Button();
Button ans4=new Button();
ans1.setFont(new Font("algerian",Font.PLAIN,19));
ans2.setFont(new Font("algerian",Font.PLAIN,19));
ans3.setFont(new Font("algerian",Font.PLAIN,19));
ans4.setFont(new Font("algerian",Font.PLAIN,19));
ans1.setBackground(Color.GREEN);
ans2.setBackground(Color.GREEN);
ans3.setBackground(Color.GREEN);
ans4.setBackground(Color.GREEN);
pan.add(ans1);
pan.add(ans2);
pan.add(ans3);
pan.add(ans4);
fri.add(questionBox);
fri.add(pan);
String[] array=(String[])object.answers.get(0);
String[] array2=new String[4];
System.arraycopy(array,0, array2, 0, 4);
array2=randomize(array2);
ans1.setLabel(array2[0]);
ans2.setLabel(array2[1]);
ans3.setLabel(array2[2]);
ans4.setLabel(array2[3]);
ActionListener ar=(ev)->{
	answered.add(ev.getActionCommand());
	c_answers.add(((String[])(object.answers.get(c)))[0]);
     ++c;             	
	if(!((c+1)>object.questions.size()))
	{
	questionBox.setText((String)object.questions.get(c));
	String[] arrayd= (String[])object.answers.get(c);
	String[] arrayded= new String[4];
	System.arraycopy(arrayd,0, arrayded, 0, 4);
	arrayded=randomize(arrayded);
	ans1.setLabel(arrayded[0]);
	ans2.setLabel(arrayded[1]);
	ans3.setLabel(arrayded[2]);
	ans4.setLabel(arrayded[3]);
	}
	else
	{
		fri.setVisible(false);
		fri.dispose();
		displayResults(object,answered,c_answers);}
	};
ans1.addActionListener(ar);
ans2.addActionListener(ar);
ans3.addActionListener(ar);
ans4.addActionListener(ar);
fri.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent we)
	{	
	fri.setVisible(false);
	fri.dispose();
	}
});
fri.setSize(500,500);
fri.setVisible(true);
}
public static void displayResults(QuizApplicationNew obj,ArrayList<? extends String> anss,ArrayList<? extends String> c_anss)
{
 	Frame fam=new Frame("Results");
 	fam.setIconImage(ico);
 	fam.setResizable(false);
 	Panel main=new Panel();
 	main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
 	TextField questionBo=new TextField(20);
 	main.add(questionBo);
 	questionBo.setEditable(false);
 	questionBo.setText((String)obj.questions.get(0));
 	TextField ansc=new TextField(10);
 	main.add(ansc);
 	ansc.setEditable(false);
 	ansc.setBackground(Color.GREEN);
 	ansc.setText((String)c_anss.get(0));
 	TextField ansu=new TextField(10);
 	main.add(ansu);
 	ansu.setEditable(false);
 	ansu.setBackground(Color.BLUE);
 	ansu.setText((String)(anss.get(0)));
    ArrayList<String> cquestions=obj.questions;
    int correct=0;
    for(int k=0;k<cquestions.size();++k)
    {
    String s1=(String)(anss.get(k));
    String s2=(String)(c_anss.get(k));
    if(s1.equals(s2)) {++correct;}	
    }
    Choice ch=new Choice();
    for(int i=0;i<cquestions.size();++i)
    {
    ch.addItem("Q"+(i+1));	
    }
    ch.addItemListener((il)->{
    	int n=ch.getSelectedIndex();
    	questionBo.setText(("Q- ")+(String)cquestions.get(n));
    	ansc.setText("Correct ans- "+(String)(c_anss.get(n)));
    	ansu.setText("Your ans- "+(String)(anss.get(n)));
        });
    Label l=new Label("You got "+correct+" questions right");
    Label l1=new Label("You got "+(cquestions.size()-correct)+" questions wrong.");
    main.add(l);
    main.add(l1);
    fam.add(main);
    fam.add(ch,BorderLayout.EAST);
    fam.setSize(300, 300);
    fam.setVisible(true);
    fam.addWindowListener(new WindowAdapter() {
    	public void windowClosing(WindowEvent we)
    	{fam.setVisible(false);
    	 fam.dispose();  
    	   }
    	
    });
}
@Override
public File getRules() {
	
	return DependancyManager.loadDependancy(66);
}
public static void showRules()
{
RulesManager rm=new RulesManager(DependancyManager.loadDependancy(66),new int[] {8,13,24,27,59,62},DependancyManager.loadIconBatch(60,66),450,350);	
rm.displayRules();
}
}