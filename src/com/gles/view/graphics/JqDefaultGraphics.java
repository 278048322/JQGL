package com.gles.view.graphics;

import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqGLContents;

/**
 * �ѹ�ʱ
 * 
 * @author qianjunping
 * 
 */
@Deprecated
public abstract class JqDefaultGraphics extends JqGraphics {

	/**
	 * �ܱ任��������id (uniform)
	 */
	protected int jqShaderMVPMatrixId;

	/**
	 * ����λ����������id
	 */
	protected int jqShaderVertexPositionAttrId;

	public JqDefaultGraphics(GLSurfaceView mv, String vertexPath,
			String fragmentPath) {
		super(mv, vertexPath, fragmentPath);
	}

	/**
	 * // ��ȡ�����ж���λ����������id<br>
	 * jqShaderVertexPositionAttrId = GLES20.glGetAttribLocation(
	 * jqShaderProgramId, "aPosition"); <br>
	 * // ��ȡ�����ж�����ɫ��������id <br>
	 * jqShaderVertexColorAttrId = GLES20.glGetAttribLocation(
	 * jqShaderProgramId, "aColor"); <br>
	 * // ��ȡ�������ܱ任��������id <br>
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
