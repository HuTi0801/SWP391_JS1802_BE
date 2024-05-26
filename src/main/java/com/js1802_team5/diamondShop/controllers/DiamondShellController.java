package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.request_models.DiamondRequest;
import com.js1802_team5.diamondShop.models.request_models.DiamondShellRequest;
import com.js1802_team5.diamondShop.services.DiamondShellService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/diamond-shell")
@RequiredArgsConstructor
public class DiamondShellController {
    private final DiamondShellService diamondShellService;

    /// This function use to mapping from DiamondShell to DiamondShellDTO
    private DiamondShellRequest toDiamondShellRequest(DiamondShell diamondShell) {
        var diamondShellRequest = new DiamondShellRequest();
        diamondShellRequest.setId(diamondShell.getId());
        diamondShellRequest.setGender(diamondShell.getGender());
        diamondShellRequest.setQuantity(diamondShell.getQuantity());
        diamondShellRequest.setSecondaryStoneType(diamondShell.getSecondaryStoneType());
        diamondShellRequest.setMaterial(diamondShell.getMaterial());
        diamondShellRequest.setImageDiamondShell(diamondShell.getImageDiamondShell());
        diamondShellRequest.setPrice(diamondShell.getPrice());
        return diamondShellRequest;
    }

    /// This function use to mapping from List Diamond to List DiamondDTO
    private List<DiamondShellRequest> toListDiamondShellRequest(List<DiamondShell> diamondShells) {
        List<DiamondShellRequest> diamondShellRequest = new ArrayList<>();
        for (DiamondShell diamondShell : diamondShells) {
            // Using the function with same name above
            diamondShellRequest.add(toDiamondShellRequest(diamondShell));
        }
        return diamondShellRequest;
    }

    private DiamondShell toDiamond(DiamondShellRequest diamondShellRequest) {
        var diamondShell = new DiamondShell();
        diamondShell.setId(diamondShellRequest.getId());
        diamondShell.setGender(diamondShellRequest.getGender());
        diamondShell.setQuantity(diamondShellRequest.getQuantity());
        diamondShell.setSecondaryStoneType(diamondShellRequest.getSecondaryStoneType());
        diamondShell.setMaterial(diamondShellRequest.getMaterial());
        diamondShell.setImageDiamondShell(diamondShellRequest.getImageDiamondShell());
        diamondShell.setPrice(diamondShellRequest.getPrice());
        return diamondShell;
    }

    //get all diamond shell
    @GetMapping("/get-all-diamond-shell")
    public List<DiamondShellRequest> getAllDiamondShell(){
        var diamondShellRequest = toListDiamondShellRequest(diamondShellService.getAllDiamondShell());
        return diamondShellRequest;
    }

    //create diamond shell
    @PostMapping("/create-diamond-shell")
    public ResponseEntity<DiamondShell> createDiamondShell(@Valid @RequestBody DiamondShellRequest diamondShellRequest){
        var diamondShell = toDiamond(diamondShellRequest);
        diamondShell = diamondShellService.createDiamondShell(diamondShell);
        return ResponseEntity.ok(diamondShell);
    }

    //Add Size to Diamond Shell
//    @PostMapping("/{diamondShellId}/add-size/{sizeId}")
//    public ResponseEntity<DiamondShell> addSizeToDiamondShell(@PathVariable Integer diamondShellId, @PathVariable Integer sizeId) {
//        DiamondShell updatedDiamondShell = diamondShellService.addSizeToDiamondShell(diamondShellId, sizeId);
//        return ResponseEntity.ok(updatedDiamondShell);
//    }

    @GetMapping("/get-a-diamond-shell-{id}")
    public DiamondShellRequest getADiamondShell(@PathVariable Integer id) {
        var diamondShellRequest = toDiamondShellRequest(diamondShellService.getADiamondShell(id));
        return diamondShellRequest;
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

    @PostMapping("/searchDiamondShell")
    public List<DiamondShell> searchDiamonds(@RequestBody DiamondShellRequest diamondShellRequest) {
        return diamondShellService.searchDiamondShell(diamondShellRequest);
    }
}
