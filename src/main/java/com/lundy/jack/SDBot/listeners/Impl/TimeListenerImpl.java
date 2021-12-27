package com.lundy.jack.SDBot.listeners.Impl;

import com.lundy.jack.SDBot.AudioPlayer.LavaplayerAudioSource;
import com.lundy.jack.SDBot.listeners.TimeListener;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeListenerImpl implements TimeListener {

    @Value("${time.url}")
    private String audioURL;
    /**
     * This method instructs the Bot what to do when a message with the contents "!time" is created
     *
     * @param messageCreateEvent - the message creation event
     */
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!time")) {
            if (messageCreateEvent.getMessage().getAuthor().isServerAdmin()) {
                createVoiceChannelConnection(messageCreateEvent);
            } else {
                messageCreateEvent.getChannel().sendMessage("You must be a server admin to use the '!time' command");
            }
        }
    }

    /**
     * This method will search through server voice channels and find the ID of the one the user is currently connected to and return it
     *
     * @param event - a MessageCreateEvent
     * @return a long value containing the ID of the user's current voice channel
     */
    public long getUserVoiceChannel(MessageCreateEvent event) {
        User user = event.getMessage().getAuthor().asUser().get();
        long userVoiceChannel = 0L;
        List<ServerVoiceChannel> voiceChannels = event.getServer().get().getVoiceChannels();
        for (ServerVoiceChannel channel : voiceChannels) {
            if (user.isConnected(channel)) {
                userVoiceChannel = channel.getId();
            }
        }
        return userVoiceChannel;
    }

    /**
     * used to create a connection to a voice channel
     *
     * @param event - the message creation event
     */
    public void createVoiceChannelConnection(MessageCreateEvent event) {
        long userVoiceChannel = getUserVoiceChannel(event);
        if (userVoiceChannel != 0) {
            try {
                ServerVoiceChannel channel = event.getApi().getServerVoiceChannelById(userVoiceChannel).get();
                channel.connect().thenAccept(audioConnection -> {

                    //create player and playerManager
                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    playerManager.registerSourceManager(new YoutubeAudioSourceManager()); //use YouTube for audio
                    AudioPlayer player = playerManager.createPlayer();

                    // create audio source and add it to the connection's queue
                    AudioSource source = new LavaplayerAudioSource(event.getApi(), player);
                    audioConnection.setAudioSource(source);

                    // load audio track and define commands
                    playerManager.loadItem(audioURL, new AudioLoadResultHandler() {
                        @Override
                        public void trackLoaded(AudioTrack audioTrack) {
                            player.playTrack(audioTrack);
                            event.getChannel().sendMessage("Bruce Buffer says 'It's TIME!!'");
                        }

                        @Override
                        public void playlistLoaded(AudioPlaylist audioPlaylist) {
                            for(AudioTrack track: audioPlaylist.getTracks()){
                                player.playTrack(track);
                            }
                        }

                        @Override
                        public void noMatches() {
                            event.getMessage().getChannel().sendMessage("I can't find that audio file - Sorry :/ "+audioURL);
                        }

                        @Override
                        public void loadFailed(FriendlyException e) {
                            event.getMessage().getChannel().sendMessage("Something went wrong, and I couldn't load that file - Sorry about that");
                        }
                    });

                }).exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
