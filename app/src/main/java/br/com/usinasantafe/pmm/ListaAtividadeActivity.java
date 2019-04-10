package br.com.usinasantafe.pmm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.usinasantafe.pmm.bo.ConexaoWeb;
import br.com.usinasantafe.pmm.bo.ManipDadosEnvio;
import br.com.usinasantafe.pmm.bo.ManipDadosVerif;
import br.com.usinasantafe.pmm.bo.Tempo;
import br.com.usinasantafe.pmm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pmm.to.tb.estaticas.AtividadeTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.REquipAtivTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.ROSAtivTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.BackupApontaMMTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.BoletimMMTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.ConfiguracaoTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.RendimentoTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.TransbordoTO;

public class ListaAtividadeActivity extends ActivityGeneric {

    private ListView lista;
    private PMMContext pmmContext;
    private List listAtiv;
    private ProgressDialog progressBar;
    private ArrayList lAtivExib;
    private Long nroOS = 0L;
    private ConfiguracaoTO configTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_atividade);

        pmmContext = (PMMContext) getApplication();

        Button buttonAtualAtividade = (Button) findViewById(R.id.buttonAtualAtividade);
        Button buttonRetAtividade = (Button) findViewById(R.id.buttonRetAtividade);
        TextView textViewTituloAtividade = (TextView) findViewById(R.id.textViewTituloAtividade);

        configTO = new ConfiguracaoTO();
        List configList = configTO.all();
        configTO = (ConfiguracaoTO) configList.get(0);

        nroOS = configTO.getOsConfig();

        buttonAtualAtividade.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if (conexaoWeb.verificaConexao(ListaAtividadeActivity.this)) {

                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Atualizando Atividades...");
                    progressBar.show();

                    ManipDadosVerif.getInstance().verDados(nroOS + "_" + configTO.getEquipConfig(), "Atividade"
                            , ListaAtividadeActivity.this, ListaAtividadeActivity.class, progressBar);

                } else {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaAtividadeActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.show();

                }

            }
        });

        buttonRetAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaAtividadeActivity.this, OSActivity.class);
                startActivity(it);
            }
        });

        if (pmmContext.getVerPosTela() == 1) {
            textViewTituloAtividade.setText("ATIVIDADE PRINCIPAL");
        }
        else {
            textViewTituloAtividade.setText("ATIVIDADE");
        }

        AtividadeTO atividadeTO = new AtividadeTO();
        ArrayList<String> itens = new ArrayList<String>();

        if ((pmmContext.getVerPosTela() == 1) || (pmmContext.getVerPosTela() == 2) || (pmmContext.getVerPosTela() == 3)
                || (pmmContext.getVerPosTela() == 8) || (pmmContext.getVerPosTela() == 12)) {

            REquipAtivTO rEquipAtivTO = new REquipAtivTO();
            List lrea = rEquipAtivTO.get("codEquip", configTO.getEquipConfig());

            configList.clear();

            ArrayList<Long> rLista = new ArrayList<Long>();

            for (int i = 0; i < lrea.size(); i++) {
                rEquipAtivTO = (REquipAtivTO) lrea.get(i);
                rLista.add(rEquipAtivTO.getCodAtiv());
            }

            listAtiv = atividadeTO.in("codAtiv", rLista);

        }
        else if ((pmmContext.getVerPosTela() == 9)|| (pmmContext.getVerPosTela() == 13)
                || (pmmContext.getVerPosTela() == 15)|| (pmmContext.getVerPosTela() == 16)) {
            listAtiv = atividadeTO.get("flagCarretel", 1L);
        }

        lAtivExib = new ArrayList();

        ROSAtivTO rOSAtivTO = new ROSAtivTO();
        List lroa = rOSAtivTO.get("nroOS", nroOS);

        if(lroa.size() > 0){

            for (int i = 0; i < listAtiv.size(); i++) {
                atividadeTO = (AtividadeTO) listAtiv.get(i);
                for (int j = 0; j < lroa.size(); j++) {
                    rOSAtivTO = (ROSAtivTO) lroa.get(j);
                    if (Objects.equals(atividadeTO.getCodAtiv(), rOSAtivTO.getCodAtiv())) {
                        lAtivExib.add(atividadeTO);
                    }
                }
            }

        } else {

            for (int i = 0; i < listAtiv.size(); i++) {
                atividadeTO = (AtividadeTO) listAtiv.get(i);
                lAtivExib.add(atividadeTO);
            }

        }

        for (int i = 0; i < lAtivExib.size(); i++) {
            atividadeTO = (AtividadeTO) lAtivExib.get(i);
            itens.add(atividadeTO.getCodAtiv() + " - " + atividadeTO.getDescrAtiv());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        lista = (ListView) findViewById(R.id.listAtividade);
        lista.setAdapter(adapterList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                AtividadeTO atividadeTO = new AtividadeTO();
                atividadeTO = (AtividadeTO) lAtivExib.get(position);

                if (pmmContext.getVerPosTela() == 1) {

                    pmmContext.getBoletimMMTO().setAtivPrincBoletim(atividadeTO.getIdAtiv());
                    pmmContext.getBoletimMMTO().setStatusConBoletim(configTO.getStatusConConfig());
                    pmmContext.setTextoHorimetro("HORÍMETRO INICIAL:");
                    Intent it = new Intent(ListaAtividadeActivity.this, HorimetroActivity.class);
                    startActivity(it);
                    finish();

                    listAtiv.clear();
                    lAtivExib.clear();

                } else if((pmmContext.getVerPosTela() == 2) || (pmmContext.getVerPosTela() == 8))  {

                    pmmContext.getApontaMMTO().setAtividadeAponta(atividadeTO.getIdAtiv());
                    pmmContext.getApontaMMTO().setStatusConAponta(configTO.getStatusConConfig());
                    pmmContext.getApontaMMTO().setParadaAponta(0L);

                    if(verifBackup()){

                        AlertDialog.Builder alerta = new AlertDialog.Builder(ListaAtividadeActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("OPERAÇÃO JÁ APONTADA PARA O EQUIPAMENTO!");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });

                        alerta.show();

                    }
                    else {

                        if(atividadeTO.getFlagTransbordo() == 0) {

                            TransbordoTO transbordoTO = new TransbordoTO();
                            if (transbordoTO.hasElements()) {
                                List transbList = transbordoTO.all();
                                transbordoTO = (TransbordoTO) transbList.get(0);
                                pmmContext.getApontaMMTO().setTransbordoAponta(transbordoTO.getCodEquipTransbordo());
                            } else {
                                pmmContext.getApontaMMTO().setTransbordoAponta(0L);
                            }

                            pmmContext.getApontaMMTO().setLatitudeAponta(getLatitude());
                            pmmContext.getApontaMMTO().setLongitudeAponta(getLongitude());
                            ManipDadosEnvio.getInstance().salvaApontaMM(pmmContext.getApontaMMTO());

                            if(atividadeTO.getFlagRendimento() == 1) {

                                BoletimMMTO boletimMMTO = new BoletimMMTO();
                                List listBoletim = boletimMMTO.get("statusBoletim", 1L);

                                if (listBoletim.size() > 0) {

                                    boletimMMTO = (BoletimMMTO) listBoletim.get(0);

                                    RendimentoTO rendimentoTO = new RendimentoTO();
                                    ArrayList listaPesq = new ArrayList();

                                    EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                                    pesquisa.setCampo("idBolRendimento");
                                    pesquisa.setValor(boletimMMTO.getIdBoletim());
                                    listaPesq.add(pesquisa);

                                    EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                                    pesquisa2.setCampo("nroOSRendimento");
                                    pesquisa2.setValor(pmmContext.getApontaMMTO().getOsAponta());
                                    listaPesq.add(pesquisa2);

                                    List rendList = rendimentoTO.get(listaPesq);

                                    if (rendList.size() == 0) {
                                        rendimentoTO.setIdBolRendimento(boletimMMTO.getIdBoletim());
                                        rendimentoTO.setIdExtBolRendimento(boletimMMTO.getIdExtBoletim());
                                        rendimentoTO.setNroOSRendimento(pmmContext.getApontaMMTO().getOsAponta());
                                        rendimentoTO.setValorRendimento(0D);
                                        rendimentoTO.insert();
                                        rendimentoTO.commit();
                                    }

                                }

                            }

                            if(pmmContext.getVerPosTela() == 2){
                                configTO.setDtUltApontConfig(Tempo.getInstance().data());
                                configTO.update();
                                Intent it = new Intent(ListaAtividadeActivity.this, MenuPrincNormalActivity.class);
                                startActivity(it);
                                finish();

                                listAtiv.clear();
                                lAtivExib.clear();

                            }
                            else if(pmmContext.getVerPosTela() == 8) {
                                Intent it = new Intent(ListaAtividadeActivity.this, ListaEquipFertActivity.class);
                                startActivity(it);
                                finish();

                                listAtiv.clear();
                                lAtivExib.clear();

                            }

                        }else{

                            Intent it = new Intent(ListaAtividadeActivity.this, TransbordoActivity.class);
                            startActivity(it);
                            finish();

                            listAtiv.clear();
                            lAtivExib.clear();

                        }

                    }

                } else if ((pmmContext.getVerPosTela() == 3) || (pmmContext.getVerPosTela() == 12)) {

                    pmmContext.getApontaMMTO().setAtividadeAponta(atividadeTO.getIdAtiv());
                    pmmContext.getApontaMMTO().setStatusConAponta(configTO.getStatusConConfig());
                    Intent it = new Intent(ListaAtividadeActivity.this, ListaParadaActivity.class);
                    startActivity(it);
                    finish();

                    listAtiv.clear();
                    lAtivExib.clear();

                } else if ((pmmContext.getVerPosTela() == 9)|| (pmmContext.getVerPosTela() == 15)) {

                    pmmContext.getApontaAplicFertTO().setAtivApontaAplicFert(atividadeTO.getIdAtiv());
                    pmmContext.getApontaAplicFertTO().setParadaApontaAplicFert(0L);

                    Intent it = new Intent(ListaAtividadeActivity.this, BocalFertActivity.class);
                    startActivity(it);
                    finish();

                    listAtiv.clear();
                    lAtivExib.clear();

                }

                else if ((pmmContext.getVerPosTela() == 13)|| (pmmContext.getVerPosTela() == 16)) {

                    pmmContext.getApontaAplicFertTO().setAtivApontaAplicFert(atividadeTO.getIdAtiv());
                    Intent it = new Intent(ListaAtividadeActivity.this, ListaParadaActivity.class);
                    startActivity(it);
                    finish();

                    listAtiv.clear();
                    lAtivExib.clear();

                }

            }

        });


    }

    public boolean verifBackup(){

        boolean v = false;

        BackupApontaMMTO backupApontaMMTO = new BackupApontaMMTO();
        List bkpApontaList = backupApontaMMTO.all();

        if(bkpApontaList.size() > 0) {

            backupApontaMMTO = (BackupApontaMMTO) bkpApontaList.get(bkpApontaList.size() - 1);
            if((pmmContext.getApontaMMTO().getOsAponta().equals(backupApontaMMTO.getOsAponta()))
                    && (pmmContext.getApontaMMTO().getAtividadeAponta().equals(backupApontaMMTO.getAtividadeAponta()))
                    && (pmmContext.getApontaMMTO().getParadaAponta().equals(backupApontaMMTO.getParadaAponta()))){

                v = true;

            }

        }

        return v;

    }

    public void onBackPressed()  {
    }

}
