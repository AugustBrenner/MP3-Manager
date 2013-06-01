// August Ryan Brenner
// abrenn10@my.smccd.edu
// CIS 255HJ
// MP3Manager.java
// creates a frame for MP3 objects
// Assignment 8
// May 14th, 2012 

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MP3Manager extends JFrame
{	
	private JLabel artistLabel, songLabel, 
		albumLabel, trackLengthLabel;
	private JTextField artistField, songField, 
		albumField, trackLengthField;
	private JButton addMP3Button, displayMP3sButton, 
		findMP3Button, deleteMP3Button;
	private Container container = getContentPane();
	private JPanel panel;
	private JTextArea textArea;
	
	MP3Manager()
	{
		super("MP3 Manager August Ryan Brenner");
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		
		artistLabel = new JLabel("Artist Name ", SwingConstants.RIGHT);	
		artistField = new JTextField();
		songLabel = new JLabel("Song Title ", SwingConstants.RIGHT);	
		songField = new JTextField();	
		albumLabel = new JLabel("Album Name ", SwingConstants.RIGHT);	
		albumField = new JTextField();	
		trackLengthLabel = new JLabel("Track Length (in seconds) ",
				SwingConstants.RIGHT);
		trackLengthField = new JTextField();
		addMP3Button = new JButton(" Add MP3 ");	
		displayMP3sButton = new JButton(" Display MP3s ");	
		findMP3Button = new JButton(" Find MP3 ");
		deleteMP3Button = new JButton(" Delete MP3 ");
		
		panel.add(artistLabel);
		panel.add(artistField);
		panel.add(songLabel);
		panel.add(songField);
		panel.add(albumLabel);
		panel.add(albumField);
		panel.add(trackLengthLabel);
		panel.add(trackLengthField);
		panel.add(addMP3Button);
		panel.add(displayMP3sButton);
		panel.add(findMP3Button);
		panel.add(deleteMP3Button);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		container.add(panel, BorderLayout.NORTH );
		container.add(textArea, BorderLayout.CENTER);
		
		ButtonHandler handler = new ButtonHandler();
		addMP3Button.addActionListener(handler);
		displayMP3sButton.addActionListener(handler);
		findMP3Button.addActionListener(handler);
		deleteMP3Button.addActionListener(handler);
	}
	
	private class ButtonHandler implements ActionListener
	{	
		MP3 track;
		String outputText = "";
		File file = new File( "MP3.txt" );
		
		public void actionPerformed(ActionEvent event)
		{		
			if(event.getSource() == addMP3Button)
			{
				addMP3();
			}
			if(event.getSource() == displayMP3sButton)
			{
				displayMP3();
			}
			if(event.getSource() == findMP3Button)
			{
				findMP3();
			}
			if(event.getSource() == deleteMP3Button)
			{
				deleteMP3();
			}
		}
		
		public void addMP3()
		{
			String artist = artistField.getText();
			String song = songField.getText();
			String album = albumField.getText();
			String trackLength = trackLengthField.getText();
			
			textArea.setText("");
			
			if(artist.isEmpty() == true || album.isEmpty() == true
					|| song.isEmpty() == true
					|| trackLength.isEmpty() == true)
			{
				JOptionPane.showMessageDialog(null,
						"Please Complete Information.",
					    "Incomplete Error",
					    JOptionPane.ERROR_MESSAGE);
			}else if(trackLength.matches("^[1-9][0-9]*$") == false)
			{
				JOptionPane.showMessageDialog(null,
						"Please Enter A Time In Seconds",
					    "Out Of Bounds Error",
					    JOptionPane.ERROR_MESSAGE);
						System.out.printf(":",artist);
			}else{
				track = new MP3(artist, song, album,
						Integer.parseInt(trackLength));
				outputText = track.toString();
				textArea.setText(outputText + "\n");
				try {
					FileWriter addWriter = new FileWriter( file, true);
					String exportText = String.format("%s:%s:%s:%s",
						artist,song,album,trackLength);
					addWriter.write(exportText + "\n");
					addWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				artistField.setText("");
				songField.setText("");
				albumField.setText("");
				trackLengthField.setText("");
			}
		}
		
		public void displayMP3()
		{	
			String line, name, song, album;
			int trackLength;
			StringTokenizer tokenizer;
			BufferedReader inFile;
			FileReader fileReader;
			ArrayList<MP3> arrayMP3 = new ArrayList<MP3>(10);
			
			textArea.setText("");
			
			try {
				fileReader = new FileReader( file );
				inFile = new BufferedReader( fileReader );
				line = inFile.readLine();
				while ( line != null )
				{
					tokenizer = new StringTokenizer( line, ":");
					name = tokenizer.nextToken();
					song = tokenizer.nextToken();
					album = tokenizer.nextToken();
					
					try
					{
						trackLength = Integer.parseInt( tokenizer.nextToken());
						MP3 mp3 = new MP3(name, song, album, trackLength);
						arrayMP3.add(0, mp3);
					
					} catch ( NumberFormatException exception )
					{
						System.out.println( 
						"Error in input. Line ignored:" );
						System.out.println( line );
					}
					line =inFile.readLine();
				} 
		
				Comparator<MP3> CompareArtist = new Comparator<MP3>()
				{
			        public int compare(MP3 one, MP3 other)
			        {
			            return one.getArtist().compareToIgnoreCase(
			            	other.getArtist());
			        }
			    };

				Collections.sort(arrayMP3, CompareArtist);
				for(MP3 mp3Object : arrayMP3)
				{
					outputText = mp3Object.toString();
					textArea.append(outputText + "\n");
				}
				
				inFile.close();
			
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		
		public void deleteMP3()
		{	
			String songName = songField.getText();
			String line, name, song, album;
			int trackLength;
			StringTokenizer tokenizer;
			BufferedReader inFile;
			FileReader fileReader;
			ArrayList<MP3> arrayMP3 = new ArrayList<MP3>(10);
			Boolean wasDeleted = false;
			
			textArea.setText("");
			
			try {
				fileReader = new FileReader( file );
				inFile = new BufferedReader( fileReader );
				line = inFile.readLine();
				while ( line != null )
				{
					tokenizer = new StringTokenizer( line, ":");
					name = tokenizer.nextToken();
					song = tokenizer.nextToken();
					album = tokenizer.nextToken();
					
					try
					{
						trackLength = Integer.parseInt( tokenizer.nextToken());
						if(song.equalsIgnoreCase(songName))
						{
							wasDeleted = true;
						}
						else
						{
							MP3 mp3 = new MP3(name, song, album, trackLength);
							arrayMP3.add(0, mp3);
						}
					
					} catch ( NumberFormatException exception )
					{
						System.out.println( 
						"Error in input. Line ignored:" );
						System.out.println( line );
					}
					line = inFile.readLine();
				} 
		
				if ( wasDeleted == false )
				{
					inFile.close();
					textArea.setText("No Songs Found");
				}
				else
				{
					inFile.close();
					
					int selection = JOptionPane.showConfirmDialog(
						    null, "Delete Song?", null,
						    JOptionPane.YES_NO_OPTION);
					
					if(selection == JOptionPane.YES_OPTION)
					{
						FileWriter addWriter = new FileWriter( file, false);
						try {
							for(MP3 mp3Object : arrayMP3)
							{

								String exportText = String.format(
									"%s:%s:%s:%s", mp3Object.getArtist(),
									mp3Object.getSong(),
										mp3Object.getAlbum(),
										mp3Object.getTrackLength());
								addWriter.write(exportText + "\n");
							} 
							addWriter.close();
						}catch (IOException e) {
							e.printStackTrace();
						}
						textArea.setText("Song Deleted");
					}
				}
				
				
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		
		public void findMP3()
		{	
			String artistName = artistField.getText();
			String songName = songField.getText();
			String albumName = albumField.getText();
			
			String line, name, song, album;
			int trackLength;
			StringTokenizer tokenizer;
			BufferedReader inFile;
			FileReader fileReader;
			ArrayList<MP3> arrayMP3 = new ArrayList<MP3>(10);
			
			textArea.setText("");
			
			try {
				fileReader = new FileReader( file );
				inFile = new BufferedReader( fileReader );
				line = inFile.readLine();
				while ( line != null )
				{
					tokenizer = new StringTokenizer( line, ":");
					name = tokenizer.nextToken();
					song = tokenizer.nextToken();
					album = tokenizer.nextToken();
					
					try
					{
						trackLength = Integer.parseInt( tokenizer.nextToken());
						if(name.equalsIgnoreCase(artistName) || 
							song.equalsIgnoreCase(songName) || 
							album.equalsIgnoreCase(albumName))
						{
							MP3 mp3 = new MP3(name, song, album, trackLength);
							arrayMP3.add(0, mp3);
						}
					
					} catch ( NumberFormatException exception )
					{
						System.out.println( 
						"Error in input. Line ignored:" );
						System.out.println( line );
					}
					line =inFile.readLine();
				} 
		
				if ( arrayMP3.size() < 1 )
				{
					textArea.setText("No Songs Found");
				}
				else
				{
					Comparator<MP3> FindSong = new Comparator<MP3>()
					{
						public int compare(MP3 one, MP3 other)
						{
							return one.getArtist().compareToIgnoreCase(
								other.getArtist());
						}
					};

					Collections.sort(arrayMP3, FindSong);
					for(MP3 mp3Object : arrayMP3)
					{
						outputText = mp3Object.toString();
						textArea.append(outputText + "\n");
					}
				}
				
				inFile.close();
				
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
	}
}