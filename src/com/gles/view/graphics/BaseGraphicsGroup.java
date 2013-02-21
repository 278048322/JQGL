package com.gles.view.graphics;

import android.opengl.GLSurfaceView;

/**
 * 基础 Graphics组类，组的适配器
 * 
 * @author qianjunping
 * 
 */
public abstract class BaseGraphicsGroup extends JqGraphics {

	public BaseGraphicsGroup(GLSurfaceView mv) {
		super(mv);
	}

	/**
	 * do nothing/
	 */
	@Override
	public void initShaderMember() {

	}

	/**
	 * do nothing/
	 */
	@Override
	public void initVertexData() {

	}

	/**
	 * 子图形的布局
	 * 
	 * @param graphics
	 *            图形数组
	 */
	public abstract void onChildsLayout(JqGraphics[] graphics);

	public abstract void addChild(JqGraphics graphics);

	public abstract void addChilds(JqGraphics[] graphics);

	// public abstract void removeChild(JqGraphics graphics);
	//
	// public abstract void removeChilds(JqGraphics[] graphics);

	public abstract void removeAllChilds();

	/**
	 * 刷新视图，如运行时增加、删除了child
	 */
	public abstract void invalidate();

}
