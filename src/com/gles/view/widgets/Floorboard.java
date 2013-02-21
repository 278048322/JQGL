package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqGLContents;
import com.gles.view.contents.JqTextureUtil;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.loadutil.ObjData3D;

/**
 * 类FloorBoard(地板),可使用GL中的纹理填充的View<br>
 * 必须透视投影才能看出效果
 * 
 * @author qianjunping
 * 
 */
public class Floorboard extends JqTextureView {

	private GLSurfaceView mGlSurfaceView;
	private int textureDrawable = -1;

	public Floorboard(GLSurfaceView mv, int drawable) {
		super(mv, drawable);
		this.mGlSurfaceView = mv;
		this.textureDrawable = drawable;
	}

	@Override
	public ObjData3D setData() {

		float tempWidth = width / 2f;
		float tempheight = height / 2f;

		float[] vertexs = new float[] {
				//
				-JqGLContents.UNIT_SIZE * tempWidth,
				0,
				-JqGLContents.UNIT_SIZE * tempheight,
				//
				JqGLContents.UNIT_SIZE * tempWidth,
				0,
				-JqGLContents.UNIT_SIZE * tempheight,
				//
				JqGLContents.UNIT_SIZE * tempWidth,
				0,
				JqGLContents.UNIT_SIZE * tempheight,
				//
				-JqGLContents.UNIT_SIZE * tempWidth,
				0,
				-JqGLContents.UNIT_SIZE * tempheight,
				//
				JqGLContents.UNIT_SIZE * tempWidth, 0,
				JqGLContents.UNIT_SIZE * tempheight,
				//
				-JqGLContents.UNIT_SIZE * tempWidth, 0,
				JqGLContents.UNIT_SIZE * tempheight };

		float[] textures = new float[] {
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
		return new ObjData3D(vertexs, null, textures);
	}

	@Override
	public int initTexture() {
		return JqTextureUtil.getInstance(mGlSurfaceView.getResources())
				.create2DMipMapTexture(textureDrawable);
	}

}
