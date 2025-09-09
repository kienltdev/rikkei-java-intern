package intern.rikkei.warehousesystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class test {
    @GetMapping("/exception")
    public String throwException() {
        throw new RuntimeException("Lỗi test nè!");
    }
}
