package lampung.dispenda.cctv;

/**
 * Created by Chandra on 5/5/2016.
 * Function Adapter Data CCTV
 */
public class Cctv {
    private String id_cctv;
    private String ip_cctv;
    private String nama_cctv;
    private String status_cctv;
    private String loc_cctv;
    private String loc_list;
    private String id_list;
    private String capture;

    public void setCctvId (String id_cctv)
    {
        this.id_cctv = id_cctv;
    }
    public void setCctvIp (String ip_cctv){ this.ip_cctv = ip_cctv;}

    public String getCctvId()
    {
        return id_cctv;
    }
    public String getCctvIp(){return ip_cctv;}

    public void setCctvName (String nama_cctv)
    {
        this.nama_cctv = nama_cctv;
    }
    public String getCctvName()
    {
        return nama_cctv;
    }

    public void setLocList (String loc_list)
    {
        this.loc_list = loc_list;
    }
    public String getLocList()
    {
        return loc_list;
    }

    public void setIdList (String id_list)
    {
        this.id_list = id_list;
    }
    public String getIdList()
    {
        return id_list;
    }

    public void setCctvStatus (String status_cctv)
    {
        this.status_cctv = status_cctv;
    }
    public String getCctvStatus()
    {
        return status_cctv;
    }

    public void setLocCctv (String loc_cctv)
    {
        this.loc_cctv = loc_cctv;
    }
    public String getLocCctv()
    {
        return loc_cctv;
    }

    public void setCapture (String capture)
    {
        this.capture = capture;
    }
    public String getCapture()
    {
        return capture;
    }


}
