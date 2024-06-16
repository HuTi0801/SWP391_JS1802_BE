package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.request_models.DiamondSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.services.DiamondService;
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
public class DiamondServiceImpl implements DiamondService {
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
                Diamond diamond = toDiamond(diamondRequest);
                diamond.generateName();
                response.setMessage("Create diamonds successfully!");
                response.setResult(diamondRequest);
                response.setSuccess(true);
                response.setStatusCode(200);
                diamondRepo.save(diamond);
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

            List<Diamond> filteredDiamonds = diamonds.stream()
                    .filter(Diamond::isStatusDiamond)
                    .toList();

            if (diamonds.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no diamond!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all diamonds successfully!");
                response.setResult(toListDiamondRequest(filteredDiamonds));
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
            if (diamonds.isEmpty() || !diamonds.get().isStatusDiamond()) {
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
                diamond.get().generateName();

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
            if (diamond.isPresent()) {
                diamondRepo.softDeleteById(id);
                response.setMessage("Remove diamond successfully!");
                response.setResult(null);
                response.setSuccess(true);
                response.setStatusCode(200);
            } else {
                response.setMessage("There are no diamond!");
                response.setResult(id);
                response.setSuccess(false);
                response.setStatusCode(404);
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
    public List<Diamond> searchDiamond(DiamondSearchRequest diamondSearchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Diamond> cq = cb.createQuery(Diamond.class);
        Root<Diamond> diamond = cq.from(Diamond.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(diamond.get("statusDiamond")));


        if (diamondSearchRequest.getCaratWeight() > 0) {
            predicates.add(cb.equal(diamond.get("caratWeight"), diamondSearchRequest.getCaratWeight()));

        }
        if (diamondSearchRequest.getColor() != null && !diamondSearchRequest.getColor().isEmpty()) {
            predicates.add(cb.equal(diamond.get("color"), diamondSearchRequest.getColor()));

        }
        if (diamondSearchRequest.getClarity() != null && !diamondSearchRequest.getClarity().isEmpty()) {
            predicates.add(cb.equal(diamond.get("clarity"), diamondSearchRequest.getClarity()));

        }
        if (diamondSearchRequest.getCut() != null && !diamondSearchRequest.getCut().isEmpty()) {
            predicates.add(cb.equal(diamond.get("cut"), diamondSearchRequest.getCut()));

        }
        if (diamondSearchRequest.getOrigin() != null && !diamondSearchRequest.getOrigin().isEmpty()) {
            predicates.add(cb.equal(diamond.get("origin"), diamondSearchRequest.getOrigin()));

        }
        if (diamondSearchRequest.getMin_price() > 0.0) {
            predicates.add(cb.greaterThanOrEqualTo(diamond.get("price"), diamondSearchRequest.getMin_price()));

        }
        if (diamondSearchRequest.getMax_price() > 0.0 && (diamondSearchRequest.getMin_price() <= diamondSearchRequest.getMax_price())) {
            predicates.add(cb.lessThanOrEqualTo(diamond.get("price"), diamondSearchRequest.getMax_price()));

        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq.select(diamond)).getResultList();
    }

    @Override
    public DiamondRequest toDiamondRequest(Diamond diamond) {
        return DiamondRequest.builder()
                .id(diamond.getId())
                .cut(diamond.getCut())
                .imageDiamond(diamond.getImageDiamond())
                .clarity(diamond.getClarity())
                .color(diamond.getColor())
                .caratWeight(diamond.getCaratWeight())
                .certificateNumber(diamond.getCertificateNumber())
                .quantity(diamond.getQuantity())
                .price(diamond.getPrice())
                .origin(diamond.getOrigin())
                .statusDiamond(diamond.isStatusDiamond())
                .build();
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
        return Diamond.builder()
                .id(diamondRequest.getId())
                .cut(diamondRequest.getCut())
                .imageDiamond(diamondRequest.getImageDiamond())
                .clarity(diamondRequest.getClarity())
                .color(diamondRequest.getColor())
                .caratWeight(diamondRequest.getCaratWeight())
                .certificateNumber(diamondRequest.getCertificateNumber())
                .quantity(diamondRequest.getQuantity())
                .price(diamondRequest.getPrice())
                .origin(diamondRequest.getOrigin())
                .statusDiamond(diamondRequest.isStatusDiamond())
                .build();
    }
}
