package cz.knaisl.munchen_tdd.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlProviderImpl implements UrlProvider {

    @Value("${server.port:8080}")
    int port;

    @Value("${server.ssl.enabled}")
    boolean sslEnabled;

    @Value("${server.address}")
    String address;

    public String getHttpAddress() {
        return (!sslEnabled ? "http" : "https") + "://" + address + ":" + port;
    }

}
