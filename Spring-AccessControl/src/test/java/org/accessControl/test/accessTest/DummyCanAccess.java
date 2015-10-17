package org.accessControl.test.accessTest;

import org.accessControl.persistence.annotation.AccessControl;

public class DummyCanAccess {

	@AccessControl( resource = "ACRESOUR01", action="ACACTION2")
	public void metodoDummy(String profile) {
		System.out.println("Di hola");
	}

}
