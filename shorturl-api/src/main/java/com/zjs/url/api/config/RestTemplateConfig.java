package com.zjs.url.api.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    private final static Integer CONNECT_TIMEOUT = 5 * 1000;
    private final static Integer READ_TIMEOUT = 50 * 1000;
    private final static Integer CONNECTION_REQUEST_TIMEOUT = 2 * 1000;
    private final static Integer MAX_CONN_TOTAL = 600;
    private final static Integer MAX_CONN_PER_ROUTE = 300;
    private final static Integer SOCKET_TIMEOUT = 30 * 1000;
    private final static Integer RETRY_COUNT = 3;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .build();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }
    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory(CloseableHttpClient httpClient){

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        requestFactory.setReadTimeout(READ_TIMEOUT);
        requestFactory.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
        requestFactory.setBufferRequestBody(false);

        return requestFactory;
    }

    private static SSLConnectionSocketFactory socketFactory = null;
    private static PoolingHttpClientConnectionManager connectionManager = null;
    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, (TrustStrategy) (x509Certificates, s) -> true);
            socketFactory = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", socketFactory)
                    .build();
            connectionManager = new PoolingHttpClientConnectionManager(registry);
            connectionManager.setDefaultMaxPerRoute(MAX_CONN_PER_ROUTE);
            connectionManager.setMaxTotal(MAX_CONN_TOTAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Bean
    CloseableHttpClient httpClient()  {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout( SOCKET_TIMEOUT )
                .setConnectTimeout( CONNECT_TIMEOUT )
                .setConnectionRequestTimeout( CONNECTION_REQUEST_TIMEOUT )
                .build();
        return HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setSSLSocketFactory(socketFactory)
                .setConnectionManager(connectionManager)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, true))
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setConnectionManagerShared(true)
                .build();
    }

}
