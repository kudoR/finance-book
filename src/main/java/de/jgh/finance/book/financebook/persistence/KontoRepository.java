package de.jgh.finance.book.financebook.persistence;

import de.jgh.finance.book.financebook.persistence.entity.Konto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KontoRepository extends JpaRepository<Konto, Long> {
    Konto findByIban(String iban);
}
