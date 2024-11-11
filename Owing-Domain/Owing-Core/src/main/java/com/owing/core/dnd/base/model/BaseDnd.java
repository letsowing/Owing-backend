package com.owing.core.dnd.base.model;

public interface BaseDnd {
	Long getId();
	Long getPosition();
	String getDescription();
	Long getParentId();
	boolean validatePosition(long newPosition);
	void updatePosition(long newPosition);
}
