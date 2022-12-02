package com.bot.coatlicue.service.impl;

import com.bot.coatlicue.service.MessagingService;
import com.bot.coatlicue.listeners.DeleteReactionListener;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    private DeleteReactionListener deleteReactionListener;

    @Override
    public CompletableFuture<Message> sendMessage(String author, String title, String description, String footer, String thumbnail, SlashCommandInteraction sci) {
        int red = (int) Math.floor(Math.random() * 255);
        int green = (int) Math.floor(Math.random() * 255);
        int blue = (int) Math.floor(Math.random() * 255);

        return new MessageBuilder().setEmbed(new EmbedBuilder()
                        .setAuthor(author)
                        .setTitle(title)
                        .setDescription(description)
                        .setFooter(footer)
                        .setThumbnail(thumbnail)
                        .setColor(new Color(red, green, blue)))
                .send(sci.getChannel().get());
    }

    @Override
    public void sendMessage(String author, String title, String description, String footer, String thumbnail, SlashCommandInteraction sci, boolean withDelete) {
        this.sendMessage(author, title, description, footer, thumbnail, sci)
                .thenAccept(message -> message.addReactionAddListener(deleteReactionListener)
                        .removeAfter(30, TimeUnit.SECONDS));
    }
}
