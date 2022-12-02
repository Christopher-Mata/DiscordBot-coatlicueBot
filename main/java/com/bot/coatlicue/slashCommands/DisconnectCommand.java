package com.bot.coatlicue.slashCommands;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.VoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.stereotype.Component;

@Component
public class DisconnectCommand implements SlashCommandCreateListener {
    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {
        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("cdisconnect")){

            Server server = sci.getServer().get();
            ServerVoiceChannel vc = sci.getUser().getConnectedVoiceChannel(server).get();

            try{
                vc.disconnect();

                sci.createImmediateResponder()
                        .setContent("Disconnected successfully")
                        .respond();

            } catch(Exception e){
                sci.createImmediateResponder()
                        .setContent("Could not disconnect")
                        .respond();
            }
        }
    }
}
