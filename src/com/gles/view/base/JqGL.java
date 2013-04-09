package com.gles.view.base;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

/**
 * JqGL ��� API
 * 
 * @author qianjunping
 * 
 */
public final class JqGL {

	/**
	 * ��λ����
	 */
	public static final float UNIT_SIZE = 1f;

	/**
	 * �򿪱���ü�<br>
	 * ���ǰ��չ涨��ʽ���Ƶġ������ᱻ����<br>
	 * 
	 * @param isOpen
	 *            default false
	 */
	public void openBackCut(boolean isOpen) {
		if (isOpen) {
			GLES20.glEnable(GLES20.GL_CULL_FACE);
		} else {
			GLES20.glDisable(GLES20.GL_CULL_FACE);
		}
	}

	/**
	 * ��˳ʱ����ƣ�Ĭ��Ϊ��ʱ��
	 * 
	 * @param isOpen
	 *            default false
	 */
	public void openClockwiseWind(boolean isOpen) {
		// �Ƿ��˳ʱ�����,ϵͳĬ������ʱ�����
		if (isOpen) {
			GLES20.glFrontFace(GLES20.GL_CW);
		} else {
			GLES20.glFrontFace(GLES20.GL_CCW);
		}
	}

	/**
	 * �����任����<br>
	 * ��ִ�б任ǰ���Ƚ���ʼ�ľ���ֵѹ��ջ<br>
	 */
	public static void saveSceneMatrix() {
		JqMatrixState.saveMatrix();
	}

	/**
	 * �ָ��任����<br>
	 * �ڱ任��ɺ󣬽�ԭ��ѹ��ջ�ĳ�ʼ���ݣ��ٷ�������ǰ����<br>
	 */
	public static void restoreSceneMatrix() {
		JqMatrixState.restoreMatrix();
	}

	/**
	 * ������x��y��z���ƶ��ľ���
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void translate(float x, float y, float z) {
		JqMatrixState.translate(x, y, z);
	}

	/**
	 * ������x��y��z�����������������
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void scale(float x, float y, float z) {
		JqMatrixState.scale(x, y, z);
	}

	/**
	 * ������ xyz��ת��, xyz ��������һ����,�˵�������ϵԶ������һ������,ת���ĽǶ���Χ�Ƹ��������е�<br>
	 * 
	 * @param angle
	 *            �Ƕ�
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void rotate(float angle, float x, float y, float z) {
		JqMatrixState.rotate(angle, x, y, z);
	}

	/**
	 * �����ӿ�<br>
	 * �����е�����ͶӰ����ƽ������ջ�ӳ�䵽��ʾ���ϵ��ӿ��С�<br>
	 * �ӿ�Ҳ������ʾ����ָ���ľ�������<br>
	 * <p>
	 * �������� 4 �������� x��y��width��height��˳��Ϊ���ϵ���<br>
	 * 
	 * @param x
	 *            Ϊ�ӿھ�����Ļ���Ե���� <br>
	 * @param y
	 *            Ϊ�ӿھ�����Ļ�ϱ�Ե����<br>
	 * @param width
	 *            Ϊ�ӿڵĿ�� ,ͨ������ GLSufaceView �Ŀ�ȣ�ʹ�� JqScene.getViewWidth() ���Ի��<br>
	 * @param height
	 *            Ϊ�ӿڵĸ߶� ,ͨ������ GLSufaceView �ĸ߶ȣ�ʹ�� JqScene.getViewHeight() ���Ի��<br>
	 * 
	 */
	public static void setViewport(int x, int y, int width, int height) {

		GLES20.glViewport(x, y, width, height);

	}

	/**
	 * �������������
	 * 
	 * @param cx
	 *            �����λ��x
	 * @param cy
	 *            �����λ��y
	 * @param cz
	 *            �����λ��z
	 * @param tx
	 *            �����Ŀ���x
	 * @param ty
	 *            �����Ŀ���y
	 * @param tz
	 *            �����Ŀ���z
	 * @param upx
	 *            �����UP����X����
	 * @param upy
	 *            �����UP����Y����
	 * @param upz
	 *            �����UP����Z����
	 */
	public static final void setCamer(float[] cameara) {

		if (cameara.length != 9) {
			throw new RuntimeException(JqGL.EXCEPTION_PARAMETER_NUMBER_OVERSTEP);
		}

		JqMatrixState.setCameraMatrix(cameara[0], cameara[1], cameara[2],
				cameara[3], cameara[4], cameara[5], cameara[6], cameara[7],
				cameara[8]);
	}

	/**
	 * ����ͶӰ��ʽ <br>
	 * 
	 * @param orthogonalOrOutcrossing
	 *            ͶӰ��ʽ��֧��ƽ�У�������ͶӰ�Լ�͸�ӣ�Զ����ͶӰ ,���� 0 Ϊ ����ͶӰ��1 Ϊ͸��ͶӰ<br>
	 *            <p>
	 *            ע��㣺<br>
	 *            1.ֻ�д��� ��ƽ����Զƽ��֮�������ŻᱻͶӰ����ƽ�����棬Ȼ����ӳ�䵽��Ļ�ϡ� <br>
	 *            2.������ͶӰ�����ʱ��<br>
	 *            �����ƽ��ľ�����ָ--����������ƽ��ľ��룬���������������ľ����������������ƽ��ľ���ʱ��
	 *            ������ܱ�ͶӰ��ƽ������ <br>
	 *            3.ֻ��͸��ͶӰʱ�������������Զ���ܸı��������ʾ��С������ͶӰ�����������Զ����Ĵ�Сʼ�ղ���<br>
	 * 
	 *            float left, // near���left <br>
	 *            float right, // near���right<br>
	 *            float bottom, // near���bottom <br>
	 *            float top, // near���top<br>
	 *            float near, // near����� <br>
	 *            float far // far�����<br>
	 * 
	 * @param viewAspectRatio
	 *            ��Ļ�Ŀ�߱�,���� JqScene.getViewAspectRatio() ���
	 * 
	 * @param near
	 *            ���� near��ľ���
	 * 
	 * @param far
	 *            ���� far��ľ���
	 * @return
	 */
	public static final void setProjectionParams(int orthogonalOrOutcrossing,
			float viewAspectRatio, int near, int far) {

		if (orthogonalOrOutcrossing > 0) {
			// ͸��ͶӰ
			JqMatrixState.setProjectOutcrossing(-viewAspectRatio,
					viewAspectRatio, -1, 1, near, far);
		} else {
			// ƽ��ͶӰ
			JqMatrixState.setProjectOrthogonal(-viewAspectRatio,
					viewAspectRatio, -1, 1, near, far);
		}

	}

	/**
	 * <p>
	 * ����ƽ�ơ���ת�����ŵȲ����������������ͶӰ�ȴ������ܾ���
	 * <p>
	 * 
	 * @return jqMVPMatrixWithCameraPro[]
	 */
	public static float[] getFinalMatrix() {
		return JqMatrixState.getFinalMatrix();
	}

	/**
	 * ���ù�Դ��ǰ��λ��
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void setLightLocation(float x, float y, float z) {
		JqMatrixState.setLightLocation(x, y, z);
	}

	/**
	 * ��ȡ��Դλ�û�����
	 * 
	 * @return FloatBuffer
	 */
	public static FloatBuffer getLightLocationBuffer() {
		return JqMatrixState.getLightLocationBuffer();
	}

	/**
	 * ��ȡ�任����,�����Խ��� ��ת �� ���� ��ƽ�ƵȲ����ľ���
	 * 
	 * @return
	 */
	public static float[] getChangeMatrix() {
		return JqMatrixState.getChangeMatrix();
	}

	/**
	 * ��ȡ�����λ������
	 */
	public static float[] getCameraLocation() {
		return JqMatrixState.getCameraLocation();
	}

	/**
	 * ��ȡ�����λ�����ݻ���
	 * 
	 * @return
	 */
	public static FloatBuffer getCameraLocationFloatBuffer() {
		return JqMatrixState.getCameraLocationFloatBuffer();
	}

	// constants
	public static final String EXCEPTION_VERTEXTDATA_NULL = "VertexData is null";
	public static final String EXCEPTION_PARAMETER_NUMBER_OVERSTEP = "Parameter number is wrong!";
}
