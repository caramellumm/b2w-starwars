package br.com.b2w.exception;

public class ServiceException extends Exception{

	private static final long serialVersionUID = -7457569824524962075L;
	
	private String msg;
	
	public ServiceException(Throwable cause){
	      super(cause);
	 }
	
    public ServiceException(String msg){
      super(msg);
      this.msg = msg;
    }
    
    
    public ServiceException(String msg, Throwable cause){
        super(msg, cause);
        this.msg = msg;
      }
    
    public String getMessage(){
      return msg;
    }
}
