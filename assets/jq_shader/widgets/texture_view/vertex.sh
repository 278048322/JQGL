uniform mat4 sMVPMatrix; //�ܱ任����
attribute vec3 sPosition;  //����λ��
attribute vec2 sTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
void main()     
{                            		
   gl_Position = sMVPMatrix * vec4(sPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vTextureCoord = sTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}                      