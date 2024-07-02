package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.Role;
import com.js1802_team5.diamondShop.mappers.DiamondMapper;
import com.js1802_team5.diamondShop.mappers.DiamondShellMapper;
import com.js1802_team5.diamondShop.models.entity_models.*;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellSearchRequest;
import com.js1802_team5.diamondShop.models.response_models.DiamondShellResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.models.response_models.SizeDiamondShellResponse;
import com.js1802_team5.diamondShop.repositories.AccountRepo;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class
DiamondShellServiceImpl implements DiamondShellService {

    private final DiamondShellRepo diamondShellRepo;
    private final SizeRepo sizeRepo;
    private final SizeDiamondShellRepo sizeDiamondShellRepo;
    private final AccountRepo accountRepo;
    private final DiamondShellMapper diamondShellMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Response createDiamondShell(DiamondShellRequest diamondShellRequest) {
        Response response = new Response();
        try {
            DiamondShell diamondShell = diamondShellMapper.toDiamond(diamondShellRequest);

            // Tìm tài khoản có role là MANAGER
            List<Account> managers = accountRepo.findByRoleAndIsActiveOrderByUsernameAsc(Role.MANAGER, true);
            if (managers.isEmpty()) {
                throw new IllegalArgumentException("No active manager found.");
            }
            // Sử dụng tài khoản quản lý đầu tiên tìm được
            diamondShell.setAccount(managers.get(0));

            diamondShell.generateName();
            diamondShell = diamondShellRepo.save(diamondShell);

            // Lưu vào bảng SizeDiamondShell
            List<SizeDiamondShell> sizeDiamondShells = new ArrayList<>();
            if (diamondShellRequest.getSizeIds() != null && !diamondShellRequest.getSizeIds().isEmpty()) {
                for (Integer sizeId : diamondShellRequest.getSizeIds()) {
                    Size size = sizeRepo.findById(sizeId)
                            .orElseThrow(() -> new IllegalArgumentException("Size not found with id: " + sizeId));

                    SizeDiamondShell sizeDiamondShell = SizeDiamondShell.builder()
                            .diamondShell(diamondShell)
                            .size(size)
                            .build();

                    sizeDiamondShells.add(sizeDiamondShellRepo.save(sizeDiamondShell));
                }
            }
            // Cập nhật danh sách SizeDiamondShell trong DiamondShell
            diamondShell.setSizeDiamondShellList(sizeDiamondShells);
            DiamondShellResponse diamondShellResponse = convertToDiamondShellResponse(diamondShell);
            response.setMessage("Create diamond shell successfully!");
            response.setResult(diamondShellResponse);
            response.setSuccess(true);
            response.setStatusCode(200);
            // diamondShellRepo.save(toDiamond(diamondShellRequest));
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

            List<DiamondShell> filteredDiamondShells = diamondShells.stream()
                    .filter(DiamondShell::isStatusDiamondShell)
                    .toList();

            if (diamondShells.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no diamond shell!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all diamond shell successfully!");
                response.setResult(diamondShellMapper.toListDiamondShellRequest(filteredDiamondShells));
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

    //get a diamond shell
    @Override
    public Response getADiamondShell(Integer id) {
        Response response = new Response();
        try {
            Optional<DiamondShell> diamondShell = diamondShellRepo.findById(id);
            if (diamondShell.isEmpty() || !diamondShell.get().isStatusDiamondShell()) {
                response.setSuccess(false);
                response.setMessage("There are no diamond!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get a diamond shell successfully!");
                response.setResult(diamondShellMapper.toDiamondShellRequest(diamondShell.get()));
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
            Optional<DiamondShell> diamondShellOptional = diamondShellRepo.findById(id);
            if (diamondShellOptional.isPresent()) {
                diamondShellOptional.get().setPrice(updateDiamondShell.getPrice());
                diamondShellOptional.get().setQuantity(updateDiamondShell.getQuantity());
                diamondShellOptional.get().setGender(updateDiamondShell.getGender());
                diamondShellOptional.get().setImageDiamondShell(updateDiamondShell.getImageDiamondShell());
                diamondShellOptional.get().setStatusDiamondShell(updateDiamondShell.isStatusDiamondShell());
                diamondShellOptional.get().setMaterial(updateDiamondShell.getMaterial());
                diamondShellOptional.get().setSecondaryStoneType(updateDiamondShell.getSecondaryStoneType());

                // Tìm tài khoản có role là MANAGER
                List<Account> managers = accountRepo.findByRoleAndIsActiveOrderByUsernameAsc(Role.MANAGER, true);
                if (managers.isEmpty()) {
                    throw new IllegalArgumentException("No active manager found.");
                }
                // Sử dụng tài khoản quản lý đầu tiên tìm được
                diamondShellOptional.get().setAccount(managers.get(0)); // Chuyển thành setAccount thay vì setAccountId

                // Gọi phương thức generateName để tự động cập nhật name cho diamond shell
                diamondShellOptional.get().generateName();

                diamondShellRepo.save(diamondShellOptional.get());
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
            if (diamond.isPresent()) {
                diamondShellRepo.softDeleteById(id);
                response.setMessage("Remove diamond shell successfully!");
                response.setResult(null);
                response.setSuccess(true);
                response.setStatusCode(200);
            } else {
                response.setMessage("There are no diamond shell!");
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
        response.setName(diamondShell.getName());
        response.setQuantity(diamondShell.getQuantity());
        response.setSecondaryStoneType(diamondShell.getSecondaryStoneType());
        response.setMaterial(diamondShell.getMaterial());
        response.setGender(diamondShell.getGender());
        response.setPrice(diamondShell.getPrice());
        response.setImageDiamondShell(diamondShell.getImageDiamondShell());
        response.setAccountId(diamondShell.getAccount().getId());
        List<SizeDiamondShellResponse> sizeResponses = diamondShell.getSizeDiamondShellList().stream()
                .map(sizeDiamondShell -> SizeDiamondShellResponse.builder()
                        .id(sizeDiamondShell.getId())
                        .size(sizeDiamondShell.getSize().getSize())
                        .build())
                .collect(Collectors.toList());
        response.setSize(sizeResponses);
        return response;
    }

    @Override
    public List<String> getAllMaterials() {
        return diamondShellRepo.findAll()
                .stream()
                .map(DiamondShell::getMaterial)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllSecondaryStoneTypes() {
        return diamondShellRepo.findAll()
                .stream()
                .map(DiamondShell::getSecondaryStoneType)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllDiamondShellNames() {
        List<DiamondShell> diamondShells = diamondShellRepo.findAll();
        return diamondShells.stream()
                .map(DiamondShell::getName)
                .collect(Collectors.toList());
    }
}
