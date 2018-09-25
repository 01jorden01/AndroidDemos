package be.example.j.retrofitoef.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

//https://www.brugge.be/afvalmandenjson
public class AfvalMand {


    /*

       {
        "json_featuretype":"Afvalmanden"
        ,"DEELGEMEENTE":"Dudzele"
        ,"STRAAT":"AMAAT VYNCKESTRAAT"
        ,"NUMMER":"DU025"
        ,"TYPE":"F"
        ,"LIGGING":"sportterrein, aan kleedkamers"
        ,"MI_PRINX":1933
        ,"Aangemaakt op":"30/10/2017"
        ,"json_ogc_wkt_crs":"GEOGCS[\"WGS84 Lat/Longs\",DATUM[\"WGS_1984\",SPHEROID[\"World Geodetic System of 1984, GEM 10C\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0],UNIT[\"degree\",0.0174532925199433],AUTHORITY[\"EPSG\",\"4326\"]]"
        ,"json_geometry":{"TYPE":"Point","coordinates":[3.22883282750274,51.2757797386089]}
        }

    */

    @SerializedName("json_featuretype")
    private String json_featuretype;

    @SerializedName("DEELGEMEENTE")
    private String DEELGEMEENTE;

    @SerializedName("STRAAT")
    private String STRAAT;

    @SerializedName("NUMMER")
    private String NUMMER;

    @SerializedName("TYPE")
    private String TYPE;

    @SerializedName("LIGGING")
    private String LIGGING;

    @SerializedName("MI_PRINX")
    private String MI_PRINX;

    @SerializedName("aanmaakDatum")
    private Date aanmaakDatum;

    @SerializedName("json_ogc_wkt_crs")
    private String json_ogc_wkt_crs;

    @SerializedName("json_geometry")
    private Geometry json_geometry;

    //ctor
    public AfvalMand() {

    }

    public AfvalMand(String json_featuretype, String DEELGEMEENTE, String straat, String NUMMER, String type, String ligging, String mi_prinx, Date aanmaakDatum, String json_ogc_wkt_crs, Geometry json_geometry) {
        this.json_featuretype = json_featuretype;
        this.DEELGEMEENTE = DEELGEMEENTE;
        this.STRAAT = straat;
        this.NUMMER = NUMMER;
        this.TYPE = type;
        this.LIGGING = ligging;
        this.MI_PRINX = mi_prinx;
        this.aanmaakDatum = aanmaakDatum;
        this.json_ogc_wkt_crs = json_ogc_wkt_crs;
        this.json_geometry = json_geometry;
    }


    public String getJson_featuretype() {
        return json_featuretype;
    }

    public void setJson_featuretype(String json_featuretype) {
        this.json_featuretype = json_featuretype;
    }


    public String getDEELGEMEENTE() {
        return DEELGEMEENTE;
    }

    public void setDEELGEMEENTE(String DEELGEMEENTE) {
        this.DEELGEMEENTE = DEELGEMEENTE;
    }

    public String getSTRAAT() {
        return STRAAT;
    }

    public String getNUMMER() {
        return NUMMER;
    }

    public void setNUMMER(String NUMMER) {
        this.NUMMER = NUMMER;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getLIGGING() {
        return LIGGING;
    }

    public void setLIGGING(String LIGGING) {
        this.LIGGING = LIGGING;
    }

    public String getMI_PRINX() {
        return MI_PRINX;
    }

    public Date getAanmaakDatum() {
        return aanmaakDatum;
    }

    public void setAanmaakDatum(Date datum) {
        this.aanmaakDatum = datum;
    }

    public void setjson_ogc_wkt_crs(String json_ogc_wkt_crs) {
        this.json_ogc_wkt_crs = json_ogc_wkt_crs;
    }

    public String getjson_ogc_wkt_crs() {
        return json_ogc_wkt_crs;
    }

    public void setjson_geometry(Geometry json_geometry) {
        this.json_geometry = json_geometry;
    }

    public Geometry getjson_geometry() {
        return json_geometry;
    }


}
