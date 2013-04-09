package com.gles.view.base;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

/**
 * JqGLSurfaceView
 * 
 * ԭ�� Ĭ�ϵ�ͶӰ�����Լ����������
 * 
 * @author qianjunping
 * 
 */
public abstract class JqScene extends GLSurfaceView {

	/**
	 * GLSurfaceView ��ʾ�Ŀ�߱�<br>
	 * �� SceneRenderer �� onSurfaceChanged() �����ú󱻸�ֵ���������� �ӿ��Լ� ͶӰ����<br>
	 */
	private static float viewAspectRatio = -1;

	/**
	 * Ĭ�ϵ�����������������Ϸ� Z �᷽�� 5f ����Ŀ���Ϊ�������ĵ㣬up ����Ϊƽ����y������
	 */
	private float[] defaultCameraProjection = new float[] { 0, 0, 5, 0f, 0f,
			0f, 0f, 1.0f, 0.0f };

	/**
	 * Ĭ�Ͼ��� near ��ľ���
	 */
	private int defaultNear = 1;

	/**
	 * Ĭ�Ͼ��� far ��ľ���
	 */
	private int defaultFar = 100;

	/**
	 * GLSufaceView �Ŀ��
	 */
	private static int viewWidth = -1;

	/**
	 * GLSufaceView �ĸ߶�
	 */
	private static int viewHeight = -1;

	/**
	 * ��������ֵ length 4 ,arga
	 */
	private float[] jqDrawBackground = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };

	/**
	 * ��Ҫ���Ƶ�view ���� length n
	 */
	private JqGraphics[] jqGraphics;

	/**
	 * ������Ⱦ��
	 */
	private SceneRenderer mRenderer;

	public JqScene(Context context) {
		super(context);
		// ����ʹ��OPENGL ES2.0
		this.setEGLContextClientVersion(2);
		// ����������Ⱦ��
		mRenderer = new SceneRenderer();
		// ������Ⱦ��
		setRenderer(mRenderer);
		// ������ȾģʽΪ������Ⱦ
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	/**
	 * 
	 * ��������������<br>
	 * ��������,����ֻ��д��ʵ��<br>
	 * ����<br>
	 * �򿪱���ü�;<br>
	 * ��˳ʱ�����<br>
	 * �����������������<br>
	 * 
	 */
	@Deprecated
	public abstract void sceneSetting();

	/**
	 * ��ʼ����������<br>
	 * {@link #Jqscene.onSurfaceCreated()}<br>
	 * ��onSurfaceCreated()���ô˷���<br>
	 */
	public void initTexture() {

	}

	/**
	 * ������Ҫ���Ƶ�3Dͼ��,������Ҫ����ͼ������
	 */
	public abstract JqGraphics[] onCreateView();

	/**
	 * 
	 * ����������ͼ�νӿ�,ϵͳ�Զ�ˢ�´˽ӿ�<br>
	 * �ڻ�ͼ֮ǰ�����������Ƿ������Ч��<br>
	 * ���������Ҫ����ͼ��֮ǰ��ͼ���л��Ч��<br>
	 * 
	 * //�������<br>
	 * GLES20.glEnable(GLES20.GL_BLEND); <br>
	 * //���û������<br>
	 * GLES20.glBlendFunc(GLES20.GL_SRC_COLOR, GLES20.GL_ONE_MINUS_SRC_COLOR);<br>
	 * // �رջ��<br>
	 * GLES20.glDisable(GLES20.GL_BLEND);<br>
	 * 
	 * @param graphics
	 *            onCreateView ����������view
	 */
	public abstract void onDraw(JqGraphics[] graphics);

	/**
	 * @param r
	 *            ��
	 * @param g
	 *            ��
	 * @param b
	 *            ��
	 * @param a
	 *            ͸����
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
	 * ��� GLSufaceView ��ʾ�Ŀ�߱���
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
		// SixPointedStar[] ha = new SixPointedStar[6];// ����������

		public void onDrawFrame(GL10 gl) {

			// �����Ȼ�������ɫ����
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
					| GLES20.GL_COLOR_BUFFER_BIT);

			// ���ÿͻ��˻�ͼ�ӿ�
			onDraw(jqGraphics);
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {

			// ����Ĭ�ϵ��ӿڴ�С
			JqGL.setViewport(0, 0, width, height);

			JqScene.viewWidth = width;
			JqScene.viewHeight = height;
			viewAspectRatio = (float) width / height;

			// ����Ĭ�ϵ�ͶӰ����
			JqGL.setProjectionParams(1, viewAspectRatio, defaultNear,
					defaultFar);

			// ��������
			sceneSetting();

			// ��ʼ����������
			initTexture();

		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {

			// ��ʼ���任����
			JqMatrixState.setInitStack();

			// �������������
			JqGL.setCamer(defaultCameraProjection);

			// ������Ļ����ɫRGBA
			GLES20.glClearColor(jqDrawBackground[0], jqDrawBackground[1],
					jqDrawBackground[2], jqDrawBackground[3]);

			jqGraphics = onCreateView();

			// ��ʼ��ͼ��
			for (int i = 0; i < jqGraphics.length; i++) {
				if (jqGraphics[i] != null) {
					jqGraphics[i].initGraphicsData();
				}
			}
			// ����ȼ��
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);

		}
	}
}
