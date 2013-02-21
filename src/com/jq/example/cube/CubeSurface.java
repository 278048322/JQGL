package com.jq.example.cube;

import android.content.Context;

import com.gles.view.contents.JqMatrixState;
import com.gles.view.graphics.JqGraphics;
import com.gles.view.scene.JqDefaultScene;

public class CubeSurface extends JqDefaultScene {

	private float ange = 30;
	
	public CubeSurface(Context context) {
		super(context);
	}

	@Override
	public JqGraphics[] onCreateView() {
		return new JqGraphics[] {
				new Cube(this, "cube/vertex.sh", "cube/frag.sh"),
				new Cube(this, "cube/vertex.sh", "cube/frag.sh"),
				new Cube(this, "cube/vertex.sh", "cube/frag.sh"),
				new Cube(this, "cube/vertex.sh", "cube/frag.sh"),
				new Cube(this, "cube/vertex.sh", "cube/frag.sh"),
				new Cube(this, "cube/vertex.sh", "cube/frag.sh") };
	}

	@Override
	public void onDraw(JqGraphics[] graphics) {
		JqMatrixState.saveMatrix();
		JqMatrixState.rotate(ange, 0.3f, 1, 0);
		for (int i = 0; i < graphics.length; i++) {
			JqMatrixState.saveMatrix();
			switch (i) {
			case 0:
				graphics[0].drawSelf();
				break;
			case 1:
				JqMatrixState.translate(-0.5f, 0f, -0.5f);
				JqMatrixState.rotate(90, 0f, 1f, 0f);
				graphics[1].drawSelf();
				break;
			case 2:
				JqMatrixState.translate(0f, 0.5f, -0.5f);
				JqMatrixState.rotate(90, 1f, 0f, 0f);
				graphics[2].drawSelf();
				break;
			case 3:
				JqMatrixState.translate(0f, -0.5f, -0.5f);
				JqMatrixState.rotate(-90, 1f, 0f, 0f);
				graphics[3].drawSelf();
				break;
			case 4:

				JqMatrixState.translate(0.5f, 0f, -0.5f);
				JqMatrixState.rotate(90, 0f, 1f, 0f);
				graphics[4].drawSelf();
				break;
			case 5:

				JqMatrixState.translate(0f, 0f, -1.0f);
				// JqMatrixState.rotate(-90, 1f, 0f, 0f);
				graphics[5].drawSelf();
				break;

			default:
				break;

			}
			JqMatrixState.restoreMatrix();
		}
		JqMatrixState.restoreMatrix();
		ange++;
	}

	@Override
	public void sceneSetting() {

	}

}
