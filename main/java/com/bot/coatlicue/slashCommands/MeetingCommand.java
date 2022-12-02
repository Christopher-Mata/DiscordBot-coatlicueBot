package com.bot.coatlicue.slashCommands;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MeetingCommand implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();
        DiscordApi api;

        if(sci.getCommandName().equals("cmeeting")){

            // Getting command properties
            api = sci.getApi();
            Server server = sci.getServer().get();

            try {
                // Getting command Options
                String name = String.valueOf(sci.getOptionStringRepresentationValueByIndex(0));
                double numOfPeople = sci.getOptionDecimalValueByIndex(1).get();

                // Format the name


                // Create a temporary voice channel
                ServerVoiceChannel channel = new ServerVoiceChannelBuilder(server)
                        .setName(name)
                        .setUserlimit((int) numOfPeople)
                        .create()
                        .join();

                // Delete the channel if the last user leaves
                channel.addServerVoiceChannelMemberLeaveListener(event -> {
                    if (event.getChannel().getConnectedUserIds().isEmpty()) {
                        event.getChannel().delete();
                    }
                });

                // Delete the channel if no user joined in the first 30 seconds
                api.getThreadPool().getScheduler().schedule(() -> {
                    if (channel.getConnectedUserIds().isEmpty()) {
                        channel.delete();
                    }
                }, 30, TimeUnit.SECONDS);

                // sends information regarding the voice channel
                sci.createImmediateResponder()
                        .setContent("Senpai! Temp channel created! \n" +
                                "Channel will be deleted after the last user leaves, or if 30 seconds have passed without someone" +
                                " joining!")
                        .respond();
            } catch (Exception e){
                // Create a temporary voice channel
                ServerVoiceChannel channel = new ServerVoiceChannelBuilder(server)
                        .setName("meeting")
                        .setUserlimit(20)
                        .create()
                        .join();

                // Delete the channel if the last user leaves
                channel.addServerVoiceChannelMemberLeaveListener(event -> {
                    if (event.getChannel().getConnectedUserIds().isEmpty()) {
                        event.getChannel().delete();
                    }
                });

                // Delete the channel if no user joined in the first 30 seconds
                api.getThreadPool().getScheduler().schedule(() -> {
                    if (channel.getConnectedUserIds().isEmpty()) {
                        channel.delete();
                    }
                }, 30, TimeUnit.SECONDS);

                // sends information regarding the voice channel
                sci.createImmediateResponder()
                        .setContent("Senpai! Something went wrong! \n" +
                                "Channel created with default values, will be deleted after the last user leaves, or if 30 seconds have passed without someone" +
                                " joining!")
                        .respond();
            }
        }
    }
}
