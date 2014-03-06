package org.impeng;

import java.io.Serializable;

public class DiaryVo implements Serializable{
	private static final long serialVersionUID = 5616886290500335667L;
	private int id; //id标识
	private int icon; //天气图片icon
	private String title; //日记标题
	private String date; //日记日期
	private String content; //日记内容

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
