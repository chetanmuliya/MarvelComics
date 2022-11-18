package cm.velotio.marvelcomics.model;

public class CharactersResponse{
	private int code;
	private Data data;
	private String status;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"CharactersResponse{" +
			",code = '" + code + '\'' + 
			",data = '" + data + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}
