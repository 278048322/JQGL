package com.jq.example.texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqMatrixState;
import com.gles.view.graphics.JqDefaultGraphics;

public class Triangle extends JqDefaultGraphics {

	private FloatBuffer mPosition;
	private FloatBuffer mPositionTexture;
	private int vCount = 6;
	float xAngle = 100;

	private int texture;

	public Triangle(GLSurfaceView mv, String vertexPath, String fragmentPath) {
		super(mv, vertexPath, fragmentPath);
	}

	@Override
	public void initVertexData() {

		// �����������ݵĳ�ʼ��
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
	public void drawSelf(int... textureID) {
		// �ƶ�ʹ��ĳ��shader����
		GLES20.glUseProgram(getShaderMannger().getShaderProgramId());
		// ��������
		JqMatrixState.saveMatrix();

		JqMatrixState.rotate(xAngle, 1, 0, 0);

		GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
				JqMatrixState.getFinalMatrix(), 0);
		// Ϊ����ָ������λ������
		GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
				GLES20.GL_FLOAT, false, 3 * 4, mPosition);
		GLES20.glVertexAttribPointer(texture, 2, GLES20.GL_FLOAT, false, 2 * 4,
				mPositionTexture);
		// ������λ����������
		GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);
		GLES20.glEnableVertexAttribArray(texture);
		// ����������
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vCount);

		// �ָ�����
		JqMatrixState.restoreMatrix();
	}

	@Override
	@Deprecated
	public void addMoreShaderMember() {

		texture = getShaderMannger().getShaderAttribId("aTexture");
	}

}
