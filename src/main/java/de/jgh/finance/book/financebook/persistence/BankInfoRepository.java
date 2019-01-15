package de.jgh.finance.book.financebook.persistence;

import de.jgh.finance.book.financebook.persistence.entity.BankInfo;
import de.jgh.finance.book.financebook.persistence.entity.Konto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
    BankInfo findByBlz(String blz);

    BankInfo findByBic(String bic);
}
