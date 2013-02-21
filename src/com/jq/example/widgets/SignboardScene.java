package com.jq.example.widgets;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;

import com.example.jqglstudy_1.R;
import com.gles.view.contents.JqMatrixState;
import com.gles.view.graphics.JqGraphics;
import com.gles.view.scene.JqDefaultScene;
import com.gles.view.widgets.Floorboard;
import com.gles.view.widgets.Mountain;
import com.gles.view.widgets.Signboard;
import com.jq.activitys.All3DActivity;

public class SignboardScene extends JqDefaultScene {

	static float direction = 0;// 视线方向
	static float cx = 0;// 摄像机x坐标
	static float cz = 0;// 摄像机z坐标
	static final float DEGREE_SPAN = (float) (3.0 / 180.0f * Math.PI);// 摄像机每次转动的角度
	// 线程循环的标志位
	boolean flag = true;
	float x;
	float y;
	float Offset = 10;

	/**
	 * 设置投影矩阵
	 */
	@Override
	public float[] setProjectionParams(float ratio) {
		return new float[] { 1, -ratio, ratio, -1, 1, 1, 100 };
	}

	public SignboardScene(Context context) {
		super(context);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = event.getX();
		y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			flag = true;
			new Thread() {
				@Override
				public void run() {
					while (flag) {
						if (x > 0 && x < All3DActivity.WIDTH / 2 && y > 0
								&& y < All3DActivity.HEIGHT / 2) {// 向前
							Offset = Offset - 0.5f;
						} else if (x > All3DActivity.WIDTH / 2
								&& x < All3DActivity.WIDTH && y > 0
								&& y < All3DActivity.HEIGHT / 2) {// 向后
							Offset = Offset + 0.5f;
						} else if (x > 0 && x < All3DActivity.WIDTH / 2
								&& y > All3DActivity.HEIGHT / 2
								&& y < All3DActivity.HEIGHT) {
							direction = direction + DEGREE_SPAN;
						} else if (x > All3DActivity.WIDTH / 2
								&& x < All3DActivity.WIDTH
								&& y > All3DActivity.HEIGHT / 2
								&& y < All3DActivity.HEIGHT) {
							direction = direction - DEGREE_SPAN;
						}
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			break;
		case MotionEvent.ACTION_UP:
			flag = false;
			break;
		}
		// 设置新的观察目标点XZ坐标
		cx = (float) (Math.sin(direction) * Offset);// 观察目标点x坐标
		cz = (float) (Math.cos(direction) * Offset);// 观察目标点z坐标

		// // 计算所有树的朝向
		// mRender.tg.calculateBillboardDirection();
		//
		// // 给树按照离视点的距离排序
		// Collections.sort(mRender.tg.alist);
		// 设置新的摄像机位置
		JqMatrixState.setCameraMatrix(cx, 0, cz, 0, 0, 0, 0, 1, 0);
		return true;
	}

	@Override
	public JqGraphics[] onCreateView() {

		// Mountain moun = new Mountain(this, R.drawable.grass,
		// R.drawable.land);
		// moun.setWidthAndHeight(40, 40);
		// moun.setTexWidthAndHeight(4, 4);

		Floorboard image = new Floorboard(this, R.drawable.desert);
		image.setTexWidthAndHeight(12, 12);
		image.setWidthAndHeight(120, 120);
		image.translate(0, -2, 0);

		Signboard sign1 = new Signboard(this, R.drawable.tree);
		sign1.translate(-10, -2, 0);
		sign1.setWidthAndHeight(10, 15);
		Signboard sign2 = new Signboard(this, R.drawable.tree);
		sign2.translate(5, -2, -19);
		sign2.setWidthAndHeight(9, 13);
		Signboard sign3 = new Signboard(this, R.drawable.tree);
		sign3.translate(20, -2, 0);
		sign3.setWidthAndHeight(12, 17);
		// sign1, sign2,
		return new JqGraphics[] { sign3, sign2, sign1, image };
	}

	@Override
	public void onDraw(JqGraphics[] graphics) {
		// 调用view的画出自己的方法
		graphics[3].drawSelf();

		// 开启混合
		GLES20.glEnable(GLES20.GL_BLEND);
		// 设置混合因子
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		graphics[0].drawSelf();
		graphics[1].drawSelf();
		graphics[2].drawSelf();
		// 关闭混合
		GLES20.glDisable(GLES20.GL_BLEND);
	}

	@Override
	public void sceneSetting() {
		// openBackCut(true);
		// openClockwiseWind(true);
	}

}
