package br.com.usinasantafe.pmm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pmm.bo.ManipDadosEnvio;
import br.com.usinasantafe.pmm.bo.Tempo;
import br.com.usinasantafe.pmm.to.variaveis.RecolhFertTO;

public class RecolhimentoActivity extends ActivityGeneric {

    private PMMContext pmmContext;
    private RecolhFertTO recolhFertTO;
    private List recolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recolhimento);

        pmmContext = (PMMContext) getApplication();

        Button buttonOkRecolMang = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancRecolMang = (Button) findViewById(R.id.buttonCancPadrao);
        TextView textViewRecolMang = (TextView) findViewById(R.id.textViewPadrao);
        EditText editText = (EditText) findViewById(R.id.editTextPadrao);

        int cont = 0;

        if (pmmContext.getVerPosTela() == 4) {
            cont = pmmContext.getContRecolh() - 1;
        } else if (pmmContext.getVerPosTela() == 14) {
            cont = pmmContext.getPosRecolh();
        }

        recolhFertTO = new RecolhFertTO();
        recolList = recolhFertTO.orderBy("idRecolhFert", true);
        recolhFertTO = (RecolhFertTO) recolList.get(cont);

        textViewRecolMang.setText("OS: " + recolhFertTO.getNroOSRecolhFert() + " \nRECOL. MANGUEIRA:");
        if (recolhFertTO.getValorRecolhFert() > 0) {
            editText.setText(String.valueOf(recolhFertTO.getValorRecolhFert()));
        } else {
            editText.setText("");
        }

        buttonOkRecolMang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pmmContext.getVerPosTela() == 4) {

                    if (!editTextPadrao.getText().toString().equals("")) {

                        Long valorRecolMang = Long.parseLong(editTextPadrao.getText().toString());

                        recolhFertTO.setValorRecolhFert(valorRecolMang);
                        recolhFertTO.setDthrRecolhFert(Tempo.getInstance().datahora());
                        recolhFertTO.setStatusRecolhFert(2L);
                        recolhFertTO.update();
                        recolhFertTO.commit();

                        if (recolList.size() == pmmContext.getContRecolh()) {

                            ManipDadosEnvio.getInstance().salvaBoletimFechadoFert();
                            ManipDadosEnvio.getInstance().envioDadosPrinc();
                            Intent it = new Intent(RecolhimentoActivity.this, MenuInicialActivity.class);
                            startActivity(it);
                            finish();

                        } else {

                            pmmContext.setContRecolh(pmmContext.getContRecolh() + 1);
                            Intent it = new Intent(RecolhimentoActivity.this, RecolhimentoActivity.class);
                            startActivity(it);
                            finish();

                        }

                    }

                } else if (pmmContext.getVerPosTela() == 14) {

                    if (!editTextPadrao.getText().toString().equals("")) {

                        Long valorRecolMang = Long.parseLong(editTextPadrao.getText().toString());

                        recolhFertTO.setValorRecolhFert(valorRecolMang);
                        recolhFertTO.update();
                        recolhFertTO.commit();

                    }

                    Intent it = new Intent(RecolhimentoActivity.this, ListaOSRecolActivity.class);
                    startActivity(it);
                    finish();

                }

            }
        });

        buttonCancRecolMang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });


    }

    public void onBackPressed() {
        if (pmmContext.getVerPosTela() == 4) {
            if(pmmContext.getPosRecolh() > 1){
                pmmContext.setPosRecolh(pmmContext.getPosRecolh() - 1);
                Intent it = new Intent(RecolhimentoActivity.this, RecolhimentoActivity.class);
                startActivity(it);
                finish();
            }
            else{
                Intent it = new Intent(RecolhimentoActivity.this, HorimetroActivity.class);
                startActivity(it);
                finish();
            }
        } else if (pmmContext.getVerPosTela() == 14) {
            Intent it = new Intent(RecolhimentoActivity.this, ListaOSRendActivity.class);
            startActivity(it);
            finish();
        }
    }
}
