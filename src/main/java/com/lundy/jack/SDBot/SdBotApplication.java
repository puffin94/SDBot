package com.lundy.jack.SDBot;

import com.lundy.jack.SDBot.listeners.*;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
@Slf4j
@SpringBootApplication
public class SdBotApplication {

    @Autowired
    private Environment env;

    @Autowired
    PingListener pingListener;

    @Autowired
    VTTListener vttListener;

    @Autowired
    CommandsListener commandsListener;

    @Autowired
    DndListener dndListener;

    @Autowired
    TimeListener timeListener;

    @Autowired
    LuckyListener luckyListener;

    @Value("${Discord.Token}")
    private String token;

    public static void main(String[] args) {
        SpringApplication.run(SdBotApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(value = "discord-api")
    public DiscordApi discordApi() {

        DiscordApi api = new DiscordApiBuilder().setToken(token)
                .setAllNonPrivilegedIntents()
                .login()
                .join();
        addListeners(api);
        log.info("Application Start");
        return api;
    }

    public void addListeners(DiscordApi api) {
        log.info("Adding listeners");
        api.addMessageCreateListener(commandsListener);
        api.addMessageCreateListener(pingListener);
        api.addMessageCreateListener(vttListener);
        api.addMessageCreateListener(dndListener);
        api.addMessageCreateListener(timeListener);
        api.addMessageCreateListener(luckyListener);
        log.info("Listeners Added");
    }
}
