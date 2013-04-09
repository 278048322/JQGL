package com.jq.example.ball;

import android.content.Context;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.jqglstudy_1.R;
import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqMatrixState;
import com.gles.view.base.JqScene;
import com.gles.view.util.JqTextureUtil;
import com.jq.example.widgets.GestureTest;

public class BallSceneTest extends JqScene {

	private String tag = "BallSceneTest";
	float mPreviousX, mPreviousY, dx, dy, yAngle;
	boolean isX = true;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// 角度缩放比例
	private float scale;
	private float mNewDist;
	private float mOldDist;
	private float mNewDist2;
	private GestureDetector mGesture;
	private int ZOOM = 3;
	private int NONE = 1;
	private int mRecode;
	private int mode;

	private float beginX0;
	private float beginY0;
	private float beginX1;
	private float beginY1;
	private float endX0;
	private float endY0;
	private float endX1;
	private float endY1;

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		switch (e.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.e(tag, "ACTION_POINTER_DOWN");
			mOldDist = spacing(e);
			initBeginXY(e);
			if (mOldDist > 10f) {
				mode = ZOOM;
				// scale = 3;
			}
			// JqMatrixState.setCameraMatrix(0, 0, 100, 0f, 0f, 0f, 0f, 1.0f,
			// 0.0f );
			break;
		case MotionEvent.ACTION_POINTER_UP:
			recodeEndXY(e);
			/**
			 * 
			 */
			float k0;
			float k1;
			float x0 = beginX0 - endX0;
			float x1 = beginX1 - endX1;
			float y0 = beginY0 - endY0;
			float y1 = beginY1 - endY1;
			k0 = (beginY0 - endY0) / (beginX0 - endX0);// 起始点 - 抬起点
			k1 = (beginY1 - endY1) / (beginX1 - endX1);
			Log.e(tag, "beginY0-endY0" + beginY0 + "endY0" + endY0 + "begingX0"
					+ beginX0 + "endX0" + endX0);
			Log.e(tag, "beginY1 - endY1--" + beginY1 + "endY1----" + endY1
					+ "begingX1" + beginX1 + "endX1" + endX1);
			Log.e(tag, "k0----->" + k0 + "k1-------->" + k1);
			if (Math.abs(k0) > 1 && Math.abs(k1) > 1) {
				k0 = k0 / 1000;
				k1 = k1 / 1000;
			}
			if (Math.abs(k0 - k1) < 0.3) {
				Log.e(tag, "调整摄像机");
			}
			/*
			 * if (x0 >= 0 && x1 >= 0 && ((y0>0&&y1>0)||(y0<0&&y1<0))) { if
			 * (Math.abs(k0-k1) >10) { Log.e(tag, "调整摄像机"); } Log.e(tag, "成立1");
			 * } if (x0 < 0 && x1 < 0 && ((y0>0&&y1>0)||(y0<0&&y1<0))) {
			 * Log.e(tag, "成立2"); }
			 */
			mode = NONE;
			break;
		case MotionEvent.ACTION_UP:
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {

				mNewDist = spacing(e);
				if (Math.abs(mNewDist - mNewDist2) > 5) {
					float AAA = (mOldDist - mNewDist);
					scale = scale + AAA / 1000f;
					if (scale > 7f) {
						scale = 7f;
					}
					if (scale < -1.8f) {
						scale = (-1.8f);
					}
					JqGL.setCamer(new float[] { 1, 0, 3 + scale, 0f, 0f, 0f,
							0f, 1.0f, 0.0f });
					mNewDist2 = mNewDist;
				}
			}
			break;
		default:
			break;
		}
		mGesture.onTouchEvent(e);

		return true;
	}

	GestureTest gesture = new GestureTest();

	public BallSceneTest(Context context) {
		super(context);
		mGesture = new GestureDetector(gesture);
	}

	@Override
	public void sceneSetting() {
		JqGL.setCamer(new float[] { 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f });
	}

	@Override
	public JqGraphics[] onCreateView() {
		return new JqGraphics[] { new Ball(this, "tex_ball/vertex.sh",
				"tex_ball/frag.sh") };
	}

	private int indexDay;
	private int indexNight;
	private float angle;

	/**
	 * 
	 * Math.toRadians(yAngle) 将角度转化的角，转化为近似相等的用弧度的角
	 */
	@Override
	public void onDraw(JqGraphics[] graphics) {
		float sunx = (float) (Math.cos(Math.toRadians(yAngle)));
		float sunz = -(float) (Math.sin(Math.toRadians(yAngle))) * 5;
		JqGL.setLightLocation(sunx, 0, sunz);
		JqGL.saveSceneMatrix();
		JqGL.rotate(angle, 1, gesture.getKKK(), 0);
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

	/*
	 * 两点手势的xy的距离想加求他们的平方根
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		Log.e(tag, "x---------------->" + x);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y); // 正平方根
	}

	/*
	 * 记录起始点位置
	 */
	private void initBeginXY(MotionEvent event) {
		beginX0 = event.getX(0);
		beginY0 = event.getY(0);
		beginX1 = event.getX(1);
		beginY1 = event.getY(1);
	}

	/*
	 * 记录移动位置的值
	 */
	private void recodeEndXY(MotionEvent event) {
		endX0 = event.getX(0);
		endY0 = event.getY(0);
		endX1 = event.getX(1);
		endY1 = event.getY(1);
	}

}
