package ua.nure.kn.baibarak.usermanagement.db;

import java.util.Collection;

import ua.nure.kn.baibarak.usermanagement.User;

public interface UserDao {
	/**
	 * Add new record to DB with user
	 * @param user with null id
	 * @return modified user with auto generated id from DB
	 * @throws DatabaseException
	 */
	User create(User user) throws DatabaseException;
	
	/**
	 * Find record according to id in DB
	 * @param user with null id
	 * @return modified user with auto generated id from DB 
	 */
	User find(Long id) throws DatabaseException;
	
	/**
	 * Find all records in DB
	 * @return modified user with auto generated id from DB 
	 * @throws DatabaseException
	 */
	Collection<User> findAll() throws DatabaseException;
	
	/**
	 * Update record in DB
	 * @param user
	 * @throws DatabaseException
	 */
	void update(User user) throws DatabaseException;
	
	/**
	 * Delete record in DB
	 * @param user
	 * @throws DatabaseException
	 */
	void delete(User user) throws DatabaseException;
    
	/**
	 * Connects the user with the database
	 * @param ConnectionFactory
	 * @throws DatabaseException
	 */
	void setConnectionFactory(ConnectionFactory connectionFactory) throws DatabaseException;
}
