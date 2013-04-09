package com.gles.view.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import android.content.res.Resources;
import android.util.Log;

import com.gles.view.base.JqVertex;
import com.gles.view.base.math.Normal;

/**
 * 加载 3DMax文件，生成3D物体
 * 
 * @author qianjunping
 * 
 */
public class JqLoadObjUtil {

	/**
	 * 求两个向量的叉积
	 * 
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return 向量在分个维度的分量
	 */
	public static float[] getCrossProduct(float x1, float y1, float z1,
			float x2, float y2, float z2) {
		// 求出两个矢量叉积矢量在XYZ轴的分量ABC
		float A = y1 * z2 - y2 * z1;
		float B = z1 * x2 - z2 * x1;
		float C = x1 * y2 - x2 * y1;

		return new float[] { A, B, C };
	}

	/**
	 * 向量规格化
	 * 
	 * @param vector
	 * @return
	 */
	public static float[] normailzeVector(float[] vector) {
		// 求向量的模
		float module = (float) Math.sqrt(vector[0] * vector[0] + vector[1]
				* vector[1] + vector[2] * vector[2]);
		return new float[] { vector[0] / module, vector[1] / module,
				vector[2] / module };
	}

	/**
	 * 从obj文件中加载携带顶点信息的物体<br>
	 * 
	 * 注意：面的发向量与顶点发向量以及平均法向量的问题<br>
	 * 
	 * 顶点法向量适合于棱角分明的物体，可以直接使用<br>
	 * <P>
	 * 
	 * 将纹理坐标组织到结果纹理坐标列表中<br>
	 * 情况明细：<br>
	 * 1.只有顶点数据 f 3 2 5 <br>
	 * 2.有顶点跟纹理数据 f 3/2 4/5 5/6 <br>
	 * 3.有顶点跟法向量数据 f 3//2 6//7 4//2 <br>
	 * 4.有所有的数据 f 3/1/2 6/4/7 5/1/2 <br>
	 * 
	 * @param objPath
	 * @param r
	 * @return
	 */
	public static JqVertex loadObjFromAssets(String objPath, Resources r) {

		JqVertex obj = null;

		// 原始顶点坐标列表,直接从obj文件中加载的 V开头的行
		ArrayList<Float> objFileV = new ArrayList<Float>();
		// 结果顶点坐标列表，按面组织好
		ArrayList<Float> objResultV = new ArrayList<Float>();

		// 原始纹理坐标列表
		ArrayList<Float> objFileTex = new ArrayList<Float>();
		// 纹理坐标结果列表
		ArrayList<Float> objResultTex = new ArrayList<Float>();

		// 记录每个页面的顶点索引
		ArrayList<Integer> objFileFaceVertex = new ArrayList<Integer>();

		// 平均前各个索引对应的点的法向量集合Map
		// 此HashMap的key为点的索引， value为点所在的各个面的法向量的集合
		HashMap<Integer, HashSet<Normal>> hmn = new HashMap<Integer, HashSet<Normal>>();

		try {
			String temps = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(r
					.getAssets().open(objPath)));
			while ((temps = br.readLine()) != null) {
				/**
				 * 用空格分割行中的各个组成部分 <br>
				 * eg: <br>
				 * v 10.8926 21.5745 -0.2146 <br>
				 * vn -0.9667 -0.2558 -0.0000 <br>
				 * vt 7.1140 10.6710 0.0000 <br>
				 * f 3/3/3 12/12/12 13/13/13 <br>
				 */
				// 正则表达式 [ ]+,n取一,当前只有空格
				String[] tempsa = temps.split("[ ]+");
				if ("v".equals(tempsa[0].trim())) {
					// 此行为顶点左边行
					objFileV.add(Float.parseFloat(tempsa[1]));
					objFileV.add(Float.parseFloat(tempsa[2]));
					objFileV.add(Float.parseFloat(tempsa[3]));
				} else if ("vt".equals(tempsa[0].trim())) {
					// 此行为纹理坐标行
					objFileTex.add(Float.parseFloat(tempsa[1]) / 2.0f);
					objFileTex.add(Float.parseFloat(tempsa[2]) / 2.0f);
				} else if ("f".equals(tempsa[0].trim())) {
					// 此行为三角形面
					/*
					 * 若为三角形面行则根据 组成面的顶点的索引从原始顶点坐标列表中
					 * 提取相应的顶点坐标值添加到结果顶点坐标列表中，同时根据三个
					 * 顶点的坐标计算出此面的法向量并添加到平均前各个索引对应的点的法向量集合组成的Map中
					 */
					// String[] tempsa = {"f","3/3/3","12/12/12","13/13/13"};
					int[] index = new int[3];// 三个顶点索引值的数组
					// index = {3,12,13};
					// 计算第0个顶点的索引，并获取此顶点的XYZ三个坐标
					index[0] = Integer.parseInt(tempsa[1].split("/")[0]) - 1;
					float x0 = objFileV.get(3 * index[0]);
					float y0 = objFileV.get(3 * index[0] + 1);
					float z0 = objFileV.get(3 * index[0] + 2);
					objResultV.add(x0);
					objResultV.add(y0);
					objResultV.add(z0);

					// 计算第1个顶点的索引，并获取此顶点的XYZ三个坐标
					index[1] = Integer.parseInt(tempsa[2].split("/")[0]) - 1;
					float x1 = objFileV.get(3 * index[1]);
					float y1 = objFileV.get(3 * index[1] + 1);
					float z1 = objFileV.get(3 * index[1] + 2);
					objResultV.add(x1);
					objResultV.add(y1);
					objResultV.add(z1);

					// 计算第2个顶点的索引，并获取此顶点的XYZ三个坐标
					index[2] = Integer.parseInt(tempsa[3].split("/")[0]) - 1;
					float x2 = objFileV.get(3 * index[2]);
					float y2 = objFileV.get(3 * index[2] + 1);
					float z2 = objFileV.get(3 * index[2] + 2);
					objResultV.add(x2);
					objResultV.add(y2);
					objResultV.add(z2);
					// 顶点坐标统计完毕 //

					// 记录此面的顶点索引
					objFileFaceVertex.add(index[0]);
					objFileFaceVertex.add(index[1]);
					objFileFaceVertex.add(index[2]);

					// 通过三角形面两个边向量0-1，0-2求叉积得到此面的法向量
					// 求0号点到1号点的向量
					float vxa = x1 - x0;
					float vya = y1 - y0;
					float vza = z1 - z0;
					// 求0号点到2号点的向量
					float vxb = x2 - x0;
					float vyb = y2 - y0;
					float vzb = z2 - z0;
					// 通过求两个向量的叉积计算法向量
					float[] vNormal = normailzeVector(getCrossProduct(vxa, vya,
							vza, vxb, vyb, vzb));
					for (int tempInxex : index) {
						// 遍历当前页面的 3 个顶点索引
						HashSet<Normal> hsn = hmn.get(tempInxex);
						if (hsn == null) {
							hsn = new HashSet<Normal>();
						}
						// 将此点的法向量添加到集合中
						// 由于Normal类重写了equals方法，因此同样的法向量不会重复出现在此点
						// 对应的法向量集合中
						hsn.add(new Normal(vNormal[0], vNormal[1], vNormal[2]));
						// 根据顶点索引 将当前顶点的面法向量保存起来,3个顶点使用的同一个面法向量
						hmn.put(tempInxex, hsn);
					}

					/**
					 * 将纹理坐标组织到结果纹理坐标列表中<br>
					 * 情况明细：<br>
					 * 1.只有顶点数据 f 3 2 5 <br>
					 * 2.有顶点跟纹理数据 f 3/2 4/5 5/6 <br>
					 * 3.有顶点跟法向量数据 f 3//2 6//7 4//2 <br>
					 * 4.有所有的数据 f 3/1/2 6/4/7 5/1/2 <br>
					 */
					if (tempsa[1].split("/").length > 1
							&& !tempsa[1].split("/")[1].equals("")) {
						int indexTex = Integer
								.parseInt(tempsa[1].split("/")[1]) - 1;
						objResultTex.add(objFileTex.get(indexTex * 2));
						objResultTex.add(objFileTex.get(indexTex * 2 + 1));
						// 第1个顶点的纹理坐标
						indexTex = Integer.parseInt(tempsa[2].split("/")[1]) - 1;
						objResultTex.add(objFileTex.get(indexTex * 2));
						objResultTex.add(objFileTex.get(indexTex * 2 + 1));
						// 第2个顶点的纹理坐标
						indexTex = Integer.parseInt(tempsa[3].split("/")[1]) - 1;
						objResultTex.add(objFileTex.get(indexTex * 2));
						objResultTex.add(objFileTex.get(indexTex * 2 + 1));
					}
				}
			}

			// 生成顶点数组
			int size = objResultV.size();
			float[] vXYZ = new float[size];
			for (int i = 0; i < size; i++) {
				vXYZ[i] = objResultV.get(i);
			}

			// 生成法向量数组，一个向量有 XYZ 3个分量
			float[] nXYZ = new float[objFileFaceVertex.size() * 3];
			int c = 0;
			for (Integer i : objFileFaceVertex) {
				// 根据当前点的索引从Map中取出一个法向量的集合
				HashSet<Normal> hsn = hmn.get(i);
				// 求出平均法向量
				float[] tn = Normal.getAverage(hsn);
				// 将计算出的平均法向量存放到法向量数组中
				nXYZ[c++] = tn[0];
				nXYZ[c++] = tn[1];
				nXYZ[c++] = tn[2];
			}

			// 生成纹理数组
			size = objResultTex.size();
			float[] tST = new float[size];
			for (int i = 0; i < size; i++) {
				tST[i] = objResultTex.get(i);
			}

			// 创建3D物体对象
			obj = new JqVertex(vXYZ, nXYZ, tST);
		} catch (Exception e) {
			Log.e("load error", "load error");
			e.printStackTrace();
		}
		return obj;
	}
}
