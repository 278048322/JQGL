uniform mat4 sMVPMatrix; //总变换矩阵
attribute vec3 sPosition;  //顶点位置
attribute vec4 aColor;    //顶点颜色
varying  vec4 vColor;  //用于传递给片元着色器的变量
void main()  {                            		
   gl_Position = sMVPMatrix * vec4(sPosition,1); //根据总变换矩阵计算此次绘制此顶点位置
   vColor = aColor;//将接收的颜色传递给片元着色器
}                      