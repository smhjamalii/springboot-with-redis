package ir.encoding.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indices")
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @GetMapping
    public int findIndex(String name) {
        return indexService.getIndex(name);
    }

}
