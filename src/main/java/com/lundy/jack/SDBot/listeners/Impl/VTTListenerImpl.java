package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.VTTListener;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class VTTListenerImpl implements VTTListener {

    @Autowired
    private Environment env;

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        String vtturl = env.getProperty("VTTURL");

        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!vtt")) {
            messageCreateEvent.getChannel().sendMessage(vtturl);
        }
    }
}
