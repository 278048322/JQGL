uniform mat4 sMVPMatrix; //�ܱ任����
attribute vec3 sPosition;  //����λ��
attribute vec4 aColor;    //������ɫ
varying  vec4 vColor;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
void main()  {                            		
   gl_Position = sMVPMatrix * vec4(sPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vColor = aColor;//�����յ���ɫ���ݸ�ƬԪ��ɫ��
}                      