package support;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: luweihong
 * @Date: 2018/12/14
 */
public class TimeVo {

    private LocalDateTime localDateTime;

    private Instant instant;

    private LocalDate localDate;

    private Duration duration;

    private PeopleVo peopleVo;

    public TimeVo(){
        this.localDate = LocalDate.now();
        this.instant = Instant.now();
        this.localDateTime = LocalDateTime.now();
        this.duration = Duration.ofHours(12);
        this.peopleVo = new PeopleVo();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public PeopleVo getPeopleVo() {
        return peopleVo;
    }

    public void setPeopleVo(PeopleVo peopleVo) {
        this.peopleVo = peopleVo;
    }

    @Override
    public String toString() {
        return "TimeVo{" +
                "localDateTime=" + localDateTime +
                ", instant=" + instant +
                ", localDate=" + localDate +
                ", duration=" + duration +
                ", peopleVo=" + peopleVo +
                '}';
    }
}
