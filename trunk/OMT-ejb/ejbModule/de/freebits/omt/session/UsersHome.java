package de.freebits.omt.session;

import de.freebits.omt.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("usersHome")
public class UsersHome extends EntityHome<Users> {

	private static final long serialVersionUID = 4379059966201704587L;

	public void setUsersId(Integer id) {
		setId(id);
	}

	public Integer getUsersId() {
		return (Integer) getId();
	}

	@Override
	protected Users createInstance() {
		Users users = new Users();
		return users;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Users getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
