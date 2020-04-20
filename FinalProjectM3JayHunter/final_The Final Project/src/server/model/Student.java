package server.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores Data about an individual student including name, id,
 * password and an Arraylist of Courses the are in
 * 
 * @author Hunter Coles, Jay Chuang
 *
 */
public class Student implements Serializable{

    /**
     * name of the student
     */
    private String studentName;
    /**
     * student id number
     */
    private int studentId;
    /**
     * student password
     */
    private String studentPass;
    /**
     * List of courses taken by one student
     */
    private ArrayList<Course> courseList;
    /**
     * constructor for Student, initializes everything to null or 0
     */
    public Student() {
        setStudentName(null);
        setStudentId(0);
        studentPass = null;
        courseList = new ArrayList<Course>();
    }
    /**
     * Student Constructor,
     * @param studentName, to be set
     * @param studentId, to be set
     * @param studentPass, to be set
     * @param courseList, to be set
     */
    public Student (String studentName, int studentId, String studentPass, ArrayList<Course> courseList) {
        this.setStudentName(studentName);
        this.setStudentId(studentId);
        this.studentPass = studentPass;
        this.courseList = courseList;
    }
    /**
     * 
     * @return student Name
     */
    public String getStudentName() {
        return studentName;
    }
    /**]
     * 
     * @param set student name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    /**
     * 
     * @return Student Id
     */
    public int getStudentId() {
        return studentId;
    }
    /**
     * 
     * @return password
     */
    public String getPassword() {
        return studentPass;
    }
    /**
     * 
     * @param studentId, to be set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    /**
     * toString method for student, puts it in the from
     * Student Name: name+Student Id: id+All Classes:+
     */
    @Override
    public String toString () {
        return  "Student Name: " + getStudentName() + "+" +
                    "Student Id: " + getStudentId() + "+" +
                    "All Classes:+";
    }
    /**
     * 
     * @param c, added to courseList
     */
    public void addCourse(Course c) {

        courseList.add(c);
    }
    /**
     * @param c, to be removed from courseList
     */
    public void removeCourse(Course c) {

        courseList.remove(c);
    }
    /**
     * checks if a student is enrolled in a course
     * @param name, of course
     * @param courseId, of course
     * @return true if student is enrolled in course
     */
    public boolean enrolled(String name , int courseId) {

        for(Course c : courseList)
        {
            if(c.getCourseName().equals(name) && c.getCourseNum() == courseId) return true;
        }
        return false;
    }
    /**
     * @return true if student has the max courses false else
     */
    public boolean maxCourses() {
    	if(courseList.size() > 5) return true;
    	return false;
    }
}
