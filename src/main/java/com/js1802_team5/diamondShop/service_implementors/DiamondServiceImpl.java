package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.services.IDiamondService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiamondServiceImpl implements IDiamondService {
    private final DiamondRepo diamondRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Response createDiamond(DiamondRequest diamondRequest) {
        Response response = new Response();
        try {
            Optional<Diamond> existingDiamond = diamondRepo.findByCertificateNumber(diamondRequest.getCertificateNumber());
            if (existingDiamond.isPresent()) {
                response.setSuccess(false);
                response.setMessage("Diamond is duplicated!");
                response.setStatusCode(404);
                response.setResult(diamondRequest);
            } else {
                response.setMessage("Create diamonds successfully!");
                response.setResult(diamondRequest);
                response.setSuccess(true);
                response.setStatusCode(200);
                diamondRepo.save(toDiamond(diamondRequest));
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
    public Response getAllDiamond() {
        Response response = new Response();
        try {
            var diamonds = diamondRepo.findAll();
            if (diamonds.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no diamond!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all diamonds successfully!");
                response.setResult(toListDiamondRequest(diamonds));
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
    public Response getADiamond(Integer id) {
        Response response = new Response();
        try {
            Optional<Diamond> diamonds = diamondRepo.findById(id);
            if (diamonds.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no diamond!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get diamond successfully!");
                response.setResult(toDiamondRequest(diamonds.get()));
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

    //update diamond
    @Override
    public Response updateDiamond(Integer id, DiamondRequest updateDiamond) {
        var response = new Response();
        try {
            var diamond = diamondRepo.findById(id);
            if (diamond.isPresent()) {
                diamond.get().setOrigin(updateDiamond.getOrigin());
                diamond.get().setClarity(updateDiamond.getClarity());
                diamond.get().setCaratWeight(updateDiamond.getCaratWeight());
                diamond.get().setPrice(updateDiamond.getPrice());
                diamond.get().setColor(updateDiamond.getColor());
                diamond.get().setCut(updateDiamond.getCut());
                diamond.get().setCertificateNumber(updateDiamond.getCertificateNumber());
                diamond.get().setQuantity(updateDiamond.getQuantity());
                diamond.get().setImageDiamond(updateDiamond.getImageDiamond());
                diamond.get().setStatusDiamond(updateDiamond.isStatusDiamond());

                diamondRepo.save(diamond.get());
                //Set response to return
                response.setMessage("Update diamond successfully!");
                response.setResult(updateDiamond);
                response.setSuccess(true);
                response.setStatusCode(200);
            } else {
                //Set response to return when the diamond to update is null
                response.setMessage("There are no diamond!");
                response.setResult(updateDiamond);
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
    public Response removeDiamond(Integer id) {
        var response = new Response();
        try {
            var diamond = diamondRepo.findById(id);
            if(diamond.isPresent()){
                diamondRepo.softDeleteById(id);
                response.setMessage("Remove diamond successfully!");
                response.setResult(null);
                response.setSuccess(true);
                response.setStatusCode(200);
            }else {
                response.setMessage("There are no diamond!");
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

    @Override
    public DiamondRequest toDiamondRequest(Diamond diamond) {
        var diamondRequest = new DiamondRequest();
        diamondRequest.setId(diamond.getId());
        diamondRequest.setCut(diamond.getCut());
        diamondRequest.setImageDiamond(diamond.getImageDiamond());
        diamondRequest.setClarity(diamond.getClarity());
        diamondRequest.setColor(diamond.getColor());
        diamondRequest.setCaratWeight(diamond.getCaratWeight());
        diamondRequest.setCertificateNumber(diamond.getCertificateNumber());
        diamondRequest.setQuantity(diamond.getQuantity());
        diamondRequest.setPrice(diamond.getPrice());
        diamondRequest.setOrigin(diamond.getOrigin());
        diamondRequest.setStatusDiamond(diamond.isStatusDiamond());
        return diamondRequest;
    }

    @Override
    public List<DiamondRequest> toListDiamondRequest(List<Diamond> diamond) {
        List<DiamondRequest> diamondRequest = new ArrayList<>();
        for (Diamond diamonds : diamond) {
            diamondRequest.add(toDiamondRequest(diamonds));
        }
        return diamondRequest;
    }

    @Override
    public Diamond toDiamond(DiamondRequest diamondRequest) {
        var diamond = new Diamond();
        diamond.setCut(diamondRequest.getCut());
        diamond.setImageDiamond(diamondRequest.getImageDiamond());
        diamond.setClarity(diamondRequest.getClarity());
        diamond.setColor(diamondRequest.getColor());
        diamond.setCaratWeight(diamondRequest.getCaratWeight());
        diamond.setCertificateNumber(diamondRequest.getCertificateNumber());
        diamond.setQuantity(diamondRequest.getQuantity());
        diamond.setPrice(diamondRequest.getPrice());
        diamond.setOrigin(diamondRequest.getOrigin());
        diamond.setStatusDiamond(diamondRequest.isStatusDiamond());
        return diamond;
    }
}
