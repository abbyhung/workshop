package com.ah.workshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    
    private String code; // 狀態碼 (例如 "200" 代表成功, "400" 代表錯誤)
    private String msg;  // 訊息 (例如 "查詢成功", "訂單不存在")

    private T data;
}
