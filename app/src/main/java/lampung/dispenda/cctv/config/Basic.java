package lampung.dispenda.cctv.config;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lampung.dispenda.cctv.JSONParser;


/**
 * Created by Chandra on 5/15/2016.
 * config API
 * Warning don't edit or remove it
 */
public class Basic extends Application {
    Context context;
    JSONParser jParser = new JSONParser();
    private String VERSION="1.1.1";
    private String ADDRESS ="http://222.124.4.226:8030/app/";
    private String URLTOWER="https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB3kN4NqZHunCJQIjztVxZqXm2GbbYOavQ";
    private String URLLOC="https://maps.googleapis.com/maps/api/geocode/json";
    //private String ADDRESS ="http://192.168.1.100/cctv/";
    private String LOGIN ="api/signin";
    private String DASHBOARD ="api/dashboard";
    private String infoServer ="linfo-master/index.php";
    private String PROVLIST ="api/list_prov";
    private String CITYLIST ="api/list_city";
    private String CAMLIST ="api/camlist";
    private String CAMONLINE ="api/online";
    private String CAMARSIP ="api/arsip_cam";
    private String CAMCOVER ="cover/";
    private String CAMOFFLINE ="asset/images/online.png";
    private String SETUP_LIST_USER ="api/list_user";
    private String SETUP_LIST_USER_ID ="api/list_user_id";
    private String SETUP_POST_USER ="api/post_user";
    private String SETUP_POST_LOC ="api/post_loc";
    private String SETUP_LIST_LOC ="api/list_location";
    private String SETUP_LIST_LOC_ID ="api/list_location_id";
    private String REPORT ="api/report";
    private String UPDATE_APK ="apk/app-debug.apk";
    private String response;

    public String getVer(){return VERSION;}
    public String getUrl(){return ADDRESS;}
    public String getLogin(){return ADDRESS+LOGIN;}
    public String getDASHBOARD(){return ADDRESS+DASHBOARD;}
    public String getInfoServer(){return ADDRESS+infoServer;}
    public String getPROVLIST(){return ADDRESS+PROVLIST;}
    public String getCITYLIST(){return ADDRESS+CITYLIST;}
    public String getCAMLIST(){return ADDRESS+CAMLIST;}
    public String getCAMONLINE(){return ADDRESS+CAMONLINE;}
    public String getCAMCOVER(){return ADDRESS+CAMCOVER;}
    public String getCAMARSIP(){return ADDRESS+CAMARSIP;}
    public String getCAMOFFLINE(){return ADDRESS+CAMOFFLINE;}
    public String getSETUP_LIST_USER(){return ADDRESS+SETUP_LIST_USER;}
    public String getSETUP_LIST_USER_ID(){return ADDRESS+SETUP_LIST_USER_ID;}
    public String setSETUP_POST_USER(){return ADDRESS+SETUP_POST_USER;}
    public String getSETUP_LIST_LOC(){return ADDRESS+SETUP_LIST_LOC;}
    public String setSETUP_POST_LOC(){return ADDRESS+SETUP_POST_LOC;}
    public String getSETUP_LIST_LOC_ID(){return ADDRESS+SETUP_LIST_LOC_ID;}
    public String getREPORT(){return ADDRESS+REPORT;}
    public String getApk(){return ADDRESS+UPDATE_APK;}

    //public void setContext(Context contextf){context = contextf;}

    public String getResultSignin(){
        response ="{\"profile\":[{\"user_id\":\"1\",\"user_fullname\":\"DEMO\",\"user_name\":\"demo\",\"user_password\":\"202cb962ac59075b964b07152d234b70\",\"group_type_id\":\"1\",\"location_id\":\"0\",\"active_id\":\"1\",\"email\":\"chandra.comment@gmail.com\",\"phone\":\"085781571275\",\"mobile_phone\":\"085781571275\",\"departement_id\":\"7\",\"claim\":\"0\",\"input_by\":\"\",\"input_date\":\"2010-09-15 09:06:39\",\"edit_by\":\"0110090005\",\"edit_date\":\"2013-09-24 18:45:48\",\"modtime\":\"2015-10-06 11:02:40\",\"dp_name\":\"user-avatar.png\"}],\"location\":[{\"location_id\":\"5\",\"location_name\":\"Container\",\"city_id\":\"21\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:19:03\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"10\",\"location_name\":\"Gunung Sugih\",\"city_id\":\"225\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:22:52\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"6\",\"location_name\":\"Kalianda\",\"city_id\":\"224\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:20:07\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"14\",\"location_name\":\"Kotabumi\",\"city_id\":\"227\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:24:37\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"13\",\"location_name\":\"Liwa\",\"city_id\":\"223\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:24:15\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"3\",\"location_name\":\"Mall Chandra\",\"city_id\":\"21\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 07:08:15\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"4\",\"location_name\":\"Mall Kartini\",\"city_id\":\"21\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 07:09:51\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"16\",\"location_name\":\"Mesuji\",\"city_id\":\"282\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:25:23\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"11\",\"location_name\":\"Metro\",\"city_id\":\"283\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:23:13\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"7\",\"location_name\":\"Pesawaran\",\"city_id\":\"355\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:21:27\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"8\",\"location_name\":\"Pringsewu\",\"city_id\":\"368\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:22:00\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"1\",\"location_name\":\"Rajabasa R2\",\"city_id\":\"21\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-10-09 02:47:17\",\"edit_by\":\"1\",\"edit_date\":\"2015-11-21 14:08:54\"},{\"location_id\":\"2\",\"location_name\":\"Rajabasa R4\",\"city_id\":\"21\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-10-09 20:16:03\",\"edit_by\":\"1\",\"edit_date\":\"2015-11-21 14:09:23\"},{\"location_id\":\"12\",\"location_name\":\"Sukadana\",\"city_id\":\"226\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:23:45\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"9\",\"location_name\":\"Tanggamus\",\"city_id\":\"458\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:22:26\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"location_id\":\"15\",\"location_name\":\"Way Kanan\",\"city_id\":\"496\",\"status_id\":\"1\",\"input_by\":\"1\",\"input_date\":\"2015-11-23 10:25:02\",\"edit_by\":\"0\",\"edit_date\":\"0000-00-00 00:00:00\"}],\"group_type\":[{\"group_type_id\":\"1\",\"user_type_id\":\"1\",\"user_group_id\":\"2\",\"group_type_name\":\"Super Admin\",\"group_type_desc\":\"Administrator\",\"input_by\":\"0110090005\",\"input_date\":\"2014-01-27 13:46:03\",\"edit_by\":\"0110090005\",\"edit_date\":\"2014-04-10 14:03:46\"},{\"group_type_id\":\"2\",\"user_type_id\":\"2\",\"user_group_id\":\"2\",\"group_type_name\":\"Monitor All Area\",\"group_type_desc\":\"Admin 1\",\"input_by\":\"\",\"input_date\":\"0000-00-00 00:00:00\",\"edit_by\":\"\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"group_type_id\":\"3\",\"user_type_id\":\"3\",\"user_group_id\":\"2\",\"group_type_name\":\"Monitor Location\",\"group_type_desc\":\"Admin 2\",\"input_by\":\"\",\"input_date\":\"0000-00-00 00:00:00\",\"edit_by\":\"\",\"edit_date\":\"0000-00-00 00:00:00\"},{\"group_type_id\":\"5\",\"user_type_id\":\"5\",\"user_group_id\":\"2\",\"group_type_name\":\"Developer\",\"group_type_desc\":\"Admin 3\",\"input_by\":\"\",\"input_date\":\"0000-00-00 00:00:00\",\"edit_by\":\"\",\"edit_date\":\"0000-00-00 00:00:00\"}],\"menu\":[{\"menu_id\":\"2\",\"menu_name\":\"Dashboard\",\"group_type_id\":\"1,2,3,5\",\"mob_href\":\"chart.Bar\",\"icon\":\"\",\"child_data\":[]},{\"menu_id\":\"5\",\"menu_name\":\"Camera Zones\",\"group_type_id\":\"1,2,3,5\",\"mob_href\":\"child.ChildListActivity\",\"icon\":\"\",\"child_data\":[{\"menu_id\":\"1\",\"menu_name\":\"Rajabasa R2\",\"mob_href\":\"cctv\"},{\"menu_id\":\"2\",\"menu_name\":\"Rajabasa R4\",\"mob_href\":\"cctv\"},{\"menu_id\":\"3\",\"menu_name\":\"Mall Chandra\",\"mob_href\":\"cctv\"},{\"menu_id\":\"4\",\"menu_name\":\"Mall Kartini\",\"mob_href\":\"cctv\"},{\"menu_id\":\"5\",\"menu_name\":\"Container\",\"mob_href\":\"cctv\"},{\"menu_id\":\"6\",\"menu_name\":\"Kalianda\",\"mob_href\":\"cctv\"},{\"menu_id\":\"7\",\"menu_name\":\"Pesawaran\",\"mob_href\":\"cctv\"},{\"menu_id\":\"8\",\"menu_name\":\"Pringsewu\",\"mob_href\":\"cctv\"},{\"menu_id\":\"9\",\"menu_name\":\"Tanggamus\",\"mob_href\":\"cctv\"},{\"menu_id\":\"10\",\"menu_name\":\"Gunung Sugih\",\"mob_href\":\"cctv\"},{\"menu_id\":\"11\",\"menu_name\":\"Metro\",\"mob_href\":\"cctv\"},{\"menu_id\":\"12\",\"menu_name\":\"Sukadana\",\"mob_href\":\"cctv\"},{\"menu_id\":\"13\",\"menu_name\":\"Liwa\",\"mob_href\":\"cctv\"},{\"menu_id\":\"14\",\"menu_name\":\"Kotabumi\",\"mob_href\":\"cctv\"},{\"menu_id\":\"15\",\"menu_name\":\"Way Kanan\",\"mob_href\":\"cctv\"},{\"menu_id\":\"16\",\"menu_name\":\"Mesuji\",\"mob_href\":\"cctv\"}]},{\"menu_id\":\"8\",\"menu_name\":\"Setup\",\"group_type_id\":\"1,5\",\"mob_href\":\"child.ChildListActivity\",\"icon\":\"\",\"child_data\":[{\"menu_id\":\"9\",\"menu_name\":\"Users\",\"group_type_id\":\"1,5\",\"mob_href\":\"module.Setup_users\",\"icon\":\"\"},{\"menu_id\":\"106\",\"menu_name\":\"Location\",\"group_type_id\":\"1,5\",\"mob_href\":\"\",\"icon\":\"\"},{\"menu_id\":\"107\",\"menu_name\":\"Camera\",\"group_type_id\":\"1,5\",\"mob_href\":\"\",\"icon\":\"\"},{\"menu_id\":\"110\",\"menu_name\":\"Record\",\"group_type_id\":\"1,5\",\"mob_href\":\"record\",\"icon\":\"\"}]},{\"menu_id\":\"108\",\"menu_name\":\"Report\",\"group_type_id\":\"1,2,3,5\",\"mob_href\":\"\",\"icon\":\"\",\"child_data\":[]},{\"menu_id\":\"109\",\"menu_name\":\"Logout\",\"group_type_id\":\"1,2,3,5\",\"mob_href\":\"exit\",\"icon\":\"\",\"child_data\":[]}]}";
        return response;
    }
    public String getResponse(int argumen){
        if(argumen == 1){
            response = getResultSignin();
        }else if(argumen == 2){
            response ="[{\"camera_id\":\"1\",\"camera_name\":\"Camera 1\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.39:7879\",\"status\":\"1\",\"location_name\":\"Rajabasa R2\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/1.jpg\"},{\"camera_id\":\"2\",\"camera_name\":\"Camera 2\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.39:7881\",\"status\":\"1\",\"location_name\":\"Rajabasa R2\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/2.jpg\"},{\"camera_id\":\"3\",\"camera_name\":\"Camera 3\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.39:7883\",\"status\":\"1\",\"location_name\":\"Rajabasa R2\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/3.jpg\"},{\"camera_id\":\"4\",\"camera_name\":\"Camera 4\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.39:7885\",\"status\":\"1\",\"location_name\":\"Rajabasa R2\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/4.jpg\"},{\"camera_id\":\"5\",\"camera_name\":\"Camera 5\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.39:7887\",\"status\":\"1\",\"location_name\":\"Rajabasa R2\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/5.jpg\"},{\"camera_id\":\"6\",\"camera_name\":\"Camera 6\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.39:7889\",\"status\":\"1\",\"location_name\":\"Rajabasa R2\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/6.jpg\"},{\"camera_id\":\"7\",\"camera_name\":\"Camera 1\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.58:7879\",\"status\":\"1\",\"location_name\":\"Rajabasa R4\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/7.jpg\"},{\"camera_id\":\"8\",\"camera_name\":\"Camera 2\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.58:7881\",\"status\":\"1\",\"location_name\":\"Rajabasa R4\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/8.jpg\"},{\"camera_id\":\"9\",\"camera_name\":\"Camera 3\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.58:7883\",\"status\":\"1\",\"location_name\":\"Rajabasa R4\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/9.jpg\"},{\"camera_id\":\"10\",\"camera_name\":\"Camera 4\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.58:7885\",\"status\":\"1\",\"location_name\":\"Rajabasa R4\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/10.jpg\"},{\"camera_id\":\"11\",\"camera_name\":\"Camera 1\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.240.218:7880\",\"status\":\"1\",\"location_name\":\"Mall Chandra\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/11.jpg\"},{\"camera_id\":\"12\",\"camera_name\":\"Camera 2\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.240.218:7881\",\"status\":\"1\",\"location_name\":\"Mall Chandra\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/12.jpg\"},{\"camera_id\":\"19\",\"camera_name\":\"Camera 1\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.78:7880\",\"status\":\"1\",\"location_name\":\"Pesawaran\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/19.jpg\"},{\"camera_id\":\"25\",\"camera_name\":\"Camera 1\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.61:7880\",\"status\":\"1\",\"location_name\":\"Gunung Sugih\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/25.jpg\"},{\"camera_id\":\"26\",\"camera_name\":\"Camera 2\",\"ip\":\"rtsp:\\/\\/admin:d1p3nd4l4mpung@36.76.241.61:7881\",\"status\":\"1\",\"location_name\":\"Gunung Sugih\",\"img\":\"http:\\/\\/182.23.32.90:8030\\/app\\/cover\\/26.jpg\"}]";
        }else if(argumen == 3){
            response ="{\"OS\":\"Linux\",\"Kernel\":\"3.19.0-59-generic\",\"AccessedIP\":\"192.168.6.2\",\"Distro\":{\"name\":\"Ubuntu\",\"version\":\"14.04 (Trusty)\"},\"RAM\":{\"type\":\"Physical\",\"total\":16777007104,\"free\":1074941952,\"swapTotal\":8877240320,\"swapFree\":3904892928,\"swapCached\":1175859200,\"swapInfo\":[{\"device\":\"\\/dev\\/sda8\",\"type\":\"partition\",\"size\":8877240320,\"used\":6148206592}]},\"HD\":[{\"name\":\"PERC H710P\",\"vendor\":\"DELL\",\"device\":\"\\/dev\\/sda\",\"reads\":\"4038434\",\"writes\":\"1146219\",\"size\":598879502336,\"partitions\":[{\"size\":9999220736,\"number\":\"1\"},{\"size\":1024,\"number\":\"2\"},{\"size\":149998796800,\"number\":\"5\"},{\"size\":299998642176,\"number\":\"6\"},{\"size\":129999306752,\"number\":\"7\"},{\"size\":8877244416,\"number\":\"8\"}]},{\"name\":\"DVD-ROM DS-8D3SH\",\"vendor\":\"PLDS\",\"device\":\"\\/dev\\/sr0\",\"reads\":\"0\",\"writes\":\"0\",\"size\":1073741312,\"partitions\":false}],\"Mounts\":[],\"Load\":{\"now\":\"3.56\",\"5min\":\"3.20\",\"15min\":\"2.93\"},\"HostName\":\"dipenda-1\",\"UpTime\":{\"text\":\"2 days, 18 hours, 28 minutes, 6 seconds\",\"bootedTimestamp\":\"1463063145\"},\"CPU\":[{\"usage_percentage\":4.12,\"Vendor\":\"GenuineIntel\",\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"MHz\":\"2282.031\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":1.98,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1983.281\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":3.92,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2363.828\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":1.02,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1373.046\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":5.94,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2301.328\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":2.97,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1679.609\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":3,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2361.250\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":1,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1300.468\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":6.93,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2291.718\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0.98,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1850.078\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":8.82,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2301.171\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2021.484\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":1.96,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2364.921\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1245.156\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":3,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2365.703\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1529.062\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":2.94,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2300.000\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1748.828\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0.99,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2343.593\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0.99,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1257.109\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":1.02,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2299.921\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":0,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2097.265\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":6,\"Vendor\":\"GenuineIntel\",\"MHz\":\"2385.781\"},{\"Model\":\"Intel(R) Xeon(R) CPU E5-2620 0 @ 2.00GHz\",\"usage_percentage\":1,\"Vendor\":\"GenuineIntel\",\"MHz\":\"1236.796\"}],\"Model\":\"PowerEdge R720 (Dell Inc. 0VWT90)\",\"CPUArchitecture\":\"x86_64\",\"Network Devices\":{\"lo\":{\"recieved\":{\"bytes\":\"125920\",\"errors\":\"0\",\"packets\":\"2100\"},\"sent\":{\"bytes\":\"125920\",\"errors\":\"0\",\"packets\":\"2100\"},\"state\":\"up\",\"type\":\"Loopback\",\"port_speed\":false},\"em4\":{\"recieved\":{\"bytes\":\"40130209267\",\"errors\":\"0\",\"packets\":\"52666503\"},\"sent\":{\"bytes\":\"3279084913\",\"errors\":\"0\",\"packets\":\"52666503\"},\"state\":\"up\",\"type\":\"Ethernet (PCI) (bnx2x)\",\"port_speed\":\"1000\"},\"em1\":{\"recieved\":{\"bytes\":\"0\",\"errors\":\"0\",\"packets\":\"0\"},\"sent\":{\"bytes\":\"0\",\"errors\":\"0\",\"packets\":\"0\"},\"state\":\"down\",\"type\":\"Ethernet (PCI) (bnx2x)\",\"port_speed\":false},\"em2\":{\"recieved\":{\"bytes\":\"0\",\"errors\":\"0\",\"packets\":\"0\"},\"sent\":{\"bytes\":\"0\",\"errors\":\"0\",\"packets\":\"0\"},\"state\":\"down\",\"type\":\"Ethernet (PCI) (bnx2x)\",\"port_speed\":false},\"em3\":{\"recieved\":{\"bytes\":\"0\",\"errors\":\"0\",\"packets\":\"0\"},\"sent\":{\"bytes\":\"0\",\"errors\":\"0\",\"packets\":\"0\"},\"state\":\"down\",\"type\":\"Ethernet (PCI) (bnx2x)\",\"port_speed\":false}},\"Devices\":[],\"Temps\":[{\"path\":\"\",\"name\":\"Core 1\",\"temp\":44,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 2\",\"temp\":45,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 3\",\"temp\":48,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 4\",\"temp\":45,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 5\",\"temp\":47,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Physical id 0\",\"temp\":48,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 0\",\"temp\":46,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 1\",\"temp\":41,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 2\",\"temp\":38,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 3\",\"temp\":38,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 4\",\"temp\":41,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 5\",\"temp\":40,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Physical id 1\",\"temp\":42,\"unit\":\"C\"},{\"path\":\"\",\"name\":\"Core 0\",\"temp\":38,\"unit\":\"C\"}],\"Battery\":[],\"Raid\":[],\"Wifi\":[],\"SoundCards\":[],\"processStats\":{\"exists\":true,\"totals\":{\"running\":3,\"zombie\":0,\"sleeping\":766,\"stopped\":0},\"proc_total\":769,\"threads\":5160},\"services\":[],\"numLoggedIn\":0,\"virtualization\":[],\"cpuUsage\":2.29,\"phpVersion\":false,\"webService\":false,\"contains\":[],\"timestamp\":\"2016-05-15T08:53:21+00:00\",\"extensions\":[]}";
        }else if(argumen == 5){
            response ="{\"name\":\"DEMO\",\"online\":\"15\",\"offline\":\"29\",\"mem_usage\":15.469429016113,\"mem_tot\":15.624805450439,\"mem_free\":0.15537643432617,\"hd_tot\":274.88510131836,\"hd_usage\":252.96043395996}";
        }else if(argumen == 6){
            response ="{\"data\":[{\"user_id\":\"1\",\"user_fullname\":\"DEMO\",\"user_name\":\"demo\",\"user_password\":\"202cb962ac59075b964b07152d234b70\",\"group_type_id\":\"1\",\"location_id\":\"0\",\"active_id\":\"1\",\"email\":\"chandra.comment@gmail.com\",\"phone\":\"085781571275\",\"mobile_phone\":\"085781571275\",\"departement_id\":\"7\",\"claim\":\"0\",\"input_by\":\"\",\"input_date\":\"2010-09-15 09:06:39\",\"edit_by\":\"0110090005\",\"edit_date\":\"2013-09-24 18:45:48\",\"modtime\":\"2015-10-06 11:02:40\",\"dp_name\":\"user-avatar.png\",\"status_name\":\"Active\",\"group_type_name\":\"Super Admin\",\"location_name\":null},{\"user_id\":\"5\",\"user_fullname\":\"admin rajabasa R2\",\"user_name\":\"rajabasa2\",\"user_password\":\"caf1a3dfb505ffed0d024130f58c5cfa\",\"group_type_id\":\"2\",\"location_id\":\"0\",\"active_id\":\"1\",\"email\":\"\",\"phone\":\"\",\"mobile_phone\":\"\",\"departement_id\":\"7\",\"claim\":\"0\",\"input_by\":\"1\",\"input_date\":\"2016-01-03 10:02:36\",\"edit_by\":\"1\",\"edit_date\":\"2016-01-04 00:31:26\",\"modtime\":\"2016-01-04 00:31:26\",\"dp_name\":\"user-avatar.png\",\"status_name\":\"Active\",\"group_type_name\":\"Monitor All Area\",\"location_name\":null},{\"user_id\":\"6\",\"user_fullname\":\"chandra\",\"user_name\":\"chandra\",\"user_password\":\"7cd9b22054065156dd30dadf4e7278b2\",\"group_type_id\":\"5\",\"location_id\":\"0\",\"active_id\":\"1\",\"email\":\"\",\"phone\":\"\",\"mobile_phone\":\"\",\"departement_id\":\"7\",\"claim\":\"0\",\"input_by\":\"1\",\"input_date\":\"2016-01-03 12:03:29\",\"edit_by\":\"\",\"edit_date\":\"0000-00-00 00:00:00\",\"modtime\":\"2016-01-03 12:03:29\",\"dp_name\":\"user-avatar.png\",\"status_name\":\"Active\",\"group_type_name\":\"Developer\",\"location_name\":null},{\"user_id\":\"7\",\"user_fullname\":\"dieend dobblas\",\"user_name\":\"dieend\",\"user_password\":\"f9819c1ca6bdaa76e76f4eba7dfc27d7\",\"group_type_id\":\"5\",\"location_id\":\"0\",\"active_id\":\"1\",\"email\":\"\",\"phone\":\"\",\"mobile_phone\":\"\",\"departement_id\":\"7\",\"claim\":\"0\",\"input_by\":\"1\",\"input_date\":\"2016-01-18 05:23:59\",\"edit_by\":\"\",\"edit_date\":\"0000-00-00 00:00:00\",\"modtime\":\"2016-01-18 05:23:59\",\"dp_name\":\"user-avatar.png\",\"status_name\":\"Active\",\"group_type_name\":\"Developer\",\"location_name\":null}],\"success\":1}";
        }
        return response;
    }
    public JSONArray getSelect(int arg) throws JSONException {
        String sel = getResponse(1);
        JSONObject jsonObj = new JSONObject(sel);
        JSONArray data_sel=null;
        if(arg==1) {
            data_sel = jsonObj.getJSONArray("location");
        }else{
            data_sel = jsonObj.getJSONArray("group_type");
        }
        return data_sel;
    }
    public String getUniqueID(){
        String myAndroidDeviceId = "";
        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null){
            myAndroidDeviceId = mTelephony.getDeviceId();
        }else{
            myAndroidDeviceId = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return myAndroidDeviceId;
    }
    public String getDeviceInfo(String p_seperator) throws Throwable
    {
        String m_data = "";
        StringBuilder m_builder = new StringBuilder();
        /*m_builder.append("RELEASE " + android.os.Build.VERSION.RELEASE + p_seperator);
        m_builder.append("DEVICE " + android.os.Build.DEVICE + p_seperator);
        m_builder.append("MODEL " + android.os.Build.MODEL + p_seperator);
        m_builder.append("PRODUCT " + android.os.Build.PRODUCT + p_seperator);
        m_builder.append("BRAND " + android.os.Build.BRAND + p_seperator);
        m_builder.append("DISPLAY " + android.os.Build.DISPLAY + p_seperator);
        // TODO : android.os.Build.CPU_ABI is deprecated
        m_builder.append("CPU_ABI " + android.os.Build.CPU_ABI + p_seperator);
        // TODO : android.os.Build.CPU_ABI2 is deprecated
        m_builder.append("CPU_ABI2 " + android.os.Build.CPU_ABI2 + p_seperator);
        m_builder.append("UNKNOWN " + android.os.Build.UNKNOWN + p_seperator);
        m_builder.append("HARDWARE " + android.os.Build.HARDWARE + p_seperator);
        m_builder.append("ID " + android.os.Build.ID + p_seperator);
        m_builder.append("MANUFACTURER " + android.os.Build.MANUFACTURER + p_seperator);
        m_builder.append("SERIAL " + android.os.Build.SERIAL + p_seperator);
        m_builder.append("USER " + android.os.Build.USER + p_seperator);
        m_builder.append("HOST " + android.os.Build.HOST + p_seperator);*/
        m_builder.append(android.os.Build.VERSION.RELEASE + p_seperator);
        m_builder.append(android.os.Build.DEVICE + p_seperator);
        m_builder.append(android.os.Build.MODEL + p_seperator);
        m_builder.append(android.os.Build.PRODUCT + p_seperator);
        m_builder.append(android.os.Build.BRAND + p_seperator);
        m_builder.append(android.os.Build.DISPLAY + p_seperator);
        // TODO : android.os.Build.CPU_ABI is deprecated
        m_builder.append(android.os.Build.CPU_ABI + p_seperator);
        // TODO : android.os.Build.CPU_ABI2 is deprecated
        m_builder.append(android.os.Build.CPU_ABI2 + p_seperator);
        m_builder.append(android.os.Build.UNKNOWN + p_seperator);
        m_builder.append(android.os.Build.HARDWARE + p_seperator);
        m_builder.append(android.os.Build.ID + p_seperator);
        m_builder.append(android.os.Build.MANUFACTURER + p_seperator);
        m_builder.append(android.os.Build.SERIAL + p_seperator);
        m_builder.append(android.os.Build.USER + p_seperator);
        m_builder.append(android.os.Build.HOST + p_seperator);
        m_data = m_builder.toString();
        return m_data;
    }
    public String getTower(String mcc, String mnc, String lac, String cid) throws JSONException {
        JSONObject requestObject = new JSONObject();
        requestObject.put("homeMobileCountryCode", mcc);
        requestObject.put("homeMobileNetworkCode", mnc);
        requestObject.put("radioType", "gsm");
        requestObject.put("carrier", "");
        requestObject.put("considerIp", "true");
        JSONArray requestArray = new JSONArray();
        JSONObject requestObjectA = new JSONObject();
        requestObjectA.put("cellId", cid);
        requestObjectA.put("locationAreaCode", lac);
        requestObjectA.put("mobileCountryCode", mcc);
        requestObjectA.put("mobileNetworkCode", mnc);
        requestArray.put(0, requestObjectA);
        requestObject.put("cellTowers", requestArray);

        Log.d("REQUEST TOWER:", String.valueOf(requestObject));
        Jsonfire bridge =new Jsonfire();
        String resTower =bridge.postData(URLTOWER, requestObject);
        Log.d("RESPONSE TOWER:", String.valueOf(resTower));

        return resTower;
    }
    public String listProv() {
        String urlProv = getPROVLIST();
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("id", "0"));
        parameter.add(new BasicNameValuePair("loc", "1"));
        Log.d("URL DATA PROVINCE", urlProv);
        try {
            JSONObject jsonProv = jParser.makeHttpRequest(urlProv, "POST", parameter);
            if(String.valueOf(jsonProv).equals("null")){
                return "Exception Caught";
            }else {
                JSONArray list_prov = jsonProv.getJSONArray("data");
                Log.d("RESULT DATA PROVINCE", String.valueOf(list_prov));
                return String.valueOf(list_prov);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d("URL DATA ERROR", String.valueOf(e));
            return "Exception Caught";
        }
    }
    public String listCity(String prov_id) {
        String urlCity = getCITYLIST();
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("prov_id", prov_id));
        try {
            JSONObject jsonCity = jParser.makeHttpRequest(urlCity, "POST", parameter);
            if(String.valueOf(jsonCity).equals("null")){
                return "Exception Caught";
            }else {
                JSONArray list_city = jsonCity.getJSONArray("data");
                Log.d("RESULT DATA PROVINCE", String.valueOf(list_city));
                return String.valueOf(list_city);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d("URL DATA ERROR", String.valueOf(e));
            return "Exception Caught";
        }
    }

}
