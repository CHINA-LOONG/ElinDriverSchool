package com.elin.elindriverschool.db;

import android.content.Context;

import com.elin.elindriverschool.R;

/**
 * Created by 17535 on 2018/4/9.
 */

public class JsonDBHelper extends DataBaseHelper {
    private static JsonDBHelper mJsonDBHelper;

    private JsonDBHelper(Context context){
        super(context);
    }

    public static JsonDBHelper getInstance(Context context){
        if (mJsonDBHelper==null){
            synchronized (DataBaseHelper.class){
                if (mJsonDBHelper==null){
                    mJsonDBHelper = new JsonDBHelper(context);
                    if (mJsonDBHelper.getDB()==null||!mJsonDBHelper.getDB().isOpen()){
                        mJsonDBHelper.open();
                    }
                }
            }
        }
        return mJsonDBHelper;
    }

    @Override
    protected int getMDbVersion(Context context) {
        return Integer.valueOf(context.getResources().getStringArray(R.array.DATABASE_INFO)[1]);
    }

    @Override
    protected String getDbName(Context context) {
        return context.getResources().getStringArray(R.array.DATABASE_INFO)[0];
    }

    @Override
    protected String[] getDbCreateSql(Context context) {
        return context.getResources().getStringArray(R.array.CREATE_TABLE_SQL);
    }

    @Override
    protected String[] getDbUpdateSql(Context context) {
        return context.getResources().getStringArray(R.array.UPDATE_TABLE_SQL);
    }
}
