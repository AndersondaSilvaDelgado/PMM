package br.com.usinasantafe.pmm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pmm.bo.ConexaoWeb;
import br.com.usinasantafe.pmm.bo.ManipDadosEnvio;
import br.com.usinasantafe.pmm.bo.ManipDadosVerif;
import br.com.usinasantafe.pmm.bo.Tempo;
import br.com.usinasantafe.pmm.pst.EspecificaPesquisa;
import br.com.usinasantafe.pmm.to.estaticas.EquipSegTO;
import br.com.usinasantafe.pmm.to.estaticas.EquipTO;
import br.com.usinasantafe.pmm.to.estaticas.ItemCheckListTO;
import br.com.usinasantafe.pmm.to.estaticas.TurnoTO;
import br.com.usinasantafe.pmm.to.variaveis.CabecCLTO;
import br.com.usinasantafe.pmm.to.variaveis.ConfigTO;

public class EquipMBActivity extends ActivityGeneric {

    private PMMContext pmmContext;
    private ProgressDialog progressBar;
    private ConfigTO configTO;
    private EquipTO equipTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_bomba);

        pmmContext = (PMMContext) getApplication();

        Button buttonOkMotoBomba = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancMotoBomba = (Button) findViewById(R.id.buttonCancPadrao);
        Button buttonAtualPadrao = (Button) findViewById(R.id.buttonAtualPadrao);

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(EquipMBActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(EquipMBActivity.this)) {

                            progressBar = new ProgressDialog(EquipMBActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Operador...");
                            progressBar.show();

                            ManipDadosVerif.getInstance().verDados("", "EquipSeg"
                                    , EquipMBActivity.this, EquipMBActivity.class, progressBar);

                        } else {

                            AlertDialog.Builder alerta = new AlertDialog.Builder(EquipMBActivity.this);
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

        buttonOkMotoBomba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextPadrao.getText().toString().equals("")) {

                    Long motoBomba = Long.parseLong(editTextPadrao.getText().toString());

                    ArrayList listaPesq = new ArrayList();
                    EquipSegTO equipSegTO = new EquipSegTO();

                    EspecificaPesquisa pesquisa1 = new EspecificaPesquisa();
                    pesquisa1.setCampo("nroEquip");
                    pesquisa1.setValor(motoBomba);
                    pesquisa1.setTipo(1);
                    listaPesq.add(pesquisa1);

                    EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                    pesquisa2.setCampo("tipoEquip");
                    pesquisa2.setValor(4L);
                    pesquisa2.setTipo(1);
                    listaPesq.add(pesquisa2);

                    List equipSegList = equipSegTO.get(listaPesq);
                    listaPesq.clear();

                    if (equipSegList.size() == 0) {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(EquipMBActivity.this);
                        alerta.setTitle("ATENCAO");
                        alerta.setMessage("NUMERAÇÃO DA MOTO BOMBA INCORRETA. FAVOR, VERIFICAR A NUMERAÇÃO OU ATUALIZAR A BASE DE DADOS NOVAMENTE!");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();

                    } else {

                        equipSegTO = (EquipSegTO) equipSegList.get(0);
                        equipSegList.clear();

                        pmmContext.getBoletimFertTO().setIdEquipBombaBolFert(equipSegTO.getIdEquip());
                        pmmContext.getBoletimFertTO().setStatusBolFert(1L);

                        configTO = new ConfigTO();
                        List listConfigTO = configTO.all();
                        configTO = (ConfigTO) listConfigTO.get(0);
                        listConfigTO.clear();

                        equipTO = new EquipTO();
                        List listEquipTO = equipTO.get("idEquip", pmmContext.getBoletimFertTO().getIdEquipBolFert());
                        equipTO = (EquipTO) listEquipTO.get(0);
                        listEquipTO.clear();

                        TurnoTO turnoTO = new TurnoTO();
                        List turnoList = turnoTO.get("idTurno", pmmContext.getBoletimFertTO().getIdTurnoBolFert());
                        turnoTO = (TurnoTO) turnoList.get(0);

                        if ((equipTO.getIdCheckList() > 0) &&
                                ((configTO.getUltTurnoCLConfig() != turnoTO.getIdTurno())
                                        || ((configTO.getUltTurnoCLConfig() == turnoTO.getIdTurno()) && (!configTO.getDtUltCLConfig().equals(Tempo.getInstance().dataSHora()))))) {

                            ManipDadosEnvio.getInstance().salvaBoletimAbertoFert(pmmContext.getBoletimFertTO(), true, 0D, 0D);
                            ManipDadosEnvio.getInstance().envioDadosPrinc();

                            ItemCheckListTO itemCheckListTO = new ItemCheckListTO();
                            List itemCheckList =  itemCheckListTO.get("idCheckList", equipTO.getIdCheckList());
                            Long qtde = (long) itemCheckList.size();
                            itemCheckList.clear();

                            CabecCLTO cabecCLTO = new CabecCLTO();
                            cabecCLTO.setDtCabCL(Tempo.getInstance().datahora());
                            EquipTO equipTO = new EquipTO();
                            List equipList = equipTO.get("idEquip", configTO.getEquipConfig());
                            equipTO = (EquipTO) equipList.get(0);
                            equipList.clear();
                            cabecCLTO.setEquipCabCL(equipTO.getNroEquip());
                            cabecCLTO.setFuncCabCL(pmmContext.getBoletimFertTO().getMatricFuncBolFert());
                            cabecCLTO.setTurnoCabCL(pmmContext.getBoletimFertTO().getIdTurnoBolFert());
                            cabecCLTO.setQtdeItemCabCL(qtde);
                            cabecCLTO.setStatusCabCL(1L);
                            cabecCLTO.setDtAtualCabCL("0");
                            cabecCLTO.insert();

                            pmmContext.setPosCheckList(1L);
                            Intent it = new Intent(EquipMBActivity.this, ItemCheckListActivity.class);
                            startActivity(it);
                            finish();

                        }
                        else{

                            configTO.setHorimetroConfig(pmmContext.getBoletimFertTO().getHodometroInicialBolFert());
                            configTO.update();
                            configTO.commit();

                            ManipDadosEnvio.getInstance().salvaBoletimAbertoFert(pmmContext.getBoletimFertTO(), false, 0D, 0D);
                            ManipDadosEnvio.getInstance().envioDadosPrinc();

                            Intent it = new Intent(EquipMBActivity.this, EsperaDadosOperActivity.class);
                            startActivity(it);
                            finish();

                        }

                    }

                }


            }
        });

        buttonCancMotoBomba.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed() {
        pmmContext.setContImplemento(pmmContext.getContImplemento() - 1);
        Intent it = new Intent(EquipMBActivity.this, HorimetroActivity.class);
        startActivity(it);
        finish();
    }

}
