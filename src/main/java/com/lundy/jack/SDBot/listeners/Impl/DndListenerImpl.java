package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.DndListener;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DndListenerImpl implements DndListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!dnd")){
            log.info("Responding to !dnd by {}",messageCreateEvent.getMessage().getAuthor().getDisplayName());
            messageCreateEvent.getChannel().sendMessage("https://www.dndbeyond.com");
        }
    }
}
