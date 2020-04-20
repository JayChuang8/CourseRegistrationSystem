package server.model;

/**
 * Stores Data about Classes including the name, number, section and free seat
 * in the class
 * @author Hunter Coles, Jay Chuang
 * @version 1.0
 * @since April 19, 2020
 */
public class Course {
	/**
	 * class section number, default 1
	 */
    private int secNum;
    /**
     * free seats left in the class, default 200
     */
    private int freeSeats;
    /**
     * name of the class
     */
    private String courseName;
    /**
     * number of the class
     */
    private int courseNum;
    /**
     * constructor for course
     * @param courseName
     * @param courseNum
     */
    public Course(String courseName, int courseNum) {
        this.setCourseName(courseName);
        this.setCourseNum(courseNum);
        setSecNum(1);
        setFreeSeats(200);

    }
    /**
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }
    /**
     * set Course Name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    /**
     * @return course number
     */
    public int getCourseNum() {
        return courseNum;
    }
    /**
     * set Course number
     */
    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }
    /**
     * takes all elements of course and concatenates into large string
     */
    @Override
    public String toString () {
        String st = getCourseName() + " " + getCourseNum () + "    section Number: "+ secNum + "  Open Seats: " + freeSeats ;
        st += "+----------------------------------------------------------------------------------------------+";
        return st;
    }
    /**
     * @return free seats
     */
    public int getFreeSeats() {
        return freeSeats;
    }
    /**
     * set free seats
     */
    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }
    /**
     * @return section number
     */
    public int getSecNum() {
        return secNum;
    }
    /**
     * set section number
     */
    public void setSecNum(int secNum) {
        this.secNum = secNum;
    }
    /**
     * if there is a free seat in the class return false
     * else return true
     * @return
     */
    public boolean enroll()
    {
        if(freeSeats > 0)
            {
            freeSeats--;
            return false;
            }
        return true;

    }
}