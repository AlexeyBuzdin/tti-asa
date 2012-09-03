package lv.tti.app.models;

import org.simpleframework.xml.Element;

public class Lesson {
    @Element
	private String time;
    @Element
	private String classroom;
    @Element
	private String subject;
    @Element
	private String professor;

    public Lesson() {}

    public Lesson(String time, String classroom, String subject,
			String professor) {
		this.time = time;
		this.classroom = classroom;
		this.subject = subject;
		this.professor = professor;
	}
	
	public String getTime() {
		return time;
	}
	public String getClassroom() {
		return classroom;
	}
	public String getSubject() {
		return subject;
	}
	public String getProfessor() {
		return professor;
	}

    public void setTime(String time) {
        this.time = time;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    @Override
	public String toString() {
		return "Lesson [time=" + time + ", classroom=" + classroom
				+ ", subject=" + subject + ", professor=" + professor + "]";
	}
}
