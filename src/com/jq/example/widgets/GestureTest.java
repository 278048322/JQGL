package com.jq.example.widgets;

import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;


public class GestureTest implements  OnGestureListener {
	private String tag = "GestureTest";
	float mPreviousX, mPreviousY, dx, dy, yAngle;
	private float angle;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// �Ƕ����ű���
	private VelocityTracker mVelocivtyTracker;//��û���������
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
	 * e1 ���δ��ص�ͼ��event1
	e2 ÿ�δ���onScroll�����õ��ĵ�event2 
	distance����һ�ε�event2 ��ȥ ��ǰevent2�õ��Ľ�� //ע�⵽˳�� 
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
	 * ��Ļ�����Ե������Ļ���ϱ�Ե
	 * e1��ָ��һ�ΰ�����Ļ����� e2ָ����̧����ָ����յ�
	 * velocityXΪ����ˮƽ������ٶȣ�velocityYΪ������ֱ������ٶ�
	 * ��ָ���һ������յ㣨e2������㣨e1�����Ҳ࣬��e2.getX() - e1.getX() ����0
		��ָ���󻬶����յ㣨e2������㣨e1������࣬��e2.getX() - e1.getX() С��0
		��ָ���»������յ㣨e2������㣨e1�����²࣬��e2.getY() - e1.getY() ����0
	            ��ָ���ϻ������յ㣨e2������㣨e1�����ϲ࣬��e2.getY() - e1.getY() С��0
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
		
		
		k = (down1Y-y2)/(down1X-x2);// ��ʼ�� - ̧���  
		 kkkk =1/k;
		/*if (e2.getRawX()-e1.getRawX()>0 && Math.abs(velocityX)-Math.abs(velocityY)>0 ) {
			Log.e(tag, "�������һ���");
		}else if(e2.getRawX()-e1.getRawX()<0 && Math.abs(velocityX)-Math.abs(velocityY)>0){
			Log.e(tag, "�������󻬶���");
		}else if (e2.getRawY()-e1.getRawY()>0 && Math.abs(velocityX)-Math.abs(velocityY)<0) {
			Log.e(tag, "�������»���");
		}else{
			Log.e(tag, "�������ϻ���");
			}*/
		return false;
	}

}
