package de.jgh.finance.book.financebook.service;

import de.jgh.finance.book.financebook.mapper.BuchungMapper;
import de.jgh.finance.book.financebook.persistence.BankInfoRepository;
import de.jgh.finance.book.financebook.persistence.BuchungRepository;
import de.jgh.finance.book.financebook.persistence.KontoRepository;
import de.jgh.finance.book.financebook.persistence.entity.BankInfo;
import de.jgh.finance.book.financebook.persistence.entity.Buchung;
import org.kapott.hbci.structures.Konto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import static de.jgh.finance.book.financebook.service.AccountFetchType.*;
import static org.kapott.hbci.GV_Result.GVRKUms.UmsLine;

@Service
public class ImportService {

    @Autowired
    private FinTSService finTSService;

    @Autowired
    private KontoRepository kontoRepository;

    @Autowired
    private BuchungMapper buchungMapper;

    @Autowired
    private BuchungRepository buchungRepository;

    @Autowired
    private BankInfoRepository bankInfoRepository;


    @Scheduled(fixedRate = 3600000)
    public void performImports() {
        kontoRepository.findAll().forEach(konto -> performImport(konto.getBlz(), konto.getUser(), konto.getPin(), FULL));
    }

    private void performImport(String blz, String user, String pin, AccountFetchType fetchType) {
        HashMap<Konto, List<UmsLine>> accountsWithBookings = finTSService.fetchAccount(blz, user, pin, fetchType);
        accountsWithBookings.entrySet().forEach(this::processKonto);
    }

    private void processKonto(Entry<Konto, List<UmsLine>> entry) {
        Konto konto = entry.getKey();

        de.jgh.finance.book.financebook.persistence.entity.BankInfo bankInfo = bankInfoRepository.findByBlz(konto.blz);
        if (bankInfo == null) {
            bankInfo = bankInfoRepository.findByBic(konto.bic);
            if (bankInfo == null) {
                bankInfo = new de.jgh.finance.book.financebook.persistence.entity.BankInfo();
                bankInfo.setBic(konto.bic);
                bankInfo.setBlz(konto.blz);
                bankInfoRepository.save(bankInfo);
            }
        }

        de.jgh.finance.book.financebook.persistence.entity.Konto savedKonto = kontoRepository.findByIban(konto.iban);
        if (savedKonto == null) {
            savedKonto = new de.jgh.finance.book.financebook.persistence.entity.Konto();
            savedKonto.setIban(konto.iban);
            savedKonto.setAcctype(konto.acctype);
            savedKonto.setBic(konto.bic);
            savedKonto.setCustomerid(konto.customerid);
            savedKonto = kontoRepository.save(savedKonto);
        }

        for (UmsLine line : entry.getValue()) {
            processBuchung(line, savedKonto);
        }
    }

    private void processBuchung(UmsLine umsLine, de.jgh.finance.book.financebook.persistence.entity.Konto konto) {
        Buchung buchung = buchungMapper.toBuchung(umsLine);
        buchung.setKonto(konto);
        if (!buchungExistsAlready(buchung)) {
            buchungRepository.save(buchung);
        }
    }

    private boolean buchungExistsAlready(Buchung buchung) {
        Buchung savedBuchung = buchungRepository.findBuchungByValutaAndBdateAndValueAndSaldoAndIbanAndBic(buchung.getValuta(), buchung.getBdate(), buchung.getValue(), buchung.getSaldo(), buchung.getIban(), buchung.getBic());
        return savedBuchung != null;
    }

}
