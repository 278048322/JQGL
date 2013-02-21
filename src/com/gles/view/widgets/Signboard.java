package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqMatrixState;
import com.gles.view.contents.JqTextureUtil;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.loadutil.ObjData3D;

/**
 * Χ��Y�ᣬ���������λ��ת���Ŀؼ���<br>
 * signboard(��־��) ʼ�����������<br>
 * 
 * @author qianjunping
 * 
 */
public class Signboard extends JqTextureView {

	private GLSurfaceView mGlSurfaceView;
	private int textureDrawable = -1;

	public Signboard(GLSurfaceView mv, int drawable) {
		super(mv, drawable);
		this.mGlSurfaceView = mv;
		this.textureDrawable = drawable;
	}

	@Override
	protected void chageMatrix() {
		JqMatrixState.translate(locationX, locationY, locationZ);
		JqMatrixState.rotate(calculateBillboardDirection(), rotaX, 1, rotaZ);
	}

	private float calculateBillboardDirection() {

		float yAngle = 0f;
		float[] camer = JqMatrixState.getCameraLocation();
		// ���������λ�ü�����ľ�泯��
		float xspan = locationX - camer[0];
		float zspan = locationZ - camer[2];

		if (zspan <= 0) {
			yAngle = (float) Math.toDegrees(Math.atan(xspan / zspan));
		} else {
			yAngle = 180 + (float) Math.toDegrees(Math.atan(xspan / zspan));
		}

		return yAngle;
	}

	@Override
	public ObjData3D setData() {

		float tempWidth = width / 2f;
		float tempheight = height / 2f;

		float[] vertexs = new float[] {
				// 0
				-tempWidth, tempheight, 0,
				// 1
				-tempWidth, 0, 0,
				// 2
				tempWidth, tempheight, 0,
				// 3
				-tempWidth, 0, 0,
				// 4
				tempWidth, 0, 0,
				// 5
				tempWidth, tempheight, 0 };

		float[] textures = new float[] {
				// 0
				0, 0,
				// 1
				0, texHeight,
				// 2
				texWidth, 0,
				// 3
				0, texHeight,
				// 4
				texWidth, texHeight,
				// 5
				texWidth, 0 };
		return new ObjData3D(vertexs, null, textures);
	}

	@Override
	public int initTexture() {
		return JqTextureUtil.getInstance(mGlSurfaceView.getResources())
				.create2DTexture(textureDrawable);
	}

}
