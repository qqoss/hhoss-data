package com.hhoss.mold.able;

//task, call, run, execute,service
public interface Service<T> {
	default T initial(){return null;}
	default T destroy(){return null;}
	default T prepare() throws Exception{return null;}
	default T process() throws Exception{return null;}
	default T perfect() throws Exception{return null;}
	
	//perfect,persist,recover,restore
	

}
