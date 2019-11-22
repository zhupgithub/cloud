package com.zhupeng.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP客户端工具类
 *
 * @author Chen Jianliang
 * @since 1.0
 * @since 2019/7/31
 */
public class HttpClientUtils {
    public static CloseableHttpClient httpclient = HttpClients.createDefault();

    private static boolean useSSL = true;

    public static final String CONTENT_KEY = "content";
    public static final String STATUS_CODE_KEY = "statusCode";

    public static Map<String, Object> get(String url) {
        return get(httpclient, url);
    }

    public static Map<String, Object> get(CloseableHttpClient httpclient, String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String backContent = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // start 读取整个页面内容
                InputStream is = entity.getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append(line + "\r\n");
                }
                // end 读取整个页面内容
                backContent = buffer.toString();
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap(backContent, response.getStatusLine().getStatusCode());
    }

    public static Map<String, Object> post(String url, List<NameValuePair> nvps) {
        return post(httpclient, url, nvps);
    }

    public static Map<String, Object> post(CloseableHttpClient httpclient, String url, List<NameValuePair> nvps) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String backContent = null;
        try {
            if (nvps != null)
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // start 读取整个页面内容
                InputStream is = entity.getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append(line + "\r\n");
                }
                if (buffer != null && buffer.length() > 2) {
                    // end 读取整个页面内容
                    backContent = buffer.delete(buffer.length() - 2, buffer.length()).toString();
                }
                // System.out.println(backContent);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap(backContent, response.getStatusLine().getStatusCode());
    }

    public static Map<String, Object> resultMap(String backContent, int statusCode) {
        Map<String, Object> map = new HashMap<>();
        map.put(CONTENT_KEY, backContent);
        map.put(STATUS_CODE_KEY, statusCode);
        return map;
    }

    @Autowired
    CloseableHttpClient closeableHttpClient;

    /*
     * Get,put,post delete功能
     *
     * */
    public static String httpRequest(CloseableHttpClient httpClient ,  String requestUrl, String requestJson, String httpMethod) {
        try {
            if(useSSL) {
            }else {
            }
            HttpResponse response;

            if (null == httpMethod) {
                throw new RuntimeException("Http Method should be Get, Post, Put or Delete.");
            }

            if (httpMethod == "Get") {
                HttpGet httpGet = new HttpGet(requestUrl);
                response = httpClient.execute(httpGet);

            } else {
                HttpEntityEnclosingRequestBase requestBase = null;

                switch (httpMethod) {
                    case "Post":
                        requestBase = new HttpPost(requestUrl);
                        break;
                    case "Put":
                        requestBase = new HttpPut(requestUrl);
                        break;
                    case "DELETE":
//                        requestBase = new HttpDeleteWithBody(requestUrl);
                        break;
                }

                if (StringUtils.isEmpty(requestJson)) {
                    StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
                    if (requestBase != null) {
                        requestBase.setEntity(requestEntity);
                    }
                }

                response = httpClient.execute(requestBase);
            }

            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
//            try {
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }



    /*
     * Get,put,post delete功能
     *
     * */
    public static String httpsslRequest(CloseableHttpClient httpClient , String requestUrl, String requestJson, String httpMethod) {
        try {
            HttpResponse response;

            if (null == httpMethod) {
                throw new RuntimeException("Http Method should be Get, Post, Put or Delete.");
            }

            if (httpMethod == "Get") {
                HttpGet httpGet = new HttpGet(requestUrl);
                response = httpClient.execute(httpGet);
            } else {
                HttpEntityEnclosingRequestBase requestBase = null;

                switch (httpMethod) {
                    case "Post":
                        requestBase = new HttpPost(requestUrl);
                        break;
                    case "Put":
                        requestBase = new HttpPut(requestUrl);
                        break;
                    case "DELETE":
//                        requestBase = new HttpDeleteWithBody(requestUrl);
                        break;
                }
                if (StringUtils.isEmpty(requestJson)) {
                    StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
                    if (requestBase != null) {
                        requestBase.setEntity(requestEntity);
                    }
                }

                response = httpClient.execute(requestBase);
            }

            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String doPost(String url,String jsonstr){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
//            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"utf-8");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 发起Get请求
     *
     * @param urlStr
     * @return
     */
    public static String GetHttpForFile(String urlStr) {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        byte[] buff = new byte[1024];
        int len = 0;
        try {

            URL url = new URL(UriUtils.encodePath(urlStr, "utf-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "plain/text;charset=" + "utf-8");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30 * 1000);
            conn.connect();
            is = conn.getInputStream();
            os = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            //return os.toString();
            return Base64.encodeBase64String(os.toByteArray());

        } catch (IOException e) {
            System.out.print("发起请求出现异常:" + e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.print("【关闭流异常】");
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.print("【关闭流异常】");
                }
            }
        }
    }


    public static String GetHttpSSLFile(String urlStr){

        InputStream is = null;
        ByteArrayOutputStream os = null;
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

            URL console = new URL(UriUtils.encodePath(urlStr, "utf-8"));
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            is = conn.getInputStream();
            os = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            return Base64.encodeBase64String(os.toByteArray());

        }
        catch (ConnectException e)
        {
            System.out.print("发起请求出现异常:" + e);
            return null;
         }

        catch (Exception e)
        {
            System.out.print("发起请求出现异常:" + e);
            return null;
        }
         finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.print("【关闭流异常】");
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.print("【关闭流异常】");
                }
            }
        }
    }




    public static String sendSSLPost(String url, String param)
    {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try
        {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null)
            {
                if (ret != null && !ret.trim().equals(""))
                {
                    result.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
                }
            }
             conn.disconnect();
            br.close();
        }
        catch (ConnectException e)
        {
          //  log.error("调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param, e);
        }
        catch (SocketTimeoutException e)
        {
           // log.error("调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        }
        catch (IOException e)
        {
           // log.error("调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param, e);
        }
        catch (Exception e)
        {
           // log.error("调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param, e);
        }
        return result.toString();
    }


    private static class TrustAnyTrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[] {};
        }
    }
    private static class TrustAnyHostnameVerifier implements HostnameVerifier
    {
        @Override
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
    }
}

