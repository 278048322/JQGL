package com.gles.view.base;

public class JqVertex {

	/**
	 * 顶点数据
	 */
	private float[] vertex;

	/**
	 * 顶点法向量
	 */
	private float[] normal;

	/**
	 * 纹理数据
	 */
	private float[] texture;

	public JqVertex() {
	}

	/**
	 * 
	 * @param vertex
	 *            顶点数据
	 * 
	 * @param normal
	 *            顶点法向量数据
	 * 
	 * @param texture
	 *            纹理坐标数据
	 */
	public JqVertex(float[] vertex, float[] normal, float[] texture) {
		this.vertex = vertex;
		this.normal = normal;
		this.texture = texture;
	}

	public float[] getVertex() {
		return vertex;
	}

	public void setVertex(float[] vertex) {
		this.vertex = vertex;
	}

	public float[] getNormal() {
		return normal;
	}

	public void setNormal(float[] normal) {
		this.normal = normal;
	}

	public float[] getTexture() {
		return texture;
	}

	public void setTexture(float[] texture) {
		this.texture = texture;
	}

}
