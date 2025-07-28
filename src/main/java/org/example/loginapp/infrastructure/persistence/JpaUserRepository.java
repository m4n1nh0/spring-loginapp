package org.example.loginapp.infrastructure.persistence;

import org.example.loginapp.domain.model.User;
import org.example.loginapp.domain.model.UserId;
import org.example.loginapp.domain.repository.UserRepository;
import org.example.loginapp.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataJpaUserRepository springDataJpaUserRepository;

    public JpaUserRepository(SpringDataJpaUserRepository springDataJpaUserRepository) {
        this.springDataJpaUserRepository = springDataJpaUserRepository;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return springDataJpaUserRepository.findById(id.getValue())
                .map(this::toDomainUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return springDataJpaUserRepository.findByUsername(username)
                .map(this::toDomainUser);
    }

    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = toJpaEntity(user);
        UserJpaEntity savedEntity = springDataJpaUserRepository.save(jpaEntity);
        return toDomainUser(savedEntity);
    }

    @Override
    public void delete(User user) {
        springDataJpaUserRepository.deleteById(user.getId().getValue());
    }

    private User toDomainUser(UserJpaEntity entity) {
        return User.from(
                UserId.of(entity.getId()),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRoles()
        );
    }

    private UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        if (user.getId() != null) {
            entity.setId(user.getId().getValue());
        } else {
            entity.setId(UUID.randomUUID());
            user.getId().of(entity.getId());
        }
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword().getHashedValue());
        entity.setRoles(user.getRolesAsString());
        return entity;
    }
}

interface SpringDataJpaUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByUsername(String username);
}
