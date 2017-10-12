package com.zlz.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class HttpUtils {
	private static Thread requestThread;
	public interface getResultCallback {
		public void getMessage(String message);
	}

	public static void getPostResult(final String path,
			final Map<String, String> params,
			final getResultCallback resultCallback) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				String result = msg.obj.toString();
				resultCallback.getMessage(result);
			}
		};
		requestThread=new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				// 增强for循环遍历entryset
				for (Map.Entry<String, String> entry : params.entrySet()) {
					NameValuePair valuePair = new BasicNameValuePair(
							entry.getKey(), entry.getValue());
					list.add(valuePair);
				}
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(path);
				HttpResponse httpResponse = null;
				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
					httpPost.setEntity(entity);
					httpResponse = httpClient.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						String result = EntityUtils.toString(httpResponse
								.getEntity());
						Message message = handler.obtainMessage();
						message.obj = result;
						message.what = 1;
						handler.sendMessage(message);
					}

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					httpClient.getConnectionManager().shutdown();
				}

			}
		});
		requestThread.start();
	}
	public static void stopThread(){
		Thread.interrupted();
	}
	public static void getGetResult(final String path,final getResultCallback resultCallback){
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				String result=msg.obj.toString();
				resultCallback.getMessage(result);
			}
		};
		requestThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpClient client=new DefaultHttpClient();
				HttpGet get=new HttpGet(path);
				try {
					HttpResponse response=client.execute(get);
					if (response.getStatusLine().getStatusCode()==200) {
						String result=EntityUtils.toString(response.getEntity());
						Message message=handler.obtainMessage();
						message.obj=result;
						message.what=2;
						handler.sendMessage(message);
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		requestThread.start();
	}
	public static void getNetBytes(final Context context,final String Path,final ImageView imv) {
		final Handler handler = new Handler();
		final String imgName=Path.substring(Path.lastIndexOf("/")+1,Path.length());
		File file=context.getFilesDir();
		final File imgFile=new File(file, imgName);
		if(imgFile.exists()){
			System.out.println("*****图片已存在*****");
			FileInputStream fis = null;
			try {
				fis=new FileInputStream(imgFile);
				Bitmap bitmap=BitmapFactory.decodeStream(fis);
				imv.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			requestThread=new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					InputStream is = null;
					FileOutputStream fos = null;
					FileInputStream fis = null;
					HttpURLConnection connection = null;
					try {
						URL url=new URL(Path);
						connection=(HttpURLConnection) url.openConnection();
						connection.setDoInput(true);
						connection.connect();
						if (connection.getResponseCode()==200) {
							is=connection.getInputStream();
							fos=new FileOutputStream(imgFile);
							byte[] buf=new byte[1024];
							int len;
							while ((len=is.read(buf))!=-1) {
								fos.write(buf,0,len);
							}
							fis=new FileInputStream(imgFile);
							final Bitmap bitmap=BitmapFactory.decodeStream(fis);
							handler.post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									imv.setImageBitmap(bitmap);
									
								}
							});
						}
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						if (is != null) {
							try {
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (fos != null) {
							try {
								fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (connection != null) {
							connection.disconnect();
						}
						if (fis != null) {
							try {
								fis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
			requestThread.start();
		}
	}
}
