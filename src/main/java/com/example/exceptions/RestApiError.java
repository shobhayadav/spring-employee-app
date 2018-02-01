package com.example.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

/* Class to display API Error message with status code*/

public class RestApiError {

	   private HttpStatus status;
	   private Date timestamp;
	   private String message;
	   private String debugMessage;


	   public RestApiError(HttpStatus status) {

	       this.status = status;
	       timestamp = new Date();
	   }

	   public RestApiError(HttpStatus status, String message, Throwable ex) {

	       this.status = status;
	       this.message = message;
	       this.debugMessage = ex.getLocalizedMessage();
	       timestamp = new Date();
	   }

	   public RestApiError(HttpStatus status, Throwable ex) {

	       this.status = status;
	       this.message = "Unexpected error";
	       this.debugMessage = ex.getLocalizedMessage();
	       timestamp = new Date();
	   }

	public String getDebugMessage() {
		return debugMessage;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}
	public Date getTimestamp() {
		return timestamp;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	   
	   
	   
	}