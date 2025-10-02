package com.ah.workshop.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 訂單資訊dto。
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Data
public class OrderInfoRequest {
	// 訂單編號
	private Long orderId;
	
	// 訂單日期
    private LocalDateTime date;
    
    // 客戶姓名
    @NotBlank(message = "客戶姓名不可為空")
    private String custName;

    // 客戶電話
    @NotBlank(message = "客戶電話不可為空")
    @Size(min = 10, max = 10, message = "電話號碼必須是 10 碼")
    @Pattern(regexp = "^09\\d{8}$", message = "電話號碼格式不正確，必須為 09 開頭")
    private String custNumber;

    // 取貨店名
    @NotBlank(message = "取貨店名不可為空")
    private String custShip;
    
    // 訂單備註
    private String comment;
    
    // 總金額
    private Integer totalAmount;
    
    // 同時包含 Enum 的名稱和中文描述
    private String status;
    private String statusDescription;
    
    private List<OrderDetailRequest> details;
}
