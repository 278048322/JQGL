package com.jq.example.ball;

import android.content.Context;
import android.view.MotionEvent;

import com.example.jqglstudy_1.R;
import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqMatrixState;
import com.gles.view.base.JqScene;
import com.gles.view.util.JqTextureUtil;

public class BallScene extends JqScene {

	float mPreviousX, mPreviousY, dx, dy, yAngle;
	boolean isX = true;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// ½Ç¶ÈËõ·Å±ÈÀý

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getRawX();
		float y = e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// ´¥¿ØºáÏòÎ»ÒÆÌ«ÑôÈÆyÖáÐý×ª
			dx = x - mPreviousX;// ¼ÆËã´¥¿Ø±ÊXÎ»ÒÆ
			dy = y - mPreviousY;
			angle++;

			/*
			 * if (Math.abs(dx) > Math.abs(dy)) { isX = false; } else { isX =
			 * true; }
			 */
			yAngle += dx * TOUCH_SCALE_FACTOR;// ÉèÖÃÌ«ÑôÈÆyÖáÐý×ªµÄ½Ç¶È

		}
		mPreviousX = x;// ¼ÇÂ¼´¥¿Ø±ÊÎ»ÖÃ
		mPreviousY = y;

		return true;
	}

	public BallScene(Context context) {
		super(context);
	}

	@Override
	public void sceneSetting() {

	}

	@Override
	public JqGraphics[] onCreateView() {
		return new JqGraphics[] { new Ball(this, "tex_ball/vertex.sh",
				"tex_ball/frag.sh") };
	}

	private int indexDay;
	private int indexNight;
	private float angle;

	@Override
	public void onDraw(JqGraphics[] graphics) {

		float sunx = (float) (Math.cos(Math.toRadians(yAngle)));
		float sunz = -(float) (Math.sin(Math.toRadians(yAngle))) * 5;

		JqGL.setLightLocation(sunx, 0, sunz);

		JqGL.saveSceneMatrix();

		//
		JqGL.rotate(angle, 0, 1, 0);

		// openBackCut(true);
		for (int i = 0; i < graphics.length; i++) {
			graphics[i].onDraw(indexDay, indexNight);
		}
		JqGL.restoreSceneMatrix();
		angle++;
		yAngle += 0.5;
	}

	@Override
	public void initTexture() {
		indexDay = JqTextureUtil.getInstance(this.getResources())
				.create2DTexture(R.drawable.earth);
		indexNight = JqTextureUtil.getInstance(this.getResources())
				.create2DTexture(R.drawable.earthn);
	}

}
