package ua.nure.kn.baibarak.usermanagement.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.kn.baibarak.usermanagement.User;

public class MockUserDao implements UserDao {

	private long id = 0;
	private Map users = new HashMap();
	
	@Override
	public User create(User user) throws DatabaseException {
		Long currentId = new Long(++id);
		user.setId(currentId);
		users.put(currentId, user);
		return user;
	}

	@Override
	public User find(Long id) throws DatabaseException {
		return (User) users.get(id);
	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		return users.values();
	}

	@Override
	public void update(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
		users.put(currentId, user);
	}

	@Override
	public void delete(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
	}

	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<User> find(String firstName, String lastName) throws DatabaseException {
		throw new UnsupportedOperationException();
	}

}
