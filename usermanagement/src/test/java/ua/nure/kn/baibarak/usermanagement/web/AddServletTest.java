package ua.nure.kn.baibarak.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.baibarak.usermanagement.User;

public class AddServletTest extends MockServletTestCase {

	private static final Long ID_ETALON = 1000L;
	private static final String LASTNAME_ETALON = "Doe";
	private static final String FIRSTNAME_ETALON = "John";

	@Before
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    @Test
    public void testAdd() {
        Date date = new Date();
        User newUser = new User(FIRSTNAME_ETALON, LASTNAME_ETALON, date);
        User user = new User(ID_ETALON, FIRSTNAME_ETALON, LASTNAME_ETALON, date);
        getMockUserDao().expectAndReturn("create", newUser, user);

        addRequestParameter("firstName", FIRSTNAME_ETALON);
        addRequestParameter("lastName", LASTNAME_ETALON);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
    }

    @Test
    public void testAddEmptyFirstName() {
        Date date = new Date();
        addRequestParameter("lastName", LASTNAME_ETALON);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    @Test
    public void testAddEmptyLastName() {
        Date date = new Date();
        addRequestParameter("firstName", FIRSTNAME_ETALON);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    @Test
    public void testAddEmptyDate() {
        Date date = new Date();
        addRequestParameter("firstName", FIRSTNAME_ETALON);
        addRequestParameter("lastName", LASTNAME_ETALON);
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    @Test
    public void testAddEmptyDateIncorrect() {
        Date date = new Date();
        addRequestParameter("firstName", FIRSTNAME_ETALON);
        addRequestParameter("lastName", LASTNAME_ETALON);
        addRequestParameter("date", "ab78cde12fg");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

}
