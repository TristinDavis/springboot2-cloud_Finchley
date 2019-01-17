import com.aha.tech.base.commons.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Author: luweihong
 * @Date: 2018/9/10
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class Jdk8TimeTest {

    private Logger LOGGER = LoggerFactory.getLogger(Jdk8TimeTest.class);


    @Test
    public void zoneId() {
        LocalDateTime localDateTime = DateUtil.currentlocalDateTime();
        LOGGER.debug("当前 加了时区 的localDateTime : {} ", localDateTime);
        LOGGER.debug("当前 默认时区 的localDateTime : {} ", LocalDateTime.now());
    }

    @Test
    public void customizeTime() {
        LocalTime localTime = DateUtil.customizeTime(2, 2, 2);
        LOGGER.debug("customize localTime : {} ", localTime);
        LOGGER.debug("format customize localTime : {} ", DateUtil.convertDate2Str(null, localTime));

        LocalDate localDate = DateUtil.customizeDate(2018, 9, 9);
        LOGGER.debug("customize localDate : {} ", localDate);
        LOGGER.debug("format customize localDate : {} ", DateUtil.convertDate2Str(null, localDate));

        LocalDateTime localDateTime = DateUtil.customizeDateTime(2018, 7, 20, 11, 11, 11);
        LOGGER.debug("customize localTimDate : {} ", localDateTime);
        LOGGER.debug("format customize localTimDate : {} ", DateUtil.convertDate2Str(null, localDateTime));
    }
}
