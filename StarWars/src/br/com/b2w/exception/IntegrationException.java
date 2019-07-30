package br.com.b2w.exception;

public class IntegrationException extends Exception{

	private static final long serialVersionUID = -7457569824524962075L;
	
	private String msg;
	
	public IntegrationException(Throwable cause){
	      super(cause);
	 }
	
    public IntegrationException(String msg){
      super(msg);
      this.msg = msg;
    }
    
    
    public IntegrationException(String msg, Throwable cause){
        super(msg, cause);
        this.msg = msg;
      }
    
    public String getMessage(){
      return msg;
    }
}
