package assignment2;

import java.io.*;

public class Contact implements Serializable {
  private static final long serialVersionUID = 1L;
  private static int id_counter = 0;
  private static int num_objects = 0;
  private int id;
  private String name;
  private String email;
  private String phone;
  private String notes;

  public Contact(String name, String email, String phone, String notes) {
    // INCREASE NUMBER OF OBJECTS CREATED WITHIN THIS CLASS
    Contact.num_objects++;

    // SET CONTACT INFORMATION
    id_counter++;
    this.id = id_counter;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.notes = notes;
  }

  // GET,SET, & RESET NUMBER OF OBJECTS CREATED FROM THIS CLASS
  public static void set_num_objects(int num_objects) {Contact.num_objects = num_objects;}

  public static int get_num_objects() {return Contact.num_objects;}

  public static void reset_num_objects() {Contact.num_objects = 0;}

  // GET, SET, & RESET ID
  public static int get_id_counter() {return id_counter;}

  public static void reset_id_counter() {id_counter = 0;}

  public static void set_id_counter(int new_id_counter) {id_counter = new_id_counter;}

  // GETTERS
  public int get_id() {return id;}

  public String get_name() {return name;}

  public String get_email() {return email;}

  public String get_phone() {return phone;}

  public String get_notes() {return notes;}

  // SETTERS
  public void set_name(String name) {this.name = name;}

  public void set_email(String email) {this.email = email;}

  public void set_phone(String phone) {this.phone = phone;}

  public void set_notes(String notes) {this.notes = notes;}

  @Override
  public String toString() {return id + "  | " + name + "\t\t\t| " + email + "\t\t\t| " + phone + "\t\t\t\t| " + notes;}
}
