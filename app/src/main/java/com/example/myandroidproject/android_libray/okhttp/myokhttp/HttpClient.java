package com.example.myandroidproject.android_libray.okhttp.myokhttp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {
    public String method;
    public String url;
    public List<RequestParams> params;
    public BaseCallBack callback;


    private HttpClient(String requestMethod,String requestUrl,List<RequestParams> requestParamsList){
        this.method = requestMethod;
        this.url = requestUrl;
        this.params = requestParamsList;
    }


    public void enqueue(BaseCallBack callBack){
        this.callback = callBack;
        HttpClientManager.getInstance().request(this);
    }


    //todo 构建请求
    private void buildRequest(){
        if (method == "GET"){

        }else if (method == "POST"){

        }
    }


    public static Builder newBuilder(){
        return new Builder();
    }


    static final class Builder{
        private String requestMethod = "GET";
        private String url;
        private List<RequestParams> params;


        public Builder url(String url){
            this.url = url;
            return this;
        }


        public Builder get(){
            requestMethod = "GET";
            return this;
        }

        public Builder post(){
            requestMethod = "POST";
            return this;
        }

        public Builder addParams(String key,Object value){
            if (params == null){
                params = new ArrayList<>();
            }
            params.add(new RequestParams(key,value));
            return this;
        }


        public HttpClient build(){
            return new HttpClient(requestMethod,url,params);
        }
    }
}
