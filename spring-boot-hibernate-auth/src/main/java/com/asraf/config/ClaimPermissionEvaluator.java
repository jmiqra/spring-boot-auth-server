package com.asraf.config;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.asraf.utils.JwtUtils;

public class ClaimPermissionEvaluator implements PermissionEvaluator {

	private JwtUtils jwtUtils;

	@Autowired
	public ClaimPermissionEvaluator(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	/*
	 * hasPermission(param1, param2)
	 */
	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		if ((auth == null) || (targetDomainObject == null) || (permission == null)) {
			return false;
		}
		String[] claimTypes = targetDomainObject.toString().split(",");
		String[] claimValues = permission.toString().split(",");
		try {
			for (int I = 0; I < claimTypes.length; I++) {
				Object payloadValue = jwtUtils.getPayloadValue(claimTypes[I]);
				if (payloadValue == null || !payloadValue.toString().equals(claimValues[I])) {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * hasPermission(param1, param2, param3)
	 */
	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
		if ((auth == null) || (targetType == null) || (permission == null)) {
			return false;
		}
		return false;
	}

}