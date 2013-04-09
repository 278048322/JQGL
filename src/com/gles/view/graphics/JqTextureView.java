package com.gles.view.graphics;

import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.gles.view.base.JqGL;
import com.gles.view.base.JqGraphics;
import com.gles.view.base.JqVertex;

/**
 * 无光照效果 的纹理图片基类<br>
 * 使用纹理图填充指定的3D图形<br>
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
	 * 着色器内建变量
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
	 * 数据填充器<br>
	 * 提供顶点数据、纹理数据、光照法向量等。<br>
	 * 由initData()方法调用 {@link #initGraphicsData()}<br>
	 * 
	 * @param width
	 *            图形宽度
	 * @param height
	 *            图形高度
	 * @param texWidth
	 *            纹理宽度
	 * @param texHeight
	 *            纹理显宽度
	 * 
	 * @return ObjData3D
	 */
	public abstract JqVertex loadData(float width, float height,
			float texWidth, float texHeight);

	/**
	 * 初始化纹理<br>
	 * 由子类自行调用 JqTextureUtil 自行创建<br>
	 * 
	 * @return
	 */
	public abstract int initTexture();

	/**
	 * 改变矩阵以实现对视图的旋转、移动等变换<br>
	 * 子类有特殊需求可自行实现<br>
	 */
	protected void chageMatrix() {
		JqGL.translate(locationX, locationY, locationZ);
		JqGL.rotate(rotaAngle, rotaX, rotaY, rotaZ);
	}

	@Override
	public void initGraphicsData() {

		// 初始化着色器，并且初始化上层代码-着色器中的成员印射
		initGraphic(mSurfaceView, "jq_shader/widgets/texture_view/vertex.sh",
				"jq_shader/widgets/texture_view/frag.sh");

		JqVertex data = loadData(width, height, texWidth, texHeight);
		if (data != null) {
			float[] tempVertex = data.getVertex();
			vCount = tempVertex.length / 3;
			verBuffer = createFloatBuffer(tempVertex);
			texBuffer = createFloatBuffer(data.getTexture());

			// 生成纹理
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

		// 保存矩阵状态
		JqGL.saveSceneMatrix();
		// 改变矩阵
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

		// 绑定纹理
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texPictID);

		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);

		// 恢复矩阵
		JqGL.restoreSceneMatrix();

	}

	/**
	 * 设置宽高
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
	 * 设置在坐标系中的位置，默认的每个view 的初始位置都为 (0,0,0)
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
