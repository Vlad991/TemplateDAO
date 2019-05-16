package ua.univer.controller;

import ua.univer.model.account.Account;
import ua.univer.model.account.AccountStatus;
import ua.univer.model.client.Client;

public class TestDAO {
    public static void main(String[] args) throws Exception {
        ClientDAO clientDAO = new ClientDAO();
        Client vasya = clientDAO.getClientByName("Vasya");
        System.out.println(vasya);

        AdministratorDAO administratorDAO = new AdministratorDAO();
        System.out.println(administratorDAO.getAdministratorByName("Vlad"));


    }
}
