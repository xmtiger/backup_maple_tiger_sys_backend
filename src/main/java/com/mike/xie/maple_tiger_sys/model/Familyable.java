package com.mike.xie.maple_tiger_sys.model;

public interface Familyable<T> {
	
	boolean isTheFather(T t);
    boolean isTheChildren(T t);
}
