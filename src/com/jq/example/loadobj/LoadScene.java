package com.jq.example.loadobj;

import android.content.Context;

import com.example.jqglstudy_1.R;
import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqMatrixState;
import com.gles.view.base.JqScene;
import com.gles.view.util.JqTextureUtil;

public class LoadScene extends JqScene {

	public LoadScene(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sceneSetting() {
		// TODO Auto-generated method stub
		JqGL.setCamer(new float[] { 0, 0, 5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f });
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
		JqGL.saveSceneMatrix();
		JqGL.translate(0, 0, -25f);
		JqGL.rotate(angle, 1, 0, 0);
		graphics[0].onDraw(texID);
		JqGL.restoreSceneMatrix();
		angle++;
	}

}
