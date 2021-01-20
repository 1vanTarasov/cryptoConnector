package com.trading.strategy;

import ...;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class ConnectorsApplication implements CommandLineRunner {
    private ExchangeManager exchangeManager;
    private Environment env;
    private EventsHandler eventsHandler;
    private ArbitrageService arbitrageService;

    @Value("${trading.exchanges.ws.multiple.instance}")
    private List<String> exchangesWsMultipleInstance;
    
    @Value("${management.program.port}")
    private int managementProgramPort;
    
    @Value("${server.port}")
    private int serverPort;
    
    public ConnectorsApplication(
            Environment env,
            ExchangeManager exchangeManager,
            EventsHandler eventsHandler,
            ArbitrageService arbitrageService) {
        this.env = env;
        this.exchangeManager = exchangeManager;
        this.eventsHandler = eventsHandler;
        this.arbitrageService = arbitrageService;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ConnectorsApplication.class);
        application.run(args);
    }
    
    @Override
    public void run(String... args) {
        List<String> allExchanges = Arrays.asList(EnvUtils.getAllExchanges(env));
        //...
        exchanges.forEach(exchangeName -> exchangeManager.subscribeExchange(pairs, exchangeName));
        //...
    }

    //...
}
