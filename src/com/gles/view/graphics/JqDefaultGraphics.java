package com.gles.view.graphics;

import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqGLContents;

/**
 * 已过时
 * 
 * @author qianjunping
 * 
 */
@Deprecated
public abstract class JqDefaultGraphics extends JqGraphics {

	/**
	 * 总变换矩阵引用id (uniform)
	 */
	protected int jqShaderMVPMatrixId;

	/**
	 * 顶点位置属性引用id
	 */
	protected int jqShaderVertexPositionAttrId;

	public JqDefaultGraphics(GLSurfaceView mv, String vertexPath,
			String fragmentPath) {
		super(mv, vertexPath, fragmentPath);
	}

	/**
	 * // 获取程序中顶点位置属性引用id<br>
	 * jqShaderVertexPositionAttrId = GLES20.glGetAttribLocation(
	 * jqShaderProgramId, "aPosition"); <br>
	 * // 获取程序中顶点颜色属性引用id <br>
	 * jqShaderVertexColorAttrId = GLES20.glGetAttribLocation(
	 * jqShaderProgramId, "aColor"); <br>
	 * // 获取程序中总变换矩阵引用id <br>
	 * jqShaderMVPMatrixId = GLES20.glGetUniformLocation(jqShaderProgramId,
	 * "uMVPMatrix");<br>
	 */
	@Override
	public void initShaderMember() {

		jqShaderMVPMatrixId = getShaderMannger().getShaderUniformId(
				JqGLContents.SHADER_DEFAULT_MVPMATRIX);

		jqShaderVertexPositionAttrId = getShaderMannger().getShaderAttribId(
				JqGLContents.SHADER_DEFAULT_VERTEX_POSITION);

	}

}
