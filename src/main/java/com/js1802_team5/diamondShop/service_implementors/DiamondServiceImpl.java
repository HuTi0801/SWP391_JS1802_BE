package com.js1802_team5.diamondShop.service_implementors;
import com.js1802_team5.diamondShop.mappers.DiamondMapper;
import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.request_models.DiamondSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondSearchResponse;
import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.models.entity_models.Account;
import com.js1802_team5.diamondShop.models.response_models.DiamondResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiamondServiceImpl implements DiamondService {
    private final DiamondRepo diamondRepo;
    private final AccountRepo accountRepo;
    private final DiamondMapper diamondMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Response createDiamond(DiamondRequest diamondRequest) {
        Response response = new Response();
        try {
            // Trường hợp 1: Kiểm tra trùng hết tất cả các thuộc tính bao gồm certificateNumber
            Optional<Diamond> exactMatchDiamond = diamondRepo.findExactMatch(
                    //diamondRequest.getName(),
                    diamondRequest.getOrigin(),
                    diamondRequest.getClarity(),
                    diamondRequest.getCaratWeight(),
                    diamondRequest.getPrice(),
                    diamondRequest.getColor(),
                    diamondRequest.getCut(),
                    diamondRequest.getCertificateNumber(),
                    diamondRequest.getQuantity()
            );
            if (exactMatchDiamond.isPresent()) {
                // Cập nhật số lượng
                Diamond existingDiamond = exactMatchDiamond.get();
                existingDiamond.setQuantity(existingDiamond.getQuantity() + diamondRequest.getQuantity());
                diamondRepo.save(existingDiamond);

                response.setMessage("Diamond updated successfully with combined quantity!");
                response.setResult(diamondMapper.convertToDiamondResponse(existingDiamond));
                response.setSuccess(true);
                response.setStatusCode(200);
                return response;
            }

            // Trường hợp 2: Kiểm tra trùng hết tất cả các thuộc tính ngoại trừ certificateNumber
            Optional<Diamond> partialMatchDiamond = diamondRepo.findPartialMatch(
                    //diamondRequest.getName(),
                    diamondRequest.getOrigin(),
                    diamondRequest.getClarity(),
                    diamondRequest.getCaratWeight(),
                    diamondRequest.getPrice(),
                    diamondRequest.getColor(),
                    diamondRequest.getCut()
            );
            if (partialMatchDiamond.isPresent()) {
                // Cập nhật số lượng
                Diamond existingDiamond = partialMatchDiamond.get();
                existingDiamond.setQuantity(existingDiamond.getQuantity() + diamondRequest.getQuantity());
                diamondRepo.save(existingDiamond);

                response.setMessage("Diamond updated successfully with combined quantity (certificate number ignored)!");
                response.setResult(diamondMapper.convertToDiamondResponse(existingDiamond));
                response.setSuccess(true);
                response.setStatusCode(200);
                return response;
            }

            // Trường hợp 3: Kiểm tra trùng certificateNumber
            Optional<Diamond> certificateNumberMatchDiamond = diamondRepo.findByCertificateNumber(diamondRequest.getCertificateNumber());
            if (certificateNumberMatchDiamond.isPresent()) {
                response.setSuccess(false);
                response.setMessage("Diamond with the same certificate number already exists with different attributes!");
                response.setStatusCode(409);
                response.setResult(diamondRequest);
                return response;
            }

            // Nếu không rơi vào trường hợp nào, tạo mới diamond
            Diamond diamond = diamondMapper.toDiamond(diamondRequest);
            List<Account> managers = accountRepo.findByRoleAndIsActiveOrderByUsernameAsc(Role.MANAGER, true);
            if (managers.isEmpty()) {
                throw new IllegalArgumentException("No active manager found.");
            }
            diamond.setAccount(managers.get(0));
            diamond.generateName();

            DiamondResponse diamondResponse = diamondMapper.convertToDiamondResponse(diamond);
            response.setMessage("Create diamonds successfully!");
            response.setResult(diamondResponse);
            response.setSuccess(true);
            response.setStatusCode(200);
            diamondRepo.save(diamond);

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
                response.setResult(diamondMapper.toListDiamondRequest(filteredDiamonds));
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
                response.setResult(diamondMapper.toDiamondRequest(diamonds.get()));
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
                // Kiểm tra trùng certificateNumber
                Optional<Diamond> certificateNumberMatchDiamond = diamondRepo.findByCertificateNumber(updateDiamond.getCertificateNumber());
                if (certificateNumberMatchDiamond.isPresent() && !certificateNumberMatchDiamond.get().getId().equals(id)) {
                    response.setSuccess(false);
                    response.setMessage("Diamond with the same certificate number already exists!");
                    response.setStatusCode(409);
                    response.setResult(updateDiamond);
                    return response;
                }

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

                if (updateDiamond.getAccountId() != null) {
                    Account account = accountRepo.findById(updateDiamond.getAccountId())
                            .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + updateDiamond.getAccountId()));
                    diamond.get().setAccount(account);
                }

                diamondRepo.save(diamond.get());
                DiamondResponse diamondResponse = diamondMapper.convertToDiamondResponse(diamond.get());
                response.setMessage("Update diamond successfully!");
                response.setResult(diamondResponse);
                response.setSuccess(true);
                response.setStatusCode(200);

            } else {
                response.setMessage("There are no diamond!");
                response.setResult(updateDiamond);
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
    public List<DiamondSearchResponse> searchDiamond(DiamondSearchRequest diamondSearchRequest) {
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

        List<Diamond> diamonds = entityManager.createQuery(cq.select(diamond)).getResultList();

        return diamonds.stream()
                .map(DiamondMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllOrigins() {
        return diamondRepo.findAll()
                .stream()
                .map(Diamond::getOrigin)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllClarities() {
        return diamondRepo.findAll()
                .stream()
                .map(Diamond::getClarity)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllColors() {
        return diamondRepo.findAll()
                .stream()
                .map(Diamond::getColor)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCuts() {
        return diamondRepo.findAll()
                .stream()
                .map(Diamond::getCut)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Float> getAllCaratWeights() {
        return diamondRepo.findAll()
                .stream()
                .map(Diamond::getCaratWeight)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllDiamondNames() {
        List<Diamond> diamonds = diamondRepo.findAll();
        return diamonds.stream()
                .map(Diamond::getName)
                .collect(Collectors.toList());
    }
}
