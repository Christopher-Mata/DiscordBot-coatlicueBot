package com.bot.coatlicue.slashCommands;

import com.bot.coatlicue.assets.LavaplayerAudioSource;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.stereotype.Component;

/**
 * Note: Do not add a queue system, That is how many people are getting blacklisted off of YouTube right now
 */
@Component
public class JoinCommand implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {
        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("cjoin")) {

            Server server = sci.getServer().get();
            User user = sci.getUser();
            ServerVoiceChannel serverVoiceChannel = user.getConnectedVoiceChannel(server).get();
            DiscordApi api = sci.getApi();
            String url = sci.getOptionStringRepresentationValueByIndex(0).toString();

            // Create a player manager
            AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
            playerManager.registerSourceManager(new YoutubeAudioSourceManager(true));
            AudioPlayer player = playerManager.createPlayer();

            //Format string
            if (url.contains("https://www.youtube.com")) {
                for (int i = 0; i < url.length(); i++) {
                    if (url.charAt(i) == '[') {
                        url = url.substring(i + 1, url.length() - 1);
                        break;
                    }
                }
            } else {
                sci.createImmediateResponder()
                        .setContent("Please enter a youtube link :)")
                        .respond();
            }

            String finalUrl = url;

            if (serverVoiceChannel.canYouConnect()) {
                serverVoiceChannel.connect().thenAccept(audioConnection -> {

                    // Create an audio source and add it to the audio connection's queue
                    AudioSource source = new LavaplayerAudioSource(api, player);
                    audioConnection.setAudioSource(source);

                    // You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
                    playerManager.loadItem(finalUrl, new AudioLoadResultHandler() {

                        @Override
                        public void trackLoaded(AudioTrack track) {
                            player.playTrack(track);
                        }

                        @Override
                        public void playlistLoaded(AudioPlaylist playlist) {
                            for (AudioTrack track : playlist.getTracks()) {
                                player.playTrack(track);
                            }
                        }

                        @Override
                        public void noMatches() {
                            // Notify the user that we've got nothing
                            sci.createImmediateResponder()
                                    .setContent("No Song Matches, Please try again!")
                                    .respond();
                        }

                        @Override
                        public void loadFailed(FriendlyException throwable) {
                            // Notify the user that everything exploded
                            sci.createImmediateResponder()
                                    .setContent("I got overwhelmed, PLZ try a valid link! \n Failed to play: " + finalUrl)
                                    .respond();
                        }
                    });
                }).exceptionally(e -> {

                    // Failed to connect to voice channel (no permissions?)
                    sci.createImmediateResponder()
                            .setContent("Something went wrong while loading " + finalUrl + " Plz try again!")
                            .respond();

                    e.printStackTrace();
                    return null;
                });

                sci.createImmediateResponder()
                        .setContent("Now Playing: " + url)
                        .respond();
            }
        } else {
            sci.createImmediateResponder()
                    .setContent(sci.getUser().getName() + " I disconnected!, if this was " +
                            "not intended then make sure that I have the proper permissions set for" +
                            "the server!")
                    .respond();
        }
    }
}