package com.gles.view.graphics;

import java.util.ArrayList;
import java.util.List;

import android.opengl.GLSurfaceView;

/**
 * JqGraphicsGroup
 * 
 * @author qianjunping
 * 
 */
public abstract class JqGraphicsGroup extends BaseGraphicsGroup {

	/**
	 * 最终展示的结果数组
	 */
	private JqGraphics[] graphics;

	/**
	 * 数据缓冲带，避免直接操作数组的麻烦
	 */
	private List<JqGraphics> listGraphics = new ArrayList<JqGraphics>();

	private boolean isGetNewData = true;

	public JqGraphicsGroup(GLSurfaceView mv) {
		super(mv);
	}

	/**
	 * 添加需要在组里面画出的孩子
	 * 
	 * @return
	 */
	@Override
	public void addChild(JqGraphics graphic) {
		if (graphic != null) {
			graphic.initVertexData();
			listGraphics.add(graphic);
			invalidate();
		}
	}

	@Override
	public void addChilds(JqGraphics[] graphics) {

		if (graphics != null) {
			for (int i = 0; i < graphics.length; i++) {
				if (graphics[i] != null) {
					graphics[i].initVertexData();
					listGraphics.add(graphics[i]);
				}
			}
			invalidate();
		}

	}

	@Override
	public void removeAllChilds() {
		listGraphics.clear();
		invalidate();
	}

	@Override
	public void drawSelf(int... textureID) {

		if (isGetNewData) {
			// 第一次加载的时候，刷新数据
			invalidate();
			isGetNewData = false;
		}
		onChildsLayout(graphics);
	}

	/**
	 * 刷新数据
	 */
	@Override
	public void invalidate() {

		if (listGraphics != null && listGraphics.size() > 0) {
			clearData();
			graphics = new JqGraphics[listGraphics.size()];
			for (int i = 0; i < graphics.length; i++) {
				graphics[i] = listGraphics.get(i);
			}
		} else {
			clearData();
		}

	}

	private void clearData() {

		if (graphics != null) {
			// 释放原先所用的引用
			for (int i = 0; i < graphics.length; i++) {
				graphics[i] = null;
			}
			graphics = null;
		}
	}
}
