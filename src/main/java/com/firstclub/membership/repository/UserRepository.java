package com.firstclub.membership.repository;

import com.firstclub.membership.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link User} entities.
 * <p>
 * Provides standard CRUD operations and custom query methods if needed.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
