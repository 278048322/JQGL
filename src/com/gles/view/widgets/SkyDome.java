package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.base.JqGraphicsFactory;
import com.gles.view.base.JqVertex;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.util.JqTextureUtil;

public class SkyDome extends JqTextureView {

	private int drawable;

	public SkyDome(GLSurfaceView mv, int drawable) {
		super(mv, drawable);
		this.drawable = drawable;
	}

	@Override
	public JqVertex loadData(float width, float height, float texWidth,
			float texHeight) {

		return JqGraphicsFactory.getBallUpVertexData(100f, 10);
	}

	@Override
	public int initTexture() {
		return JqTextureUtil.getInstance(mSurfaceView.getResources())
				.create2DTexture(drawable);
	}

}
