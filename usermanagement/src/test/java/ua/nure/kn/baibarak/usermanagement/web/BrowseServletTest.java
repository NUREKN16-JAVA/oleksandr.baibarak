package ua.nure.kn.baibarak.usermanagement.web;

import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.baibarak.usermanagement.User;

public class BrowseServletTest extends MockServletTestCase {

	private static final String ID_STR_ETALON = "1000";
	private static final Long ID_ETALON = 1000L;
	private static final String LASTNAME_ETALON = "Doe";
	private static final String FIRSTNAME_ETALON = "John";

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testBrowse() {
		User user = new User(ID_ETALON, FIRSTNAME_ETALON, LASTNAME_ETALON, new Date());
		List list = Collections.singletonList(user);
		getMockUserDao().expectAndReturn("findAll", list);
		doGet();
		Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotNull("Could not find list of users in session", collection);
		assertSame(list, collection);
	}

	@Test
	public void testEdit() {
		User user = new User(ID_ETALON, FIRSTNAME_ETALON, LASTNAME_ETALON, new Date());
		getMockUserDao().expectAndReturn("find", ID_ETALON, user);
		addRequestParameter("editButton", "Edit");
		addRequestParameter("id", ID_STR_ETALON);
		doPost();
		User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull("Could not find user in session", user);
		assertSame(user, userInSession);
	}

	@Test
	public void testDelete() {
		User user = new User(ID_ETALON, FIRSTNAME_ETALON, LASTNAME_ETALON, new Date());
		getMockUserDao().expectAndReturn("find", ID_ETALON, user);
		getMockUserDao().expect("delete", user);
		List<User> list = new ArrayList<User>();
		getMockUserDao().expectAndReturn("findAll", list);
		addRequestParameter("deleteButton", "Delete");
		addRequestParameter("id", ID_STR_ETALON);
		doPost();
		List<User> users = (List<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotNull("Couldn't find users in session", users);
		assertSame(list, users);
	}

	@Test
	public void testDetails() {
		User user = new User(ID_ETALON, FIRSTNAME_ETALON, LASTNAME_ETALON, new Date());
		getMockUserDao().expectAndReturn("find", ID_ETALON, user);
		addRequestParameter("id", ID_STR_ETALON);
		addRequestParameter("detailsButton", "Details");
		doGet();
		User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull("Could not find user in session", user);
		assertSame(user, userInSession);
	}

}
