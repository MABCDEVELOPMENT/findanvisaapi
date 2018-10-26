package com.anvisa.repository.generic;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.ScheduledEmail;
import com.anvisa.model.persistence.User;

public interface RepositoryScheduledEmail extends JpaRepository<ScheduledEmail, Long> {

	@Query("select s from ScheduledEmail s where s.sent=:sent")
	public Collection<ScheduledEmail> getAll(@Param("sent") boolean sent);
	
	@Query("select u from ScheduledEmail u where u.insertUser=:user")
	public Collection<ScheduledEmail> findUses(@Param("user") User user);

}
