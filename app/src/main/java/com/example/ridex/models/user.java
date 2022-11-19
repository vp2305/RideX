package com.example.ridex.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import org.bson.types.ObjectId;

public class user extends RealmObject {
    @PrimaryKey
    @Required
    private ObjectId _id;

    @Required
    private String email;

    @Required
    private String firstName;

    @Required
    private String lastName;

    @Required
    private String uid;

    // Standard getters & setters
    public ObjectId getId() { return _id; }
    public void setId(ObjectId _id) { this._id = _id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
}
