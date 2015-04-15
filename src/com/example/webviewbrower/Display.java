package com.example.webviewbrower;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends Activity implements OnPageChangeListener{
	private ViewPager vp;
	private List<View> views;
	private ViewPagerAdapter vpAdapter;
	private int currentIndex;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		initViews();
		/*Button button=(Button)findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vp.setCurrentItem(5, true);
			}
		});*/
	}
	private void initViews()
	{
			LayoutInflater inflater = LayoutInflater.from(this);
			views = new ArrayList<View>();
			views.add(inflater.inflate(R.layout.diaplayitem, null));
			views.add(inflater.inflate(R.layout.diaplayitem, null));
			views.add(inflater.inflate(R.layout.diaplayitem, null));
			views.add(inflater.inflate(R.layout.diaplayitem, null));
			views.add(inflater.inflate(R.layout.diaplayitem, null));
			vpAdapter = new ViewPagerAdapter(views, this);// ��ʼ��Adapter
			vp = (ViewPager) findViewById(R.id.viewpagerLayout);
			vp.setAdapter(vpAdapter);
			vp.setOnPageChangeListener(this);
			currentIndex=0;
	}
	
	private class ViewPagerAdapter extends PagerAdapter{
		private List<View> views;
		private Activity activity;
		
		public ViewPagerAdapter(List<View> views, Activity activity)
		{
			this.views = views;
			this.activity = activity;
		}

		// ���arg1λ�õĽ���
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2)
		{
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0)
		{
		}

		// ��õ�ǰ������
		@Override
		public int getCount()
		{
			if (views != null) return views.size();
			return 0;
		}

		// ��ʼ��arg1λ�õĽ���
		@Override
		public Object instantiateItem(View arg0, int arg1)
		{
			((ViewPager)arg0).addView(views.get(arg1),0);
			
			//Button start=(Button)arg0.findViewById(R.id.guide_end);
			TextView button1=(TextView)arg0.findViewById(R.id.viewdetail);
			final int pageindex=arg1;
			button1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(activity, "第"+String.valueOf(pageindex)+"页", Toast.LENGTH_LONG).show();
				}
			});
			return views.get(arg1);
		}
		
		// �ж��Ƿ��ɶ�����ɽ���
		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return (arg0==arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1)
		{
		}

		@Override
		public Parcelable saveState()
		{
			return null;
		}

		@Override
		public void startUpdate(View arg0)
		{
		}
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		currentIndex=arg0;
		Toast.makeText(this, "已经滑倒了第"+String.valueOf(currentIndex)+"页", Toast.LENGTH_LONG).show();
		
	}

}
