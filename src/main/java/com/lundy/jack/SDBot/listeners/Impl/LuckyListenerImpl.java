package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.LuckyListener;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class LuckyListenerImpl implements LuckyListener {

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        String name = messageCreateEvent.getMessageAuthor().getDisplayName();

        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!lucky")) {
            messageCreateEvent.getChannel().sendMessage(name + "'s lucky number is " + luckyNumberGenerator(name));
        }
    }

    public int luckyNumberGenerator(String name) {
        System.out.println(name);
        char[] nameArray = name.toCharArray();
        int luckyNum = 0;
        for (Character character : nameArray) {
            luckyNum += character;
        }
        return luckyNum % nameArray.length;
    }
}
