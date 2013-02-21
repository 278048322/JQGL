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
	 * ����չʾ�Ľ������
	 */
	private JqGraphics[] graphics;

	/**
	 * ���ݻ����������ֱ�Ӳ���������鷳
	 */
	private List<JqGraphics> listGraphics = new ArrayList<JqGraphics>();

	private boolean isGetNewData = true;

	public JqGraphicsGroup(GLSurfaceView mv) {
		super(mv);
	}

	/**
	 * �����Ҫ�������滭���ĺ���
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
			// ��һ�μ��ص�ʱ��ˢ������
			invalidate();
			isGetNewData = false;
		}
		onChildsLayout(graphics);
	}

	/**
	 * ˢ������
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
			// �ͷ�ԭ�����õ�����
			for (int i = 0; i < graphics.length; i++) {
				graphics[i] = null;
			}
			graphics = null;
		}
	}
}
