package cn.edu.jlu.personnel.management.support;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 将String转化为Date类型
 * Created by nostalie on 17-3-17.
 */
@Component
public class DateConversionService implements Converter<String,Date> {

    private static Logger logger = LoggerFactory.getLogger(DateConversionService.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date convert(String s) {
        try {
            DateTime dateTime = DATE_TIME_FORMATTER.parseDateTime(s);
            Date date = dateTime.toDate();
            logger.info("日期转化成功，date is: {}",date);
            return date;
        } catch (Exception e) {
            logger.error("日期转化失败，date String is: {}",s);
            return null;
        }
    }
}
