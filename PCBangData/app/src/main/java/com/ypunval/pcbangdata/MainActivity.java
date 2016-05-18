package com.ypunval.pcbangdata;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ypunval.pcbangdata.models.Category;
import com.ypunval.pcbangdata.models.Convenience;
import com.ypunval.pcbangdata.models.Doe;
import com.ypunval.pcbangdata.models.Dong;
import com.ypunval.pcbangdata.models.Line;
import com.ypunval.pcbangdata.models.PCBang;
import com.ypunval.pcbangdata.models.Region;
import com.ypunval.pcbangdata.models.Si;
import com.ypunval.pcbangdata.models.Subway;
import com.ypunval.pcbangdata.models.Sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String  TAG = "MAINACTIVITY";

    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void make_does(View v) throws IOException, JSONException {
        Log.i("doe", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_does.json"));

        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");
            String nickname = item.getString("nickname");

            Doe doe = realm.createObject(Doe.class);
            doe.setName(name);
            doe.setNickname(nickname);
            doe.setId(id);
        }
        realm.commitTransaction();
        Log.i("doe", "end");
    }

    public void make_sis(View v) throws JSONException, IOException {
        Log.i("si", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_sis.json"));
        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");
//            int pcBangCount = item.getInt("pcbang_count");
            int doe_id = item.getInt("doe");
            Doe doe = realm.where(Doe.class).equalTo("id", doe_id).findFirst();

            Si si = realm.createObject(Si.class);
            si.setName(name);
            si.setId(id);
            si.setDoe(doe);
//            si.setPcBangCount(pcBangCount);
        }
        realm.commitTransaction();
        Log.i("si", "end");
    }

    public void make_dongs(View v) throws JSONException, IOException {
        Log.i("dong", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_dongs.json"));
        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");
            int doe_id = item.getInt("doe");
            int si_id = item.getInt("si");
            Si si = realm.where(Si.class).equalTo("id", si_id).findFirst();
            Doe doe = realm.where(Doe.class).equalTo("id", doe_id).findFirst();

            Dong dong = realm.createObject(Dong.class);
            dong.setName(name);
            dong.setId(id);
            dong.setDoe(doe);
            dong.setSi(si);
        }
        realm.commitTransaction();
        Log.i("dong", "end");
    }

    public void make_regions(View v) throws IOException, JSONException {
        Log.i("region", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_regions.json"));

        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");
            String code = item.getString("code");

            Region region = realm.createObject(Region.class);
            region.setName(name);
            region.setCode(code);
            region.setId(id);
        }
        realm.commitTransaction();
        Log.i("region", "end");
    }

    public void make_lines(View v) throws IOException, JSONException {
        Log.i("line", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_lines.json"));

        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String line_num = item.getString("line_num");
            int region_id = item.getInt("region");
            String name = item.getString("name");

            Region region = realm.where(Region.class).equalTo("id", region_id).findFirst();

            Line line = realm.createObject(Line.class);
            line.setName(name);
            line.setLineNum(line_num);
            line.setRegion(region);
            line.setId(id);
        }
        realm.commitTransaction();
        Log.i("line", "end");
    }


    public void make_subways(View v) throws IOException, JSONException {
        Log.i("subway", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_subways.json"));
        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");
            String line = item.getString("line");
            String region = item.getString("region");
//            Integer pcbang_count = item.getInt("pcbang_count");


            float latitude = (float)item.getDouble("latitude");
            float longitude = (float)item.getDouble("longitude");

            Subway subway = realm.createObject(Subway.class);
            subway.setId(id);
            subway.setName(name);
            subway.setLine(line);
            subway.setRegion(region);
//            subway.setPcBangCount(pcbang_count);
            subway.setLattitude(latitude);
            subway.setLongitude(longitude);
        }
        realm.commitTransaction();
        Log.i("subway", "end");
    }

    public void make_categories(View v) throws IOException, JSONException {
        Log.i("category", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_categories.json"));
        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");

            Category category = realm.createObject(Category.class);
            category.setId(id);
            category.setName(name);
        }
        realm.commitTransaction();
        Log.i("category", "end");
    }

    public void make_conveniences(View v) throws IOException, JSONException {
        Log.i("convenience", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_conveniences.json"));
        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");
            int order = item.getInt("order");
            int category_id = item.getInt("category");
//            String created = item.getString("created");
            Category category = realm.where(Category.class).equalTo("id", category_id).findFirst();

            Convenience convenience = realm.createObject(Convenience.class);
            convenience.setName(name);
            convenience.setId(id);
            convenience.setOrder(order);
//            convenience.setCreated(created);
            convenience.setCategory(category);

        }
        realm.commitTransaction();
        Log.i("convenience", "end");
    }


    public void make_pcbangs(View v) throws IOException, JSONException {
        Log.i("pcbang", "start");

        realm.beginTransaction();
        JSONArray reader = new JSONArray(loadJSONFromAsset("json_pcbangs.json"));
        for (int i = 0; i < reader.length(); i++) {
            JSONObject item = reader.getJSONObject(i);
            int id = item.getInt("id");
            String name = item.getString("name");
            String phone_number = item.getString("phone_number");
            String computer = item.getString("computer");
            String address1 = item.getString("address1");
            int pcbang_number = item.getInt("pcbang_number");
//            int i_exist = item.getInt("exist");
//            boolean exist = true;
//            if (i_exist == 0)
//                exist = false;
            boolean exist = item.getBoolean("exist");

//                        String address2 = obj.getString("address2");

            float latitude = (float) item.getDouble("latitude");
            float longitude = (float) item.getDouble("longitude");

            Integer review_count = item.getInt("review_count");
            Integer total_rate = item.getInt("total_rate");
            Float average_rate = (float) item.getDouble("average_rate");

            Integer alliance_level = item.getInt("alliance_level");


            String images = item.getString("images");
            String imageMain = item.getString("imageMain");
            String imageThumb = item.getString("imageThumb");
            String description1  = item.getString("description1");
            String description2  = item.getString("description2");

            String price = item.getString("price");
            Integer min_price = (Integer) item.get("min_price");

            String seat = item.getString("seat");
            Integer total_seat = item.getInt("total_seat");
            Integer left_seat = item.getInt("left_seat");

//            String allied = item.getString("allied");
//            String updated = item.getString("updated");

            int doe_id = item.getInt("doe");
            int si_id = item.getInt("si");
            int dong_id = 0;
            try {
                dong_id = item.getInt("dong");
            } catch (JSONException e) {

            }


            Doe doe = realm.where(Doe.class).equalTo("id", doe_id).findFirst();
            Si si = realm.where(Si.class).equalTo("id", si_id).findFirst();
            Dong dong = realm.where(Dong.class).equalTo("id", dong_id).findFirst();


            JSONArray ja_subways = item.getJSONArray("subway");
            RealmList<Subway> subways = new RealmList<Subway>();
            for (int j = 0 ; j < ja_subways.length() ; j++){
                Subway subway = realm.where(Subway.class).equalTo("id", (int)ja_subways.get(j)).findFirst();
                subways.add(subway);
            }

            JSONArray ja_conveniences = item.getJSONArray("convenience");
            RealmList<Convenience> conveniences = new RealmList<Convenience>();
            for (int j = 0 ; j < ja_conveniences.length() ; j++){
                Convenience convenience = realm.where(Convenience.class).equalTo("id", (int)ja_conveniences.get(j)).findFirst();
                conveniences.add(convenience);
            }

            PCBang pcbang = realm.createObject(PCBang.class);
            pcbang.setId(id);
            pcbang.setName(name);
            pcbang.setPhoneNumber(phone_number);
            pcbang.setExist(exist);
            pcbang.setPcBangNumber(pcbang_number);
            pcbang.setComputer(computer);
            pcbang.setAddress1(address1);
//                        pcbang.setAddress2(address2);
            pcbang.setLatitude(latitude);
            pcbang.setLongitude(longitude);
            pcbang.setReviewCount(review_count);
            pcbang.setTotalRate(total_rate);
            pcbang.setAverageRate(average_rate);
            pcbang.setAllianceLevel(alliance_level);
            pcbang.setImages(images);
            pcbang.setImageMain(imageMain);
            pcbang.setImageThumb(imageThumb);
            pcbang.setDescription1(description1);
            pcbang.setDescription2(description2);
            pcbang.setPrice(price);
            pcbang.setMinPrice(min_price);
            pcbang.setSeat(seat);
            pcbang.setTotalSeat(total_seat);
            pcbang.setLeftSeat(left_seat);
//            Todo: 시간정보 추가되면 넣기
//            pcbang.setAllied(allied);
//            pcbang.setUpdated(updated);

            pcbang.setDoe(doe);
            pcbang.setSi(si);
            pcbang.setDong(dong);

            if (subways.size() != 0)
                pcbang.setSubway(subways);
            if (conveniences.size() != 0)
                pcbang.setConvenience(conveniences);
        }
        realm.commitTransaction();
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT);
        Log.i("pcbang", "success end");

    }

    public void make_update(View v) throws IOException, JSONException {
        Log.i("sync", "start");
        realm.beginTransaction();

        JSONObject item = new JSONObject(loadJSONFromAsset("json_updatelog.json"));

        int period = item.getInt("period");
        String updated_st = item.getString("updated");

        Sync sync = realm.createObject(Sync.class);
        sync.setId(1);
        sync.setPeriod(period);
        sync.setUpdated(updated_st);
        sync.setLastRequsetTime(System.currentTimeMillis());
        Log.i("sync", "end");
        realm.commitTransaction();
    }


    public void update_pcbang_count(View v){
        Log.i(TAG, "update_pcbang_count: start");
        realm.beginTransaction();
        RealmResults<Si> sis = realm.where(Si.class).findAll();
        for (int i = 0; i < sis.size(); i++) {
            Si si = sis.get(i);
            si.setPcBangCount((int) realm.where(PCBang.class).equalTo("si.id", si.getId()).equalTo("exist", true).count());
        }

        RealmResults<Subway> subways = realm.where(Subway.class).findAll();
        for (int i = 0; i < subways.size(); i++) {
            Subway subway = subways.get(i);
            subway.setPcBangCount((int) realm.where(PCBang.class).equalTo("subway.id", subway.getId()).equalTo("exist", true).count());
        }

        realm.commitTransaction();

        Log.i(TAG, "update_pcbang_count: end");
    }




    public void send_email(View v){
        // init realm
        File exportRealmFile = null;

        try {
            exportRealmFile = new File(this.getExternalCacheDir(), "pcbang.realm");
            exportRealmFile.delete();
            realm.writeCopyTo(exportRealmFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "YOUR MAIL");
        intent.putExtra(Intent.EXTRA_SUBJECT, "YOUR SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }


    public String loadJSONFromAsset(String file_name) {
        String json = null;
        try {
            InputStream is = getAssets().open(file_name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
