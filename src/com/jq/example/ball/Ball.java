package com.jq.example.ball;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.contents.JqMatrixState;
import com.gles.view.graphics.JqDefaultGraphics;

public class Ball extends JqDefaultGraphics {

	private int vCount;
	private FloatBuffer mPosition;
	private FloatBuffer mTexPosition;

	private int shaderTexPosi;
	private int shaderLight;
	private int changeMat;
	private int creamLocation;

	private int texDay;
	private int texNigth;

	public Ball(GLSurfaceView scene, String vertexPath, String fragmentPath) {
		super(scene, vertexPath, fragmentPath);
	}

	@Override
	public void addMoreShaderMember() {

		shaderTexPosi = getShaderMannger().getShaderAttribId("aTexture");

		shaderLight = getShaderMannger().getShaderUniformId("aLightlocation");

		changeMat = getShaderMannger().getShaderUniformId("changeMatrix");

		creamLocation = getShaderMannger().getShaderUniformId("creamLocation");

		texDay = getShaderMannger().getShaderUniformId("sTextureDay");

		texNigth = getShaderMannger().getShaderUniformId("sTextureNight");

	}

	@Override
	public void initVertexData() {

		int anglSpace = 10;

		float r = 0.5f;

		List<Float> mlist = new ArrayList<Float>();
		List<Float> mlistTex = new ArrayList<Float>();

		for (int hang = 90, hangS = 0; hang >= -90; hang -= anglSpace, hangS += 1) {
			for (int lie = 0, lieS = 0; lie <= 360; lie += anglSpace, lieS += 1) {

				// 0
				float x0 = (float) (r * Math.cos(Math.toRadians(hang)) * Math
						.cos(Math.toRadians(lie)));
				float y0 = (float) (r * Math.cos(Math.toRadians(hang)) * Math
						.sin(Math.toRadians(lie)));
				float z0 = (float) (r * Math.sin(Math.toRadians(hang)));

				// 1
				float x1 = (float) (r * Math.cos(Math.toRadians(hang)) * Math
						.cos(Math.toRadians(lie + anglSpace)));
				float y1 = (float) (r * Math.cos(Math.toRadians(hang)) * Math
						.sin(Math.toRadians(lie + anglSpace)));
				float z1 = (float) (r * Math.sin(Math.toRadians(hang)));

				// 2
				float x2 = (float) (r
						* Math.cos(Math.toRadians(hang - anglSpace)) * Math
						.cos(Math.toRadians(lie)));
				float y2 = (float) (r
						* Math.cos(Math.toRadians(hang - anglSpace)) * Math
						.sin(Math.toRadians(lie)));
				float z2 = (float) (r * Math.sin(Math.toRadians(hang
						- anglSpace)));

				// 3
				float x3 = (float) (r
						* Math.cos(Math.toRadians(hang - anglSpace)) * Math
						.cos(Math.toRadians(lie + anglSpace)));
				float y3 = (float) (r
						* Math.cos(Math.toRadians(hang - anglSpace)) * Math
						.sin(Math.toRadians(lie + anglSpace)));
				float z3 = (float) (r * Math.sin(Math.toRadians(hang
						- anglSpace)));

				mlist.add(x0);
				mlist.add(y0);
				mlist.add(z0);

				// 1
				mlistTex.add(lieS / 36f);
				mlistTex.add(hangS / 18f);

				mlist.add(x2);
				mlist.add(y2);
				mlist.add(z2);

				// 2
				mlistTex.add(lieS / 36f);
				mlistTex.add((hangS + 1) / 18f);

				mlist.add(x1);
				mlist.add(y1);
				mlist.add(z1);

				// 3
				mlistTex.add((lieS + 1) / 36f);
				mlistTex.add(hangS / 18f);

				mlist.add(x1);
				mlist.add(y1);
				mlist.add(z1);

				// 4
				mlistTex.add((lieS + 1) / 36f);
				mlistTex.add(hangS / 18f);

				mlist.add(x2);
				mlist.add(y2);
				mlist.add(z2);

				// 5
				mlistTex.add(lieS / 36f);
				mlistTex.add((hangS + 1) / 18f);

				mlist.add(x3);
				mlist.add(y3);
				mlist.add(z3);

				// 6
				mlistTex.add((lieS + 1) / 36f);
				mlistTex.add((hangS + 1) / 18f);
			}
		}

		float[] position = new float[mlist.size()];

		for (int i = 0; i < mlist.size(); i++) {
			position[i] = mlist.get(i);
		}

		float[] texPosi = new float[mlistTex.size()];

		for (int i = 0; i < mlistTex.size(); i++) {
			texPosi[i] = mlistTex.get(i);
		}

		mPosition = createFloatBuffer(position);

		mTexPosition = createFloatBuffer(texPosi);
		vCount = mlist.size() / 3;

	}

	@Override
	public void drawSelf(int... textureID) {

		GLES20.glUseProgram(getShaderMannger().getShaderProgramId());

		GLES20.glUniformMatrix4fv(jqShaderMVPMatrixId, 1, false,
				JqMatrixState.getFinalMatrix(), 0);

		GLES20.glUniform3fv(shaderLight, 1,
				JqMatrixState.getLightLocationBuffer());

		GLES20.glUniformMatrix4fv(changeMat, 1, false,
				JqMatrixState.getChangeMatrix(), 0);

		GLES20.glUniform3fv(creamLocation, 1,
				JqMatrixState.getCameraLocationFloatBuffer());

		GLES20.glVertexAttribPointer(jqShaderVertexPositionAttrId, 3,
				GLES20.GL_FLOAT, false, 3 * 4, mPosition);

		GLES20.glVertexAttribPointer(shaderTexPosi, 2, GLES20.GL_FLOAT, false,
				2 * 4, mTexPosition);

		GLES20.glEnableVertexAttribArray(jqShaderVertexPositionAttrId);
		GLES20.glEnableVertexAttribArray(shaderTexPosi);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[0]);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[1]);
		GLES20.glUniform1i(texDay, 0);
		GLES20.glUniform1i(texNigth, 1);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);

	}

}
