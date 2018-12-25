package ua.nure.kn.baibarak.usermanagement.web;

import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.baibarak.usermanagement.User;

public class DetailsServletTest extends MockServletTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
        createServlet(DetailsServlet.class);
	}

	@Test
	public void testOkButton() {
        addRequestParameter("okButton", "Ok");
        User nullUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("User must not exist in session scope", nullUser);
    }
}
