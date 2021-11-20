package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.TimeListener;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class TimeListenerImpl implements TimeListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!time")) {
            if (messageCreateEvent.getMessage().getAuthor().isServerAdmin()) {
                messageCreateEvent.getChannel().sendMessage("Bruce Buffer says 'It's TIME!!'");
            }else{
                messageCreateEvent.getChannel().sendMessage("You must be a server admin to use the '!time' command");
            }
        }
    }
}
