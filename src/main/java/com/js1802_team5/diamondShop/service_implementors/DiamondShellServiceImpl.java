package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.models.entity_models.SizeDiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
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
    public Response createDiamondShell(DiamondShellRequest diamondShellRequest) {
        Response response = new Response();
        try {
            response.setMessage("Create diamond shell successfully!");
            response.setResult(diamondShellRequest);
            response.setSuccess(true);
            response.setStatusCode(200);
            diamondShellRepo.save(toDiamond(diamondShellRequest));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response getAllDiamondShell() {
        Response response = new Response();
        try {
            var diamondShells = diamondShellRepo.findAll();
            if (diamondShells.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no diamond shell!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all diamond shell successfully!");
                response.setResult(toListDiamondShellRequest(diamondShells));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
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
    public Response getADiamondShell(Integer id) {
        Response response = new Response();
        try {
            Optional<DiamondShell> diamondShell1 = diamondShellRepo.findById(id);
            if (diamondShell1.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no diamond!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get a diamond shell successfully!");
                response.setResult(toDiamondShellRequest(diamondShell1.get()));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response updateDiamondShell(Integer id, DiamondShellRequest updateDiamondShell) {
        var response = new Response();
        try {
            var diamondShell = diamondShellRepo.findById(id);
            if (diamondShell.isPresent()) {
                diamondShell.get().setPrice(updateDiamondShell.getPrice());
                diamondShell.get().setQuantity(updateDiamondShell.getQuantity());
                diamondShell.get().setGender(updateDiamondShell.getGender());
                diamondShell.get().setImageDiamondShell(updateDiamondShell.getImageDiamondShell());
                diamondShell.get().setStatusDiamondShell(updateDiamondShell.isStatusDiamondShell());
                diamondShell.get().setMaterial(updateDiamondShell.getMaterial());
                diamondShell.get().setSecondaryStoneType(updateDiamondShell.getSecondaryStoneType());
                diamondShellRepo.save(diamondShell.get());
                //Set response to return
                response.setMessage("Update diamond shell successfully!");
                response.setResult(updateDiamondShell);
                response.setSuccess(true);
                response.setStatusCode(200);
            } else {
                //Set response to return when the diamond to update is null
                response.setMessage("There are no diamond shell!");
                response.setResult(updateDiamondShell);
                response.setSuccess(false);
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            //Set response to return when exception occur
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response removeDiamondShell(Integer id) {
        var response = new Response();
        try {
            var diamond = diamondShellRepo.findById(id);
            if(diamond.isPresent()){
                diamondShellRepo.softDeleteById(id);
                response.setMessage("Remove diamond shell successfully!");
                response.setResult(null);
                response.setSuccess(true);
                response.setStatusCode(200);
            }else {
                response.setMessage("There are no diamond shell!");
                response.setResult(id);
                response.setSuccess(false);
                response.setStatusCode(404);
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    //search diamond shell
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
            predicates.add(cb.equal(diamondShell.get("material"), diamondShellRequest.getMaterial()));
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

    @Override
    public DiamondShellRequest toDiamondShellRequest(DiamondShell diamondShell) {
        var diamondShellRequest = new DiamondShellRequest();
        diamondShellRequest.setId(diamondShell.getId());
        diamondShellRequest.setGender(diamondShell.getGender());
        diamondShellRequest.setQuantity(diamondShell.getQuantity());
        diamondShellRequest.setSecondaryStoneType(diamondShell.getSecondaryStoneType());
        diamondShellRequest.setMaterial(diamondShell.getMaterial());
        diamondShellRequest.setImageDiamondShell(diamondShell.getImageDiamondShell());
        diamondShellRequest.setPrice(diamondShell.getPrice());
        diamondShellRequest.setStatusDiamondShell(diamondShell.isStatusDiamondShell());
        return diamondShellRequest;
    }

    @Override
    public List<DiamondShellRequest> toListDiamondShellRequest(List<DiamondShell> diamondShells) {
        List<DiamondShellRequest> diamondShellRequest = new ArrayList<>();
        for (DiamondShell diamondShell : diamondShells) {
            diamondShellRequest.add(toDiamondShellRequest(diamondShell));
        }
        return diamondShellRequest;
    }

    @Override
    public DiamondShell toDiamond(DiamondShellRequest diamondShellRequest) {
        var diamondShell = new DiamondShell();
        diamondShell.setId(diamondShellRequest.getId());
        diamondShell.setGender(diamondShellRequest.getGender());
        diamondShell.setQuantity(diamondShellRequest.getQuantity());
        diamondShell.setSecondaryStoneType(diamondShellRequest.getSecondaryStoneType());
        diamondShell.setMaterial(diamondShellRequest.getMaterial());
        diamondShell.setImageDiamondShell(diamondShellRequest.getImageDiamondShell());
        diamondShell.setPrice(diamondShellRequest.getPrice());
        diamondShell.setStatusDiamondShell(diamondShellRequest.isStatusDiamondShell());
        return diamondShell;
    }
}
