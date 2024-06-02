package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondShellResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.DiamondShellRepo;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiamondShellServiceImpl implements DiamondShellService {

    private final DiamondShellRepo diamondShellRepo;
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
//    @Override
//    public DiamondShell addSizeToDiamondShell(Integer diamondShellId, Integer sizeId) {
//        DiamondShell diamondShell = diamondShellRepo.findById(diamondShellId).orElseThrow();
//        Size size = sizeRepo.findById(sizeId).orElseThrow();
//
//        SizeDiamondShell sizeDiamondShell = new SizeDiamondShell();
//        sizeDiamondShell.setDiamondShell(diamondShell);
//        sizeDiamondShell.setSize(size);
//
//        sizeDiamondShellRepo.save(sizeDiamondShell);
//        diamondShell.getSizeDiamondShellList().add(sizeDiamondShell);
//        return diamondShellRepo.save(diamondShell);
//    }

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
    public List<DiamondShell> searchDiamondShell(DiamondShellSearchRequest diamondShellSearchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DiamondShell> cq = cb.createQuery(DiamondShell.class);
        Root<DiamondShell> diamondShell = cq.from(DiamondShell.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isTrue(diamondShell.get("statusDiamondShell")));


        if (diamondShellSearchRequest.getSecondaryStoneType() != null && !diamondShellSearchRequest.getSecondaryStoneType().isEmpty()) {
            predicates.add(cb.equal(diamondShell.get("secondaryStoneType"), diamondShellSearchRequest.getSecondaryStoneType()));
        }
        if (diamondShellSearchRequest.getMaterial() != null && !diamondShellSearchRequest.getMaterial().isEmpty()) {
            predicates.add(cb.equal(diamondShell.get("material"), diamondShellSearchRequest.getMaterial()));
        }
        if (diamondShellSearchRequest.getGender() != null && !diamondShellSearchRequest.getGender().isEmpty()) {
            predicates.add(cb.equal(diamondShell.get("gender"), diamondShellSearchRequest.getGender()));
        }
        if (diamondShellSearchRequest.getMin_price() > 0) {
            predicates.add(cb.greaterThanOrEqualTo(diamondShell.get("price"), diamondShellSearchRequest.getMin_price()));
        }
        if (diamondShellSearchRequest.getMax_price() > 0 && (diamondShellSearchRequest.getMin_price() <= diamondShellSearchRequest.getMax_price())) {
            predicates.add(cb.lessThanOrEqualTo(diamondShell.get("price"), diamondShellSearchRequest.getMax_price()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq.select(diamondShell)).getResultList();
    }

    @Override
    public DiamondShellResponse convertToDiamondShellResponse(DiamondShell diamondShell) {
        DiamondShellResponse response = new DiamondShellResponse();
        response.setId(diamondShell.getId());
        response.setQuantity(diamondShell.getQuantity());
        response.setSecondaryStoneType(diamondShell.getSecondaryStoneType());
        response.setMaterial(diamondShell.getMaterial());
        response.setGender(diamondShell.getGender());
        response.setPrice(diamondShell.getPrice());
        response.setImageDiamondShell(diamondShell.getImageDiamondShell());

        // Lấy danh sách size từ SizeDiamondShellList
        List<Integer> sizes = diamondShell.getSizeDiamondShellList().stream()
                .map(sizeDiamondShell -> sizeDiamondShell.getSize().getSize())
                .collect(Collectors.toList());
        response.setSize(sizes);

        return response;
    }

    @Override
    public DiamondShellRequest toDiamondShellRequest(DiamondShell diamondShell) {
        return DiamondShellRequest.builder()
                .id(diamondShell.getId())
                .gender(diamondShell.getGender())
                .quantity(diamondShell.getQuantity())
                .secondaryStoneType(diamondShell.getSecondaryStoneType())
                .material(diamondShell.getMaterial())
                .imageDiamondShell(diamondShell.getImageDiamondShell())
                .price(diamondShell.getPrice())
                .statusDiamondShell(diamondShell.isStatusDiamondShell())
                .build();
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
        return DiamondShell.builder()
                .id(diamondShellRequest.getId())
                .gender(diamondShellRequest.getGender())
                .quantity(diamondShellRequest.getQuantity())
                .secondaryStoneType(diamondShellRequest.getSecondaryStoneType())
                .material(diamondShellRequest.getMaterial())
                .imageDiamondShell(diamondShellRequest.getImageDiamondShell())
                .price(diamondShellRequest.getPrice())
                .statusDiamondShell(diamondShellRequest.isStatusDiamondShell())
                .build();
    }
}
