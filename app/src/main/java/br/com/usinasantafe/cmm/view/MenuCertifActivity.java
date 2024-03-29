package br.com.usinasantafe.cmm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.cmm.CMMContext;
import br.com.usinasantafe.cmm.R;
import br.com.usinasantafe.cmm.model.dao.LogProcessoDAO;

public class MenuCertifActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private ListView menuCertifListView;
    private CMMContext cmmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_certif);

        cmmContext = (CMMContext) getApplication();

        Button buttonRetMenuInicialApont = findViewById(R.id.buttonRetMenuInicialApont);

        ArrayList<String> itens = new ArrayList<>();

        itens.add("APONTAMENTO");
        itens.add("ATUALIZAR");
        itens.add("LOG VIAGEM");
        itens.add("LOG BOLETIM");

        LogProcessoDAO.getInstance().insertLogProcesso("AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        menuCertifListView = findViewById(R.id.listViewMenuInicialApont);\n" +
                "        menuCertifListView.setAdapter(adapterList);", getLocalClassName());

        AdapterList adapterList = new AdapterList(this, itens);
        menuCertifListView = findViewById(R.id.listViewMenuInicialApont);
        menuCertifListView.setAdapter(adapterList);

        menuCertifListView.setOnItemClickListener((l, v, position, id) -> {

            LogProcessoDAO.getInstance().insertLogProcesso("menuCertifListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                    "            @SuppressWarnings(\"rawtypes\")\n" +
                    "            @Override\n" +
                    "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                    "                                    long id) {\n" +
                    "                TextView textView = v.findViewById(R.id.textViewItemList);\n" +
                    "                String text = textView.getText().toString();", getLocalClassName());

            TextView textView = v.findViewById(R.id.textViewItemList);
            String text = textView.getText().toString();

            if (text.equals("APONTAMENTO")) {

                LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"APONTAMENTO\")) {", getLocalClassName());

                if (cmmContext.getCecCTR().verPreCECAberto()) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getCecCTR().verPreCECAberto()) {", getLocalClassName());
                    if (cmmContext.getCecCTR().verDataPreCEC()) {

                        LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getCecCTR().verDataPreCEC()) {\n" +
                                "                            Intent it = new Intent(MenuCertifActivity.this, EquipActivity.class);", getLocalClassName());
                        Intent it = new Intent(MenuCertifActivity.this, EquipActivity.class);
                        startActivity(it);
                        finish();

                    } else {

                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);\n" +
                                "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                            alerta.setMessage(\"É NECESSÁRIO A INSERÇÃO DO HORARIO DE SAÍDA DA USINA E/OU DE CHEGADA AO CAMPO.\");", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("É NECESSÁRIO A INSERÇÃO DO HORARIO DE SAÍDA DA USINA E/OU DE CHEGADA AO CAMPO.");
                        alerta.setPositiveButton("OK", (dialog, which) -> {
                            LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                                @Override\n" +
                                    "                                public void onClick(DialogInterface dialog, int which) {\n" +
                                    "                                    Intent it = new Intent(MenuCertifActivity.this, MenuPrincECMActivity.class);", getLocalClassName());
                            Intent it = new Intent(MenuCertifActivity.this, MenuPrincECMActivity.class);
                            startActivity(it);
                            finish();
                        });

                        alerta.show();

                    }
                } else {

                    LogProcessoDAO.getInstance().insertLogProcesso("else {\n" +
                            "                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(\"É NECESSÁRIO A INSERÇÃO DO HORARIO DE SAÍDA DA USINA E/OU DE CHEGADA AO CAMPO.\");", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("É NECESSÁRIO A INSERÇÃO DO HORARIO DE SAÍDA DA USINA E/OU DE CHEGADA AO CAMPO.");
                    alerta.setPositiveButton("OK", (dialog, which) -> {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                "                            @Override\n" +
                                "                            public void onClick(DialogInterface dialog, int which) {\n" +
                                "                                Intent it = new Intent(MenuCertifActivity.this, MenuPrincECMActivity.class);", getLocalClassName());
                        Intent it = new Intent(MenuCertifActivity.this, MenuPrincECMActivity.class);
                        startActivity(it);
                        finish();
                    });

                    alerta.show();


                }

            } else if (text.equals("ATUALIZAR")) {

                LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"ATUALIZAR\")) {", getLocalClassName());
                if (connectNetwork) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                            "                        progressBar = new ProgressDialog(v.getContext());\n" +
                            "                        progressBar.setCancelable(true);\n" +
                            "                        progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                            "                        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                            "                        progressBar.setProgress(0);\n" +
                            "                        progressBar.setMax(100);\n" +
                            "                        progressBar.show();", getLocalClassName());

                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getConfigCTR().atualTodasTabelas(MenuCertifActivity.this, progressBar, getLocalClassName());", getLocalClassName());
                    cmmContext.getConfigCTR().atualTodasTabelas(MenuCertifActivity.this, progressBar, getLocalClassName());

                } else {

                    LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
                            "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                            @Override\n" +
                            "                            public void onClick(DialogInterface dialog, int which) {\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                        alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta.setPositiveButton("OK", (dialog, which) -> {
                    });
                    alerta.show();

                }

            } else if (text.equals("LOG VIAGEM")) {

                LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"LOG VIAGEM\")) {", getLocalClassName());
                if (cmmContext.getCecCTR().verPreCECTerminadoList()) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getCecCTR().verPreCECTerminadoList()) {\n" +
                            "                        Intent it = new Intent(MenuCertifActivity.this, BackupPreCECActivity.class);", getLocalClassName());
                    Intent it = new Intent(MenuCertifActivity.this, BackupPreCECActivity.class);
                    startActivity(it);
                    finish();
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(\"NÃO TEM NENHUMA VIAGEM SALVA NA BASE DE DADOS.\");\n" +
                            "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                            @Override\n" +
                            "                            public void onClick(DialogInterface dialog, int which) {\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                        alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("NÃO TEM NENHUMA VIAGEM SALVA NA BASE DE DADOS.");
                    alerta.setPositiveButton("OK", (dialog, which) -> {
                    });
                    alerta.show();
                }

            } else if (text.equals("LOG BOLETIM")) {

                LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"LOG BOLETIM\")) {", getLocalClassName());
                if (cmmContext.getCecCTR().hasElemCEC()) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getCecCTR().hasElemCEC()) {\n" +
                            "                        Intent it = new Intent(MenuCertifActivity.this, BackupCECActivity.class);", getLocalClassName());
                    Intent it = new Intent(MenuCertifActivity.this, BackupCECActivity.class);
                    startActivity(it);
                    finish();
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                        AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(\"NÃO TEM NENHUM BOLETIM SALVO NA BASE DE DADOS.\");\n" +
                            "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                            @Override\n" +
                            "                            public void onClick(DialogInterface dialog, int which) {\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                        alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MenuCertifActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("NÃO TEM NENHUM BOLETIM SALVO NA BASE DE DADOS.");
                    alerta.setPositiveButton("OK", (dialog, which) -> {
                    });
                    alerta.show();
                }

            }

        });

        buttonRetMenuInicialApont.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonRetMenuInicialApont.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(MenuCertifActivity.this, MenuPrincECMActivity.class);", getLocalClassName());
            Intent it = new Intent(MenuCertifActivity.this, MenuPrincECMActivity.class);
            startActivity(it);
            finish();

        });

    }

    public void onBackPressed() {
    }

}