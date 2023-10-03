package com.castoffs.commands;

/*
 * Copyright 2015 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

/**
 * Indicates that a Message was received in a {@link net.dv8tion.jda.api.entities.channel.middleman.MessageChannel MessageChannel}.
 * <br>This includes {@link TextChannel TextChannel} and {@link PrivateChannel PrivateChannel}!
 * 
 * <p>Can be used to detect that a Message is received in either a guild- or private channel. Providing a MessageChannel and Message.
 *
 * <p><b>Requirements</b><br>
 *
 * <p>This event requires at least one of the following intents (Will not fire at all if neither is enabled):
 * <ul>
 *     <li>{@link net.dv8tion.jda.api.requests.GatewayIntent#GUILD_MESSAGES GUILD_MESSAGES} to work in guild text channels</li>
 *     <li>{@link net.dv8tion.jda.api.requests.GatewayIntent#DIRECT_MESSAGES DIRECT_MESSAGES} to work in private channels</li>
 * </ul>

 */
public class CommandRecivedEvent extends GenericMessageEvent
{
    private final Message message;
    private final Command command;

    public CommandRecivedEvent(Command command, @Nonnull JDA api, long responseNumber, @Nonnull Message message){
        super(api, responseNumber, message.getIdLong(), message.getChannel());
        this.message = message;
        this.command = command;
    }

    /**
     * The received {@link net.dv8tion.jda.api.entities.Message Message} object.
     *
     * @return The received {@link net.dv8tion.jda.api.entities.Message Message} object.
     */
    public Message getMessage(){
        return message;
    }

    /**
     * The Author of the Message received as {@link net.dv8tion.jda.api.entities.User User} object.
     * <br>This will be never-null but might be a fake user if Message was sent via Webhook (Guild only).
     * See {@link Webhook#getDefaultUser()}.
     *
     * @return The Author of the Message.
     *
     * @see #isWebhookMessage()
     */
    @Nonnull
    public User getAuthor(){
        return message.getAuthor();
    }

    /**
     * The Author of the Message received as {@link net.dv8tion.jda.api.entities.Member Member} object.
     * <br>This will be {@code null} in case of Message being received in
     * a {@link PrivateChannel PrivateChannel}
     * or {@link #isWebhookMessage() isWebhookMessage()} returning {@code true}.
     *
     * @return The Author of the Message as null-able Member object.
     *
     * @see    #isWebhookMessage()
     */
    @Nullable
    public Member getMember(){
        return message.getMember();
    }

    /**
     * Whether or not the Message received was sent via a Webhook.
     * <br>This is a shortcut for {@code getMessage().isWebhookMessage()}.
     *
     * @return True, if the Message was sent via Webhook
     */
    public boolean isWebhookMessage(){
        return getMessage().isWebhookMessage();
    }

    /**
     * Retrieve the command which was used to execute this event
     * @return the command
     */
    public Command getCommand(){
        return command;
    }

    /**
     * breaks the message into an array of arguments
     * the first index is command name
     * @return the arguments
     */
    public String[] getArgs() {
        return message.getContentRaw().trim().split("\\s+");
    }

    /**
     * gets the argument at the specified index
     * @param index
     * @return
     */
    public String getArgumentAtIndexAsString(int index){
        return this.getArgs()[index];
    }
    
    /**
     * checks if the argument at the index is valid
     * @param index
     * @return
     */
    public boolean isArgumentValidAtIndex(int index){
        int length = getArgs().length;
        return index >= 0 && index < length;
    }

    /**
     * Checks if the argument at the specified index is valid and of the specified type.
     * Only primitive wrapper classes are supported.
     *
     * @param index the index of the argument to check
     * @param type  the expected type of the argument
     * @return true if the argument is valid and of the specified type, false otherwise
     */
    public boolean confirmArgumentType(int index, Object type) {
        if (!isArgumentValidAtIndex(index)) {
            return false;
        }

        String argument = getArgs()[index];
        Class<?> expectedType = (Class<?>) type;

        try {
            // Use the parse*() methods of the wrapper classes to validate the argument type
            if (expectedType == Integer.class) {
                Integer.parseInt(argument);
            } else if (expectedType == Double.class) {
                Double.parseDouble(argument);
            } else if (expectedType == Boolean.class) {
                Boolean.parseBoolean(argument);
            } else if (expectedType == Float.class) {
                Float.parseFloat(argument);
            } else if (expectedType == Long.class) {
                Long.parseLong(argument);
            } else if (expectedType == Short.class) {
                Short.parseShort(argument);
            } else if (expectedType == Byte.class) {
                Byte.parseByte(argument);
            } else if (expectedType == Character.class) {
                if (argument.length() != 1) {
                    return false;
                }
                Character.valueOf(argument.charAt(0));
            } else {
                // Unsupported type
                return false;
            }
        } catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e) {
            // Invalid argument or conversion error
            return false;
        }
        return true;
    }


}
