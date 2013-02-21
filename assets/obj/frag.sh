precision mediump float;

varying vec2 vTexture;//���ݸ�ƬԪ��ɫ������������
varying vec4 vDiffLight;//���ݸ�ƬԪ��ɫ����ɢ���ǿ��
varying vec4 vSpecLight;//�����ǿ��
varying vec4 vEnviLight;//������ǿ��

uniform sampler2D sTextureDay;//������������
//uniform sampler2D sTextureNight;//������������

void main()                         
{
	vec4 initColor = texture2D(sTextureDay, vTexture);
	vec4 tempColor = initColor * vDiffLight + initColor * vSpecLight + initColor * vEnviLight;
	gl_FragColor = tempColor;
}