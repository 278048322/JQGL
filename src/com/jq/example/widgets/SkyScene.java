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

	static float direction = 0;// ���߷���
	static float cx = 0;// �����x����
	static float cz = 0;// �����z����
	static final float DEGREE_SPAN = (float) (3.0 / 180.0f * Math.PI);// �����ÿ��ת���ĽǶ�
	// �߳�ѭ���ı�־λ
	boolean flag = true;
	float x;
	float y;
	float Offset = 10;

	// /**
	// * ����ͶӰ����
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
								&& y < All3DActivity.HEIGHT / 2) {// ��ǰ
							Offset = Offset - 0.5f;
						} else if (x > All3DActivity.WIDTH / 2
								&& x < All3DActivity.WIDTH && y > 0
								&& y < All3DActivity.HEIGHT / 2) {// ���
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
		// �����µĹ۲�Ŀ���XZ����
		cx = (float) (Math.sin(direction) * Offset);// �۲�Ŀ���x����
		cz = (float) (Math.cos(direction) * Offset);// �۲�Ŀ���z����

		// // �����������ĳ���
		// mRender.tg.calculateBillboardDirection();
		//
		// // �����������ӵ�ľ�������
		// Collections.sort(mRender.tg.alist);
		// �����µ������λ��
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
		// ����view�Ļ����Լ��ķ���
		graphics[0].drawSelf();

	}

	@Override
	public void sceneSetting() {
	}

}
