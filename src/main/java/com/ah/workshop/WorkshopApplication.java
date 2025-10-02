package com.ah.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 簡易線上購物系統 (Workshop) 的主啟動類別。
 * <p>
 * 這個類別是整個 Spring Boot 應用程式的進入點 (Entry Point)。
 * {@code @SpringBootApplication} 是一個方便的複合註解，它本身包含了以下三個核心註解：
 * <ul>
 * <li>{@code @SpringBootConfiguration}: 標示這個類別為應用程式的組態來源，是 {@code @Configuration} 的特化版本。</li>
 * <li>{@code @EnableAutoConfiguration}: 啟用 Spring Boot 的自動組態機制，會根據 classpath 中的 jar 依賴，自動組態您的專案（例如，偵測到 Spring Web 就會自動組態 Tomcat 和 DispatcherServlet）。</li>
 * <li>{@code @ComponentScan}: 啟用元件掃描，Spring 會自動掃描這個 package (`com.ah.workshop`) 及其所有子 package 下的元件（如 @Controller, @Service, @Repository 等）並註冊為 Bean。</li>
 * </ul>
 *
 * @author abbyhung
 * @version 1.0
 * @since 2025-10-02
 */
@SpringBootApplication
public class WorkshopApplication {

    /**
     * 應用程式的主方法 (main method)，也是程式執行的起點。
     * @param args 來自命令列的參數 (Command-line arguments)
     */
    public static void main(String[] args) {
        // 呼叫 SpringApplication.run() 來引導並啟動整個 Spring 應用程式
        SpringApplication.run(WorkshopApplication.class, args);
    }

}
