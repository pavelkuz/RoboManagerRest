package kz.kuzovatov.pavel.daos;

import kz.kuzovatov.pavel.models.NamedEntity;

import java.util.List;

public interface GenericDao<T extends NamedEntity> {
        T findById(int id);

        T findByName(String name);

        List<T> findAll();

        void update(T t);

        void save(T t);

        boolean removeById(int id);
}
