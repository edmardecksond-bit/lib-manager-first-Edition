package com.nb.library.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String id) {
        super("Aucun document trouvé avec l'identifiant : " + id);
    }
}
