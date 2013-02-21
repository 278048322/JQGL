package com.gles.view.scene;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.gles.view.contents.JqMatrixState;
import com.gles.view.graphics.JqGraphics;
import com.gles.view.touch.TouchEventProcessor;

/**
 * JqGLSurfaceView
 * 
 * @author qianjunping
 * 
 */
public abstract class JqScene extends GLSurfaceView {

	// /**
	// * {@link #JqBaseGraphics.doInbackground()}
	// *
	// * 是否开启后台绘制线程
	// */
	//
	// private boolean isDoInbackground = false;

	/**
	 * 是否打开背面裁剪
	 */
	private boolean isOpenBackCut = false;

	/**
	 * 是否打开顺时针卷绕
	 */
	private boolean isClockwiseWind = false;

	/**
	 * 画布背景值 length 4 ,arga
	 */
	private float[] jqDrawBackground = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };

	/**
	 * 需要绘制的view 数组 length n
	 */
	private JqGraphics[] jqGraphics;

	/**
	 * 照相机属性 length 9
	 */
	private float[] jqCamear;

	/**
	 * 投影属性 length 7
	 */
	private float[] jqProjection;

	/**
	 * 场景渲染器
	 */
	private SceneRenderer mRenderer;

	public JqScene(Context context) {
		super(context);
		// 设置使用OPENGL ES2.0
		this.setEGLContextClientVersion(2);
		// 创建场景渲染器
		mRenderer = new SceneRenderer();
		// 设置渲染器
		setRenderer(mRenderer);
		// 设置渲染模式为主动渲染
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	/**
	 * 
	 * 场景的属性设置<br>
	 * 若无设置,可以只重写不实现<br>
	 * 例：<br>
	 * 打开背面裁剪;<br>
	 * 打开顺时针卷绕<br>
	 * 
	 */
	public abstract void sceneSetting();

	/**
	 * 设置投影方式 参数按照从上到下的顺序排列<br>
	 * 投影方式 0 正交投影 1 远交投影 <br>
	 * float left, // near面的left <br>
	 * float right, // near面的right<br>
	 * float bottom, // near面的bottom <br>
	 * float top, // near面的top<br>
	 * float near, // near面距离 <br>
	 * float far // far面距离<br>
	 * 
	 * @return float[]
	 */
	public abstract float[] setProjectionParams(float ratio);

	/**
	 * 设置视口的位置<br>
	 * <p>
	 * 需求设置 4 个参数， x、y、width、height，顺序为从上到下<br>
	 * 
	 * x 为视口距离view 的左边缘距离 <br>
	 * y 为视口距离view 的上边缘距离<br>
	 * width 为视口的宽度 <br>
	 * height 为视口的高度<br>
	 * 
	 * @param width
	 *            GlsufaceView 所占有的宽
	 * @param height
	 *            GlsufaceView 所占有的高
	 * @return int[]
	 */
	public abstract int[] setViewport(int width, int height);

	/**
	 * 摄像机属性设置矩阵(每组 x,y,z 三个坐标点)
	 * <p>
	 * 1.摄像机位置 eyes
	 * <p>
	 * 2.目标点 center
	 * <p>
	 * 3.摄像机 up 方向
	 * <p>
	 * 摄像机位置朝向9参数矩阵
	 * 
	 * eg： new float[]{0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f};
	 * 
	 * @return float[]
	 */
	public abstract float[] setCamera();

	/**
	 * 初始化纹理数据<br>
	 * {@link #Jqscene.onSurfaceCreated()}<br>
	 * 由onSurfaceCreated()调用此方法<br>
	 */
	public abstract void initTexture();

	/**
	 * 创建需要绘制的3D图形,返回需要绘制图形数组
	 */
	public abstract JqGraphics[] onCreateView();

	/**
	 * 
	 * 画出所创建图形接口<br>
	 * 在画图之前，可以设置是否开启混合效果<br>
	 * 如果开启则将要画的图与之前的图会有混合效果<br>
	 * 
	 * //开启混合<br>
	 * GLES20.glEnable(GLES20.GL_BLEND); <br>
	 * //设置混合因子<br>
	 * GLES20.glBlendFunc(GLES20.GL_SRC_COLOR, GLES20.GL_ONE_MINUS_SRC_COLOR);<br>
	 * // 关闭混合<br>
	 * GLES20.glDisable(GLES20.GL_BLEND);<br>
	 * 
	 * @param graphics
	 *            onCreateView 中所创建的view
	 */
	public abstract void onDraw(JqGraphics[] graphics);

	/**
	 * @param r
	 *            红
	 * @param g
	 *            绿
	 * @param b
	 *            蓝
	 * @param a
	 *            透明度
	 * @return
	 */
	public void setDrawBackground(float[] bg) {

		if (bg == null || bg.length <= 3) {
			throw new RuntimeException("Stack overflow");
		}

		for (int i = 0; i < jqDrawBackground.length; i++) {
			jqDrawBackground[i] = bg[i];
		}
	}

	// /**
	// * 开启图形的后台处理线程，执行图形类的 <br>
	// * {@link #doInbackgroud()};
	// */
	// public void openBackgroundThread() {
	// this.isDoInbackground = true;
	// }

	/**
	 * 打开背面裁剪<br>
	 * 不是按照规定方式卷绕的△将不会被绘制<br>
	 * 
	 * @param isOpen
	 */
	public void openBackCut(boolean isOpen) {
		this.isOpenBackCut = isOpen;
	}

	/**
	 * 打开顺时针卷绕，默认为逆时针
	 * 
	 * @param isOpen
	 */
	public void openClockwiseWind(boolean isOpen) {
		this.isClockwiseWind = isOpen;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		// SixPointedStar[] ha = new SixPointedStar[6];// 六角星数组

		public void onDrawFrame(GL10 gl) {
			// 场景设置
			sceneSetting();
			// 是否打开背面裁剪
			if (isOpenBackCut) {
				GLES20.glEnable(GLES20.GL_CULL_FACE);
			} else {
				GLES20.glDisable(GLES20.GL_CULL_FACE);
			}

			// 是否打开顺时针卷绕,系统默认是逆时针卷绕
			if (isClockwiseWind) {
				GLES20.glFrontFace(GLES20.GL_CW);
			} else {
				GLES20.glFrontFace(GLES20.GL_CCW);
			}

			// 清除深度缓冲与颜色缓冲
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
					| GLES20.GL_COLOR_BUFFER_BIT);

			// 调用客户端画图接口
			onDraw(jqGraphics);
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {

			// 初始化变换矩阵
			JqMatrixState.setInitStack();

			// 设置视窗大小及位置
			int[] tempScreen = setViewport(width, height);
			GLES20.glViewport(tempScreen[0], tempScreen[1], tempScreen[2],
					tempScreen[3]);
			// 计算GLSurfaceView的宽高比
			float ratio = (float) tempScreen[2] / tempScreen[3];
			jqProjection = setProjectionParams(ratio);

			if (jqProjection[0] > 0) {
				// 透视投影
				JqMatrixState.setProjectOutcrossing(jqProjection[1],
						jqProjection[2], jqProjection[3], jqProjection[4],
						jqProjection[5], jqProjection[6]);
			} else {
				// 平行投影
				JqMatrixState.setProjectOrthogonal(jqProjection[1],
						jqProjection[2], jqProjection[3], jqProjection[4],
						jqProjection[5], jqProjection[6]);
			}

		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// 设置屏幕背景色RGBA
			GLES20.glClearColor(jqDrawBackground[0], jqDrawBackground[1],
					jqDrawBackground[2], jqDrawBackground[3]);

			// 设置照相机
			jqCamear = setCamera();
			// 调用此方法产生摄像机9参数位置矩阵
			JqMatrixState.setCameraMatrix(jqCamear[0], jqCamear[1],
					jqCamear[2], jqCamear[3], jqCamear[4], jqCamear[5],
					jqCamear[6], jqCamear[7], jqCamear[8]);

			jqGraphics = onCreateView();

			// 初始化图形
			for (int i = 0; i < jqGraphics.length; i++) {
				if (jqGraphics[i] != null) {
					jqGraphics[i].initVertexData();
				}
			}
			// 打开深度检测
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);

			// 场景设置
			sceneSetting();

			// 初始化纹理数据
			initTexture();

			// // 打开背景线程
			// if (isDoInbackground) {
			// new Thread() {
			// @Override
			// public void run() {
			//
			// for (int i = 0; i < jqGraphics.length; i++) {
			// // TODO 如果其中一个view的 doinbackgroud 方法是
			// // 无限循环，那么就会卡在相应的方法那里 流程无法继续，需修改此种方式
			// jqGraphics[i].doInbackground();
			// }
			// };
			// }.start();
			// }
		}
	}
}
