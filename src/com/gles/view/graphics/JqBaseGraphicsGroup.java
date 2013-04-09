package com.gles.view.graphics;

import com.gles.view.base.JqGraphics;

import android.opengl.GLSurfaceView;

/**
 * ���� Graphics���࣬���������
 * 
 * @author qianjunping
 * 
 */
public abstract class JqBaseGraphicsGroup extends JqGraphics {

	public JqBaseGraphicsGroup(GLSurfaceView mv) {
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
	public void initGraphicsData() {

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
