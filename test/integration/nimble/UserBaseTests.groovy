package nimble

import static org.junit.Assert.*
import grails.plugins.nimble.core.Role
import grails.plugins.nimble.core.UserBase;

import org.junit.*

class UserBaseTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
       UserBase user = UserBase.findByUsername('admin')
	   def all = UserBase.list()
	   assertNotNull(user)
	   def allRoles = user.roles
	   assertEquals(2, allRoles.size())
    }
}
