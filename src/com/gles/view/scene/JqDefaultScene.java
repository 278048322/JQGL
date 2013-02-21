package com.gles.view.scene;

import android.content.Context;

public abstract class JqDefaultScene extends JqScene {

	public JqDefaultScene(Context context) {
		super(context);
	}

	/**
	 * ����ͶӰ����<br>
	 * Ĭ�ϵ�ͶӰΪ����ͶӰ���������͸��ͶӰ�뽫��һλ��Ϊ 1
	 */
	@Override
	public float[] setProjectionParams(float ratio) {
		return new float[] { 0, -ratio, ratio, -1, 1, 1, 100 };
	}

	/**
	 * �����ӿ�
	 */
	@Override
	public int[] setViewport(int width, int height) {
		return new int[] { 0, 0, width, height };
	}

	/**
	 * �������������
	 */
	@Override
	public float[] setCamera() {
		// ����� ���� �Ϸ� 3 ��
		return new float[] { 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f };
	}

	/**
	 * Ĭ�ϵĳ�ʼ������<br>
	 * 
	 * {@link #Jqscene.onSurfaceCreated()}<br>
	 * ��onSurfaceCreated()���ô˷���
	 */
	@Override
	public void initTexture() {
	}

}
