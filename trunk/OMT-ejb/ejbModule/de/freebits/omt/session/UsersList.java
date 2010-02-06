package de.freebits.omt.session;

import de.freebits.omt.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("usersList")
public class UsersList extends EntityQuery<Users> {

	private static final long serialVersionUID = -8878350970743404482L;

	private static final String EJBQL = "select users from Users users";

	private static final String[] RESTRICTIONS = {
			"lower(users.firstname) like lower(concat(#{usersList.users.firstname},'%'))",
			"lower(users.lastname) like lower(concat(#{usersList.users.lastname},'%'))", };

	private Users users = new Users();

	public UsersList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		//setMaxResults(25);
	}

	public Users getUsers() {
		return users;
	}
}
