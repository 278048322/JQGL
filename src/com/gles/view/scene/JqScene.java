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
	// * �Ƿ�����̨�����߳�
	// */
	//
	// private boolean isDoInbackground = false;

	/**
	 * �Ƿ�򿪱���ü�
	 */
	private boolean isOpenBackCut = false;

	/**
	 * �Ƿ��˳ʱ�����
	 */
	private boolean isClockwiseWind = false;

	/**
	 * ��������ֵ length 4 ,arga
	 */
	private float[] jqDrawBackground = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };

	/**
	 * ��Ҫ���Ƶ�view ���� length n
	 */
	private JqGraphics[] jqGraphics;

	/**
	 * ��������� length 9
	 */
	private float[] jqCamear;

	/**
	 * ͶӰ���� length 7
	 */
	private float[] jqProjection;

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
	 * 
	 */
	public abstract void sceneSetting();

	/**
	 * ����ͶӰ��ʽ �������մ��ϵ��µ�˳������<br>
	 * ͶӰ��ʽ 0 ����ͶӰ 1 Զ��ͶӰ <br>
	 * float left, // near���left <br>
	 * float right, // near���right<br>
	 * float bottom, // near���bottom <br>
	 * float top, // near���top<br>
	 * float near, // near����� <br>
	 * float far // far�����<br>
	 * 
	 * @return float[]
	 */
	public abstract float[] setProjectionParams(float ratio);

	/**
	 * �����ӿڵ�λ��<br>
	 * <p>
	 * �������� 4 �������� x��y��width��height��˳��Ϊ���ϵ���<br>
	 * 
	 * x Ϊ�ӿھ���view �����Ե���� <br>
	 * y Ϊ�ӿھ���view ���ϱ�Ե����<br>
	 * width Ϊ�ӿڵĿ�� <br>
	 * height Ϊ�ӿڵĸ߶�<br>
	 * 
	 * @param width
	 *            GlsufaceView ��ռ�еĿ�
	 * @param height
	 *            GlsufaceView ��ռ�еĸ�
	 * @return int[]
	 */
	public abstract int[] setViewport(int width, int height);

	/**
	 * ������������þ���(ÿ�� x,y,z ���������)
	 * <p>
	 * 1.�����λ�� eyes
	 * <p>
	 * 2.Ŀ��� center
	 * <p>
	 * 3.����� up ����
	 * <p>
	 * �����λ�ó���9��������
	 * 
	 * eg�� new float[]{0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f};
	 * 
	 * @return float[]
	 */
	public abstract float[] setCamera();

	/**
	 * ��ʼ����������<br>
	 * {@link #Jqscene.onSurfaceCreated()}<br>
	 * ��onSurfaceCreated()���ô˷���<br>
	 */
	public abstract void initTexture();

	/**
	 * ������Ҫ���Ƶ�3Dͼ��,������Ҫ����ͼ������
	 */
	public abstract JqGraphics[] onCreateView();

	/**
	 * 
	 * ����������ͼ�νӿ�<br>
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

	// /**
	// * ����ͼ�εĺ�̨�����̣߳�ִ��ͼ����� <br>
	// * {@link #doInbackgroud()};
	// */
	// public void openBackgroundThread() {
	// this.isDoInbackground = true;
	// }

	/**
	 * �򿪱���ü�<br>
	 * ���ǰ��չ涨��ʽ���Ƶġ������ᱻ����<br>
	 * 
	 * @param isOpen
	 */
	public void openBackCut(boolean isOpen) {
		this.isOpenBackCut = isOpen;
	}

	/**
	 * ��˳ʱ����ƣ�Ĭ��Ϊ��ʱ��
	 * 
	 * @param isOpen
	 */
	public void openClockwiseWind(boolean isOpen) {
		this.isClockwiseWind = isOpen;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		// SixPointedStar[] ha = new SixPointedStar[6];// ����������

		public void onDrawFrame(GL10 gl) {
			// ��������
			sceneSetting();
			// �Ƿ�򿪱���ü�
			if (isOpenBackCut) {
				GLES20.glEnable(GLES20.GL_CULL_FACE);
			} else {
				GLES20.glDisable(GLES20.GL_CULL_FACE);
			}

			// �Ƿ��˳ʱ�����,ϵͳĬ������ʱ�����
			if (isClockwiseWind) {
				GLES20.glFrontFace(GLES20.GL_CW);
			} else {
				GLES20.glFrontFace(GLES20.GL_CCW);
			}

			// �����Ȼ�������ɫ����
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
					| GLES20.GL_COLOR_BUFFER_BIT);

			// ���ÿͻ��˻�ͼ�ӿ�
			onDraw(jqGraphics);
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {

			// ��ʼ���任����
			JqMatrixState.setInitStack();

			// �����Ӵ���С��λ��
			int[] tempScreen = setViewport(width, height);
			GLES20.glViewport(tempScreen[0], tempScreen[1], tempScreen[2],
					tempScreen[3]);
			// ����GLSurfaceView�Ŀ�߱�
			float ratio = (float) tempScreen[2] / tempScreen[3];
			jqProjection = setProjectionParams(ratio);

			if (jqProjection[0] > 0) {
				// ͸��ͶӰ
				JqMatrixState.setProjectOutcrossing(jqProjection[1],
						jqProjection[2], jqProjection[3], jqProjection[4],
						jqProjection[5], jqProjection[6]);
			} else {
				// ƽ��ͶӰ
				JqMatrixState.setProjectOrthogonal(jqProjection[1],
						jqProjection[2], jqProjection[3], jqProjection[4],
						jqProjection[5], jqProjection[6]);
			}

		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// ������Ļ����ɫRGBA
			GLES20.glClearColor(jqDrawBackground[0], jqDrawBackground[1],
					jqDrawBackground[2], jqDrawBackground[3]);

			// ���������
			jqCamear = setCamera();
			// ���ô˷������������9����λ�þ���
			JqMatrixState.setCameraMatrix(jqCamear[0], jqCamear[1],
					jqCamear[2], jqCamear[3], jqCamear[4], jqCamear[5],
					jqCamear[6], jqCamear[7], jqCamear[8]);

			jqGraphics = onCreateView();

			// ��ʼ��ͼ��
			for (int i = 0; i < jqGraphics.length; i++) {
				if (jqGraphics[i] != null) {
					jqGraphics[i].initVertexData();
				}
			}
			// ����ȼ��
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);

			// ��������
			sceneSetting();

			// ��ʼ����������
			initTexture();

			// // �򿪱����߳�
			// if (isDoInbackground) {
			// new Thread() {
			// @Override
			// public void run() {
			//
			// for (int i = 0; i < jqGraphics.length; i++) {
			// // TODO �������һ��view�� doinbackgroud ������
			// // ����ѭ������ô�ͻῨ����Ӧ�ķ������� �����޷����������޸Ĵ��ַ�ʽ
			// jqGraphics[i].doInbackground();
			// }
			// };
			// }.start();
			// }
		}
	}
}
