package com.owing.core.dnd.base.model;

public interface Dnd {
	Long getId();
	Long getPosition();
	String getDescription();
	Long getParentId();
	String getName();
	boolean validatePosition(long newPosition);
	void updatePosition(long newPosition);
	void updateName(String newName);
}
