package me.sores.soup.profile;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.sores.Orion.util.MessageUtil;
import me.sores.Orion.util.StringUtil;
import me.sores.Orion.util.profile.MongoPlayerProfile;
import me.sores.soup.Init;
import me.sores.soup.handler.KitsHandler;
import me.sores.soup.kit.Kit;
import me.sores.soup.profile.settings.PlayerSettings;
import me.sores.soup.profile.stats.PlayerStats;
import me.sores.soup.util.EconUtil;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Created by sores on 8/11/2022.
 */
public class SoupProfile extends MongoPlayerProfile {

    /**
     * Saved Data
     */
    private PlayerStats playerStats;
    private PlayerSettings playerSettings;

    private String previousKit;
    private List<String> unlockedKits = Lists.newArrayList();

    /**
     * Non-Saved Data
     */
    private Kit selectedKit;

    private boolean building;

    public SoupProfile(UUID uuid) {
        super(uuid);
        selectedKit = null;
        previousKit = null;

        playerStats = new PlayerStats(this);
        playerSettings = new PlayerSettings(this);

        KitsHandler.getInstance().getKits().forEach(kit -> {
            if(kit.isDefault()){
                unlockedKits.add(kit.getName());
            }
        });

        building = false;
    }

    @Override
    public void saveData() {
        try{
            MongoCollection<Document> collection = Init.getInstance().getMongoBase().getCollection();
            Document fetched = fetchCurrentObject();

            if(fetched != null){
                collection.replaceOne(fetched, createDocument());
            }else{
                collection.insertOne(createDocument());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        Document fetched = fetchCurrentObject();

        if(fetched != null){
            try{
                Player player = getPlayer();

                if(fetched.containsKey("previousKit")) previousKit = fetched.getString("previousKit");

                if(fetched.containsKey("unlockedKits")){
                    this.unlockedKits = (List<String>) fetched.get("unlockedKits");
                }

                if(fetched.containsKey("playerStats")) this.playerStats = new PlayerStats(this, (Document) fetched.get("playerStats"));
                if(fetched.containsKey("playerSettings")) this.playerSettings = new PlayerSettings(this, (Document) fetched.get("playerSettings"));

            }catch (Exception ex){
                StringUtil.log("&c[Soup] Failed to load data for " + getName() + "'s profile, see log.");
                ex.printStackTrace();
            }
        }else{
            saveData();
        }

    }

    @Override
    public Document createDocument() {
        Document document = new Document("_id", getID().toString());

        document.put("name", getName());

        document.put("previousKit", previousKit);
        document.put("unlockedKits", unlockedKits);

        if(playerStats != null) document.put("playerStats", playerStats.serialize());
        if(playerSettings != null) document.put("playerSettings", playerSettings.serialize());

        return document;
    }

    @Override
    public Document fetchCurrentObject() {
        FindIterable<Document> cursor = Init.getInstance().getMongoBase().getCollection().find(new Document("_id", getID().toString()));

        return cursor.first();
    }

    /**
     * Saved Data Methods
     */
    public PlayerSettings getPlayerSettings() {
        return playerSettings;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public String getPreviousKit() {
        return previousKit;
    }

    public void setPreviousKit(String previousKit) {
        this.previousKit = previousKit;
    }

    public boolean hasPreviousKit(){
        return previousKit != null && !previousKit.isEmpty();
    }

    public boolean hasKit(Kit kit){
        if(this.unlockedKits.contains(kit.getName())){
            return true;
        }

        if(this.getPlayer().isOp() || this.getPlayer().hasPermission("soup.kits." + kit.getName().toLowerCase())){
            return true;
        }

        return kit.isDefault();
    }

    public void purchaseKit(Kit kit){
        if(unlockedKits.contains(kit.getName())) return;

        unlockedKits.add(kit.getName());
        EconUtil.takeCredits(this, kit.getPrice());

        Player player = getPlayer();
        if(player != null){
            MessageUtil.message(player, ChatColor.YELLOW + "You have purchased the " + ChatColor.LIGHT_PURPLE + kit.getName() + ChatColor.YELLOW + " kit.");
        }
    }

    public void clearKit(){
        setPreviousKit(getSelectedKit().getName());
        getSelectedKit().destroy(getPlayer());
        setSelectedKit(null);
    }

    public List<String> getUnlockedKits() {
        return unlockedKits;
    }

    /**
     * Non-Saved Data Methods
     */
    public Kit getSelectedKit() {
        return selectedKit;
    }

    public void setSelectedKit(Kit selectedKit) {
        this.selectedKit = selectedKit;
    }

    public boolean hasSelectedKit(){
        return selectedKit != null;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

}
