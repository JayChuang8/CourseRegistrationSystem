package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import server.model.Course;

/**
 * JUnit test for the course class.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
class CourseTest {

	/**
	 * Tests the constructor of course.
	 */
    @Test
    void testCourse() {
        Course c = new Course("Shop", 21);
        assertEquals(c.getSecNum(), 1);
        assertEquals(c.getCourseName(),"Shop");
        assertEquals(c.getCourseNum(), 21);
        assertEquals(c.getFreeSeats(), 200);
    }
    
    /**
     * Tests the to string function of course.
     */
    @Test
    void testToString() {
        Course c = new Course("Shop", 21);
        String test = c.toString();
        String actual = "Shop 21    section Number: 1  Open Seats: 200+--------------------"
                + "--------------------------------------------------------------------------+";
        assertEquals(test,actual);
    }

    /**
     * Tests the enroll function of course.
     */
    @Test
    void testEnroll() {
        Course c = new Course("Shop", 21);
        c.setFreeSeats(0);
        boolean bool = c.enroll();
        assertEquals(bool,true);
    }

}
