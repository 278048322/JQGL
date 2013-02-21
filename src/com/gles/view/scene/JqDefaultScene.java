package com.gles.view.scene;

import android.content.Context;

public abstract class JqDefaultScene extends JqScene {

	public JqDefaultScene(Context context) {
		super(context);
	}

	/**
	 * 设置投影矩阵<br>
	 * 默认的投影为正交投影，如需采用透视投影请将第一位置为 1
	 */
	@Override
	public float[] setProjectionParams(float ratio) {
		return new float[] { 0, -ratio, ratio, -1, 1, 1, 100 };
	}

	/**
	 * 设置视口
	 */
	@Override
	public int[] setViewport(int width, int height) {
		return new int[] { 0, 0, width, height };
	}

	/**
	 * 设置摄像机矩阵
	 */
	@Override
	public float[] setCamera() {
		// 照相机 物体 上方 3 处
		return new float[] { 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f };
	}

	/**
	 * 默认的初始化纹理<br>
	 * 
	 * {@link #Jqscene.onSurfaceCreated()}<br>
	 * 由onSurfaceCreated()调用此方法
	 */
	@Override
	public void initTexture() {
	}

}
