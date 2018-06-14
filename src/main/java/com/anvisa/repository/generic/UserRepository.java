package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.userName=:userName and u.active = true")
	public User findLogin(@Param("userName") String userName);

	@Query("select u from User u where u.email=:email and u.active = true")
	public User findEmail(@Param("email") String email);

}
