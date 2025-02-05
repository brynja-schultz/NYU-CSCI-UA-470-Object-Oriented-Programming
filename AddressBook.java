package assignment2;

import java.io.*;
import java.util.*;

public class AddressBook {
  private ArrayList<Contact> contacts; 

  public AddressBook() {
    contacts = new ArrayList<Contact>();
    loadContacts();
    saveContacts();
  }

  public void saveContacts() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.bin"))) {
      oos.writeObject(contacts);
      oos.writeInt(Contact.get_id_counter());
    } catch (IOException e) {
      System.out.println("Error Saving Contacts: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  public void loadContacts() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.bin"))) {
      contacts = (ArrayList<Contact>) ois.readObject();
      Contact.set_id_counter(ois.readInt());
      Contact.set_num_objects(contacts.size());
    } catch (FileNotFoundException e) {
      System.out.println("No Data Found.");
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error Loading Contacts: " + e.getMessage());
    }
  }

  public void print_and_get_choices() {
    System.out.println("Main Window:\n=============\nChoose one of the following options:");
    System.out.println("(1) Add a new contact.\n(2) Search for contact.\n(3) Display all contacts.\n(4) Quit");
    System.out.print("Enter your choice: ");
  }

  public void addContact(Scanner scanner) {
    System.out.print("Name: ");
    String name = scanner.nextLine();
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Phone: ");
    String phone = scanner.nextLine();
    System.out.print("Notes: ");
    String notes = scanner.nextLine();

    // CREATE THE NEW CONTACT WITH THE GIVEN INFORMATION
    Contact contact = new Contact(name, email, phone, notes);
    contacts.add(contact);
    saveContacts();

    System.out.println("----------------------------------------------------------------------------");
    System.out.println("Saved successfully....Press Enter to go back to the Main Window");
    scanner.nextLine();
  }

  public void searchContact(Scanner scanner) {
    String intro_msg = "Main Window --> Search for Contact window";
    String search_term = "";
    System.out.println("(1) Search by Name\n(2) Search by Email\n(3) Search by Phone");
    System.out.print("Enter your choice: ");
    int user_choice = scanner.nextInt();
    scanner.nextLine();

    // SEARCHING BY NAME
    if (user_choice == 1) {
      intro_msg += " --> Search by Name";
      System.out.println("\n" + intro_msg);
      System.out.println("=========================");
      System.out.print("(1) Enter Name: ");
      search_term = scanner.nextLine();
    }
    // SEARCHING BY EMAIL
    else if (user_choice == 2) {
      intro_msg += " --> Search by Email";
      System.out.println("\n" + intro_msg);
      System.out.println("=========================");
      System.out.print("(2) Enter Email: ");
      search_term = scanner.nextLine();
    }

    // SEARCHING BY PHONE
    else if (user_choice == 3) {
      intro_msg += " --> Search by Phone";
      System.out.println("\n" + intro_msg);
      System.out.println("=========================");
      System.out.print("(3) Enter Phone: ");
      search_term = scanner.nextLine();
    }

    // ARRAYLIST OF ALL CONTACTS THAT MATCH THE SEARCH TERM
    ArrayList<Contact> results = new ArrayList<Contact>();

    // SEARCH THROUGH ALL CONTACTS ACCORDING TO SEARCHING METHOD
    for (Contact contact : contacts) {
      // SEARCH BY NAME
      if (user_choice == 1) {
        if (contact.get_name().toLowerCase().contains(search_term.toLowerCase())) {results.add(contact);}
      }
      // SEARCH BY EMAIL
      else if (user_choice == 2) {
        if (contact.get_email().equals(search_term)) {results.add(contact);}
      }
      // SEARCH BY PHONE
      else if (user_choice == 3) {
        if (contact.get_phone().equals(search_term)) {results.add(contact);}
      }
    }

    //IF CONTACTS WITH SEARCH TERM EXIST... print them out to the user
    if (results.size() > 0) {
      System.out.println("\nSearch results:");
      System.out.println("--------------------------------------------------------------------------------");
      System.out
          .println(String.format("%-5s | %-15s | %-25s | %-15s | %-25s", "ID", "Name", "Email", "Phone", "Notes"));
      System.out.println("--------------------------------------------------------------------------------");

      // PRINT OUT THE FOUND CONTACTS
      for (Contact contact : results) {
        System.out.println(String.format("%-5d | %-15s | %-25s | %-15s | %-25s", contact.get_id(), contact.get_name(),
            contact.get_email(), contact.get_phone(), contact.get_notes()));
      }

      System.out.println("\nChoose one of these options:\n(1) To delete a contact\n(2) Back to main window\n");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();

      if (choice == 1) {
        intro_msg += " --> Delete a Contact";
        System.out.println("\n" + intro_msg);
        System.out.println("=========================");
        System.out.print("Enter the Contact ID: ");
        int deleted_id = scanner.nextInt();

        // FIND THE CONTACT TO BE DELETE
        for (Contact contact : contacts) {
          if (contact.get_id() == deleted_id) {
            contacts.remove(contact);
            Contact.set_num_objects(Contact.get_num_objects() - 1);
            System.out.println("Deleted successfully....Press Enter to go back to the Main Window");
            scanner.nextLine();
            break;
          }
        }
        saveContacts();
        scanner.nextLine();
      } 
      else if (choice == 2) {System.out.println();}
    }
  }

  public void displayContacts(Scanner scanner) {
    System.out.println("--------------------------------------------------------------------------------");
    System.out.println(String.format("%-5s | %-15s | %-25s | %-15s | %-25s", "ID", "Name", "Email", "Phone", "Notes"));
    System.out.println("--------------------------------------------------------------------------------");

    // SORT THE CONTACTS IN ALPHABETICAL ORDER
    contacts.sort(Comparator.comparing(Contact::get_name).thenComparing(Contact::get_email));

    for (Contact contact : contacts) {
      System.out.println(String.format("%-5d | %-15s | %-25s | %-15s | %-25s", contact.get_id(), contact.get_name(),
          contact.get_email(), contact.get_phone(), contact.get_notes()));
    }

    System.out.println("\n--------------------------------------------------------------------------------");
    System.out.println("\nPress Enter to go back to the Main Window");
    scanner.nextLine();
  }

  // MAIN METHOD
  public static void main(String[] args) {
    AddressBook addressBook = new AddressBook();
    Scanner scanner = new Scanner(System.in);
    int user_choice = 0;

    // LOOP RUNS UNTIL USER CHOOSES TO EXIT
    while (user_choice != 4) {
      addressBook.print_and_get_choices();
      user_choice = scanner.nextInt();
      scanner.nextLine();

      switch (user_choice) {
        case 1:
          // ADD A NEW CONTACT
          System.out.println("\nMain Window --> Add a new contact window: (Enter the following information)");
          System.out.println("===========================================================================");
          addressBook.addContact(scanner);
          break;

        case 2:
          // SEARCH FOR A CONTACT
          System.out.println("\nMain Window --> Search for a contact window: (Enter the following information)");
          System.out.println("===========================================================================");
          addressBook.searchContact(scanner);
          break;

        case 3:
          // DISPLAY ALL CONTACTS
          System.out.println("\nMain Window --> Display All Contacts");
          System.out.println("=========================");
          addressBook.displayContacts(scanner);
          break;
      }
    }
    scanner.close();
  }
}
