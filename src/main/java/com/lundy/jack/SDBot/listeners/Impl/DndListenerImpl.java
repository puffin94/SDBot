package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.DndListener;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class DndListenerImpl implements DndListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!dnd")){
            messageCreateEvent.getChannel().sendMessage("https://www.dndbeyond.com");
        }
    }
}
