package com.gles.view.loadutil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * ��������ͼ�����ɶ�Ӧ�ĵ���
 * 
 * @author qianjunping
 * 
 */
public class LoadPixelsUtil {

	/**
	 * @param resources
	 *            ͼƬ��Դ
	 * 
	 * @param drawableId
	 *            ����ͼ
	 * 
	 * @param highest
	 *            ��ߺ���
	 * 
	 * @param lowest
	 *            ��ͺ���
	 * 
	 * @param unit
	 *            һ����Ԫ��ĳ���
	 * 
	 * @return ObjData3D ��δ�ṩ�������������
	 */
	public static ObjData3D getLandObjFromPixels(Resources resources,
			int pixelsDrawableId, float highest, float lowest, float unit) {

		int cols, rows = 0;
		float[][] yArray = loadLandforms(resources, pixelsDrawableId, highest,
				lowest);
		cols = yArray.length - 1;
		rows = yArray[0].length - 1;
		// �����������ݵĳ�ʼ��
		int vCount = cols * rows * 2 * 3;// ÿ���������������Σ�ÿ��������3������
		float vertices[] = new float[vCount * 3];// ÿ������xyz��������
		int count = 0;// ���������
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				// ���㵱ǰ�������ϲ������
				float zsx = -unit * cols / 2 + i * unit;
				float zsz = -unit * rows / 2 + j * unit;

				vertices[count++] = zsx;
				vertices[count++] = yArray[j][i];
				vertices[count++] = zsz;

				vertices[count++] = zsx;
				vertices[count++] = yArray[j + 1][i];
				vertices[count++] = zsz + unit;

				vertices[count++] = zsx + unit;
				vertices[count++] = yArray[j][i + 1];
				vertices[count++] = zsz;

				vertices[count++] = zsx + unit;
				vertices[count++] = yArray[j][i + 1];
				vertices[count++] = zsz;

				vertices[count++] = zsx;
				vertices[count++] = yArray[j + 1][i];
				vertices[count++] = zsz + unit;

				vertices[count++] = zsx + unit;
				vertices[count++] = yArray[j + 1][i + 1];
				vertices[count++] = zsz + unit;
			}
		}

		// ���������������ݵĳ�ʼ��
		float[] texCoor = generateTexCoor(cols, rows);

		ObjData3D data = new ObjData3D();
		data.setVertex(vertices);
		data.setTexture(texCoor);

		return data;
	}

	/**
	 * @param resources
	 *            ͼƬ��Դ
	 * @param drawableId
	 *            ����ͼ
	 * @param highest
	 *            ��ߺ���
	 * @param lowest
	 *            ��ͺ���
	 * 
	 * @return �������ݵĶ�ά����
	 */
	private static float[][] loadLandforms(Resources resources, int drawableId,
			float highest, float lowest) {

		Bitmap bt = BitmapFactory.decodeResource(resources, drawableId);
		int colsPlusOne = bt.getWidth();
		int rowsPlusOne = bt.getHeight();
		float[][] result = new float[rowsPlusOne][colsPlusOne];
		for (int i = 0; i < rowsPlusOne; i++) {
			for (int j = 0; j < colsPlusOne; j++) {
				int color = bt.getPixel(j, i);
				int r = Color.red(color);
				int g = Color.green(color);
				int b = Color.blue(color);
				int px = (r + g + b) / 3;
				result[i][j] = (px * (highest - lowest)) / 255 + lowest;
			}
		}
		if (bt != null) {
			bt.recycle();
			bt = null;
		}
		return result;
	}

	// �Զ��з����������������ķ���
	private static float[] generateTexCoor(int bw, int bh) {
		float[] result = new float[bw * bh * 6 * 2];
		float sizew = 16.0f / bw;// ����
		float sizeh = 16.0f / bh;// ����
		int c = 0;
		for (int i = 0; i < bh; i++) {
			for (int j = 0; j < bw; j++) {
				// ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
				float s = j * sizew;
				float t = i * sizeh;

				result[c++] = s;
				result[c++] = t;

				result[c++] = s;
				result[c++] = t + sizeh;

				result[c++] = s + sizew;
				result[c++] = t;

				result[c++] = s + sizew;
				result[c++] = t;

				result[c++] = s;
				result[c++] = t + sizeh;

				result[c++] = s + sizew;
				result[c++] = t + sizeh;
			}
		}
		return result;
	}
}
