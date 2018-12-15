package ua.nure.kn.baibarak.usermanagement.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.kn.baibarak.usermanagement.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	private static final String LASTNAME_FIND_ETALON = "Gates";
	private static final String FIRSTNAME_FIND_ETALON = "Bill";

	private static final int TABLE_SIZE_ETALON = 2;

	private static final String LASTNAME_CREATE_ETALON = "Baibarak";
	private static final String FIRSTNAME_CREATE_UPDATE_ETALON = "Aleksandr";

	private static final Long ID_FIND_ETALON = 1000L;

	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;

	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}

	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName(FIRSTNAME_CREATE_UPDATE_ETALON);
			user.setLastName(LASTNAME_CREATE_ETALON);
			user.setDateOfBirth(new Date());
			assertNull(user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testFindAll() {
		try {
			Collection<User> collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size", TABLE_SIZE_ETALON, collection.size());

		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testUpdate() throws DatabaseException {
		User user = dao.find(ID_FIND_ETALON);
		assertNotNull(user);
		user.setFirstName(FIRSTNAME_CREATE_UPDATE_ETALON);
		dao.update(user);
		User updatedUser = dao.find(ID_FIND_ETALON);
		assertEquals(user.getFirstName(), updatedUser.getFirstName());
	}

	public void testDelete() {
		User deletedUser = new User();
		deletedUser.setId(ID_FIND_ETALON);
		try {
			dao.delete(deletedUser);
			assertNull(dao.find(ID_FIND_ETALON));
		} catch (DatabaseException e) {
			String left = e.getMessage();
			String right = ID_FIND_ETALON.toString();
			left = left.substring(left.length() - right.length());
			assertTrue(left.equals(right));
		}
	}

	public void testFindById() throws DatabaseException {
		User userToCheck = dao.find(ID_FIND_ETALON);
		assertNotNull(userToCheck);
		assertEquals(FIRSTNAME_FIND_ETALON, userToCheck.getFirstName());
		assertEquals(LASTNAME_FIND_ETALON, userToCheck.getLastName());
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement", "sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}