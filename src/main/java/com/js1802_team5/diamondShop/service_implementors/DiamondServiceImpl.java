package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.services.IDiamondService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiamondServiceImpl implements IDiamondService {
    private final DiamondRepo diamondRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Diamond createDiamond(Diamond diamond) {
        Optional<Diamond> existingDiamond = diamondRepo.findByCertificateNumber(diamond.getCertificateNumber());
        if (existingDiamond.isPresent()) {
            return null;
        }
        return diamondRepo.save(diamond);
    }

    @Override
    public List<Diamond> getAllDiamond() {
        return diamondRepo.findAll();
    }

    @Override
    public Diamond getADiamond(Integer id) {
        Optional<Diamond> diamond = diamondRepo.findById(id);
        if (diamond.isPresent()) {
            return diamond.get();
        } else {
            throw new RuntimeException("Diamond not found with id " + id);
        }
    }

    @Override
    public List<Diamond> searchDiamond(DiamondRequest diamondRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Diamond> cq = cb.createQuery(Diamond.class);
        Root<Diamond> diamond = cq.from(Diamond.class);

        List<Predicate> predicates = new ArrayList<>();
        boolean hasConditions = false;

        if (diamondRequest.getCaratWeight() > 0) {
            predicates.add(cb.equal(diamond.get("caratWeight"), diamondRequest.getCaratWeight()));
            hasConditions = true;
        }
        if (diamondRequest.getColor() != null && !diamondRequest.getColor().isEmpty()) {
            predicates.add(cb.equal(diamond.get("color"), diamondRequest.getColor()));
            hasConditions = true;
        }
        if (diamondRequest.getClarity() != null && !diamondRequest.getClarity().isEmpty()) {
            predicates.add(cb.equal(diamond.get("clarity"), diamondRequest.getClarity()));
            hasConditions = true;
        }
        if (diamondRequest.getCut() != null && !diamondRequest.getCut().isEmpty()) {
            predicates.add(cb.equal(diamond.get("cut"), diamondRequest.getCut()));
            hasConditions = true;
        }
        if (diamondRequest.getOrigin() != null && !diamondRequest.getOrigin().isEmpty()) {
            predicates.add(cb.equal(diamond.get("origin"), diamondRequest.getOrigin()));
            hasConditions = true;
        }
        if (diamondRequest.getMin_price() > 0.0) {
            predicates.add(cb.greaterThanOrEqualTo(diamond.get("price"), diamondRequest.getMin_price()));
            hasConditions = true;
        }
        if (diamondRequest.getMax_price() >= 0.0 && (diamondRequest.getMin_price() <= diamondRequest.getMax_price())) {
            predicates.add(cb.lessThanOrEqualTo(diamond.get("price"), diamondRequest.getMax_price()));
            hasConditions = true;
        }

        if (!hasConditions) {
            return entityManager.createQuery(cq.select(diamond)).getResultList();
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
