package lv.tti.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class User {
    @Element
    private String studentCode;
//    @Element
//    private List<Day> days;

    public User() {}

    public User(String studentCode, List<Day> days) {
        this.studentCode = studentCode;
       // this.days = days;
    }

    public String getStudentCode() {
        return studentCode;
    }

//    public List<Day> getLessons() {
//        return days;
//    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

//    public void setLessons(List<Day> days) {
//        this.days = days;
//    }
}
