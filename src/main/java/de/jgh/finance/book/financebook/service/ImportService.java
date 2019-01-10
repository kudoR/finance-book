package de.jgh.finance.book.financebook.service;

import de.jgh.finance.book.financebook.mapper.BuchungMapper;
import de.jgh.finance.book.financebook.persistence.BuchungRepository;
import de.jgh.finance.book.financebook.persistence.KontoRepository;
import de.jgh.finance.book.financebook.persistence.entity.Buchung;
import org.kapott.hbci.structures.Konto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    @Scheduled(fixedRate = 1000000)
    public void performImports() {
        performImport("x", "x", "x");
        int size = buchungRepository.findAll().size();
        System.out.println("Import has been performed. Imported booking-lines: " + size);
    }

    private void performImport(String blz, String user, String pin) {
        HashMap<Konto, List<UmsLine>> accountsWithBookings = finTSService.fetchAccount(blz, user, pin);
        accountsWithBookings.entrySet().forEach(this::processKonto);
    }

    private void processKonto(Entry<Konto, List<UmsLine>> entry) {
        Konto konto = entry.getKey();
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
        buchungRepository.save(buchung);
    }

}
