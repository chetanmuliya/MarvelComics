package cm.velotio.marvelcomics.model;

import java.util.List;

public class Series{
	private String collectionURI;
	private int available;
	private int returned;
	private List<ItemsItem> items;

	public void setCollectionURI(String collectionURI){
		this.collectionURI = collectionURI;
	}

	public String getCollectionURI(){
		return collectionURI;
	}

	public void setAvailable(int available){
		this.available = available;
	}

	public int getAvailable(){
		return available;
	}

	public void setReturned(int returned){
		this.returned = returned;
	}

	public int getReturned(){
		return returned;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"Series{" + 
			"collectionURI = '" + collectionURI + '\'' + 
			",available = '" + available + '\'' + 
			",returned = '" + returned + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}