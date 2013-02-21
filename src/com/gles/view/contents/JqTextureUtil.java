package com.gles.view.contents;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * �������ɹ���<br>
 * <p>
 * ע��㣺ʹ�õ�������ͼƬ ����߱���Ϊ 2��n�η��ĳߴ��ͼƬ����Ȼ���ܻ�����޷�������ʾ�����⣡<br>
 * 
 * eg: ��ʹ�õ�����Ŀ������ֵ��2��4��16��32��64��128...2��N�η�
 * 
 * @author qianjunping
 * 
 */
public class JqTextureUtil {

	private static JqTextureUtil mJqTextureUtil;
	private static Resources res;

	/**
	 * ע��㣺ʹ�õ�������ͼƬ ����߱���Ϊ 2��n�η��ĳߴ��ͼƬ����Ȼ���ܻ�����޷�������ʾ�����⣡
	 * 
	 * @param res
	 * @return
	 */
	public static JqTextureUtil getInstance(Resources res) {
		JqTextureUtil.res = res;
		if (mJqTextureUtil == null) {
			mJqTextureUtil = new JqTextureUtil();
		}
		return mJqTextureUtil;
	}

	/**
	 * ����2D����ͼ
	 * 
	 * @param drawableId
	 *            ͼƬ��ԴId
	 * @return
	 */
	public int create2DTexture(int drawableId) {

		// ��������ID
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // ����������id������
				textures, // ����id������
				0 // ƫ����
		);
		int textureId = textures[0];
		// �����ɵĿ�����󶨵���ǰ2D����ͨ��
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		// ��ν��Ĭ�ϼ���s��t �����췽ʽ��Ĭ�����ã�MIN �� MAG ������ʽ��Ĭ��
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		// ����ķ�ʽĬ�϶�Ϊ�ظ�����
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);

		Bitmap bitmapTmp = getBit(drawableId);

		// ��bitmapӦ�õ�2D����ͨ����ǰ�󶨵�������
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // ��������
				0, // ����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
				bitmapTmp, // ����ͼ��
				0 // ����߿�ߴ�
		);
		bitmapTmp.recycle(); // ������سɹ����ͷ�ͼƬ
		return textureId;
	}

	public int create2DTexture(int drawableId, int minFilter, int magFilter,
			int glWrapS, int glWrapT) {
		// ��������ID
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // ����������id������
				textures, // ����id������
				0 // ƫ����
		);
		int textureId = textures[0];
		// �����ɵĿ�����󶨵���ǰ2D����ͨ��
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		// ��ν��Ĭ�ϼ���s��t �����췽ʽ��Ĭ�����ã�MIN �� MAG ������ʽ��Ĭ��
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, minFilter);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, magFilter);
		// ����ķ�ʽĬ�϶�Ϊ�ظ�����
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				glWrapS);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				glWrapT);

		Bitmap bitmapTmp = getBit(drawableId);
		// ��bitmapӦ�õ�2D����ͨ����ǰ�󶨵�������
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // ��������
				0, // ����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
				bitmapTmp, // ����ͼ��
				0 // ����߿�ߴ�
		);
		bitmapTmp.recycle(); // ������سɹ����ͷ�ͼƬ
		return textureId;
	}

	/**
	 * ���� MipMap ���͵�����ͼ����� ����ģ��Զ������������<br>
	 * ��Ҫ������ͼƬ�����������
	 * 
	 * @param drawableId
	 * @return
	 */
	public int create2DMipMapTexture(int drawableId) {
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // ����������id������
				textures, // ����id������
				0 // ƫ����
		);
		int textureId = textures[0];
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST_MIPMAP_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);
		// ͨ������������ͼƬ
		Bitmap bitmapTmp = getBit(drawableId);
		// ʵ�ʼ�������
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // �������ͣ���OpenGL
													// ES�б���ΪGL10.GL_TEXTURE_2D
				0, // ����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
				bitmapTmp, // ����ͼ��
				0 // ����߿�ߴ�
		);
		bitmapTmp.recycle(); // ������سɹ����ͷ�ͼƬ

		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
		return textureId;
	}

	private Bitmap getBit(int drawableId) {

		Bitmap bitmapTmp = null;
		InputStream is = res.openRawResource(drawableId);
		try {
			bitmapTmp = BitmapFactory.decodeStream(is);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmapTmp;
	}

	private JqTextureUtil() {

	}

}
