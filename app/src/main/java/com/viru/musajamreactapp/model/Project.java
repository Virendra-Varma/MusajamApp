package com.viru.musajamreactapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by viru on 4/2/2017.
 */

public class Project {
  @SerializedName("s.no")
  @Expose
  public Integer sNo;
  @SerializedName("amt.pledged")
  @Expose
  public Integer amtPledged;
  @SerializedName("blurb")
  @Expose
  public String blurb;
  @SerializedName("by")
  @Expose
  public String by;
  @SerializedName("country")
  @Expose
  public String country;
  @SerializedName("currency")
  @Expose
  public String currency;
  @SerializedName("end.time")
  @Expose
  public String endTime;
  @SerializedName("location")
  @Expose
  public String location;
  @SerializedName("percentage.funded")
  @Expose
  public Integer percentageFunded;
  @SerializedName("num.backers")
  @Expose
  public String numBackers;
  @SerializedName("state")
  @Expose
  public String state;
  @SerializedName("title")
  @Expose
  public String title;
  @SerializedName("type")
  @Expose
  public String type;
  @SerializedName("url")
  @Expose
  public String url;

  public Project(Integer sNo, Integer amtPledged, String blurb, String by, String country,
      String currency, String endTime, String location, Integer percentageFunded,
      String numBackers, String state, String title, String type, String url) {
    this.sNo = sNo;
    this.amtPledged = amtPledged;
    this.blurb = blurb;
    this.by = by;
    this.country = country;
    this.currency = currency;
    this.endTime = endTime;
    this.location = location;
    this.percentageFunded = percentageFunded;
    this.numBackers = numBackers;
    this.state = state;
    this.title = title;
    this.type = type;
    this.url = url;
  }

  public Integer getsNo() {
    return sNo;
  }

  public Integer getAmtPledged() {
    return amtPledged;
  }

  public String getBlurb() {
    return blurb;
  }

  public String getBy() {
    return by;
  }

  public String getCountry() {
    return country;
  }

  public String getCurrency() {
    return currency;
  }

  public String getEndTime() {
    return endTime;
  }

  public String getLocation() {
    return location;
  }

  public Integer getPercentageFunded() {
    return percentageFunded;
  }

  public String getNumBackers() {
    return numBackers;
  }

  public String getState() {
    return state;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }

  @Override public String toString() {
    return "Project{" +
        "sNo=" + sNo +
        ", amtPledged=" + amtPledged +
        ", blurb='" + blurb + '\'' +
        ", by='" + by + '\'' +
        ", country='" + country + '\'' +
        ", currency='" + currency + '\'' +
        ", endTime='" + endTime + '\'' +
        ", location='" + location + '\'' +
        ", percentageFunded=" + percentageFunded +
        ", numBackers='" + numBackers + '\'' +
        ", state='" + state + '\'' +
        ", title='" + title + '\'' +
        ", type='" + type + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
