package com.anvisa.interceptor.auditlog;

import java.time.LocalDateTime;

public class AuditLogUtil{
	
	   public static void LogIt(String action,
	     IAuditLog entity ){
			
	     
				
	     try {

		AuditLog auditRecord = new AuditLog(action,
			    LocalDateTime.now(), entity.getId(), entity.getClass().toString(),entity.getLogDeatil());

				
	     } catch (Exception e) {
			// TODO: handle exception
				
	     }		
	  }
	}
