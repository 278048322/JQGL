package com.gles.view.widgets;

import android.opengl.GLSurfaceView;

import com.gles.view.graphics.JqGraphics;
import com.gles.view.graphics.JqGraphicsGroup;

/**
 * 
 * ��պ�
 * 
 * @author qianjunping
 * 
 */
public class SkyBox extends JqGraphicsGroup {

	public SkyBox(GLSurfaceView mv) {
		super(mv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onChildsLayout(JqGraphics[] graphics) {
		// TODO Auto-generated method stub
		graphics[0].drawSelf();
	}

}
