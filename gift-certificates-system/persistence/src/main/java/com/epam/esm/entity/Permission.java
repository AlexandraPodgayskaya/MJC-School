package com.epam.esm.entity;

public enum Permission {
	AUTHORITY_READ("authority:read"), AUTHORITY_WRITE("authority:write");

	private final String permission;

	Permission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

}
