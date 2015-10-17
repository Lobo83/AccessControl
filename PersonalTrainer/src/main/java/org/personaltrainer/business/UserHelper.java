package org.personaltrainer.business;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.accessControl.persistence.annotation.AccessControl;
import org.accessControl.persistence.bean.AccessControlUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.personaltrainer.persistence.bean.UserData;
import org.personaltrainer.persistence.bean.UserDataHistoric;
import org.personaltrainer.web.beans.user.UserDataBean;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lobo
 * 
 */
public class UserHelper {

	private static final String RESOURCE_USER_PAGE = "ACRESOUR01";
	private static final String ACTION_USER_PAGE = "ACACTION1";

	private static final Log log = LogFactory.getLog(UserHelper.class);

	private HibernateTemplate hibernateTemplate;
	private BioMeasureHelper bioMeasureHelper;

	public BioMeasureHelper getBioMeasureHelper() {
		return bioMeasureHelper;
	}

	public void setBioMeasureHelper(BioMeasureHelper bioMeasureHelper) {
		this.bioMeasureHelper = bioMeasureHelper;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * This method getUserDataBean for a given user. Profile parameter is
	 * necessary to check rather this operation can be performed and must be the
	 * first parameter according to AccessControlLogic
	 * 
	 * @param profile
	 * @param user
	 * @return UserDataBean
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@AccessControl(resource = RESOURCE_USER_PAGE, action = ACTION_USER_PAGE)
	public UserDataBean getUserDataByLogin(String profile, String login) {
		UserDataBean userData = null;
		ArrayList<UserData> lUserData = (ArrayList<UserData>) hibernateTemplate
				.find(" from UserData AS user  where user.accessControlUser.userLogin=?",
						login);
		// ArrayList<UserData> lUserData=(ArrayList<UserData>)
		// hibernateTemplate.find(" from UserData ");
		if (lUserData.isEmpty()) {
			log.info("No se han encontrado usuarios con login " + login);
			return null;
		}

		return UserData2Bean(lUserData.get(0));
	}

	/**
	 * This method getUserDataBean for a given user. Profile parameter is
	 * necessary to check rather this operation can be performed and must be the
	 * first parameter according to AccessControlLogic
	 * 
	 * @param profile
	 * @param user
	 * @return UserDataBean
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@AccessControl(resource = RESOURCE_USER_PAGE, action = ACTION_USER_PAGE)
	public UserDataBean createDefaultUserData(String profile, String login) {

		UserData userData = new UserData();
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
		userData.setCreateDate(today);
		userData.setUpdateDate(today);
		userData.setUpdateProgram("org.personaltrainer.business.UserHelper.createDefaultUserData");
		userData.setUpdateUser(login);

		userData.setName(login);

		AccessControlUser user = (AccessControlUser) hibernateTemplate.load(
				AccessControlUser.class, login);
		userData.setAccessControlUser(user);
		hibernateTemplate.save(userData);
		saveHistoricData(userData);
		return UserData2Bean(userData);
	}

	/**
	 * This method getUserDataBean for a given user. Profile parameter is
	 * necessary to check rather this operation can be performed and must be the
	 * first parameter according to AccessControlLogic
	 * 
	 * @param profile
	 * @param user
	 * @return UserDataBean
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@AccessControl(resource = RESOURCE_USER_PAGE, action = ACTION_USER_PAGE)
	public UserData getUserDataPersistenceByLogin(String profile, String login) {

		ArrayList<UserData> lUserData = (ArrayList<UserData>) hibernateTemplate
				.find(" from UserData AS user  where user.accessControlUser.userLogin=?",
						login);
		// ArrayList<UserData> lUserData=(ArrayList<UserData>)
		// hibernateTemplate.find(" from UserData ");
		if (lUserData.isEmpty()) {
			log.info("No se han encontrado usuarios con login " + login);
			return null;
		}

		return lUserData.get(0);
	}

	private UserDataBean UserData2Bean(UserData userData) {
		UserDataBean bean = new UserDataBean();
		bean.setAge(userData.getAge());
		bean.setBelly(userData.getBelly());
		bean.setChest(userData.getChest());
		bean.setEmail(userData.getMail());
		bean.setHeartrate(userData.getBaseHeartRate());
		bean.setHeight(userData.getHeight());
		bean.setHip(userData.getHip());
		bean.setICA(userData.getBellyHeightRate());
		bean.setIGC(userData.getIgc());
		bean.setImc(userData.getImc());
		bean.setLogin(userData.getAccessControlUser().getUserLogin());
		bean.setMaxheartrate(userData.getMaxHeartRate());
		bean.setHeartrateA(userData.getHeartRatea());
		bean.setHeartrateB(userData.getHeartRateb());
		bean.setHeartrateC(userData.getHeartRatec());
		bean.setHeartrateD(userData.getHeartRated());
		bean.setHeartrateE(userData.getHeartRatee());
		bean.setMbasal(userData.getMb());
		bean.setName(userData.getName());
		bean.setProfile(userData.getAccessControlUser().getCodProfile());
		bean.setSex(userData.getSex());
		bean.setWeight(userData.getWeight());
		bean.setIdUserData(userData.getIdUserData());
		bean.setMcm(userData.getMcm());
		return bean;
	}

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@AccessControl(resource = RESOURCE_USER_PAGE, action = ACTION_USER_PAGE)
	public void saveUserData(String profile, UserDataBean userData)
			throws DataAccessException {
		UserData persistenceUserData;
		if (userData.getIdUserData() == null) {
			persistenceUserData = new UserData();
		} else {
			persistenceUserData = (UserData) hibernateTemplate.load(
					UserData.class, userData.getIdUserData(), LockMode.READ);
		}

		AccessControlUser credentials = (AccessControlUser) hibernateTemplate
				.load(AccessControlUser.class, userData.getLogin(),
						LockMode.READ);
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
		
		persistenceUserData.setAccessControlUser(credentials);
		persistenceUserData.setMail(userData.getEmail());

		persistenceUserData.setName(userData.getName());
		persistenceUserData.setSex(userData.getSex());
		if (null != userData.getHeartrate() && null != userData.getBelly()
				&& null != userData.getHeight() && null != userData.getHip()&& null!=userData.getAge()) {
			persistenceUserData.setAge(userData.getAge());
			persistenceUserData.setBaseHeartRate(userData.getHeartrate());
			persistenceUserData.setBelly(userData.getBelly());
			persistenceUserData.setWeight(userData.getWeight());
			persistenceUserData.setHeight(userData.getHeight());
			persistenceUserData.setHip(userData.getHip());
			persistenceUserData.setChest(userData.getChest());
			
			// calculo de medidas biometricas y actualizacion de la userDataBean
			// para que se vuelvan a mostrar los datos actualizados donde haga
			// falta
			if (userData.getSex().equals("male")) {
				BigDecimal maxHearRate = this.bioMeasureHelper.getMeasureValue(
						"HRTRTMALE", userData);// Este valor no esta disponible
												// durante la creación del
												// usuario así que hay que
												// calcularlo antes para que
												// este disponible en el resto
												// de calculos
				BigDecimal igc = this.bioMeasureHelper.getMeasureValue(
						"IGCMALE", userData);
				BigDecimal mcm = this.bioMeasureHelper.getMeasureValue(
						"MCMMALE", userData);
				BigDecimal mb = this.bioMeasureHelper.getMeasureValue(
						"METBASMALE", userData);
				userData.setMaxheartrate(maxHearRate);
				userData.setIGC(igc);
				userData.setMcm(mcm);
				userData.setMbasal(mb);

				persistenceUserData.setMaxHeartRate(maxHearRate);
				persistenceUserData.setIgc(igc);
				persistenceUserData.setMcm(mcm);
				persistenceUserData.setMb(mb);
			} else {
				BigDecimal maxHearRate = this.bioMeasureHelper.getMeasureValue(
						"HRRTFEMALE", userData);// Este valor no esta disponible
												// durante la creación del
												// usuario así que hay que
												// calcularlo antes para que
												// este disponible en el resto
												// de calculos
				BigDecimal igc = this.bioMeasureHelper.getMeasureValue(
						"IGCFEMALE", userData);
				BigDecimal mcm = this.bioMeasureHelper.getMeasureValue(
						"MCMFEMALE", userData);
				BigDecimal mb = this.bioMeasureHelper.getMeasureValue(
						"MBASFEMALE", userData);
				userData.setMaxheartrate(maxHearRate);
				userData.setIGC(igc);
				userData.setMcm(mcm);
				userData.setMbasal(mb);
				persistenceUserData.setMaxHeartRate(maxHearRate);
				persistenceUserData.setIgc(igc);
				persistenceUserData.setMcm(mcm);
				persistenceUserData.setMb(mb);
			}
			String heartRateA = this.bioMeasureHelper.getMeasureValue(
					"MINHRRATEA", userData)
					+ " - "
					+ this.bioMeasureHelper.getMeasureValue("MAXHRRATEA",
							userData);
			String heartRateB = this.bioMeasureHelper.getMeasureValue(
					"MINHRRATEB", userData)
					+ " - "
					+ this.bioMeasureHelper.getMeasureValue("MAXHRRATEB",
							userData);
			String heartRateC = this.bioMeasureHelper.getMeasureValue(
					"MINHRRATEC", userData)
					+ " - "
					+ this.bioMeasureHelper.getMeasureValue("MAXHRRATEC",
							userData);
			String heartRateD = this.bioMeasureHelper.getMeasureValue(
					"MINHRRATED", userData)
					+ " - "
					+ this.bioMeasureHelper.getMeasureValue("MAXHRRATED",
							userData);
			String heartRateE = this.bioMeasureHelper.getMeasureValue(
					"MINHRRATEE", userData)
					+ " - "
					+ this.bioMeasureHelper.getMeasureValue("MAXHRRATEE",
							userData);
			BigDecimal imc = this.bioMeasureHelper.getMeasureValue("IMC",
					userData);
			BigDecimal ica = this.bioMeasureHelper.getMeasureValue("ICA",
					userData);

			userData.setHeartrateA(heartRateA);
			userData.setHeartrateB(heartRateB);
			userData.setHeartrateC(heartRateC);
			userData.setHeartrateD(heartRateD);
			userData.setHeartrateE(heartRateE);
			userData.setImc(imc);
			userData.setICA(ica);

			persistenceUserData.setHeartRatea(heartRateA);
			persistenceUserData.setHeartRateb(heartRateB);
			persistenceUserData.setHeartRatec(heartRateC);
			persistenceUserData.setHeartRated(heartRateD);
			persistenceUserData.setHeartRatee(heartRateE);
			persistenceUserData.setImc(imc);
			persistenceUserData.setBellyHeightRate(ica);
		}

		// setting auditory camps before saving
		if (userData.getIdUserData() == null) {
			persistenceUserData.setCreateDate(today);// new record. Set
														// createDate

		} else {
			persistenceUserData.setIdUserData(userData.getIdUserData());// Hay
																		// que
																		// mirar
																		// lo
																		// del
																		// OPTIMIST_LOCK
																		// para
																		// el
																		// update.
																		// Hibernate
																		// ya
																		// contempla
																		// esto
		}

		persistenceUserData.setUpdateDate(today);
		persistenceUserData.setUpdateUser(userData.getLogin());
		persistenceUserData
				.setUpdateProgram("org.personaltrainer.business.UserHelper.saveUserData");

		hibernateTemplate.saveOrUpdate(persistenceUserData);
		saveHistoricData(persistenceUserData);

	}

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	private void saveHistoricData(UserData persistenceUserData) {
		UserDataHistoric historic = new UserDataHistoric();
		historic.setBaseHeartRate(persistenceUserData.getBaseHeartRate());
		historic.setBelly(persistenceUserData.getBelly());
		historic.setBellyHeightRate(persistenceUserData.getBellyHeightRate());
		historic.setChest(persistenceUserData.getChest());
		historic.setHeartRatea(persistenceUserData.getHeartRatea());
		historic.setHeartRateb(persistenceUserData.getHeartRateb());
		historic.setHeartRatec(persistenceUserData.getHeartRatec());
		historic.setHeartRated(persistenceUserData.getHeartRated());
		historic.setHeartRatee(persistenceUserData.getHeartRatee());
		historic.setHeight(persistenceUserData.getHeight());
		historic.setHip(persistenceUserData.getHip());
		historic.setIgc(persistenceUserData.getIgc());
		historic.setImc(persistenceUserData.getImc());
		historic.setMaxHeartRate(persistenceUserData.getMaxHeartRate());
		historic.setMb(persistenceUserData.getMb());
		historic.setMcm(persistenceUserData.getMcm());
		historic.setWeight(persistenceUserData.getWeight());

		// datos de auditoria
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

		historic.setCreateDate(today);
		historic.setUpdateDate(today);
		historic.setUpdateProgram(persistenceUserData.getUpdateProgram());
		historic.setUpdateUser(persistenceUserData.getUpdateUser());

		historic.setHistoricDate(today);
		historic.setUserData(persistenceUserData);
		hibernateTemplate.save(historic);
	}

}
