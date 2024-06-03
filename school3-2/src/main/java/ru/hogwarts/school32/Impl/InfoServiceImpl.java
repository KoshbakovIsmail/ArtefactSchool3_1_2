package ru.hogwarts.school32.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school32.service.InfoService;


import java.util.stream.LongStream;
import java.util.stream.Stream;
@Service
public class InfoServiceImpl implements InfoService {
    private final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);
    @Value("${server.port}")
    private String serverPort;

    @Override
    public String getPort() {
        return "Server Port:" + serverPort;
    }

    @Override
    public void calculate(int limit) {
        calculate1(limit);
        calculate2(limit);
        calculate3(limit);
    }

    private void calculate1(int limit) {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(limit)
                .reduce(0, (a, b) -> a + b);
        long end = System.currentTimeMillis();
        logger.info("Time 1: " + (end - start));
    }

    private void calculate2(int limit) {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(limit)
                .reduce(0, (a, b) -> a + b);
        long end = System.currentTimeMillis();
        logger.info("Time 2: " + (end - start));
    }

    private void calculate3(int limit) {
        long start = System.currentTimeMillis();
        long sum = LongStream
                .range(1, limit)
                .sum();
        long end = System.currentTimeMillis();
        logger.info("Time 3: " + (end - start));
    }
}
