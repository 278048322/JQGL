package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.base.JqVertex;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.util.JqTextureUtil;

/**
 * 类 ImageView控件，可以填充一张纹理图<br>
 * 图形显示为方形
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

		// 顶点纹理坐标数据的初始化
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
