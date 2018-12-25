package ua.nure.kn.baibarak.usermanagement.agent;

import ua.nure.kn.baibarak.usermanagement.db.DatabaseException;

public class SearchException extends Exception {
	
	public SearchException(DatabaseException e) {
        super(e);
    }
}
