package com.jq.example.texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqMatrixState;

public class Triangle extends JqGraphics {

	private FloatBuffer mPosition;
	private FloatBuffer mPositionTexture;
	private int vCount = 6;
	float xAngle = 100;
	/**
	 * 总变换矩阵引用id (uniform)
	 */
	protected int jqShaderMVPMatrixId;

	/**
	 * 顶点位置属性引用id
	 */
	protected int jqShaderVertexPositionAttrId;
	private int texture;

	public Triangle(GLSurfaceView mv, String vertexPath, String fragmentPath) {
		super(mv, vertexPath, fragmentPath);
	}

	@Override
	public void initGraphicsData() {

		// 顶点坐标数据的初始化
		vCount = 3;
		final float UNIT_SIZE = 0.2f;
		float vertices[] = new float[] {
				//
				-4 * UNIT_SIZE, 0, 0,
				//
				4 * UNIT_SIZE, 0, 0,
				//
				0, 4 * UNIT_SIZE, 0 };

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mPosition = vbb.asFloatBuffer();
		mPosition.put(vertices);
		mPosition.position(0);

		float texture[] = new float[] {
				//
				0, 1,
				//
				1, 1,
				//
				0.5f, 0 };
		mPositionTexture = createFloatBuffer(texture);
	}

	@Override
	public void onDraw(int... textureID) {
		// 制定使用某套shader程序
		GLES20.glUseProgram(getShaderProgramId());
		// 保护矩阵
		JqGL.saveSceneMatrix();

		JqGL.rotate(xAngle, 1, 0, 0);

		GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
				JqGL.getFinalMatrix(), 0);
		// 为画笔指定顶点位置数据
		GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
				GLES20.GL_FLOAT, false, 3 * 4, mPosition);
		GLES20.glVertexAttribPointer(texture, 2, GLES20.GL_FLOAT, false, 2 * 4,
				mPositionTexture);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);
		GLES20.glEnableVertexAttribArray(texture);
		// 绘制三角形
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vCount);

		// 恢复矩阵
		JqGL.restoreSceneMatrix();
	}

	@Override
	public void initShaderMember() {

		texture = getShaderAttribId("aTexture");

		jqShaderMVPMatrixId = getShaderUniformId("sMVPMatrix");

		jqShaderVertexPositionAttrId = getShaderAttribId("sPosition");
	}

}
