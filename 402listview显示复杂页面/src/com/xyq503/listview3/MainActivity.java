package com.xyq503.listview3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
//测试一下

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //1.找到控件
        ListView lv =(ListView)findViewById(R.id.lv);
        //2.
        lv.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 7;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			//1.想办法把我们定义的布局转化成VIEW对象
			View view;
			if(convertView == null) {
				//创建新的view 对象 通过打气筒把一个布局资源转化成一个view对象
				//resource就是我们定义的布局文件
				view = View.inflate(MainActivity.this,R.layout.item, null);
			} else {
				//复用历史缓存对象
				view = convertView;
			}
			return view;
		}
    	
    }
}
