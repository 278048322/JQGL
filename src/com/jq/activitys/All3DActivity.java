package com.jq.activitys;

/**
 * 2013-1-30
 * author:wangjunbao
 */
import java.util.ArrayList;
import java.util.List;

import com.example.jqglstudy_1.R;
import com.gles.view.scene.JqScene;
import com.jq.example.ball.BallScene;
import com.jq.example.cube.CubeSurface;
import com.jq.example.loadobj.LoadScene;
import com.jq.example.texture.SimpleTexScene;
import com.jq.example.widgets.MountainScene;
import com.jq.example.widgets.SignboardScene;
import com.jq.example.widgets.SkyScene;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class All3DActivity extends Activity {

	private String TAG = "All3DActivity";
	private ListView mIPNLV;
	private IPNAdapter mIpnAdapter;
	private List<DemoBean> mAll3DList = new ArrayList<DemoBean>();
	private int mCurrent = -1; // ����һ����ʾλ��������ʾ�Ƿ��˳�
	private GLSurfaceView mGLSurfaceView;
	// ��Ļ��Ӧ�Ŀ�Ⱥ͸߶�
	public static float WIDTH;
	public static float HEIGHT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		setContentView(R.layout.all_3d);
		initData();
		initView();
		mIpnAdapter = new IPNAdapter(this);
		if (mIpnAdapter != null) {
			mIPNLV.setAdapter(mIpnAdapter);
		}
		if (dm.widthPixels > dm.heightPixels) {
			WIDTH = dm.widthPixels;
			HEIGHT = dm.heightPixels;
		} else {
			WIDTH = dm.heightPixels;
			HEIGHT = dm.widthPixels;
		}
		initListener();
	}

	/*
	 * ��ʼ��View�ؼ�
	 */
	private void initView() {
		mIPNLV = (ListView) findViewById(R.id.lv_all);
	}

	/*
	 * ��ʼ������
	 */
	private void initData() {
		addIPN(new SimpleTexScene(this), "��X����ת������������");
		addIPN(new CubeSurface(this), "��ת��������");
		addIPN(new LoadScene(this), "�� 3DMAX ������صģ���X����ת�Ĳ��");
		addIPN(new BallScene(this), "��ת�ĵ����а����ҹ��ͬ�������Լ�����Ч��");
		addIPN(new SignboardScene(this), "��־���ʹ�ã�һ�������������ת��ͼƬ��ģ�����3D Ч���ɽ�ϵͳ����Դ");
		addIPN(new MountainScene(this), "��һ�Ŵ�����ͼƬ���ɵ�ɽ������");
		addIPN(new SkyScene(this), "��պв���");
	}

	/*
	 * ��������
	 */
	private void addIPN(JqScene sceneName, String content) {
		DemoBean demoBean = new DemoBean(sceneName, content);
		mAll3DList.add(demoBean);
	}

	/*
	 * ��ʼ����������
	 */
	private void initListener() {
		mIPNLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mCurrent = arg2;

				DemoBean db = (DemoBean) arg0.getAdapter().getItem(arg2);
				mGLSurfaceView = db.getSceneName();
				if (mGLSurfaceView != null) {
					setContentView(mGLSurfaceView);
					db.getSceneName().requestFocus();// ��ȡ����
					db.getSceneName().setFocusableInTouchMode(true);// ��s��Ϊ�ɴ���
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		switch (mCurrent) {
		case -1:
			break;
		default:
			mGLSurfaceView.onResume();
			break;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		switch (mCurrent) {
		case -1:
			break;
		default:
			mGLSurfaceView.onPause();
			break;
		}

	}

	private class IPNAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater mlInflater;

		public IPNAdapter(Context mContext) {
			super();
			this.mContext = mContext;
			mlInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAll3DList.size();
			// return 5;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mAll3DList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mlInflater.inflate(R.layout.all_3d_item, null);
				viewHolder.implepression = (TextView) convertView
						.findViewById(R.id.tv_implepression);
				viewHolder.content = (TextView) convertView
						.findViewById(R.id.tv_content);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			DemoBean demo = mAll3DList.get(position);
			viewHolder.implepression.setText(demo.getSceneName().getClass()
					.getName());
			viewHolder.content.setText(demo.getContent());
			return convertView;
		}

		private class ViewHolder {
			TextView implepression, content;
		}
	}

	/*
	 * ��д���ؿؼ��������б����ʾ (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		switch (mCurrent) {
		case -1:
			finish();
			break;
		default:
			mAll3DList.clear();
			setContentView(R.layout.all_3d);
			initData();
			initView();
			mIPNLV.setAdapter(new IPNAdapter(this));
			initListener();
			mCurrent = -1;
			break;
		}
	}

}
