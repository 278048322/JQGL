package com.gles.view.shader;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

/**
 * 
 * ��ɫ��������
 * 
 * @author qianjunping
 * 
 */
public class JqShaderMannger {

	// =====> ����ɫ���л�ȡ����
	/**
	 * ��ȡ��ɫ�����Ա���id
	 * 
	 * @return int
	 */
	public int getShaderAttribId(String shaderAttriMeName) {
		return GLES20.glGetAttribLocation(jqShaderProgramId, shaderAttriMeName);
	}

	/**
	 * ��ȡ��ɫ��ͨ��(uniform)����id
	 * 
	 * @return int
	 */
	public int getShaderUniformId(String shaderUniformMeName) {
		return GLES20.glGetUniformLocation(jqShaderProgramId,
				shaderUniformMeName);
	}

	// ======== end

	// ======> ����ɫ������ֵ
	/**
	 * ��������ɫ��-����-������ֵ,�����<br>
	 * 
	 * ֻ��ԣ�ÿ��3�����ݣ�ÿ�����ݶ�Ϊ float�ͣ�ռ��4���ֽ�<br>
	 * 
	 * @param shaderMeId
	 *            ���Ա���id
	 * @param buffer
	 *            ��������
	 */
	@Deprecated
	public void setShaderAttrib3fv(int shaderMeId, FloatBuffer buffer) {
		GLES20.glVertexAttribPointer(shaderMeId, 3, GLES20.GL_FLOAT, false,
				3 * 4, buffer);
	}

	/**
	 * ��������ɫ��-����-������ֵ,�����<br>
	 * 
	 * ֻ��ԣ�ÿ��4�����ݣ�ÿ�����ݶ�Ϊ float�ͣ�ռ��4���ֽ�<br>
	 * 
	 * @param shaderMeId
	 *            ���Ա���id
	 * @param buffer
	 *            ��������
	 */
	@Deprecated
	public void setShaderAttrib4fv(int shaderMeId, FloatBuffer buffer) {
		GLES20.glVertexAttribPointer(shaderMeId, 4, GLES20.GL_FLOAT, false,
				4 * 4, buffer);
	}

	/**
	 * ��������ɫ��-uniform-������ֵ,�����<br>
	 * 
	 * ֻ��ԣ�ÿ��4�����ݣ�ÿ�����ݶ�Ϊ float�ͣ�ռ��4���ֽ�<br>
	 * 
	 * @param shaderMeId
	 *            ���Ա���id
	 * @param matrix
	 *            ����
	 */
	@Deprecated
	public void setShaderUniform4fv(int shaderUniformId, float[] matrix) {
		GLES20.glUniformMatrix4fv(shaderUniformId, 1, false, matrix, 0);
	}

	/**
	 * ��������ɫ��-uniform-������ֵ,�����<br>
	 * 
	 * ֻ��ԣ�ÿ��3�����ݣ�ÿ�����ݶ�Ϊ float�ͣ�ռ��4���ֽ�<br>
	 * 
	 * @param shaderMeId
	 *            ���Ա���id
	 * @param matrix
	 *            ����
	 */
	@Deprecated
	public void setShaderUniform3fv(int shaderUniformId, int count,
			FloatBuffer date) {
		GLES20.glUniform3fv(shaderUniformId, 1, date);
	}

	/**
	 * ��������ɫ��-uniform-������ֵ,�����<br>
	 * 
	 * ֻ��ԣ�1�����ݣ�����Ϊ float�ͣ�ռ��4���ֽ�<br>
	 * 
	 * @param shaderUniformId
	 *            ���Ա���id
	 * @param date
	 *            float
	 */
	@Deprecated
	public void setShaderUniform1f(int shaderUniformId, float date) {
		GLES20.glUniform1f(shaderUniformId, date);
	}

	// ======= end

	/**
	 * ��ȡ��ɫ������id
	 * 
	 * @return jqShaderProgramId
	 */
	public int getShaderProgramId() {
		return jqShaderProgramId;
	}

	/**
	 * ������ɫ������id
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
	 * �Զ�����Ⱦ���߳���id
	 */
	private int jqShaderProgramId;

}
