package lv.tti.app.models;

import org.simpleframework.xml.Element;

import java.util.Calendar;
import java.util.List;

public class Day {
    @Element
    private Calendar date;
    @Element
    private List<Lesson> lessons;

    public Day() {}

    public Day(Calendar date, List<Lesson> lessons) {
        this.date = date;
        this.lessons = lessons;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
