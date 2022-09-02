package me.sores.soup.profile.stats;

import me.sores.soup.profile.IStatWrapper;
import me.sores.soup.profile.SoupProfile;
import org.bson.Document;

/**
 * Created by sores on 8/11/2022.
 */
public class PlayerStats implements IStatWrapper {

    private final SoupProfile profile;

    private int kills = 0, deaths = 0, credits = 0, streak = 0;

    public PlayerStats(SoupProfile profile) {
        this.profile = profile;
    }

    public PlayerStats(SoupProfile profile, Document document){
        this.profile = profile;
        deserialize(document);
    }

    @Override
    public void deserialize(Document document) {
        this.kills = document.getInteger("kills");
        this.deaths = document.getInteger("deaths");
        this.streak = document.getInteger("streak");
        this.credits = document.getInteger("credits");
    }

    @Override
    public Document serialize() {
        Document document = new Document();

        document.put("kills", this.kills);
        document.put("deaths", this.deaths);
        document.put("streak", this.streak);
        document.put("credits", this.credits);

        return document;
    }

    public SoupProfile getProfile() {
        return profile;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

}
