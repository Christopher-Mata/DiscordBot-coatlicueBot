package com.bot.coatlicue.slashCommands;

import com.bot.coatlicue.service.MessagingService;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RateCommand implements SlashCommandCreateListener {

    //This creates the pattern based upon the regex "!rate(\\w+)"
    private final static Pattern pattern = Pattern.compile("!rate (\\w+)");

    // Makes the MessagingServices(Embedded) available to the class.
    @Autowired
    private MessagingService messagingService;

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction sci = slashCommandCreateEvent.getSlashCommandInteraction();

        if(sci.getCommandName().equals("crate")){

            String rateObj = String.valueOf(sci.getOptionStringRepresentationValueByIndex(0));

            // Format the first option
            for(int i = 0; i < rateObj.length(); i++){

                if (rateObj.charAt(i) == '['){
                    rateObj = rateObj.substring(i + 1, rateObj.length() - 1);
                    break;
                }
            }

            //Do the rating
            int rating = (int) Math.floor(Math.random() * 100) + 1;

            //This creates the embed with the information pertaining to the rating
            messagingService.sendMessage(sci.getUser().getName(),
                    "Rate calculator",
                    sci.getUser().getName() + " is " + rating + "% " + rateObj,
                    "Rate again?",
                    "https://www.looper.com/img/gallery/takt-op-destiny-release-date-cast-and-plot-what-we-know-so-far/intro-1625582798.webp",
                    sci);

            // else (if the syntax is wrong) it creates an embed that states the correct syntax for the command

            sci.createImmediateResponder()
                    .setContent("Here you go " + sci.getUser().getName())
                    .respond();

        }
    }
}

