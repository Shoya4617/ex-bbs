package com.example.domain;

import java.util.List;

public class Article {
	
	private Integer id;
	private String name;
	private String comment;
	private List<Comment> commentList;
	
	@Override
	public String toString() {
		return "Article [id=" + id + ", name=" + name + ", comment=" + comment + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

}
