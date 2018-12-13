package com.chunbin.app.test;



import com.chunbin.app.entity.Trailers;
import com.chunbin.app.utils.GsonUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainTest {
    private static String TAG = "MainTest";
    private static String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        String s = response.body().string();
                        //Log.d(TAG, "s = " + s);
                        System.out.println("s = " + s);
                                    /*Trailers trailers = new Gson().fromJson(s,Trailers.class);
                                    Log.e(TAG,"trailers.toString() = " + trailers.toString());*/
                        Map<String, Object> map = GsonUtil.toMap(s);
                        List<Map<String, Object>> listMap = (List<Map<String, Object>>) map.get("trailers");
                        ArrayList<Trailers> list = null;
                        for (Map<String, Object> map1 : listMap) {
                            list = new ArrayList<Trailers>();
                            Trailers trailers = new Trailers();
                            trailers.setId(Long.parseLong(map1.get("id").toString()));
                            trailers.setMovieName(map1.get("movieName").toString());
                            trailers.setCoverImg(map1.get("coverImg").toString());
                            trailers.setMovieId(Long.parseLong(map1.get("movieId").toString()));
                            trailers.setUrl(map1.get("url").toString());
                            trailers.setHightUrl(map1.get("hightUrl").toString());
                            trailers.setVideoTitle(map1.get("videoTitle").toString());
                            trailers.setRating(map1.get("rating").toString());
                            trailers.setSummary(map1.get("summary").toString());
                            trailers.setVideoLength(Integer.parseInt(map1.get("videoLength").toString()));
                            String str = map1.get("type").toString().substring(1,map1.get("type").toString().length()-1);
                            //System.out.println(str);
                            String[] stra = str.split(",");
                            trailers.setType(stra);
                            list.add(trailers);

                        }
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i));
                        }

                        //  Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
                        //           while (it.hasNext()) {
                        //                    Map.Entry<String, Object> entry = it.next();
                        //                     System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                        //                     str = GsonUtil.tojson(entry.getValue().toString());
                        //               }
                        //   System.out.println("str = "+str);
                        //    String str1 = GsonUtil.tojson(str);

                        //  Map<String, Object> map1 = GsonUtil.toMap(str);

                    }
                }
            }
        });
    }

}
