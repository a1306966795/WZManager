package gjp.com.wzmanager.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import gjp.com.wzmanager.bean.AudirecordMxZcBean;
import gjp.com.wzmanager.contract.AZcmxContract;
import gjp.com.wzmanager.param.AudirecordMxParam;
import gjp.com.wzmanager.util.OkHttpUtils;

/**
 * Created by Administrator on 2017/8/2.
 */

public class AZcmxModel implements AZcmxContract.Model {
    @Override
    public void getZcmx(String url, AudirecordMxParam param, final AsyncCallback callback) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tablename", "tc_ck_zc");
            obj.put("keycol", "pzh");
            obj.put("keyvalue", param.getKeyvalue());
            obj.put("module", param.getModule());
            obj.put("usercode", param.getUsercode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postHttp(obj, url, callback, new AsyncCallback2() {
            @Override
            public void onSuccess(Object s) {
                try {
                    String res=s.toString();
                    JSONObject jsonObject = new JSONObject(res);
                    boolean success = jsonObject.getBoolean("Success");
                    if (success) {
                        JSONObject data = jsonObject.getJSONObject("Data");
                        Gson gson = new Gson();
                        AudirecordMxZcBean audirecordMxZcBean = gson.fromJson(data.toString(), AudirecordMxZcBean.class);
                        callback.onSuccess(audirecordMxZcBean);
                    } else {
                        String msg = jsonObject.getString("Message");
                        callback.onError(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
