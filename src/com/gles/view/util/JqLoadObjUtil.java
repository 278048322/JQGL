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
 * ���� 3DMax�ļ�������3D����
 * 
 * @author qianjunping
 * 
 */
public class JqLoadObjUtil {

	/**
	 * �����������Ĳ��
	 * 
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return �����ڷָ�ά�ȵķ���
	 */
	public static float[] getCrossProduct(float x1, float y1, float z1,
			float x2, float y2, float z2) {
		// �������ʸ�����ʸ����XYZ��ķ���ABC
		float A = y1 * z2 - y2 * z1;
		float B = z1 * x2 - z2 * x1;
		float C = x1 * y2 - x2 * y1;

		return new float[] { A, B, C };
	}

	/**
	 * �������
	 * 
	 * @param vector
	 * @return
	 */
	public static float[] normailzeVector(float[] vector) {
		// ��������ģ
		float module = (float) Math.sqrt(vector[0] * vector[0] + vector[1]
				* vector[1] + vector[2] * vector[2]);
		return new float[] { vector[0] / module, vector[1] / module,
				vector[2] / module };
	}

	/**
	 * ��obj�ļ��м���Я��������Ϣ������<br>
	 * 
	 * ע�⣺��ķ������붥�㷢�����Լ�ƽ��������������<br>
	 * 
	 * ���㷨�����ʺ�����Ƿ��������壬����ֱ��ʹ��<br>
	 * <P>
	 * 
	 * ������������֯��������������б���<br>
	 * �����ϸ��<br>
	 * 1.ֻ�ж������� f 3 2 5 <br>
	 * 2.�ж������������ f 3/2 4/5 5/6 <br>
	 * 3.�ж�������������� f 3//2 6//7 4//2 <br>
	 * 4.�����е����� f 3/1/2 6/4/7 5/1/2 <br>
	 * 
	 * @param objPath
	 * @param r
	 * @return
	 */
	public static JqVertex loadObjFromAssets(String objPath, Resources r) {

		JqVertex obj = null;

		// ԭʼ���������б�,ֱ�Ӵ�obj�ļ��м��ص� V��ͷ����
		ArrayList<Float> objFileV = new ArrayList<Float>();
		// ������������б�������֯��
		ArrayList<Float> objResultV = new ArrayList<Float>();

		// ԭʼ���������б�
		ArrayList<Float> objFileTex = new ArrayList<Float>();
		// �����������б�
		ArrayList<Float> objResultTex = new ArrayList<Float>();

		// ��¼ÿ��ҳ��Ķ�������
		ArrayList<Integer> objFileFaceVertex = new ArrayList<Integer>();

		// ƽ��ǰ����������Ӧ�ĵ�ķ���������Map
		// ��HashMap��keyΪ��������� valueΪ�����ڵĸ�����ķ������ļ���
		HashMap<Integer, HashSet<Normal>> hmn = new HashMap<Integer, HashSet<Normal>>();

		try {
			String temps = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(r
					.getAssets().open(objPath)));
			while ((temps = br.readLine()) != null) {
				/**
				 * �ÿո�ָ����еĸ�����ɲ��� <br>
				 * eg: <br>
				 * v 10.8926 21.5745 -0.2146 <br>
				 * vn -0.9667 -0.2558 -0.0000 <br>
				 * vt 7.1140 10.6710 0.0000 <br>
				 * f 3/3/3 12/12/12 13/13/13 <br>
				 */
				// ������ʽ [ ]+,nȡһ,��ǰֻ�пո�
				String[] tempsa = temps.split("[ ]+");
				if ("v".equals(tempsa[0].trim())) {
					// ����Ϊ���������
					objFileV.add(Float.parseFloat(tempsa[1]));
					objFileV.add(Float.parseFloat(tempsa[2]));
					objFileV.add(Float.parseFloat(tempsa[3]));
				} else if ("vt".equals(tempsa[0].trim())) {
					// ����Ϊ����������
					objFileTex.add(Float.parseFloat(tempsa[1]) / 2.0f);
					objFileTex.add(Float.parseFloat(tempsa[2]) / 2.0f);
				} else if ("f".equals(tempsa[0].trim())) {
					// ����Ϊ��������
					/*
					 * ��Ϊ��������������� �����Ķ����������ԭʼ���������б���
					 * ��ȡ��Ӧ�Ķ�������ֵ��ӵ�������������б��У�ͬʱ��������
					 * �����������������ķ���������ӵ�ƽ��ǰ����������Ӧ�ĵ�ķ�����������ɵ�Map��
					 */
					// String[] tempsa = {"f","3/3/3","12/12/12","13/13/13"};
					int[] index = new int[3];// ������������ֵ������
					// index = {3,12,13};
					// �����0�����������������ȡ�˶����XYZ��������
					index[0] = Integer.parseInt(tempsa[1].split("/")[0]) - 1;
					float x0 = objFileV.get(3 * index[0]);
					float y0 = objFileV.get(3 * index[0] + 1);
					float z0 = objFileV.get(3 * index[0] + 2);
					objResultV.add(x0);
					objResultV.add(y0);
					objResultV.add(z0);

					// �����1�����������������ȡ�˶����XYZ��������
					index[1] = Integer.parseInt(tempsa[2].split("/")[0]) - 1;
					float x1 = objFileV.get(3 * index[1]);
					float y1 = objFileV.get(3 * index[1] + 1);
					float z1 = objFileV.get(3 * index[1] + 2);
					objResultV.add(x1);
					objResultV.add(y1);
					objResultV.add(z1);

					// �����2�����������������ȡ�˶����XYZ��������
					index[2] = Integer.parseInt(tempsa[3].split("/")[0]) - 1;
					float x2 = objFileV.get(3 * index[2]);
					float y2 = objFileV.get(3 * index[2] + 1);
					float z2 = objFileV.get(3 * index[2] + 2);
					objResultV.add(x2);
					objResultV.add(y2);
					objResultV.add(z2);
					// ��������ͳ����� //

					// ��¼����Ķ�������
					objFileFaceVertex.add(index[0]);
					objFileFaceVertex.add(index[1]);
					objFileFaceVertex.add(index[2]);

					// ͨ��������������������0-1��0-2�����õ�����ķ�����
					// ��0�ŵ㵽1�ŵ������
					float vxa = x1 - x0;
					float vya = y1 - y0;
					float vza = z1 - z0;
					// ��0�ŵ㵽2�ŵ������
					float vxb = x2 - x0;
					float vyb = y2 - y0;
					float vzb = z2 - z0;
					// ͨ�������������Ĳ�����㷨����
					float[] vNormal = normailzeVector(getCrossProduct(vxa, vya,
							vza, vxb, vyb, vzb));
					for (int tempInxex : index) {
						// ������ǰҳ��� 3 ����������
						HashSet<Normal> hsn = hmn.get(tempInxex);
						if (hsn == null) {
							hsn = new HashSet<Normal>();
						}
						// ���˵�ķ�������ӵ�������
						// ����Normal����д��equals���������ͬ���ķ����������ظ������ڴ˵�
						// ��Ӧ�ķ�����������
						hsn.add(new Normal(vNormal[0], vNormal[1], vNormal[2]));
						// ���ݶ������� ����ǰ������淨������������,3������ʹ�õ�ͬһ���淨����
						hmn.put(tempInxex, hsn);
					}

					/**
					 * ������������֯��������������б���<br>
					 * �����ϸ��<br>
					 * 1.ֻ�ж������� f 3 2 5 <br>
					 * 2.�ж������������ f 3/2 4/5 5/6 <br>
					 * 3.�ж�������������� f 3//2 6//7 4//2 <br>
					 * 4.�����е����� f 3/1/2 6/4/7 5/1/2 <br>
					 */
					if (tempsa[1].split("/").length > 1
							&& !tempsa[1].split("/")[1].equals("")) {
						int indexTex = Integer
								.parseInt(tempsa[1].split("/")[1]) - 1;
						objResultTex.add(objFileTex.get(indexTex * 2));
						objResultTex.add(objFileTex.get(indexTex * 2 + 1));
						// ��1���������������
						indexTex = Integer.parseInt(tempsa[2].split("/")[1]) - 1;
						objResultTex.add(objFileTex.get(indexTex * 2));
						objResultTex.add(objFileTex.get(indexTex * 2 + 1));
						// ��2���������������
						indexTex = Integer.parseInt(tempsa[3].split("/")[1]) - 1;
						objResultTex.add(objFileTex.get(indexTex * 2));
						objResultTex.add(objFileTex.get(indexTex * 2 + 1));
					}
				}
			}

			// ���ɶ�������
			int size = objResultV.size();
			float[] vXYZ = new float[size];
			for (int i = 0; i < size; i++) {
				vXYZ[i] = objResultV.get(i);
			}

			// ���ɷ��������飬һ�������� XYZ 3������
			float[] nXYZ = new float[objFileFaceVertex.size() * 3];
			int c = 0;
			for (Integer i : objFileFaceVertex) {
				// ���ݵ�ǰ���������Map��ȡ��һ���������ļ���
				HashSet<Normal> hsn = hmn.get(i);
				// ���ƽ��������
				float[] tn = Normal.getAverage(hsn);
				// ���������ƽ����������ŵ�������������
				nXYZ[c++] = tn[0];
				nXYZ[c++] = tn[1];
				nXYZ[c++] = tn[2];
			}

			// ������������
			size = objResultTex.size();
			float[] tST = new float[size];
			for (int i = 0; i < size; i++) {
				tST[i] = objResultTex.get(i);
			}

			// ����3D�������
			obj = new JqVertex(vXYZ, nXYZ, tST);
		} catch (Exception e) {
			Log.e("load error", "load error");
			e.printStackTrace();
		}
		return obj;
	}
}
