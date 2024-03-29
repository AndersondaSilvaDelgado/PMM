package br.com.usinasantafe.cmm.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.usinasantafe.cmm.CMMContext;
import br.com.usinasantafe.cmm.R;
import br.com.usinasantafe.cmm.control.MotoMecFertCTR;
import br.com.usinasantafe.cmm.model.bean.variaveis.RecolhFertBean;
import br.com.usinasantafe.cmm.model.dao.LogProcessoDAO;

public class RecolhimentoActivity extends ActivityGeneric {

    private CMMContext cmmContext;
    private RecolhFertBean recolhFertBean;
    private MotoMecFertCTR motoMecFertCTR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recolhimento);

        cmmContext = (CMMContext) getApplication();

        Button buttonOkRecolMang = findViewById(R.id.buttonOkPadrao);
        Button buttonCancRecolMang = findViewById(R.id.buttonCancPadrao);
        TextView textViewRecolMang = findViewById(R.id.textViewPadrao);
        EditText editText = findViewById(R.id.editTextPadrao);

        motoMecFertCTR = new MotoMecFertCTR();

        int cont = 0;

        LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {\n" +
                "            cont = pmmContext.getMotoMecFertCTR().getContRecolh() - 1;\n" +
                "        } else {\n" +
                "            cont = pmmContext.getMotoMecFertCTR().getPosRecolh();\n" +
                "        }", getLocalClassName());
        if (cmmContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {
            cont = cmmContext.getMotoMecFertCTR().getContRecolh() - 1;
        } else {
            cont = cmmContext.getMotoMecFertCTR().getPosRecolh();
        }

        LogProcessoDAO.getInstance().insertLogProcesso("recolhFertBean =  motoMecFertCTR.getRecolh(" + cont + ");", getLocalClassName());
        recolhFertBean =  motoMecFertCTR.getRecolh(cont);

        textViewRecolMang.setText("OS: " + recolhFertBean.getNroOSRecolhFert() + " \nRECOL. MANGUEIRA:");

        LogProcessoDAO.getInstance().insertLogProcesso("if (recolhFertBean.getValorRecolhFert() > 0) {\n" +
                "            editText.setText(String.valueOf(recolhFertBean.getValorRecolhFert()));\n" +
                "        } else {\n" +
                "            editText.setText(\"\");\n" +
                "        }", getLocalClassName());
        if (recolhFertBean.getValorRecolhFert() > 0) {
            editText.setText(String.valueOf(recolhFertBean.getValorRecolhFert()));
        } else {
            editText.setText("");
        }

        buttonOkRecolMang.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkRecolMang.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            if (!editTextPadrao.getText().toString().equals("")) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {\n" +
                        "                    verTela();", getLocalClassName());
                verTela();
            }
            else{
                LogProcessoDAO.getInstance().insertLogProcesso("else{", getLocalClassName());
                if(cmmContext.getConfigCTR().getConfig().getPosicaoTela() == 9L){
                    LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                            "                    if(pmmContext.getConfigCTR().getConfig().getPosicaoTela() == 9L){\n" +
                            "                        Intent it = new Intent(RecolhimentoActivity.this, MenuPrincPMMActivity.class);", getLocalClassName());
                    Intent it = new Intent(RecolhimentoActivity.this, MenuPrincPMMActivity.class);
                    startActivity(it);
                    finish();
                }
            }

        });

        buttonCancRecolMang.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancRecolMang.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "if (editTextPadrao.getText().toString().length() > 0) {\n" +
                    "                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));\n" +
                    "                }", getLocalClassName());
            if (editTextPadrao.getText().toString().length() > 0) {
                editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
            }
        });


    }

    public void onBackPressed() {
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {", getLocalClassName());
        if (cmmContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {
            LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {", getLocalClassName());
            if(cmmContext.getMotoMecFertCTR().getPosRecolh() > 1){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pmmContext.getMotoMecFertCTR().getPosRecolh() > 1){\n" +
                        "                pmmContext.getMotoMecFertCTR().setPosRecolh(pmmContext.getMotoMecFertCTR().getPosRecolh() - 1);\n" +
                        "                Intent it = new Intent(RecolhimentoActivity.this, RecolhimentoActivity.class);", getLocalClassName());
                cmmContext.getMotoMecFertCTR().setPosRecolh(cmmContext.getMotoMecFertCTR().getPosRecolh() - 1);
                Intent it = new Intent(RecolhimentoActivity.this, RecolhimentoActivity.class);
                startActivity(it);
            }
            else{
                LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                        "                Intent it = new Intent(RecolhimentoActivity.this, HorimetroActivity.class);", getLocalClassName());
                Intent it = new Intent(RecolhimentoActivity.this, HorimetroActivity.class);
                startActivity(it);
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            Intent it = new Intent(RecolhimentoActivity.this, ListaOSRendActivity.class);", getLocalClassName());
            Intent it = new Intent(RecolhimentoActivity.this, ListaOSRendActivity.class);
            startActivity(it);
        }
        finish();
    }

    public void verTela(){

        LogProcessoDAO.getInstance().insertLogProcesso("Long valorRecolMang = Long.parseLong(editTextPadrao.getText().toString());\n" +
                "        recolhFertBean.setValorRecolhFert(valorRecolMang);\n" +
                "pmmContext.getMotoMecFertCTR().atualRecolh(recolhFertBean);", getLocalClassName());
        Long valorRecolMang = Long.parseLong(editTextPadrao.getText().toString());
        recolhFertBean.setValorRecolhFert(valorRecolMang);
        cmmContext.getMotoMecFertCTR().atualRecolh(recolhFertBean);
        if (cmmContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {
            LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {", getLocalClassName());
            if (cmmContext.getMotoMecFertCTR().qtdeRecolh() == cmmContext.getMotoMecFertCTR().getContRecolh()) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getMotoMecFertCTR().qtdeRecolh() == pmmContext.getMotoMecFertCTR().getContRecolh()) {\n" +
                        "                pmmContext.getMotoMecFertCTR().salvarBolMMFertFechado();", getLocalClassName());
                cmmContext.getMotoMecFertCTR().salvarBolMMFertFechado(getLocalClassName());
                LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(RecolhimentoActivity.this, TelaInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(RecolhimentoActivity.this, TelaInicialActivity.class);
                startActivity(it);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                pmmContext.getMotoMecFertCTR().setContRecolh(pmmContext.getMotoMecFertCTR().getContRecolh() + 1);\n" +
                        "                Intent it = new Intent(RecolhimentoActivity.this, RecolhimentoActivity.class);", getLocalClassName());
                cmmContext.getMotoMecFertCTR().setContRecolh(cmmContext.getMotoMecFertCTR().getContRecolh() + 1);
                Intent it = new Intent(RecolhimentoActivity.this, RecolhimentoActivity.class);
                startActivity(it);
            }
        }
        else {
            LogProcessoDAO.getInstance().insertLogProcesso("else {\n" +
                    "            Intent it = new Intent(RecolhimentoActivity.this, ListaOSRecolhActivity.class);", getLocalClassName());
            Intent it = new Intent(RecolhimentoActivity.this, ListaOSRecolhActivity.class);
            startActivity(it);
        }
        finish();
    }

}
