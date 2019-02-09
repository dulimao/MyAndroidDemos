package com.example.myandroidproject.network_frame.myhttp;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonHttpService implements IHttpService {

    private String url;
    private byte[] requestData;
    private IHttpListener httpListener;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] data) {
        this.requestData = data;
    }


    @Override
    public void setHttpCallback(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void execute() {
        //真正的网络操作实现
       httpUrlconnPost();
    }

    public void httpUrlconnPost(){
        HttpURLConnection conn = null;
        try {
            URL httpUrl = new URL(this.url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();
            //-----------使用字节流发送数据------------
            OutputStream outputStream = conn.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            if (requestData != null){
                bufferedOutputStream.write(requestData);
            }

            bufferedOutputStream.flush();
            outputStream.close();
            bufferedOutputStream.close();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = conn.getInputStream();
                httpListener.onSuccess(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpListener.onFailure();
        }finally {
            if (conn != null){
                conn.disconnect();//释放关闭TCP连接
            }
        }
    }

}
