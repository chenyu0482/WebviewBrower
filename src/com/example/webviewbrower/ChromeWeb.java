package com.example.webviewbrower;

import java.io.File;

import com.mogoweb.chrome.CookieManager;
import com.mogoweb.chrome.WebChromeClient;
import com.mogoweb.chrome.WebSettings;
import com.mogoweb.chrome.WebView;
import com.mogoweb.chrome.WebViewClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/*
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;*/
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChromeWeb extends Activity {

	protected WebView mywebView;
	private Button gotoview;
	private EditText urlText;
	private Handler wHandler=new Handler();
	private String url;
	private LinearLayout toolbuttons;
	private Bitmap mDefaultVideoPoster=null;
	private View mVideoProgressView = null;
	int mDensity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		
		setContentView(R.layout.chromeweblayout);
		mywebView=(WebView)findViewById(R.id.webView1);
		gotoview=(Button)findViewById(R.id.gotoweb);
		urlText=(EditText)findViewById(R.id.urltext);
		urlText.setText("http://qly.wlgj.kp179.com/");
		toolbuttons=(LinearLayout)findViewById(R.id.toolbuttons);
		//urlText.setText("http://61.187.51.109/demo/loginDemo.html");
		WebSettings webSettings= mywebView.getSettings(); // webView: 类WebView的实例
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
		webSettings.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
		webSettings.setUseWideViewPort(true);//可任意比例缩放
		webSettings.setJavaScriptEnabled(true);//设置WebView属性，能够执行Javascript脚本  
		webSettings.setPluginsEnabled(true);
		webSettings.setAllowFileAccess(true);//设置允许访问文件数据
		url="http://www.baidu.com/";
		webSettings.setSupportZoom(true);  
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDomStorageEnabled(true);
		mywebView.setWebContentsDebuggingEnabled(true);//启用调试		 
		mywebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); 
        mywebView.requestFocus(); 
		mywebView.setWebViewClient(new WebViewClient(){
			// 新开页面时用自己定义的 webview 来显示,不用系统自带的浏览器来显示
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // TODO Auto-generated method stub
			// 当有新连接时使用当前的 webview 进行显示 
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
			
		});
		mywebView.setWebChromeClient(new WebChromeClient()
		{
			@Override
			public Bitmap getDefaultVideoPoster() {
				// TODO Auto-generated method stub
				//return super.getDefaultVideoPoster();
				if (mDefaultVideoPoster == null) {
					mDefaultVideoPoster = BitmapFactory.decodeResource(getResources(), R.drawable.default_video_poster);
				}
				return mDefaultVideoPoster;
			}
			@Override
			public View getVideoLoadingProgressView() {
				// TODO Auto-generated method stub
				//return super.getVideoLoadingProgressView();
				if (mVideoProgressView == null) {
					LayoutInflater inflater = LayoutInflater.from(ChromeWeb.this);
					mVideoProgressView = inflater.inflate(R.layout.video_loading_progress, null);
				}
				return mVideoProgressView;
			}
		});
		mywebView.loadUrl(url); 
		gotoview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!urlText.getText().toString().equals(""))
					mywebView.loadUrl(urlText.getText().toString());
				else
					mywebView.loadUrl(url); 
				urlText.setVisibility(View.GONE);
				v.setVisibility(View.GONE);
				toolbuttons.setVisibility(View.GONE);
			}
		});
		//添加webview的javascriptInterface接口
		mywebView.addJavascriptInterface(new runCallJavaScript(), "android_js_login");
			
	}
	final class runCallJavaScript
	{
		public void loginSucc() {
			new AlertDialog.Builder(ChromeWeb.this).setTitle("Html5登陆").setMessage("登陆成功").setPositiveButton("确定", null).show(); 
		}
		public void loginError(){
			new AlertDialog.Builder(ChromeWeb.this).setTitle("Html5登陆").setMessage("登陆失败").setPositiveButton("确定", null).show(); 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override 
	//设置回退  
	//覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	      if ((keyCode == KeyEvent.KEYCODE_BACK) && mywebView.canGoBack()) {  
	           mywebView.goBack(); //goBack()表示返回WebView的上一页面
	           return true;  
	      }
	      return false;  
	} 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		else if(id==R.id.clear)
		{
			//mywebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
			//
			//清除cookie
			//CookieSyncManager.createInstance(this);  
			//CookieSyncManager.getInstance().startSync();  
			CookieManager.getInstance().removeAllCookie(); 
			//清除缓存
			mywebView.clearCache(true);
			mywebView.clearHistory();
			clearCacheFolder(this.getCacheDir(), System.currentTimeMillis());//删除此时之前的缓存.
			Toast.makeText(this, "缓存和cookie已被删除", Toast.LENGTH_SHORT).show();
			toolbuttons.setVisibility(View.VISIBLE);
			gotoview.setVisibility(View.VISIBLE);
			urlText.setVisibility(View.VISIBLE);
		}	
		else if(id==R.id.urlapi)
		{
			//urlText.setText("http://qly.wlgj.kp179.com/plugin.php?id=kp_qly:api");
			/*if(!urlText.getText().toString().equals(""))
				mywebView.loadUrl(urlText.getText().toString());
			else
				mywebView.loadUrl(url); */
			String urlString="http://qly.wlgj.kp179.com/plugin.php?id=kp_qly:api";
			if(!urlText.getText().toString().trim().equals("")&&urlText.getVisibility()==View.VISIBLE)
			{
				urlString+=urlText.getText().toString();
			}
			ProgressDialog progressDialog = ProgressDialog.show(this, "加载中", "...", true, false);
			new Webhttp(this, urlString,progressDialog).run();
		}
		return super.onOptionsItemSelected(item);
	}
	// clear the cache before time numDays  
	private int clearCacheFolder(File dir, long numDays) {        
	    int deletedFiles = 0;       
	    if (dir!= null && dir.isDirectory()) {           
	        try {              
	            for (File child:dir.listFiles()) {  
	                if (child.isDirectory()) {            
	                    deletedFiles += clearCacheFolder(child, numDays);        
	                }  
	                if (child.lastModified() < numDays) {   
	                    if (child.delete()) {                 
	                        deletedFiles++;         
	                    }  
	                }  
	            }           
	        } catch(Exception e) {     
	            e.printStackTrace();  
	        }   
	    }     
	    return deletedFiles;   
	}  
}
