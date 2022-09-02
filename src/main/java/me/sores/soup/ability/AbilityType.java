package me.sores.soup.ability;

import me.sores.soup.ability.abilities.Fisherman;
import me.sores.soup.ability.abilities.Swarm;
import me.sores.soup.ability.abilities.Switcher;

/**
 * Created by sores on 8/11/2022.
 */
public enum AbilityType {

    FISHERMAN(Fisherman.class, "Fisherman"),
    SWITCHER(Switcher.class, "Switcher"),
    SWARM(Swarm.class, "Swarm")

    ;

    private final Class<? extends Ability> clazz;
    private final String display;

    AbilityType(Class<? extends Ability> clazz, String display) {
        this.clazz = clazz;
        this.display = display;
    }

    public void init() throws Exception {
        getClazz().newInstance();
    }

    public static String toPrettyList(){
        StringBuilder builder = new StringBuilder();

        for(AbilityType type : values()){
            if(builder.length() == 0){
                builder.append(type.toString());
            }else{
                builder.append(", ").append(type.toString());
            }
        }

        return builder.toString();
    }

    public Class<? extends Ability> getClazz() {
        return clazz;
    }

    public String getDisplay() {
        return display;
    }
}
