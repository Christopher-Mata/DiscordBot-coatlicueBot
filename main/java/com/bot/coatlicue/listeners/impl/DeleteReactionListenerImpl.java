package com.bot.coatlicue.listeners.impl;

import com.bot.coatlicue.listeners.DeleteReactionListener;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.springframework.stereotype.Component;

/**
 * This is the main Delete Message Listener that Deletes messages if they are reacted with the thumbs down emoji.
 *
 * @author Erroede
 */
@Component
public class DeleteReactionListenerImpl implements DeleteReactionListener {

    // This method states that if the emoji response is a thumbs down, delete the message.
    @Override
    public void onReactionAdd(ReactionAddEvent reactionAddEvent) {
        if (reactionAddEvent.getEmoji().equalsEmoji("\uD83D\uDC4E")) {
            reactionAddEvent.deleteMessage();
        }
    }
}
