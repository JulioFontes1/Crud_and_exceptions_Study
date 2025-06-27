import br.com.julio.dao.UserDAO;
import br.com.julio.exception.CustomException;
import br.com.julio.exception.EmptyStorageException;
import br.com.julio.exception.UserNotFoundException;
import br.com.julio.exception.ValidatorException;
import br.com.julio.model.MenuOption;
import br.com.julio.model.UserModel;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static br.com.julio.validator.UserValidator.verifyModel;

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
                    try {
                        var user = dao.save(requestToSave());
                        System.out.printf("Usuário %s cadastrado!", user);
                    }catch (CustomException ex){
                        ex.printStackTrace();
                        return;
                    }

                }
                case UPDATE -> {
                    try{
                        var user = dao.update(requestToUpdate());
                        System.out.printf("Usuário atualizado!\n %S", user);
                    }catch (UserNotFoundException | EmptyStorageException ex){
                        System.out.println(ex.getMessage());
                    }catch (CustomException ex){
                        ex.printStackTrace();
                        return;
                    }

                }
                case DELETE -> {
                    try {
                        dao.delete(requestId());
                        System.out.println("Usuário excluido da base de dados!");
                    }catch (UserNotFoundException | EmptyStorageException ex){
                        ex.printStackTrace();
                        return;
                    }

                }
                case FIND_BY_ID -> {

                    try{
                        var id = requestId();
                        var user = dao.findById(id);
                        System.out.println(user);
                    }catch (UserNotFoundException | EmptyStorageException ex){
                        ex.printStackTrace();
                        return;
                    }

                }
                case FIND_ALL -> {
                    var users = dao.findAll();
                    System.out.println("Usuários cadastrados");
                    users.forEach(System.out::println);
                }
                case EXIT -> System.exit(0);
            }
        }
    }t

    private static long requestId(){
        System.out.println("Informe identificador o usuário");
        return scanner.nextLong();
    }

    private static UserModel requestToSave() {
        System.out.println("Informe nome o usuário");
        var name = scanner.next();
        System.out.println("Informe o email do usuário");
        var email = scanner.next();
        System.out.println("informe a data de nascimento do usuário (dd/MM/yyyy)");
        var birthdayString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = LocalDate.parse(birthdayString, formatter);


        return validateInputs(0, name, email, birthday);


    }

    private static UserModel requestToUpdate() {
        System.out.println("Informe identificador o usuário");
        var id = scanner.nextLong();
        System.out.println("Informe nome o usuário");
        var name = scanner.next();
        System.out.println("Informe o email do usuário");
        var email = scanner.next();
        System.out.println("informe a data de nascimento do usuário (dd/MM/yyyy)");
        var birthdayString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = LocalDate.parse(birthdayString, formatter);

        return validateInputs(id, name, email, birthday);


    }

    private static UserModel validateInputs(final long id, final String name,
                               final String email, final LocalDate birthday) {
        var user = new UserModel(0, name, email, birthday);
        try {
            verifyModel(user);
            return user;
        }catch (ValidatorException ex){
            throw new CustomException("O seu usúario contem erros " + ex.getMessage(), ex);
        }

    }

}