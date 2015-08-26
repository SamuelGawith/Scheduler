public class Data {

    private String group;
    private String lecture;
    private String auditory;
    private String lecturer;
    private String dayOfWeek;
    private String time;


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Sched{" +
                "group='" + group + '\'' +
                ", lecture='" + lecture + '\'' +
                ", auditory='" + auditory + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", time=" + time +
                '}';
    }
}