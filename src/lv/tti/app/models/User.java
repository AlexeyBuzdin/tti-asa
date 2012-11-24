package lv.tti.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class User {
    @Element
    private String studentCode;

    public User() {}

    public User(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
}
