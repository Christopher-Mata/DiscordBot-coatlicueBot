package com.bot.coatlicue.slashCommands;

import com.bot.coatlicue.service.MessagingService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RaceCommand implements SlashCommandCreateListener {

    //Checks if the races are ongoing
    private static boolean active = false;

    // Makes the MessagingServices(Embedded) available to the class.
    @Autowired
    private MessagingService messagingService;

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("crace")){

            sci.getChannel().get();

            if(!active) {
                active = true;
                messagingService.sendMessage(sci.getUser().getName(),
                                "The race begins!",
                                "Be the first to **react** to this message to win!",
                                null,
                                "https://images.unsplash.com/photo-1457969414820-5fdd86fc0b84?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1048&q=80",
                                sci)
                        .thenAccept(message -> {
                            message.addReactionAddListener(listener -> {
                                //If the race is reacted to set the boolean to false and edit the message to display the winner.
                                if(active) {
                                    active = false;
                                    message.edit(new EmbedBuilder()
                                            .setTitle("The race ends!")
                                            .setDescription("Congratulations! **" + sci.getUser().getName() + "** was first!\nThe race is now over!")
                                            .setFooter("Race again?"));
                                }
                            });
                        });

                sci.createImmediateResponder()
                        .setContent("Race created")
                        .respond();
            }
        }
    }
}
