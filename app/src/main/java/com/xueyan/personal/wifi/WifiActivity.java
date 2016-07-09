package com.xueyan.personal.wifi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xueyan.personal.R;

import java.util.List;
/*获取已经连接的wifi密码*/
public class WifiActivity extends Activity {
    private WifiManage wifiManage;          //获取wifi密码的实现类，需root 然后读取cat/data/misc/wifi/*.conf
    private   ListView wifiInfosView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        wifiManage = new WifiManage();
        try {
            Init();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Init() throws Exception {
        List<WifiInfo> wifiInfos = wifiManage.Read();

        WifiAdapter ad = new WifiAdapter(wifiInfos,WifiActivity.this);
        wifiInfosView= (ListView)findViewById(R.id.wifiListView);
        wifiInfosView.setAdapter(ad);
    }

    public class WifiAdapter extends BaseAdapter {

        List<WifiInfo> wifiInfos =null;
        Context con;

        public WifiAdapter(List<WifiInfo> wifiInfos,Context con){
            this.wifiInfos =wifiInfos;
            this.con = con;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return wifiInfos.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return wifiInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = LayoutInflater.from(con).inflate(android.R.layout.simple_list_item_1, null);
            TextView tv = (TextView)convertView.findViewById(android.R.id.text1);
            tv.setText("Wifi:"+wifiInfos.get(position).Ssid+"\n密码:"+wifiInfos.get(position).Password);
            return convertView;
        }
    }
}
