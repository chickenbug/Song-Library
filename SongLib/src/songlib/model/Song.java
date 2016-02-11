//Tim Goetjen and Kinh Hoang
package songlib.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Model class for a Song.
 */

public class Song implements Comparable<Song>{
	
	private final StringProperty name;
	private final StringProperty artist;
	private final StringProperty album;
	private final StringProperty year;
	
	public Song(){
		this(null,null,null,null);
	}
	
	public Song(String name, String artist, String album, String year){
		this.name = new SimpleStringProperty(name);
		this.artist = new SimpleStringProperty(artist);
		this.album = new SimpleStringProperty(album);
		this.year = new SimpleStringProperty(year);
	}
	
	public int compareTo(Song song){
		if(name.get().toLowerCase().compareTo(song.name.get().toLowerCase())==0){
			return artist.get().toLowerCase().compareTo(song.artist.get().toLowerCase());
		}
		return name.get().toLowerCase().compareTo(song.name.get().toLowerCase());
	}
	
	public String getName(){
		return name.get();
	}
	
	public void setName(String name){
		this.name.set(name);
	}
	
	public StringProperty nameProperty(){
		return name;
	}
	
	public String getArtist(){
		return artist.get();
	}
	
	public void setArtist(String artist){
		this.artist.set(artist);
	}
	
	public StringProperty artistProperty(){
		return artist;
	}
	
	public String getAlbum(){
		return album.get();
	}
	
	public void setAlbum(String album){
		this.album.set(album);
	}
	
	public StringProperty albumProperty(){
		return album;
	}
	
	public String getYear(){
		return year.get();
	}
	
	public void setYear(String year){
		this.year.set(year);
	}
	
	public StringProperty yearProperty(){
		return year;
	}
	
	public String toData(){
		String s = name.get() + "," + artist.get() + ",";
		
		if(album.get() == null || album.get().compareTo("") == 0) s  = s.concat(" ,");
		else s = s.concat(album.get()+ ",");
		if(year.get() == null || year.get().compareTo("") == 0) s = s.concat(" ");
		else s = s.concat(year.get());
			
		return s;
	}


}
