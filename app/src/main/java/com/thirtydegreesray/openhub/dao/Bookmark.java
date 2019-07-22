package com.thirtydegreesray.openhub.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "BOOKMARK".
 */
@Entity
public class Bookmark {

	@Id
	@NotNull
	private String id;

	@NotNull
	private String type;
	private String userId;
	private Long repoId;
	private java.util.Date markTime;

	@Generated
	public Bookmark() {
	}

	public Bookmark(String id) {
		this.id = id;
	}

	@Generated
	public Bookmark(String id, String type, String userId, Long repoId, java.util.Date markTime) {
		this.id = id;
		this.type = type;
		this.userId = userId;
		this.repoId = repoId;
		this.markTime = markTime;
	}

	@NotNull
	public String getId() {
		return id;
	}

	/**
	 * Not-null value; ensure this value is available before it is saved to the database.
	 */
	public void setId(@NotNull String id) {
		this.id = id;
	}

	@NotNull
	public String getType() {
		return type;
	}

	/**
	 * Not-null value; ensure this value is available before it is saved to the database.
	 */
	public void setType(@NotNull String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getRepoId() {
		return repoId;
	}

	public void setRepoId(Long repoId) {
		this.repoId = repoId;
	}

	public java.util.Date getMarkTime() {
		return markTime;
	}

	public void setMarkTime(java.util.Date markTime) {
		this.markTime = markTime;
	}

}
