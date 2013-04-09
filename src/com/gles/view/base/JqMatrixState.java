package com.gles.view.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.Matrix;

/**
 * 存储矩阵状态
 * 
 * @author qianjunping
 * 
 */
public final class JqMatrixState {

	/**
	 * 最后起作用的总变换矩阵。<br>
	 * 已经加入照相机，投影等矩阵信息<br>
	 */
	private static float[] jqMVPMatrixWithCameraPro = new float[16];

	/**
	 * 用来做平移，旋转，缩放等操作的矩阵。(3D)<br>
	 * 使用前需调用 {@link #setInitStack()} 进行初始化<br>
	 */
	private static float[] jqChangeMatrix;

	/**
	 * 4x4矩阵 投影用,
	 * <p>
	 * 如正交(平行投影)，透视投影使用的矩阵
	 */
	private static float[] jqProjectionMatrix = new float[16];

	/**
	 * 摄像机属性设置矩阵
	 * <p>
	 * 1.摄像机位置
	 * <p>
	 * 2.目标点
	 * <p>
	 * 3.摄像机 up 方向 (每组 x,y,z 三个坐标点) 摄像机位置朝向9参数矩阵
	 */
	private static float[] jqCameraMatrix = new float[16];

	/**
	 * 光源位置：x,y,z坐标
	 */

	private static float[] lightLocation = new float[] { -2, 0, 4 };

	/**
	 * 摄像机位置：x,y,z坐标
	 */

	private static float[] cameraLocation = new float[3];

	/**
	 * 保护变换矩阵的栈<br>
	 * 因为矩阵的变换，是将坐标系的平移，如果频繁操作会浪费很大的效率
	 */
	private static float[][] mStack = new float[10][16];

	/**
	 * 栈指针
	 */
	private static int stackTop = -1;

	/**
	 * 初始化变换矩阵，并且赋值给当前的变换矩阵
	 */
	public static void setInitStack() {
		jqChangeMatrix = new float[16];
		Matrix.setRotateM(jqChangeMatrix, 0, 0, 1, 0, 0);
	}

	/**
	 * 获得变换矩阵
	 * 
	 * @return
	 */
	static float[] getChangeMatrix() {
		if (jqChangeMatrix == null)
			throw new RuntimeException("In so doing before setInitStack()!");
		return jqChangeMatrix;
	}

	/**
	 * 保护变换矩阵<br>
	 * 在执行变换前，先将初始的矩阵值压入栈
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
	 * 恢复变换矩阵<br>
	 * 在变换完成后，将原先压入栈的初始数据，再返还给当前矩阵
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
	 * 设置光源的位置
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
	 * 获取光源位置数据缓冲
	 * 
	 * @return
	 */
	static FloatBuffer getLightLocationBuffer() {
		FloatBuffer tempFloatBuffer = null;
		// 每个float 占有4 个字节
		ByteBuffer vbb = ByteBuffer.allocateDirect(lightLocation.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		tempFloatBuffer = vbb.asFloatBuffer();
		tempFloatBuffer.put(lightLocation);
		tempFloatBuffer.position(0);
		return tempFloatBuffer;
	}

	/**
	 * 设置沿x、y、z轴移动
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
	 * 设置在x、y、z三个分量上面的缩放
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
	 * 设置绕 xyz轴转动, xyz 坐标会组成一个点,此点与坐标系远点会组成一个向量,转动的角度是围绕该向量进行的<br>
	 * 
	 * @param angle
	 *            角度
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
	 * 设置摄像机矩阵
	 * 
	 * @param cx
	 *            摄像机位置x
	 * @param cy
	 *            摄像机位置y
	 * @param cz
	 *            摄像机位置z
	 * @param tx
	 *            摄像机目标点x
	 * @param ty
	 *            摄像机目标点y
	 * @param tz
	 *            摄像机目标点z
	 * @param upx
	 *            摄像机UP向量X分量
	 * @param upy
	 *            摄像机UP向量Y分量
	 * @param upz
	 *            摄像机UP向量Z分量
	 */
	static void setCameraMatrix(float cx, float cy, float cz, float tx,
			float ty, float tz, float upx, float upy, float upz) {
		// 给摄像机位置数组赋值
		cameraLocation[0] = cx;
		cameraLocation[1] = cy;
		cameraLocation[2] = cz;

		Matrix.setLookAtM(jqCameraMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy,
				upz);
	}

	/**
	 * 获取摄像机位置数据缓冲
	 * 
	 * @return
	 */
	static FloatBuffer getCameraLocationFloatBuffer() {

		FloatBuffer tempFloatBuffer = null;
		// 每个float 占有4 个字节
		ByteBuffer vbb = ByteBuffer.allocateDirect(cameraLocation.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		tempFloatBuffer = vbb.asFloatBuffer();
		tempFloatBuffer.put(cameraLocation);
		tempFloatBuffer.position(0);
		return tempFloatBuffer;
	}

	/**
	 * 获取摄像机位置数组
	 */

	static float[] getCameraLocation() {
		return cameraLocation;
	}

	/**
	 * 设置正交投影参数
	 */
	static void setProjectOrthogonal(float left, // near面的left
			float right, // near面的right
			float bottom, // near面的bottom
			float top, // near面的top
			float near, // near面距离
			float far // far面距离
	) {
		Matrix.orthoM(jqProjectionMatrix, 0, left, right, bottom, top, near,
				far);
	}

	/**
	 * 设置远交投影参数
	 */
	static void setProjectOutcrossing(float left, // near面的left
			float right, // near面的right
			float bottom, // near面的bottom
			float top, // near面的top
			float near, // near面距离
			float far // far面距离
	) {
		Matrix.frustumM(jqProjectionMatrix, 0, left, right, bottom, top, near,
				far);
	}

	/**
	 * <p>
	 * 经过平移、旋转、缩放、并且包含照相机、投影等处理后的总矩阵
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
