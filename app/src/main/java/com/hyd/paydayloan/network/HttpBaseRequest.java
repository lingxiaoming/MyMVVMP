package com.hyd.paydayloan.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.hyd.paydayloan.base.BasicParamsInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import static okhttp3.internal.Util.UTF_8;

/**
 * 网络请求基类，用于初始化一些必要的东西
 * Created by lingxiaoming on 2017/7/26 0026.
 */

public class HttpBaseRequest {

    protected HttpInterface mHttpInterface;
    protected Retrofit mRetrofit;

    protected static final int TIMEOUT = 10;//十秒超时时间
    protected static final long RETRY_TIMES = 1;//重订阅次数

    protected Gson gson = new Gson();

    public HttpBaseRequest() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(new BasicParamsInterceptor.Builder().addParamsMap(getCommonMap()).addHeaderParamsMap(getHeadMap()).build());
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        OkHttpClient okHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(RequestConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(new CustomGsonConverterfactory(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        mHttpInterface = mRetrofit.create(HttpInterface.class);
    }


    public class CustomGsonConverterfactory extends Converter.Factory {
        private Gson gson;

        public CustomGsonConverterfactory(Gson gson) {
            this.gson = gson;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new CustomGsonResponseBodyConverter<>(gson, adapter);
        }
    }

    final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
            BaseResponse httpStatus = gson.fromJson(response, BaseResponse.class);
            if (!httpStatus.isSuccess()) {
                value.close();
                throw new ApiException(httpStatus.rtn_code, httpStatus.rtn_msg);
            }

            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = gson.newJsonReader(reader);

            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }



    private Map<String, String> getHeadMap() {
        return new HashMap<>();//TODO 添加头部信息

    }

    private Map<String, String> getCommonMap() {
        return new HashMap<>();//TODO 添加公共参数
    }

    public <T> T getProxy(Class<T> tClass){
        T t = mRetrofit.create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[] {tClass}, new ProxyHandler(t, this));
    }

}
