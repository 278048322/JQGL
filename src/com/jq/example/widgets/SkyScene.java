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
import com.gles.view.widgets.TextureSquare;
import com.gles.view.widgets.Mountain;
import com.gles.view.widgets.Signboard;
import com.gles.view.widgets.SkyBox;
import com.jq.activitys.All3DActivity;

public class SkyScene extends JqDefaultScene {

	static float direction = 0;// 视线方向
	static float cx = 0;// 摄像机x坐标
	static float cz = 0;// 摄像机z坐标
	static final float DEGREE_SPAN = (float) (3.0 / 180.0f * Math.PI);// 摄像机每次转动的角度
	// 线程循环的标志位
	boolean flag = true;
	float x;
	float y;
	float Offset = 10;

	// /**
	// * 设置投影矩阵
	// */
	// @Override
	// public float[] setProjectionParams(float ratio) {
	// return new float[] { 1, -ratio, ratio, -1, 1, 1, 100 };
	// }

	public SkyScene(Context context) {
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

		// SkyBox sy = new SkyBox(this);
		// sy.addChild();
		TextureSquare image = new TextureSquare(this, R.drawable.and);
		image.setWidthAndHeight(2, 2);
		image.setTexWidthAndHeight(10, 10);
		return new JqGraphics[] { image };
	}

	@Override
	public void onDraw(JqGraphics[] graphics) {
		// 调用view的画出自己的方法
		graphics[0].drawSelf();

	}

	@Override
	public void sceneSetting() {
	}

}
