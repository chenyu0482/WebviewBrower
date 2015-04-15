package com.example.webviewbrower;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Webhttp extends Handler{
	private Activity act;
	
	private String url;
	private ProgressDialog dialog;
	public Webhttp(Activity act,String url,ProgressDialog progressDialog)
	{
		this.act=act;
		this.url=url;
		this.dialog=progressDialog;
	}
	@Override
	public void handleMessage(Message msg)
	{
		dialog.dismiss();
		super.handleMessage(msg);
		String res = (String)msg.obj;
		if (res.equals("internetfailed"))
		{
			new AlertDialog.Builder(act).setTitle("网络不通").setMessage("网络不通").setPositiveButton("确定", null).show(); 
		}
		else
		{
			new AlertDialog.Builder(act).setTitle(url).setMessage(res).setPositiveButton("确定", null).show(); 
		}
	}
	public void run()
	{
		WebhttpLinker con = new WebhttpLinker(this,url);
		new Thread(con).start();
	}
}
class WebhttpLinker implements Runnable
{
	
	private Handler handler;
	private String urlString;
	public WebhttpLinker(Handler h,String url)
	{
		handler = h;
		urlString=url;
	}
	
	@Override
	public void run()
	{
		HttpGet httpRequest = new HttpGet(urlString);
		String res = "internetfailed";
		try
		{
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
				
			HttpResponse response = new DefaultHttpClient(params).execute(httpRequest);
			if (response.getStatusLine().getStatusCode() == 200)
				res = EntityUtils.toString(response.getEntity());
		}
		catch(Exception e){
		}
		Message msg = new Message();
		msg.obj = res;
		handler.sendMessage(msg);
	}
}