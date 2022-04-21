package animations;

import java.awt.Rectangle;

public abstract class Sprite
{
public static Rectangle top;
public static Rectangle bottom;
public static Rectangle right;
public static Rectangle left;
public boolean visible;
public int x;
public int y;
public int dx;
public int dy;
public int width;
public int height;
public int speed_x;
public int speed_y;
public static int swidth=30;
public static int sheight=30;
{
	width=30;
	height=30;
	this.speed_x=3;
	this.speed_y=3;
}
public Sprite(int x,int y)
{
	this.x=x;
    this.y=y;
}
public Sprite(int x,int y,int w,int h)
{
this(x,y);
width=w;
height=h;
}
public void move()
{
	if(this.getBounds().intersects(top))
	{y+=4;return;}
	else if(this.getBounds().intersects(right))
	{x-=4;return;}
	else if(this.getBounds().intersects(bottom))
	{y-=4;return;}
	else if(this.getBounds().intersects(left))
	{x+=4;return;}
	x+=dx;
	y+=dy;
}
public boolean isVisible()
{return visible;}
public void setVisible(boolean vis)
{visible=vis;}
public void setSpeedX(int sx)
{speed_x=sx;}
public void setSpeedY(int sy)
{speed_y=sy;}
public Rectangle getBounds()
{
return new Rectangle(this.x,this.y,this.width,this.height);	
}
}
