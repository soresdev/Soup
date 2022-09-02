package me.sores.soup.profile;

import org.bson.Document;

/**
 * Created by sores on 8/11/2022.
 */
public interface IStatWrapper {

    Document serialize();
    void deserialize(Document document);

}
