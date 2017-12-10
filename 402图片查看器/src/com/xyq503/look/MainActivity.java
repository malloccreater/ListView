package com.xyq503.look;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.xyq503.imagelook.R;
	

public class MainActivity extends ActionBarActivity {
	private EditText et_path;
	private ImageView iv;
	
//	private Handler handler = new Handler() {
//		//处理消息
//		public void handleMessage(Message msg) {
//			Bitmap bitmap = (Bitmap)msg.obj;//obj现在存的是Bitmap
//			iv.setImageBitmap(bitmap);//在iv控件上显示出来
//		};
//	};
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_path = (EditText)findViewById(R.id.et_path);
        iv = (ImageView)findViewById(R.id.iv);
    }

    public void click(View v) {
    	new Thread(){public void run(){
    		
    		try {
    			//2.1获取访问图片的路径
    			String path = et_path.getText().toString().trim();
    			
    			File file = new File(getCacheDir(),Base64.encodeToString(path.getBytes(), Base64.DEFAULT));
    			
    			if(file.exists()&&file.length()>0) {
    				//使用缓存的图片
    				System.out.println("使用缓存图片");
    				final Bitmap cacheBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
    				//把cacheBitmap显示到iv上
//    				Message msg = Message.obtain();//不用new了 
//    				msg.obj = cacheBitmap; //把bitmap放进消息里
//    				handler.sendMessage(msg);//在子线程中发消息 handleMessage（）就执行
    				
    				//这句API 不管在什么位置上调用 action都运行在UI线程中
    				runOnUiThread( new Runnable() {
						public void run() {
							
		    				iv.setImageBitmap(cacheBitmap);//内部类访问外部成员 要改成final
						}
					});
    				
    				
    				
    			}else {
    				System.out.println("第一次联网");
    			
    			
    			//2.2创建url对象
    			URL url = new URL(path);
    			//2.3获取httpurlconnection
    			HttpURLConnection conn =(HttpURLConnection) url.openConnection();
    			
    			//2.4设置请求的方式
    			conn.setRequestMethod("GET");
    			//2.5设置超时时间
    			conn.setReadTimeout(5000);
    			
    			//2.6获取服务器返回的状态码
    			int code = conn.getResponseCode();
    			if(code==200) {
    				//2.7获取图片的数据 不管是什么数据 都是以流的形式返回
    				InputStream in = conn.getInputStream();//in 现在就存的图片的信息
    				
    				//2.7.1缓存图片 谷歌给我们提供了一个缓存目录
    				
    				FileOutputStream fos = new FileOutputStream(file);
    				int len = -1;
    				byte[] buffer = new byte[1024];
    				while((len=in.read(buffer))!=-1) {
    					fos.write(buffer, 0, len);
    				}
    				
    				fos.close();
    				in.close();
    				
    				
    				
    				
    				
    				//2.8通过位图工厂获取bitmap
    				final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
    				
    				//这句API 不管在什么位置上调用 action都运行在UI线程中
    				runOnUiThread( new Runnable() {
						public void run() {
							//run方法一定运行在UI线程里
		    				//2.9把bitmap显示到iv上
		    				iv.setImageBitmap(bitmap);//内部类访问外部成员 要改成final
						}
					});
    				
    				
    				
    				
    				//2.9把bitmap显示到iv上
    				//iv.setImageBitmap(bitmap);
    				
//    				
//    				Message msg = Message.obtain();//不用new了 
//    				msg.obj = bitmap; //把bitmap放进消息里
//    				handler.sendMessage(msg);//在子线程中发消息 handleMessage（）就执行
    			}
    		}
    		} catch (Exception e) {
    			
    			e.printStackTrace();
    		
    		}
    		
    	};}.start();
    	
    	
    	
    	
    }

}
