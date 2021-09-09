package com.vocabulary.board.utils;

import org.springframework.data.repository.CrudRepository;

public class JpaUtil {

    public static Object save(CrudRepository repository, Object entity) {
        return repository.save(entity);
    }

}
