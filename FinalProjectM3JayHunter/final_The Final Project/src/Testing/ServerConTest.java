package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import server.controller.ServerCommController;

/**
 * The JUnit test for the ServerCommController class.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 *
 */
class ServerConTest {

	/**
	 * Tests the server connection.
	 */
    @Test
    void testServerCon() {
        ServerCommController aServer = new ServerCommController(9320);
        assertNotNull(aServer);
    }

}
