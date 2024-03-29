package br.com.usinasantafe.cmm.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import br.com.usinasantafe.cmm.CMMContext;
import br.com.usinasantafe.cmm.R;
import br.com.usinasantafe.cmm.model.bean.estaticas.PressaoBocalBean;
import br.com.usinasantafe.cmm.model.dao.LogProcessoDAO;

public class ListaPressaoFertActivity extends ActivityGeneric {

    private CMMContext cmmContext;
    private ListView pressaoBocalListView;
    private List pressaoBocalList;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pressao_fert);

        cmmContext = (CMMContext) getApplication();

        Button buttonRetPressao = findViewById(R.id.buttonRetPressao);
        Button buttonAtualPressao = findViewById(R.id.buttonAtualPressao);

        buttonAtualPressao.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualPressao.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                AlertDialog.Builder alerta = new AlertDialog.Builder(  ListaPressaoFertActivity.this);\n" +
                    "                alerta.setTitle(\"ATENÇÃO\");\n" +
                    "                alerta.setMessage(\"DESEJA REALMENTE ATUALIZAR BASE DE DADOS?\");", getLocalClassName());
            AlertDialog.Builder alerta = new AlertDialog.Builder(  ListaPressaoFertActivity.this);
            alerta.setTitle("ATENÇÃO");
            alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
            alerta.setNegativeButton("SIM", (dialog, which) -> {

                LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                        "                    @Override\n" +
                        "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                if (connectNetwork) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                            "                            progressBar = new ProgressDialog(ListaPressaoFertActivity.this);\n" +
                            "                            progressBar.setCancelable(true);\n" +
                            "                            progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                            "                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                            "                            progressBar.setProgress(0);\n" +
                            "                            progressBar.setMax(100);\n" +
                            "                            progressBar.show();", getLocalClassName());

                    progressBar = new ProgressDialog(ListaPressaoFertActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getMotoMecFertCTR().atualDados(ListaPressaoFertActivity.this, ListaPressaoFertActivity.class, progressBar, \"Pressao\", 1, getLocalClassName());", getLocalClassName());
                    cmmContext.getMotoMecFertCTR().atualDados(ListaPressaoFertActivity.this, ListaPressaoFertActivity.class, progressBar, "Pressao", 1, getLocalClassName());

                } else {

                    LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder( ListaPressaoFertActivity.this);\n" +
                            "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                            alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
                            "                            alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                                @Override\n" +
                            "                                public void onClick(DialogInterface dialog, int which) {\n" +
                            "                                }\n" +
                            "                            });\n" +
                            "                            alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta1 = new AlertDialog.Builder( ListaPressaoFertActivity.this);
                    alerta1.setTitle("ATENÇÃO");
                    alerta1.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                    alerta1.setPositiveButton("OK", (dialog1, which1) -> {
                    });
                    alerta1.show();

                }


            });

            alerta.setPositiveButton("NÃO", (dialog, which) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                    "                    @Override\n" +
                    "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName()));

            alerta.show();

        });

        LogProcessoDAO.getInstance().insertLogProcesso("pressaoBocalList = pmmContext.getMotoMecFertCTR().pressaoBocalList();", getLocalClassName());
        pressaoBocalList = cmmContext.getMotoMecFertCTR().pressaoBocalList();

        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList<String> itens = new ArrayList<String>();\n" +
                "        for(int i = 0; i < pressaoBocalList.size(); i++){\n" +
                "            PressaoBocalBean pressaoBocalBean = (PressaoBocalBean) pressaoBocalList.get(i);\n" +
                "            itens.add(\"\" + pressaoBocalBean.getValorPressao());\n" +
                "        }\n" +
                "        HashSet<String> hashSet = new HashSet<String>(itens);\n" +
                "        itens.clear();\n" +
                "        itens.addAll(hashSet);\n" +
                "        Collections.sort(itens);\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        pressaoBocalListView = findViewById(R.id.listPressao);\n" +
                "        pressaoBocalListView.setAdapter(adapterList);", getLocalClassName());
        ArrayList<String> itens = new ArrayList<>();

        for(int i = 0; i < pressaoBocalList.size(); i++){
            PressaoBocalBean pressaoBocalBean = (PressaoBocalBean) pressaoBocalList.get(i);
            itens.add("" + pressaoBocalBean.getValorPressao());
        }

        HashSet<String> hashSet = new HashSet<>(itens);
        itens.clear();
        itens.addAll(hashSet);
        Collections.sort(itens);

        AdapterList adapterList = new AdapterList(this, itens);
        pressaoBocalListView = findViewById(R.id.listPressao);
        pressaoBocalListView.setAdapter(adapterList);

        pressaoBocalListView.setOnItemClickListener((l, v, position, id) -> {

            LogProcessoDAO.getInstance().insertLogProcesso("pressaoBocalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                    "                                    long id) {\n" +
                    "                TextView textView = (TextView) v.findViewById(R.id.textViewItemList);\n" +
                    "                pmmContext.getConfigCTR().setPressaoConfig(Double.parseDouble(textView.getText().toString()));\n" +
                    "                pressaoBocalList.clear();\n" +
                    "                Intent it = new Intent(ListaPressaoFertActivity.this, ListaVelocFertActivity.class);", getLocalClassName());

            TextView textView = (TextView) v.findViewById(R.id.textViewItemList);
            cmmContext.getConfigCTR().setPressaoConfig(Double.parseDouble(textView.getText().toString()));
            pressaoBocalList.clear();

            Intent it = new Intent(ListaPressaoFertActivity.this, ListaVelocFertActivity.class);
            startActivity(it);
            finish();

        });

        buttonRetPressao.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonRetPressao.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(ListaPressaoFertActivity.this, ListaBocalFertActivity.class);", getLocalClassName());
            Intent it = new Intent(ListaPressaoFertActivity.this, ListaBocalFertActivity.class);
            startActivity(it);
            finish();

        });

    }

    public void onBackPressed() {
    }

}
