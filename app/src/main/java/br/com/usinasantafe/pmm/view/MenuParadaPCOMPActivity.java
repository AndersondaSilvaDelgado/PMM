package br.com.usinasantafe.pmm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pmm.PMMContext;
import br.com.usinasantafe.pmm.R;
import br.com.usinasantafe.pmm.model.bean.estaticas.MotoMecBean;
import br.com.usinasantafe.pmm.util.ConexaoWeb;
import br.com.usinasantafe.pmm.util.Tempo;

public class MenuParadaPCOMPActivity extends ActivityGeneric {

    private ListView paradaListView;
    private PMMContext pmmContext;
    private List<MotoMecBean> paradaList;
    private int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_parada_pcomp);

        pmmContext = (PMMContext) getApplication();

        Button buttonRetMenuParada = findViewById(R.id.buttonRetMenuParada);

        ArrayList<String> itens = new ArrayList<String>();
        paradaList = pmmContext.getMotoMecFertCTR().paradaList();
        for(MotoMecBean motoMecBean : paradaList){
            itens.add(motoMecBean.getDescrOperMotoMec());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        paradaListView = findViewById(R.id.listViewMotParada);
        paradaListView.setAdapter(adapterList);

        paradaListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                posicao = position;
                MotoMecBean motoMecBean = paradaList.get(posicao);
                pmmContext.getMotoMecFertCTR().setMotoMecBean(motoMecBean);

                if (pmmContext.getConfigCTR().getConfig().getDtUltApontConfig().equals(Tempo.getInstance().dataComHora())) {
                    Toast.makeText(MenuParadaPCOMPActivity.this, "POR FAVOR! ESPERE 1 MINUTO PARA REALIZAR UM NOVO APONTAMENTO.",
                            Toast.LENGTH_LONG).show();
                }
                else {

                    if (pmmContext.getMotoMecFertCTR().verifBackupApont()) {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuParadaPCOMPActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("OPERAÇÃO JÁ APONTADA PARA O EQUIPAMENTO!");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        alerta.show();

                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuParadaPCOMPActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("FOI DADO ENTRADA NA ATIVIDADE: " + motoMecBean.getDescrOperMotoMec());

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ConexaoWeb conexaoWeb = new ConexaoWeb();
                                if (conexaoWeb.verificaConexao(MenuParadaPCOMPActivity.this)) {
                                    pmmContext.getConfigCTR().setStatusConConfig(1L);
                                } else {
                                    pmmContext.getConfigCTR().setStatusConConfig(0L);
                                }
                                pmmContext.getMotoMecFertCTR().salvarApont(getLongitude(), getLatitude());
                                paradaListView.setSelection(posicao + 1);
                            }
                        });

                        alerta.show();

                    }

                }

            }

        });

        buttonRetMenuParada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ConexaoWeb conexaoWeb = new ConexaoWeb();
                if (conexaoWeb.verificaConexao(MenuParadaPCOMPActivity.this)) {
                    pmmContext.getConfigCTR().setStatusConConfig(1L);
                }
                else{
                    pmmContext.getConfigCTR().setStatusConConfig(0L);
                }
                pmmContext.getMotoMecFertCTR().inserirVoltaTrabalho(getLongitude(), getLatitude());
                Intent it = new Intent(MenuParadaPCOMPActivity.this, MenuPrincPCOMPActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}