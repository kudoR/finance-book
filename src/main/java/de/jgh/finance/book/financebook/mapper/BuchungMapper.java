package de.jgh.finance.book.financebook.mapper;

import de.jgh.finance.book.financebook.persistence.entity.Buchung;
import org.kapott.hbci.GV_Result.GVRKUms.UmsLine;
import org.mapstruct.Mapper;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class BuchungMapper {

    public Buchung toBuchung(UmsLine umsLine) {

        if (umsLine == null) {

            return null;
        }

        Buchung buchung = new Buchung();

        if (umsLine.valuta != null) {
            buchung.setValuta(umsLine.valuta.getTime());
        }

        if (umsLine.bdate != null) {
            buchung.setBdate(umsLine.bdate.getTime());
        }

        if (umsLine.value != null && umsLine.value.getBigDecimalValue() != null) {
            buchung.setValue(umsLine.value.getBigDecimalValue());
        }

        buchung.setStorno(umsLine.isStorno);

        if (umsLine.saldo != null && umsLine.saldo.value != null && umsLine.saldo.value.getBigDecimalValue() != null) {
            buchung.setSaldo(umsLine.saldo.value.getBigDecimalValue());
        }

        buchung.setCustomerref(umsLine.customerref);

        buchung.setInstref(umsLine.instref);

        buchung.setText(umsLine.text);

        buchung.setUsageList(new ArrayList<>(umsLine.usage));

        if (umsLine.other != null && umsLine.other.name != null) {
            buchung.setName(umsLine.other.name);
        }

        if (umsLine.other != null && umsLine.other.name2 != null) {
            buchung.setName2(umsLine.other.name2);
        }

        if (umsLine.other != null && umsLine.other.iban != null) {
            buchung.setName2(umsLine.other.iban);
        }

        if (umsLine.other != null && umsLine.other.bic != null) {
            buchung.setName2(umsLine.other.bic);
        }

        buchung.setSepa(umsLine.isSepa);

        return buchung;
    }

}