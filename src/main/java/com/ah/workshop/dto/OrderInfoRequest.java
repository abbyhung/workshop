package com.ah.workshop.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ah.workshop.entity.enums.OrderStatusType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderInfoRequest {
	private Long orderId;
    private LocalDateTime date;
    @NotBlank(message = "客戶姓名不可為空")
    private String custName;

    @NotBlank(message = "客戶電話不可為空")
    @Size(min = 10, max = 10, message = "電話號碼必須是 10 碼")
    @Pattern(regexp = "^09\\d{8}$", message = "電話號碼格式不正確，必須為 09 開頭")
    private String custNumber;

    @NotBlank(message = "取貨店名不可為空")
    private String custShip;
    private String comment;
    private Integer totalAmount;
    
    // 同時包含 Enum 的名稱和中文描述
    private String status;
    private String statusDescription;
    
    private List<OrderDetailRequest> details;
}
