package com.bot.coatlicue.slashCommands;

import com.bot.coatlicue.service.MessagingService;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class HelpCommand implements SlashCommandCreateListener {

    @Autowired
    private MessagingService messagingService;

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("chelp")){

            messagingService.sendMessage(sci.getUser().getName(),
                    "List Of Commands!",
                    """
                            **/chelp**
                             -List of bot commands \s
                            **/crate**
                             -Rates you 0-100% based on [word].\s
                            **/crace**
                             -First to emote on the embedded image wins.\s
                            **/cping**
                             -Pong!.
                            **/cmeeting**
                             -Creates a temp VC (if no one joins in 30 seconds it will be deleted)\s
                            **/cjoin**
                             -Joins and plays music. Please use youtube links! \s
                             **/cpoll**
                             -Creates a poll! \s
                             **/cdisconnect**
                             -If joined to a VC, I will disconnect 
                             **NOTE: Since YouTube likes to ban
                             networks that implement a queue system. If you want to play a song right after
                             another, please put the link of the song once the current one has ended. Sorry for
                             the inconvenience**""",
                    "Here to help!",
                    "https://otakukart.com/wp-content/uploads/2021/12/Destiny.jpg",
                    sci);
                    //TODO: Add a different image for the command

            sci.createImmediateResponder()
                    .setContent(sci.getUser().getName() + " Got sent a list of my Commands!!! Enjoy!")
                    .respond();
        }
    }
}
