package xyz.vvrf;

import org.apache.ibatis.session.SqlSessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}