uniform mat4 sMVPMatrix; //总变换矩阵
attribute vec3 sPosition;  //顶点位置
attribute vec2 aTexture;//顶点纹理坐标
varying vec2 vTexture;

void main()     
{                            		
   gl_Position = sMVPMatrix * vec4(sPosition,1); //根据总变换矩阵计算此次绘制此顶点位置
   vTexture = aTexture;
}                      