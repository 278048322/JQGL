package com.gles.view.graphics;

import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqVertex;

/**
 * �޹���Ч�� ������ͼƬ����<br>
 * ʹ������ͼ���ָ����3Dͼ��<br>
 * 
 * @author qianjunping
 * 
 */
public abstract class JqTextureView extends JqGraphics {

	protected GLSurfaceView mSurfaceView;

	private float width = 1f;
	private float height = 1f;
	private float texWidth = 1f;
	private float texHeight = 1f;

	protected float locationX;
	protected float locationY;
	protected float locationZ;

	protected float rotaAngle;
	protected float rotaX;
	protected float rotaY;
	protected float rotaZ;

	/**
	 * ��ɫ���ڽ�����
	 */
	private int mvpMatrixId;
	private int vertexId;
	private int textureId;

	/**
	 * data
	 */
	private int texPictID;
	private int vCount;
	private FloatBuffer verBuffer;
	private FloatBuffer texBuffer;

	public JqTextureView(GLSurfaceView mv, int texDrawableId) {
		super(mv);
		this.mSurfaceView = mv;
	}

	/**
	 * ���������<br>
	 * �ṩ�������ݡ��������ݡ����շ������ȡ�<br>
	 * ��initData()�������� {@link #initGraphicsData()}<br>
	 * 
	 * @param width
	 *            ͼ�ο��
	 * @param height
	 *            ͼ�θ߶�
	 * @param texWidth
	 *            ������
	 * @param texHeight
	 *            �����Կ��
	 * 
	 * @return ObjData3D
	 */
	public abstract JqVertex loadData(float width, float height,
			float texWidth, float texHeight);

	/**
	 * ��ʼ������<br>
	 * ���������е��� JqTextureUtil ���д���<br>
	 * 
	 * @return
	 */
	public abstract int initTexture();

	/**
	 * �ı������ʵ�ֶ���ͼ����ת���ƶ��ȱ任<br>
	 * �������������������ʵ��<br>
	 */
	protected void chageMatrix() {
		JqGL.translate(locationX, locationY, locationZ);
		JqGL.rotate(rotaAngle, rotaX, rotaY, rotaZ);
	}

	@Override
	public void initGraphicsData() {

		// ��ʼ����ɫ�������ҳ�ʼ���ϲ����-��ɫ���еĳ�Աӡ��
		initGraphic(mSurfaceView, "jq_shader/widgets/texture_view/vertex.sh",
				"jq_shader/widgets/texture_view/frag.sh");

		JqVertex data = loadData(width, height, texWidth, texHeight);
		if (data != null) {
			float[] tempVertex = data.getVertex();
			vCount = tempVertex.length / 3;
			verBuffer = createFloatBuffer(tempVertex);
			texBuffer = createFloatBuffer(data.getTexture());

			// ��������
			texPictID = initTexture();
		} else {
			throw new RuntimeException(JqGL.EXCEPTION_VERTEXTDATA_NULL);
		}

	};

	@Override
	public void initShaderMember() {
		mvpMatrixId = getShaderUniformId("sMVPMatrix");
		vertexId = getShaderAttribId("sPosition");
		textureId = getShaderAttribId("sTexCoor");
	}

	@Override
	public void onDraw(int... textureID) {

		// �������״̬
		JqGL.saveSceneMatrix();
		// �ı����
		chageMatrix();

		GLES20.glUseProgram(getShaderProgramId());

		GLES20.glUniformMatrix4fv(mvpMatrixId, 1, false, JqGL.getFinalMatrix(),
				0);

		GLES20.glVertexAttribPointer(vertexId, 3, GLES20.GL_FLOAT, false,
				3 * 4, verBuffer);

		GLES20.glVertexAttribPointer(textureId, 2, GLES20.GL_FLOAT, false,
				2 * 4, texBuffer);

		GLES20.glEnableVertexAttribArray(vertexId);
		GLES20.glEnableVertexAttribArray(textureId);

		// ������
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texPictID);

		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);

		// �ָ�����
		JqGL.restoreSceneMatrix();

	}

	/**
	 * ���ÿ��
	 * 
	 * @param width
	 * @param height
	 */
	public void setWidthAndHeight(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public void setTexWidthAndHeight(float width, float height) {
		this.texWidth = width;
		this.texHeight = height;
	}

	public void rotate(float angle, float x, float y, float z) {
		this.rotaAngle = angle;
		this.rotaX = x;
		this.rotaY = y;
		this.rotaZ = z;
	}

	/**
	 * ����������ϵ�е�λ�ã�Ĭ�ϵ�ÿ��view �ĳ�ʼλ�ö�Ϊ (0,0,0)
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(float x, float y, float z) {
		this.locationX = x;
		this.locationY = y;
		this.locationZ = z;
	}

}
