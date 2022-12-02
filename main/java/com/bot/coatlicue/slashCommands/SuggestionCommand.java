package com.bot.coatlicue.slashCommands;

import com.bot.coatlicue.assets.Firebase;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.stereotype.Component;

@Component
public class SuggestionCommand implements SlashCommandCreateListener {
    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("csuggest")){

            Firebase firebase = new Firebase();

            sci.createImmediateResponder()
                    .setContent("Please visit our website: " + "[Work in Progress] "
                    + "to make a suggestion!!!")
                    .respond();

            firebase.close();
        }
    }
}
