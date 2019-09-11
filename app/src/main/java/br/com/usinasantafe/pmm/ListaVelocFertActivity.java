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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import br.com.usinasantafe.pmm.bo.ConexaoWeb;
import br.com.usinasantafe.pmm.bo.ManipDadosEnvio;
import br.com.usinasantafe.pmm.bo.ManipDadosVerif;
import br.com.usinasantafe.pmm.bo.Tempo;
import br.com.usinasantafe.pmm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pmm.to.estaticas.PressaoBocalTO;
import br.com.usinasantafe.pmm.to.variaveis.BoletimFertTO;
import br.com.usinasantafe.pmm.to.variaveis.ConfigTO;
import br.com.usinasantafe.pmm.to.variaveis.RecolhFertTO;

public class ListaVelocFertActivity extends ActivityGeneric {

    private PMMContext pmmContext;
    private ListView velocListView;
    private List velocList;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_veloc_fert);

        pmmContext = (PMMContext) getApplication();

        Button buttonRetVelocidade = (Button) findViewById(R.id.buttonRetVelocidade);
        Button buttonAtualVelocidade = (Button) findViewById(R.id.buttonAtualVelocidade);

        buttonAtualVelocidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(  ListaVelocFertActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(ListaVelocFertActivity.this)) {

                            progressBar = new ProgressDialog(ListaVelocFertActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("ATUALIZANDO VELOCIDADE...");
                            progressBar.show();

                            ManipDadosVerif.getInstance().verDados("", "PressaoBocal"
                                    , ListaVelocFertActivity.this, ListaVelocFertActivity.class, progressBar);

                        } else {

                            AlertDialog.Builder alerta = new AlertDialog.Builder( ListaVelocFertActivity.this);
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

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alerta.show();

            }

        });


        PressaoBocalTO pressaoBocalTO = new PressaoBocalTO();

        ArrayList listaPesq = new ArrayList();
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idBocal");
        pesquisa.setValor(pmmContext.getApontFertTO().getBocalApontFert());
        pesquisa.setTipo(1);
        listaPesq.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("valorPressao");
        pesquisa2.setValor(pmmContext.getApontFertTO().getPressaoApontFert());
        pesquisa2.setTipo(1);
        listaPesq.add(pesquisa2);

        velocList = pressaoBocalTO.getAndOrderBy(listaPesq, "valorVeloc", true);

        ArrayList<String> itens = new ArrayList<String>();

        for(int i = 0; i < velocList.size(); i++){
            pressaoBocalTO = (PressaoBocalTO) velocList.get(i);
            itens.add("" + pressaoBocalTO.getValorVeloc());
        }

        HashSet<String> hashSet = new HashSet<String>(itens);
        itens.clear();
        itens.addAll(hashSet);
        Collections.sort(itens);

        AdapterList adapterList = new AdapterList(this, itens);
        velocListView = (ListView) findViewById(R.id.listVelocidade);
        velocListView.setAdapter(adapterList);

        velocListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
                pmmContext.getApontFertTO().setVelocApontFert(Long.parseLong(textView.getText().toString()));
                velocList.clear();

                BoletimFertTO boletimFertTO = new BoletimFertTO();
                List boletimList = boletimFertTO.get("statusBolFert", 1L);

                if (boletimList.size() > 0) {

                    boletimFertTO = (BoletimFertTO) boletimList.get(0);

                    RecolhFertTO recolhFertTO = new RecolhFertTO();
                    ArrayList pesqList = new ArrayList();

                    EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                    pesquisa.setCampo("idBolRecolhFert");
                    pesquisa.setValor(boletimFertTO.getIdBolFert());
                    pesquisa.setTipo(1);
                    pesqList.add(pesquisa);

                    EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                    pesquisa2.setCampo("nroOSRecolhFert");
                    pesquisa2.setValor(pmmContext.getApontFertTO().getOsApontFert());
                    pesquisa2.setTipo(1);
                    pesqList.add(pesquisa2);

                    List rendList = recolhFertTO.get(pesqList);

                    if (rendList.size() == 0) {
                        recolhFertTO.setIdBolRecolhFert(boletimFertTO.getIdBolFert());
                        recolhFertTO.setIdExtBolRecolhFert(boletimFertTO.getIdExtBolFert());
                        recolhFertTO.setNroOSRecolhFert(pmmContext.getApontFertTO().getOsApontFert());
                        recolhFertTO.setValorRecolhFert(0L);
                        recolhFertTO.insert();
                        recolhFertTO.commit();
                    }

                }

                boletimList.clear();

                pmmContext.getApontFertTO().setLatitudeApontFert(0D);
                pmmContext.getApontFertTO().setLongitudeApontFert(0D);

                ConfigTO configTO = new ConfigTO();
                List configList = configTO.all();
                configTO = (ConfigTO) configList.get(0);
                configList.clear();
                configTO.setDtUltApontConfig(Tempo.getInstance().datahora());
                configTO.update();

                ManipDadosEnvio.getInstance().salvaApontaFert(pmmContext.getApontFertTO());
                Intent it = new Intent(ListaVelocFertActivity.this, MenuPrincNormalActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonRetVelocidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaVelocFertActivity.this, ListaPressaoFertActivity.class);
                startActivity(it);
            }
        });


    }

    public void onBackPressed()  {
    }

}
