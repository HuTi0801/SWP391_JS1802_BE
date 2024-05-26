package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.services.FirebaseStorageService;
import com.js1802_team5.diamondShop.services.IDiamondService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.services.IDiamondService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diamond")
@RequiredArgsConstructor
public class DiamondController {
    private final IDiamondService diamondService;

    private final FirebaseStorageService firebaseStorageService;

    @GetMapping("/testapi")
    public String testAPI(){
        return "Hello";
    }

    //API create Diamond
    @PostMapping("/create-diamond")
    public Diamond createDiamond(@RequestBody Diamond diamond){
        return diamondService.createDiamond(diamond);
    }

    @GetMapping("/get-all-diamond")
    public List<Diamond> getAllDiamond(){
        return diamondService.getAllDiamond();
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = firebaseStorageService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
    //API create Diamond
//    @PostMapping("/create-diamond")
//    public String createDiamond(@RequestBody Diamond diamond) {
//        return diamondService.createDiamond(diamond);
//    }

    //This function use to mapping from Diamond to DiamondDTO
    private DiamondRequest toDiamondRequest(Diamond diamond) {
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
        return diamondRequest;
    }

    //This function use to mapping from List Diamond to List DiamondDTO
    private List<DiamondRequest> toListDiamondRequest(List<Diamond> diamonds) {
        List<DiamondRequest> diamondRequest = new ArrayList<>();
        for (Diamond diamond : diamonds) {
            // Using the function with same name above
            diamondRequest.add(toDiamondRequest(diamond));
        }
        return diamondRequest;
    }

    private Diamond toDiamond(DiamondRequest diamondRequest) {
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
        return diamond;
    }

    @PostMapping("/create-diamond")
    public ResponseEntity<String> createDiamond(@Valid @RequestBody DiamondRequest diamondRequest) {
        // Đảm bảo diamond được xác thực theo các ràng buộc
        // Nếu diamond không hợp lệ, MethodArgumentNotValidException sẽ được ném và xử lý bởi handleValidationExceptions
        // Nếu diamond hợp lệ, tiến hành lưu vào cơ sở dữ liệu hoặc xử lý khác

        // Using the function with same name above
        var diamond = toDiamond(diamondRequest);

        if(diamondService.createDiamond(diamond) != null){
            return ResponseEntity.ok("Diamond created successfully");
        }
        return ResponseEntity.status(500).body("Diamond create failed");
    }

    //Get A Diamond by id
    @GetMapping("/get-a-diamond-{id}")
    public DiamondRequest getADiamond(@PathVariable Integer id) {
        var diamondRequest = toDiamondRequest(diamondService.getADiamond(id));
        return diamondRequest;
    }

    // Xử lý ngoại lệ khi dữ liệu không hợp lệ
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Tạo một bản đồ chứa các lỗi xác thực
        Map<String, String> errors = new HashMap<>();
        // Duyệt qua danh sách các lỗi và thêm chúng vào bản đồ
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // Lấy tên trường (field) gây ra lỗi
            String fieldName = ((FieldError) error).getField();
            // Lấy thông báo lỗi tương ứng
            String errorMessage = error.getDefaultMessage();
            // Thêm thông tin lỗi vào bản đồ
            errors.put(fieldName, errorMessage);
        });
        // Trả về phản hồi HTTP 400 Bad Request cùng với bản đồ các lỗi
        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping("/searchDiamond")
    public List<Diamond> searchDiamonds(@RequestBody DiamondRequest diamondRequest) {
        return diamondService.searchDiamond(diamondRequest);
    }
}
