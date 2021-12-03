/**
 * This class is part of the "Insanity" application. 
 * "Insanity" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command can consist of five words, one command word which is what the user wants to do
 * and the other 4 words can be used to point to items or characters or used for filler words.
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then all the other words are <null>.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2021.12.01
 */

public class Command
{
    private String commandWord;
    private String secondWord;
    private String thirdWord;
    private String fifthWord;
    private String fourthWord;
    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord,String thirdWord,String fourthWord,String fifthWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.fourthWord = fourthWord;
        this.fifthWord = fifthWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }
    public String getThirdWord()
    {
        return thirdWord;
    }
    public String getFourthWord()
    {
        return fourthWord;
    }
    public String getFifthWord()
    {
        return fifthWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

