package kr.gyk.adobby.unsolved_backend.service.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.util.ObjectUtils;

public class JSoupService {

    public static Connection getConnection (String url) throws Exception {
        return Jsoup.connect(url);
    }

    public static Elements getElements (String url, String query) throws Exception {
        Connection connection = getConnection(url);
        return connection.get().select(query);
    }

    public static Elements getElements (Connection connection, String query) throws Exception {
        return connection.get().select(query);
    }

}
