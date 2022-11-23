package com.example.ridex.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import org.bson.types.ObjectId;

public class Posts extends RealmObject {
    @PrimaryKey
    @Required
    private ObjectId _id;

    private ChatRoom chatRoomID;

    @Required
    private RealmList<String> confirmedUsers;

    private String date;

    @Required
    private String driverUID;

    private String fromLocation;

    private Ride postID;

    private String ridePostDescription;

    private String time;

    private String toLocation;

    // Standard getters & setters
    public ObjectId getId() { return _id; }
    public void setId(ObjectId _id) { this._id = _id; }

    public ChatRoom getChatRoomID() { return chatRoomID; }
    public void setChatRoomID(ChatRoom chatRoomID) { this.chatRoomID = chatRoomID; }

    public RealmList<String> getConfirmedUsers() { return confirmedUsers; }
    public void setConfirmedUsers(RealmList<String> confirmedUsers) { this.confirmedUsers = confirmedUsers; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDriverUID() { return driverUID; }
    public void setDriverUID(String driverUID) { this.driverUID = driverUID; }

    public String getFromLocation() { return fromLocation; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }

    public Ride getPostID() { return postID; }
    public void setPostID(Ride postID) { this.postID = postID; }

    public String getRidePostDescription() { return ridePostDescription; }
    public void setRidePostDescription(String ridePostDescription) { this.ridePostDescription = ridePostDescription; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getToLocation() { return toLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }
}
