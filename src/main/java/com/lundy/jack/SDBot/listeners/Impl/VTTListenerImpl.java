package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.VTTListener;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class VTTListenerImpl implements VTTListener {

    @Autowired
    private Environment env;

    @Value("${VTT.URL}")
    private String vtturl;

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!vtt")) {
            log.info("Responding to !vtt by {}",messageCreateEvent.getMessage().getAuthor().getDisplayName());
            messageCreateEvent.getChannel().sendMessage(vtturl);
        }
    }
}
