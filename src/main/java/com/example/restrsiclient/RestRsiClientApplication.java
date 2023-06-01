package com.example.restrsiclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.EntityModel;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class RestRsiClientApplication implements CommandLineRunner {
    private static final String BASE_URL = "http://10.182.234.166:8080";
    private final PersonClient personClient;

    public RestRsiClientApplication() {
        this.personClient = new PersonClient(BASE_URL);
    }

    public static void main(String[] args) {
        MyData.myInfo();
        SpringApplication app = new SpringApplication(RestRsiClientApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "3000"));
        app.run(args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\nWybierz opcję: ");
            System.out.println("1. Wyświetl wszystkich");
            System.out.println("2. Wyświetl osobę");
            System.out.println("3. Dodaj osobę");
            System.out.println("4. Aktualizuj osobę");
            System.out.println("5. Usuń osobę");
            System.out.println("6. Wyświetl rozmiar listy");
            System.out.println("0. Wyjdź\n");

            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("\nLista osób:");
                    List<Person> persons = personClient.getAllPersons();
                    for (int i = 0; i < persons.size(); ++i) {
                        System.out.println(persons.get(i));
                    }
                }
                case "2" -> {
                    System.out.print("\nPodaj id: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    try{
                        System.out.println(personClient.getPerson(id));
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.print("\nPodaj id: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Podaj imię i nazwisko: ");
                    String name = scanner.nextLine();
                    System.out.print("Podaj wiek: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("Podaj email: ");
                    String email = scanner.nextLine();
                    Person newPerson = new Person(id, name, age, email);
                    try{
                        Person createdPerson = personClient.addPerson(newPerson);
                        System.out.println("Dodano osobę: " + createdPerson);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case "4" -> {
                    System.out.print("\nPodaj ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Podaj nowe imię i nazwisko: ");
                    String newName = scanner.nextLine();
                    System.out.print("Podaj nowy wiek: ");
                    int newAge = Integer.parseInt(scanner.nextLine());
                    System.out.print("Podaj nowy email: ");
                    String newEmail = scanner.nextLine();
                    Person updatedPerson = new Person(id, newName, newAge, newEmail);
                    try {
                        Person result = personClient.updatePerson(updatedPerson);
                        if (result != null) {
                            System.out.println("Zaktualizowano osobę: " + result);
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case "5" -> {
                    System.out.print("\nPodaj ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    try {
                        if(personClient.deletePerson(id)){
                            System.out.println("Usunięto osobę z ID: " + id);
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case "6" -> {
                    int size = personClient.getSize();
                    System.out.println("\nRozmiar listy: " + size);
                }
                case "0" -> System.out.println("Miłego dnia :)");
                default -> System.out.println("Nieznana opcja. Spróbuj ponownie.");
            }
        } while (!choice.equals("0"));

        scanner.close();
    }
}
