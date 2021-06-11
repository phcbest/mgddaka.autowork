package AutoWork.utils;

import AutoWork.pojo.ProxyListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 12:26 2020/11/28
 */
@Component
@Slf4j
public class OkHttpUtils {

    private static final OkHttpClient.Builder clientBuilder = new OkHttpClient()
            .newBuilder();
    private static final MediaType TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=utf-8");

    private static List<ProxyListBean> usableProxy = new ArrayList<>();

    /**
     * 异步使用的
     *
     * @param u
     * @param headers
     * @param requestBody
     * @param callback
     * @throws IOException
     */
    public void post(String u, Map<String, String> headers, Object requestBody, Callback callback) throws IOException {
        OkHttpClient proxyClient = getProxyClient();
        doRequest(u, headers, requestBody, proxyClient, callback);
    }

    private OkHttpClient getProxyClient() {
        if (usableProxy == null) {
            usableProxy = new ArrayList<>();
        }
        if (usableProxy.size() == 0) {
//            log.error("代理地址集合为null,返回正常代理");
            return clientBuilder
                    .build();
        } else {
            String address = usableProxy.get(new Random().nextInt(usableProxy.size()))
                    .getProxy();
            String[] proxy = address.split(":");
//            log.info("代理地址" + address);
            OkHttpClient client = clientBuilder
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                            proxy[0], Integer.parseInt(proxy[1]))))
                    .build();
            return client;
        }
    }

    /**
     * 必须同步使用的接口工具类
     */
    public Call post(String u, Map<String, String> headers, Object requestBody) throws IOException {
        Gson gson = new Gson();
        Request.Builder request = new Request.Builder();
        request.url(u);
        request.post(RequestBody.create(TYPE, gson.toJson(requestBody)));
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        for (String key : headers.keySet()) {
            request.addHeader(key, headers.get(key));
        }
        return getProxyClient().newCall(request.build());
    }


    /**
     * 进行请求
     *
     * @param u
     * @param headers
     * @param requestBody
     * @param client
     * @param callback
     */
    private void doRequest(String u, Map<String, String> headers, Object requestBody,
                           OkHttpClient client, Callback callback) {
        Gson gson = new Gson();
        Request.Builder request = new Request.Builder();
        request.url(u);
        request.post(RequestBody.create(TYPE, gson.toJson(requestBody)));
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        for (String key : headers.keySet()) {
            request.addHeader(key, headers.get(key));
        }
        client.newCall(request.build()).enqueue(callback);
    }


    public static void getUsableProxy() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://mgddaka.icu:5010/get_all")
                .method("GET", null)
                .build();
        try {
            Response execute = client.newCall(request).execute();
            if (execute.isSuccessful()) {
                List<ProxyListBean> proxyList = new Gson().fromJson(execute.body().string(),
                        new TypeToken<List<ProxyListBean>>() {
                        }.getType());
                usableProxy.addAll(proxyList);
                //检测出当前能用的代理
                testProxy(new ArrayList<>(usableProxy));

//                if (usableProxy.size() == 0) {
//                    log.info("代理列表为空");
//                }
                execute.close();
            } else {
                log.info("获取代理失效" + execute.message());
                execute.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static void testProxy(List<ProxyListBean> proxyList) {
        usableProxy.clear();
        for (ProxyListBean plb : proxyList) {
            String proxy = plb.getProxy();
            clientBuilder.build().newBuilder()
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                            proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1]))))
                    .build().newCall(
                    //ping蘑菇钉测试
                    new Request.Builder()
                            .url("https://api.moguding.net:9000/job/post/v2/newList")
                            .method("GET", null)
                            .build()
            ).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    log.info(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    usableProxy.add(plb);
                    log.info("添加可用代理" + plb.toString());
                    response.close();
                }
            });
        }
    }
}
