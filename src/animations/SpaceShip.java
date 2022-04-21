package animations;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Scanner;
@SuppressWarnings("unused")
public final class SpaceShip extends Sprite 
{
{
	super.speed_x=3;
    super.speed_y=3;
}
public SpaceShip(int x,int y)
{	
super(x,y);
}
    
	public void keyPressed(KeyEvent ke)
	{
		int code=ke.getKeyCode();
		if(code==KeyEvent.VK_RIGHT)
		{dx=speed_x;}
		else if(code==KeyEvent.VK_LEFT)
		{dx=speed_x*-1;}
		else if(code==KeyEvent.VK_UP)
		{dy=speed_y*-1;}
		else if(code==KeyEvent.VK_DOWN)
		{dy=speed_y;}
	}
	public void keyReleased(KeyEvent ke)
	{
		int code=ke.getKeyCode();
	if((code==KeyEvent.VK_RIGHT)||(code==KeyEvent.VK_LEFT))
	{dx=0;}
	else if((code==KeyEvent.VK_DOWN)||(code==KeyEvent.VK_UP))
	{dy=0;}
	}

}
