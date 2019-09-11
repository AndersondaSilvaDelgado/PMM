package br.com.usinasantafe.pmm.to.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pmm.pst.Entidade;

/**
 * Created by anderson on 13/04/2018.
 */
@DatabaseTable(tableName="tbrecolhfertvar")
public class RecolhFertTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idRecolhFert;
    @DatabaseField
    private Long idBolRecolhFert;
    @DatabaseField
    private Long idExtBolRecolhFert;
    @DatabaseField
    private Long nroOSRecolhFert;
    @DatabaseField
    private Long valorRecolhFert;
    @DatabaseField
    private String dthrRecolhFert;
    @DatabaseField
    private Long statusRecolhFert; //1 - Aberto; 2 - Encerrado

    public RecolhFertTO() {
    }

    public Long getIdRecolhFert() {
        return idRecolhFert;
    }

    public void setIdRecolhFert(Long idRecolhFert) {
        this.idRecolhFert = idRecolhFert;
    }

    public Long getIdBolRecolhFert() {
        return idBolRecolhFert;
    }

    public void setIdBolRecolhFert(Long idBolRecolhFert) {
        this.idBolRecolhFert = idBolRecolhFert;
    }

    public Long getIdExtBolRecolhFert() {
        return idExtBolRecolhFert;
    }

    public void setIdExtBolRecolhFert(Long idExtBolRecolhFert) {
        this.idExtBolRecolhFert = idExtBolRecolhFert;
    }

    public Long getNroOSRecolhFert() {
        return nroOSRecolhFert;
    }

    public void setNroOSRecolhFert(Long nroOSRecolhFert) {
        this.nroOSRecolhFert = nroOSRecolhFert;
    }

    public Long getValorRecolhFert() {
        return valorRecolhFert;
    }

    public void setValorRecolhFert(Long valorRecolhFert) {
        this.valorRecolhFert = valorRecolhFert;
    }

    public String getDthrRecolhFert() {
        return dthrRecolhFert;
    }

    public void setDthrRecolhFert(String dthrRecolhFert) {
        this.dthrRecolhFert = dthrRecolhFert;
    }

    public Long getStatusRecolhFert() {
        return statusRecolhFert;
    }

    public void setStatusRecolhFert(Long statusRecolhFert) {
        this.statusRecolhFert = statusRecolhFert;
    }
}
