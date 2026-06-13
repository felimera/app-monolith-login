package com.project.app_login_back.application.repository;

import com.project.app_login_back.domain.models.entity.User;
import com.project.app_login_back.domain.models.entity.User_;
import com.project.app_login_back.domain.repository.IUserCriteriaRepository;
import com.project.app_login_back.insfraestructure.util.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class IUserCriteriaRepositoryImpl implements IUserCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getConsultUserDifferentCriteria(Map<String, String> criterios) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);

        userRoot.join(User_.ROL, JoinType.INNER);
        userRoot.fetch(User_.ROL, JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        String key = criterios.keySet()
                .stream()
                .findFirst()
                .orElse(null);

        if (Constants.E.equals(key))
            predicates.add(cb.equal(userRoot.get(User_.email), criterios.get(key)));
        if (Constants.U.equals(key))
            predicates.add(cb.equal(userRoot.get(User_.username), criterios.get(key)));


        if (predicates.isEmpty()) return Optional.empty();

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<User> query = entityManager.createQuery(cq);

        try {
            // Devuelve un solo objeto User
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            // Es vital capturar esto, si no la app lanza un error 500
            return Optional.empty();
        }
    }
}
