package com.gles.view.base;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

/**
 * JqGL 框架 API
 * 
 * @author qianjunping
 * 
 */
public final class JqGL {

	/**
	 * 单位长度
	 */
	public static final float UNIT_SIZE = 1f;

	/**
	 * 打开背面裁剪<br>
	 * 不是按照规定方式卷绕的△将不会被绘制<br>
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
	 * 打开顺时针卷绕，默认为逆时针
	 * 
	 * @param isOpen
	 *            default false
	 */
	public void openClockwiseWind(boolean isOpen) {
		// 是否打开顺时针卷绕,系统默认是逆时针卷绕
		if (isOpen) {
			GLES20.glFrontFace(GLES20.GL_CW);
		} else {
			GLES20.glFrontFace(GLES20.GL_CCW);
		}
	}

	/**
	 * 保护变换矩阵<br>
	 * 在执行变换前，先将初始的矩阵值压入栈<br>
	 */
	public static void saveSceneMatrix() {
		JqMatrixState.saveMatrix();
	}

	/**
	 * 恢复变换矩阵<br>
	 * 在变换完成后，将原先压入栈的初始数据，再返还给当前矩阵<br>
	 */
	public static void restoreSceneMatrix() {
		JqMatrixState.restoreMatrix();
	}

	/**
	 * 设置沿x、y、z轴移动的距离
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void translate(float x, float y, float z) {
		JqMatrixState.translate(x, y, z);
	}

	/**
	 * 设置在x、y、z三个分量上面的缩放
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void scale(float x, float y, float z) {
		JqMatrixState.scale(x, y, z);
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
	public static void rotate(float angle, float x, float y, float z) {
		JqMatrixState.rotate(angle, x, y, z);
	}

	/**
	 * 设置视口<br>
	 * 场景中的物体投影到近平面后，最终会映射到显示屏上的视口中。<br>
	 * 视口也就是显示屏上指定的矩形区域。<br>
	 * <p>
	 * 需求设置 4 个参数， x、y、width、height，顺序为从上到下<br>
	 * 
	 * @param x
	 *            为视口距离屏幕左边缘距离 <br>
	 * @param y
	 *            为视口距离屏幕上边缘距离<br>
	 * @param width
	 *            为视口的宽度 ,通常采用 GLSufaceView 的宽度，使用 JqScene.getViewWidth() 可以获得<br>
	 * @param height
	 *            为视口的高度 ,通常采用 GLSufaceView 的高度，使用 JqScene.getViewHeight() 可以获得<br>
	 * 
	 */
	public static void setViewport(int x, int y, int width, int height) {

		GLES20.glViewport(x, y, width, height);

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
	public static final void setCamer(float[] cameara) {

		if (cameara.length != 9) {
			throw new RuntimeException(JqGL.EXCEPTION_PARAMETER_NUMBER_OVERSTEP);
		}

		JqMatrixState.setCameraMatrix(cameara[0], cameara[1], cameara[2],
				cameara[3], cameara[4], cameara[5], cameara[6], cameara[7],
				cameara[8]);
	}

	/**
	 * 设置投影方式 <br>
	 * 
	 * @param orthogonalOrOutcrossing
	 *            投影方式，支持平行（正交）投影以及透视（远交）投影 ,设置 0 为 正交投影，1 为透视投影<br>
	 *            <p>
	 *            注意点：<br>
	 *            1.只有处在 近平面与远平面之间的物体才会被投影到近平面上面，然后在映射到屏幕上。 <br>
	 *            2.在设置投影矩阵的时候：<br>
	 *            距离近平面的距离是指--摄像机距离近平面的距离，当摄像机距离物体的距离大于摄像机距离近平面的距离时，
	 *            物体才能被投影到平面上面 <br>
	 *            3.只有透视投影时候，摄像机拉近拉远才能改变物体的显示大小，正交投影摄像机拉近拉远物体的大小始终不变<br>
	 * 
	 *            float left, // near面的left <br>
	 *            float right, // near面的right<br>
	 *            float bottom, // near面的bottom <br>
	 *            float top, // near面的top<br>
	 *            float near, // near面距离 <br>
	 *            float far // far面距离<br>
	 * 
	 * @param viewAspectRatio
	 *            屏幕的宽高比,可有 JqScene.getViewAspectRatio() 获得
	 * 
	 * @param near
	 *            距离 near面的距离
	 * 
	 * @param far
	 *            距离 far面的距离
	 * @return
	 */
	public static final void setProjectionParams(int orthogonalOrOutcrossing,
			float viewAspectRatio, int near, int far) {

		if (orthogonalOrOutcrossing > 0) {
			// 透视投影
			JqMatrixState.setProjectOutcrossing(-viewAspectRatio,
					viewAspectRatio, -1, 1, near, far);
		} else {
			// 平行投影
			JqMatrixState.setProjectOrthogonal(-viewAspectRatio,
					viewAspectRatio, -1, 1, near, far);
		}

	}

	/**
	 * <p>
	 * 经过平移、旋转、缩放等操作并包含照相机、投影等处理后的总矩阵
	 * <p>
	 * 
	 * @return jqMVPMatrixWithCameraPro[]
	 */
	public static float[] getFinalMatrix() {
		return JqMatrixState.getFinalMatrix();
	}

	/**
	 * 设置光源当前的位置
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void setLightLocation(float x, float y, float z) {
		JqMatrixState.setLightLocation(x, y, z);
	}

	/**
	 * 获取光源位置缓冲流
	 * 
	 * @return FloatBuffer
	 */
	public static FloatBuffer getLightLocationBuffer() {
		return JqMatrixState.getLightLocationBuffer();
	}

	/**
	 * 获取变换矩阵,即用以进行 旋转 ， 缩放 ，平移等操作的矩阵
	 * 
	 * @return
	 */
	public static float[] getChangeMatrix() {
		return JqMatrixState.getChangeMatrix();
	}

	/**
	 * 获取摄像机位置数组
	 */
	public static float[] getCameraLocation() {
		return JqMatrixState.getCameraLocation();
	}

	/**
	 * 获取摄像机位置数据缓冲
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
