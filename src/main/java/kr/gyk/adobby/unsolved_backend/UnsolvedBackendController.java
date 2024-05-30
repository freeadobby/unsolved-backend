package kr.gyk.adobby.unsolved_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnsolvedBackendController {
    @GetMapping("")
    public String index() {
        return "unsolved";
    }
}
