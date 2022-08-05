package takeaway_one;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@Slf4j
public class TakeawayOneApplication {

    public static void main(String[] args) {

        SpringApplication.run(TakeawayOneApplication.class, args);
        log.info("项目开始启动");
    }

}
