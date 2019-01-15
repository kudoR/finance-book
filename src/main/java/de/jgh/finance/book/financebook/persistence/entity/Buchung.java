package de.jgh.finance.book.financebook.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Buchung implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long valuta;
    private long bdate;
    private BigDecimal value;
    private boolean isStorno;
    private BigDecimal saldo;
    private String customerref;
    private String instref;
    private String text;
    @ElementCollection
    private List<String> usage;
    private String name;
    private String name2;
    private String iban;
    private String bic;
    private boolean isSepa;

    @ManyToOne(fetch = FetchType.LAZY)
    private Konto konto;

    public long getValuta() {
        return valuta;
    }

    public void setValuta(long valuta) {
        this.valuta = valuta;
    }

    public long getBdate() {
        return bdate;
    }

    public void setBdate(long bdate) {
        this.bdate = bdate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isStorno() {
        return isStorno;
    }

    public void setStorno(boolean storno) {
        isStorno = storno;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getCustomerref() {
        return customerref;
    }

    public void setCustomerref(String customerref) {
        this.customerref = customerref;
    }

    public String getInstref() {
        return instref;
    }

    public void setInstref(String instref) {
        this.instref = instref;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getUsage() {
        return usage;
    }

    public void setUsage(List<String> usage) {
        this.usage = usage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public boolean isSepa() {
        return isSepa;
    }

    public void setSepa(boolean sepa) {
        isSepa = sepa;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buchung buchung = (Buchung) o;
        return id == buchung.id &&
                valuta == buchung.valuta &&
                bdate == buchung.bdate &&
                isStorno == buchung.isStorno &&
                isSepa == buchung.isSepa &&
                Objects.equals(value, buchung.value) &&
                Objects.equals(saldo, buchung.saldo) &&
                Objects.equals(customerref, buchung.customerref) &&
                Objects.equals(instref, buchung.instref) &&
                Objects.equals(text, buchung.text) &&
                Objects.equals(usage, buchung.usage) &&
                Objects.equals(name, buchung.name) &&
                Objects.equals(name2, buchung.name2) &&
                Objects.equals(iban, buchung.iban) &&
                Objects.equals(bic, buchung.bic) &&
                Objects.equals(konto, buchung.konto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, valuta, bdate, value, isStorno, saldo, customerref, instref, text, usage, name, name2, iban, bic, isSepa, konto);
    }
}
