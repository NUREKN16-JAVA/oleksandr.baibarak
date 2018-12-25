package ua.nure.kn.baibarak.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.baibarak.usermanagement.User;
import ua.nure.kn.baibarak.usermanagement.db.DaoFactory;
import ua.nure.kn.baibarak.usermanagement.db.DaoFactoryImpl;
import ua.nure.kn.baibarak.usermanagement.db.MockUserDao;
import ua.nure.kn.baibarak.usermanagement.db.MockDaoFactory;
import ua.nure.kn.baibarak.usermanagement.util.Messages;

public class MainFrameTest extends JFCTestCase {

	private static final Long ID_ADD_USER_ETALON = 1L;
	private static final String LASTNAME_ADD_USER_ETALON = "Doe";
	private static final String FIRSTNAME_ADD_USER_ETALON = "John";
	private static final int NUM_ROW_AFTER_ADD_USER_ETALON = 1;
	private static final int NUM_ROW_BEFORE_ADD_USER_ETALON = 0;
	private static final int NUM_COLUMN_ETALON = 3;
	private MainFrame mainFrame;
	private Mock mockUserDao;

	protected void setUp() throws Exception {
		super.setUp();

		Properties properties = new Properties();
		properties.setProperty("dao.factory", MockDaoFactory.class.getName());
		DaoFactory.init(properties);
		mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
		mockUserDao.expectAndReturn("findAll", new ArrayList());
		setHelper(new JFCTestHelper());
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component find(Class<?> componentClass, String name) {
		Component component;
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component '" + name + "'", component);
		return component;
	}

	public void testBrowseControls() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(NUM_COLUMN_ETALON, table.getColumnCount());
		assertEquals(Messages.getString("UserTableModel.ID"), table.getColumnName(0));
		assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1));
		assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2));

		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}

	public void testAddUser() {
		Date now = new Date();
		User user = new User(FIRSTNAME_ADD_USER_ETALON, LASTNAME_ADD_USER_ETALON, now);
		
		User expectedUser = new User(ID_ADD_USER_ETALON, FIRSTNAME_ADD_USER_ETALON, LASTNAME_ADD_USER_ETALON, now);
		mockUserDao.expectAndReturn("create", user, expectedUser);
		
		ArrayList users = new ArrayList();
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(NUM_ROW_BEFORE_ADD_USER_ETALON, table.getRowCount());

		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class, "addPanel");

		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

		JButton okButton = (JButton) find(JButton.class, "okButton");
		find(JButton.class, "cancelButton");

		getHelper().sendString(new StringEventData(this, firstNameField, FIRSTNAME_ADD_USER_ETALON));
		getHelper().sendString(new StringEventData(this, lastNameField, LASTNAME_ADD_USER_ETALON));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(NUM_ROW_AFTER_ADD_USER_ETALON, table.getRowCount());

	}

}
