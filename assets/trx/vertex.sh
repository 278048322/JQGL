uniform mat4 sMVPMatrix; //�ܱ任����
attribute vec3 sPosition;  //����λ��
attribute vec2 aTexture;//������������
varying vec2 vTexture;

void main()     
{                            		
   gl_Position = sMVPMatrix * vec4(sPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vTexture = aTexture;
}                      