package com.android_servicepractice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.*;
import java.net.*;

public class DownloadService extends Service
{
	private final String HTTP_URL = "http://118.169.189.207/music/01.mp3";
	
	@Override
	public void onCreate() {
		// TODO �۰ʲ��ͪ���k Stub
		DownloadTask download = new DownloadTask();
		download.execute(HTTP_URL);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO �۰ʲ��ͪ���k Stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO �۰ʲ��ͪ���k Stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO �۰ʲ��ͪ���k Stub
		return null;
	}
	
	public class DownloadTask extends AsyncTask<String, Void, Void>
	{
		@Override
		protected Void doInBackground(String... params) {
			// TODO �۰ʲ��ͪ���k Stub
			URL url = null;
			try {
				url = new URL(params[0]);
			} catch (MalformedURLException e) {
				// TODO �۰ʲ��ͪ� catch �϶�
				Log.d("Message", "MalformedURLException~~!!" + e.getMessage());
			}
			try {
				HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
				urlConn.setRequestMethod("POST");
				urlConn.setDoInput(true);
				urlConn.setConnectTimeout(10000);
				InputStream is = urlConn.getInputStream();
				File file = new File(android.os.Environment.getExternalStorageDirectory()+"/Download/01.mp3");
				//if(!file.exists()) file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] data = new byte[1024];
				int totalSize = 0,getPerSize = 0;
				while( (getPerSize = is.read(data)) != -1)
				{
					totalSize += getPerSize;
					fos.write(data,0,getPerSize);
				}
				Log.d("Message", "�U������~~!!");
				fos.flush(); fos.close();
				is.close(); urlConn.disconnect();
			} catch (IOException e) {
				// TODO �۰ʲ��ͪ� catch �϶�
				Log.d("Message", "IOException~~!!" + e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
	}
}
