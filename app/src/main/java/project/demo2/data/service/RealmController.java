package project.demo2.data.service;


/**
 * Created by hassan on 9/24/2017.
 */

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import project.demo2.data.model.Item;

/**
 * this class handles deletion ,adding and getting items from realm
 */
public class RealmController {


    /**
     * save items to realm and delete old copies
     *
     * @param items
     */
    public void saveItems(List<Item> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Item> rows =  Realm.getDefaultInstance().where(Item.class).findAll();
        rows.deleteAllFromRealm();
        realm.copyToRealm(items);
        realm.commitTransaction();
        realm.close();
    }


    /**
     * delete item from realm
     * @param item
     */
    public void deleteItem(Item item){
        Realm realm= Realm.getDefaultInstance();
        RealmResults<Item> rows =  realm.where(Item.class).equalTo("id",item.getId()).findAll();
        realm.beginTransaction();
        rows.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    /**
     * get items cached in realm
     *
     * @return
     */
    public List<Item> getItems() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> examplesRealm = realm.where(Item.class).findAll();
        List<Item> itemList = realm.copyFromRealm(examplesRealm);
        realm.close();
        return itemList;
    }

    public void addItem(final Item itemToAdd){
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                Item item = realm.createObject(Item.class,itemToAdd.getId());
                item.setImageUrl(itemToAdd.getImageUrl());
                item.setDescription(itemToAdd.getDescription());

            }
        });

        Realm.getDefaultInstance().close();
    }

}
