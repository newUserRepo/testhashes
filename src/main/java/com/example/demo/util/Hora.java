package com.example.demo.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Consumer;

@Service
public class Hora {

    @Async
    public void setHour(final Consumer<String> labelHora) {
        for(;;) {
            try {
                Thread.sleep(1000);
                labelHora.accept("Hora servidor: "+getHour());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHour() {
        return DateTimeFormatter.ofPattern("h:mm:ss a")
                .withZone(ZoneId.systemDefault())
                .withLocale(Locale.ENGLISH)
                .format(Instant.now());
    }
}
