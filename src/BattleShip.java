import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.event.*;
import java.util.*;
@SuppressWarnings("unused")
//Temporarily paused saved for later
public final class BattleShip implements utilities.Playable
{
	JFrame mboard;
	JPanel cpane;
    JButton[] gboxes;
	public static void main(String[] args)
	{
		new BattleShip();
	}
	
	public BattleShip()
	{
		
	}

	@Override
	public File getRules() {
		// TODO Auto-generated method stub
		return null;
	}

}
