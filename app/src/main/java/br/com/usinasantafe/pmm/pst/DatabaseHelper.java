package br.com.usinasantafe.pmm.pst;
 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.usinasantafe.pmm.to.tb.estaticas.AtividadeTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.BocalTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.EquipSegTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.EquipTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.ItemCheckListTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.MotoristaTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.OSTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.ParadaTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.PressaoBocalTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.RAtivParadaTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.REquipAtivTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.ROSAtivTO;
import br.com.usinasantafe.pmm.to.tb.estaticas.TurnoTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.ApontaFertTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.ApontaMMTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.BackupApontaTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.BoletimFertTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.BoletimMMTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.CabecCheckListTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.ConfigTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.ImplementoTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.PerdaTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.RecolhimentoTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.RendimentoTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.RespItemCheckListTO;
import br.com.usinasantafe.pmm.to.tb.variaveis.TransbordoTO;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String FORCA_DB_NAME = "pmm_db";
	public static final int FORCA_BD_VERSION = 1;

	private static DatabaseHelper instance;
	
	public static DatabaseHelper getInstance(){
		return instance;
	}
	
	public DatabaseHelper(Context context) {

		super(context, FORCA_DB_NAME, null, FORCA_BD_VERSION);

		instance = this;
		
	}

	@Override
	public void close() {
		super.close();
		instance = null;
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		
		try{

			TableUtils.createTable(cs, EquipTO.class);
			TableUtils.createTable(cs, EquipSegTO.class);
			TableUtils.createTable(cs, AtividadeTO.class);
			TableUtils.createTable(cs, REquipAtivTO.class);
			TableUtils.createTable(cs, TurnoTO.class);
			TableUtils.createTable(cs, MotoristaTO.class);
			TableUtils.createTable(cs, OSTO.class);
			TableUtils.createTable(cs, ROSAtivTO.class);
			TableUtils.createTable(cs, RAtivParadaTO.class);
			TableUtils.createTable(cs, ParadaTO.class);
			TableUtils.createTable(cs, ItemCheckListTO.class);
			TableUtils.createTable(cs, BocalTO.class);
			TableUtils.createTable(cs, PressaoBocalTO.class);

			TableUtils.createTable(cs, ConfigTO.class);
			TableUtils.createTable(cs, BoletimMMTO.class);
			TableUtils.createTable(cs, ApontaMMTO.class);
			TableUtils.createTable(cs, CabecCheckListTO.class);
			TableUtils.createTable(cs, RespItemCheckListTO.class);
			TableUtils.createTable(cs, RendimentoTO.class);
			TableUtils.createTable(cs, RecolhimentoTO.class);
			TableUtils.createTable(cs, TransbordoTO.class);
			TableUtils.createTable(cs, ImplementoTO.class);
			TableUtils.createTable(cs, BackupApontaTO.class);
			TableUtils.createTable(cs, BoletimFertTO.class);
			TableUtils.createTable(cs, ApontaFertTO.class);
			TableUtils.createTable(cs, PerdaTO.class);
			
		}
		catch(Exception e){
			Log.e(DatabaseHelper.class.getName(),
					"Erro criando banco de dados...",
					e);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,
			ConnectionSource cs,
			int oldVersion,
			int newVersion) {
		
		try {
			
			if(oldVersion == 1 && newVersion == 2){
//				TableUtils.createTable(cs, ConfigTO.class);
				oldVersion = 2;
			}
			
			
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Erro atualizando banco de dados...", e);
		}
		
	}

}
