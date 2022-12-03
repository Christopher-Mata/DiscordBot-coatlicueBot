package com.bot.coatlicue.slashCommands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class PollCommand implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("cpoll")){

            // Grabs the options that the user specified
            String opt1 = String.valueOf(sci.getOptionStringRepresentationValueByIndex(0));
            String opt2 = String.valueOf(sci.getOptionStringValueByIndex(1));

            // Format the first option
            for(int i = 0; i < opt1.length(); i++){

                if (opt1.charAt(i) == '['){
                    opt1 = opt1.substring(i + 1, opt1.length() - 1);
                    break;
                }
            }

            // Format the last option
            for(int i = 0; i < opt2.length(); i++){

                if (opt2.charAt(i) == '['){
                    opt2 = opt2.substring(i + 1, opt2.length() - 1);
                    break;
                }
            }

            // Creates the embed to host the poll
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Poll Created!!! PLZ respond.")
                    .setAuthor(sci.getUser())
                    .setColor(Color.PINK)
                    .setDescription("**Option 1:**\n " + opt1 + "\n react with thumbs up! \n" +
                            "**Option 2:**\n " + opt2 + "\n react with thumbs down! \n");

            // Sends the embed
            sci.createImmediateResponder().addEmbed(embed).respond();
        }
    }
}

