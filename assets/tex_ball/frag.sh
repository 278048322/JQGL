precision mediump float;
varying vec2 vTexture;//��������
varying vec4 diffLight;//ɢ���ǿ��
varying vec4 specLight;//�����ǿ��
varying vec4 envi;//������ǿ��

uniform sampler2D sTextureDay;//������������
uniform sampler2D sTextureNight;//������������
void main()                         
{  
  //����ƬԪ�������в�������ɫֵ   
  vec4 finalColorDay;   
  vec4 finalColorNight;   
  
  finalColorDay= texture2D(sTextureDay, vTexture);
  finalColorDay = finalColorDay* diffLight +finalColorDay* specLight +finalColorDay * envi;
  finalColorNight = texture2D(sTextureNight, vTexture); 
  finalColorNight = finalColorNight*vec4(0.5,0.5,0.5,1.0);
  
  if(diffLight.x>0.21)
  {
    gl_FragColor=finalColorDay;    
  } 
  else if(diffLight.x<0.05)
  {     
     gl_FragColor=finalColorNight;
  }
  else
  {
     float t=(diffLight.x-0.05)/0.16;
     gl_FragColor=t*finalColorDay+(1.0-t)*finalColorNight;
  }  
}