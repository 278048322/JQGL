package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqTextureUtil;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.loadutil.ObjData3D;

public class TextureSquare extends JqTextureView {

	private GLSurfaceView mGlSurfaceView;
	private int textureDrawable = -1;

	public TextureSquare(GLSurfaceView mv, int drawable) {
		super(mv, drawable);
		this.mGlSurfaceView = mv;
		this.textureDrawable = drawable;
	}

	@Override
	public ObjData3D setData() {

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
		return new ObjData3D(vertices, null, texCoor);
	}

	@Override
	public int initTexture() {
		return JqTextureUtil.getInstance(mGlSurfaceView.getResources())
				.create2DTexture(textureDrawable);
	}
}
