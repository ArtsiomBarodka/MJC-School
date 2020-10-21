package dao;

import entity.Tag;

import java.util.Optional;

public interface TagDAO {
    Optional<Tag> findById(long id);
    Tag create(Tag tag);
    void delete(long id);
}
