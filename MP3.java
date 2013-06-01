// August Ryan Brenner
// abrenn10@my.smccd.edu
// CIS 255HJ
// MP3.java
// Creates MP3 Objects
// Assignment 8
// May 14th, 2012 


/**
 * A simple class that stores MP3 song data
 *
 */
public class MP3
{
	private String artist;
	private String song;
	private String album;
	private int trackLength;
	
	/**
	 * Constructor that calls set methods for inputs
	 * @param newArtist
	 * @param newSong
	 * @param newAlbum
	 * @param newTrackLength
	 */
	public MP3(String newArtist, String newSong, 
			String newAlbum, int newTrackLength)
	{
		setArtist(newArtist);
		setSong(newSong);
		setAlbum(newAlbum);
		setTrackLength(newTrackLength);	
	}
	
	/**
	 * Set method for artist name
	 * @param newArtist
	 */
	public void setArtist(String newArtist)
	{
		artist = newArtist;
	}
	
	/**
	 * Get method for artist name
	 * @return artist
	 */
	public String getArtist()
	{
		return artist;
	}
	
	/**
	 * Set method for song title 
	 * @param newSong
	 */
	public void setSong(String newSong)
	{
		song = newSong;
	}
	/**
	 * Get method for song title
	 * @return song
	 */
	public String getSong(){
		return song;
	}
	
	/**
	 * Set method for album title
	 * @param newAlbum
	 */
	public void setAlbum(String newAlbum)
	{
		album = newAlbum;
	}
	
	/**
	 * Get method for album title
	 * @return album
	 */
	public String getAlbum()
	{
		return album;
	}
	
	/**
	 * Set method for album title
	 * @param newTrackLength
	 */
	public void setTrackLength(int newTrackLength)
	{
		trackLength = newTrackLength;
	}
	
	/**
	 * Get method for album title
	 * @return trackLength
	 */
	public int getTrackLength()
	{
		return trackLength;  
	}
	
	/** 
	 * toSting method that returns information as a string
	 **/
	public String toString(){
		return String.format("%s, %s, %s, %d:%d", getArtist(), 
			getSong(), getAlbum(), getTrackLength() / 60,
			getTrackLength() % 60);
	}
}