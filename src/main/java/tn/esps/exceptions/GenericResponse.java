package tn.esps.exceptions;

public class GenericResponse {
	private String httpStatus;
	private String message;
	private int httpStatusCode;


	public String getHttpStatus() {
		return httpStatus;
	}


	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getHttpStatusCode() {
		return httpStatusCode;
	}


	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}


	@Override
	public String toString() {
		return httpStatus + "|" + message + "|" + httpStatusCode;
	}
}
