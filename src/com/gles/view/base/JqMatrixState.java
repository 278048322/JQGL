package com.gles.view.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.Matrix;

/**
 * �洢����״̬
 * 
 * @author qianjunping
 * 
 */
public final class JqMatrixState {

	/**
	 * ��������õ��ܱ任����<br>
	 * �Ѿ������������ͶӰ�Ⱦ�����Ϣ<br>
	 */
	private static float[] jqMVPMatrixWithCameraPro = new float[16];

	/**
	 * ������ƽ�ƣ���ת�����ŵȲ����ľ���(3D)<br>
	 * ʹ��ǰ����� {@link #setInitStack()} ���г�ʼ��<br>
	 */
	private static float[] jqChangeMatrix;

	/**
	 * 4x4���� ͶӰ��,
	 * <p>
	 * ������(ƽ��ͶӰ)��͸��ͶӰʹ�õľ���
	 */
	private static float[] jqProjectionMatrix = new float[16];

	/**
	 * ������������þ���
	 * <p>
	 * 1.�����λ��
	 * <p>
	 * 2.Ŀ���
	 * <p>
	 * 3.����� up ���� (ÿ�� x,y,z ���������) �����λ�ó���9��������
	 */
	private static float[] jqCameraMatrix = new float[16];

	/**
	 * ��Դλ�ã�x,y,z����
	 */

	private static float[] lightLocation = new float[] { -2, 0, 4 };

	/**
	 * �����λ�ã�x,y,z����
	 */

	private static float[] cameraLocation = new float[3];

	/**
	 * �����任�����ջ<br>
	 * ��Ϊ����ı任���ǽ�����ϵ��ƽ�ƣ����Ƶ���������˷Ѻܴ��Ч��
	 */
	private static float[][] mStack = new float[10][16];

	/**
	 * ջָ��
	 */
	private static int stackTop = -1;

	/**
	 * ��ʼ���任���󣬲��Ҹ�ֵ����ǰ�ı任����
	 */
	public static void setInitStack() {
		jqChangeMatrix = new float[16];
		Matrix.setRotateM(jqChangeMatrix, 0, 0, 1, 0, 0);
	}

	/**
	 * ��ñ任����
	 * 
	 * @return
	 */
	static float[] getChangeMatrix() {
		if (jqChangeMatrix == null)
			throw new RuntimeException("In so doing before setInitStack()!");
		return jqChangeMatrix;
	}

	/**
	 * �����任����<br>
	 * ��ִ�б任ǰ���Ƚ���ʼ�ľ���ֵѹ��ջ
	 */
	static void saveMatrix() {

		if (jqChangeMatrix == null)
			throw new RuntimeException("In so doing before setInitStack()!");
		stackTop++;
		for (int i = 0; i < 16; i++) {
			mStack[stackTop][i] = jqChangeMatrix[i];
		}
	}

	/**
	 * �ָ��任����<br>
	 * �ڱ任��ɺ󣬽�ԭ��ѹ��ջ�ĳ�ʼ���ݣ��ٷ�������ǰ����
	 */
	static void restoreMatrix() {

		if (jqChangeMatrix == null)
			throw new RuntimeException("In so doing before setInitStack()!");

		for (int i = 0; i < 16; i++) {
			jqChangeMatrix[i] = mStack[stackTop][i];
		}
		stackTop--;
	}

	/**
	 * ���ù�Դ��λ��
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	static void setLightLocation(float x, float y, float z) {

		lightLocation[0] = x;
		lightLocation[1] = y;
		lightLocation[2] = z;

	}

	/**
	 * ��ȡ��Դλ�����ݻ���
	 * 
	 * @return
	 */
	static FloatBuffer getLightLocationBuffer() {
		FloatBuffer tempFloatBuffer = null;
		// ÿ��float ռ��4 ���ֽ�
		ByteBuffer vbb = ByteBuffer.allocateDirect(lightLocation.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		tempFloatBuffer = vbb.asFloatBuffer();
		tempFloatBuffer.put(lightLocation);
		tempFloatBuffer.position(0);
		return tempFloatBuffer;
	}

	/**
	 * ������x��y��z���ƶ�
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	static void translate(float x, float y, float z) {
		if (jqChangeMatrix == null)
			throw new RuntimeException("In so doing before setInitStack()!");
		Matrix.translateM(jqChangeMatrix, 0, x, y, z);
	}

	/**
	 * ������x��y��z�����������������
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	static void scale(float x, float y, float z) {
		if (jqChangeMatrix == null)
			throw new RuntimeException("In so doing before setInitStack()!");
		Matrix.scaleM(jqChangeMatrix, 0, x, y, z);
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
	static void rotate(float angle, float x, float y, float z) {
		if (jqChangeMatrix == null)
			throw new RuntimeException("In so doing before setInitStack()!");
		if (angle == 0 && x == 0 && y == 0 && z == 0) {
			return;
		}
		Matrix.rotateM(jqChangeMatrix, 0, angle, x, y, z);
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
	static void setCameraMatrix(float cx, float cy, float cz, float tx,
			float ty, float tz, float upx, float upy, float upz) {
		// �������λ�����鸳ֵ
		cameraLocation[0] = cx;
		cameraLocation[1] = cy;
		cameraLocation[2] = cz;

		Matrix.setLookAtM(jqCameraMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy,
				upz);
	}

	/**
	 * ��ȡ�����λ�����ݻ���
	 * 
	 * @return
	 */
	static FloatBuffer getCameraLocationFloatBuffer() {

		FloatBuffer tempFloatBuffer = null;
		// ÿ��float ռ��4 ���ֽ�
		ByteBuffer vbb = ByteBuffer.allocateDirect(cameraLocation.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		tempFloatBuffer = vbb.asFloatBuffer();
		tempFloatBuffer.put(cameraLocation);
		tempFloatBuffer.position(0);
		return tempFloatBuffer;
	}

	/**
	 * ��ȡ�����λ������
	 */

	static float[] getCameraLocation() {
		return cameraLocation;
	}

	/**
	 * ��������ͶӰ����
	 */
	static void setProjectOrthogonal(float left, // near���left
			float right, // near���right
			float bottom, // near���bottom
			float top, // near���top
			float near, // near�����
			float far // far�����
	) {
		Matrix.orthoM(jqProjectionMatrix, 0, left, right, bottom, top, near,
				far);
	}

	/**
	 * ����Զ��ͶӰ����
	 */
	static void setProjectOutcrossing(float left, // near���left
			float right, // near���right
			float bottom, // near���bottom
			float top, // near���top
			float near, // near�����
			float far // far�����
	) {
		Matrix.frustumM(jqProjectionMatrix, 0, left, right, bottom, top, near,
				far);
	}

	/**
	 * <p>
	 * ����ƽ�ơ���ת�����š����Ұ����������ͶӰ�ȴ������ܾ���
	 * <p>
	 * 
	 * @return jqMVPMatrixWithCameraPro[]
	 */
	static float[] getFinalMatrix() {
		Matrix.multiplyMM(jqMVPMatrixWithCameraPro, 0, jqCameraMatrix, 0,
				jqChangeMatrix, 0);
		Matrix.multiplyMM(jqMVPMatrixWithCameraPro, 0, jqProjectionMatrix, 0,
				jqMVPMatrixWithCameraPro, 0);
		return jqMVPMatrixWithCameraPro;
	}
}
