package br.com.usinasantafe.pmm.to.tb.variaveis;

import br.com.usinasantafe.pmm.pst.Entidade;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tbconfigvar")
public class ConfigTO extends Entidade {
	
	private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idConfig;
	@DatabaseField
	private Long equipConfig;
	@DatabaseField
	private Long classeEquipConfig;
    @DatabaseField
	private String senhaConfig;
	@DatabaseField
	private Long ultTurnoCLConfig;
    @DatabaseField
    private String dtUltCLConfig;
	@DatabaseField
	private String dtUltApontConfig;
	@DatabaseField
	private String dtServConfig;
	@DatabaseField
	private Long difDthrConfig;
	@DatabaseField
	private Long osConfig;
	@DatabaseField
	private Double horimetroConfig;
	@DatabaseField
	private Long visDadosConfig; //0 - Verificar Dados; 1- Dados Recebidos; 2 - Dados Visualizados
	@DatabaseField
	private Long statusConConfig;  //0 - Offline; 1 - Online

	public ConfigTO() {
	}

	public Long getEquipConfig() {
		return equipConfig;
	}

	public void setEquipConfig(Long camconfig) {
		this.equipConfig = camconfig;
	}

	public String getSenhaConfig() {
		return senhaConfig;
	}

	public void setSenhaConfig(String senhaconfig) {
		this.senhaConfig = senhaconfig;
	}

	public Long getClasseEquipConfig() {
		return classeEquipConfig;
	}

	public void setClasseEquipConfig(Long classeEquipConfig) {
		this.classeEquipConfig = classeEquipConfig;
	}

	public Long getUltTurnoCLConfig() {
		return ultTurnoCLConfig;
	}

	public void setUltTurnoCLConfig(Long ultTurnoCLConfig) {
		this.ultTurnoCLConfig = ultTurnoCLConfig;
	}

	public String getDtUltApontConfig() {
		return dtUltApontConfig;
	}

	public void setDtUltApontConfig(String dtUltApontConfig) {
		this.dtUltApontConfig = dtUltApontConfig;
	}

	public Long getOsConfig() {
		return osConfig;
	}

	public void setOsConfig(Long osConfig) {
		this.osConfig = osConfig;
	}

	public Double getHorimetroConfig() {
		return horimetroConfig;
	}

	public void setHorimetroConfig(Double horimetroConfig) {
		this.horimetroConfig = horimetroConfig;
	}

    public String getDtUltCLConfig() {
        return dtUltCLConfig;
    }

    public void setDtUltCLConfig(String dtUltCLConfig) {
        this.dtUltCLConfig = dtUltCLConfig;
    }

	public Long getVisDadosConfig() {
		return visDadosConfig;
	}

	public void setVisDadosConfig(Long visDadosConfig) {
		this.visDadosConfig = visDadosConfig;
	}

	public Long getStatusConConfig() {
		return statusConConfig;
	}

	public void setStatusConConfig(Long statusConConfig) {
		this.statusConConfig = statusConConfig;
	}

	public String getDtServConfig() {
		return dtServConfig;
	}

	public void setDtServConfig(String dtServConfig) {
		this.dtServConfig = dtServConfig;
	}

	public Long getDifDthrConfig() {
		return difDthrConfig;
	}

	public void setDifDthrConfig(Long difDthrConfig) {
		this.difDthrConfig = difDthrConfig;
	}
}