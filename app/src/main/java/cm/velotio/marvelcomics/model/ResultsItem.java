package cm.velotio.marvelcomics.model;

import java.util.List;

public class ResultsItem{
	private Thumbnail thumbnail;
	private Series series;
	private Comics comics;
	private String name;
	private String description;
	private int id;

	public void setThumbnail(Thumbnail thumbnail){
		this.thumbnail = thumbnail;
	}

	public Thumbnail getThumbnail(){
		return thumbnail;
	}

	public void setSeries(Series series){
		this.series = series;
	}

	public Series getSeries(){
		return series;
	}

	public void setComics(Comics comics){
		this.comics = comics;
	}

	public Comics getComics(){
		return comics;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"thumbnail = '" + thumbnail + '\'' +
			",comics = '" + comics + '\'' + 
			",series = '" + series + '\'' +
			",name = '" + name + '\'' +
			",description = '" + description + '\'' +
			",id = '" + id + '\'' +
			"}";
		}
}