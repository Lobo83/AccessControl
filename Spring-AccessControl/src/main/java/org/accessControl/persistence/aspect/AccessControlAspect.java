package org.accessControl.persistence.aspect;

import org.accessControl.persistence.annotation.AccessControl;
import org.accessControl.persistence.logic.AccessControlLogic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AccessControlAspect {
	private AccessControlLogic accessLogic;
	private static final Log log=LogFactory.getLog(AccessControlAspect.class);
	
	public AccessControlLogic getAccessLogic() {
		return accessLogic;
	}

	public void setAccessLogic(AccessControlLogic accessLogic) {
		this.accessLogic = accessLogic;
	}

	@Around("@annotation(accessControl)")
	public Object checkPossibleExecution(ProceedingJoinPoint pjp,AccessControl accessControl) throws Throwable {
	    // start stopwatch
		Object[] arguments=pjp.getArgs();//accessing to arguments array. The first element must be the user profile
		
		Object retVal=null;
		if(accessLogic.canAccess((String)arguments[0],accessControl.resource(),accessControl.action())){
			 retVal = pjp.proceed();
		}else{
			log.info("El perfil "+(String)arguments[0]+" no puede realizar la acción "+accessControl.action()+" sobre el recurso "+accessControl.resource());
		}
		
	    // stop stopwatch
	    return retVal;
	  }
}
