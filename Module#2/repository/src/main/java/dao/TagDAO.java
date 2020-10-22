package dao;

import entity.Tag;
import org.springframework.lang.NonNull;
import java.util.Optional;

public interface TagDAO {
    Optional<Tag> findById(long id);
    @NonNull Tag create(@NonNull Tag tag);
    void delete(long id);
}
