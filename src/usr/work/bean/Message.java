package usr.work.bean;

public class Message {
	private int status;
	private boolean success;
	private Object result;
	private String error;
	
	public Message(int status, boolean success, Object result) {
		super();
		this.status = status;
		this.success = success;
		this.result = result;
	}
	
	public Message(){
		super();
	}
	
	public Message(int status){
		super();
		this.status = status;
	}
	public Message(int status, Object result) {
		super();
		this.status = status;
		this.result = result;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	
}
