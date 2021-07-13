package br.com.usinasantafe.pmm.control;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.usinasantafe.pmm.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pmm.model.bean.variaveis.CECBean;
import br.com.usinasantafe.pmm.model.bean.variaveis.PreCECBean;
import br.com.usinasantafe.pmm.model.dao.CECDAO;
import br.com.usinasantafe.pmm.model.dao.CarretaDAO;
import br.com.usinasantafe.pmm.model.dao.EquipDAO;
import br.com.usinasantafe.pmm.model.dao.LogErroDAO;
import br.com.usinasantafe.pmm.model.dao.OSDAO;
import br.com.usinasantafe.pmm.model.dao.PreCECDAO;
import br.com.usinasantafe.pmm.util.EnvioDadosServ;
import br.com.usinasantafe.pmm.util.Json;
import br.com.usinasantafe.pmm.util.VerifDadosServ;

public class CECCTR {

    public CECCTR() {
    }

    ///////////////////////////////////// CABECALHO //////////////////////////////////////////////

    public void salvarPrecCECAberto(){
        PreCECDAO preCECDAO = new PreCECDAO();
        preCECDAO.abrirPreCEC();
    }

    public void clearPreCECAberto(){
        PreCECDAO preCECDAO = new PreCECDAO();
        preCECDAO.clearPreCECAberto();
    }

    public void fechaPreCEC(){
        MotoMecFertCTR motoMecFertCTR = new MotoMecFertCTR();
        ConfigCTR configCTR = new ConfigCTR();
        PreCECDAO preCECDAO = new PreCECDAO();
        preCECDAO.fechaPreCEC(motoMecFertCTR.getBoletimMMFertAberto().getMatricFuncBolMMFert()
                , motoMecFertCTR.getTurnoId(motoMecFertCTR.getBoletimMMFertAberto().getIdTurnoBolMMFert()).getCodTurno()
                , configCTR.getEquip().getNroEquip());
    }

    public String dadosEnvioPreCEC(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.dadosEnvioPreCEC();
    }

    public void updPreCEC(String result){

        try{

            int pos1 = result.indexOf("_") + 1;
            String objPrinc = result.substring(pos1);

            PreCECDAO preCECDAO = new PreCECDAO();
            preCECDAO.atualPreCEC(objPrinc);

            EnvioDadosServ.getInstance().envioDados(2);

        }
        catch (Exception e){
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insert(e);
        }

    }

    public void receberVerifCEC(String result){

        try{

            if (!result.contains("exceeded")) {

                Log.i("PMM", "CHEGOU AKI 1 RECDADOS");

                int pos1 = result.indexOf("_") + 1;
                String precec = result.substring(0, (pos1 - 1));
                String cec = result.substring(pos1);

                PreCECDAO preCECDAO = new PreCECDAO();
                preCECDAO.atualPreCEC(precec);

                CECDAO cecDAO = new CECDAO();
                cecDAO.recDadosCEC(cec);

                ConfigCTR configCTR = new ConfigCTR();
                configCTR.setStatusRetVerif(0L);

                VerifDadosServ.getInstance().pulaTela();

            } else {
                VerifDadosServ.status = 1;
            }

        }
        catch(Exception e){
            Log.i("ECM", "ERRO REC = " + e);
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insert(e);
        }

    }

    public void delPreCECAberto(){
        PreCECDAO preCECDAO = new PreCECDAO();
        preCECDAO.delPreCECAberto();
    }

    public void delCEC(){
        CECDAO cecDAO = new CECDAO();
        cecDAO.delCEC();
    }

    /////////////////////////////VERIFICAR DADOS////////////////////////////////

    public boolean verPreCECAberto(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.verPreCECAberto();
    }

    public boolean verPreCECFechado(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.verPreCECFechado();
    }

    public boolean verDataPreCEC(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.verDataPreCEC();
    }

    public boolean verAtivOS(Long idAtivOS){
        OSDAO osDAO = new OSDAO();
        ConfigCTR configCTR = new ConfigCTR();
        return osDAO.verAtivOS(idAtivOS, configCTR.getConfig().getOsConfig());
    }

    public boolean hasElemCEC(){
        CECDAO cecDAO = new CECDAO();
        return cecDAO.verCEC();
    }

    public void verCEC(Context telaAtual, Class telaProx, ProgressDialog progressDialog){

        ConfigCTR configCTR = new ConfigCTR();
        configCTR.setStatusRetVerif(1L);

        CECDAO cecDAO = new CECDAO();
        EquipDAO equipDAO = new EquipDAO();
        PreCECDAO preCECDAO = new PreCECDAO();

        String dados = equipDAO.dadosEnvioEquip() + "_" + preCECDAO.dadosEnvioPreCEC();
        cecDAO.verCEC(dados, telaAtual, telaProx, progressDialog);
    }

    ////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////SET DADOS////////////////////////////////

    public void setDataChegCampo(){
        PreCECDAO preCECDAO = new PreCECDAO();
        preCECDAO.setDataChegCampo();
    }

    public void setAtivOS(Long ativOS){
        PreCECDAO preCECDAO = new PreCECDAO();
        preCECDAO.setAtivOS(ativOS);
    }

    public void setLib(Long libCam){
        PreCECDAO preCECDAO = new PreCECDAO();
        CarretaDAO carretaDAO = new CarretaDAO();
        preCECDAO.setLib(libCam, carretaDAO.getQtdeCarreta());
    }

    public void setCarr(Long carr){
        PreCECDAO preCECDAO = new PreCECDAO();
        CarretaDAO carretaDAO = new CarretaDAO();
        preCECDAO.setCarr(carr, carretaDAO.getQtdeCarreta());
    }

    /////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////GET DADOS/////////////////////////////////

    public String getDataChegCampo(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.getDataChegCampo();
    }

    public OSBean getOSTipoAtiv(){
        OSDAO osDAO = new OSDAO();
        ConfigCTR configCTR = new ConfigCTR();
        Long tipo;
        if(configCTR.getEquip().getClassifEquip() == 1){
            tipo = 557L;
        }
        else if(configCTR.getEquip().getClassifEquip() == 2){
            tipo = 558L;
        }
        else{
            tipo = 559L;
        }
        return osDAO.getOSTipoAtiv(tipo, configCTR.getConfig().getOsConfig());
    }

    public boolean hasPreCEC(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.hasPreCEC();
    }

    public boolean verPreCECTerminadoList(){
        PreCECDAO preCECDAO = new PreCECDAO();
        List<PreCECBean> preCECTerminadoList = preCECDAO.preCECListTerminado();
        boolean verTerminado = preCECTerminadoList.size() > 0;
        preCECTerminadoList.clear();
        return verTerminado;
    }

    public List<PreCECBean> preCECTerminadoList(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.preCECListTerminado();
    }

    public PreCECBean getPreCECAberto(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.getPreCECAberto();
    }

    public CECBean getCEC(){
        CECDAO cecDAO = new CECDAO();
        return cecDAO.getCEC();
    }

    public List<CECBean> cecListDesc(){
        CECDAO cecDAO = new CECDAO();
        return cecDAO.cecListDesc();
    }

    public String getDataSaidaUlt(){
        PreCECDAO preCECDAO = new PreCECDAO();
        return preCECDAO.getDataSaidaUlt();
    }

    /////////////////////////////////////////////////////////////////////////////

}
