package com.gles.view.base;

public class JqVertex {

	/**
	 * ��������
	 */
	private float[] vertex;

	/**
	 * ���㷨����
	 */
	private float[] normal;

	/**
	 * ��������
	 */
	private float[] texture;

	public JqVertex() {
	}

	/**
	 * 
	 * @param vertex
	 *            ��������
	 * 
	 * @param normal
	 *            ���㷨��������
	 * 
	 * @param texture
	 *            ������������
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
