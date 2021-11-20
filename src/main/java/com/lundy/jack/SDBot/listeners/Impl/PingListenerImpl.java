package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.PingListener;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class PingListenerImpl implements PingListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!Ping")){
            messageCreateEvent.getChannel().sendMessage("Pong!");
        }
    }
}
