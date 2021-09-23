package br.com.usinasantafe.pmm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pmm.PMMContext;
import br.com.usinasantafe.pmm.R;
import br.com.usinasantafe.pmm.model.dao.LogProcessoDAO;

public class SenhaActivity extends ActivityGeneric {

    private EditText editTextSenha;
    private PMMContext pmmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        editTextSenha = findViewById(R.id.editTextSenha);
        Button btOkSenha = findViewById(R.id.buttonOkSenha);
        Button btCancSenha = findViewById(R.id.buttonCancSenha);

        pmmContext = (PMMContext) getApplication();

        btOkSenha.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insert("btOkSenha.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"unchecked\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (pmmContext.getConfigCTR().getConfig().getPosicaoTela() == 11L){
                    LogProcessoDAO.getInstance().insert("if (pmmContext.getConfigCTR().getConfig().getPosicaoTela() == 11L){", getLocalClassName());
                    if (!pmmContext.getConfigCTR().hasElemConfig()) {
                        LogProcessoDAO.getInstance().insert("if (!pmmContext.getConfigCTR().hasElemConfig()) {\n" +
                                "Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);", getLocalClassName());
                        Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);
                        startActivity(it);
                        finish();
                    } else {
                        LogProcessoDAO.getInstance().insert("} else {", getLocalClassName());
                        if (pmmContext.getConfigCTR().verSenha(editTextSenha.getText().toString())) {
                            LogProcessoDAO.getInstance().insert("if (pmmContext.getConfigCTR().verSenha(editTextSenha.getText().toString())) {\n" +
                                    "Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);", getLocalClassName());
                            Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);
                            startActivity(it);
                            finish();

                        }

                    }
                }
                else{
                    LogProcessoDAO.getInstance().insert("} else {", getLocalClassName());
                    if (editTextSenha.getText().toString().equals("fgbny946")) {
                        LogProcessoDAO.getInstance().insert("if (editTextSenha.getText().toString().equals(\"fgbny946\")) {\n" +
                                "Intent it = new Intent(SenhaActivity.this, LogProcessoActivity.class);", getLocalClassName());
                        Intent it = new Intent(SenhaActivity.this, LogProcessoActivity.class);
                        startActivity(it);
                        finish();
                    }
                }


            }
        });

        btCancSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insert("btCancSenha.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "Intent it = new Intent(SenhaActivity.this, MenuInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(SenhaActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();
            }

        });

    }

    public void onBackPressed()  {
    }

}
