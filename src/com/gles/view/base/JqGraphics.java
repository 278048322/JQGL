package com.gles.view.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.gles.view.graphics.Shader;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;


/**
 * 基础图形 基类
 * 
 * @author qianjunping
 * 
 */
public abstract class JqGraphics {

	/**
	 * 着色器程序Id
	 */
	private int jqShaderProgramId = -1;

	/**
	 * 顶点着色器
	 */
	private String jqShaderVertex;

	/**
	 * 片元着色器
	 */
	private String jqShaderFragment;

	public JqGraphics(GLSurfaceView mv, String vertexPath, String fragmentPath) {
		initGraphic(mv, vertexPath, fragmentPath);
	}

	/**
	 * 仅GraphicsGroup可以使用
	 * 
	 * @param mv
	 */
	public JqGraphics(GLSurfaceView mv) {

	}

	protected void initGraphic(GLSurfaceView mv, String vertexPath,
			String fragmentPath) {
		// 加载着色器程序
		loadShader(mv, vertexPath, fragmentPath);
	}

	/**
	 * 
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
	public abstract void initShaderMember();

	/**
	 * 
	 * 如果所写类继承自 JqTextureView 必须加入initGraphic();<br>
	 * <p>
	 * 示例：以下代码是自定义顶点的相关数据，还可以使用LoadUtil从obj模型中导入3D图形等<code>
	 * 常见初始化的数据有：<br>
	 * 1.3D 图形顶点数据<br>
	 * 2.图形顶点法向量数据<br>
	 * 3.图形纹理坐标数据<br>
	 * 4.图形顶点颜色数据<br>
	 * <p>
	 * 初始化 顶点数据，由子类自行实现<br> 
	 * <p>
	 * 顶点数据<br>
	 * float UNIT_SIZE = 0.2f; <br>
	 * float vertices[] = new float[] { -4 * UNIT_SIZE, 0, 0, <br>
	 * 0, -4 * UNIT_SIZE,0, 4 * UNIT_SIZE, 0, 0 }; <br>
	 * ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);//每个float 占有 4 个字节  <br>
	 * vbb.order(ByteOrder.nativeOrder());  <br>
	 * jqVertexBuffer = vbb.asFloatBuffer();  <br>
	 * jqVertexBuffer.put(vertices);  <br>
	 * jqVertexBuffer.position(0); <br> 
	 * <p>
	 * 顶点颜色数据<br> 
	 * float colors[] = new float[] { 1, 1, 1,0, 0, 0, 1, 0, 0, 1, 0, 0 }; <br> 
	 * ByteBuffer cbb =ByteBuffer.allocateDirect(colors.length * 4); <br>
	 * cbb.order(ByteOrder.nativeOrder());  <br> 
	 * jqVertexColorBuffer =cbb.asFloatBuffer(); <br> 
	 * jqVertexColorBuffer.put(colors); <br>
	 * jqVertexColorBuffer.position(0); </code>
	 */
	public abstract void initGraphicsData();

	/**
	 * 示例：
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
		// 每个float 占有4 个字节
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

	// 加载着色器
	private void loadShader(GLSurfaceView mv, String vertexPath,
			String fragmentPath) {

		// 加载顶点着色器的脚本内容
		jqShaderVertex = Shader.loadFromAssetsFile(vertexPath,
				mv.getResources());
		// 加载片元着色器的脚本内容
		jqShaderFragment = Shader.loadFromAssetsFile(fragmentPath,
				mv.getResources());
		// 基于顶点着色器与片元着色器创建程序
		jqShaderProgramId = Shader.createProgram(jqShaderVertex,
				jqShaderFragment);

		// 初始化着色器成员
		initShaderMember();
	}

	/**
	 * 将数据以及矩阵变换等操作传入渲染管线<br>
	 * <p>
	 * GLES20.glUseProgram(jqShaderProgramId); <br>
	 * // 初始化变换矩阵<br>
	 * Matrix.setRotateM(jqMVPMatrix, 0, 0, 0, 1, 0); <br>
	 * // 设置沿Z轴正向位移1<br>
	 * Matrix.translateM(jqMVPMatrix, 0, 0, 0, 1); <br>
	 * 设置绕x轴旋转<br>
	 * Matrix.rotateM(jqMVPMatrix, 0, xAngle, 1, 0, 0);<br>
	 * <p>
	 * //将 uniform 变量值传递给着色器<br>
	 * GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
	 * Triangle.getFianlMatrix(mMatrix), 0);<br>
	 * <p>
	 * // 将 attribpointer属性变量传递给着色器<br>
	 * GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
	 * GLES20.GL_FLOAT, false, 3 * 4, jqVertexBuffer);<br>
	 * GLES20.glVertexAttribPointer(jqShaderVertexColorAttrId, 4,
	 * GLES20.GL_FLOAT, false, 4 * 4, jqVertexColorBuffer); <br>
	 * <p>
	 * //允许顶点位置数据数组<br>
	 * GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);<br>
	 * GLES20.glEnableVertexAttribArray(jqShaderVertexColorAttrId); <br>
	 * <p>
	 * // 绑定纹理 <br>
	 * GLES20.glActiveTexture(GLES20.GL_TEXTURE0);<br>
	 * GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);<br>
	 * GLES20.glActiveTexture(GLES20.GL_TEXTURE1);<br>
	 * GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texIdNight);<br>
	 * GLES20.glUniform1i(uDayTexHandle, 0); <br>
	 * GLES20.glUniform1i(uNightTexHandle, 1); <br>
	 * <p>
	 * 绘制三D图形<br>
	 * GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, jqVertexCount);
	 */
	public abstract void onDraw(int... textureID);

	/**
	 * 获取着色器程序id
	 * 
	 * @return jqShaderProgramId
	 */
	public int getShaderProgramId() {
		if (jqShaderProgramId == -1) {
			throw new RuntimeException("错误的着色器程序 Id");
		}
		return jqShaderProgramId;
	}

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
}
