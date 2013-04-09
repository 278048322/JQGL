package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.base.JqVertex;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.util.JqTextureUtil;

/**
 * �� ImageView�ؼ����������һ������ͼ<br>
 * ͼ����ʾΪ����
 * 
 * @author qianjunping
 * 
 */
public class TextureImage extends JqTextureView {

	private GLSurfaceView mGlSurfaceView;
	private int textureDrawable = -1;

	public TextureImage(GLSurfaceView mv, int drawable) {
		super(mv, drawable);
		this.mGlSurfaceView = mv;
		this.textureDrawable = drawable;
	}

	@Override
	public JqVertex loadData(float width, float height, float texWidth,
			float texHeight) {

		float vertices[] = new float[] {
				//
				-width / 2, height / 2, 0,
				//
				-width / 2, -height / 2, 0,
				//
				width / 2, -height / 2, 0,
				//
				width / 2, -height / 2, 0,
				//
				width / 2, height / 2, 0,
				//
				-width / 2, height / 2, 0 };

		// ���������������ݵĳ�ʼ��
		float texCoor[] = new float[] {
				//
				0, 0,
				//
				0, texHeight,
				//
				texWidth, texHeight,
				//
				texWidth, texHeight,
				//
				texWidth, 0,
				//
				0, 0 };
		return new JqVertex(vertices, null, texCoor);
	}

	@Override
	public int initTexture() {
		return JqTextureUtil.getInstance(mGlSurfaceView.getResources())
				.create2DTexture(textureDrawable);
	}
}
