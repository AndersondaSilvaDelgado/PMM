package br.com.usinasantafe.pmm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.usinasantafe.pmm.util.ConexaoWeb;
import br.com.usinasantafe.pmm.util.VerifDadosServ;

public class EsperaDadosOperActivity extends ActivityGeneric {

    private PMMContext pmmContext;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espera_dados_oper);

        pmmContext = (PMMContext) getApplication();

        ConexaoWeb conexaoWeb = new ConexaoWeb();
        if (conexaoWeb.verificaConexao(this)) {

            pmmContext.getColheitaCTR().verInfoColheitaEquip(String.valueOf(pmmContext.getBoletimCTR().getFunc()), EsperaDadosOperActivity.this, MenuPrincNormalActivity.class, DadosColheitaActivity.class);
            customHandler.postDelayed(runnable, 10000);
        }
        else{
            Intent it = new Intent(EsperaDadosOperActivity.this, MenuPrincNormalActivity.class);
            startActivity(it);
            finish();
        }

    }

    private Runnable runnable = new Runnable(){
        public void run() {
            if(!VerifDadosServ.getInstance().isVerTerm()) {
                Intent it = new Intent(EsperaDadosOperActivity.this, MenuPrincNormalActivity.class);
                startActivity(it);
                finish();
            }
        }
    };

}
