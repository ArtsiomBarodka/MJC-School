package dao;

import entity.GiftCertificate;

import java.util.Optional;

public interface GiftCertificateDAO {
    Optional<GiftCertificate> findById(long id);


}
