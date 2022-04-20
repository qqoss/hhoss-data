package com.hhoss.mold.hold;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hhoss.mold.Item;


/**
 * action result holder VO
 * @author kejun
 *
 */
public class Result extends Item<Result> {
	private static final long serialVersionUID = 47660125198273988L;
	private boolean produced;
	private boolean finished;
	private boolean achieved;
	//success-failure; succeed-fail-done-
	private Serializable body;
	private List<String> errors;
	
	private Exception exception;


	public boolean isProduced() {
		return produced;
	}
	public void setProduced(boolean produced) {
		this.produced = produced;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public boolean isAchieved() {
		return achieved;
	}
	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}

	public List<String> getErrors() {
		return errors;
	}
	
	public void addError(String error) {
		if( this.errors==null ){
			this.errors= new ArrayList<String>();
		}
		this.errors.add(error);
	}

	public Serializable getBody() {
		return body;
	}
	public void setBody(Serializable body) {
		this.body = body;
	}
	
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}

}
