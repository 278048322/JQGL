package com.gles.view.graphics;

import android.opengl.GLSurfaceView;

/**
 * ���� Graphics���࣬���������
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
	 * ��ͼ�εĲ���
	 * 
	 * @param graphics
	 *            ͼ������
	 */
	public abstract void onChildsLayout(JqGraphics[] graphics);

	public abstract void addChild(JqGraphics graphics);

	public abstract void addChilds(JqGraphics[] graphics);

	// public abstract void removeChild(JqGraphics graphics);
	//
	// public abstract void removeChilds(JqGraphics[] graphics);

	public abstract void removeAllChilds();

	/**
	 * ˢ����ͼ��������ʱ���ӡ�ɾ����child
	 */
	public abstract void invalidate();

}
