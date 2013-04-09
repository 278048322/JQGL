package com.jq.example.cube;

import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;

/**
 * ������
 * 
 * @author qianjunping
 * 
 */
public class Cube extends JqGraphics {

	private FloatBuffer mPotions;
	private FloatBuffer mPColors;
	private int vCount;

	/**
	 * �ܱ任��������id (uniform)
	 */
	protected int jqShaderMVPMatrixId;

	/**
	 * ����λ����������id
	 */
	protected int jqShaderVertexPositionAttrId;

	private float ange;
	private int jqShaderVertexColorAttrId;

	public Cube(GLSurfaceView mv, String vertexPath, String fragmentPath) {
		super(mv, vertexPath, fragmentPath);
	}

	@Override
	public void initGraphicsData() {
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
	public void onDraw(int... textureID) {
		// ָ��ʹ����ɫ�����򣬱�����ã�������ͼ�β���ʹ�ò�ͬ����ɫ������
		GLES20.glUseProgram(getShaderProgramId());

		// JqMatrixState.saveMatrix();
		//
		// JqMatrixState.rotate(ange, 0, 0, 1);

		GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
				JqGL.getFinalMatrix(), 0);

		// �� attribpointer���Ա������ݸ���ɫ��
		GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
				GLES20.GL_FLOAT, false, 3 * 4, mPotions);
		GLES20.glVertexAttribPointer(jqShaderVertexColorAttrId, 4,
				GLES20.GL_FLOAT, false, 4 * 4, mPColors);
		// ������λ����������
		GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);
		GLES20.glEnableVertexAttribArray(jqShaderVertexColorAttrId);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);

		// JqMatrixState.restoreMatrix();
	}

	@Override
	public void initShaderMember() {
		jqShaderMVPMatrixId = getShaderUniformId("sMVPMatrix");
		jqShaderVertexPositionAttrId = getShaderAttribId("sPosition");
		jqShaderVertexColorAttrId = getShaderAttribId("aColor");
	}

}
