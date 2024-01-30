package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    Полезные для демо ссылки

    Стартовая страницerа
    http://localhost:8080

    Страница клиентов
    http://localhost:8080/clients
*/
@SpringBootApplication
public class SpringDataJdbcDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJdbcDemo.class, args);
    }
}