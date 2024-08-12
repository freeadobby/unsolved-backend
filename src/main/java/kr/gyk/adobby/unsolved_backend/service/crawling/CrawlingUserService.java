package kr.gyk.adobby.unsolved_backend.service.crawling;

import kr.gyk.adobby.unsolved_backend.dto.crawling.UserStatsDTO;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class CrawlingUserService {

    public UserStatsDTO getUserStats (String username) throws Exception {
        UserStatsDTO userStats = new UserStatsDTO();

        userStats.setUsername(username);

        Connection connection = JSoupService.getConnection("https://www.acmicpc.net/user/" + username);

        Elements contents = JSoupService.getElements(connection, "body > div.wrapper > div.container.content > div.row > div:nth-child(2) > div > div.col-md-9 > div:nth-child(2) > div.panel-body > div > a");
        ArrayList<Long> listProblemSolved = new ArrayList<>();
        for (var element : contents) listProblemSolved.add(Long.parseLong(element.text()));
        userStats.setProblemSolved(listProblemSolved);

        contents = JSoupService.getElements(connection, "#statics > tbody > tr");
        for (var element : contents) {
            String key = element.select("tr > th").text();
            Integer value = null;
            System.out.println(key + " " + value);
            try {
                value = Integer.parseInt(element.select("td").text());
            } catch (Exception e) { continue; }

            switch (key) { // TODO
                case "등수" -> userStats.setSRanking(value);
                case "맞은 문제" -> userStats.setSProblemSolved(value);
                case "맞았지만 만점을 받지 못한 문제" -> userStats.setSProblemSolvedNotPerfect(value);
                case "시도했지만 맞지 못한 문제" -> userStats.setSProblemWrong(value);
                case "제출" -> userStats.setSCountSubmit(value);
                case "맞았습니다" -> userStats.setSCountRight(value);
                case "틀렸습니다" -> userStats.setSCountWrong(value);
                case "출력 형식" -> userStats.setSCountOutputFormat(value);
                case "시간 초과" -> userStats.setSCountTimeout(value);
                case "메모리 초과" -> userStats.setSCountMemoryEx(value);
                case "출력 초과" -> userStats.setSCountOutputEx(value);
                case "런타임 에러" -> userStats.setSCountRuntimeErr(value);
                case "컴파일 에러" -> userStats.setSCountCompileRrr(value);
            }
        }

        contents = JSoupService.getElements(
            JSoupService.getConnection("https://solved.ac/profile/" + username),
        "#__next > div.css-axxp2y > div > div.css-zi8sic > div.css-5vptc8 > div:nth-child(1) > div.css-1midmz7 span"
        );
        userStats.setRating(contents.get(0).text());
        userStats.setRatingScore(Integer.parseInt(contents.get(1).text()));


        return userStats;
    }

}
