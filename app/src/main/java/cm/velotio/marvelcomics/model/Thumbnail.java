package cm.velotio.marvelcomics.model;

public class Thumbnail{
	private String path;
	private String extension;

	public Thumbnail(String path, String extension) {
		this.path = path;
		this.extension = extension;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setExtension(String extension){
		this.extension = extension;
	}

	public String getExtension(){
		return extension;
	}

	@Override
 	public String toString(){
		return 
			"Thumbnail{" + 
			"path = '" + path + '\'' + 
			",extension = '" + extension + '\'' + 
			"}";
		}
}
