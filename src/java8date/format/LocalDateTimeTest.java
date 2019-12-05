package java8date.format;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * LocalDateTime测试
 *
 * @author Jann Lee
 * @date 2019-12-01 22:47
 **/
public class LocalDateTimeTest {

    @Test
    public void testLocalDateTime() {

        // 1. 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // 2. 获取指定日期，时间
        LocalDateTime localDateTime2 = LocalDateTime.of(2019, 12, 1, 13, 20, 20);
        System.out.println(localDateTime2);

        // 3. 日期，时间加减操作
        LocalDateTime localDateTime3 = localDateTime2.plusYears(1).plusDays(2);
        System.out.println(localDateTime3);

        //4. 获取年月日时分秒
        System.out.println(localDateTime3.getYear());
        System.out.println(localDateTime3.getDayOfYear());
        System.out.println(localDateTime3.getMinute());
    }

    /**
     * 时间戳, 计算机读的时间
     */
    @Test
    public void testInstant() {
        // 获取世界协调时间 UTC
        Instant instant = Instant.now();
        System.out.println(instant);

        // 获取北京时间，东八区 GMT?
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        // 获取毫秒值
        System.out.println(instant.toEpochMilli());
        System.out.println(offsetDateTime.toEpochSecond());


        // Unix元年 + 59秒
        Instant instant2 = Instant.ofEpochSecond(59);
        System.out.println(instant2);
    }

    /**
     * 时间间隔 to to to
     */
    @Test
    public void testDuration() {
        Instant instant1 = Instant.now();

        // 1. duration 参数顺序很重要， result = arg2 - arg1
        Instant instant2 = instant1.plusSeconds(99);
        Duration duration = Duration.between(instant1, instant2);
        System.out.println(duration.getSeconds());
        System.out.println(duration.toMillis());
        Duration duration2 = Duration.between(instant2, instant1);
        System.out.println(duration2.getSeconds());

        // 2. 时间间隔不支持LocalDate之间计算
        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime2 = localDateTime1.plusDays(1);
        Duration duration3 = Duration.between(localDateTime1, localDateTime2);
        System.out.println(duration3);
    }

    /**
     * 日期间隔
     */
    @Test
    public void testPeriod() {
        // 1. 日期间隔
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = localDate1.plusDays(1);
        Period period = Period.between(localDate1, localDate2);
        System.out.println(period.getDays());
    }

    /**
     * 时间校正器, 下一个周日，下一个生日纪念日
     */
    @Test
    public void testTemporalAdjuster() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime localDateTime2 = localDateTime.withDayOfMonth(20);
        System.out.println(localDateTime2);

        LocalDateTime localDateTime3 = localDateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(localDateTime3);

        // 自定义： 下一个工作日
        LocalDateTime localDateTime4 = localDateTime.with((tempDateTime) -> {
            LocalDateTime localDateTime5 = (LocalDateTime) tempDateTime;
            DayOfWeek dayOfWeek = localDateTime5.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return localDateTime5.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return localDateTime5.plusDays(2);
            } else {
                return localDateTime5.plusDays(1);
            }
        });
        System.out.println(localDateTime4);
    }

    @Test
    public void testDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime1 = LocalDateTime.now();

        String dateFormated = dateTimeFormatter.format(localDateTime1);
        System.out.println(dateFormated);

        /**
         * Can't do this
         */
        LocalDateTime localDateTime2 = LocalDateTime.parse(dateFormated, dateTimeFormatter);
        System.out.println(localDateTime2);

        LocalDate localDate = LocalDate.parse(dateFormated, dateTimeFormatter);
        System.out.println(localDate);
    }

    @Test
    public void testZone(){
        LocalDateTime localDateTime1 = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        System.out.println(localDateTime1);

        LocalDateTime localDateTime2 = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime2.atZone(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zonedDateTime2 = localDateTime2.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime2);
    }


    @Test
    public void testTransform(){
        // 时间戳转LocalDateTime
        long timestamp = Instant.now().toEpochMilli();
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
        System.out.println(localDateTime);

        // LocalDateTime 转时间戳
        timestamp = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(timestamp);

        // 兼容旧版本Date
        LocalDateTime localDateTime2 = LocalDateTime.now();
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(0)));
        System.out.println(date);

        // Date转 LocalDateTime
        localDateTime2 = date.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
        System.out.println(localDateTime2);
    }
}