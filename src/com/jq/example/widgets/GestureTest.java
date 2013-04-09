package com.jq.example.widgets;

import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;


public class GestureTest implements  OnGestureListener {
	private String tag = "GestureTest";
	float mPreviousX, mPreviousY, dx, dy, yAngle;
	private float angle;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// 角度缩放比例
	private VelocityTracker mVelocivtyTracker;//获得滑动的速率
	private float kkkk;
	@Override
	public boolean onDown(MotionEvent e) {
		Log.e(tag, "onDown");
		down1X = e.getRawX();
		down1Y = e.getRawY();
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	/**
	 * e1 初次触控地图的event1
	e2 每次触发onScroll函数得到的的event2 
	distance是上一次的event2 减去 当前event2得到的结果 //注意到顺序 
	lastEvent2 - event2 = distance
	 */
	private float b;
	private float k;
	private float down1X,down1Y;
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		switch (e1.getAction()&MotionEvent.ACTION_MASK) {
		
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		default:
			break;
		}
		
		
		return false;
	}
	
	public float getKKK(){
		return kkkk;
	}
	public float getyAngle() {
		return yAngle;
	}

	public void setyAngle(float yAngle) {
		this.yAngle = yAngle;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	/*
	 * 屏幕的左边缘或者屏幕的上边缘
	 * e1手指第一次按上屏幕的起点 e2指的是抬起手指后的终点
	 * velocityX为向量水平方向的速度，velocityY为向量垂直方向的速度
	 * 手指向右滑动，终点（e2）在起点（e1）的右侧，有e2.getX() - e1.getX() 大于0
		手指向左滑动，终点（e2）在起点（e1）的左侧，有e2.getX() - e1.getX() 小于0
		手指向下滑动，终点（e2）在起点（e1）的下侧，有e2.getY() - e1.getY() 大于0
	            手指向上滑动，终点（e2）在起点（e1）的上侧，有e2.getY() - e1.getY() 小于0
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
		float x2 = e2.getRawX();
		float y2= e2.getRawY();
		
		float x1 = e1.getRawX();
		float y1= e1.getRawY();
		
//		float distance1 = Math.sqrt(Math.pow(x, y));
		
		
		k = (down1Y-y2)/(down1X-x2);// 起始点 - 抬起点  
		 kkkk =1/k;
		/*if (e2.getRawX()-e1.getRawX()>0 && Math.abs(velocityX)-Math.abs(velocityY)>0 ) {
			Log.e(tag, "手势向右滑动");
		}else if(e2.getRawX()-e1.getRawX()<0 && Math.abs(velocityX)-Math.abs(velocityY)>0){
			Log.e(tag, "手势向左滑动势");
		}else if (e2.getRawY()-e1.getRawY()>0 && Math.abs(velocityX)-Math.abs(velocityY)<0) {
			Log.e(tag, "手势向下滑动");
		}else{
			Log.e(tag, "手势向上滑动");
			}*/
		return false;
	}

}
