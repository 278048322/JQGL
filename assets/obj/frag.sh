precision mediump float;

varying vec2 vTexture;//传递给片元着色器的纹理坐标
varying vec4 vDiffLight;//传递给片元着色器的散射光强度
varying vec4 vSpecLight;//镜面光强度
varying vec4 vEnviLight;//环境光强度

uniform sampler2D sTextureDay;//纹理内容数据
//uniform sampler2D sTextureNight;//纹理内容数据

void main()                         
{
	vec4 initColor = texture2D(sTextureDay, vTexture);
	vec4 tempColor = initColor * vDiffLight + initColor * vSpecLight + initColor * vEnviLight;
	gl_FragColor = tempColor;
}