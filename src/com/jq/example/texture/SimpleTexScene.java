package com.jq.example.texture;

import android.content.Context;

import com.example.jqglstudy_1.R;
import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqMatrixState;
import com.gles.view.base.JqScene;
import com.gles.view.util.JqTextureUtil;

/**
 * 简单的纹理例子
 * 
 * @author qianjunping
 * 
 */
public class SimpleTexScene extends JqScene {

	public SimpleTexScene(Context context) {
		super(context);
	}

	@Override
	public void sceneSetting() {
	}

	private int textureId;

	@Override
	public void initTexture() {
		textureId = JqTextureUtil.getInstance(this.getResources())
				.create2DTexture(R.drawable.wall);
	}

	@Override
	public JqGraphics[] onCreateView() {
		return new JqGraphics[] { new Triangle(this, "trx/vertex.sh",
				"trx/frag.sh") };
	}

	float angle = 10;

	@Override
	public void onDraw(JqGraphics[] graphics) {
		JqGL.saveSceneMatrix();
		JqGL.rotate(angle, 1, 0, 0);
		for (int i = 0; i < graphics.length; i++) {
			graphics[i].onDraw();
		}
		JqGL.restoreSceneMatrix();
		angle++;
	}

}
