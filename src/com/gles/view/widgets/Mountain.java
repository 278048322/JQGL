package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqTextureUtil;
import com.gles.view.graphics.JqTextureView;
import com.gles.view.loadutil.LoadPixelsUtil;
import com.gles.view.loadutil.ObjData3D;

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
	public ObjData3D setData() {

		ObjData3D obj = LoadPixelsUtil.getLandObjFromPixels(
				mSurfaceView.getResources(), pixDId, 20f, -2f, 1f);

		return obj;
	}

	@Override
	public int initTexture() {
		return JqTextureUtil.getInstance(mSurfaceView.getResources())
				.create2DMipMapTexture(texDId);
	}
}
