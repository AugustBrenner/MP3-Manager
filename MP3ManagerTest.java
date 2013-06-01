// August Ryan Brenner
// abrenn10@my.smccd.edu
// CIS 255HJ
// MP3ManagerTest.java
// test program runs MP3Manager
// Assignment 8
// May 14th, 2012 
import javax.swing.JFrame;


public class MP3ManagerTest
{
	public static void main(String [] args)
	{
		
		MP3Manager manager = new MP3Manager();
		manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		manager.setSize(500,500); // set frame size
		manager.setVisible(true); // display frame
	} // end main
} // end class