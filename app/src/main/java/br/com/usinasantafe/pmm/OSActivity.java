package br.com.usinasantafe.pmm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pmm.bo.ConexaoWeb;
import br.com.usinasantafe.pmm.bo.ManipDadosVerif;
import br.com.usinasantafe.pmm.to.estaticas.OSTO;
import br.com.usinasantafe.pmm.to.variaveis.ConfigTO;

public class OSActivity extends ActivityGeneric {

    private PMMContext pmmContext;
    private ProgressDialog progressBar;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_os);

        pmmContext = (PMMContext) getApplication();

        Button buttonOkOS = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancOS = (Button) findViewById(R.id.buttonCancPadrao);

        if (pmmContext.getVerPosTela() == 1) {
            EditText editText = (EditText) findViewById(R.id.editTextPadrao);
            editText.setText("");
        } else {
            ConfigTO configTO = new ConfigTO();
            List configList = configTO.all();
            configTO = (ConfigTO) configList.get(0);
            EditText editText = (EditText) findViewById(R.id.editTextPadrao);
            editText.setText(String.valueOf(configTO.getOsConfig()));
        }

        buttonOkOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextPadrao.getText().toString().equals("")) {

                    try{

                        OSTO osTO = new OSTO();

                        if (pmmContext.getVerPosTela() == 1) {
                            if(pmmContext.getTipoEquip() == 1) {
                                pmmContext.getBoletimMMTO().setOsBolMM(Long.parseLong(editTextPadrao.getText().toString()));
                            }
                            else {
                                pmmContext.getBoletimFertTO().setOsBolFert(Long.parseLong(editTextPadrao.getText().toString()));
                            }
                        }
                        else {
                            if(pmmContext.getTipoEquip() == 1) {
                                pmmContext.getApontMMTO().setOsApontMM(Long.parseLong(editTextPadrao.getText().toString()));
                            }
                            else {
                                pmmContext.getApontFertTO().setOsApontFert(Long.parseLong(editTextPadrao.getText().toString()));
                            }

                        }

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (osTO.hasElements()) {

                            List osList = osTO.get("nroOS", Long.parseLong(editTextPadrao.getText().toString()));

                            if (osList.size() > 0) {

                                ConfigTO configTO = new ConfigTO();
                                List configList = configTO.all();
                                configTO = (ConfigTO) configList.get(0);
                                configTO.setOsConfig(Long.parseLong(editTextPadrao.getText().toString()));

                                if (conexaoWeb.verificaConexao(OSActivity.this)) {
                                    configTO.setStatusConConfig(1L);
                                }
                                else{
                                    configTO.setStatusConConfig(0L);
                                }

                                configTO.update();

                                Intent it = new Intent(OSActivity.this, ListaAtividadeActivity.class);
                                startActivity(it);
                                finish();

                            } else {

                                if (conexaoWeb.verificaConexao(OSActivity.this)) {

                                    progressBar = new ProgressDialog(v.getContext());
                                    progressBar.setCancelable(true);
                                    progressBar.setMessage("PESQUISANDO OS...");
                                    progressBar.show();

                                    customHandler.postDelayed(updateTimerThread, 10000);

                                    ManipDadosVerif.getInstance().verDados(editTextPadrao.getText().toString(), "OS"
                                            , OSActivity.this, ListaAtividadeActivity.class, progressBar);

                                } else {

                                    ConfigTO configTO = new ConfigTO();
                                    List configList = configTO.all();
                                    configTO = (ConfigTO) configList.get(0);
                                    configTO.setOsConfig(Long.parseLong(editTextPadrao.getText().toString()));
                                    configTO.setStatusConConfig(0L);
                                    configTO.update();

                                    Intent it = new Intent(OSActivity.this, ListaAtividadeActivity.class);
                                    startActivity(it);
                                    finish();

                                }

                            }

                        } else {

                            if (conexaoWeb.verificaConexao(OSActivity.this)) {

                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("PESQUISANDO OS...");
                                progressBar.show();

                                customHandler.postDelayed(updateTimerThread, 10000);

                                ManipDadosVerif.getInstance().verDados(editTextPadrao.getText().toString(), "OS"
                                        , OSActivity.this, ListaAtividadeActivity.class, progressBar);

                            } else {

                                ConfigTO configTO = new ConfigTO();
                                List configList = configTO.all();
                                configTO = (ConfigTO) configList.get(0);
                                configTO.setOsConfig(Long.parseLong(editTextPadrao.getText().toString()));
                                configTO.setStatusConConfig(0L);
                                configTO.update();

                                Intent it = new Intent(OSActivity.this, ListaAtividadeActivity.class);
                                startActivity(it);
                                finish();

                            }

                        }

                    }
                    catch (NumberFormatException e){

                        AlertDialog.Builder alerta = new AlertDialog.Builder( OSActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("VALOR DE OS INCORRETO! FAVOR VERIFICAR.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();

                    }
                }
            }
        });

        buttonCancOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed()  {
        if (pmmContext.getVerPosTela() == 1) {
            Intent it = new Intent(OSActivity.this, ListaTurnoActivity.class);
            startActivity(it);
            finish();
        } else {
            Intent it = new Intent(OSActivity.this, MenuPrincNormalActivity.class);
            startActivity(it);
            finish();
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if(!ManipDadosVerif.getInstance().isVerTerm()) {

                ManipDadosVerif.getInstance().cancelVer();
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                ConfigTO configTO = new ConfigTO();
                List configList = configTO.all();
                configTO = (ConfigTO) configList.get(0);
                configTO.setOsConfig(Long.parseLong(editTextPadrao.getText().toString()));
                configTO.setStatusConConfig(0L);
                configTO.update();

                Intent it = new Intent(OSActivity.this, ListaAtividadeActivity.class);
                startActivity(it);
                finish();

            }

        }
    };

}
