package com.bot.coatlicue.slashCommands;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.invite.Invite;
import org.javacord.api.entity.server.invite.InviteBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.stereotype.Component;

@Component
public class InviteCommand implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("cinvite")){

            ServerTextChannel channel =sci.getChannel().get().asServerTextChannel().get();

            Invite invite = new InviteBuilder(channel)
                    .setMaxAgeInSeconds(60*60*24)
                    .setMaxUses(42)
                    .create()
                    .join();

            sci.createImmediateResponder()
                    .setContent("here is the invite you requested! It is valid for 42 uses! \n")
                    .append(invite.getUrl())
                    .respond();
        }
    }
}
