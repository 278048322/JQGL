package com.gles.view.base;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

/**
 * JqGLSurfaceView
 * 
 * 原带 默认的投影矩阵以及摄像机矩阵
 * 
 * @author qianjunping
 * 
 */
public abstract class JqScene extends GLSurfaceView {

	/**
	 * GLSurfaceView 显示的宽高比<br>
	 * 在 SceneRenderer 的 onSurfaceChanged() 被调用后被赋值，用以设置 视口以及 投影矩阵<br>
	 */
	private static float viewAspectRatio = -1;

	/**
	 * 默认的摄像机矩阵，在物体上方 Z 轴方向 5f 处，目标点为物体中心点，up 方向为平行于y轴向上
	 */
	private float[] defaultCameraProjection = new float[] { 0, 0, 5, 0f, 0f,
			0f, 0f, 1.0f, 0.0f };

	/**
	 * 默认距离 near 面的距离
	 */
	private int defaultNear = 1;

	/**
	 * 默认距离 far 面的距离
	 */
	private int defaultFar = 100;

	/**
	 * GLSufaceView 的宽度
	 */
	private static int viewWidth = -1;

	/**
	 * GLSufaceView 的高度
	 */
	private static int viewHeight = -1;

	/**
	 * 画布背景值 length 4 ,arga
	 */
	private float[] jqDrawBackground = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };

	/**
	 * 需要绘制的view 数组 length n
	 */
	private JqGraphics[] jqGraphics;

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
	 * 重新设置摄像机矩阵<br>
	 * 
	 */
	@Deprecated
	public abstract void sceneSetting();

	/**
	 * 初始化纹理数据<br>
	 * {@link #Jqscene.onSurfaceCreated()}<br>
	 * 由onSurfaceCreated()调用此方法<br>
	 */
	public void initTexture() {

	}

	/**
	 * 创建需要绘制的3D图形,返回需要绘制图形数组
	 */
	public abstract JqGraphics[] onCreateView();

	/**
	 * 
	 * 画出所创建图形接口,系统自动刷新此接口<br>
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

	/**
	 * 获得 GLSufaceView 显示的宽高比例
	 * 
	 * @return
	 */
	public static float getViewAspectRatio() {

		if (viewAspectRatio == -1) {
			throw new RuntimeException(
					"viewAspectRatio only be use after SceneRenderer. onSurfaceChanged()");
		}
		return viewAspectRatio;
	}

	public static int getViewWidth() {

		if (viewWidth == -1) {
			throw new RuntimeException(
					"viewWidth only be use after SceneRenderer. onSurfaceChanged()");
		}
		return viewWidth;
	}

	public static int getViewHeight() {

		if (viewHeight == -1) {
			throw new RuntimeException(
					"viewHeight only be use after SceneRenderer. onSurfaceChanged()");
		}
		return viewHeight;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		// SixPointedStar[] ha = new SixPointedStar[6];// 六角星数组

		public void onDrawFrame(GL10 gl) {

			// 清除深度缓冲与颜色缓冲
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
					| GLES20.GL_COLOR_BUFFER_BIT);

			// 调用客户端画图接口
			onDraw(jqGraphics);
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {

			// 设置默认的视口大小
			JqGL.setViewport(0, 0, width, height);

			JqScene.viewWidth = width;
			JqScene.viewHeight = height;
			viewAspectRatio = (float) width / height;

			// 设置默认的投影矩阵
			JqGL.setProjectionParams(1, viewAspectRatio, defaultNear,
					defaultFar);

			// 场景设置
			sceneSetting();

			// 初始化纹理数据
			initTexture();

		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {

			// 初始化变换矩阵
			JqMatrixState.setInitStack();

			// 设置照相机矩阵
			JqGL.setCamer(defaultCameraProjection);

			// 设置屏幕背景色RGBA
			GLES20.glClearColor(jqDrawBackground[0], jqDrawBackground[1],
					jqDrawBackground[2], jqDrawBackground[3]);

			jqGraphics = onCreateView();

			// 初始化图形
			for (int i = 0; i < jqGraphics.length; i++) {
				if (jqGraphics[i] != null) {
					jqGraphics[i].initGraphicsData();
				}
			}
			// 打开深度检测
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);

		}
	}
}
