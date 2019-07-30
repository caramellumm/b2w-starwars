package br.com.b2w.exception;

public class BusinessException extends Exception{

	private static final long serialVersionUID = -7457569824524962075L;
	
	private String msg;
	
	public BusinessException(Throwable cause){
	      super(cause);
	 }
	
    public BusinessException(String msg){
      super(msg);
      this.msg = msg;
    }
    
    
    public BusinessException(String msg, Throwable cause){
        super(msg, cause);
        this.msg = msg;
      }
    
    public String getMessage(){
      return msg;
    }
}
