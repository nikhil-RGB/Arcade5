//Marker interface playable marks a class as an arcade game.
//Not applicable to helper, manager classes.
package utilities;
import javax.swing.ImageIcon;

public interface Playable
{
public java.io.File getRules();
public default void displayRules()
{
new RulesManager(this.getRules()).displayRules();
}
public default void displayRules(int[] lbs,ImageIcon[] imgs,int x1,int y1)
{
	new RulesManager(this.getRules(),lbs,imgs,x1,y1).displayRules();
}
public default void terminateGame()
{}
}
