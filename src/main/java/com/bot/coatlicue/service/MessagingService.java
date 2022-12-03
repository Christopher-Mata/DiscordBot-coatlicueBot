package com.bot.coatlicue.service;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.concurrent.CompletableFuture;

public interface MessagingService {

    CompletableFuture<Message> sendMessage(String author, String title, String description, String footer, String thumbnail, SlashCommandInteraction sci);
    void sendMessage(String author, String title, String description, String footer, String thumbnail,SlashCommandInteraction sci, boolean withDelete);
}
