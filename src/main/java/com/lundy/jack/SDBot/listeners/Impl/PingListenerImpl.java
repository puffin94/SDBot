package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.PingListener;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PingListenerImpl implements PingListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!Ping")){
            log.info("Responding to !Ping request by {}",messageCreateEvent.getMessage().getAuthor().getDisplayName());
            messageCreateEvent.getChannel().sendMessage("Pong!");
        }
    }
}
