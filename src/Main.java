import br.com.julio.dao.UserDAO;
import br.com.julio.model.MenuOption;
import br.com.julio.model.UserModel;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    private final static UserDAO dao = new UserDAO();
    private final static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {


        while (true){
            System.out.println("Bem vindo ao cadastro de usuários, selecione a operação desejada.");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Seleciona pelo Identificador");
            System.out.println("5 - Listar");
            System.out.println("6 - Sair");
            var userInput = scanner.nextInt();

            var selectedOption = MenuOption.values()[userInput - 1];
            switch (selectedOption){
                case SAVE -> {
                    var user = dao.save(requestToSave());
                    System.out.printf("Usuário %s cadastrado!", user);
                }
                case UPDATE -> {
                    var user = dao.update(requestToUpdate());
                    System.out.println("Usuário atualizado!");
                }
                case DELETE -> {
                    dao.delete(requestId());
                    System.out.println("Usuário excluido da base de dados!");
                }
                case FIND_BY_ID -> {
                    var id = requestId();
                    var user = dao.findById(id);
                    System.out.println(user);
                }
                case FIND_ALL -> {
                    var users = dao.findAll();
                    System.out.println("Usuários cadastrados");
                    users.forEach(System.out::println);
                }
                case EXIT -> System.exit(0);
            }
        }
    }

    private static long requestId(){
        System.out.println("Informe identificador o usuário");
        return scanner.nextLong();
    }

    private static UserModel requestToSave(){
        System.out.println("Informe nome o usuário");
        var name = scanner.next();
        System.out.println("Informe o email do usuário");
        var email = scanner.next();
        System.out.println("informe a data de nascimento do usuário (dd/MM/yyyy)");
        var birthdayString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = OffsetDateTime.parse(birthdayString, formatter);
        return new UserModel(0, name, email, birthday);
    }

    private static UserModel requestToUpdate(){
        System.out.println("Informe identificador o usuário");
        var id = scanner.nextLong();
        System.out.println("Informe nome o usuário");
        var name = scanner.next();
        System.out.println("Informe o email do usuário");
        var email = scanner.next();
        System.out.println("informe a data de nascimento do usuário (dd/MM/yyyy)");
        var birthdayString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = OffsetDateTime.parse(birthdayString, formatter);
        return new UserModel(id, name, email, birthday);
    }

}