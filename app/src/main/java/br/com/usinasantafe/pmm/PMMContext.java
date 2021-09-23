package br.com.usinasantafe.pmm;

import android.app.Application;

import br.com.usinasantafe.pmm.control.CECCTR;
import br.com.usinasantafe.pmm.control.CompostoCTR;
import br.com.usinasantafe.pmm.control.MotoMecFertCTR;
import br.com.usinasantafe.pmm.control.CheckListCTR;
import br.com.usinasantafe.pmm.control.ConfigCTR;
import br.com.usinasantafe.pmm.control.InformativoCTR;
import br.com.usinasantafe.pmm.model.dao.LogErroDAO;

/**
 * Created by anderson on 26/04/2017.
 */

public class PMMContext extends Application {

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private MotoMecFertCTR motoMecFertCTR;
    private CECCTR cecCTR;
    private InformativoCTR informativoCTR;
    private CheckListCTR checkListCTR;
    private ConfigCTR configCTR;
    private CompostoCTR compostoCTR;

    public static String versaoAplic = "4.00";
    public static int aplic = 1;   // 1 - PMM; 2 - ECM; 3 - PCOMP

    @Override
    public void onCreate() {
        super.onCreate();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public MotoMecFertCTR getMotoMecFertCTR() {
        if (motoMecFertCTR == null)
            motoMecFertCTR = new MotoMecFertCTR();
        return motoMecFertCTR;
    }

    public CECCTR getCecCTR() {
        if (cecCTR == null)
            cecCTR = new CECCTR();
        return cecCTR;
    }

    public InformativoCTR getInformativoCTR(){
        if (informativoCTR == null)
            informativoCTR = new InformativoCTR();
        return informativoCTR;
    }

    public CheckListCTR getCheckListCTR(){
        if (checkListCTR == null)
            checkListCTR = new CheckListCTR();
        return checkListCTR;
    }

    public ConfigCTR getConfigCTR(){
        if (configCTR == null)
            configCTR = new ConfigCTR();
        return configCTR;
    }

    public CompostoCTR getCompostoCTR(){
        if (compostoCTR == null)
            compostoCTR = new CompostoCTR();
        return compostoCTR;
    }

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            LogErroDAO.getInstance().insert(ex);
            mDefaultExceptionHandler.uncaughtException(thread, ex);
        }
    };

}
