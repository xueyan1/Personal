package com.xueyan.personal.wifi;

/**
 * Created by Administrator on 2016/7/8.
 */
public class WifiInfo {
    public String Ssid = "";
    public String Password = "";

    public WifiInfo() {
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSsid() {
        return Ssid;
    }

    public void setSsid(String ssid) {
        Ssid = ssid;
    }
}
