package com.gles.view.shader;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

/**
 * 
 * 着色器管理器
 * 
 * @author qianjunping
 * 
 */
public class JqShaderMannger {

	// =====> 从着色器中获取内容
	/**
	 * 获取着色器属性变量id
	 * 
	 * @return int
	 */
	public int getShaderAttribId(String shaderAttriMeName) {
		return GLES20.glGetAttribLocation(jqShaderProgramId, shaderAttriMeName);
	}

	/**
	 * 获取着色器通用(uniform)变量id
	 * 
	 * @return int
	 */
	public int getShaderUniformId(String shaderUniformMeName) {
		return GLES20.glGetUniformLocation(jqShaderProgramId,
				shaderUniformMeName);
	}

	// ======== end

	// ======> 给着色参数赋值
	/**
	 * 给顶点着色器-属性-参数赋值,不规格化<br>
	 * 
	 * 只针对，每组3个数据，每个数据都为 float型，占有4个字节<br>
	 * 
	 * @param shaderMeId
	 *            属性变量id
	 * @param buffer
	 *            缓冲数据
	 */
	@Deprecated
	public void setShaderAttrib3fv(int shaderMeId, FloatBuffer buffer) {
		GLES20.glVertexAttribPointer(shaderMeId, 3, GLES20.GL_FLOAT, false,
				3 * 4, buffer);
	}

	/**
	 * 给顶点着色器-属性-参数赋值,不规格化<br>
	 * 
	 * 只针对，每组4个数据，每个数据都为 float型，占有4个字节<br>
	 * 
	 * @param shaderMeId
	 *            属性变量id
	 * @param buffer
	 *            缓冲数据
	 */
	@Deprecated
	public void setShaderAttrib4fv(int shaderMeId, FloatBuffer buffer) {
		GLES20.glVertexAttribPointer(shaderMeId, 4, GLES20.GL_FLOAT, false,
				4 * 4, buffer);
	}

	/**
	 * 给顶点着色器-uniform-参数赋值,不规格化<br>
	 * 
	 * 只针对，每组4个数据，每个数据都为 float型，占有4个字节<br>
	 * 
	 * @param shaderMeId
	 *            属性变量id
	 * @param matrix
	 *            矩阵
	 */
	@Deprecated
	public void setShaderUniform4fv(int shaderUniformId, float[] matrix) {
		GLES20.glUniformMatrix4fv(shaderUniformId, 1, false, matrix, 0);
	}

	/**
	 * 给顶点着色器-uniform-参数赋值,不规格化<br>
	 * 
	 * 只针对，每组3个数据，每个数据都为 float型，占有4个字节<br>
	 * 
	 * @param shaderMeId
	 *            属性变量id
	 * @param matrix
	 *            矩阵
	 */
	@Deprecated
	public void setShaderUniform3fv(int shaderUniformId, int count,
			FloatBuffer date) {
		GLES20.glUniform3fv(shaderUniformId, 1, date);
	}

	/**
	 * 给顶点着色器-uniform-参数赋值,不规格化<br>
	 * 
	 * 只针对，1个数据，数据为 float型，占有4个字节<br>
	 * 
	 * @param shaderUniformId
	 *            属性变量id
	 * @param date
	 *            float
	 */
	@Deprecated
	public void setShaderUniform1f(int shaderUniformId, float date) {
		GLES20.glUniform1f(shaderUniformId, date);
	}

	// ======= end

	/**
	 * 获取着色器程序id
	 * 
	 * @return jqShaderProgramId
	 */
	public int getShaderProgramId() {
		return jqShaderProgramId;
	}

	/**
	 * 设置着色器程序id
	 */
	public void setShaderProgramId(int id) {
		this.jqShaderProgramId = id;
	}

	public static JqShaderMannger getJqShaderMannger(long key) {
		if (key != manngerKey) {
			throw new RuntimeException(
					"you key is worng! you can't getJqShaderMannger,please use this.getShaderMannger()");
		}
		return new JqShaderMannger();
	}

	private JqShaderMannger() {

	}

	private static long manngerKey = 291291939234429l;
	/**
	 * 自定义渲染管线程序id
	 */
	private int jqShaderProgramId;

}
