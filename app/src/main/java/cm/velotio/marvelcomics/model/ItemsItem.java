package cm.velotio.marvelcomics.model;

public class ItemsItem{
	private String name;
	private String resourceURI;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setResourceURI(String resourceURI){
		this.resourceURI = resourceURI;
	}

	public String getResourceURI(){
		return resourceURI;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"name = '" + name + '\'' + 
			",resourceURI = '" + resourceURI + '\'' + 
			"}";
		}
}
