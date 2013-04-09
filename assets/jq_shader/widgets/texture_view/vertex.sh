uniform mat4 sMVPMatrix; //总变换矩阵
attribute vec3 sPosition;  //顶点位置
attribute vec2 sTexCoor;    //顶点纹理坐标
varying vec2 vTextureCoord;  //用于传递给片元着色器的变量
void main()     
{                            		
   gl_Position = sMVPMatrix * vec4(sPosition,1); //根据总变换矩阵计算此次绘制此顶点位置
   vTextureCoord = sTexCoor;//将接收的纹理坐标传递给片元着色器
}                      