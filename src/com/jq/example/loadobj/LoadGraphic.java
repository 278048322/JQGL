package com.jq.example.loadobj;

import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqVertex;
import com.gles.view.base.JqMatrixState;
import com.gles.view.util.JqLoadObjUtil;

public class LoadGraphic extends JqGraphics {

	// uniform mat4 sMVPMatrix; //总变换矩阵
	// uniform mat4 sChangeMatrix;//变化矩阵
	// uniform vec3 sLightlocation;//光源位置
	// uniform vec3 sCreamLocation;//摄像机位置
	// attribute vec2 sTexture;//顶点纹理坐标
	// attribute vec3 sNormalVector;//顶点法向量
	private GLSurfaceView glSurface;

	private FloatBuffer mPosition;
	private FloatBuffer mNoramlPosition;
	private FloatBuffer mTextureBuffer;
	private int vCount;

	/**
	 * 总变换矩阵引用id (uniform)
	 */
	protected int jqShaderMVPMatrixId;

	/**
	 * 顶点位置属性引用id
	 */
	protected int jqShaderVertexPositionAttrId;
	private int changeMatrix;
	private int lightLocation;
	private int creamLocation;
	private int normalVertex;

	private int texturePosition;

	public LoadGraphic(GLSurfaceView mv, String vertexPath, String fragmentPath) {
		super(mv, vertexPath, fragmentPath);
		this.glSurface = mv;
	}

	@Override
	public void initShaderMember() {
		jqShaderMVPMatrixId = getShaderUniformId("sMVPMatrix");

		jqShaderVertexPositionAttrId = getShaderAttribId("sPosition");
		changeMatrix = getShaderUniformId("sChangeMatrix");
		lightLocation = getShaderUniformId("sLightlocation");
		creamLocation = getShaderUniformId("sCreamLocation");

		normalVertex = getShaderAttribId("sNormalVector");
		texturePosition = getShaderAttribId("sTexture");
	}

	@Override
	public void initGraphicsData() {

		JqVertex obj = JqLoadObjUtil.loadObjFromAssets("obj/ch_t.obj",
				glSurface.getResources());

		vCount = obj.getVertex().length / 3;
		mPosition = createFloatBuffer(obj.getVertex());
		mNoramlPosition = createFloatBuffer(obj.getNormal());
		mTextureBuffer = createFloatBuffer(obj.getTexture());
	}

	@Override
	public void onDraw(int... textureID) {

		GLES20.glUseProgram(getShaderProgramId());

		GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
				JqGL.getFinalMatrix(), 0);

		GLES20.glUniformMatrix4fv(changeMatrix, 1, false,
				JqGL.getChangeMatrix(), 0);

		GLES20.glUniform3fv(lightLocation, 1, JqGL.getLightLocationBuffer());

		GLES20.glUniform3fv(creamLocation, 1,
				JqGL.getCameraLocationFloatBuffer());

		GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
				GLES20.GL_FLOAT, false, 3 * 4, mPosition);

		GLES20.glVertexAttribPointer(texturePosition, 2, GLES20.GL_FLOAT,
				false, 2 * 4, mTextureBuffer);

		GLES20.glVertexAttribPointer(normalVertex, 3, GLES20.GL_FLOAT, false,
				3 * 4, mNoramlPosition);

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[0]);
		GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);
		GLES20.glEnableVertexAttribArray(normalVertex);
		GLES20.glEnableVertexAttribArray(texturePosition);

		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}
}
