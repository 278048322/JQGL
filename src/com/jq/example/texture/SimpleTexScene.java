package com.jq.example.texture;

import android.content.Context;

import com.example.jqglstudy_1.R;
import com.gles.view.contents.JqMatrixState;
import com.gles.view.contents.JqTextureUtil;
import com.gles.view.graphics.JqGraphics;
import com.gles.view.scene.JqDefaultScene;

/**
 * 简单的纹理例子
 * 
 * @author qianjunping
 * 
 */
public class SimpleTexScene extends JqDefaultScene {

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
		JqMatrixState.saveMatrix();
		JqMatrixState.rotate(angle, 1, 0, 0);
		for (int i = 0; i < graphics.length; i++) {
			graphics[i].drawSelf();
		}
		JqMatrixState.restoreMatrix();
		angle++;
	}

}
