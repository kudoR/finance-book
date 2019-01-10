package de.jgh.finance.book.financebook.persistence;

import de.jgh.finance.book.financebook.persistence.entity.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {
}
