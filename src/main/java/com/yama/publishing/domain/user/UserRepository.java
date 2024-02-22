package com.yama.publishing.domain.user;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    public UserDetails findByLogin(String login);
}
