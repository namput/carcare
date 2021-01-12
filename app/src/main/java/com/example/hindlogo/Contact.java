package com.example.hindlogo;

public class Contact {
    private String contact_name;
    private String contact_id;

    public Contact() {
    }

    public Contact(String contact_name, String contact_id) {
        this.contact_name = contact_name;
        this.contact_id = contact_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    @Override
    public String toString() {
        return contact_name;
    }
}
