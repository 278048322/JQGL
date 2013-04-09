package com.gles.view.base;

import java.util.ArrayList;
import java.util.List;

import com.gles.view.util.JqTextureUtil;

public class JqGraphicsFactory {

	public static JqVertex getBallUpVertexData(float radius, float angleSpan) {

		float angleV = 90;
		ArrayList<Float> alVertix = new ArrayList<Float>();// ��Ŷ��������ArrayList

		for (float vAngle = angleV; vAngle > 0; vAngle = vAngle - angleSpan)// ��ֱ����angleSpan��һ��
		{
			for (float hAngle = 360; hAngle > 0; hAngle = hAngle - angleSpan)// ˮƽ����angleSpan��һ��
			{
				// ����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ��ı��ζ�������
				// ��������������ı��ε�������
				double xozLength = radius * Math.cos(Math.toRadians(vAngle));
				float x1 = (float) (xozLength * Math
						.cos(Math.toRadians(hAngle)));
				float z1 = (float) (xozLength * Math
						.sin(Math.toRadians(hAngle)));
				float y1 = (float) (radius * Math.sin(Math.toRadians(vAngle)));

				xozLength = radius
						* Math.cos(Math.toRadians(vAngle - angleSpan));
				float x2 = (float) (xozLength * Math
						.cos(Math.toRadians(hAngle)));
				float z2 = (float) (xozLength * Math
						.sin(Math.toRadians(hAngle)));
				float y2 = (float) (radius * Math.sin(Math.toRadians(vAngle
						- angleSpan)));

				xozLength = radius
						* Math.cos(Math.toRadians(vAngle - angleSpan));
				float x3 = (float) (xozLength * Math.cos(Math.toRadians(hAngle
						- angleSpan)));
				float z3 = (float) (xozLength * Math.sin(Math.toRadians(hAngle
						- angleSpan)));
				float y3 = (float) (radius * Math.sin(Math.toRadians(vAngle
						- angleSpan)));

				xozLength = radius * Math.cos(Math.toRadians(vAngle));
				float x4 = (float) (xozLength * Math.cos(Math.toRadians(hAngle
						- angleSpan)));
				float z4 = (float) (xozLength * Math.sin(Math.toRadians(hAngle
						- angleSpan)));
				float y4 = (float) (radius * Math.sin(Math.toRadians(vAngle)));

				// ������һ������
				alVertix.add(x1);
				alVertix.add(y1);
				alVertix.add(z1);
				alVertix.add(x4);
				alVertix.add(y4);
				alVertix.add(z4);
				alVertix.add(x2);
				alVertix.add(y2);
				alVertix.add(z2);

				// �����ڶ�������
				alVertix.add(x2);
				alVertix.add(y2);
				alVertix.add(z2);
				alVertix.add(x4);
				alVertix.add(y4);
				alVertix.add(z4);
				alVertix.add(x3);
				alVertix.add(y3);
				alVertix.add(z3);
			}
		}

		// ��alVertix�е�����ֵת�浽һ��float������
		float vertices[] = new float[alVertix.size()];
		for (int i = 0; i < alVertix.size(); i++) {
			vertices[i] = alVertix.get(i);
		}

		int bw = (int) (360 / angleSpan);
		int bh = (int) ((angleV / angleSpan));
		float[] result = new float[bw * bh * 6 * 2];
		float sizew = 1.0f / bw;// ����
		float sizeh = 1.0f / bh;// ����
		int c = 0;
		for (int i = 0; i < bh; i++) {
			for (int j = 0; j < bw; j++) {
				// ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
				float s = j * sizew;
				float t = i * sizeh;

				result[c++] = s;
				result[c++] = t;

				result[c++] = s + sizew;
				result[c++] = t;

				result[c++] = s;
				result[c++] = t + sizeh;

				result[c++] = s;
				result[c++] = t + sizeh;

				result[c++] = s + sizew;
				result[c++] = t;

				result[c++] = s + sizew;
				result[c++] = t + sizeh;
			}
		}

		return new JqVertex(vertices, vertices, result);
	}

	/**
	 * 
	 * ��ȡ�������ݣ����� �������꣬���㷨��������������<br>
	 * 
	 * ��ѧ��ʽ: ��γ��Ϊ A,����ΪB <br>
	 * x = R * cos A * cos B<br>
	 * y = R * cos A * sin B<br>
	 * z = R * sin A<br>
	 * 
	 * 
	 * @param radius
	 *            ���ΰ뾶
	 * @param anglSpace
	 *            ȡ����ܼ���,�ܼ���ԽС,ȡ��Խ��ϸ
	 * 
	 * @return JqGraphicsData
	 */
	public static JqVertex getBallVertexData(float radius, float anglSpace) {
		// ��������list
		List<Float> vertexList = new ArrayList<Float>();
		// ������������list
		List<Float> textureList = new ArrayList<Float>();

		// ��ֱ�������
		float oneVerticalTex = 1 / (180f / anglSpace);
		// ˮƽ�������
		float oneHorizontalTex = 1 / (360f / anglSpace);

		for (float horizontal = 90, horiTex = 0; horizontal >= -90; horizontal -= anglSpace, horiTex += oneVerticalTex) {
			for (float vertical = 0, vertTex = 0; vertical <= 360; vertical += anglSpace, vertTex += oneHorizontalTex) {

				// 0
				float x0 = (float) (radius
						* Math.cos(Math.toRadians(horizontal)) * Math.cos(Math
						.toRadians(vertical)));
				float y0 = (float) (radius
						* Math.cos(Math.toRadians(horizontal)) * Math.sin(Math
						.toRadians(vertical)));
				float z0 = (float) (radius * Math.sin(Math
						.toRadians(horizontal)));

				// 1
				float x1 = (float) (radius
						* Math.cos(Math.toRadians(horizontal)) * Math.cos(Math
						.toRadians(vertical + anglSpace)));
				float y1 = (float) (radius
						* Math.cos(Math.toRadians(horizontal)) * Math.sin(Math
						.toRadians(vertical + anglSpace)));
				float z1 = (float) (radius * Math.sin(Math
						.toRadians(horizontal)));

				// 2
				float x2 = (float) (radius
						* Math.cos(Math.toRadians(horizontal - anglSpace)) * Math
						.cos(Math.toRadians(vertical)));
				float y2 = (float) (radius
						* Math.cos(Math.toRadians(horizontal - anglSpace)) * Math
						.sin(Math.toRadians(vertical)));
				float z2 = (float) (radius * Math.sin(Math.toRadians(horizontal
						- anglSpace)));

				// 3
				float x3 = (float) (radius
						* Math.cos(Math.toRadians(horizontal - anglSpace)) * Math
						.cos(Math.toRadians(vertical + anglSpace)));
				float y3 = (float) (radius
						* Math.cos(Math.toRadians(horizontal - anglSpace)) * Math
						.sin(Math.toRadians(vertical + anglSpace)));
				float z3 = (float) (radius * Math.sin(Math.toRadians(horizontal
						- anglSpace)));

				vertexList.add(x0);
				vertexList.add(y0);
				vertexList.add(z0);

				// 1
				textureList.add(vertTex);
				textureList.add(horiTex);

				vertexList.add(x2);
				vertexList.add(y2);
				vertexList.add(z2);

				// 2
				textureList.add(vertTex);
				textureList.add(horiTex + oneVerticalTex);

				vertexList.add(x1);
				vertexList.add(y1);
				vertexList.add(z1);

				// 3
				textureList.add(vertTex + oneHorizontalTex);
				textureList.add(horiTex);

				vertexList.add(x1);
				vertexList.add(y1);
				vertexList.add(z1);

				// 4
				textureList.add(vertTex + oneHorizontalTex);
				textureList.add(horiTex);

				vertexList.add(x2);
				vertexList.add(y2);
				vertexList.add(z2);

				// 5
				textureList.add(vertTex);
				textureList.add(horiTex + oneVerticalTex);

				vertexList.add(x3);
				vertexList.add(y3);
				vertexList.add(z3);

				// 6
				textureList.add(vertTex + oneHorizontalTex);
				textureList.add(horiTex + oneVerticalTex);
			}
		}

		float[] vertex = new float[vertexList.size()];

		for (int i = 0; i < vertexList.size(); i++) {
			vertex[i] = vertexList.get(i);
		}

		float[] texTure = new float[textureList.size()];
		for (int i = 0; i < textureList.size(); i++) {
			texTure[i] = textureList.get(i);
		}
		return new JqVertex(vertex, vertex, texTure);
	}

}
