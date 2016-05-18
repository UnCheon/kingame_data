package com.ypunval.pcbangdata;

import android.app.Application;
import android.content.res.AssetManager;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by uncheon on 16. 4. 13..
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration pcBangConfig = new RealmConfiguration.Builder(this)
                .name("pcbang.realm")
                .setModules(new PCBangRealmModule())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(pcBangConfig);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }
}
