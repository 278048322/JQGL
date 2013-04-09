package com.gles.view.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.gles.view.graphics.Shader;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;


/**
 * ����ͼ�� ����
 * 
 * @author qianjunping
 * 
 */
public abstract class JqGraphics {

	/**
	 * ��ɫ������Id
	 */
	private int jqShaderProgramId = -1;

	/**
	 * ������ɫ��
	 */
	private String jqShaderVertex;

	/**
	 * ƬԪ��ɫ��
	 */
	private String jqShaderFragment;

	public JqGraphics(GLSurfaceView mv, String vertexPath, String fragmentPath) {
		initGraphic(mv, vertexPath, fragmentPath);
	}

	/**
	 * ��GraphicsGroup����ʹ��
	 * 
	 * @param mv
	 */
	public JqGraphics(GLSurfaceView mv) {

	}

	protected void initGraphic(GLSurfaceView mv, String vertexPath,
			String fragmentPath) {
		// ������ɫ������
		loadShader(mv, vertexPath, fragmentPath);
	}

	/**
	 * 
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
	public abstract void initShaderMember();

	/**
	 * 
	 * �����д��̳��� JqTextureView �������initGraphic();<br>
	 * <p>
	 * ʾ�������´������Զ��嶥���������ݣ�������ʹ��LoadUtil��objģ���е���3Dͼ�ε�<code>
	 * ������ʼ���������У�<br>
	 * 1.3D ͼ�ζ�������<br>
	 * 2.ͼ�ζ��㷨��������<br>
	 * 3.ͼ��������������<br>
	 * 4.ͼ�ζ�����ɫ����<br>
	 * <p>
	 * ��ʼ�� �������ݣ�����������ʵ��<br> 
	 * <p>
	 * ��������<br>
	 * float UNIT_SIZE = 0.2f; <br>
	 * float vertices[] = new float[] { -4 * UNIT_SIZE, 0, 0, <br>
	 * 0, -4 * UNIT_SIZE,0, 4 * UNIT_SIZE, 0, 0 }; <br>
	 * ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);//ÿ��float ռ�� 4 ���ֽ�  <br>
	 * vbb.order(ByteOrder.nativeOrder());  <br>
	 * jqVertexBuffer = vbb.asFloatBuffer();  <br>
	 * jqVertexBuffer.put(vertices);  <br>
	 * jqVertexBuffer.position(0); <br> 
	 * <p>
	 * ������ɫ����<br> 
	 * float colors[] = new float[] { 1, 1, 1,0, 0, 0, 1, 0, 0, 1, 0, 0 }; <br> 
	 * ByteBuffer cbb =ByteBuffer.allocateDirect(colors.length * 4); <br>
	 * cbb.order(ByteOrder.nativeOrder());  <br> 
	 * jqVertexColorBuffer =cbb.asFloatBuffer(); <br> 
	 * jqVertexColorBuffer.put(colors); <br>
	 * jqVertexColorBuffer.position(0); </code>
	 */
	public abstract void initGraphicsData();

	/**
	 * ʾ����
	 * <p>
	 * ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	 * <p>
	 * vbb.order(ByteOrder.nativeOrder());
	 * <p>
	 * jqVertexBuffer = vbb.asFloatBuffer();
	 * <p>
	 * jqVertexBuffer.put(vertices);
	 * <p>
	 * jqVertexBuffer.position(0);
	 * <p>
	 * 
	 * @param vertices
	 * @return
	 */
	public FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer tempFloatBuffer = null;
		// ÿ��float ռ��4 ���ֽ�
		ByteBuffer vbb = ByteBuffer.allocateDirect(data.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		tempFloatBuffer = vbb.asFloatBuffer();
		tempFloatBuffer.put(data);
		tempFloatBuffer.position(0);
		return tempFloatBuffer;
	}

	protected ByteBuffer createByteBuffer(byte indexs[]) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(indexs.length);
		// vbb.order(ByteOrder.nativeOrder());
		vbb.put(indexs);
		vbb.position(0);
		return vbb;
	}

	// ������ɫ��
	private void loadShader(GLSurfaceView mv, String vertexPath,
			String fragmentPath) {

		// ���ض�����ɫ���Ľű�����
		jqShaderVertex = Shader.loadFromAssetsFile(vertexPath,
				mv.getResources());
		// ����ƬԪ��ɫ���Ľű�����
		jqShaderFragment = Shader.loadFromAssetsFile(fragmentPath,
				mv.getResources());
		// ���ڶ�����ɫ����ƬԪ��ɫ����������
		jqShaderProgramId = Shader.createProgram(jqShaderVertex,
				jqShaderFragment);

		// ��ʼ����ɫ����Ա
		initShaderMember();
	}

	/**
	 * �������Լ�����任�Ȳ���������Ⱦ����<br>
	 * <p>
	 * GLES20.glUseProgram(jqShaderProgramId); <br>
	 * // ��ʼ���任����<br>
	 * Matrix.setRotateM(jqMVPMatrix, 0, 0, 0, 1, 0); <br>
	 * // ������Z������λ��1<br>
	 * Matrix.translateM(jqMVPMatrix, 0, 0, 0, 1); <br>
	 * ������x����ת<br>
	 * Matrix.rotateM(jqMVPMatrix, 0, xAngle, 1, 0, 0);<br>
	 * <p>
	 * //�� uniform ����ֵ���ݸ���ɫ��<br>
	 * GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
	 * Triangle.getFianlMatrix(mMatrix), 0);<br>
	 * <p>
	 * // �� attribpointer���Ա������ݸ���ɫ��<br>
	 * GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
	 * GLES20.GL_FLOAT, false, 3 * 4, jqVertexBuffer);<br>
	 * GLES20.glVertexAttribPointer(jqShaderVertexColorAttrId, 4,
	 * GLES20.GL_FLOAT, false, 4 * 4, jqVertexColorBuffer); <br>
	 * <p>
	 * //������λ����������<br>
	 * GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);<br>
	 * GLES20.glEnableVertexAttribArray(jqShaderVertexColorAttrId); <br>
	 * <p>
	 * // ������ <br>
	 * GLES20.glActiveTexture(GLES20.GL_TEXTURE0);<br>
	 * GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);<br>
	 * GLES20.glActiveTexture(GLES20.GL_TEXTURE1);<br>
	 * GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texIdNight);<br>
	 * GLES20.glUniform1i(uDayTexHandle, 0); <br>
	 * GLES20.glUniform1i(uNightTexHandle, 1); <br>
	 * <p>
	 * ������Dͼ��<br>
	 * GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, jqVertexCount);
	 */
	public abstract void onDraw(int... textureID);

	/**
	 * ��ȡ��ɫ������id
	 * 
	 * @return jqShaderProgramId
	 */
	public int getShaderProgramId() {
		if (jqShaderProgramId == -1) {
			throw new RuntimeException("�������ɫ������ Id");
		}
		return jqShaderProgramId;
	}

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
}
