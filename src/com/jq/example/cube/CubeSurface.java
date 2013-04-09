package com.jq.example.cube;

import android.content.Context;

import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqScene;

public class CubeSurface extends JqScene {

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
		JqGL.saveSceneMatrix();
		;
		JqGL.rotate(ange, 0.3f, 1, 0);
		for (int i = 0; i < graphics.length; i++) {
			JqGL.saveSceneMatrix();
			;
			switch (i) {
			case 0:
				graphics[0].onDraw();
				break;
			case 1:
				JqGL.translate(-0.5f, 0f, -0.5f);
				JqGL.rotate(90, 0f, 1f, 0f);
				graphics[1].onDraw();
				break;
			case 2:
				JqGL.translate(0f, 0.5f, -0.5f);
				JqGL.rotate(90, 1f, 0f, 0f);
				graphics[2].onDraw();
				break;
			case 3:
				JqGL.translate(0f, -0.5f, -0.5f);
				JqGL.rotate(-90, 1f, 0f, 0f);
				graphics[3].onDraw();
				break;
			case 4:

				JqGL.translate(0.5f, 0f, -0.5f);
				JqGL.rotate(90, 0f, 1f, 0f);
				graphics[4].onDraw();
				break;
			case 5:

				JqGL.translate(0f, 0f, -1.0f);
				// JqGL.rotate(-90, 1f, 0f, 0f);
				graphics[5].onDraw();
				break;

			default:
				break;

			}
			JqGL.restoreSceneMatrix();
		}
		JqGL.restoreSceneMatrix();
		ange++;
	}

	@Override
	public void sceneSetting() {

	}

}
