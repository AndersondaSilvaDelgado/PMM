package br.com.usinasantafe.pmm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.usinasantafe.pmm.to.variaveis.BoletimFertTO;
import br.com.usinasantafe.pmm.to.variaveis.RecolhFertTO;

public class ListaOSRecolActivity extends ActivityGeneric {

    private PMMContext pmmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_os_recolh);

        Button buttonRetRecMang = (Button) findViewById(R.id.buttonRetRecMang);

        pmmContext = (PMMContext) getApplication();

        BoletimFertTO boletimFertTO = new BoletimFertTO();
        List boletimList = boletimFertTO.get("statusBolFert", 1L);
        boletimFertTO = (BoletimFertTO) boletimList.get(0);
        boletimList.clear();

        RecolhFertTO recolhFertTO = new RecolhFertTO();

        ListView listaRecMang = (ListView) findViewById(R.id.listaRecMang);
        AdapterListRecolh adapterListRecolh = new AdapterListRecolh(this, recolhFertTO.getAndOrderBy("idBolRecolhFert", boletimFertTO.getIdBolFert(), "idRecolhFert", true));
        listaRecMang.setAdapter(adapterListRecolh);

        listaRecMang.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                pmmContext.setContRecolh(position);
                Intent it = new Intent(ListaOSRecolActivity.this, RecolhimentoActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonRetRecMang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(ListaOSRecolActivity.this, MenuPrincNormalActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed() {
    }

}
