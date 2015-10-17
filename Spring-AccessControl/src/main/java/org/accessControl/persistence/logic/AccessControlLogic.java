package org.accessControl.persistence.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.accessControl.persistence.bean.AccessControlResource;
import org.accessControl.persistence.bean.AccessControlResourceType;
import org.accessControl.persistence.bean.AccessControlRight;
import org.accessControl.persistence.bean.AccessControlTypeBehavior;
import org.accessControl.persistence.bean.AccessControlUser;
import org.accessControl.persistence.exception.CannotAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lobo
 * 
 */

public class AccessControlLogic {

	private static final String USER_PROFILE="ACPROFILE2";
	private static final String ADMIN_PROFILE="ACPROFILE3";
	/**
	 * hibernateTemplate: implements transaction control
	 */
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * @param profile
	 *            Profile of the user who tries to access to the resource
	 * @param resource
	 *            Resource to be accessed
	 * @param actionToPerform
	 *            Action to perform by the user on the resource
	 * @throws CannotAccessException
	 * 
	 */
	// Hay un problema con los métodos final, no pueden ser pasados por
	// Proxy(Obviamente ya que no son sobreescribibles) así que al poner
	// @Transactional no se resuelve bien la transacción y hace cosas raras. Con
	// esto mantiene bien las sessiones y todo
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Boolean canAccess(final String profile, final String resource,
			final String actionToPerform) throws CannotAccessException {

		// result variable
		Boolean canAccess = false;

		List<AccessControlResourceType> possibleActions;
		boolean actionFound = false;
		// First step: checking rather the action can be performed on the
		// resource

		if (isActionPossibleOnResource(resource, actionToPerform)) {

			// Second step: checking rather the action can be performed by the
			// user
			// profile on that resource
			Boolean accessRights = null;

			accessRights = getRights(profile, resource, actionToPerform);
			if (accessRights) {
				canAccess = true;
			}

		}
		return canAccess;

	}

	// en base a esto ya se puede implentar caché. Hacerlo
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	private Boolean getRights(final String profile, final String resource,
			final String actionToPerform) {
		// TODO Auto-generated method stub
		Boolean isPossible = false;
		ArrayList<AccessControlResource> lResource = (ArrayList<AccessControlResource>) hibernateTemplate
				.find("from AccessControlResource where codResource=?",
						resource);
		if (!lResource.isEmpty()) {
			AccessControlResource oResource = lResource.get(0);
			Set<AccessControlRight> rights = oResource.getAccessControlRights();
			for (AccessControlRight right : rights) {
				if (right.getAccessControlAction().getCodAction()
						.equals(actionToPerform)) {
					isPossible = true;
					break;
				}
			}
		}
		return isPossible;
	}

	// en base a esto ya se puede implentar caché. Hacerlo
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	private Boolean isActionPossibleOnResource(String resource, String action)
			throws CannotAccessException {
		Boolean isPossible = false;
		ArrayList<AccessControlResource> lResource = (ArrayList<AccessControlResource>) hibernateTemplate
				.find("from AccessControlResource where codResource=?",
						resource);

		if (!lResource.isEmpty()) {

			AccessControlResource oResource = lResource.get(0);
			AccessControlResourceType resourceType = oResource
					.getAccessControlResourceType();
			Set<AccessControlTypeBehavior> actionByType = resourceType
					.getAccessControlTypeBehaviors();
			for (AccessControlTypeBehavior actionBehavior : actionByType) {
				if (actionBehavior.getAccessControlAction().getCodAction()
						.equals(action)) {
					isPossible = true;
					break;
				}
			}
		}
		return isPossible;

	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public String doLoginAndGetProfile(String login, String password) {
		String profile = "";
		String[] credential = { login, password };
		ArrayList<AccessControlUser> userData = (ArrayList<AccessControlUser>) hibernateTemplate
				.find("from AccessControlUser where userLogin=? and userPassword=?",
						credential);
		if (!userData.isEmpty()) {
			profile = userData.get(0).getCodProfile();
		}
		return profile;
	}
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void createUserAccount(String login, String password) throws CannotAccessException{
		createAccount(login, password, USER_PROFILE);
		
	}
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void createAdminAccount(String login, String password) throws CannotAccessException{
		createAccount(login, password, ADMIN_PROFILE);
		
	}

	@Transactional(rollbackFor = {DataAccessException.class, CannotAccessException.class}, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	private void createAccount(String login, String password, String profile) throws CannotAccessException{
		AccessControlUser user = new AccessControlUser();
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
		user.setCreateDate(today);
		user.setUpdateDate(today);
		user.setUpdateUser(login);
		user.setUpdateProgram("org.accessControl.persistence.logic.AccessControlLogic.createAccount");
		user.setCodProfile(profile);
		user.setNombre(login);
		user.setUserLogin(login);
		user.setUserPassword(password);
		//Comprobación de que no existe ya ni en la session ni en la base de datos
		if(hibernateTemplate.contains(user)||null!=hibernateTemplate.get(AccessControlUser.class, login)){
			throw new CannotAccessException("Error, ya existe usuario con Login "+login);
		}
		hibernateTemplate.save(user);
	}
}
