package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.listeners.CommandsListener;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandsListenerImpl implements CommandsListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        String pingCommand = "!ping - a left over command from testing";
        String vttCommand = "!vtt - provides link to the Virtual Table Top";
        String dndCommand = "!dnd - provides link to D&D Beyond";
        String timeCommand = "!time - usable only by admins, It's Time";
        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!help")) {
            log.info("Responding to !help request by {}",messageCreateEvent.getMessage().getAuthor().getDisplayName());
            messageCreateEvent.getChannel().sendMessage(pingCommand + "\n" + vttCommand + "\n"
                    + dndCommand + "\n" + timeCommand);
        }
    }
}
