package com.jq.example.widgets;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.example.jqglstudy_1.R;
import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqScene;
import com.gles.view.widgets.Mountain;
import com.jq.activitys.All3DActivity;

public class MountainScene extends JqScene {

	private String TAG = "MountainScene";
	static float direction = 0;// ���߷���
	static float cx = 0;// �����x����
	static float cz = 0;// �����z����
	static final float DEGREE_SPAN = (float) (3.0 / 180.0f * Math.PI);// �����ÿ��ת���ĽǶ�
	// �߳�ѭ���ı�־λ
	boolean flag = true;
	float x;
	float y;
	float Offset = 10;

	public MountainScene(Context context) {
		super(context);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = event.getX();
		y = event.getY();

		Log.e(TAG, "X=====>" + x + "Y=======>" + y);
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
							Log.e(TAG, "X=====>" + x + "Y=======>" + y
									+ "offset=====>" + Offset);
						} else if (x > All3DActivity.WIDTH / 2
								&& x < All3DActivity.WIDTH && y > 0
								&& y < All3DActivity.HEIGHT / 2) {// ���
							Offset = Offset + 0.5f;
							Log.e(TAG, "X=====>" + x + "Y=======>" + y
									+ "offset=====>" + Offset);
						} else if (x > 0 && x < All3DActivity.WIDTH / 2
								&& y > All3DActivity.HEIGHT / 2
								&& y < All3DActivity.HEIGHT) {
							direction = direction + DEGREE_SPAN;
							Log.e(TAG, "X=====>" + x + "Y=======>" + y
									+ "direction=====>" + direction);
						} else if (x > All3DActivity.WIDTH / 2
								&& x < All3DActivity.WIDTH
								&& y > All3DActivity.HEIGHT / 2
								&& y < All3DActivity.HEIGHT) {
							direction = direction - DEGREE_SPAN;
							Log.e(TAG, "X=====>" + x + "Y=======>" + y
									+ "direction=====>" + direction);
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
		JqGL.setCamer(new float[] { cx, 0, cz, 0, 0, 0, 0, 1, 0 });
		return true;
	}

	@Override
	public JqGraphics[] onCreateView() {

		Mountain moun = new Mountain(this, R.drawable.grass, R.drawable.land);
		moun.setWidthAndHeight(40, 40);
		moun.setTexWidthAndHeight(4, 4);
		return new JqGraphics[] { moun };
	}

	@Override
	public void onDraw(JqGraphics[] graphics) {
		// ����view�Ļ����Լ��ķ���
		graphics[0].onDraw();
	}

	@Override
	public void sceneSetting() {
		// openBackCut(true);
		// openClockwiseWind(true);
	}

}
