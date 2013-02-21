package com.jq.example.ball;

import android.content.Context;
import android.view.MotionEvent;

import com.example.jqglstudy_1.R;
import com.gles.view.contents.JqMatrixState;
import com.gles.view.contents.JqTextureUtil;
import com.gles.view.graphics.JqGraphics;
import com.gles.view.scene.JqDefaultScene;

public class BallScene extends JqDefaultScene {

	float mPreviousX, mPreviousY, dx, dy, yAngle;
	boolean isX = true;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// �Ƕ����ű���

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getRawX();
		float y = e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// ���غ���λ��̫����y����ת
			dx = x - mPreviousX;// ���㴥�ر�Xλ��
			dy = y - mPreviousY;
			angle++;

			if (Math.abs(dx) > Math.abs(dy)) {
				isX = false;
			} else {
				isX = true;
			}
			yAngle += dx * TOUCH_SCALE_FACTOR;// ����̫����y����ת�ĽǶ�

		}
		mPreviousX = x;// ��¼���ر�λ��
		mPreviousY = y;

		return true;
	}

	public BallScene(Context context) {
		super(context);
	}

	@Override
	public void sceneSetting() {

	}

	/**
	 * ����ͶӰ����
	 */
	@Override
	public float[] setProjectionParams(float ratio) {
		return new float[] { 0, -ratio, ratio, -1, 1, 1, 100 };
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

		JqMatrixState.setLightLocation(sunx, 0, sunz);

		JqMatrixState.saveMatrix();

		JqMatrixState.rotate(angle, 0, 1, 0);
		for (int i = 0; i < graphics.length; i++) {
			graphics[i].drawSelf(indexDay, indexNight);
		}
		JqMatrixState.restoreMatrix();
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
