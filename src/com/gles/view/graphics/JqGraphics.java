package com.gles.view.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLSurfaceView;

import com.gles.view.shader.JqShaderMannger;
import com.gles.view.shader.Shader;

/**
 * 基础图形 基类
 * 
 * @author qianjunping
 * 
 */
public abstract class JqGraphics {

	/**
	 * @hide
	 */
	private static long key = 291291939234429l;

	/**
	 * 着色器管理器
	 */
	private JqShaderMannger jqShaderMannger;

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
	 * only GraphicsGroup use
	 * 
	 * @param mv
	 */
	public JqGraphics(GLSurfaceView mv) {

	}

	protected void initGraphic(GLSurfaceView mv, String vertexPath,
			String fragmentPath) {
		if (jqShaderMannger == null) {
			jqShaderMannger = JqShaderMannger.getJqShaderMannger(key);
		}
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
	 * 除了通用的着色器变量外，选择性的加入更多的着色器变量<br>
	 * 
	 * 在默认的graphics中 <br>
	 * 已经初始化了 :<br>
	 * 总矩阵id - jqShaderMVPMatrixId<br>
	 * 顶点位置属性引用id - jqShaderVertexPositionAttrId<br>
	 * 以及着色器程序id - getShaderMannger().getShaderProgramId()<br>
	 */
	@Deprecated
	public void addMoreShaderMember() {
	}

	/**
	 * 
	 * 如果所写类继承自 TextureImage 必须加入initGraphic();<br>
	 * <p>
	 * 示例：以下代码是自定义顶点的相关数据，还可以使用LoadUtil从obj模型中导入3D图形<code>
	 * 初始化 顶点数据，由子类自行实现<br> 
	 * <p>
	 * jqVertexCount = 3; <br>
	 * <p>
	 * position <br> 
	 * final float UNIT_SIZE = 0.2f; <br>
	 * float vertices[] = new float[] { -4 * UNIT_SIZE, 0, 0, 0, -4 * UNIT_SIZE,0, 4 * UNIT_SIZE, 0, 0 }; <br>
	 * ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);//每个float 占有 4 个字节  <br>
	 * vbb.order(ByteOrder.nativeOrder());  <br>
	 * jqVertexBuffer = vbb.asFloatBuffer();  <br>
	 * jqVertexBuffer.put(vertices);  <br>
	 * jqVertexBuffer.position(0); <br> 
	 * <p>
	 * color <br> 
	 * float colors[] = new float[] { 1, 1, 1,0, 0, 0, 1, 0, 0, 1, 0, 0 }; <br> 
	 * ByteBuffer cbb =ByteBuffer.allocateDirect(colors.length * 4); <br>
	 * cbb.order(ByteOrder.nativeOrder());  <br> 
	 * jqVertexColorBuffer =cbb.asFloatBuffer(); <br> 
	 * jqVertexColorBuffer.put(colors); <br>
	 * jqVertexColorBuffer.position(0); </code>
	 */
	public abstract void initVertexData();

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

		int jqShaderProgramId = -1;

		// 加载顶点着色器的脚本内容
		jqShaderVertex = Shader.loadFromAssetsFile(vertexPath,
				mv.getResources());
		// 加载片元着色器的脚本内容
		jqShaderFragment = Shader.loadFromAssetsFile(fragmentPath,
				mv.getResources());
		// 基于顶点着色器与片元着色器创建程序
		jqShaderProgramId = Shader.createProgram(jqShaderVertex,
				jqShaderFragment);
		jqShaderMannger.setShaderProgramId(jqShaderProgramId);

		// 初始化着色器成员
		initShaderMember();
		addMoreShaderMember();
	}

	public JqShaderMannger getShaderMannger() {
		return jqShaderMannger;
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
	public abstract void drawSelf(int... textureID);

	// /**
	// * 后台处理方法 ,会启动子线程调用一次此方法<br>
	// * 重写此方法实现3D图形的变换操作，后台运行不占用主UI线程<br>
	// * 若无次操作，可只重写接口，不添加任何代码
	// */
	// public abstract void doInbackground();
}
