package com.bot.coatlicue;

import com.bot.coatlicue.slashCommands.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * This is the main class for the bot executions, handles the overall activity for the bot and waiting for the commands to be
 * activated.
 * It is currently hosted locally. I am trying to find a way to host it for free on a cloud server or something.
 *
 * @author Erroede
 * @version 1.00.00 Full Release
 */
@SpringBootApplication
public class CoatlicueApplication {

	// This creates the environment fot the token to be stored
	@Autowired
	private Environment env;

	// This is to allow the main file, via Spring, to be able to read the other files
	@Autowired
	private HelpCommand helpCommand;
	@Autowired
	private PingCommand pingCommand;
	@Autowired
	private MeetingCommand meetingCommand;
	@Autowired
	private JoinCommand joinCommand;
	@Autowired
	private DisconnectCommand disconnectCommand;
	@Autowired
	private InviteCommand inviteCommand;
	@Autowired
	private PollCommand pollCommand;
	@Autowired
	private RateCommand rateCommand;
	@Autowired
	private RaceCommand raceCommand;
	@Autowired
	private SuggestionCommand suggestionCommand;

	//Connects to the discord API
	private DiscordApi api;

	/**
	 * This is the method is the main method the bot runs on.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CoatlicueApplication.class, args);

	}

	// These sets up the Discord API and allows the bot to come online with the TOKEN
	@Bean
	@ConfigurationProperties(value = "discord-api")
	public DiscordApi discordApi() {

		// The token is set up in the runtime environment
		String token = env.getProperty("TOKEN");
		api = new DiscordApiBuilder().setToken(token)
				.setAllNonPrivilegedIntents()
				.login()
				.join();

		// Sets up a custom status for the bot
		api.updateActivity("(っ◔◡◔)っ");

		//This is where commands are added so the API knows when a command is triggered and what to do in those
		//Instances
		addSlashToAPI();
		addSlashFunctionality(api);

		// This returns the results of the bot and waits for more commands
		return api;
	}

	/**
	 *   _____ _           _      _____                                          _   _____       _ _   _       _
	 *  / ____| |         | |    / ____|                                        | | |_   _|     (_) | (_)     | |
	 * | (___ | | __ _ ___| |__ | |     ___  _ __ ___  _ __ ___   __ _ _ __   __| |   | |  _ __  _| |_ _  __ _| |_ ___  _ __ ___
	 *  \___ \| |/ _` / __| '_ \| |    / _ \| '_ ` _ \| '_ ` _ \ / _` | '_ \ / _` |   | | | '_ \| | __| |/ _` | __/ _ \| '__/ __|
	 *  ____) | | (_| \__ \ | | | |___| (_) | | | | | | | | | | | (_| | | | | (_| |  _| |_| | | | | |_| | (_| | || (_) | |  \__ \
	 * |_____/|_|\__,_|___/_| |_|\_____\___/|_| |_| |_|_| |_| |_|\__,_|_| |_|\__,_| |_____|_| |_|_|\__|_|\__,_|\__\___/|_|  |___/
	 */

	public void addSlashToAPI(){

		SlashCommand.with("chelp"," (✿˵◕‿◕˵)I can tell you what I can do!(˶◕‿◕˶✿)")
				.createGlobal(api)
				.join();

		SlashCommand.with("cping"," What does this do??? (◠‿◠✿)")
				.createGlobal(api)
				.join();

		SlashCommand.with("cmeeting"," A room to chat!(◕∇◕✿) Deactivates with 30 seconds of inactivity",
				Arrays.asList(
								SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "name of the meeting", true),
								SlashCommandOption.create(SlashCommandOptionType.DECIMAL, "users", "number of users attending(between 0-99)", true)))
				.createGlobal(api)
				.join();

		SlashCommand.with("cinvite"," (✿˵◕‿◕˵)Invite your Friends to server!!!(˶◕‿◕˶✿)")
				.createGlobal(api)
				.join();

		SlashCommand.with("cdisconnect", "Disconnects if connected in a channel")
				.createGlobal(api)
				.join();

		SlashCommand.with("cjoin"," I join and play music off VIMEO in VC so you don't feel lowly (///ˬ///✿)",
				Arrays.asList(
								SlashCommandOption.create(SlashCommandOptionType.STRING, "youtubelink", "send the youtube song you want to play", true)))
				.createGlobal(api)
				.join();
		SlashCommand.with("cpoll","Create a poll between two things!!!",
						Arrays.asList(
								SlashCommandOption.create(SlashCommandOptionType.STRING, "optone", "first option", true),
								SlashCommandOption.create(SlashCommandOptionType.STRING, "opttwo", "second option", true)))
				.createGlobal(api)
				.join();
		SlashCommand.with("crate", "rates you based on a word",
						Arrays.asList(
								SlashCommandOption.create(SlashCommandOptionType.STRING, "rate", "insert what you want to be rated by", true)))
				.createGlobal(api)
				.join();
		SlashCommand.with("crace","React with an emoji to win the race")
				.createGlobal(api)
				.join();
		SlashCommand.with("csuggest", "Have a suggestion for the bot???")
				.createGlobal(api)
				.join();
	}

	public void addSlashFunctionality(DiscordApi api){
		api.addSlashCommandCreateListener(helpCommand);
		api.addSlashCommandCreateListener(pingCommand);
		api.addSlashCommandCreateListener(meetingCommand);
		api.addSlashCommandCreateListener(joinCommand);
		api.addSlashCommandCreateListener(disconnectCommand);
		api.addSlashCommandCreateListener(inviteCommand);
		api.addSlashCommandCreateListener(pollCommand);
		api.addSlashCommandCreateListener(rateCommand);
		api.addSlashCommandCreateListener(raceCommand);
		api.addSlashCommandCreateListener(suggestionCommand);
	}
}
