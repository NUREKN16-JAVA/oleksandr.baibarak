package ua.nure.kn.baibarak.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.baibarak.usermanagement.User;

public class EditServletTest extends MockServletTestCase {

	private static final String ID_STR_ETALON = "1000";
	private static final Long ID_ETALON = 1000L;
	private static final String LASTNAME_ETALON = "Doe";
	private static final String FIRSTNAME_ETALON = "John";

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}

	@Test
	public void testEdit() {
		Date date = new Date();
		User user = new User(ID_ETALON, FIRSTNAME_ETALON, LASTNAME_ETALON, date);
		getMockUserDao().expect("update", user);

		addRequestParameter("id", ID_STR_ETALON);
		addRequestParameter("firstName", FIRSTNAME_ETALON);
		addRequestParameter("lastName", LASTNAME_ETALON);
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}

	@Test
	public void testEditEmptyFirstName() {
		Date date = new Date();
		addRequestParameter("id", ID_STR_ETALON);
		addRequestParameter("lastName", LASTNAME_ETALON);
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}

	@Test
	public void testEditEmptyLastName() {
		Date date = new Date();
		addRequestParameter("id", ID_STR_ETALON);
		addRequestParameter("firstName", FIRSTNAME_ETALON);
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}

	@Test
	public void testEditEmptyDate() {
		Date date = new Date();
		addRequestParameter("id", ID_STR_ETALON);
		addRequestParameter("firstName", FIRSTNAME_ETALON);
		addRequestParameter("lastName", LASTNAME_ETALON);
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}

	@Test
	public void testEditEmptyDateIncorrect() {
		Date date = new Date();
		addRequestParameter("id", ID_STR_ETALON);
		addRequestParameter("firstName", FIRSTNAME_ETALON);
		addRequestParameter("lastName", LASTNAME_ETALON);
		addRequestParameter("date", "ab78cde12fg");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}

}
