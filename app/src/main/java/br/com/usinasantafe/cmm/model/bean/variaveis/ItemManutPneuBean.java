package br.com.usinasantafe.cmm.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.cmm.model.pst.Entidade;

@DatabaseTable(tableName="tbitemmanutpneuvar")
public class ItemManutPneuBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idItemManutPneu;
    @DatabaseField
    private Long idBolItemManutPneu;
    @DatabaseField
    private Long idPosConfItemManutPneu;
    @DatabaseField
    private String nroPneuRetItemManutPneu;
    @DatabaseField
    private String nroPneuColItemManutPneu;
    @DatabaseField
    private String dthrItemManutPneu;

    public ItemManutPneuBean() {
    }

    public Long getIdItemManutPneu() {
        return idItemManutPneu;
    }

    public void setIdItemManutPneu(Long idItemManutPneu) {
        this.idItemManutPneu = idItemManutPneu;
    }

    public Long getIdBolItemManutPneu() {
        return idBolItemManutPneu;
    }

    public void setIdBolItemManutPneu(Long idBolItemManutPneu) {
        this.idBolItemManutPneu = idBolItemManutPneu;
    }

    public Long getIdPosConfItemManutPneu() {
        return idPosConfItemManutPneu;
    }

    public void setIdPosConfItemManutPneu(Long idPosConfItemManutPneu) {
        this.idPosConfItemManutPneu = idPosConfItemManutPneu;
    }

    public String getNroPneuRetItemManutPneu() {
        return nroPneuRetItemManutPneu;
    }

    public void setNroPneuRetItemManutPneu(String nroPneuRetItemManutPneu) {
        this.nroPneuRetItemManutPneu = nroPneuRetItemManutPneu;
    }

    public String getNroPneuColItemManutPneu() {
        return nroPneuColItemManutPneu;
    }

    public void setNroPneuColItemManutPneu(String nroPneuColItemManutPneu) {
        this.nroPneuColItemManutPneu = nroPneuColItemManutPneu;
    }

    public String getDthrItemManutPneu() {
        return dthrItemManutPneu;
    }

    public void setDthrItemManutPneu(String dthrItemManutPneu) {
        this.dthrItemManutPneu = dthrItemManutPneu;
    }
}
