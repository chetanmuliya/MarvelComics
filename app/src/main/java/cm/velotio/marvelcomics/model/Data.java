package cm.velotio.marvelcomics.model;

import java.util.List;

public class Data{
	private int total;
	private int offset;
	private int limit;
	private int count;
	private List<ResultsItem> results;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setLimit(int limit){
		this.limit = limit;
	}

	public int getLimit(){
		return limit;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"total = '" + total + '\'' + 
			",offset = '" + offset + '\'' + 
			",limit = '" + limit + '\'' + 
			",count = '" + count + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}