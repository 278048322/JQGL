package com.jq.example.cube;

import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqMatrixState;
import com.gles.view.graphics.JqDefaultGraphics;

/**
 * 正方形
 * 
 * @author qianjunping
 * 
 */
public class Cube extends JqDefaultGraphics {

	private FloatBuffer mPotions;
	private FloatBuffer mPColors;
	private int vCount;

	private float ange;
	private int jqShaderVertexColorAttrId;

	public Cube(GLSurfaceView mv, String vertexPath, String fragmentPath) {
		super(mv, vertexPath, fragmentPath);
	}

	@Override
	public void initVertexData() {
		float SIZE = 0.5f;
		vCount = 6;
		float[] points = new float[] {
				// one
				-SIZE, -SIZE, 0,
				// two
				-SIZE, SIZE, 0,
				// three
				SIZE, SIZE, 0,
				// four
				-SIZE, -SIZE, 0,
				// five
				SIZE, SIZE, 0,
				// siex
				SIZE, -SIZE, 0, };

		mPotions = createFloatBuffer(points);

		float[] colors = new float[] {
				// one
				0, 1, 1, 0,
				//
				1, 1, 0, 0,
				//
				0, 1, 1, 0,
				//
				1, 0, 1, 0,
				//
				1, 1, 0, 0,
				//
				0, 1, 1, 0, };

		mPColors = createFloatBuffer(colors);
	}

	@Override
	public void drawSelf(int... textureID) {
		// 指定使用着色器程序，必须调用，避免多个图形不是使用不同的着色器程序
		GLES20.glUseProgram(getShaderMannger().getShaderProgramId());

		// JqMatrixState.saveMatrix();
		//
		// JqMatrixState.rotate(ange, 0, 0, 1);

		// GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
		// JqMatrixState.getFinalMatrix(), 0);
		getShaderMannger().setShaderUniform4fv(jqShaderMVPMatrixId,
				JqMatrixState.getFinalMatrix());

		getShaderMannger().setShaderAttrib3fv(jqShaderVertexPositionAttrId,
				mPotions);
		// 将 attribpointer属性变量传递给着色器
		// GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
		// GLES20.GL_FLOAT, false, 3 * 4, mPotions);
		// GLES20.glVertexAttribPointer(jqShaderVertexColorAttrId, 4,
		// GLES20.GL_FLOAT, false, 4 * 4, mPColors);
		getShaderMannger().setShaderAttrib4fv(jqShaderVertexColorAttrId,
				mPColors);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);
		GLES20.glEnableVertexAttribArray(jqShaderVertexColorAttrId);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);

		// JqMatrixState.restoreMatrix();
	}

	@Override
	public void addMoreShaderMember() {
		jqShaderVertexColorAttrId = getShaderMannger().getShaderAttribId(
				"aColor");
	}

}
