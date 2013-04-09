package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.base.JqVertex;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.util.JqLoadPixelsUtil;
import com.gles.view.util.JqTextureUtil;

/**
 * É½µØ
 * 
 * @author qianjunping
 * 
 */
public class Mountain extends JqTextureView {

	private int texDId;
	private int pixDId;

	public Mountain(GLSurfaceView mv, int texDrawableId, int pixDrawableId) {
		super(mv, texDrawableId);
		this.texDId = texDrawableId;
		this.pixDId = pixDrawableId;
	}

	@Override
	public JqVertex loadData(float width, float height, float texWidth,
			float texHeight) {

		JqVertex obj = JqLoadPixelsUtil.getLandObjFromPixels(
				mSurfaceView.getResources(), pixDId, 20f, -2f, 1f);

		return obj;
	}

	@Override
	public int initTexture() {
		return JqTextureUtil.getInstance(mSurfaceView.getResources())
				.create2DMipMapTexture(texDId);
	}
}
