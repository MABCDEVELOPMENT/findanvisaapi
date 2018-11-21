package com.anvisa.repository.generic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.userName=:userName and u.active = true")
	public User findLogin(@Param("userName") String userName);

	@Query("select u from User u where u.email=:email and u.active = true")
	public User findEmailIsActive(@Param("email") String email);
	
	@Query("select u from User u where u.email=:email")
	public User findEmail(@Param("email") String email);

	@Query("select u from User u where u.id=:id")
	public User findId(@Param("id") Long id);
	
	@Query("select u from User u where u.password=null and (u.active = false or u.active = null)")
	public List<User> findWaitingForApproval();
	
	@Query("select u from User u where u.receiveActivation = true and profile <> 2")
	public List<User> findSendActivation();


}
