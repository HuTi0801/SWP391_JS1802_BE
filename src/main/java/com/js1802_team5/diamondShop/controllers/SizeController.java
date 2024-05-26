package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.models.request_models.SizeRequest;
import com.js1802_team5.diamondShop.services.SizeService;
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
@RequestMapping("/size")
@RequiredArgsConstructor
public class SizeController {
    private final  SizeService sizeService;


    private SizeRequest toSizeRequest(Size size) {
        var sizeRequest = new SizeRequest();
        sizeRequest.setId(size.getId());
        sizeRequest.setSize(size.getSize());
        return sizeRequest;
    }

    /// This function use to mapping from List Diamond to List DiamondDTO
    private List<SizeRequest> toListSizeRequest(List<Size> size) {
        List<SizeRequest> sizeRequest = new ArrayList<>();
        for (Size sizes : size) {
            // Using the function with same name above
            sizeRequest.add(toSizeRequest(sizes));
        }
        return sizeRequest;
    }

    private Size toSize(SizeRequest sizeRequest) {
        var size = new Size();
        size.setId(sizeRequest.getId());
        size.setSize(sizeRequest.getSize());
        return size;
    }


    //create size
    @PostMapping("/create-size")
    public ResponseEntity<Size> createSize(@Valid @RequestBody SizeRequest sizeRequest){
        var size = toSize(sizeRequest);
        size = sizeService.createSize(size);
        return ResponseEntity.ok(size);
    }

    //get All size
    @GetMapping("/get-all-size")
    public List<SizeRequest> getAllSize(){
        var sizeRequest = toListSizeRequest(sizeService.getAllSize());
        return sizeRequest;
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
}
