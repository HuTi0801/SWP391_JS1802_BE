package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.models.request_models.SizeRequest;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.SizeRepo;
import com.js1802_team5.diamondShop.services.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepo sizeRepo;

    //create size
    @Override
    public Response createSize(SizeRequest sizeRequest) {
        Response response = new Response();
        try {
            Optional<Size> existingSize = sizeRepo.findBySize(sizeRequest.getSize());
            if (existingSize.isPresent()) {
                response.setSuccess(false);
                response.setMessage("Size is duplicated!");
                response.setStatusCode(404);
                response.setResult(sizeRequest);
            } else {
                response.setMessage("Create size successfully!");
                response.setResult(sizeRequest);
                response.setSuccess(true);
                response.setStatusCode(200);
                sizeRepo.save(toSize(sizeRequest));
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    //get all size
    @Override
    public Response getAllSize() {
        Response response = new Response();
        try {
            var sizes = sizeRepo.findAll();
            if (sizes.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no size!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get all size successfully!");
                response.setResult(toListSizeRequest(sizes));
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

    //convert size to sizeRequest
    @Override
    public SizeRequest toSizeRequest(Size size) {
        var sizeRequest = new SizeRequest();
        sizeRequest.setId(size.getId());
        sizeRequest.setSize(size.getSize());
        return sizeRequest;
    }

    //This function use to mapping from list size to List size request
    @Override
    public List<SizeRequest> toListSizeRequest(List<Size> size) {
        List<SizeRequest> sizeRequest = new ArrayList<>();
        for (Size sizes : size) {
            sizeRequest.add(toSizeRequest(sizes));
        }
        return sizeRequest;
    }

    //convert sizeRequest to size
    @Override
    public Size toSize(SizeRequest sizeRequest) {
        var size = new Size();
        size.setId(sizeRequest.getId());
        size.setSize(sizeRequest.getSize());
        return size;
    }

}
