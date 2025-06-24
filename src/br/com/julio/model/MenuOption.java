package br.com.julio.model;

import br.com.julio.dao.UserDAO;

import java.util.function.Consumer;

public enum MenuOption {
    SAVE,
    UPDATE,
    DELETE,
    FIND_BY_ID,
    FIND_ALL,
    EXIT;
}
