package com.jq.activitys;

import java.io.Serializable;

import com.gles.view.scene.JqScene;

public class DemoBean implements Serializable {

	private JqScene scene;
	private String content;

	public DemoBean() {

	}

	public DemoBean(JqScene scene, String content) {

		this.scene = scene;
		this.content = content;

	}

	public JqScene getSceneName() {
		return scene;
	}

	public void setSceneName(JqScene scene) {
		this.scene = scene;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
