package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.models.entity_models.SizeDiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.repositories.DiamondShellRepo;
import com.js1802_team5.diamondShop.repositories.SizeDiamondShellRepo;
import com.js1802_team5.diamondShop.repositories.SizeRepo;
import com.js1802_team5.diamondShop.services.DiamondShellService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiamondShellServiceImpl implements DiamondShellService {

    private final DiamondShellRepo diamondShellRepo;
    private final SizeRepo sizeRepo;
    private final SizeDiamondShellRepo sizeDiamondShellRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DiamondShell createDiamondShell(DiamondShell diamondShell) {
        return diamondShellRepo.save(diamondShell);
    }

    @Override
    public List<DiamondShell> getAllDiamondShell() {
        return diamondShellRepo.findAll();
    }

    //add Size to Diamond Shell
    @Override
    public DiamondShell addSizeToDiamondShell(Integer diamondShellId, Integer sizeId) {
        DiamondShell diamondShell = diamondShellRepo.findById(diamondShellId).orElseThrow();
        Size size = sizeRepo.findById(sizeId).orElseThrow();

        SizeDiamondShell sizeDiamondShell = new SizeDiamondShell();
        sizeDiamondShell.setDiamondShell(diamondShell);
        sizeDiamondShell.setSize(size);

        sizeDiamondShellRepo.save(sizeDiamondShell);
        diamondShell.getSizeDiamondShellList().add(sizeDiamondShell);
        return diamondShellRepo.save(diamondShell);
    }

    //get a diamond shell
    @Override
    public DiamondShell getADiamondShell(Integer id) {
        Optional<DiamondShell> diamondShell = diamondShellRepo.findById(id);
        if (diamondShell.isPresent()) {
            return diamondShell.get();
        } else {
            throw new RuntimeException("Diamond Shell not found with id " + id);
        }
    }

    @Override
    public List<DiamondShell> searchDiamondShell(DiamondShellRequest diamondShellRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DiamondShell> cq = cb.createQuery(DiamondShell.class);
        Root<DiamondShell> diamondShell = cq.from(DiamondShell.class);

        List<Predicate> predicates = new ArrayList<>();
        boolean hasConditions = false;

        if (diamondShellRequest.getSecondaryStoneType() != null && !diamondShellRequest.getSecondaryStoneType().isEmpty()) {
            predicates.add(cb.equal(diamondShell.get("secondaryStoneType"), diamondShellRequest.getSecondaryStoneType()));
            hasConditions = true;
        }
        if (diamondShellRequest.getMaterial() != null && !diamondShellRequest.getMaterial().isEmpty()) {
            predicates.add(cb.equal(diamondShell.get("material"), diamondShellRequest.getMaterial() ));
            hasConditions = true;
        }
        if (diamondShellRequest.getGender() != null && !diamondShellRequest.getGender().isEmpty()) {
            predicates.add(cb.equal(diamondShell.get("gender"), diamondShellRequest.getGender()));
            hasConditions = true;
        }
        if (diamondShellRequest.getMin_price() > 0) {
            predicates.add(cb.greaterThanOrEqualTo(diamondShell.get("price"), diamondShellRequest.getMin_price()));
            hasConditions = true;
        }
        if (diamondShellRequest.getMax_price() > 0 && (diamondShellRequest.getMin_price() <= diamondShellRequest.getMax_price())) {
            predicates.add(cb.lessThanOrEqualTo(diamondShell.get("price"), diamondShellRequest.getMax_price()));
            hasConditions = true;
        }

        if (!hasConditions) {
            return entityManager.createQuery(cq.select(diamondShell)).getResultList();
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
