package com.gles.view.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.gles.view.base.JqVertex;

/**
 * 导入像素图像生成对应的地形
 * 
 * @author qianjunping
 * 
 */
public class JqLoadPixelsUtil {

	/**
	 * @param resources
	 *            图片资源
	 * 
	 * @param drawableId
	 *            像素图
	 * 
	 * @param highest
	 *            最高海拔
	 * 
	 * @param lowest
	 *            最低海拔
	 * 
	 * @param unit
	 *            一个单元格的长度
	 * 
	 * @return ObjData3D 暂未提供法向量相关数据
	 */
	public static JqVertex getLandObjFromPixels(Resources resources,
			int pixelsDrawableId, float highest, float lowest, float unit) {

		int cols, rows = 0;
		float[][] yArray = loadLandforms(resources, pixelsDrawableId, highest,
				lowest);
		cols = yArray.length - 1;
		rows = yArray[0].length - 1;
		// 顶点坐标数据的初始化
		int vCount = cols * rows * 2 * 3;// 每个格子两个三角形，每个三角形3个顶点
		float vertices[] = new float[vCount * 3];// 每个顶点xyz三个坐标
		int count = 0;// 顶点计数器
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				// 计算当前格子左上侧点坐标
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

		// 顶点纹理坐标数据的初始化
		float[] texCoor = JqTextureUtil.generateTexCoor(cols, rows, 16, 16);

		JqVertex data = new JqVertex();
		data.setVertex(vertices);
		data.setTexture(texCoor);

		return data;
	}

	/**
	 * @param resources
	 *            图片资源
	 * @param drawableId
	 *            像素图
	 * @param highest
	 *            最高海拔
	 * @param lowest
	 *            最低海拔
	 * 
	 * @return 像素数据的二维数组
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

}
