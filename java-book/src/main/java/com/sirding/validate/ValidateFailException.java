package com.sirding.validate;

/**
 * @described	: 添加自定义异常
 * @project		: com.sirding.validate.ValidateFailException
 * @author 		: zc.ding
 * @since 		: 2017年4月20日
 */
public class ValidateFailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 错误代号
	 */
	private int errCode = 0;
	
	public ValidateFailException(){
		super();
	}
	
	public ValidateFailException(String msg){
		super(msg);
	}
	
	public ValidateFailException(int errCode){
		super();
		this.errCode = errCode;
	}
	
	public ValidateFailException(int errCode, String msg){
		super(msg);
		this.errCode = errCode;
	}
	
	public ValidateFailException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	
	public ValidateFailException(final int errCode, String msg, Throwable throwable){
		super(msg, throwable);
		this.errCode = errCode;
	}

	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
}
