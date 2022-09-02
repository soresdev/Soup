package me.sores.soup.profile.settings;

import me.sores.soup.profile.IStatWrapper;
import me.sores.soup.profile.SoupProfile;
import org.bson.Document;

/**
 * Created by sores on 8/11/2022.
 */
public class PlayerSettings implements IStatWrapper {

    private final SoupProfile profile;

    private boolean scoreboard = true, streakMessages = true, bountyMessages = true;

    public PlayerSettings(SoupProfile profile) {
        this.profile = profile;
    }

    public PlayerSettings(SoupProfile profile, Document document){
        this.profile = profile;
        deserialize(document);
    }

    @Override
    public Document serialize() {
        Document document = new Document();

        document.put("scoreboard", this.scoreboard);
        document.put("streakMessages", this.streakMessages);
        document.put("bountyMessages", this.bountyMessages);

        return document;
    }

    @Override
    public void deserialize(Document document) {
        this.scoreboard = document.getBoolean("scoreboard");
        this.streakMessages = document.getBoolean("streakMessages");
        this.bountyMessages = document.getBoolean("bountyMessages");
    }

    public SoupProfile getProfile() {
        return profile;
    }

    public boolean isScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(boolean scoreboard) {
        this.scoreboard = scoreboard;
    }

    public boolean isStreakMessages() {
        return streakMessages;
    }

    public void setStreakMessages(boolean streakMessages) {
        this.streakMessages = streakMessages;
    }

    public boolean isBountyMessages() {
        return bountyMessages;
    }

    public void setBountyMessages(boolean bountyMessages) {
        this.bountyMessages = bountyMessages;
    }

}
