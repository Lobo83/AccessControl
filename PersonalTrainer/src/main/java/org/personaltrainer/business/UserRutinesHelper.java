package org.personaltrainer.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.personaltrainer.persistence.bean.Exercise;
import org.personaltrainer.persistence.bean.ExerciseMeasure;
import org.personaltrainer.persistence.bean.ExerciseRutine;
import org.personaltrainer.persistence.bean.ExerciseRutineHistoric;
import org.personaltrainer.persistence.bean.Rutine;
import org.personaltrainer.persistence.bean.UserData;
import org.personaltrainer.web.beans.rutine.RecordRutine;
import org.personaltrainer.web.beans.rutine.RutineList;
import org.personaltrainer.web.beans.rutine.RutineListRecord;
import org.personaltrainer.web.beans.rutine.TableRutine;
import org.personaltrainer.web.controller.RutineManager;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserRutinesHelper {
	private static final Log log = LogFactory.getLog(RutineManager.class);
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, propagation = Propagation.REQUIRED)
	public Map<String, String> getExercises() {
		Map<String, String> result = new LinkedMap();
		List<Exercise> lExercises = hibernateTemplate
				.find("from Exercise order by name asc");
		for (Exercise ejercicio : lExercises) {
			String key = new String(ejercicio.getIdExercise() + "");
			result.put(key, ejercicio.getName());
		}
		return result;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, propagation = Propagation.REQUIRED)
	public Map<String, String> getMeasures() {
		Map<String, String> result = new LinkedHashMap<String, String>();
		List<ExerciseMeasure> lMeasures = hibernateTemplate
				.find("from ExerciseMeasure order by nameType asc");
		for (ExerciseMeasure measure : lMeasures) {

			result.put(measure.getCodExerciseMeasureType(),
					measure.getNameType());
		}
		return result;
	}

	@Transactional(rollbackFor = { DataAccessException.class }, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void saveRutine(TableRutine table) {

		List<ExerciseRutine> rutine = new ArrayList<ExerciseRutine>();
		List<RecordRutine> exercises = table.getTable();
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
		Rutine rutineRecord;
		Serializable idRutine;
		if (table.getIdRutine() == 0) {// new rutine
			rutineRecord = new Rutine();
			UserData userData = (UserData) hibernateTemplate.load(
					UserData.class, table.getIdUser(), LockMode.READ);
			rutineRecord.setName(table.getRutineName());
			rutineRecord.setDescription(table.getRutineDescription());
			rutineRecord.setUserData(userData);

			rutineRecord.setCreateDate(today);
			rutineRecord.setUpdateDate(today);
			rutineRecord.setUpdateUser("" + table.getIdUser());
			rutineRecord
					.setUpdateProgram("org.personaltrainer.business.UserRutinesHelper.saveRutine");

			idRutine = (Integer) hibernateTemplate.save("Rutine", rutineRecord);

		} else {
			rutineRecord = (Rutine) hibernateTemplate.load(Rutine.class,
					table.getIdRutine(), LockMode.UPGRADE);
			rutineRecord.setDescription(table.getRutineDescription());
			rutineRecord.setName(table.getRutineName());
			hibernateTemplate.update(rutineRecord);
		}
		if (null != exercises && exercises.size() > 0) {
			for (RecordRutine exercise : exercises) {
				ExerciseMeasure measure = (ExerciseMeasure) hibernateTemplate
						.load(ExerciseMeasure.class,
								exercise.getExerciseMeasure(), LockMode.READ);
				Exercise exerciseRecord = (Exercise) hibernateTemplate
						.load(Exercise.class, exercise.getIdExercise(),
								LockMode.READ);
				ExerciseRutine recordRutine;
				boolean newRecord = false;
				if (exercise.getIdRecordRutine() == 0) {// new record

					recordRutine = new ExerciseRutine();
					recordRutine.setCreateDate(today);
					newRecord = true;
				} else {

					recordRutine = (ExerciseRutine) hibernateTemplate.load(
							ExerciseRutine.class, exercise.getIdRecordRutine(),
							LockMode.UPGRADE);

				}
				recordRutine.setDuration(Long.valueOf(exercise.getDuration()).intValue());
				recordRutine.setExerciseMeasure(measure);
				recordRutine.setRutine(rutineRecord);
				recordRutine.setExercise(exerciseRecord);
				recordRutine.setSeriesNumber(Long.valueOf(exercise.getSeriesNumber())
						.intValue());
				recordRutine.setExerciseOrder(Long.valueOf(exercise.getOrder()).intValue());
				recordRutine.setWeight(BigDecimal.valueOf(Double.valueOf(exercise.getWeight())));

				recordRutine.setUpdateDate(today);
				recordRutine.setUpdateUser("" + table.getIdUser());
				recordRutine
						.setUpdateProgram("org.personaltrainer.business.UserRutinesHelper.saveRutine");
				// rutine.add(recordRutine);

				if (newRecord) {
					hibernateTemplate.save(recordRutine);
				} else {
					hibernateTemplate.update(recordRutine);
				}
				saveHistory(recordRutine);

			}
		}
		// hibernateTemplate.saveOrUpdateAll(rutine);

	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
	public RutineList getRutineList(Integer idUser) {
		// TODO Auto-generated method stub
		List<Rutine> list = hibernateTemplate.find(
				"from Rutine where userData.idUserData=?", idUser);
		RutineList result = new RutineList();
		List<RutineListRecord> table = new ArrayList<RutineListRecord>();
		for (Rutine record : list) {
			RutineListRecord data = new RutineListRecord();
			data.setDescription(record.getDescription());
			data.setIdRutine(record.getIdRutine());
			data.setName(record.getName());
			table.add(data);
		}
		result.setTable(table);

		return result;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
	public TableRutine getRutineById(Integer idRutine) {
		// TODO Auto-generated method stub
		Rutine rutine = (Rutine) hibernateTemplate.load(Rutine.class, idRutine,
				LockMode.READ);
		TableRutine result = new TableRutine();
		result.setIdRutine(idRutine);
		result.setIdUser(rutine.getUserData().getIdUserData());// creo que esto
																// no sería
																// necesario
		result.setRutineName(rutine.getName());
		result.setRutineDescription(rutine.getDescription());

		@SuppressWarnings("unchecked")
		List<ExerciseRutine> exercises = hibernateTemplate.find(
				" from ExerciseRutine where rutine.idRutine=?  order by exerciseOrder asc", idRutine);
		int numExercise = exercises.size();
		List<RecordRutine> listExercises = new ArrayList<RecordRutine>();

		for (int i = 0; i < numExercise; i++) {
			ExerciseRutine exercise = (ExerciseRutine) exercises.get(i);
			RecordRutine record = new RecordRutine();
			record.setDuration(String.valueOf(exercise.getDuration()));
			record.setExerciseMeasure(exercise.getExerciseMeasure()
					.getCodExerciseMeasureType());
			record.setOrder(String.valueOf(exercise.getExerciseOrder()));
			record.setIdExercise(exercise.getExercise().getIdExercise());
			record.setSeriesNumber(String.valueOf(exercise.getSeriesNumber()));
			record.setWeight(String.valueOf(exercise.getWeight()));
			record.setIdRecordRutine(exercise.getIdExerciseRutine());
			record.setLineNumber(i);
			listExercises.add(record);
		}
		result.setTable(listExercises);
		return result;
	}

	@Transactional(rollbackFor = { DataAccessException.class }, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public TableRutine removeRutineExercise(Integer idRecordRutine) {
		// TODO Auto-generated method stub

		ExerciseRutine exercise = (ExerciseRutine) hibernateTemplate.load(
				ExerciseRutine.class, idRecordRutine);
		Integer idRutine = exercise.getRutine().getIdRutine();
		// TODO Hay que eliminar también el historico. Activo borrado en
		// cascada. Borrará el histórico
		hibernateTemplate.delete(exercise);
		return getRutineById(idRutine);

	}

	@Transactional(rollbackFor = { DataAccessException.class }, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	private void saveHistory(ExerciseRutine record) {
		ExerciseRutineHistoric historicRecord = new ExerciseRutineHistoric();
		historicRecord.setDuration(record.getDuration());
		historicRecord.setExercise(record.getExercise());
		historicRecord.setExerciseMeasure(record.getExerciseMeasure());
		historicRecord.setRutine(record.getRutine());
		historicRecord.setSeriesNumber(record.getSeriesNumber());
		historicRecord.setWeight(record.getWeight());
		historicRecord.setExerciseRutine(record);

		java.util.Date today = new java.util.Date(System.currentTimeMillis());
		historicRecord.setChangeDate(today);
		historicRecord.setCreateDate(today);
		historicRecord.setUpdateDate(today);
		historicRecord
				.setUpdateProgram("org.personaltrainer.business.UserRutinesHelper.saveHistory");
		historicRecord.setUpdateUser(record.getUpdateUser());
		hibernateTemplate.save(historicRecord);
	}
}
