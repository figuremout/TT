package com.example.tt.QQ;

import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

public class BaseApiListener implements IRequestListener {
    @Override
    public void onComplete(final JSONObject response) {
//        showResult("IRequestListener.onComplete:", response.toString());
//        doComplete(response, state);
    }
    protected void doComplete(JSONObject response, Object state) {
    }
    @Override
    public void onIOException(final IOException e) {
//        showResult("IRequestListener.onIOException:", e.getMessage());
    }
    @Override
    public void onMalformedURLException(final MalformedURLException e) {
//        showResult("IRequestListener.onMalformedURLException", e.toString());
    }
    @Override
    public void onJSONException(final JSONException e) {
//        showResult("IRequestListener.onJSONException:", e.getMessage());
    }
    @Override
    public void onConnectTimeoutException(ConnectTimeoutException arg0) {
// TODO Auto-generated method stub
    }
    @Override
    public void onSocketTimeoutException(SocketTimeoutException arg0) {
// TODO Auto-generated method stub
    }
    //1.4版本中IRequestListener 新增两个异常
    @Override
    public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e){
// 当前网络不可用时触发此异常
    }
    @Override
    public void onHttpStatusException(HttpUtils.HttpStatusException e) {
// http请求返回码非200时触发此异常
    }
    public void onUnknowException(Exception e) {
// 出现未知错误时会触发此异常
    }
}