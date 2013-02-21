package com.jq.example.loadobj;

import android.content.Context;

import com.example.jqglstudy_1.R;
import com.gles.view.contents.JqMatrixState;
import com.gles.view.contents.JqTextureUtil;
import com.gles.view.graphics.JqGraphics;
import com.gles.view.scene.JqDefaultScene;

public class LoadScene extends JqDefaultScene {

	public LoadScene(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float[] setProjectionParams(float ratio) {
		return new float[] { 1, -ratio, ratio, -1, 1, 2, 150 };
	}

	@Override
	public int[] setViewport(int width, int height) {
		return new int[] { 0, 0, width, height };
	}

	@Override
	public float[] setCamera() {
		// 照相机 物体 上方 3 处
		return new float[] { 0, 0, 5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f };
		// return new float[] { 0, 0, 0, 0f, 0f, -1f, 0f, 1.0f, 0.0f };
	}

	@Override
	public void sceneSetting() {
		// TODO Auto-generated method stub

	}

	private int texID;

	@Override
	public void initTexture() {
		texID = JqTextureUtil.getInstance(this.getResources()).create2DTexture(
				R.drawable.ghxp);
	}

	@Override
	public JqGraphics[] onCreateView() {
		return new JqGraphics[] { new LoadGraphic(this, "obj/vertex.sh",
				"obj/frag.sh") };
	}

	float angle;

	@Override
	public void onDraw(JqGraphics[] graphics) {
		JqMatrixState.saveMatrix();
		JqMatrixState.translate(0, 0, -25f);
		JqMatrixState.rotate(angle, 1, 0, 0);
		graphics[0].drawSelf(texID);
		JqMatrixState.restoreMatrix();
		angle++;
	}

}
