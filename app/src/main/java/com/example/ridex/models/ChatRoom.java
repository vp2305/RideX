package com.example.ridex.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import org.bson.types.ObjectId;

public class ChatRoom extends RealmObject {
    @PrimaryKey
    @Required
    private ObjectId _id;

    private Posts chatRoomID;

    @Required
    private RealmList<String> messages;

    @Required
    private String userOneUID;

    @Required
    private String userTwoUID;

    // Standard getters & setters
    public ObjectId getId() { return _id; }
    public void setId(ObjectId _id) { this._id = _id; }

    public Posts getChatRoomID() { return chatRoomID; }
    public void setChatRoomID(Posts chatRoomID) { this.chatRoomID = chatRoomID; }

    public RealmList<String> getMessages() { return messages; }
    public void setMessages(RealmList<String> messages) { this.messages = messages; }

    public String getUserOneUID() { return userOneUID; }
    public void setUserOneUID(String userOneUID) { this.userOneUID = userOneUID; }

    public String getUserTwoUID() { return userTwoUID; }
    public void setUserTwoUID(String userTwoUID) { this.userTwoUID = userTwoUID; }
}
