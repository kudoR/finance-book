package de.jgh.finance.book.financebook.persistence;

import de.jgh.finance.book.financebook.persistence.entity.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {
    Buchung findBuchungByValutaAndBdateAndValueAndSaldoAndIbanAndBic(long valuta, long bdate, BigDecimal value, BigDecimal saldo, String iban, String bic);
}
