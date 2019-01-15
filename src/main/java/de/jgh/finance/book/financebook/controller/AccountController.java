package de.jgh.finance.book.financebook.controller;

import de.jgh.finance.book.financebook.persistence.KontoRepository;
import de.jgh.finance.book.financebook.service.AccountFetchType;
import de.jgh.finance.book.financebook.service.FinTSService;
import org.kapott.hbci.GV_Result.GVRKUms;
import org.kapott.hbci.structures.Konto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private FinTSService finTSService;

    @Autowired
    private KontoRepository kontoRepository;

    @PostMapping
    public void addAccount(@RequestBody AddAcountBody body) {
        HashMap<Konto, List<GVRKUms.UmsLine>> kontoListHashMap = finTSService.fetchAccount(body.getBlz(), body.getUser(), body.getPin(), AccountFetchType.FULL);
        kontoListHashMap.keySet().forEach(konto -> addAccountInternal(konto, body.getUser(), body.getPin()));
    }

    private void addAccountInternal(Konto konto, String user, String pin) {
        String iban = konto.iban;
        de.jgh.finance.book.financebook.persistence.entity.Konto byIban = kontoRepository.findByIban(iban);
        if (byIban != null) {
            // konto already exists!
        } else {
            de.jgh.finance.book.financebook.persistence.entity.Konto newKonto = new de.jgh.finance.book.financebook.persistence.entity.Konto();
            newKonto.setIban(iban);
            newKonto.setBic(konto.bic);
            newKonto.setBlz(konto.blz);
            newKonto.setCustomerid(konto.customerid);
            newKonto.setAcctype(konto.acctype);
            newKonto.setUser(user);
            newKonto.setPin(pin);
            kontoRepository.save(newKonto);
        }
    }

}
