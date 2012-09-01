package lv.tti.app;

public class Lesson {
	private String time;
	private String classroom;
	private String subject;
	private String professor;
	
	public Lesson(String time, String classroom, String subject,
			String professor) {
		super();
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

	@Override
	public String toString() {
		return "Lesson [time=" + time + ", classroom=" + classroom
				+ ", subject=" + subject + ", professor=" + professor + "]";
	}
}
