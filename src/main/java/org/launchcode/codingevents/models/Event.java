package org.launchcode.codingevents.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class Event extends AbstractEntity {


    @NotBlank(message = "Name is required.")
    @Size(min=3,max=50, message = "Name must be between 3 and 50 characters.")
    private String name;

    @Size(max=500, message = "Description too long!")
    private String description;

    @NotBlank(message="Email is required.")
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    @NotBlank(message = "Location is required")
    @NotNull(message = "Location is required")
    private String location;

    @AssertTrue(message = "Registration should be required")
    private boolean registrationRequired;

    @Min(value = 1,message = "Value must be greater than zero!")
    private int numberOfAttendees;

    private EventType type;

    public Event(String name, String description, String contactEmail, String location,
                 boolean registrationRequired, int numberOfAttendees, EventType type) {
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.location = location;
        this.registrationRequired = registrationRequired;
        this.numberOfAttendees = numberOfAttendees;
        this.type = type;
    }

    public Event(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isRegistrationRequired() {
        return registrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }

    public int getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setNumberOfAttendees(int numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

}
