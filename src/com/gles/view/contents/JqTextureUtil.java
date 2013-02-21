package com.gles.view.contents;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * 纹理生成工具<br>
 * <p>
 * 注意点：使用到的纹理图片 宽与高必须为 2的n次方的尺寸的图片，不然可能会出现无法正常显示等问题！<br>
 * 
 * eg: 所使用的纹理的宽高像素值：2、4、16、32、64、128...2的N次方
 * 
 * @author qianjunping
 * 
 */
public class JqTextureUtil {

	private static JqTextureUtil mJqTextureUtil;
	private static Resources res;

	/**
	 * 注意点：使用到的纹理图片 宽与高必须为 2的n次方的尺寸的图片，不然可能会出现无法正常显示等问题！
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
	 * 生成2D纹理图
	 * 
	 * @param drawableId
	 *            图片资源Id
	 * @return
	 */
	public int create2DTexture(int drawableId) {

		// 生成纹理ID
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // 产生的纹理id的数量
				textures, // 纹理id的数组
				0 // 偏移量
		);
		int textureId = textures[0];
		// 将生成的空纹理绑定到当前2D纹理通道
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		// 所谓的默认即：s与t 轴拉伸方式的默认设置，MIN 与 MAG 采样方式的默认
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		// 拉伸的方式默认都为重复拉伸
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);

		Bitmap bitmapTmp = getBit(drawableId);

		// 将bitmap应用到2D纹理通道当前绑定的纹理中
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // 纹理类型
				0, // 纹理的层次，0表示基本图像层，可以理解为直接贴图
				bitmapTmp, // 纹理图像
				0 // 纹理边框尺寸
		);
		bitmapTmp.recycle(); // 纹理加载成功后释放图片
		return textureId;
	}

	public int create2DTexture(int drawableId, int minFilter, int magFilter,
			int glWrapS, int glWrapT) {
		// 生成纹理ID
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // 产生的纹理id的数量
				textures, // 纹理id的数组
				0 // 偏移量
		);
		int textureId = textures[0];
		// 将生成的空纹理绑定到当前2D纹理通道
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		// 所谓的默认即：s与t 轴拉伸方式的默认设置，MIN 与 MAG 采样方式的默认
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, minFilter);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, magFilter);
		// 拉伸的方式默认都为重复拉伸
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				glWrapS);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				glWrapT);

		Bitmap bitmapTmp = getBit(drawableId);
		// 将bitmap应用到2D纹理通道当前绑定的纹理中
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // 纹理类型
				0, // 纹理的层次，0表示基本图像层，可以理解为直接贴图
				bitmapTmp, // 纹理图像
				0 // 纹理边框尺寸
		);
		bitmapTmp.recycle(); // 纹理加载成功后释放图片
		return textureId;
	}

	/**
	 * 生成 MipMap 类型的纹理图，解决 近处模糊远处清晰的问题<br>
	 * 需要给定的图片，长跟宽相等
	 * 
	 * @param drawableId
	 * @return
	 */
	public int create2DMipMapTexture(int drawableId) {
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // 产生的纹理id的数量
				textures, // 纹理id的数组
				0 // 偏移量
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
		// 通过输入流加载图片
		Bitmap bitmapTmp = getBit(drawableId);
		// 实际加载纹理
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // 纹理类型，在OpenGL
													// ES中必须为GL10.GL_TEXTURE_2D
				0, // 纹理的层次，0表示基本图像层，可以理解为直接贴图
				bitmapTmp, // 纹理图像
				0 // 纹理边框尺寸
		);
		bitmapTmp.recycle(); // 纹理加载成功后释放图片

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
